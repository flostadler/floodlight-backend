package de.flostadler.floodlight.configuration;

import com.google.maps.GeoApiContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeoApiConfiguration {

    @Bean
    public GeoApiContext geoApiContextBeanFactory(ApiProperties apiProperties) {
        return new GeoApiContext.Builder()
                .apiKey(apiProperties.getGoogleCloudKey())
                .build();
    }
}
