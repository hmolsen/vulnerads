package de.cqrity.vulnerapp.domain;

import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class ClassifiedAd {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;

    @Enumerated(EnumType.STRING)
    Category category;

    @NotEmpty
    @Size(max = 100)
    String title;

    @NotEmpty
    @Size(max = 4000)
    String description;

    int price;

    @SuppressWarnings("unused")
    ClassifiedAd() { }

    public ClassifiedAd(User owner, Category category, String title, String description, int price) {
        this.owner = owner;
        this.category = category;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("owner", owner)
                .add("category", category)
                .add("title", title)
                .add("description", description)
                .add("price", price)
                .toString();
    }
}
