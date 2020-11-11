package mk.ukim.finki.dians.api.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

//@Setter
@Validated
@Configuration
@ConfigurationProperties("mysql")
@PropertySource("classpath:application.properties")
public class MySqlConfiguration {

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
  DataSource dataSource() {
    final MysqlDataSource dataSource = new MysqlDataSource();

    dataSource.setUser(username);
    dataSource.setPassword(password);
    dataSource.setURL(url);

    return dataSource;
  }
}

