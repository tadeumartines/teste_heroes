package br.com.gubee.interview.config;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.type.UUIDCharType;

public class CustomPostgreSQLDialect extends PostgreSQL95Dialect {
    public CustomPostgreSQLDialect() {
        super();
        registerColumnType(java.sql.Types.OTHER, "pg-uuid");
        registerColumnType(java.sql.Types.JAVA_OBJECT, "pg-uuid");
        registerHibernateType(-1, UUIDCharType.INSTANCE.getName());
        registerHibernateType(-2, UUIDCharType.INSTANCE.getName());
    }
}
