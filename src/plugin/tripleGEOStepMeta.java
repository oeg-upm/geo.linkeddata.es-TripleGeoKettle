package plugin;

import java.util.List;
import java.util.Map;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.core.CheckResult;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.Counter;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleValueException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.w3c.dom.Node;

/**
 * This interface allows custom steps to talk to Kettle. The StepMetaInterface is the main Java 
 * interface that a plugin implements. 
 * http://javadoc.pentaho.com/kettle/org/pentaho/di/trans/step/StepMetaInterface.html
 */
public class tripleGEOStepMeta extends BaseStepMeta implements StepMetaInterface {

	private static Class<?> PKG = tripleGEOStepMeta.class;

	private String attributeName;
	private String feature;
	private String ontologyNS;
	private String ontologyNSPrefix;
	private String resourceNS;
	private String resourceNSPrefix;
	private String language;
	private boolean uuidsActive = false;
	private FieldDefinition[] fields;
	private ColumnDefinition[] columns;

	public tripleGEOStepMeta() {}	

	/**
	 * Get the XML that represents the values in this step
	 * @return the XML that represents the metadata in this step
	 * @throws KettleException - in case there is a conversion or XML encoding error
	 */
	public String getXML() throws KettleValueException {		
		String retval = "";
		retval = retval + "\t\t<attributename>" + getAttributeName() + "</attributename>" + Const.CR;
		retval = retval + "\t\t<feature>" + getFeature() + "</feature>" + Const.CR;
		retval = retval + "\t\t<ontologyns>" + getOntologyNS() + "</ontologyns>" + Const.CR;
		retval = retval + "\t\t<ontologynsprefix>" + getOntologyNSPrefix() + "</ontologynsprefix>" + Const.CR;
		retval = retval + "\t\t<resourcens>" + getResourceNS() + "</resourcens>" + Const.CR;    
		retval = retval + "\t\t<resourcensprefix>" + getResourceNSPrefix() + "</resourcensprefix>" + Const.CR;
		retval = retval + "\t\t<language>" + getLanguage() + "</language>" + Const.CR;
		retval = retval + "\t\t<uuidsactive>" + isUuidsActive() + "</uuidsactive>" + Const.CR;	

		if (this.fields != null) {
			retval = retval + "\t\t<fields>" + Const.CR;	    	
			for (FieldDefinition field : this.fields) {
				retval = retval + "\t\t<fields>" + Const.CR;
				retval = retval + "\t\t" + XMLHandler.addTagValue("prefix", field.prefix);
				retval = retval + "\t\t" + XMLHandler.addTagValue("uri", field.uri);
				retval = retval + "\t\t</fields>" + Const.CR;
			}
			retval = retval + "\t\t</fields>" + Const.CR;
		}

		if (this.columns != null) {
			retval = retval + "\t\t<columns>" + Const.CR;	    	
			for (ColumnDefinition col : this.columns) {
				retval = retval + "\t\t<columns>" + Const.CR;
				retval = retval + "\t\t" + XMLHandler.addTagValue("column", col.column);
				retval = retval + "\t\t" + XMLHandler.addTagValue("prefix", col.prefix);
				retval = retval + "\t\t" + XMLHandler.addTagValue("uri", col.uri);
				retval = retval + "\t\t" + XMLHandler.addTagValue("show", col.show);
				retval = retval + "\t\t</columns>" + Const.CR;
			}
			retval = retval + "\t\t</columns>" + Const.CR;
		}

		return retval;	    
	}

	/**
	 * Get the fields that are emitted by this step
	 * @param inputRowMeta - The fields that are entering the step. These are changed to reflect the output metadata.
	 * @param name - The name of the step to be used as origin
	 * @param info - The input rows metadata that enters the step through the specified channels in the same order as 
	 * 				 in method getInfoSteps(). The step metadata can then choose what to do with it: ignore it or not. 
	 * 				 Interesting is also that in case of database lookups, the layout of the target database table is 
	 * 				 put in info[0]
	 * @param nextStep - if this is a non-null value, it's the next step in the transformation. The one who's asking, 
	 * 					 the step where the data is targetted towards.
	 * @param space
	 * @throws KettleStepException - when an error occurred searching for the fields.
	 */
	public void getFields(RowMetaInterface r, String origin, RowMetaInterface[] info, StepMeta nextStep, VariableSpace space){
		ValueMetaInterface v = new ValueMeta();
		v.setName(Constants.outputField);
		v.setType(2);
		v.setTrimType(3);
		v.setOrigin(origin);	    
		if (this.uuidsActive)
			v.setStorageType(1);		
		r.addValueMeta(v);	    
	}

	/**
	 * Make an exact copy of this step, make sure to explicitly copy Collections, etc.
	 * @return an exact copy of this step
	 */
	public Object clone() {
		Object retval = super.clone();
		return retval;
	}

	/**
	 * This method is called by PDI whenever a step needs to read its settings from XML. 
	 * The XML node containing the step's settings is passed in as an argument.	
	 * @param stepnode - the Node to get the info from
	 * @param databases - The available list of databases to reference to
	 * @param counters - Counters to reference.
	 * @throws KettleXMLException - When an unexpected XML error occurred.
	 */	
	public void loadXML(Node stepnode, List<DatabaseMeta> databases, Map<String, Counter> counters) 
			throws KettleXMLException {
		try {
			setAttributeName(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode,"attributename")));
			setFeature(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode,"feature")));
			setOntologyNS(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode,"ontologyns")));
			setOntologyNSPrefix(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode,"ontologynsprefix")));
			setResourceNS(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode,"resourcens")));      
			setResourceNSPrefix(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode,"resourcensprefix")));
			setLanguage(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode,"language")));							
			setUuidsActive("true".equalsIgnoreCase(XMLHandler.getTagValue(stepnode, "uuidsactive")));
			Node fieldsNode = XMLHandler.getSubNode(stepnode, "fields");
			if (fieldsNode != null) {
				int nrfields = XMLHandler.countNodes(fieldsNode, "fields");
				fields = new FieldDefinition[nrfields];
				for (int i = 0; i < nrfields; i++) {
					fields[i] = new FieldDefinition();
					Node fnode = XMLHandler.getSubNodeByNr(fieldsNode, "fields", i);
					fields[i].prefix = XMLHandler.getTagValue(fnode, "prefix");
					fields[i].uri = XMLHandler.getTagValue(fnode, "uri");				    
				}
			}			
			Node columnsNode = XMLHandler.getSubNode(stepnode, "columns");
			if (columnsNode != null) {
				int nrcolumns = XMLHandler.countNodes(columnsNode, "columns");
				columns = new ColumnDefinition[nrcolumns];
				for (int i = 0; i < nrcolumns; i++) {
					columns[i] = new ColumnDefinition();
					Node cnode = XMLHandler.getSubNodeByNr(columnsNode, "columns", i);
					columns[i].column = XMLHandler.getTagValue(cnode, "column");
					columns[i].prefix = XMLHandler.getTagValue(cnode, "prefix");
					columns[i].uri = XMLHandler.getTagValue(cnode, "uri");
					columns[i].show = XMLHandler.getTagValue(cnode, "show");
				}
			}	
		} catch (Exception e){
			throw new KettleXMLException("tripleGEO Plugin Unable to read step info from XML node", e);
		}
	}

	/**
	 * Sets the default values.	
	 */
	public void setDefault(){
		this.attributeName = "DESBDT";
		this.feature = "Barrio";
		this.ontologyNS = "http://vocab.linkeddata.es/datosabiertos/def/sector-publico/territorio#";
		this.ontologyNSPrefix = "esadm";
		this.resourceNS = "http://geo.linkeddata.es/resource/";    
		this.resourceNSPrefix = "georesource";
		this.language = "es";
		this.uuidsActive = false;
	}	

	/**
	 * Checks the settings of this step and puts the findings in a remarks List.
	 * @param remarks - The list to put the remarks in @see org.pentaho.di.core.CheckResult
	 * @param stepMeta - The stepMeta to help checking
	 * @param prev - The fields coming from the previous step
	 * @param input - The input step names
	 * @param output - The output step names
	 * @param info - The fields that are used as information by the step
	 */
	public void check(List<CheckResultInterface> remarks, TransMeta transmeta, StepMeta stepMeta, 
			RowMetaInterface prev, String[] input, String[] output, RowMetaInterface info){
		CheckResult cr;		
		if (prev == null || prev.size() == 0) {
			cr = new CheckResult(CheckResult.TYPE_RESULT_WARNING, "Not receiving any fields from previous steps!", stepMeta);
			remarks.add(cr);
		} else {
			cr = new CheckResult(CheckResult.TYPE_RESULT_OK, "Step is connected to previous one, receiving " + prev.size() 
			+ " fields.", stepMeta);
			remarks.add(cr);
		}		
		if (input.length > 0) {
			cr = new CheckResult(1, "Step is receiving info from other steps.", stepMeta);
			remarks.add(cr);
		} else {
			cr = new CheckResult(4, "No input received from other steps!", stepMeta);
			remarks.add(cr);
		}
	}

	/**
	 * Get the name of the class that implements the dialog for this job entry JobEntryBase provides a default
	 */
	public StepDialogInterface getDialog(Shell shell, StepMetaInterface meta, TransMeta transMeta, String name){
		return new tripleGEOStepDialog(shell, meta, transMeta, name);
	}

	/**
	 * Get the executing step, needed by Trans to launch a step.
	 * @param stepMeta - The step info
	 * @param stepDataInterface - the step data interface linked to this step. Here the step can store temporary data, 
	 * 							  database connections, etc.
	 * @param copyNr - The copy nr to get
	 * @param transMeta - The transformation info
	 * @param trans - The launching transformation
	 */
	public StepInterface getStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int cnr, 
			TransMeta transMeta, Trans disp){
		return new tripleGEOStep(stepMeta, stepDataInterface, cnr, transMeta, disp);
	}

	/**
	 * Get a new instance of the appropriate data class. This data class implements the StepDataInterface. 
	 * It basically contains the persisting data that needs to live on, even if a worker thread is terminated.
	 * @return The appropriate StepDataInterface class.
	 */
	public StepDataInterface getStepData(){
		return new tripleGEOStepData();
	}  

	/**
	 * Read the steps information from a Kettle repository
	 * @param rep - The repository to read from
	 * @param id_step - The step ID
	 * @param databases - The databases to reference
	 * @param counters - The counters to reference
	 * @throws KettleException - When an unexpected error occurred (database, network, etc)
	 */
	public void readRep(Repository rep, long id_step, List<DatabaseMeta> databases, Map<String, Counter> counters) 
			throws KettleException {
		try {
			this.attributeName = rep.getStepAttributeString(id_step, "attributename");
			this.feature = rep.getStepAttributeString(id_step, "feature");
			this.ontologyNS = rep.getStepAttributeString(id_step, "ontologyns");
			this.ontologyNSPrefix = rep.getStepAttributeString(id_step, "ontologynsprefix");
			this.resourceNS = rep.getStepAttributeString(id_step, "resourcens");
			this.resourceNSPrefix = rep.getStepAttributeString(id_step, "resourcensprefix");
			this.language = rep.getStepAttributeString(id_step, "language");
			this.uuidsActive = rep.getStepAttributeBoolean(id_step, "uuidsactive");			
			if (this.language == null) { this.language = ""; }
			if (this.ontologyNSPrefix == null) { this.ontologyNSPrefix = ""; }
			if (this.resourceNSPrefix == null) { this.resourceNSPrefix = ""; }
		} catch (Exception e) {
			throw new KettleException(BaseMessages.getString(PKG.getName(), 
					"tripleGEO.Exception.UnexpectedErrorInReadingStepInfo"), e);
		}
	}	  

	/**
	 * Save the steps data into a Kettle repository
	 * @param rep - The Kettle repository to save to
	 * @param id_transformation - The transformation ID
	 * @param id_step - The step ID
	 * @throws KettleException - When an unexpected error occurred (database, network, etc)
	 */
	public void saveRep(Repository rep, long id_transformation, long id_step) throws KettleException {
		try {
			rep.saveStepAttribute(id_transformation, id_step, "attributename", this.attributeName);
			rep.saveStepAttribute(id_transformation, id_step, "feature", this.feature);
			rep.saveStepAttribute(id_transformation, id_step, "ontologyns", this.ontologyNS);
			rep.saveStepAttribute(id_transformation, id_step, "ontologynsprefix", this.ontologyNSPrefix);
			rep.saveStepAttribute(id_transformation, id_step, "resourcens", this.resourceNS);
			rep.saveStepAttribute(id_transformation, id_step, "resourcensprefix", this.resourceNSPrefix);
			rep.saveStepAttribute(id_transformation, id_step, "language", this.language);   
			rep.saveStepAttribute(id_transformation, id_step, "uuidsactive", this.uuidsActive);
		} catch (Exception e) {
			throw new KettleException(BaseMessages.getString(PKG.getName(), 
					"tripleGEO.Exception.UnableToSaveStepInfoToRepository") + id_step, e);
		}
	}

	public String[] getShow() { return new String[] { "YES", "NO" }; }	

	public String getAttributeName(){ return this.attributeName; }	  
	public void setAttributeName(String attributeName){ this.attributeName = attributeName.toUpperCase(); }

	public String getFeature(){ return this.feature;}	  
	public void setFeature(String feature){ this.feature = feature; } 

	public String getOntologyNS(){ return this.ontologyNS; }	  
	public void setOntologyNS(String ontologyNS){ this.ontologyNS = ontologyNS; }

	public String getOntologyNSPrefix(){ return this.ontologyNSPrefix; }	  
	public void setOntologyNSPrefix(String ontologyNSPrefix){ this.ontologyNSPrefix = ontologyNSPrefix; }

	public String getResourceNS(){ return this.resourceNS; }	  
	public void setResourceNS(String resourceNS){ this.resourceNS = resourceNS;}

	public String getResourceNSPrefix(){ return this.resourceNSPrefix; }	  
	public void setResourceNSPrefix(String resourceNSPrefix){ this.resourceNSPrefix = resourceNSPrefix; }

	public String getLanguage(){ return this.language; }	  
	public void setLanguage(String language){ this.language = language; }

	public boolean isUuidsActive() { return this.uuidsActive; }
	public void setUuidsActive(boolean uuidsActive) { this.uuidsActive = uuidsActive; }

	public FieldDefinition[] getFields() { return this.fields; }
	public void setFields(FieldDefinition[] fields) { this.fields = fields; }

	public ColumnDefinition[] getColumns() { return this.columns; }
	public void setColumns(ColumnDefinition[] columns) { this.columns = columns; }	

}

// END tripleGEOStepMeta.java
