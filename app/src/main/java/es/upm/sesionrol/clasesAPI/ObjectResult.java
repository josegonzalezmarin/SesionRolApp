package es.upm.sesionrol.clasesAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectResult {
    @SerializedName("index")
    @Expose
    String index;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("url")
    @Expose
    String url;

    public ObjectResult(String index, String name, String url) {
        this.index = index;
        this.name = name;
        this.url = url;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
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
}
