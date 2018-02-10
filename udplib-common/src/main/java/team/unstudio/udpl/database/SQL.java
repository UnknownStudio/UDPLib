package team.unstudio.udpl.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Deprecated
public interface SQL {

	/**
	 * 获取数据库名称
	 */
	String getDatabaseName();

	/**
	 * 是否已连接
	 */
	boolean isConnected() throws SQLException;

	/**
	 * 断开连接
	 */
	void disconnect() throws SQLException;

	/**
	 * 执行命令
	 * 
	 * @param statement 命令
	 * @return ResultSet
	 */
	ResultSet executeQuery(String statement) throws SQLException;

	/**
	 * 执行命令
	 * 
	 * @param statement 命令
	 * @return Boolean
	 */
	boolean execute(String statement) throws SQLException;

	/**
	 * 执行命令
	 * 
	 * @param statement
	 *            命令
	 * @return int
	 */
	int executeUpdate(String statement) throws SQLException;

	/**
	 * 添加数据
	 * 
	 * @param map
	 *            数据
	 * @return Boolean
	 */
	boolean insert(HashMap<String, String> map) throws SQLException;

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
	 */
	int update(String condition, String key, String value) throws SQLException;

	/**
	 * 删除符合条件数据
	 * 
	 * @param key
	 *            条件键
	 * @param value
	 *            条件值
	 * @return int
	 */
	int delete(String key, String value) throws SQLException;

	/**
	 * 获取键中所有符合值
	 * 
	 * @param condition
	 *            条件
	 * @param key
	 *            键
	 * @return List<Object>
	 */
	ResultSet getValuesOfKey(String condition, String key) throws SQLException;

	/**
	 * 获取键中所有符合值
	 * 
	 * @param condition
	 *            条件
	 * @param key
	 *            键
	 * @return List<Object>
	 */
	List<Object> getObjectsOfKey(String condition, String key) throws SQLException;

	/**
	 * 获取键中所有符合值
	 * 
	 * @param condition
	 *            条件
	 * @param key
	 *            键
	 * @return List<Integer>
	 */
	List<Integer> getIntegersOfKey(String condition, String key) throws SQLException;

	/**
	 * 获取键中所有符合值
	 * 
	 * @param condition
	 *            条件
	 * @param key
	 *            键
	 * @return List<String>
	 */
	List<String> getStringsOfKey(String condition, String key) throws SQLException;

	/**
	 * 键是否存在
	 * 
	 * @param key
	 *            键
	 * @return Boolean
	 */
	boolean isKeyExist(String key) throws SQLException;

	/**
	 * 获取现有键
	 * 
	 * @return List<String>
	 */
	List<String> getKeys() throws SQLException;

	/**
	 * 键是否拥有值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return Boolean
	 */
	boolean isKeyHasValue(String key, String value) throws SQLException;

	/**
	 * 获取键信息
	 * 
	 * @param key
	 *            键
	 * @return ResultSet
	 */
	ResultSet getKeyInfo(String key) throws SQLException;
}
