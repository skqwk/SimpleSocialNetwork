package ru.sqwk.ssn;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class SsnApplicationTests {

    @Test
    @Transactional
    void contextLoads() {

        Object ob = new Object();
    }

}
