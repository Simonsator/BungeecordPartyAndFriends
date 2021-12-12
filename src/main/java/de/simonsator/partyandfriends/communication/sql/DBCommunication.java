package de.simonsator.partyandfriends.communication.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author simonbrungs
 * @version 1.0.0 11.04.17
 */
public class DBCommunication {
	protected final String MYSQL_DRIVER_CLASS;

	protected DBCommunication() {
		String mysqlDriverClass = "com.mysql.cj.jdbc.Driver";
		try {
			Class.forName(mysqlDriverClass);
		} catch (ClassNotFoundException e) {
			mysqlDriverClass = "com.mysql.jdbc.Driver";
		}
		MYSQL_DRIVER_CLASS = mysqlDriverClass;
	}

	protected void close(PreparedStatement pPrepStmt) {
		try {
			if (pPrepStmt != null)
				pPrepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void close(ResultSet rs, Statement stmt) {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void close(ResultSet rs, Statement stmt, PreparedStatement prepStmt) {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (prepStmt != null)
				prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
