package pl.karas.springboot2security.enums;

public enum Role {

    ADMIN("ADMIN"),
    USER("USER");

    private static final String PREFIX = "ROLE_";
    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getNameWithPrefix() {
        return PREFIX + name;
    }
}
