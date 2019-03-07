package com.kasia.model.service.imp;

import com.kasia.controller.MySessionController;
import com.kasia.exception.EmailExistRuntimeException;
import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.exception.LocaleFormatRuntimeException;
import com.kasia.exception.UserNameExistRuntimeException;
import com.kasia.model.*;
import com.kasia.model.repository.UserBudgetRepository;
import com.kasia.model.repository.UserConnectBudgetRepository;
import com.kasia.model.repository.UserRepository;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@Transactional
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository uRepository;
    @Autowired
    private UserBudgetRepository ubRepository;
    @Autowired
    private UserConnectBudgetRepository ucbRepository;
    @Autowired
    private BudgetService bService;
    @Autowired
    private MySessionController sessionController;

    @Override
    public User saveUser(User model) {
        if (model.getId() == 0) {
            if (!isUserNameUnique(model.getName())) throw new UserNameExistRuntimeException();
            if (!isUserEmailUnique(model.getEmail())) throw new EmailExistRuntimeException();
            activate(model);
            model.setPassword(cryptPassword(model.getPassword()));
            return uRepository.save(model);
        }
        if (model.getId() > 0) {
            User oldUser = findUserById(model.getId());
            if (!oldUser.getName().equals(model.getName()) && !isUserNameUnique(model.getName()))
                model.setName(oldUser.getName());
            if (!oldUser.getEmail().equals(model.getEmail()) && !isUserEmailUnique(model.getEmail()))
                model.setEmail(oldUser.getEmail());
            return uRepository.save(model);
        }
        throw new IdInvalidRuntimeException();
    }

    @Override
    public boolean deleteUser(long id) {
        User model = findUserById(id);
        if (model == null) return false;
        uRepository.delete(model);
        return true;
    }

    @Override
    public User findUserById(long id) {
        if (id <= 0) return null;
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
    public User createUser(String email, String name, String password, String zoneIdString, String localeLang, String localeCountry) {
        Locale locale = localeOf(localeLang, localeCountry);
        ZoneId zoneId = zoneIdOf(zoneIdString);
        User user = new User(email, name, password, zoneId, LocalDateTime.now().withNano(0), Role.USER, false, locale);
        return user;
    }

    @Override
    public boolean isUserEmailUnique(String email) {
        return email != null && findUserByEmail(email) == null;
    }

    @Override
    public boolean isUserNameUnique(String name) {
        return name != null && findUserByName(name) == null;
    }

    @Override
    public String cryptPassword(String nonCryptPassword) {
        if (nonCryptPassword == null) return "";

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(nonCryptPassword.getBytes(), 0, nonCryptPassword.length());
        return new BigInteger(1, md5.digest()).toString(16) + "0Aa";
    }

    @Override
    public ZoneId zoneIdOf(String zoneId) {
        if (zoneId == null) return ZoneId.systemDefault();
        try {
            return ZoneId.of(zoneId);
        } catch (DateTimeException ex) {
            return ZoneId.systemDefault();
        }
    }

    @Override
    public Locale localeOf(String lang, String country) {
        if (lang == null || country == null) return getDefaultLocale();

        Locale locale = new Locale(lang, country);
        if (getCorrectAvailableLocales().contains(locale)) return locale;
        locale = getDefaultLocale();
        if (getCorrectAvailableLocales().contains(locale)) return locale;
        throw new LocaleFormatRuntimeException();
    }

    @Override
    public User findUserByName(String name) {
        return uRepository.findByName(name).orElse(null);
    }

    @Override
    public User findUserByEmail(String email) {
        return uRepository.findByEmail(email).orElse(null);
    }

    @Override
    public boolean isActivated(User user) {
        return user != null && user.isActivated();
    }

    @Override
    public boolean activate(User user) {
        if (user == null) return false;
        user.setActivated(true);
        return true;
    }

    @Override
    public boolean deactivate(User user) {
        if (user == null) return false;
        user.setActivated(false);
        return true;
    }

    @Override
    public Set<Locale> getCorrectAvailableLocales() {
        Set<Locale> locales = new HashSet<>();
        for (Locale locale : Locale.getAvailableLocales()) {
            if (locale.getLanguage().length() > 0 && locale.getCountry().length() > 0)
                locales.add(locale);
        }
        return locales;
    }

    @Override
    public boolean isLocaleAvailable(Locale locale) {
        return getCorrectAvailableLocales().contains(locale);
    }

    @Override
    public Locale getDefaultLocale() {
        return new Locale("pl", "PL");
    }

    @Override
    public Set<Budget> findOwnBudgets(long userId) {
        Optional<UserBudget> optional = ubRepository.findByUserId(userId);
        return optional.map(UserBudget::getBudgets).orElseGet(HashSet::new);
    }

    @Override
    public User findOwner(long budgetId) {
        Budget budget = bService.findBudgetById(budgetId);

        Set<UserBudget> ub = new HashSet<>();
        ubRepository.findAll().forEach(ub::add);

        for (UserBudget userBudget : ub) {
            if (userBudget.getBudgets().contains(budget)) {
                return userBudget.getUser();
            }
        }
        return null;
    }

    @Override
    public boolean addBudget(long userId, long budgetId) {
        User user = findUserById(userId);
        Budget budget = bService.findBudgetById(budgetId);

        if (user == null || budget == null) return false;

        User owner = findOwner(budget.getId());

        if (owner == null) {
            UserBudget ub = ubRepository.findByUserId(user.getId()).orElse(new UserBudget());
            ub.setUser(user);
            if (ub.getBudgets() == null) ub.setBudgets(new HashSet<>());
            ub.getBudgets().add(budget);
            ubRepository.save(ub);
            return true;
        }
        if (owner.equals(user)) return false;

        Optional<UserConnectBudget> optionalUCB = ucbRepository.findByUserId(user.getId());
        if (optionalUCB.isPresent()) {
            UserConnectBudget ucb = optionalUCB.get();
            if (ucb.getConnectBudgets().contains(budget)) return false;
            ucb.getConnectBudgets().add(budget);
            ucbRepository.save(ucb);
            return true;
        } else {
            UserConnectBudget newUCB = new UserConnectBudget(user, new HashSet<>());
            newUCB.getConnectBudgets().add(budget);
            ucbRepository.save(newUCB);
            return true;
        }
    }

    @Override
    public boolean removeBudget(long userId, long budgetId) {
        User user = findUserById(userId);
        Budget budget = bService.findBudgetById(budgetId);

        if (user == null || budget == null) return false;

        User owner = findOwner(budget.getId());

        if (owner != null && owner.equals(user)) {
            Optional<UserBudget> optionalUB = ubRepository.findByUserId(user.getId());
            if (optionalUB.isPresent() && optionalUB.get().getBudgets().contains(budget)) {
                UserBudget ub = optionalUB.get();
                ub.getBudgets().remove(budget);
                bService.deleteBudget(budget.getId());
                ubRepository.save(ub);
                return true;
            }
            return false;
        }

        Optional<UserConnectBudget> optionalUCB = ucbRepository.findByUserId(user.getId());
        if (optionalUCB.isPresent() && optionalUCB.get().getConnectBudgets().contains(budget)) {
            UserConnectBudget ucb = optionalUCB.get();
            ucb.getConnectBudgets().remove(budget);
            ucbRepository.save(ucb);
            return true;
        }
        return false;
    }

    @Override
    public Set<User> findConnectUsers(long budgetId) {
        Budget budget = bService.findBudgetById(budgetId);

        Set<UserConnectBudget> ucb = new HashSet<>();
        ucbRepository.findAll().forEach(ucb::add);

        Set<User> users = new HashSet<>();
        for (UserConnectBudget userConnectBudget : ucb) {
            if (userConnectBudget.getConnectBudgets().contains(budget)) users.add(userConnectBudget.getUser());
        }

        return users;
    }

    @Override
    public boolean isUserOwnBudget(long budgetId, long userId) {
        Budget budget = bService.findBudgetById(budgetId);
        if (budget == null || userId <= 0) return false;

        UserBudget ub = ubRepository.findByUserId(userId).orElse(null);
        return !(ub == null || ub.getBudgets() == null) && ub.getBudgets().contains(budget);
    }

    @Override
    public boolean isUserConnectToBudget(long budgetId, long userId) {
        Budget budget = bService.findBudgetById(budgetId);
        if (budget == null || userId <= 0) return false;

        UserConnectBudget ucb = ucbRepository.findByUserId(userId).orElse(null);
        return !(ucb == null || ucb.getConnectBudgets() == null) && ucb.getConnectBudgets().contains(budget);
    }

    /*====================================
    Configuration for spring sec
     ====================================*/

    private UserDetails convertMyUserToSpringUserDetails(User myUser) {

        String necessaryToAddForSpringSecurity = "ROLE_"; /*to correct representation roles in spring sec*/
        SimpleGrantedAuthority sga = new SimpleGrantedAuthority(necessaryToAddForSpringSecurity + myUser.getRole().toString());

        /*create UserDetails from spring User which implement UserDetails*/
        org.springframework.security.core.userdetails.User springUserAndUserDetails =
                new org.springframework.security.core.userdetails.User(
                        myUser.getEmail(), myUser.getPassword()
                        , myUser.isActivated(), true, true, true
                        , Arrays.asList(sga));

        return springUserAndUserDetails;
    }

    @Override/*method to work with Spring security from UserDetailsService*/
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = findUserByEmail(s);
        if (user != null) {
            sessionController.setUser(user);
            return convertMyUserToSpringUserDetails(user);
        }
        throw new UsernameNotFoundException("need login");
    }

    @Override/*to customized encoding from PasswordEncoder*/
    public String encode(CharSequence charSequence) {
        return cryptPassword(charSequence.toString());
    }

    @Override/*to compare crypt pass with non crypt from PasswordEncoder*/
    public boolean matches(CharSequence charSequence, String expected) {
        String actual = cryptPassword(charSequence.toString());
        return actual.equals(expected);
    }

    /*====================================
    END
     ====================================*/
}
