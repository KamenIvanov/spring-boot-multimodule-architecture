package com.pe.multimodule.dao.impl.hibernate;

public interface DbQueryFactory {

    String disableForeignKeys();

    String enableForeignKeys();

    String selectAllTableNames();
}
