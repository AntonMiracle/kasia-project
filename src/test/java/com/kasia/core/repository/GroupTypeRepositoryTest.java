package com.kasia.core.repository;

import com.kasia.config.AppConfig;
import com.kasia.core.TestHelper;
import com.kasia.core.model.GroupType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class GroupTypeRepositoryTest implements TestHelper<GroupType> {
    @Autowired
    private GroupTypeRepository repository;
    private GroupType groupType;
    private String name1;
    private String name2;

    @Before
    public void before() {
        assert repository != null;
        groupType = new GroupType();
        name1 = getGroupTypeNameForTesting1();
        name2 = getGroupTypeNameForTesting2();
    }

    @After
    @Transactional
    public void after() {
        if (repository.get(name1) != null) {
            repository.delete(repository.get(name1).getId());
        }
        if (repository.get(name2) != null) {
            repository.delete(repository.get(name2).getId());
        }
    }

    @Test
    @Transactional
    public void save() {
        groupType.setName(name1);
        long id = groupType.getId();

        groupType = repository.saveOrUpdate(groupType);

        assertThat(groupType.getId()).isNotEqualTo(id);
    }

    @Test
    @Transactional
    public void getById() {
        groupType.setName(name1);
        groupType = repository.saveOrUpdate(groupType);

        assertThat(repository.get(groupType.getId())).isEqualTo(groupType);
    }

    @Test
    @Transactional
    public void deleteById() {
        groupType.setName(name1);
        Long id = repository.saveOrUpdate(groupType).getId();

        assertThat(repository.delete(id)).isTrue();
        assertThat(repository.get(id)).isNull();

    }

    @Test
    @Transactional
    public void getAll() {
        int initSize = repository.get().size();
        groupType.setName(name1);
        repository.saveOrUpdate(groupType);

        assertThat(repository.get().size()).isEqualTo(initSize + 1);
    }

    @Test
    @Transactional
    public void getByName() {
        assertThat(repository.get(name1)).isNull();

        groupType.setName(name1);
        repository.saveOrUpdate(groupType);

        assertThat(repository.get(name1)).isNotNull();
    }
}