package com.pe.multimodule.dao.impl.hibernate;

public class MysqlQueryFactory implements DbQueryFactory {

    private final String databaseName;

    public MysqlQueryFactory(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public String disableForeignKeys() {
        return "SET FOREIGN_KEY_CHECKS=0;";
    }

    @Override
    public String enableForeignKeys() {
        return "SET FOREIGN_KEY_CHECKS=1";
    }

    @Override
    public String selectAllTableNames() {
        return "SELECT table_name FROM information_schema.tables WHERE table_schema = '" + databaseName + "'";
    }
}