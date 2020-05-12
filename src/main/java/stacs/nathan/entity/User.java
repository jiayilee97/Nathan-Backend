package stacs.nathan.entity;

import org.hibernate.annotations.CreationTimestamp;
import stacs.nathan.Utils.enums.UserRole;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(name = "user_name", length = 50)
    private String userName;

    @Column(name = "wallet_address", length = 50)
    private String walletAddress;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private UserRole role;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated_date")
    private Date lastUpdatedDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(walletAddress, user.walletAddress) &&
                role == user.role &&
                Objects.equals(createdDate, user.createdDate) &&
                Objects.equals(lastUpdatedDate, user.lastUpdatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, walletAddress, role, createdDate, lastUpdatedDate);
    }
}
