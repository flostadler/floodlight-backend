package de.flostadler.floodlight.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@Document
public class Game {

    @Id
    private String id;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date date;

    @DBRef
    private Team homeTeam;

    @DBRef
    private Team awayTeam;
}
