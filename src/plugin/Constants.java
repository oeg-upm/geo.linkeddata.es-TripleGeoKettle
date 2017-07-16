/*
 * Constants.java	version 1.0   10/01/2016
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

/**
 * Constants
 * 
 * @author 2012 jonbaraq
 * initially implemented for geometry2rdf utility (source: https://github.com/boricles/geometry2rdf/tree/master/Geometry2RDF) 
 * Modified: 24/5/2013, Kostas Patroumpas
 * Last modified by: Rosangelis Garcia, 10/01/2016
 */
public class Constants {
	
	// Alias for most common tags
	public static final String GEOMETRY = "/geometry";
	public static final String LINE_STRING = "LineString";
	public static final String MULTI_LINE_STRING = "MultiLineString";
	public static final String POLYGON = "Polygon";
	public static final String MULTI_POLYGON = "MultiPolygon";
	public static final String POINT = "Point";
	public static final String WKT = "asWKT";
	public static final String WKTLiteral = "wktLiteral";
	public static final String UTF_8 = "UTF-8";	
	public static final String STRING_TO_REPLACE = "+";	
	public static final String SEPARATOR = "-";
	public static final String CVS_SPLIT = ",";
	public static final String SPECIAL_CHARACTER =  "áàäãâéèëêẽíìîïĩóòöõôúùüûũñ¥ÃÁÀÄÂẼÊÉÈËĨÍÌÏÎÕÓÔÒÖÔÚŨÙÜÛÑḉçÇ";
	public static final String ASCII = 				"aaaaaeeeeeiiiiiooooouuuuunnAAAAAEEEEEIIIIIOOOOOOUUUUUNccC";
	public static final String SYMBOLS = "/\\|@#~½¬ºª¡!\"·$%&()=¿?[]{}<>;:_*,.^+`'©®™¢Ø˜´";
	public static final String tmp = "./tmp";
	public static final String outputField = "rdf_output";
	public static final String format = "TURTLE";
	public static final String the_geom = "the_geom";
	public static final String null_ = "null";
	public static final String empty = "";
	public static final String space = " ";
	
	// URL Constants		
	public static final String NS_GEO = "http://www.opengis.net/ont/geosparql#";
	public static final String NS_SF =  "http://www.opengis.net/ont/sf#";
	public static final String NS_DC = "http://purl.org/dc/terms/";
	public static final String NS_XSD = "http://www.w3.org/2001/XMLSchema#";
	public static final String NS_RDFS = "http://www.w3.org/2000/01/rdf-schema#";
	public static final String NS_FOAF = "http://xmlns.com/foaf/0.1/";
	public static final String NS_WGS84 = "http://www.w3.org/2003/01/geo/wgs84_pos#";		
	public static final String NS_OWL = "http://www.w3.org/2002/07/owl#";
	public static final String NS_RDF = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
}

// END Constants.java
