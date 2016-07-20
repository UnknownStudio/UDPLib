package team.unstudio.udpc.api.tellraw;

public class Font {
	
	private Color color;
	private boolean bold,underlined,italic,strikethrough;
	
	public Font() {}
	
	public Font(Color color){
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

	public Font setColor(Color color) {
		this.color = color;
		return this;
	}

	public boolean isUnderlined() {
		return underlined;
	}

	public Font setUnderlined(boolean underlined) {
		this.underlined = underlined;
		return this;
	}

	public boolean isBold() {
		return bold;
	}

	public Font setBold(boolean bold) {
		this.bold = bold;
		return this;
	}

	public boolean isItalic() {
		return italic;
	}

	public Font setItalic(boolean italic) {
		this.italic = italic;
		return this;
	}

	public boolean isStrikethrough() {
		return strikethrough;
	}

	public Font setStrikethrough(boolean strikethrough) {
		this.strikethrough = strikethrough;
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if(color!=null)builder.append(",color:"+color.color);
		builder.append(",bold:"+bold);
		builder.append(",underlined:"+underlined);
		builder.append(",italic:"+italic);
		builder.append(",strikethrough:"+strikethrough);
		return builder.substring(1);
	}
}
