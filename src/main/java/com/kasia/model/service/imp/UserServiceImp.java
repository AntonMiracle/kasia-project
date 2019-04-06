package com.kasia.model.service.imp;

import com.kasia.controller.MySessionController;
import com.kasia.controller.dto.Registration;
import com.kasia.model.Role;
import com.kasia.model.User;
import com.kasia.model.repository.UserBudgetRepository;
import com.kasia.model.repository.UserRepository;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Locale;

@Service
@Transactional
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userR;
    @Autowired
    private UserBudgetRepository userBudgetR;
    @Autowired
    private MySessionController sessionC;

    @Override
    public User findByEmail(String email) {
        return userR.findByEmail(email).orElse(null);
    }

    @Override
    public User findById(long userId) {
        return userR.findById(userId).orElse(null);
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
    public User convert(Registration registration) {
        ZoneId zoneId = ZoneId.of(registration.getZoneId());
        Locale locale = new Locale(registration.getLang(), registration.getCountry());

        return new User(registration.getEmail().toLowerCase(), registration.getPassword(), Role.USER
                , LocalDateTime.now(), true, zoneId, locale);
    }

    @Override
    public User save(User user) {
        if (user.getId() == 0) {
            user.setPassword(cryptPassword(user.getPassword()));
        }
        return userR.save(user);
    }


    @Override
    public boolean isEmailUnique(String email) {
        return findByEmail(email) == null;
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
        User user = findByEmail(s.toLowerCase());
        if (user != null) {
            sessionC.setUser(user);
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
