package pl.karas.springboot2security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import pl.karas.springboot2security.model.AppUser;
import pl.karas.springboot2security.model.InMemoryAppUserList;

import static pl.karas.springboot2security.enums.Role.ADMIN;
import static pl.karas.springboot2security.enums.Role.USER;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private InMemoryAppUserList inMemoryAppUserList;

    @Autowired
    public WebSecurityConfig(InMemoryAppUserList inMemoryAppUserList) {
        this.inMemoryAppUserList = inMemoryAppUserList;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        inMemoryAppUserList.getAppUserList().forEach(user -> {
            try {
                auth.inMemoryAuthentication().withUser(user);
            } catch (Exception e) {
                throw new IllegalStateException(e.getMessage());
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/forAdmin").hasRole(ADMIN.getName())
                .antMatchers("/forUser").hasAnyRole(ADMIN.getName(), USER.getName())
                .antMatchers("/forUnknown").permitAll()
                .antMatchers("/goodbye").permitAll()
                .and().formLogin().permitAll()
                .and().logout().logoutSuccessUrl("/goodbye");
    }
}
