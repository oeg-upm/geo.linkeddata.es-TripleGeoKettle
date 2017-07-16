/*
 * tripleGEOStepData.java	version 1.0   13/11/2015
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

import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;

/**
 * Class to handle general data
 * 
 * @author Rosangelis Garcia
 * Last modified by: Rosangelis Garcia, 13/11/2015
 */
public class tripleGEOStepData extends BaseStepData implements StepDataInterface {
	
	// Indicates the output field
	public RowMetaInterface outputRowMeta;
	
    public tripleGEOStepData() {}
    
}

// END tripleGEOStepData.java
