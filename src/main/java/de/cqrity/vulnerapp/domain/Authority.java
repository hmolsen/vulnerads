package de.cqrity.vulnerapp.domain;

import com.google.common.base.MoreObjects;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue
    private long id;

    private String authority;

    Authority() {
    }

    public Authority(String authority) {
        this.authority = authority;
    }

    public long getId() {
        return id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("authority", authority)
                .toString();
    }
}
