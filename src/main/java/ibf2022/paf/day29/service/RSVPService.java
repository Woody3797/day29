package ibf2022.paf.day29.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.paf.day29.model.AggregationRSVP;
import ibf2022.paf.day29.model.RSVP;
import ibf2022.paf.day29.repository.RSVPRepository;

@Service
public class RSVPService {
    
    @Autowired
    private RSVPRepository repository;

    public RSVP insertRSVP(final RSVP rsvp) {
        return repository.insertRSVP(rsvp);
    }

    public List<AggregationRSVP> aggregateByFoodType() {
        return repository.aggregateByFoodType();
    }
}
