package com.kasia.model.repository;

import com.kasia.model.UserConnectBudgetRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserConnectBudgetRequestRepositoryTest {
    private UserConnectBudgetRequest ucbr;
    @Autowired
    private UserConnectBudgetRequestRepository ucbrR;

    @Before
    public void before() {
        ucbr = new UserConnectBudgetRequest();
    }

    @After
    public void after() {
        ucbrR.deleteAll();
    }

    @Test
    public void save() {
        assertThat(ucbr.getId() == 0).isTrue();
        ucbr = ucbrR.save(ucbr);
        assertThat(ucbr.getId() != 0).isTrue();
    }

    @Test
    public void findById() {
        ucbr.setBudgetId(100);
        ucbr.setFromUserId(3);
        ucbr.setToUserId(2);
        ucbr = ucbrR.save(ucbr);

        assertThat(ucbrR.findById(ucbr.getId()).isPresent()).isTrue();
        assertThat(ucbrR.findById(ucbr.getId()).get()).isEqualTo(ucbr);
    }

    @Test
    public void delete() {
        ucbr.setBudgetId(100);
        ucbr.setFromUserId(3);
        ucbr.setToUserId(2);
        ucbr = ucbrR.save(ucbr);
        ucbrR.delete(ucbr);

        assertThat(ucbrR.findById(ucbr.getId()).isPresent()).isFalse();
    }

    @Test
    public void findByFromUser() {
        ucbr.setBudgetId(100);
        ucbr.setFromUserId(3);
        ucbr.setToUserId(2);
        ucbr = ucbrR.save(ucbr);

        assertThat(ucbrR.findFromUserId(3).size() == 1).isTrue();
        assertThat(ucbrR.findFromUserId(3).contains(ucbr)).isTrue();
    }

    @Test
    public void findByToUser() {
        ucbr.setBudgetId(100);
        ucbr.setFromUserId(3);
        ucbr.setToUserId(2);
        ucbr = ucbrR.save(ucbr);

        assertThat(ucbrR.findToUserId(2).size() == 1).isTrue();
        assertThat(ucbrR.findToUserId(2).contains(ucbr)).isTrue();
    }

    @Test
    public void findByToUser2() {
        ucbr.setId(0);
        ucbr.setBudgetId(100);
        ucbr.setFromUserId(3);
        ucbr.setToUserId(2);
        ucbr = ucbrR.save(ucbr);

        ucbr.setId(0);
        ucbr.setBudgetId(1002);
        ucbr.setFromUserId(30);
        ucbr.setToUserId(2);
        ucbr = ucbrR.save(ucbr);
        assertThat(ucbrR.findToUserId(2).size() == 2).isTrue();
    }

}