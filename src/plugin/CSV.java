/*
 * CSV.java   version 1.0   16/11/2015
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * CSV file to generate classes.
 * 
 * @author Rosangelis Garcia
 * Last modified by: Rosangelis Garcia, 16/11/2015
 */
public class CSV {

	String path;
	String attribute;
	
	public CSV(String path) {
		setPath(path);
	}
	
	/**
	 * Check if the CSV file exists
	 * @return true, if the CSV file exists; otherwise, false.
	 */
	public Boolean exist(){			
		if (new File(getPath()).isFile()){
			return true;
		}
		return false;		
	}
	
	/**
	 * Gets the number of rows in the file
	 * @return the number of rows
	 */
	public int number_row(){
		BufferedReader buffer = null;
		int lines = 0;
		try {
			buffer = new BufferedReader(new FileReader(this.path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
		try {
			while (buffer.readLine() != null) lines++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}	
	
	/**
	 * Read the file and load the data into the 'ClassesCSV' class
	 * @return the data read in the CSV file
	 */
	public ClassesCSV[] read() {
		BufferedReader buffer = null;
		ClassesCSV[] classes = null;
		String line = "";
		Boolean flag = true;

		try {			
			buffer = new BufferedReader(new FileReader(this.path));			
			classes = new ClassesCSV[number_row() - 1];
			int i = 0;
			
			while ((line = buffer.readLine()) != null) {
				String[] lineSplit = line.split(Constants.CVS_SPLIT);			
				if (flag){
					flag = false;
					setAttribute(lineSplit[0]);
				} else {

						classes[i] = new ClassesCSV();
						classes[i].setAttribute(this.attribute);
					if (lineSplit.length != 0){
						classes[i].setColumn(lineSplit[0]);
						classes[i].setValue(lineSplit[1]);
					} else {
						classes[i].setColumn("null");
						classes[i].setValue("null");
					}
					i++;
				}				
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (buffer != null) {
				try {
					buffer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return classes;
	}
	
	public String getPath() { return path; }
	public void setPath(String path) { this.path = path; }
	
	public String getAttribute() { return this.attribute; }
	public void setAttribute(String attribute) { this.attribute = attribute; }
	
}

// END CSV.java
