package kz.iitu.springlab.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LifecycleDemo {

    private static final Logger log =
            LoggerFactory.getLogger(LifecycleDemo.class);

    public LifecycleDemo() {
        log.info("LIFECYCLE >> constructor");
    }

    @PostConstruct
    public void init() {
        log.info("LIFECYCLE >> @PostConstruct");
    }

    @PreDestroy
    public void shutdown() {
        log.info("LIFECYCLE >> @PreDestroy");
    }
}