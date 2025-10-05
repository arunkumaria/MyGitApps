package com.home.designspatterns.structural;

public class Fascade {

	public static void main(String[] args) {
		// individual access to the server
		MysqlServer mysqlServer = new MysqlServer();
		MysqlServer.getConnection();
		mysqlServer.getPdfFormat();

		OracleServer oracleServer = new OracleServer();
		OracleServer.getConnection();
		oracleServer.getTxtFormat();

		// direct access to the servers
		MyFascade.getServerFullDetails("MysqlServer", "pdf", "employee");
		MyFascade.getServerFullDetails("OracleServer", "text", "employee");

	}

}

class MysqlServer {

	public static void getConnection() {

		System.out.println("connected to Mysql server");
	}

	public void getPdfFormat() {
		System.out.println("Mysql pdf format");

	}

	public void getTxtFormat() {
		System.out.println("Mysql text format");
	}

}

class OracleServer {

	public static void getConnection() {

		System.out.println("connected to Oracle server");
	}

	public void getPdfFormat() {
		System.out.println("Oracle pdf format");

	}

	public void getTxtFormat() {
		System.out.println("Oracle text format");
	}

}

class MyFascade {

	public static void getServerFullDetails(String dbType, String format, String tableName) {

		if (dbType.equals("MysqlServer")) {
			MysqlServer.getConnection();
			MysqlServer mysqlServer = new MysqlServer();
			switch (format) {
			case "pdf":
				mysqlServer.getPdfFormat();
				break;
			case "text":
				mysqlServer.getTxtFormat();
				break;

			}

		} else {
			OracleServer.getConnection();
			OracleServer oracleServer = new OracleServer();
			switch (format) {
			case "pdf":
				oracleServer.getPdfFormat();
				break;
			case "text":
				oracleServer.getTxtFormat();
				break;

			}

		}

	}

	public enum DbSelect {
		MYSQL, ORACLE

	}

	public enum FormatSelect {
		PDF, TEXT
	}

}