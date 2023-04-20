package ibf2022.paf.day29.repository;

public class Queries {
    
    public static final String INSERT_RSVP = """
        insert into rsvp(name,email,phone,confirmation_date,
        comments,food_type) values (?,?,?,?,?,?)
    """;
}
