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

    private static final int SHORT_DESCRIPTION_LENGTH = 250;

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;

    @NotEmpty
    @Size(max = 100)
    private String title;

    @NotEmpty
    @Size(max = 4000)
    private String description;

    private int price;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdtimestamp = new Date();

    private String photofilename;

    @SuppressWarnings("unused")
    ClassifiedAd() { }

    public ClassifiedAd(User owner, String title, String description, int price, Date creationDate) {
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.price = price;
        if (creationDate != null)
            this.createdtimestamp.setTime(creationDate.getTime());
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
        String htmled = description.replaceAll("\n", "<br />");
        this.description = htmled;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getShortDescription() {
        return description.substring(0, Math.min(description.length(), SHORT_DESCRIPTION_LENGTH)) + " ...";
    }

    public Date getCreatedtimestamp() {
        return createdtimestamp;
    }

    public void setCreatedtimestamp(Date createdtimestamp) {
        this.createdtimestamp.setTime(createdtimestamp.getTime());
    }

    public String printCreatedTimestamp() {
        if (DateUtils.isToday(createdtimestamp)) {
            return "Heute, " + new SimpleDateFormat("HH:mm").format(createdtimestamp);
        }
        else if (DateUtils.isYesterday(createdtimestamp)) {
            return "Gestern, " + new SimpleDateFormat("HH:mm").format(createdtimestamp);
        }
        else {
            return new SimpleDateFormat("dd.MM.yyyy").format(createdtimestamp);
        }
    }

    public String getPhotofilename() {
        return photofilename;
    }

    public void setPhotofilename(String photofilename) {
        this.photofilename = photofilename;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("owner", owner)
                .add("title", title)
                .add("description", description)
                .add("price", price)
                .add("createdtimestamp", createdtimestamp)
                .add("photofilename", photofilename)
                .toString();
    }
}
