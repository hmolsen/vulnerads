package de.cqrity.vulnerapp.domain;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private String creditcardnumber;

    @Size(min = 5, max = 5)
    private String zip;

    private String town;

    @OneToOne
    private Authority authority;

    @SuppressWarnings("unused")
    User() {
    }

    public User(String username, String password, Authority authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCreditcardnumber() {
        return creditcardnumber;
    }

    public void setCreditcardnumber(String creditcardnumber) {
        this.creditcardnumber = creditcardnumber;
    }

    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("password", password)
                .add("firstname", firstname)
                .add("lastname", lastname)
                .add("creditcardnumber", creditcardnumber)
                .toString();
    }
}
