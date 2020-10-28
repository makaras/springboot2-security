package pl.karas.springboot2security.model;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static pl.karas.springboot2security.enums.Role.ADMIN;
import static pl.karas.springboot2security.enums.Role.USER;

@Component
public class InMemoryAppUserList {

    private List<AppUser> appUserList;

    public InMemoryAppUserList() {
        this.appUserList = generateInMemoryUserList();
    }

    private List<AppUser> generateInMemoryUserList() {
        AppUser roleAdmin = new AppUser("Maciek",
                getPasswordEncoder().encode("Maciek123"),
                Collections.singleton(new SimpleGrantedAuthority(ADMIN.getNameWithPrefix())));
        AppUser roleUser = new AppUser("Patryk",
                getPasswordEncoder().encode("Patryk123"),
                Collections.singleton(new SimpleGrantedAuthority(USER.getNameWithPrefix())));
        AppUser roleUnknown = new AppUser("Jan",
                getPasswordEncoder().encode("Jan123"),
                Collections.emptyList());
        List<AppUser> appUserList = new ArrayList<>();
        Collections.addAll(appUserList, roleAdmin, roleUser, roleUnknown);
        return appUserList;
    }

    public List<AppUser> getAppUserList() {
        return appUserList;
    }

    public AppUser filterByName(String name) {
        return appUserList.stream()
                .filter(appUser -> appUser.getUsername().equals(name))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
