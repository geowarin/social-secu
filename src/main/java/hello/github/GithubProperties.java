package hello.github;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Date: 02/03/15
 * Time: 04:50
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
@ConfigurationProperties("spring.social.github")
public class GithubProperties {
    /**
     * Application id.
     */
    private String appId;

    /**
     * Application secret.
     */
    private String appSecret;

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
