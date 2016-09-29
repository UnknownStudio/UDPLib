package team.unstudio.udpc.api.sql;

/**
 * MySql列
 * <p>
 */
public class Column {
	public final String flag;

	/**
	 * 构造列 type: VARCHAR/CHAR 字符型 TEXT 文本型 INT/SMALLINT/TINYINT 整数型 NUMERIC 浮点型
	 * MONEY/SMALLMONEY 金钱型 BIT 逻辑型 DATETIME/SMALLDATETIME 日期型
	 * <p>
	 * 
	 * @param type
	 *            类型
	 * @param rules
	 *            规则
	 * @param size
	 *            大小
	 * @param name
	 *            名称
	 */
	public Column(String type, ColumnRule[] rules, int size, String name) {
		StringBuilder sb = new StringBuilder();

		sb.append(name).append(" ").append(type).append("(").append(size)
				.append(")");

		if (rules != null) {
			sb.append(" ");
			for (ColumnRule r : rules) {
				sb.append(r.name).append(" ");
			}
		}

		this.flag = sb.toString();
	}
}