package ibf2022.paf.day29.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ibf2022.paf.day29.model.AggregationRSVP;
import ibf2022.paf.day29.model.RSVP;

@Repository
public class RSVPRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;


    public RSVP insertRSVP(final RSVP rsvp) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(Queries.INSERT_RSVP, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, rsvp.getName());
            ps.setString(2, rsvp.getEmail());
            ps.setString(3, rsvp.getPhone());
            ps.setTimestamp(4, new Timestamp(rsvp.getConfirmationDate().toDateTime().getMillis()));
            ps.setString(5, rsvp.getComment());
            ps.setString(6, rsvp.getFoodType());
            return ps;
        }, keyHolder);
        Number primaryKeyValue = keyHolder.getKey();
        if (primaryKeyValue != null) {
            rsvp.setId(primaryKeyValue.intValue());
        }
        if (rsvp.getId() > 0) {
            rsvp.setConfirmationDate(null);
            mongoTemplate.insert(rsvp, "rsvp");
        }
        return rsvp;
    }

    public List<AggregationRSVP> aggregateByFoodType() {
        ProjectionOperation project = Aggregation.project("name", "foodType");
        GroupOperation group = Aggregation.group("foodType")
        .push("name")
        .as("names")
        .count().as("count");
        SortOperation sort = Aggregation.sort(Sort.by(Direction.DESC, "count"));

        Aggregation pipeline = Aggregation.newAggregation(project, group, sort);
        AggregationResults<Document> result = mongoTemplate.aggregate(pipeline, "rsvp", Document.class);

        List<AggregationRSVP> listAgg = new LinkedList<>();
        Iterator<Document> id = result.iterator();
        while (id.hasNext()) {
            Document doc = id.next();
            listAgg.add(AggregationRSVP.create(doc));
        }
        return listAgg;
    }


}
