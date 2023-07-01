package kr.inlab.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing

public class InlabApiApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(InlabApiApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
    }
}
