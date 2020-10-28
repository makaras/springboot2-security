package pl.karas.springboot2security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import pl.karas.springboot2security.model.InMemoryAppUserList;

@Service
public class AuthenticationSuccessHandler {

    private InMemoryAppUserList inMemoryAppUserList;

    @Autowired
    public AuthenticationSuccessHandler(InMemoryAppUserList inMemoryAppUserList) {
        this.inMemoryAppUserList = inMemoryAppUserList;
    }

    @EventListener
    public void onApplicationSuccessEvent(AuthenticationSuccessEvent event) {
        inMemoryAppUserList.filterByName(((User) event.getAuthentication().getPrincipal()).getUsername())
                .incrementNumberOfSuccessAuths();
    }

}
