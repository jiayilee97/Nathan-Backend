package stacs.nathan.dto;

public class DecodedJwtDTO {

    private String username;
    private String role;

    private DecodedJwtDTO() {
    }

    public DecodedJwtDTO(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
