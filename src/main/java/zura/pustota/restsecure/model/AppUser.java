package zura.pustota.restsecure.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Table
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    @Enumerated(value = EnumType.STRING)
    private Role role;

}
