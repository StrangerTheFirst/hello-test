package com.assignments.test;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import ru.yandex.qatools.embed.postgresql.PostgresExecutable;
import ru.yandex.qatools.embed.postgresql.PostgresProcess;
import ru.yandex.qatools.embed.postgresql.PostgresStarter;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class EmbeddedPostgresConfig {

    private final PostgresConfig config;

    public EmbeddedPostgresConfig(
            @Value("${embedded.postgres.net.host}") String host,
            @Value("${embedded.postgres.net.port}") int port,
            @Value("${embedded.postgres.storage}") String storage,
            @Value("${embedded.postgres.credentials.username}") String username,
            @Value("${embedded.postgres.credentials.password}") String password) throws IOException {

        config = new PostgresConfig(
                Version.V10_1,
                new AbstractPostgresConfig.Net(host, port),
                new AbstractPostgresConfig.Storage(storage),
                new AbstractPostgresConfig.Timeout(),
                new AbstractPostgresConfig.Credentials(username, password)
        );
    }

    @Bean(destroyMethod = "stop", name = "postgresProcess")
    PostgresProcess postgresProcess() throws IOException {
        PostgresStarter<PostgresExecutable, PostgresProcess> runtime = PostgresStarter.getDefaultInstance();
        PostgresExecutable exec = runtime.prepare(config);
        PostgresProcess process = exec.start();
        return process;
    }

    @Bean(destroyMethod = "close")
    @DependsOn("postgresProcess")
    DataSource dataSource() {
        PGPoolingDataSource ds = new PGPoolingDataSource();
        ds.setUser(config.credentials().username());
        ds.setPassword(config.credentials().password());
        ds.setPortNumber(config.net().port());
        ds.setServerName(config.net().host());
        ds.setDatabaseName(config.storage().dbName());
       return ds;
    }
}
