package de.cqrity.vulnerapp.domain;

import com.google.common.base.MoreObjects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "usr")
public class User implements UserDetails, Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_id_seq", initialValue = 1000)
    private long id;

    @Column(unique = true)
    private String username;

    private String password;

    private byte[] encryptedTfaSecret;

    private boolean isTfaEnabled = false;

    private String firstname;

    private String lastname;

    private String creditcardnumber;

    private String phonenumber;

    @Pattern(regexp = "\\d*")
    @Size(min = 0, max = 5)
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(authority);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getEncryptedTfaSecret() {
        return encryptedTfaSecret;
    }

    public void setEncryptedTfaSecret(byte[] encryptedTfaSecret) {
        this.encryptedTfaSecret = encryptedTfaSecret;
    }

    public boolean isTfaEnabled() {
        return isTfaEnabled;
    }

    public void setTfaEnabled(boolean isTfaEnabled) {
        this.isTfaEnabled = isTfaEnabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return username;
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("password", password)
                .add("firstname", firstname)
                .add("lastname", lastname)
                .add("creditcardnumber", creditcardnumber)
                .add("phonenumber", phonenumber)
                .toString();
    }
}
