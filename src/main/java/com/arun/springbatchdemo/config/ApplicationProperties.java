package com.arun.springbatchdemo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Getter
public class ApplicationProperties {

    private final Batch batch = new Batch();

    @Setter
    public class Batch{
        private String inputPath = "c:/input";

        public String getInputPath(){
            return inputPath;
        }

    }
}
