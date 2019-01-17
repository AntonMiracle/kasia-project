package com.kasia.validation;

import com.kasia.ModelTestData;
import com.kasia.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserValidationServiceIT {
    @Autowired
    private UserValidationService validationService;

    @Test
    public void userIsValid() throws Exception {
        User user1 = ModelTestData.getUser1();
        User user2 = ModelTestData.getUser2();
        assertThat(validationService.isValid(user1)).isTrue();
        assertThat(validationService.isValid(user2)).isTrue();
    }

    @Test
    public void userInvalidWithNull() {
        User user1 = ModelTestData.getUser1();
        user1.setEmail(null);
        User user2 = ModelTestData.getUser1();
        user2.setName(null);
        User user3 = ModelTestData.getUser1();
        user3.setZoneId(null);
        User user4 = ModelTestData.getUser1();
        user4.setPassword(null);
        User user5 = ModelTestData.getUser1();
        user5.setCreateOn(null);
        User user6 = ModelTestData.getUser1();
        user6.setRole(null);
        User user7 = ModelTestData.getUser1();
        user7.setLocale(null);

        assertThat(validationService.isValid(user1)).isFalse();
        assertThat(validationService.isValid(user2)).isFalse();
        assertThat(validationService.isValid(user3)).isFalse();
        assertThat(validationService.isValid(user4)).isFalse();
        assertThat(validationService.isValid(user5)).isFalse();
        assertThat(validationService.isValid(user6)).isFalse();
        assertThat(validationService.isValid(user7)).isFalse();
    }

    @Test
    public void userInvalidWithNonCorrectFields() {
        User user3 = ModelTestData.getUser1();
        user3.setName("d");
        User user4 = ModelTestData.getUser1();
        user4.setCreateOn(ModelTestData.getNow().plusDays(20));
        User user5 = ModelTestData.getUser1();
        user5.setPassword(" Password2 ");
        User user6 = ModelTestData.getUser1();
        user6.setPassword("password2 ");
        User user7 = ModelTestData.getUser1();
        user7.setPassword("Passwordd ");
        User user8 = ModelTestData.getUser1();
        user8.setPassword("1111111111");
        User user9 = ModelTestData.getUser1();
        user9.setPassword("fffffffffff");
        User user10 = ModelTestData.getUser1();
        user10.setPassword("Paword2");
        User user11 = ModelTestData.getUser1();
        user11.setName("     ");
        User user12 = ModelTestData.getUser1();
        user12.setName(" name ");
        User user13 = ModelTestData.getUser1();
        user13.setName("n");
        User user14 = ModelTestData.getUser1();
        user14.setEmail("null");
        User user15 = ModelTestData.getUser1();
        user15.setEmail("   ");
        User user16 = ModelTestData.getUser1();
        user16.setEmail("  @   ");
        User user17 = ModelTestData.getUser1();
        user17.setEmail(" email2@gmail.com ");

        assertThat(validationService.isValid(user3)).isFalse();
        assertThat(validationService.isValid(user4)).isFalse();
        assertThat(validationService.isValid(user5)).isFalse();
        assertThat(validationService.isValid(user6)).isFalse();
        assertThat(validationService.isValid(user7)).isFalse();
        assertThat(validationService.isValid(user8)).isFalse();
        assertThat(validationService.isValid(user9)).isFalse();
        assertThat(validationService.isValid(user10)).isFalse();
        assertThat(validationService.isValid(user11)).isFalse();
        assertThat(validationService.isValid(user12)).isFalse();
        assertThat(validationService.isValid(user13)).isFalse();
        assertThat(validationService.isValid(user14)).isFalse();
        assertThat(validationService.isValid(user15)).isFalse();
        assertThat(validationService.isValid(user16)).isFalse();
        assertThat(validationService.isValid(user17)).isFalse();
    }
}