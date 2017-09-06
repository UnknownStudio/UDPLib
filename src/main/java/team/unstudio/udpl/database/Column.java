package team.unstudio.udpl.database;

public class Column {
	
	private final DataType type;
	private final ColumnRule[] rules;
	private final String name;
	private final int size;

	public Column(String name, DataType type, ColumnRule[] rules, int size) {
		this.type = type;
		this.rules = rules;
		this.name = name;
		this.size = size;
	}
	
	public String toSQLCommand(){
		StringBuilder sb = new StringBuilder();

		sb.append(name).append(" ").append(type).append("(").append(size).append(")");

		if (rules != null) {
			sb.append(" ");
			for (ColumnRule r : rules) {
				sb.append(r.name).append(" ");
			}
		}

		return sb.toString();
	}
}