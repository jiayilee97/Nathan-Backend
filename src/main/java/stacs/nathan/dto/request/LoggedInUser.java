package stacs.nathan.dto.request;

import java.util.List;

public class LoggedInUser {

    private String username;

    private String organization;

    private List<String> roles;

    private LoggedInUser() {
    }

    public LoggedInUser(String username, String organization, List<String> roles) {
        this.username = username;
        this.organization = organization;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getOrganization() {
        return organization;
    }

    public List<String> getRoles() {
        return roles;
    }

}
