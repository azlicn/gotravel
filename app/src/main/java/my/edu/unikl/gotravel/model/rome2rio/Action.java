package my.edu.unikl.gotravel.model.rome2rio;

import java.io.Serializable;

public class Action implements Serializable {

    private String text;
    private String url;
    private String displayUrl;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    @Override
    public String toString() {
        return "Action{" +
                "text='" + text + '\'' +
                ", url='" + url + '\'' +
                ", displayUrl='" + displayUrl + '\'' +
                '}';
    }
}
