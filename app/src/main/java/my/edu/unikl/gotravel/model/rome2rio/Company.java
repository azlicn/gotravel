package my.edu.unikl.gotravel.model.rome2rio;

import java.io.Serializable;

public class Company implements Serializable {

    private String code;
    private String name;
    private String url;
    private String iconPath;
    private String iconSize;
    private String iconOffset;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIconSize() {
        return iconSize;
    }

    public void setIconSize(String iconSize) {
        this.iconSize = iconSize;
    }

    public String getIconOffset() {
        return iconOffset;
    }

    public void setIconOffset(String iconOffset) {
        this.iconOffset = iconOffset;
    }


    @Override
    public String toString() {
        return "Company{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", iconPath='" + iconPath + '\'' +
                ", iconSize='" + iconSize + '\'' +
                ", iconOffset='" + iconOffset + '\'' +
                '}';
    }
}
