package hello.github;

import hello.github.GithubProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.TwitterProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.GenericConnectionStatusView;
import org.springframework.social.github.api.GitHub;
import org.springframework.social.github.api.impl.GitHubTemplate;
import org.springframework.social.github.connect.GitHubConnectionFactory;
import org.springframework.web.servlet.View;

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
    @ConditionalOnMissingBean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public GitHub github(ConnectionRepository repository) {
        Connection<GitHub> connection = repository.findPrimaryConnection(GitHub.class);
        if (connection != null) {
            return connection.getApi();
        }
        return new GitHubTemplate(this.properties.getAppSecret());
    }

    @Bean(name = {"connect/githubConnect", "connect/githubConnected"})
    @ConditionalOnProperty(prefix = "spring.social", name = "auto-connection-views")
    public View githubConnectView() {
        return new GenericConnectionStatusView("github", "Github");
    }

    protected ConnectionFactory<?> createConnectionFactory() {
        return new GitHubConnectionFactory(this.properties.getAppId(), this.properties.getAppSecret());
    }

}
