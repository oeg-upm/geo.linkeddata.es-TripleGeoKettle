/*
 * Configuration.java 	 version 1.0   10/01/2016
 *
 * Copyright (C) 2013 Institute for the Management of Information Systems, Athena RC, Greece.
 *
 * This library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package plugin;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Class to parse configuration files used in the library.
 *
 * @author jonathangsc
 * initially implemented for geometry2rdf utility (source: https://github.com/boricles/geometry2rdf/tree/master/Geometry2RDF)
 * Modified: 5/6/2013, Kostas Patroumpas
 * Last modified by: Rosangelis Garcia, 10/01/2016
 */
public class Configuration {

	public String path;	
	public String attributeName;
	public String feature;
	public String ontologyNS;
	public String ontologyNSPrefix;
	public String resourceNS;    
	public String resourceNSPrefix;
	public String language;
	public String pathCSV;
	public String uuids;
	public Boolean uuidsActive;

	/**
	 * Configuration
	 * @param path of the configuration file.
	 */
	public Configuration(String path) {	
		this.path = path;
	    buildConfiguration();
	}  

	/**
	 * Loads the configuration
	 */
	private void buildConfiguration() {	
		// Contains all elements of the configuration file.
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(this.path));
		} catch (IOException io) {			
			System.out.println("ERROR: No such configuration file: \"" + this.path + "\"");
			System.exit(0);
		}		
		initializeParameters(properties);		
	}		
	
	/**
	 * Returns true if the parameter is null or empty. false otherwise.
	 *
	 * @param text
	 * @return true if the parameter is null or empty.
	 */
	public static boolean isNullOrEmpty(String text) {
		if (text == null || text.equals(Constants.empty)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Initializes all the parameters from the configuration.
	 * @param properties - the properties object.
	 */
	private void initializeParameters(Properties properties) {	  
		if (!isNullOrEmpty(properties.getProperty("attributeName"))) {
			this.attributeName = properties.getProperty("attributeName");
		} else {
			this.attributeName = "ATTRIBUTE";
		}		
		if (!isNullOrEmpty(properties.getProperty("feature"))) {
			this.feature = properties.getProperty("feature");
		} else {
			this.feature = "Feature";
		}
		if (!isNullOrEmpty(properties.getProperty("ontologyNS"))) {
			this.ontologyNS = properties.getProperty("ontologyNS");
		} else {
			this.ontologyNS = "http://vocab.linkeddata.es/datosabiertos/def/sector-publico/territorio#";
		}
		if (!isNullOrEmpty(properties.getProperty("ontologyNSPrefix"))) {
			this.ontologyNSPrefix = properties.getProperty("ontologyNSPrefix");
		} else {
			this.ontologyNSPrefix = "ontprefix";
		}
	    if (!isNullOrEmpty(properties.getProperty("resourceNS"))) {
	    	this.resourceNS = properties.getProperty("resourceNS");	    	
		} else {
			this.resourceNS = "http://geo.linkeddata.es/resource/";
		}
		if (!isNullOrEmpty(properties.getProperty("resourceNSPrefix"))) {
			this.resourceNSPrefix = properties.getProperty("resourceNSPrefix");
		} else {
			this.resourceNSPrefix = "georesource";
		}
		if (!isNullOrEmpty(properties.getProperty("language"))) {
			this.language = properties.getProperty("language");
		} else {
			this.language = "es";
		}
		if (!isNullOrEmpty(properties.getProperty("pathCSV"))) {
			this.pathCSV = properties.getProperty("pathCSV");
		} else {
			this.pathCSV = Constants.null_;
		}
		if (!isNullOrEmpty(properties.getProperty("uuids"))) {
			this.uuids = properties.getProperty("uuids");			
			if (this.uuids.equalsIgnoreCase("true")){
				this.uuidsActive = true;
			} else {
				this.uuidsActive = false;
			}
		} else {
			this.uuidsActive = false;
		}
	}
}

// END Configuration.java
