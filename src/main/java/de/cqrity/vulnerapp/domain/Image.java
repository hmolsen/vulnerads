package de.cqrity.vulnerapp.domain;

import javax.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_seq_gen")
    @SequenceGenerator(name = "image_seq_gen", sequenceName = "image_id_seq", initialValue = 1000)
    private long id;

    private String name;

    @Lob
    private byte[] image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
