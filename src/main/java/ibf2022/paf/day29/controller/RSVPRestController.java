package ibf2022.paf.day29.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2022.paf.day29.model.AggregationRSVP;
import ibf2022.paf.day29.model.RSVP;
import ibf2022.paf.day29.service.RSVPService;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path = "/api/rsvp", produces = MediaType.APPLICATION_JSON_VALUE)
public class RSVPRestController {
    
    @Autowired
    private RSVPService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveRSVP(@RequestBody String json) {
        try {
            JsonObject response = null;
            RSVP rsvp = RSVP.create(json);
            RSVP result = service.insertRSVP(rsvp);
            response = Json.createObjectBuilder()
            .add("rsvpId", result.getId())
            .build();
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response.toString());
        } catch (Exception e) {
            JsonObject errResp = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return ResponseEntity.badRequest().body(errResp.toString());
        }
    }

    @GetMapping(path = "/count")
    public ResponseEntity<String> aggregateByFoodType() {
        List<AggregationRSVP> ll = service.aggregateByFoodType();
        JsonArrayBuilder arrb = Json.createArrayBuilder();

        for (AggregationRSVP a : ll) {
            arrb.add(a.toJSON());
        }
        JsonArray arr = arrb.build();

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(arr.toString());
    }
}
