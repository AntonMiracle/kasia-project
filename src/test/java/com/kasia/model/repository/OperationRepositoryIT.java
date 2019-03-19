package com.kasia.model.repository;

import com.kasia.ModelTestData;
import com.kasia.model.Operation;
import com.kasia.model.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationRepositoryIT {
    @Autowired
    private OperationRepository repository;
    @Autowired
    private ElementRepository elementRepository;
    @Autowired
    private ProviderRepository providerRepository;
    @Autowired
    private UserRepository userRepository;

    @After
    public void cleanData() {
        repository.findAll().forEach(model -> repository.delete(model));
        elementRepository.findAll().forEach(model -> elementRepository.delete(model));
        providerRepository.findAll().forEach(model -> providerRepository.delete(model));
        userRepository.findAll().forEach(model -> userRepository.delete(model));
    }


    @Test
    public void save() {
        Operation operation = ModelTestData.getOperation1();
        assertThat(operation.getId() == 0).isTrue();

        saveForTest(operation);

        assertThat(operation.getId() > 0).isTrue();
    }

    private Operation saveForTest(Operation operation) {
        elementRepository.save(operation.getElement());
        providerRepository.save(operation.getProvider());
        Optional<User> user = userRepository.findByName(operation.getUser().getName());
        if (user.isPresent()) {
            operation.setUser(user.get());
        } else {
            userRepository.save(operation.getUser());
        }
        repository.save(operation);
        return operation;
    }


    @Test
    public void getById() throws Exception {
        Operation operation = saveForTest(ModelTestData.getOperation1());
        long id = operation.getId();

        operation = repository.findById(id).get();

        assertThat(operation).isNotNull();
        assertThat(operation.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        Operation operation = saveForTest(ModelTestData.getOperation1());

        repository.delete(operation);

        assertThat(repository.findById(operation.getId()).isPresent()).isFalse();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestData.getOperation1());
        saveForTest(ModelTestData.getOperation2());
        Set<Operation> operations = new HashSet<>();

        repository.findAll().forEach(operations::add);

        assertThat(operations.size() == 2).isTrue();
    }

    @Test
    public void findByUserName() {
        Operation operation = saveForTest(ModelTestData.getOperation1());
        User user = operation.getUser();
        Set<Operation> operations = new HashSet<>();

        repository.findByUserName(user.getName()).forEach(operations::add);

        assertThat(operations.size() == 1).isTrue();
    }

    @Test
    public void findByUserId() {
        Operation operation = saveForTest(ModelTestData.getOperation1());
        User user = operation.getUser();
        Set<Operation> operations = new HashSet<>();

        repository.findByUserId(user.getId()).forEach(operations::add);

        assertThat(operations.size() == 1).isTrue();
    }

}