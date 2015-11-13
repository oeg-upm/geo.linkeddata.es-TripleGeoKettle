/*
 * ClassesCSV.java   version 1.0   13/11/2015
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
 * Classes obtained by a CSV file
 * 
 * @author Rosangelis Garcia
 * Last modified by: Rosangelis Garcia, 13/11/2015
 */
public class ClassesCSV {

	String attribute;
	String column;
	String value;
	
	public String getAttribute() { return this.attribute; }
	public void setAttribute(String attribute) { this.attribute = attribute; }
	
	public String getColumn() { return this.column; }
	public void setColumn(String column) { this.column = column; }

	public String getValue() { return this.value; }
	public void setValue(String value) { this.value = value; }	
	
}

// END ClassesCSV.java
