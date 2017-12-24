package team.unstudio.udpl.database.mysql;

import team.unstudio.udpl.database.Column;
import team.unstudio.udpl.database.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * MySql支持类 直接面向MySql的操作，以后更改为HashMap读写
 */
public class MySQL implements SQL {

	private String schema;
	private String table;
	private final Connection connection;

	public MySQL(Connection connection){
		this.connection = connection;
	}

	/**
	 * 设置数据库
	 * <p>
	 * 
	 * @param schema
	 *            数据库
	 */
	public void setSchema(String schema) throws SQLException {
		if (!connection.isClosed()) {
			this.schema = schema;
		} else {
			this.schema = null;
		}
	}

	/**
	 * 是否拥有schema
	 * <p>
	 * 
	 * @return Boolean
	 */
	public boolean hasSchema() throws SQLException {
		int i = 0;
		PreparedStatement sql = this.connection.prepareStatement("SHOW Schemas Like '" + this.schema + "';");
		ResultSet result = sql.executeQuery();

		while (result.next()) {
			i += 1;
		}

		return i > 0;
	}

	/**
	 * 设置表格
	 * <p>
	 * 
	 * @param table
	 *            表
	 */
	public void setTable(String table) throws SQLException {
		if (!this.connection.isClosed()) {
			this.table = table;
		} else {
			this.table = null;
		}
	}

	/**
	 * 是否拥有table
	 * <p>
	 * 
	 * @return Boolean
	 */
	public boolean hasTable() throws SQLException {
		if (!this.connection.isClosed()) {
			try {
				PreparedStatement sql = this.connection
						.prepareStatement("SELECT * FROM " + this.schema + "." + this.table + ";");

				return sql.execute();
			} catch (SQLException ex) {
				return false;
			}
		}

		return false;
	}

	/**
	 * 获取数据库
	 * <p>
	 * 
	 * @return String
	 */
	public String getSchema() {
		return this.schema;
	}

	/**
	 * 获取表格
	 * <p>
	 * 
	 * @return String
	 */
	public String getTable() {
		return this.table;
	}

	@Override
	public boolean isConnected() throws SQLException {
		return !this.connection.isClosed();
	}

	@Override
	public synchronized void disconnect() throws SQLException {
		if (!this.connection.isClosed()) {
			this.connection.close();
		}
	}

	/**
	 * 创建数据库
	 * <p>
	 * 
	 * @param name
	 *            数据库名称
	 * @return Boolean
	 */
	public synchronized boolean createSchema(String name) throws SQLException {
		PreparedStatement sql = this.connection.prepareStatement("create database " + name);
		return sql.execute();
	}

	/**
	 * 创建表格
	 * <p>
	 * 
	 * @param table
	 *            表名称
	 * @param slots
	 *            列
	 * @return Boolean
	 */
	public synchronized boolean createTable(String table, Column[] slots) throws SQLException {
		if (this.isConnected()) {
			if (this.schema != null) {
				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < slots.length; i++) {
					Column slot = slots[i];
					sb.append(slot.toSQLCommand());
					if (i < slots.length - 1) {
						sb.append(", ");
					}
				}

				PreparedStatement sql = this.connection
						.prepareStatement("CREATE TABLE " + this.schema + "." + table + "(" + sb.toString() + ");");
				return sql.execute();
			}
		}

		return false;
	}

	@Override
	public synchronized ResultSet executeQuery(String statement) throws SQLException {
		if (this.isConnected()) {
			PreparedStatement sql = this.connection.prepareStatement(statement);
			return sql.executeQuery();
		}

		return null;
	}

	@Override
	public synchronized boolean execute(String statement) throws SQLException {
		if (this.isConnected()) {
			PreparedStatement sql = this.connection.prepareStatement(statement);
			return sql.execute();
		}

		return false;
	}

	@Override
	public synchronized int executeUpdate(String statement) throws SQLException {
		if (this.isConnected()) {
			PreparedStatement sql = this.connection.prepareStatement(statement);
			return sql.executeUpdate();
		}

		return 0;
	}

	@Override
	public synchronized boolean insert(HashMap<String, String> map) throws SQLException {
		if (this.isConnected()) {
			if (this.getTable() != null) {
				StringBuilder keys = new StringBuilder();
				StringBuilder values = new StringBuilder();

				for (String key : map.keySet()) {
					keys.append(key).append(",");
					values.append("?").append(",");
				}

				if (keys.length() > 0) {
					keys.deleteCharAt(keys.length() - 1);
				}

				if (values.length() > 0) {
					values.deleteCharAt(values.length() - 1);
				}

				PreparedStatement sql = this.connection.prepareStatement("Insert into " + this.table + " ("
						+ keys.toString() + ")" + " values (" + values.toString() + ")");
				int i = 1;
				for (String key : map.keySet()) {
					sql.setString(i, map.get(key));
					i++;
				}
				return sql.execute();
			}
		}

		return false;
	}

	@Override
	public synchronized int update(String condition, String key, String value) throws SQLException {
		if (this.isConnected()) {
			if (this.getSchema() != null && this.getTable() != null) {
				PreparedStatement sql = this.connection.prepareStatement("Update " + this.schema + "." + this.table
						+ " SET " + key + "='" + value + "' WHERE " + condition + ";");
				return sql.executeUpdate();
			}
		}

		return 0;
	}

	@Override
	public synchronized int delete(String key, String value) throws SQLException {
		if (this.isConnected()) {
			if (this.getSchema() != null && this.getTable() != null) {
				PreparedStatement sql = this.connection.prepareStatement(
						"DELETE FROM " + this.schema + "." + this.table + " WHERE " + key + " ='" + value + "';");
				return sql.executeUpdate();
			}
		}

		return 0;
	}

	@Override
	public synchronized ResultSet getValuesOfKey(String condition, String key) throws SQLException {
		if (this.isConnected()) {
			if (this.getTable() != null) {
				PreparedStatement sql = this.connection
						.prepareStatement("SELECT " + key + " FROM " + this.table + " WHERE " + condition + ";");
				return sql.executeQuery();
			}
		}

		return null;
	}

	@Override
	public synchronized List<Object> getObjectsOfKey(String condition, String key) throws SQLException {
		if (this.isConnected()) {
			if (this.getTable() != null) {
				List<Object> values = new ArrayList<>();

				PreparedStatement sql = this.connection
						.prepareStatement("SELECT " + key + " FROM " + this.table + " WHERE " + condition + ";");
				ResultSet result = sql.executeQuery();

				while (result.next()) {
					values.add(result.getObject(key));
				}

				return values;
			}
		}

		return null;
	}

	@Override
	public synchronized List<Integer> getIntegersOfKey(String condition, String key) throws SQLException {
		List<Integer> list = new ArrayList<>();
		for (Object o : this.getObjectsOfKey(condition, key)) {
			list.add((Integer) o);
		}

		return list;
	}

	@Override
	public synchronized List<String> getStringsOfKey(String condition, String key) throws SQLException {
		List<String> list = new ArrayList<>();
		for (Object o : this.getObjectsOfKey(condition, key)) {
			list.add((String) o);
		}

		return list;
	}

	@Override
	public synchronized boolean isKeyExist(String key) throws SQLException {
		if (this.isConnected()) {
			if (this.getTable() != null) {
				int size = 0;
				PreparedStatement sql = this.connection.prepareStatement("SELECT " + key + " FROM " + this.table + ";");
				ResultSet result = sql.executeQuery();

				while (result.next()) {
					size++;
				}

				return size > 0;
			}
		}

		return false;
	}

	@Override
	public synchronized List<String> getKeys() throws SQLException {
		if (this.isConnected()) {
			if (this.getTable() != null) {
				List<String> keys = new ArrayList<>();
				PreparedStatement sql = this.connection.prepareStatement(
						"select COLUMN_NAME from information_schema.COLUMNS where table_name = '" + this.table + "';");
				ResultSet result = sql.executeQuery();

				while (result.next()) {
					keys.add(result.getString("COLUMN_NAME"));
				}

				return keys;
			}
		}

		return null;
	}

	@Override
	public synchronized boolean isKeyHasValue(String key, String value) throws SQLException {
		return (this.getObjectsOfKey(key + "='" + value + "'", key) != null) && (this.getObjectsOfKey(key + "='" + value + "'", key).size() > 0);
	}

	@Override
	public synchronized ResultSet getKeyInfo(String key) throws SQLException {
		return this.connection.prepareStatement("show columns from " + this.table + " where Field = '" + key + "';")
				.executeQuery();
	}

	@Override
	public String getDatabaseName() {
		return "mysql";
	}
}