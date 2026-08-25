package guessthecolor.backend.Domain;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyPuzzle {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private Date puzzleDate;
    private long seed;

    @OneToMany(mappedBy="dailyPuzzle")
    private List<DailyPuzzleAttempt> dailyPuzzleAttempts;

    public long getSeed() {
        return this.seed;
    }
}
