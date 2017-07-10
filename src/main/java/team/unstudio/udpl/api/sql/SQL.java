package team.unstudio.udpl.api.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface SQL {

	/**
	 * 获取数据库名称
	 */
	public String getDatabaseName();

	/**
	 * 是否已连接
	 */
	public boolean isConnected() throws SQLException;

	/**
	 * 断开连接
	 */
	public void disconnect() throws SQLException;

	/**
	 * 执行命令
	 * 
	 * @param statement 命令
	 * @return ResultSet
	 * @throws java.sql.SQLException
	 */
	public ResultSet executeQuery(String statement) throws SQLException;

	/**
	 * 执行命令
	 * 
	 * @param statement 命令
	 * @return Boolean
	 * @throws java.sql.SQLException
	 */
	public boolean execute(String statement) throws SQLException;

	/**
	 * 执行命令
	 * 
	 * @param statement
	 *            命令
	 * @return int
	 * @throws SQLException
	 */
	public int executeUpdate(String statement) throws SQLException;

	/**
	 * 添加数据
	 * 
	 * @param map
	 *            数据
	 * @return Boolean
	 * @throws SQLException
	 */
	public boolean insert(HashMap<String, String> map) throws SQLException;

	/**
	 * 更新符合条件数据
	 * 
	 * @param condition
	 *            条件
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return int
	 * @throws java.sql.SQLException
	 */
	public int update(String condition, String key, String value) throws SQLException;

	/**
	 * 删除符合条件数据
	 * 
	 * @param key
	 *            条件键
	 * @param value
	 *            条件值
	 * @return int
	 * @throws SQLException
	 */
	public int delete(String key, String value) throws SQLException;

	/**
	 * 获取键中所有符合值
	 * 
	 * @param condition
	 *            条件
	 * @param key
	 *            键
	 * @return List<Object>
	 * @throws SQLException
	 */
	public ResultSet getValuesOfKey(String condition, String key) throws SQLException;

	/**
	 * 获取键中所有符合值
	 * 
	 * @param condition
	 *            条件
	 * @param key
	 *            键
	 * @return List<Object>
	 * @throws SQLException
	 */
	public List<Object> getObjectsOfKey(String condition, String key) throws SQLException;

	/**
	 * 获取键中所有符合值
	 * 
	 * @param condition
	 *            条件
	 * @param key
	 *            键
	 * @return List<Integer>
	 * @throws SQLException
	 */
	public List<Integer> getIntegersOfKey(String condition, String key) throws SQLException;

	/**
	 * 获取键中所有符合值
	 * 
	 * @param condition
	 *            条件
	 * @param key
	 *            键
	 * @return List<String>
	 * @throws SQLException
	 */
	public List<String> getStringsOfKey(String condition, String key) throws SQLException;

	/**
	 * 键是否存在
	 * 
	 * @param key
	 *            键
	 * @return Boolean
	 * @throws java.sql.SQLException
	 */
	public boolean isKeyExist(String key) throws SQLException;

	/**
	 * 获取现有键
	 * 
	 * @return List<String>
	 * @throws SQLException
	 */
	public List<String> getKeys() throws SQLException;

	/**
	 * 键是否拥有值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return Boolean
	 * @throws SQLException
	 */
	public boolean isKeyHasValue(String key, String value) throws SQLException;

	/**
	 * 获取键信息
	 * 
	 * @param key
	 *            键
	 * @return ResultSet
	 */
	public ResultSet getKeyInfo(String key) throws SQLException;
}
