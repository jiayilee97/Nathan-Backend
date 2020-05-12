package stacs.nathan.dto;

public class LoggedInUser {

    private String username;

    private LoggedInUser() {
    }

    public LoggedInUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
