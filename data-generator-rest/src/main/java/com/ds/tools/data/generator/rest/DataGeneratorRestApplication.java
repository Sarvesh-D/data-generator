package com.ds.tools.data.generator.rest;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;

import lombok.extern.slf4j.Slf4j;
import reactor.core.scheduler.Schedulers;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 24 Jun 2018
 * @version 1.0
 */
@SpringBootApplication
@EnableSwagger2
@Slf4j
public class DataGeneratorRestApplication implements ApplicationListener<ContextRefreshedEvent> {

    static final String TEMP_DIR = System.getProperty("java.io.tmpdir") + "/exports";

    public static void main(final String[] args) {
        SpringApplication.run(DataGeneratorRestApplication.class, args);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.
     * springframework.context.ApplicationEvent)
     */
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        log.info("Creating Temp directory for saving exported data files...");
        try {
            FileUtils.forceMkdir(new File(TEMP_DIR));
        } catch (final IOException e) {
            log.error("Failed to create Temp directory", e);
            throw new RuntimeException(e);
        }
        log.info("Temp directory for saving exported data files created");
        Schedulers.newSingle("dir-cleaner")
                  .schedulePeriodically(() -> {
                      try {
                          log.info("Cleaning temp directory");
                          FileUtils.cleanDirectory(new File(TEMP_DIR));
                          log.info("Temp directory cleaned");
                      } catch (final IOException e) {
                          log.warn("Unable to clean temp directory", e);
                      }
                  }, 0, 30, TimeUnit.MINUTES);
        log.info("Scheduled a periodic scheduler to clean data export director every 5 minutes");
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                                                      .apis(RequestHandlerSelectors.any())
                                                      .paths(PathSelectors.any())
                                                      .build()
                                                      .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder().title("Data Generator Rest API")
                                   .description("REST APIs for generating and exporting data")
                                   .version("0.0.1-SNAPSHOT")
                                   .contact(new Contact("Sarvesh Dubey", "https://github.com/Sarvesh-D/", "sarvesh.dubey@hotmail.com"))
                                   .license("Apache License Version 2.0")
                                   .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                                   .build();
    }

}
