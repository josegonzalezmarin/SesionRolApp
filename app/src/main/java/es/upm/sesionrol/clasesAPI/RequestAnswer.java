package es.upm.sesionrol.clasesAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestAnswer {
    @SerializedName("count")
    @Expose
    int count;
    @SerializedName("results")
    @Expose
    List<ObjectResult> results;

    public RequestAnswer(int count, List<ObjectResult> results) {
        this.count = count;
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ObjectResult> getResults() {
        return results;
    }

    public void setResults(List<ObjectResult> results) {
        this.results = results;
    }
}
