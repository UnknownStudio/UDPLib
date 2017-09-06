package team.unstudio.udpl.database;

public enum ColumnRule {
	/**
	 * 主键
	 */
	PrimaryKey("Primary Key"),
	/**
	 * 非空
	 */
	NotNull("Not Null"),
	/**
	 * 唯一标识
	 */
	Unique("Unique"),
	/**
	 * 二进制
	 */
	Binary("Binary"),
	/**
	 * 无符号
	 */
	Unsigned("Unsigned"),
	/**
	 * 自动补零
	 */
	ZeroFill("Zero Fill"),
	/**
	 * 自动增加
	 */
	AutoIncrement("Auto Increment");
	public String name;

	private ColumnRule(String name) {
		this.name = name;
	}
}