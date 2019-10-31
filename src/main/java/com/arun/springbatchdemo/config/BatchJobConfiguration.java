package com.arun.springbatchdemo.config;

import lombok.val;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class BatchJobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }


    public Job job(Step step) {
        return this.jobBuilderFactory
                .get(Constants.JOB_NAME)
                .validator(validator())
                .start(step)
                .build();
    }


    @Bean
    public JobParametersValidator validator() {

        return (JobParameters parameters) -> {
            val fileName = parameters.getString(Constants.JOB_PARAM_FILE_NAME);
            if (StringUtils.isEmpty(fileName)) {
                throw new RuntimeException();
            }

            Path files = Paths.get(applicationProperties.getBatch().getInputPath() + File.separator + fileName);
            if (Files.notExists(files) || !Files.isReadable(files)) {
                throw new RuntimeException();
            }
        };
    }


    @Bean
    public Step step() {
        return this.stepBuilderFactory
                .get(Constants.STEP_NAME)
                .tasklet((StepContribution contribution, ChunkContext chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }
}