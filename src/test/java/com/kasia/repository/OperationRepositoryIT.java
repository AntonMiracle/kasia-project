package com.kasia.repository;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Article;
import com.kasia.model.Employer;
import com.kasia.model.Operation;
import com.kasia.model.User;
import org.junit.After;
import org.junit.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OperationRepositoryIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private OperationRepository operationRepository;
    @Inject
    private ArticleRepository articleRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private EmployerRepository employerRepository;
    private final LocalDateTime CREATE_ON = LocalDateTime.now().withNano(0);
    private final BigDecimal AMOUNT = BigDecimal.TEN;
    private final String NAME = "name1";
    private final String NAME_2 = "name2";

    @After
    public void after() {
        for (Operation o : operationRepository.getAll()) {
            operationRepository.delete(o);
        }
        for (Article o : articleRepository.getAll()) {
            articleRepository.delete(o);
        }
        for (Employer o : employerRepository.getAll()) {
            employerRepository.delete(o);
        }
        for (User o : userRepository.getAll()) {
            userRepository.delete(o);
        }
    }

    @Test
    public void getById() throws Exception {
        Operation operation = new Operation(AMOUNT, newArticle(NAME), newUser(NAME), newEmployer(NAME), CREATE_ON);
        long id = operationRepository.save(operation).getId();
        operation = operationRepository.getById(id);

        assertThat(operation.getId() == id).isTrue();
    }

    @Test
    public void getAll() throws Exception {
        assertThat(operationRepository.getAll().size() == 0).isTrue();

        operationRepository.save(new Operation(AMOUNT, newArticle(NAME), newUser(NAME), newEmployer(NAME), CREATE_ON));
        operationRepository.save(new Operation(AMOUNT, newArticle(NAME_2), newUser(NAME_2), newEmployer(NAME_2), CREATE_ON));

        assertThat(operationRepository.getAll().size() == 2).isTrue();
    }

    @Test
    public void delete() throws Exception {
        Operation operation = new Operation(AMOUNT, newArticle(NAME), newUser(NAME), newEmployer(NAME), CREATE_ON);
        long id = operationRepository.save(operation).getId();
        operation = operationRepository.getById(id);

        assertThat(operationRepository.getById(id)).isNotNull();
        assertThat(operationRepository.delete(operation)).isTrue();
        assertThat(operationRepository.getById(id)).isNull();
    }

    @Test
    public void save() throws Exception {
        Operation operation = new Operation(AMOUNT, newArticle(NAME), newUser(NAME), newEmployer(NAME), CREATE_ON);
        assertThat(operation.getId() == 0).isTrue();

        assertThat(operationRepository.save(operation).getId() > 0).isTrue();
    }

    @Test
    public void update() throws Exception {
        Operation operation = new Operation(AMOUNT, newArticle(NAME), newUser(NAME), newEmployer(NAME), CREATE_ON);
        long id = operationRepository.save(operation).getId();
        operation = operationRepository.getById(id);

        assertThat(operation.getEmployer().getName()).isEqualTo(NAME);
        operation.setEmployer(newEmployer(NAME_2));
        operationRepository.save(operation);

        operation = operationRepository.getById(id);
        assertThat(operation.getEmployer().getName()).isEqualTo(NAME_2);
    }

    @Test
    public void getByUserId() throws Exception {
        Operation operation = new Operation(AMOUNT, newArticle(NAME), newUser(NAME), newEmployer(NAME), CREATE_ON);
        operation = operationRepository.getById(operationRepository.save(operation).getId());

        long id = operation.getUser().getId();
        assertThat(operationRepository.getByUserId(id).size() == 1).isTrue();
    }

    @Test
    public void getByEmployerId() throws Exception {
        Operation operation = new Operation(AMOUNT, newArticle(NAME), newUser(NAME), newEmployer(NAME), CREATE_ON);
        operation = operationRepository.getById(operationRepository.save(operation).getId());

        long id = operation.getEmployer().getId();
        assertThat(operationRepository.getByEmployerId(id).size() == 1).isTrue();
    }

    @Test
    public void getByArticleId() throws Exception {
        Operation operation = new Operation(AMOUNT, newArticle(NAME), newUser(NAME), newEmployer(NAME), CREATE_ON);
        operation = operationRepository.getById(operationRepository.save(operation).getId());

        long id = operation.getArticle().getId();
        assertThat(operationRepository.getByArticleId(id).size() == 1).isTrue();
    }

    private User newUser(String nick) {
        User user = createUser("email@gmail.com", nick);
        long id = userRepository.save(user).getId();
        return userRepository.getById(id);
    }

    private Employer newEmployer(String name) {
        Employer employer = new Employer(name);
        employer.setDescription("EmployerDescription");
        long id = employerRepository.save(employer).getId();
        return employerRepository.getById(id);
    }

    private Article newArticle(String name) {
        Article article = new Article(name, Article.Type.INCOME);
        article.setDescription("ArticleDescription");
        long id = articleRepository.save(article).getId();
        return articleRepository.getById(id);
    }
}