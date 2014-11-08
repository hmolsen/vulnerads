package de.cqrity.vulnerapp.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;

public class ClassifiedAdResource {

    private long id;

    @NotEmpty
    @Size(max = 100)
    String title;

    @NotEmpty
    @Size(max = 4000)
    String description;

    @Range(min = 0)
    int price;

    public ClassifiedAdResource() { }

    public ClassifiedAdResource(ClassifiedAd ad) {
        this.id = ad.getId();
        this.title = ad.getTitle();
        setDescription(ad.getDescription());
        this.price = ad.getPrice();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        String stringed = description.replaceAll("<br />", "\n");
        this.description = stringed;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
