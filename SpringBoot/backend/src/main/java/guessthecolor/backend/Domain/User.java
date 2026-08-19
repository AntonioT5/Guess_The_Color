package guessthecolor.backend.Domain;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="game_user")
public class User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String mail;
    private Date createdAt;

    @OneToMany(mappedBy="user", fetch = FetchType.EAGER)
    private List<Practice> practices;

    @OneToMany(mappedBy="user", fetch = FetchType.EAGER)
    private List<DailyPuzzleAttempt> dailyPuzzleAttempts;

    @OneToOne(mappedBy="user", fetch = FetchType.EAGER)
    private Stats stats;
}