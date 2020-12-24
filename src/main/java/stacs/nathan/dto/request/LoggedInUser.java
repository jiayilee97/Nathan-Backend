package stacs.nathan.dto.request;

import java.util.List;

public class LoggedInUser {

    private String username;

    private List<String> organizations;

    private List<String> roles;

    private LoggedInUser() {
    }

    public LoggedInUser(String username, List<String> organizations, List<String> roles) {
        this.username = username;
        this.organizations = organizations;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getOrganizations() {
        return organizations;
    }

    public List<String> getRoles() {
        return roles;
    }

}
