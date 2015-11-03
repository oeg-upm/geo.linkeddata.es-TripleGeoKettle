package plugin;

/**
 * Target column descriptor
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
