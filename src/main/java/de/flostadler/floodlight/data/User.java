package de.flostadler.floodlight.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document

public class User {

    @Id
    private String username;

    private String name;

    @JsonIgnore
    private byte[] profilePicture;
}
