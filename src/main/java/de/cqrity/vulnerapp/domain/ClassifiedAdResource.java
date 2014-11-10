package de.cqrity.vulnerapp.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;

public class ClassifiedAdResource {

    private long id;

    @NotEmpty
    @Size(max = 100)
    private String title;

    @NotEmpty
    @Size(max = 4000)
    private String description;

    @Range(min = 0)
    private int price;

    private MultipartFile adphoto;

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
        String stringed = null;
        if (description != null) {
             stringed = description.replaceAll("<br />", "\n");
        }
        this.description = stringed;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public MultipartFile getAdphoto() {
        return adphoto;
    }

    public void setAdphoto(MultipartFile adphoto) {
        this.adphoto = adphoto;
    }
}
