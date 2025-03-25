package state.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Service
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReleaseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDate createDate;
}
