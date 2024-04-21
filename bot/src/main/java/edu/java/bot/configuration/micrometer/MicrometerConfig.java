package edu.java.bot.configuration.micrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfig {
    @Autowired
    private MeterRegistry registry;

    @Bean
    public Counter requestCounter() {
        return registry.counter("messages_processed");
    }
}
