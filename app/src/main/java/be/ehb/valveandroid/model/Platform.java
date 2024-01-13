package be.ehb.valveandroid.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "platform")
public class Platform implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int platformId;
    private String name;
    private String slug;


    public Platform() {
    }

    @Ignore
    public Platform(int platformId, String name, String slug) {
        this.platformId = platformId;
        this.name = name;
        this.slug = slug;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }


    @Override
    public String toString() {
        return "Platform[" +
                "name='" + name + ']';
    }
}
