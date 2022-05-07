package ch.usi.si.bsc.sa4.devinecodemy.repository;

import ch.usi.si.bsc.sa4.devinecodemy.model.level.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("The level repository")
public class LevelRepositoryTest {

    @Autowired
    private LevelRepository levelRepository;

    @Test
    @DisplayName("should insert record on start")
    public void testInsertRecordOnStart() {
        List<Level> levels = levelRepository.findAll();
        assertEquals(10, levels.size(), "record count is not matching");

        levels.forEach(level -> {
            if ("level1".equals(level.getName())) {
                assertEquals("apple", level.getDescription());
            } else if ("level2".equals(level.getName())) {
                assertEquals("guava", level.getDescription());
            }
        });
    }
}
