package ibf2022.paf.day29.model;

import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class AggregationRSVP {
    
    private String _id;
    private List<String> names;
    private Integer count;

    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public List<String> getNames() {
        return names;
    }
    public void setNames(List<String> names) {
        this.names = names;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }


    public static AggregationRSVP create(Document d) {
        AggregationRSVP a = new AggregationRSVP();
        a.set_id(d.getString("_id"));
        a.setNames(d.getList("names", String.class));
        a.setCount(d.getInteger("count"));
        return a;
    }
    
    public JsonObject toJSON() {
        JsonObject jo = Json.createObjectBuilder()
        .add("_id", get_id())
        .add("names", getNames().toString())
        .add("count", getCount())
        .build();
        return jo;
    }
}
