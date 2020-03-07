package de.cqrity.vulnerapp.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.google.common.base.MoreObjects;

import de.cqrity.vulnerapp.util.DateUtils;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class ClassifiedAd {

    private static final int SHORT_DESCRIPTION_LENGTH = 250;

    @XmlTransient
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "classified_ad_seq_gen")
    @SequenceGenerator(name = "classified_ad_seq_gen", sequenceName = "classified_ad_id_seq", initialValue = 1000)
    private long id;

    @XmlTransient
    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;

    @XmlElement(name = "title")
    @NotEmpty
    @Size(max = 100)
    private String title;

    @XmlElement(name = "description")
    @NotEmpty
    @Size(max = 4000)
    private String description;

    @XmlElement(name = "price")
    private int price;

    @XmlTransient
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdtimestamp = new Date();

    @XmlTransient
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
        this.description = description.replaceAll("\n", "<br />");
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getShortDescription() {
        if (description == null) {
            return description;
        }
        return description.substring(0, Math.min(description.length(), SHORT_DESCRIPTION_LENGTH)) + " ...";
    }

    public Date getCreatedtimestamp() {
        return createdtimestamp;
    }

    public void setCreatedtimestamp(Date createdtimestamp) {
        this.createdtimestamp.setTime(createdtimestamp.getTime());
    }

    public String printCreatedTimestamp() {
        if (DateUtils.isToday(createdtimestamp) || DateUtils.isYesterday(createdtimestamp)) {
            return new SimpleDateFormat("HH:mm").format(createdtimestamp);
        } else {
            return new SimpleDateFormat("dd.MM.yyyy").format(createdtimestamp);
        }
    }

    public boolean isFromToday() {
        return DateUtils.isToday(createdtimestamp);
    }

    public boolean isFromYesterday() {
        return DateUtils.isYesterday(createdtimestamp);
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
