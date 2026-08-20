package guessthecolor.backend.Domain;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "daily_puzzle_id"}))
public class DailyPuzzleAttempt {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private int totalScore;
    private Date completedAt;

    @ManyToOne
    @Column(name="daily_puzzle_id")
    private DailyPuzzle dailyPuzzle;

    @ManyToOne
    @Column(name="user_id")
    private User user;

    @OneToMany(mappedBy="dailyPuzzleAttempt")
    private List<DailyAttemptRound> dailyAttemptRounds;
}
