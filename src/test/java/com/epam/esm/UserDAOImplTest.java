package com.epam.esm;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.configuration.TestConfiguration;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.entity.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {TestConfiguration.class})
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = "classpath:/data_test_H2.sql")
class UserDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    void findAllUsers() {
        Page page = new Page();
        List<User> usersList = userDAO.findAll(page);
        Assertions.assertEquals(3, usersList.size());
    }

    @Test
    void findUserById() {
        Long id = 1L;
        Optional<User> actual = userDAO.findById(id);
        Assertions.assertEquals("Zhenya", actual.get().getName());
    }
}
