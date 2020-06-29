package stacs.nathan.entity;

import stacs.nathan.utils.enums.AccreditedStatus;
import stacs.nathan.utils.enums.UserRole;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User extends BaseEntity{
    private static final long serialVersionUID = 1L;

    @Column(name = "uuid", length = 50, nullable = false)
    private String uuid;

    @Column(name = "user_name", length = 50)
    private String username;

    @NotNull
    @NotEmpty(message = "Display name cannot be empty")
    @Column(name = "display_name", length = 50)
    private String displayName;

    @Column(name = "client_id", length = 50)
    private String clientId;

    @Column(name = "nationality", length = 50)
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "accredited_status", length = 10)
    private AccreditedStatus accreditedStatus;

    @Column(name = "risk_tolerance_rating", length = 10)
    private int riskToleranceRating;

    @Email
    @Size(min = 1, max = 100)
    @Column(length = 100)
    private String email;

    //for client, operation, counter party, market
    @Column(name = "private_key", length = 200)
    private String privateKey;

    //for client, operation, counter party, market
    @Column(name = "wallet_address", length = 100)
    private String walletAddress;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private UserRole role;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public AccreditedStatus getAccreditedStatus() {
        return accreditedStatus;
    }

    public void setAccreditedStatus(AccreditedStatus accreditedStatus) {
        this.accreditedStatus = accreditedStatus;
    }

    public int getRiskToleranceRating() {
        return riskToleranceRating;
    }

    public void setRiskToleranceRating(int riskToleranceRating) {
        this.riskToleranceRating = riskToleranceRating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

}
