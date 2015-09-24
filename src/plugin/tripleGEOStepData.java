package plugin;

import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;

/**
 * Class to handle general data
 */
public class tripleGEOStepData extends BaseStepData implements StepDataInterface {
	
	// Indicates the output field
	public RowMetaInterface outputRowMeta;
	
    public tripleGEOStepData() {}
    
}

// END tripleGEOStepData.java
