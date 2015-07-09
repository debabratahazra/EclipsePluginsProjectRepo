package com.odcgroup.otf.jpa.utils;

public enum DatabaseType {
	Sybase("sybase", "select 1+1"),
	Oracle("oracle", "select 1+1 from dual"),
	Derby("derby", "select SYSCS_UTIL.SYSCS_GET_DATABASE_PROPERTY('?')");
	
	DatabaseType(String databaseConnectionDiscriminator, String testQuery) {
		this.databaseConnectionDiscriminator = databaseConnectionDiscriminator;
		this.testQuery = testQuery;
	}
	
	private final String testQuery;
	private final String databaseConnectionDiscriminator;
	
	public String getTestQuery() {
		return testQuery;
	}
	
	public String getDatabaseConnectionDiscriminator() {
		return databaseConnectionDiscriminator;
	}
}

