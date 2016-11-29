package rubenshardt.ckl_challenge.retrofitAPI;

import java.util.List;

/**
 * Created by rubenshardtjunior on 11/29/16.
 */

public class APIArticle {

    private String title;
    private String website;
    private String authors;
    private String date;
    private String content;
    private List<APITag> tags;
    private String image;
    private boolean read;

    public APIArticle(String title, String website, String authors, String date, String content, List<APITag> tags, String image, boolean read) {

        this.title = title;
        this.website = website;
        this.authors = authors;
        this.date = date;
        this.content = content;
        this.tags = tags;
        this.image = image;
        this.read = read;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public List<APITag> getTags() {
        return tags;
    }

    public void setTags(List<APITag> tags) {
        this.tags = tags;
    }

    public boolean getRead() {
        return read;
    }
    public void setRead(boolean read) {
        this.read = read;
    }

}