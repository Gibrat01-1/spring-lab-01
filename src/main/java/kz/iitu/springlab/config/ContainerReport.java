package kz.iitu.springlab.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ContainerReport implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(ContainerReport.class);

    private final ApplicationContext context;

    public ContainerReport(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(String... args) {
        String[] beanNames = context.getBeanDefinitionNames();

        log.info("===== CONTAINER REPORT =====");
        log.info("Total beans: {}", beanNames.length);

        Arrays.stream(beanNames)
                .sorted()
                .forEach(beanName ->
                        log.info("BEAN: {}", beanName));

        log.info("============================");
    }
}