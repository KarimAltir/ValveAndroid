package be.ehb.valveandroid.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "game")
public class Game implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int gameId;
    private String name;
    private String slug;
    private String released;
    private String rating;
    private String background_image;
    //private String platforms;


    public Game() {
    }

    @Ignore
    public Game(int gameId, String name, String slug, String released, String rating, String background_image /*, String platforms */) {
        this.gameId = gameId;
        this.name = name;
        this.slug = slug;
        this.released = released;
        this.rating = rating;
        this.background_image = background_image;
        //this.platforms = platforms;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
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

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getBackground_image() {
        return background_image;
    }

    public void setBackground_image(String background_image) {
        this.background_image = background_image;
    }

    /*public String getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
    }*/
}
