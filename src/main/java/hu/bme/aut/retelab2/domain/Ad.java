package hu.bme.aut.retelab2.domain;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Ad {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    private int price;

    @ElementCollection
    private Set<String> tags = new HashSet();

    private String dateCreated;

    private String secretCode;

    //@Basic(optional = true) -> ez igazából implicit
    private LocalDateTime expirationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String text) {
        this.title = text;
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

    public Set<String> getTags() {
        return tags;
    }

    public void addTag (String tag) {
        tags.add(tag);
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated() {
        // date formatting tips provided by https://www.edureka.co/blog/date-format-in-java/

        Calendar cals = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.dateCreated = simpleDateFormat.format(cals.getTime());
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public LocalDateTime getExpirationDate (){
        return expirationDate;
    }

    public void setExpirationDate (LocalDateTime t) {
        expirationDate = t;
    }
}