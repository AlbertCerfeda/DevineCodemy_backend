package ch.usi.si.bsc.sa4.devinecodemy.service;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch.usi.si.bsc.sa4.devinecodemy.service.LevelService;

@SpringBootTest
@DisplayName("The Level Service")
public class LevelServiceTests {

    @Autowired
    LevelService levelService;

}
