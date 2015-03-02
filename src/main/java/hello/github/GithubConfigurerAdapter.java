package hello.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.*;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.github.api.GitHub;
import org.springframework.social.github.api.impl.GitHubTemplate;
import org.springframework.social.github.connect.GitHubConnectionFactory;

import javax.sql.DataSource;

/**
 * Date: 02/03/15
 * Time: 04:38
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
@Configuration
@EnableSocial
@EnableConfigurationProperties(GithubProperties.class)
public class GithubConfigurerAdapter extends SocialConfigurerAdapter {

    @Autowired
    private GithubProperties properties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment environment) {
        configurer.addConnectionFactory(createConnectionFactory());
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public GitHub github(ConnectionRepository repository) {
        Connection<GitHub> connection = repository.findPrimaryConnection(GitHub.class);
        if (connection != null) {
            return connection.getApi();
        }
        return new GitHubTemplate(this.properties.getAppSecret());
    }

    @Autowired
    private DataSource dataSource;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
    }

    protected ConnectionFactory<?> createConnectionFactory() {
        return new GitHubConnectionFactory(this.properties.getAppId(), this.properties.getAppSecret());
    }
}
