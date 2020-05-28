package stacs.nathan.entity;

import stacs.nathan.utils.enums.AccreditedStatus;
import stacs.nathan.utils.enums.UserRole;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "user")
public class User extends BaseEntity{
    private static final long serialVersionUID = 1L;

    @Column(name = "uuid", length = 50, nullable = false)
    private String uuid;

    @NotNull
    @NotEmpty(message = "Username cannot be empty")
    @Column(name = "user_name", length = 50)
    private String userName;

    @NotNull
    @NotEmpty(message = "Display name cannot be empty")
    @Column(name = "display_name", length = 50)
    private String displayName;

    @Email
    @NotEmpty(message = "Email cannot be empty")
    @Size(min = 1, max = 100)
    @Column(length = 100, nullable = false)
    private String email;

    //for client only
    @Column(name = "client_id", length = 50)
    private String clientId;

    //for client only
    @Column(name = "nationality", length = 50)
    private String nationality;

    //for client only
    @Column(name = "accredited_status", length = 20)
    private AccreditedStatus accreditedStatus;

    //for client only
    @Column(name = "risk_tolerance_rating")
    private long riskToleranceRating;

    //for client, operation, counter party, market
    @Column(name = "private_key", length = 200)
    private String privateKey;

    //for client, operation, counter party, market
    @Column(name = "wallet_address", length = 50)
    private String walletAddress;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Balance> balances;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BaseCurrencyToken> baseCurrencyTokens;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SPToken> spTokens;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public long getRiskToleranceRating() {
        return riskToleranceRating;
    }

    public void setRiskToleranceRating(long riskToleranceRating) {
        this.riskToleranceRating = riskToleranceRating;
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

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }

    public List<BaseCurrencyToken> getBaseCurrencyTokens() {
        return baseCurrencyTokens;
    }

    public void setBaseCurrencyTokens(List<BaseCurrencyToken> baseCurrencyTokens) {
        this.baseCurrencyTokens = baseCurrencyTokens;
    }

    public List<SPToken> getSpTokens() {
        return spTokens;
    }

    public void setSpTokens(List<SPToken> spTokens) {
        this.spTokens = spTokens;
    }
}
