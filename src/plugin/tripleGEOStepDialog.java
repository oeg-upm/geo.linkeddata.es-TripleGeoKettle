/*
 * tripleGEOStepDialog.java	version 1.0   10/01/2016
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

import org.apache.commons.io.FilenameUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.exception.KettleValueException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.ui.core.widget.ColumnInfo;
import org.pentaho.di.ui.core.widget.TableView;
import org.pentaho.di.ui.trans.step.BaseStepDialog;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

/**
 * This interface is used to launch Step Dialogs. All dialogs that implement this simple interface 
 * can be opened by Spoon.
 * http://javadoc.pentaho.com/kettle/org/pentaho/di/trans/step/StepDialogInterface.html
 * 
 * @author Rosangelis Garcia
 * Last modified by: Rosangelis Garcia, 10/01/2016
 */
public class tripleGEOStepDialog extends BaseStepDialog implements StepDialogInterface {

	private static String PKG = tripleGEOStepDialog.class.getPackage().getName();

	private tripleGEOStepMeta input;

	private Label wlAttributeName;
	private Text wAttributeName;
	private FormData fdlAttributeName;
	private FormData fdAttributeName;

	private Label wlFeature;
	private Text wFeature;
	private FormData fdlFeature;
	private FormData fdFeature;

	private Label wlOntologyNS;
	private Text wOntologyNS;
	private FormData fdlOntologyNS;
	private FormData fdOntologyNS;

	private Label wlOntologyNSPrefix;
	private Text wOntologyNSPrefix;
	private FormData fdlOntologyNSPrefix;
	private FormData fdOntologyNSPrefix;

	private Label wlResourceNS;
	private Text wResourceNS;
	private FormData fdlResourceNS;
	private FormData fdResourceNS;

	private Label wlResourceNSPrefix;
	private Text wResourceNSPrefix;
	private FormData fdlResourceNSPrefix;
	private FormData fdResourceNSPrefix;

	private Label wlLanguage;
	private Text wLanguage;
	private FormData fdlLanguage;
	private FormData fdLanguage;	

	private Label wlPathCSV;
	private Text wPathCSV;
	private FormData fdlPathCSV;
	private FormData fdPathCSV;	
	private Button wbbPathCSV;
	
	private Label wluuids;
	private Button wuuids;
	private FormData fdluuids;
	private FormData fduuids;

	private TableView wFields;	
	private FormData fdFields;

	private Label wlColumns;
	private TableView wColumns;	
	private FormData fdlColumns;
	private FormData fdColumns;

	private CTabFolder wTabFolder;
	private FormData fdTabFolder;

	private Button wRestartFields;
	private Listener lsRestartFields;

	private CTabItem wMainTab;

	private TransMeta trans;

	/**
	 * Instantiates a new base step dialog.
	 * @param parent - the parent shell
	 * @param baseStepMeta - the associated base step metadata
	 * @param transMeta - the associated transformation metadata
	 * @param stepname - the step name
	 */
	public tripleGEOStepDialog(Shell parent, Object in, TransMeta transMeta, String sname) {
		super(parent, (BaseStepMeta)in, transMeta, sname);
		this.input = ((tripleGEOStepMeta)in);
		this.trans = transMeta;
	}

	/**
	 * Opens a step dialog window.
	 * @return the (potentially new) name of the step
	 */
	public String open() {

		Shell parent = getParent();
		Display display = parent.getDisplay();

		this.shell = new Shell(parent, 3312);

		this.props.setLook(this.shell);
		setShellImage(this.shell, this.input);

		ModifyListener lsMod = new ModifyListener() {
			public void modifyText(ModifyEvent e){ tripleGEOStepDialog.this.input.setChanged(); }
		};
		this.changed = this.input.hasChanged();

		FormLayout formLayout = new FormLayout();
		formLayout.marginWidth = 5;
		formLayout.marginHeight = 5;

		this.shell.setLayout(formLayout);
		this.shell.setText(BaseMessages.getString(PKG, "tripleGEOStepDialog.Shell.Title"));

		int middle = this.props.getMiddlePct();
		int margin = 4;	    

		// STEPNAME
		this.wlStepname = new Label(this.shell, 131072);
		this.wlStepname.setText(BaseMessages.getString(PKG, "System.Label.StepName"));
		this.props.setLook(this.wlStepname);
		this.fdlStepname = new FormData();
		this.fdlStepname.left = new FormAttachment(0, 0);
		this.fdlStepname.right = new FormAttachment(middle, -margin);
		this.fdlStepname.top = new FormAttachment(0, margin);
		this.wlStepname.setLayoutData(this.fdlStepname);

		this.wStepname = new Text(this.shell, 18436);
		this.wStepname.setText(this.stepname);
		this.props.setLook(this.wStepname);
		this.wStepname.addModifyListener(lsMod);
		this.fdStepname = new FormData();
		this.fdStepname.left = new FormAttachment(middle, 0);
		this.fdStepname.top = new FormAttachment(0, margin);
		this.fdStepname.right = new FormAttachment(100, 0);
		this.wStepname.setLayoutData(this.fdStepname);	    

		// TAB FOLDER
		this.wTabFolder = new CTabFolder(this.shell, 2048);
		this.props.setLook(this.wTabFolder, 5);
		this.fdTabFolder = new FormData();
		this.fdTabFolder.left = new FormAttachment(0, 0);
		this.fdTabFolder.top = new FormAttachment(this.wStepname, 4 * margin);
		this.fdTabFolder.right = new FormAttachment(100, 0);
		this.fdTabFolder.bottom = new FormAttachment(100, -50);
		this.wTabFolder.setLayoutData(this.fdTabFolder);

		this.wMainTab = new CTabItem(this.wTabFolder, 0);
		this.wMainTab.setText(BaseMessages.getString(PKG, "tripleGEOStepDialog.Tab.MainTab"));

		Composite cData = new Composite(this.wTabFolder, 0);
		cData.setBackground(this.shell.getDisplay().getSystemColor(1));
		FormLayout formLayout2 = new FormLayout();
		formLayout2.marginWidth = this.wTabFolder.marginWidth;
		formLayout2.marginHeight = this.wTabFolder.marginHeight;
		cData.setLayout(formLayout2);

		this.wMainTab.setControl(cData);    
		this.wTabFolder.setSelection(0);

		// ATTRIBUTE NAME
		this.wlAttributeName = new Label(cData, 131072);
		this.wlAttributeName.setText(BaseMessages.getString(PKG,"tripleGEOStepDialog.AttributeName.Label"));
		this.props.setLook(this.wlAttributeName);
		this.fdlAttributeName = new FormData();
		this.fdlAttributeName.left = new FormAttachment(0, 0);
		this.fdlAttributeName.right = new FormAttachment(middle, -margin);
		this.fdlAttributeName.top = new FormAttachment(0, margin);
		this.wlAttributeName.setLayoutData(this.fdlAttributeName);

		this.wAttributeName = new Text(cData, 18436);
		this.props.setLook(this.wAttributeName);
		this.wAttributeName.addModifyListener(lsMod);
		this.fdAttributeName = new FormData();
		this.fdAttributeName.left = new FormAttachment(middle, 0);
		this.fdAttributeName.right = new FormAttachment(100, -margin);
		this.fdAttributeName.top = new FormAttachment(0, margin);
		this.wAttributeName.setLayoutData(this.fdAttributeName);

		// FEATURE	    
		this.wlFeature = new Label(cData, 131072);
		this.wlFeature.setText(BaseMessages.getString(PKG,"tripleGEOStepDialog.Feature.Label"));
		this.props.setLook(this.wlFeature);
		this.fdlFeature = new FormData();
		this.fdlFeature.left = new FormAttachment(0, 0);
		this.fdlFeature.right = new FormAttachment(middle, -margin);
		this.fdlFeature.top = new FormAttachment(this.wAttributeName, margin);
		this.wlFeature.setLayoutData(this.fdlFeature);

		this.wFeature = new Text(cData, 18436);
		this.props.setLook(this.wFeature);
		this.wFeature.addModifyListener(lsMod);
		this.fdFeature = new FormData();
		this.fdFeature.left = new FormAttachment(middle, 0);
		this.fdFeature.right = new FormAttachment(100, -margin);
		this.fdFeature.top = new FormAttachment(this.wAttributeName, margin);
		this.wFeature.setLayoutData(this.fdFeature);

		// ONTOLOGY NAMESPACE
		this.wlOntologyNS = new Label(cData, 131072);
		this.wlOntologyNS.setText(BaseMessages.getString(PKG,"tripleGEOStepDialog.OntologyNS.Label"));
		this.props.setLook(this.wlOntologyNS);
		this.fdlOntologyNS = new FormData();
		this.fdlOntologyNS.left = new FormAttachment(0, 0);
		this.fdlOntologyNS.right = new FormAttachment(middle, -margin);
		this.fdlOntologyNS.top = new FormAttachment(this.wFeature, margin);
		this.wlOntologyNS.setLayoutData(this.fdlOntologyNS);

		this.wOntologyNS = new Text(cData, 18436);
		this.props.setLook(this.wOntologyNS);
		this.wOntologyNS.addModifyListener(lsMod);
		this.fdOntologyNS = new FormData();
		this.fdOntologyNS.left = new FormAttachment(middle, 0);
		this.fdOntologyNS.right = new FormAttachment(100, -margin);
		this.fdOntologyNS.top = new FormAttachment(this.wFeature, margin);
		this.wOntologyNS.setLayoutData(this.fdOntologyNS);

		// ONTOLOGY NAMESPACE PREFIX
		this.wlOntologyNSPrefix = new Label(cData, 131072);
		this.wlOntologyNSPrefix.setText(BaseMessages.getString(PKG,"tripleGEOStepDialog.OntologyNSPrefix.Label"));
		this.props.setLook(this.wlOntologyNSPrefix);
		this.fdlOntologyNSPrefix = new FormData();
		this.fdlOntologyNSPrefix.left = new FormAttachment(0, 0);
		this.fdlOntologyNSPrefix.right = new FormAttachment(middle, -margin);
		this.fdlOntologyNSPrefix.top = new FormAttachment(this.wOntologyNS, margin);
		this.wlOntologyNSPrefix.setLayoutData(this.fdlOntologyNSPrefix);

		this.wOntologyNSPrefix = new Text(cData, 18436);
		this.props.setLook(this.wOntologyNSPrefix);
		this.wOntologyNSPrefix.addModifyListener(lsMod);
		this.fdOntologyNSPrefix = new FormData();
		this.fdOntologyNSPrefix.left = new FormAttachment(middle, 0);
		this.fdOntologyNSPrefix.right = new FormAttachment(100, -margin);
		this.fdOntologyNSPrefix.top = new FormAttachment(this.wOntologyNS, margin);
		this.wOntologyNSPrefix.setLayoutData(this.fdOntologyNSPrefix);

		// RESOURCE NAMESPACE
		this.wlResourceNS = new Label(cData, 131072);
		this.wlResourceNS.setText(BaseMessages.getString(PKG,"tripleGEOStepDialog.ResourceNS.Label"));
		this.props.setLook(this.wlResourceNS);
		this.fdlResourceNS = new FormData();
		this.fdlResourceNS.left = new FormAttachment(0, 0);
		this.fdlResourceNS.right = new FormAttachment(middle, -margin);
		this.fdlResourceNS.top = new FormAttachment(this.wOntologyNSPrefix, margin);
		this.wlResourceNS.setLayoutData(this.fdlResourceNS);

		this.wResourceNS = new Text(cData, 18436);
		this.props.setLook(this.wResourceNS);
		this.wResourceNS.addModifyListener(lsMod);
		this.fdResourceNS = new FormData();
		this.fdResourceNS.left = new FormAttachment(middle, 0);
		this.fdResourceNS.right = new FormAttachment(100, -margin);
		this.fdResourceNS.top = new FormAttachment(this.wOntologyNSPrefix, margin);
		this.wResourceNS.setLayoutData(this.fdResourceNS);

		// RESOURCE NAMESPACE PREFIX
		this.wlResourceNSPrefix = new Label(cData, 131072);
		this.wlResourceNSPrefix.setText(BaseMessages.getString(PKG,"tripleGEOStepDialog.ResourceNSPrefix.Label"));
		this.props.setLook(this.wlResourceNSPrefix);
		this.fdlResourceNSPrefix = new FormData();
		this.fdlResourceNSPrefix.left = new FormAttachment(0, 0);
		this.fdlResourceNSPrefix.right = new FormAttachment(middle, -margin);
		this.fdlResourceNSPrefix.top = new FormAttachment(this.wResourceNS, margin);
		this.wlResourceNSPrefix.setLayoutData(this.fdlResourceNSPrefix);

		this.wResourceNSPrefix = new Text(cData, 18436);
		this.props.setLook(this.wResourceNSPrefix);
		this.wResourceNSPrefix.addModifyListener(lsMod);
		this.fdResourceNSPrefix = new FormData();
		this.fdResourceNSPrefix.left = new FormAttachment(middle, 0);
		this.fdResourceNSPrefix.right = new FormAttachment(100, -margin);
		this.fdResourceNSPrefix.top = new FormAttachment(this.wResourceNS, margin);
		this.wResourceNSPrefix.setLayoutData(this.fdResourceNSPrefix);

		// LANGUAGE
		this.wlLanguage = new Label(cData, 131072);
		this.wlLanguage.setText(BaseMessages.getString(PKG,"tripleGEOStepDialog.Language.Label"));
		this.props.setLook(this.wlLanguage);
		this.fdlLanguage = new FormData();
		this.fdlLanguage.left = new FormAttachment(0, 0);
		this.fdlLanguage.right = new FormAttachment(middle, -margin);
		this.fdlLanguage.top = new FormAttachment(this.wResourceNSPrefix, margin);
		this.wlLanguage.setLayoutData(this.fdlLanguage);

		this.wLanguage = new Text(cData, 18436);
		this.props.setLook(this.wLanguage);
		this.wLanguage.addModifyListener(lsMod);
		this.fdLanguage = new FormData();
		this.fdLanguage.left = new FormAttachment(middle, 0);
		this.fdLanguage.right = new FormAttachment(100, -margin);
		this.fdLanguage.top = new FormAttachment(this.wResourceNSPrefix, margin);
		this.wLanguage.setLayoutData(this.fdLanguage);
		
		// PATH CSV PathCSV
		this.wbbPathCSV = new Button(cData,131072);
		props.setLook(this.wbbPathCSV);
		this.wbbPathCSV.setText(BaseMessages.getString(PKG,"tripleGEOStepDialog.PathCSVButton.Label"));
		this.wbbPathCSV.setToolTipText(BaseMessages.getString(PKG,"tripleGEOStepDialog.PathCSVButtonTooltip.Label"));
		FormData fdbFilename = new FormData();
		fdbFilename.top = new FormAttachment(this.wLanguage, margin);
		fdbFilename.right = new FormAttachment(100, 0);
		this.wbbPathCSV.setLayoutData(fdbFilename);
		
		this.wlPathCSV = new Label(cData, 131072);
		this.wlPathCSV.setText(BaseMessages.getString(PKG,"tripleGEOStepDialog.PathCSV.Label"));
		this.props.setLook(this.wlPathCSV);
		this.fdlPathCSV = new FormData();
		this.fdlPathCSV.left = new FormAttachment(0, 0);
		this.fdlPathCSV.right = new FormAttachment(middle, -margin);
		this.fdlPathCSV.top = new FormAttachment(this.wLanguage, margin);
		this.wlPathCSV.setLayoutData(this.fdlPathCSV);

		this.wPathCSV = new Text(cData, 18436);
		this.props.setLook(this.wPathCSV);
		this.wPathCSV.addModifyListener(lsMod);
		this.fdPathCSV = new FormData();
		this.fdPathCSV.left = new FormAttachment(middle, 0);
		this.fdPathCSV.right = new FormAttachment(this.wbbPathCSV, -margin);
		this.fdPathCSV.top = new FormAttachment(this.wLanguage, margin);
		this.wPathCSV.setLayoutData(this.fdPathCSV);	
			
		// UUIDS
		this.wluuids = new Label(cData, 131072);
		this.wluuids.setText(BaseMessages.getString(PKG,"tripleGEOStepDialog.uuids.Label"));
		this.props.setLook(this.wluuids);
		this.fdluuids = new FormData();
		this.fdluuids.left = new FormAttachment(0, 0);
		this.fdluuids.right = new FormAttachment(middle, -margin);
		this.fdluuids.top = new FormAttachment(this.wPathCSV, margin);	    
		this.wluuids.setLayoutData(fdluuids);

		this.wuuids = new Button(cData, 32);
		this.props.setLook(this.wuuids);
		this.fduuids = new FormData();
		this.fduuids.left = new FormAttachment(middle, 0);
		this.fduuids.right = new FormAttachment(100, -margin);
		this.fduuids.top = new FormAttachment(this.wPathCSV, margin);
		this.wuuids.setLayoutData(fduuids);	    

		// CHECKBOX UUIDS
		Listener lsUuids = new Listener() { 
			public void handleEvent(Event e) {
				tripleGEOStepDialog.this.input.setUuidsActive(tripleGEOStepDialog.this.wuuids.getSelection());
			}
		};		
		this.wuuids.addListener(13, lsUuids);		

		// FIELDS
		ColumnInfo[] ciFields = new ColumnInfo[2];
		ciFields[0] = new ColumnInfo(BaseMessages.getString(PKG,"tripleGEOStepDialog.otherPrefix.Label"), 
				ColumnInfo.COLUMN_TYPE_TEXT, false);
		ciFields[1] = new ColumnInfo(BaseMessages.getString(PKG,"tripleGEOStepDialog.other.Label"), 
				ColumnInfo.COLUMN_TYPE_TEXT, false);		

		this.wFields = new TableView(this.transMeta, cData, SWT.BORDER
				| SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL,
				ciFields, this.input.getFields() == null ? 1
						: this.input.getFields().length, lsMod, this.props);
		this.fdFields = new FormData();
		this.fdFields.left = new FormAttachment(0, 0);
		this.fdFields.top = new FormAttachment(this.wuuids, margin);
		this.fdFields.right = new FormAttachment(100, 0);
		this.wFields.setLayoutData(this.fdFields);

		// COLUMNS		
		this.wlColumns = new Label(cData, 131072);
		this.wlColumns.setText(BaseMessages.getString(PKG,"tripleGEOStepDialog.Columns.Label"));
		this.props.setLook(this.wlColumns);
		this.fdlColumns = new FormData();
		this.fdlColumns.left = new FormAttachment(0, 0);
		this.fdlColumns.right = new FormAttachment(middle, -margin);
		this.fdlColumns.top = new FormAttachment(this.wFields, margin);	    
		this.wlColumns.setLayoutData(fdlColumns);		

		ColumnInfo[] ciColumns = new ColumnInfo[4];
		ciColumns[0] = new ColumnInfo(BaseMessages.getString(PKG,"tripleGEOStepDialog.ShowColumns.Label"), 
				2, this.input.getShow(), true);
		ciColumns[1] = new ColumnInfo(BaseMessages.getString(PKG,"tripleGEOStepDialog.Column.Label"), 
				ColumnInfo.COLUMN_TYPE_TEXT, false);
		ciColumns[2] = new ColumnInfo(BaseMessages.getString(PKG,"tripleGEOStepDialog.otherPrefixColumns.Label"), 
				ColumnInfo.COLUMN_TYPE_TEXT, false);
		ciColumns[3] = new ColumnInfo(BaseMessages.getString(PKG,"tripleGEOStepDialog.otherColumns.Label"), 
				ColumnInfo.COLUMN_TYPE_TEXT, false);

		this.wColumns = new TableView(this.transMeta, cData, SWT.BORDER | SWT.MULTI,
				ciColumns, this.input.getColumns() == null ? 1
						: this.input.getColumns().length, true, lsMod, this.props);
		this.wColumns.setSortable(false); // Turns off sort arrows
		this.fdColumns = new FormData();
		this.fdColumns.left = new FormAttachment(0, 0);
		this.fdColumns.top = new FormAttachment(this.wlColumns, margin);
		this.fdColumns.right = new FormAttachment(100, 0);
		this.wColumns.setLayoutData(this.fdColumns);			    

		// OK - CANCEL
		this.wOK = new Button(this.shell, 8);
		this.wOK.setText(BaseMessages.getString(PKG,"System.Button.OK"));	    
		this.wCancel = new Button(this.shell, 8);
		this.wCancel.setText(BaseMessages.getString(PKG,"System.Button.Cancel"));	    

		this.wRestartFields = new Button(this.shell, 8);
		this.wRestartFields.setText(BaseMessages.getString(PKG,"tripleGEOStepDialog.RestartFields.Button"));

		BaseStepDialog.positionBottomButtons(this.shell, 
				new Button[] { this.wOK, this.wRestartFields, this.wCancel }, margin, null);

		this.lsCancel = new Listener(){ public void handleEvent(Event e){ tripleGEOStepDialog.this.cancel(); } };	    
		this.lsOK = new Listener(){ public void handleEvent(Event e){tripleGEOStepDialog.this.ok(); } };
		this.lsRestartFields = new Listener() { 
			public void handleEvent(Event e) { 			
				tripleGEOStepDialog.this.wColumns.removeAll();			
				loadColumns();
			}   
		};		

		this.wCancel.addListener(13, this.lsCancel);	    
		this.wOK.addListener(13, this.lsOK);
		this.wRestartFields.addListener(13, this.lsRestartFields);	    

		this.lsDef = new SelectionAdapter(){
			public void widgetDefaultSelected(SelectionEvent e) { tripleGEOStepDialog.this.ok(); }
		};

		this.wStepname.addSelectionListener(this.lsDef);

		// Detect X or ALT-F4 or something that kills this window
		this.shell.addShellListener(new ShellAdapter(){
			public void shellClosed(ShellEvent e){ tripleGEOStepDialog.this.cancel(); }
		});
		
		// Listen to the browse button next to the file name
		this.wbbPathCSV.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell, SWT.OPEN);
				dialog.setFilterExtensions(new String[] { "*.csv;*.CSV", "*" });
				if (tripleGEOStepDialog.this.wPathCSV.getText() != null) {
					dialog.setFileName(tripleGEOStepDialog.this.wPathCSV.getText());
			        }
	
				dialog.setFilterNames(new String[] {"CSV files", "All files" });

				if (dialog.open() != null) {
					String str = dialog.getFilterPath()
							+ System.getProperty("file.separator")
							+ dialog.getFileName();
					tripleGEOStepDialog.this.wPathCSV.setText(str);				
				}	
			}
		});
		
		// Set the shell size, based upon previous time
		setSize(this.shell, 600, 600, true);

		getData();

		this.input.setChanged(this.changed);

		this.shell.open();
		while (!this.shell.isDisposed()) {
			if (!display.readAndDispatch()) { display.sleep(); }
		}

		return this.stepname;
	}

	/**
	 * Collect data from the meta and place it in the dialog.
	 */
	public void getData() {		
		this.wStepname.selectAll();
		this.wAttributeName.setText(this.input.getAttributeName());
		this.wFeature.setText(this.input.getFeature());
		this.wOntologyNS.setText(this.input.getOntologyNS());
		this.wOntologyNSPrefix.setText(this.input.getOntologyNSPrefix());
		this.wResourceNS.setText(this.input.getResourceNS());
		this.wResourceNSPrefix.setText(this.input.getResourceNSPrefix());
		this.wLanguage.setText(this.input.getLanguage());    
		this.wPathCSV.setText(this.input.getPathCSV());
		this.wuuids.setSelection(this.input.isUuidsActive());

		FieldDefinition[] fields = this.input.getFields();
		if (fields != null) {
			for (FieldDefinition field : fields) {
				TableItem item = new TableItem(this.wFields.table, SWT.NONE);
				int colnr = 1;
				item.setText(colnr++, Const.NVL(field.prefix, Constants.empty));
				item.setText(colnr++, Const.NVL(field.uri, Constants.empty));
			}
		}
		this.wFields.removeEmptyRows();
		this.wFields.setRowNums();
		this.wFields.optWidth(true);		

		ColumnDefinition[] columns = this.input.getColumns();
		if (columns != null || (columns != null && !this.trans.haveHopsChanged())) {
			for (ColumnDefinition c : columns) {
				TableItem item = new TableItem(this.wColumns.table, SWT.NONE);
				int colnr = 1;
				item.setText(colnr++, Const.NVL(c.show, "YES"));
				item.setText(colnr++, Const.NVL(c.column, Constants.empty));
				if (c.getColumn().equalsIgnoreCase(Constants.the_geom)){					
					item.setText(colnr++, "n/a");
					item.setText(colnr++, "n/a");				
				} else {
					item.setText(colnr++, Const.NVL(c.prefix, Constants.empty));
					item.setText(colnr++, Const.NVL(c.uri, Constants.empty));
				}
			}
		} else if ((columns != null && this.trans.haveHopsChanged()) || (columns == null)){
			loadColumns();
		}
		
		this.wColumns.removeEmptyRows();
		this.wColumns.setRowNums();
		this.wColumns.optWidth(true);
	}

	/**
	 * Let the tripleGEOStepMeta know about the entered data
	 * @throws KettleValueException 
	 */
	private void ok() {
		this.stepname = this.wStepname.getText();		

		if (!isEmpty(this.wAttributeName.getText())) {
			this.input.setAttributeName(this.wAttributeName.getText());
		} else {	    	
			this.input.setAttributeName(this.input.getAttributeName());
		}

		if (!isEmpty(this.wFeature.getText())) {
			this.input.setFeature(this.wFeature.getText());
		} else {
			this.input.setFeature(this.input.getFeature());
		} 	    

		if (!isEmpty(this.wOntologyNS.getText())) {
			this.input.setOntologyNS(this.wOntologyNS.getText());
		} else {
			this.input.setOntologyNS(this.input.getOntologyNS());
		}

		if (!isEmpty(this.wOntologyNSPrefix.getText())) {
			this.input.setOntologyNSPrefix(this.wOntologyNSPrefix.getText());
		} else {
			this.input.setOntologyNSPrefix(this.input.getOntologyNSPrefix());
		}

		if (!isEmpty(this.wResourceNS.getText())) {
			this.input.setResourceNS(this.wResourceNS.getText());
		} else {
			this.input.setResourceNS(this.input.getResourceNS());
		}

		if (!isEmpty(this.wResourceNSPrefix.getText())) {
			this.input.setResourceNSPrefix(this.wResourceNSPrefix.getText());
		} else {
			this.input.setResourceNSPrefix(this.input.getResourceNSPrefix());
		}

		if (!isEmpty(this.wLanguage.getText())) {
			this.input.setLanguage(this.wLanguage.getText());
		} else {
			this.input.setLanguage(Constants.null_);
		}
		
		if (!isEmpty(this.wPathCSV.getText())) {	
			String ext = FilenameUtils.getExtension(this.wPathCSV.getText());
			if (ext.equalsIgnoreCase("csv")){
				this.input.setPathCSV(this.wPathCSV.getText());
			} else {
				this.input.setPathCSV(this.input.getPathCSV());
			}
		} else {
			this.input.setPathCSV(Constants.null_);
		}
		
		int nrNonEmptyFields = this.wFields.nrNonEmpty();
		FieldDefinition[] fields = new FieldDefinition[nrNonEmptyFields];
		for (int i = 0; i < nrNonEmptyFields; i++) {
			TableItem item = this.wFields.getNonEmpty(i);
			fields[i] = new FieldDefinition();
			int colnr = 1;
			fields[i].prefix = item.getText(colnr++);
			fields[i].uri = item.getText(colnr++);
		}		
		this.input.setFields(fields);		
		this.wFields.removeEmptyRows();
		this.wFields.setRowNums();
		this.wFields.optWidth(true);
		

		int nrNonEmptyColumns = this.wColumns.nrNonEmpty();	
		ColumnDefinition[] columns = new ColumnDefinition[nrNonEmptyColumns];
		for (int i = 0; i < nrNonEmptyColumns; i++) {
			TableItem item = this.wColumns.getNonEmpty(i);
			columns[i] = new ColumnDefinition();
			int colnr = 1;
			columns[i].show = item.getText(colnr++);			
			if (i == 0){
				columns[i].column = "the_geom";
				colnr++;
			} else {			
				String col = item.getText(colnr++);
				if (!isEmpty(col)) {			
					columns[i].column = col;
				} else {
					columns[i].column = "empty";
				}
			}			
			columns[i].prefix = item.getText(colnr++);
			@SuppressWarnings("unused")
			Boolean flag = true;
			if (columns[i].column.equalsIgnoreCase("the_geom")){
				flag = false;
			} else {
				flag = true;
			}			
			String uri = item.getText(colnr++);
			if (isValidURL(uri,true)){
				columns[i].uri = uri;
			} else {
				columns[i].uri = null;
			}			
		}	
	
		if (nrNonEmptyColumns > 0){	
			this.input.setColumns(columns);
						
			if (this.input.getColumn_name() == null) {
				loadColumnName();
			}
			
			int j = 0;
			for (String c : this.input.getColumn_name()) {
				columns[j].column_shp = c;				
				if (columns[j].column.equalsIgnoreCase("empty")){
					columns[j].column = c;
				}
				j++;
			}
		}

		this.wColumns.removeEmptyRows();
		this.wColumns.setRowNums();
		this.wColumns.optWidth(true);		
		this.input.setChanged(); 
		dispose();
	}	
	
	/**
	 * Cancel
	 */
	private void cancel() {
		this.stepname = null;
		this.input.setChanged(this.changed);
		dispose();
	}
	

	/**
	 * Verifies if the string is empty
	 * @param string - The string
	 * @return true if the string is empty; otherwise, false
	 */
	public static boolean isEmpty(String string) {
		if (string == null || string.length() == 0) {
			return true;
		}
		for (int i = 0; i < string.length(); i++) {
			if ((Character.isWhitespace(string.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Validate an URL
	 * @param url
	 * @return true if the url is valid; otherwise, false
	 */	
	public boolean isValidURL(String url, Boolean flag) {
		if (flag) {
			URL u = null;
			try {  
				u = new URL(url);  
			} catch (MalformedURLException e) {  
				return false;  
			}
			try {  
				u.toURI();  
			} catch (URISyntaxException e) {  
				return false;  
			}  
			return true;  
		}
		return true;
	}

	
	/**
	 * Load columns name in a ArrayList
	 */
	private void loadColumnName() {		
		try {			
			ArrayList<String> column_name = new ArrayList<String>();	
			if (this.trans.getPrevStepFields(this.trans.findStep(this.wStepname.getText())) != null){
				RowMetaInterface rm = this.trans.getPrevStepFields(this.trans.findStep(this.wStepname.getText()));				
				for (ValueMetaInterface vmeta : rm.getValueMetaList()) {
					column_name.add(vmeta.getName());
				}
			}
			this.input.setColumn_name(column_name);
		} catch (KettleStepException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * Load columns shapefile
	 */
	private void loadColumns() {		
		try {			
			ArrayList<String> column_name = new ArrayList<String>();			
			RowMetaInterface rm = this.trans.getPrevStepFields(this.trans.findStep(this.wStepname.getText()));				
			for (ValueMetaInterface vmeta : rm.getValueMetaList()) {
				TableItem item = new TableItem(this.wColumns.table, 0);
				item.setText(1, "YES");
				item.setText(2, vmeta.getName());
				if (vmeta.getName().equalsIgnoreCase(Constants.the_geom)){
					item.setText(3, "n/a");
					item.setText(4, "n/a");		
				}
				column_name.add(vmeta.getName());
			}			

			this.input.setColumn_name(column_name);
			
			this.wColumns.removeEmptyRows();
			this.wColumns.setRowNums();
			this.wColumns.optWidth(true);			
		} catch (KettleStepException e) {
			e.printStackTrace();
		}
	}	
}

// END tripleGEOStepDialog.java
