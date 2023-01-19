package team.emptyte.playersettings.api.sql.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLClient {

    private final Connection connection;

    private SQLClient(HikariDataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    public static class Builder {

        private static final String JDBC_FORMAT = "jdbc:%s://%s:%s/%s";

        private final HikariConfig hikariConfig;
        private final String sqlProtocol;
        private String host;
        private int port;
        private String database;
        private int maximumPoolSize = 0;
        private String driverClassName;

        public Builder(String sqlProtocol) {
            this.sqlProtocol = sqlProtocol;
            this.hikariConfig = new HikariConfig();
        }

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setDatabase(String database) {
            this.database = database;
            return this;
        }

        public Builder setUsername(String username) {
            hikariConfig.setUsername(username);
            return this;
        }

        public Builder setPassword(String password) {
            hikariConfig.setPassword(password);
            return this;
        }

        public Builder setMaximumPoolSize(int maximumPoolSize) {
            this.maximumPoolSize = maximumPoolSize;
            return this;
        }

        public Builder setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
            return this;
        }

        public SQLClient build() throws SQLException {
            hikariConfig.setJdbcUrl(String.format(JDBC_FORMAT, sqlProtocol, host, port, database));

            if (driverClassName != null) {
                hikariConfig.setDriverClassName(driverClassName);
            }

            hikariConfig.setMaximumPoolSize(maximumPoolSize);

            return new SQLClient(new HikariDataSource(hikariConfig));
        }
    }
}
