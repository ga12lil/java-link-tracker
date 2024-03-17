package edu.java.configuration;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ScheduleConfiguration {
    @Bean
    public long schedulerIntervalMs(ApplicationConfig config) {
        return config.scheduler().interval().toMillis();
    }

    @Bean
    public Duration forceCheckDelay(ApplicationConfig config) {
        return config.scheduler().forceCheckDelay();
    }
}
