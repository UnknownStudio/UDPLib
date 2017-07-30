package team.unstudio.udpl.api.database;

import java.sql.DriverManager;
import java.sql.SQLException;

import team.unstudio.udpl.api.database.mysql.MySQL;
import team.unstudio.udpl.api.database.sqlite.SQLite;

public interface SQLHelper {

	public static MySQL createMySqlConnection(String host, int port, String userName, String password) throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new SQLException("Not found driver.", e);
		}
		return new MySQL(DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/", userName, password));
	}
	
	public static SQLite createSQLiteConnection(String path) throws SQLException{
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			throw new SQLException("Not found driver.", e);
		}
		return new SQLite(DriverManager.getConnection("jdbc:sqlite:" + path));
	}
}
