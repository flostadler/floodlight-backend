package de.flostadler.floodlight.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLocationMapping {

    @DBRef
    private User user;

    private Location location;
}
