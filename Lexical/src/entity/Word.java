//entity.Word
package entity;

public class Word {
	private String type;
	private String name;
	private int line;
	
	public Word(String type, String name,int line) {
		super();
		this.type = type;
		this.name = name;
		this.line = line;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getLine() {
		return line;
	}
	
	public String getType() {
		return type;
	}
}
