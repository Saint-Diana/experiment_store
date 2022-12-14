package com.shen.experiment_store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.shen.experiment_store.mapper"})
@SpringBootApplication
public class ExperimentStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExperimentStoreApplication.class, args);
    }

}
