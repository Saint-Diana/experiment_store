package com.shen.experiment_store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class ExperimentStoreApplicationTests {
    @Autowired
    private DataSource dataSource;

    @Test
    public void getConnection() throws Exception {
        System.out.println(dataSource.getConnection());
    }

    @Test
    void contextLoads() {
    }

}
