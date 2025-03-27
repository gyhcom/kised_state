package state.member.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "data_import")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataImport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String filename;
    private String status;
    private String message;
    private LocalDateTime createdAt;
    private String createdBy;

}
