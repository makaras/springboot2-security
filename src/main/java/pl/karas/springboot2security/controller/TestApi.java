package pl.karas.springboot2security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.karas.springboot2security.model.InMemoryAppUserList;

import java.security.Principal;

@RestController
public class TestApi {

    private static final String HELLO_MSG = "Hello %s your login counter: %s";

    private InMemoryAppUserList inMemoryAppUserList;

    @Autowired
    public TestApi(InMemoryAppUserList inMemoryAppUserList) {
        this.inMemoryAppUserList = inMemoryAppUserList;
    }

    @GetMapping("/forAdmin")
    public String forAdmin(Principal principal) {
        String name = principal.getName();
        return String.format(HELLO_MSG, "admin  " + name, getNumberOfSuccessAuth(name));
    }

    @GetMapping("/forUser")
    public String forUser(Principal principal) {
        String name = principal.getName();
        return String.format(HELLO_MSG, "user  " + name, getNumberOfSuccessAuth(name));
    }

    @GetMapping("/forUnknown")
    public String forAll(Principal principal) {
        String name = principal.getName();
        return String.format(HELLO_MSG, "unknown  " + name, getNumberOfSuccessAuth(name));
    }

    @GetMapping("/goodbye")
    public String goodbye() {
        return "goodbye";
    }

    private int getNumberOfSuccessAuth(String name) {
        return inMemoryAppUserList.filterByName(name).getNumberOfSuccessAuth();
    }
}
