package com.kasia.model.service.imp;

import com.kasia.exception.EmailExistRuntimeException;
import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.exception.UserNameExistRuntimeException;
import com.kasia.model.Budget;
import com.kasia.model.Role;
import com.kasia.model.User;
import com.kasia.model.repository.UserRepository;
import com.kasia.model.service.UserService;
import com.kasia.model.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository uRepository;
    @Autowired
    private UserValidation uValidation;

    @Override
    public User saveUser(User model) {
        uValidation.verifyValidation(model);
        if (model.getId() == 0) {
            if (!isUserNameUnique(model.getName())) throw new UserNameExistRuntimeException();
            if (!isUserEmailUnique(model.getEmail())) throw new EmailExistRuntimeException();
            return uRepository.save(model);
        }
        if (model.getId() > 0) {

            return uRepository.save(model);
        }
        throw new IdInvalidRuntimeException();
    }

    @Override
    public boolean deleteUser(User model) {
        uValidation.verifyValidation(model);
        uValidation.verifyPositiveId(model.getId());
        uRepository.delete(model);
        return true;
    }

    @Override
    public User findUserById(long id) {
        uValidation.verifyPositiveId(id);
        Optional<User> user = uRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public Set<User> findAllUsers() {
        Set<User> users = new HashSet<>();
        uRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User createUser(String email, String name, String password, ZoneId zoneId, Locale locale) {
        User user = new User(email, name, password, zoneId, LocalDateTime.now().withNano(0), Role.USER, false, locale);
        uValidation.verifyValidation(user);
        return user;
    }

    @Override
    public boolean isUserEmailUnique(String email) {
        return !uRepository.findByEmail(email).isPresent();

    }

    @Override
    public boolean isUserNameUnique(String name) {
        return !uRepository.findByName(name).isPresent();
    }

    @Override
    public String cryptPassword(String nonCryptPassword) {
        throw new NotImplementedException();
    }

    @Override
    public ZoneId zoneIdOf(String zoneId) {
        throw new NotImplementedException();
    }

    @Override
    public Locale localeOf(String lang, String country) {
        throw new NotImplementedException();
    }

    @Override
    public User findUserByName(String name) {
        throw new NotImplementedException();
    }

    @Override
    public User findUserByEmail(String email) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isActivated(User user) {
        throw new NotImplementedException();
    }

    @Override
    public boolean activate(User user) {
        throw new NotImplementedException();
    }

    @Override
    public boolean deactivate(User user) {
        throw new NotImplementedException();
    }

    @Override
    public Set<Locale> getCorrectAvailableLocales() {
        throw new NotImplementedException();
    }

    @Override
    public boolean isLocaleAvailable(Locale locale) {
        throw new NotImplementedException();
    }

    @Override
    public Locale getDefaultLocale() {
        throw new NotImplementedException();
    }

    @Override
    public Set<Budget> findAllOwnBudget(User user) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addBudget(User user, Budget budget) {
        throw new NotImplementedException();
    }

    @Override
    public boolean removeBudget(User user, Budget budget) {
        throw new NotImplementedException();
    }

    @Override
    public Set<User> findAllConnectedUser(Budget budget) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isUserOwnerOfBudget(Budget budget, User user) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isUserConnectedToBudget(Budget budget, User user) {
        throw new NotImplementedException();
    }

}
