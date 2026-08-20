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
public class PracticeRound {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Practice practice;

    private int roundIndex;
    private int targetH;
    private int targetS;
    private int targetB;
    private int guessH;
    private int guessS;
    private int guessB;
    private double deltaE;
    private double score;
}
