package reddit.clone.reddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import reddit.clone.reddit.config.SwaggerConfig;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

@EnableAsync
@SpringBootApplication
@Import({SwaggerConfig.class, BeanValidatorPluginsConfiguration.class})
public class RedditApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedditApplication.class, args);
    }

}
