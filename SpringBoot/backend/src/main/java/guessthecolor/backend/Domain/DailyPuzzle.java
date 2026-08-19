package guessthecolor.backend.Domain;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    private String seed;

    @OneToMany(mappedBy="dailyPuzzle", fetch = FetchType.EAGER)
    private List<DailyPuzzleAttempt> dailyPuzzleAttempts;
}
