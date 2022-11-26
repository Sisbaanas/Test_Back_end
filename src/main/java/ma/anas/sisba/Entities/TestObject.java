package ma.anas.sisba.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestObject {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column
    private String name;

    @Column
    private Double price;

    @Column
    private String country;

    @Column
    private String city;

    @Column
    private Boolean deleted;

}
