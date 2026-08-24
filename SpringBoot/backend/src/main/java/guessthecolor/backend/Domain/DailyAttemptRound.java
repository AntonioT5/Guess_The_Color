package guessthecolor.backend.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyAttemptRound {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private DailyPuzzleAttempt dailyPuzzleAttempt;

    private int roundIndex;
    private int targetR;
    private int targetG;
    private int targetB;
    private int guessR;
    private int guessG;
    private int guessB;
    private double deltaE;
    private double score;
}
