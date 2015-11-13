/*
 * ColumnDefinition.java	version 1.0   13/11/2015
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

/**
 * Target column descriptor
 * 
 * @author Rosangelis Garcia
 * Last modified by: Rosangelis Garcia, 13/11/2015
 */
public class ColumnDefinition {

	String column;
	String column_shp;
	String uri;
	String prefix;	
	String show;	
	
	public String getUri() { return this.uri; }
	public void setUri(String uri) { this.uri = uri; }
	
	public String getPrefix() { return this.prefix; }
	public void setPrefix(String prefix) { this.prefix = prefix; }	
	
	public String getColumn() { return this.column; }	
	public void setColumn(String column) { this.column = column; }

	public String getColumn_shp() { return this.column_shp; }
	public void setColumn_shp(String column_shp) { this.column_shp = column_shp; }	
	
	public String getShow() { return this.show; }
	public void setShow(String show) { this.show = show; }
	
}

// END ColumnDefinition.java
