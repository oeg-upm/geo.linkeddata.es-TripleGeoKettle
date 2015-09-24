package plugin;

/**
 * Target column descriptor
 */
public class ColumnDefinition {

	String column;
	String uri;
	String prefix;	
	String show;
	
	public String getUri() { return this.uri; }
	public void setUri(String uri) { this.uri = uri; }
	
	public String getPrefix() { return this.prefix; }
	public void setPrefix(String prefix) { this.prefix = prefix; }	
	
	public String getColumn() { return this.column; }	
	public void setColumn(String column) { this.column = column; }	
	
	public String getShow() { return this.show; }
	public void setShow(String show) { this.show = show; }
	
}

// END ColumnDefinition.java
