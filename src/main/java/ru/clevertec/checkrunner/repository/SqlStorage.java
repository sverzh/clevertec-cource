package ru.clevertec.checkrunner.repository;

import ru.clevertec.checkrunner.sql.SqlHelper;

import java.sql.DriverManager;

abstract class SqlStorage {
    public final SqlHelper sqlHelper;
    private final String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    private final String dbUser = "postgres";
    private final String dbPassword = "postgres";

    public SqlStorage() {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (Exception e){
            throw new IllegalStateException("Driver didn`t load");
        }
    }
}
