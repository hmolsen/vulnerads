package de.cqrity.vulnerapp.domain;

import com.google.common.base.MoreObjects;
import de.cqrity.vulnerapp.util.DateUtils;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class ClassifiedAd {

    public static final int SHORT_DESCRIPTION_LENGTH = 250;

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

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdtimestamp;

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

    public String getShortDescription() {
        return description.substring(0, SHORT_DESCRIPTION_LENGTH) + " ...";
    }

    public Date getCreatedtimestamp() {
        return createdtimestamp;
    }

    public void setCreatedtimestamp(Date createdtimestamp) {
        this.createdtimestamp = createdtimestamp;
    }

    public String printCreatedTimestamp() {
        if (DateUtils.isToday(createdtimestamp)) {
            return "Today, " + new SimpleDateFormat("HH:mm").format(createdtimestamp);
        }
        else if (DateUtils.isYesterday(createdtimestamp)) {
            return "Yesterday, " + new SimpleDateFormat("HH:mm").format(createdtimestamp);
        }
        else {
            return new SimpleDateFormat("dd.MM.yyyy").format(createdtimestamp);
        }
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
