package config;

import oracle.jdbc.pool.OracleDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.constraints.NotNull;

import javax.sql.DataSource;

import java.sql.SQLException;

@Configuration
@ConfigurationProperties("oracle")
public class DataSourceConfiguration {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String url;
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    @Bean
    DataSource dataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setURL(url);
        //dataSource.setImplicitCachingEnabled(true);
        return dataSource;
    }
}