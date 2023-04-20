package ibf2022.paf.day29.model;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class RSVP implements Serializable {
    
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private DateTime confirmationDate;
    private String comment;
    private Integer totalCount;
    private String foodType;


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public DateTime getConfirmationDate() {
        return confirmationDate;
    }
    public void setConfirmationDate(DateTime confirmationDate) {
        this.confirmationDate = confirmationDate;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Integer getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
    public String getFoodType() {
        return foodType;
    }
    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public static RSVP create(SqlRowSet rs) {
        RSVP rsvp = new RSVP();
        rsvp.setId(rs.getInt("id"));
        rsvp.setName(rs.getString("name"));
        rsvp.setEmail(rs.getString("email"));
        rsvp.setPhone(rs.getString("phone"));
        DateTime conDate = new DateTime(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(rs.getString("confirmation_date")));
        rsvp.setConfirmationDate(conDate);
        rsvp.setComment(rs.getString("comment"));
        rsvp.setFoodType(rs.getString("food_type"));
        return rsvp;
    }

    public static RSVP create(String json) throws Exception {
        JsonReader jr = Json.createReader(new ByteArrayInputStream(json.getBytes()));
        return create(jr.readObject());
    }
    
    private static RSVP create(JsonObject readJObject) {
        final RSVP rsvp = new RSVP();
        rsvp.setName(readJObject.getString("name"));
        rsvp.setEmail(readJObject.getString("email"));
        rsvp.setPhone(readJObject.getString("phone"));
        rsvp.setConfirmationDate(new DateTime(Instant.parse(readJObject.getString("confirmation_date"))));
        rsvp.setComment(readJObject.getString("comments"));
        rsvp.setFoodType(readJObject.getString("food_type"));
        return rsvp;
    }

}
