package ma.anas.sisba.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "file")
@Data
@NoArgsConstructor
public class File {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column
    private String name;

    @Column
    private String url;
}
