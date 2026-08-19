package guessthecolor.backend.Domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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

    private Long userId;
    private Long puzzleId;

    private int totalScore;
    private Date completedAt;

    @ManyToOne
    private DailyPuzzle dailyPuzzle;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy="dailyPuzzleAttempt", fetch = FetchType.EAGER)
    private List<DailyAttemptRound> dailyAttemptRounds;
}
