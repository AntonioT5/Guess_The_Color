package guessthecolor.backend.Domain;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyPuzzleAttempt {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private int totalScore;
    private Date completedAt;

    @ManyToOne
    private DailyPuzzle dailyPuzzle;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy="dailyPuzzleAttempt")
    private List<DailyAttemptRound> dailyAttemptRounds;
}
