package web.model;

import java.time.Instant;
import java.util.Date;

public class SimpleNote {

    public SimpleNote() {
        this.title = "";
        this.description = "";
        this.addedOn = Date.from(Instant.now());
        this.colorCode = "#ffffff";
    }

    private String title;

    private String description;

    private Date addedOn;

    private String colorCode;

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
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

    public Date getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }

}
