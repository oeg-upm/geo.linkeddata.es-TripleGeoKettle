/*
 * tripleGEOStep.java	version 1.0   13/11/2015
 *
 * Copyright (C) 2015 Ontology Engineering Group, Universidad Politecnica de Madrid, Spain
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0	 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package plugin;

import java.io.IOException;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStep;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;

/**
 * The interface that any transformation step or plugin needs to implement.
 * http://javadoc.pentaho.com/kettle/org/pentaho/di/trans/step/StepInterface.html
 * 
 * @author Rosangelis Garcia
 * Last modified by: Rosangelis Garcia, 13/11/2015
 */
public class tripleGEOStep extends BaseStep implements StepInterface {

	private tripleGEOStepData data;
	private tripleGEOStepMeta meta;	
	private ShpToRDF shpToRDF;

	public tripleGEOStep(StepMeta s, StepDataInterface stepDataInterface, int c, TransMeta t, Trans dis) {
		super(s, stepDataInterface, c, t, dis);
	}	

	/**
	 * Perform the equivalent of processing one row. Typically this means reading a row from input 
	 * (getRow()) and passing a row to output (putRow)).
	 * @param smi - The steps metadata to work with
	 * @param sdi - The steps temporary working data to work with (database connections, result sets, 
	 *              caches, temporary variables, etc.)
	 * @return false if no more rows can be processed or an error occurred.
	 * @throws KettleException
	 */
	public boolean processRow(StepMetaInterface smi, StepDataInterface sdi) throws KettleException {
		this.meta = ((tripleGEOStepMeta)smi);
		this.data = ((tripleGEOStepData)sdi);

		Object[] row = getRow();

		if (row == null) {

			if (this.shpToRDF != null) {
				RowMetaInterface rm = this.data.outputRowMeta;

				while (rm.getValueMetaList().size() > 1) {
					rm.getValueMetaList().remove(0);
				}

				logBasic("Generating RDF.");

				this.meta = null; // Helps the garbage collector		        

				Object[] result = new Object[1];
				result[0] = this.shpToRDF.getModel_rdf();

				putRow(this.data.outputRowMeta, result);

				logBasic("RDF generated.");		        
			} else {	    		
				logBasic("No rows processed.");
			}

			setOutputDone();		
			return false;
		}

		// Load attributes in shpToRDF and init RDF
		if (this.first){
			this.first = false;

			// Check CSV file 
			Boolean flag_csv = false;
			ClassesCSV[] classes = null;
			CSV csv = null;
			if (!this.meta.getPathCSV().equalsIgnoreCase("null")){
				csv = new CSV(this.meta.getPathCSV());				
				if (csv.exist()){					
					classes = csv.read();
					flag_csv = true;
				} else {
					flag_csv = false;
					logBasic("The CSV file doesn't exist.");					
				}				
			}
			
			this.shpToRDF = new ShpToRDF(this.meta,flag_csv,classes,csv);			
			
			try {	
				this.shpToRDF.getModelFromConfiguration(); // Init RDF	    		
			} catch (Throwable t) {
				System.out.println(t.toString() + t.getCause().getMessage());
				t.printStackTrace();
			}

			this.data.outputRowMeta = getInputRowMeta().clone();
			this.meta.getFields(this.data.outputRowMeta, getStepname(), null, null, this);  	

			if (this.meta.getAttributeName() == null){
				logBasic("Attribute not found (check your tripleGEO settings and restart columns)");
				throw new KettleException("tripleGEO.Exception.AttributesNotFound: Check your tripleGEO "
						+ "settings and restart columns.");
			}
						
			
			if ((this.meta.getAttributeName() != null) && (!this.meta.getAttributeName().isEmpty())){
				RowMetaInterface rm = this.data.outputRowMeta;

				int pos = 0;
				int flag = 0;
				for (ValueMetaInterface vmeta : rm.getValueMetaList()) {

					// Check columns
					if (this.meta.getColumns() != null){
						flag = 0;
						for (ColumnDefinition c : this.meta.getColumns()) {
							if (c.getColumn_shp() != null){
								if (c.getColumn_shp().equalsIgnoreCase(vmeta.getName())
										|| vmeta.getName().equalsIgnoreCase(Constants.outputField)){
									flag++;
								}
							} else {
								if (c.getColumn().equalsIgnoreCase(vmeta.getName())
										|| vmeta.getName().equalsIgnoreCase(Constants.outputField)){
									c.setColumn_shp(vmeta.getName());
									flag++;
								}								
							}
						}	    			
						if (flag == 0){
							logBasic("Attribute not found (check your tripleGEO settings and restart columns)");
							throw new KettleException("tripleGEO.Exception.AttributesNotFound: Check your tripleGEO "
									+ "settings and restart columns.");
						}
					}

					if (vmeta.getName().equalsIgnoreCase(this.meta.getAttributeName())) {
						this.shpToRDF.setPosAttribute(pos);
					}

					if (vmeta.getName().equalsIgnoreCase(Constants.the_geom)) {
						this.shpToRDF.setPosGeometry(pos);
					}

					pos++;
				}
			}   	

			if (this.shpToRDF.getPosAttribute() < 0){
				logBasic("Field not found (check your tripleGEO settings): " + this.meta.getAttributeName());
				throw new KettleException("tripleGEO.Exception.FieldForReadNotFound: " + this.meta.getAttributeName());
			}

			logBasic("tripleGEO step initialized successfully.");		
		}

		// Process Row 
		try {	    	
			this.shpToRDF.writeRdfModel(row,this.data.outputRowMeta);	    	
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * Initialize and do work where other steps need to wait for...
	 * @param stepMetaInterface - The metadata to work with
	 * @param stepDataInterface - The data to initialize
	 */	
	public boolean init(StepMetaInterface smi, StepDataInterface sdi) {
		this.meta = ((tripleGEOStepMeta)smi);
		this.data = ((tripleGEOStepData)sdi);
		return super.init(smi, sdi);
	}	

	/**
	 * Dispose of this step: close files, empty logs, etc.
	 * @param sii - The metadata to work with
	 * @param sdi - The data to dispose of
	 */
	public void dispose(StepMetaInterface smi, StepDataInterface sdi){
		this.meta = ((tripleGEOStepMeta)smi);
		this.data = ((tripleGEOStepData)sdi);    
		super.dispose(smi, sdi);
	}

	/**
	 * Run is were the action happens
	 */
	public void run(){	
		logBasic("Starting to run.");		
		try {			
			while (processRow(this.meta,this.data) && !isStopped());
		} catch(Exception e) {
			logError("Unexpected error: " + e.toString());
			logError(Const.getStackTracker(e));
			setErrors(1);
			stopAll();
		} finally {
			dispose(this.meta, this.data);
			logBasic("Finished, processing " + getLinesRead() + " rows.");
			markStop();
		}
	}
}

// END tripleGEOStep.java
