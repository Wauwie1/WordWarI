package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @Getter @Setter private int id;
    @Getter @Setter private String username;
    @Setter @Getter private String password;
}
