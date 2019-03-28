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
    private PlaceRepository placeRepository;
    @Autowired
    private UserRepository userRepository;

    @After
    public void cleanData() {
        repository.findAll().forEach(model -> repository.delete(model));
        elementRepository.findAll().forEach(model -> elementRepository.delete(model));
        placeRepository.findAll().forEach(model -> placeRepository.delete(model));
        userRepository.findAll().forEach(model -> userRepository.delete(model));
    }


    @Test
    public void save() {
        Operation operation = ModelTestData.operation();
        assertThat(operation.getId() == 0).isTrue();

        saveForTest(operation);

        assertThat(operation.getId() > 0).isTrue();
    }

    private Operation saveForTest(Operation operation) {
        elementRepository.save(operation.getElement());
        placeRepository.save(operation.getPlace());
        Optional<User> user = userRepository.findByEmail(operation.getUser().getEmail());
        if (user.isPresent()) {
            operation.setUser(user.get());
        } else {
            userRepository.save(operation.getUser());
        }
        repository.save(operation);
        return operation;
    }


    @Test
    public void findById() throws Exception {
        Operation operation = saveForTest(ModelTestData.operation());
        long id = operation.getId();

        operation = repository.findById(id).get();

        assertThat(operation).isNotNull();
        assertThat(operation.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        Operation operation = saveForTest(ModelTestData.operation());

        repository.delete(operation);

        assertThat(repository.findById(operation.getId()).isPresent()).isFalse();
    }

    @Test
    public void findAll() throws Exception {
        saveForTest(ModelTestData.operation());
        saveForTest(ModelTestData.operation());
        Set<Operation> operations = new HashSet<>();

        repository.findAll().forEach(operations::add);

        assertThat(operations.size() == 2).isTrue();
    }

    @Test
    public void findByUserId() {
        Operation operation = saveForTest(ModelTestData.operation());
        User user = operation.getUser();
        Set<Operation> operations = new HashSet<>();

        repository.findByUserId(user.getId()).forEach(operations::add);

        assertThat(operations.size() == 1).isTrue();
    }

}