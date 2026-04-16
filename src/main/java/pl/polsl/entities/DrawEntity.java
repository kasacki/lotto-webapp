package pl.polsl.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "DRAW_ENTITY") // Dobra praktyka: zdefiniuj nazwę tabeli
public class DrawEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate drawDate;

    // Relacja 1 do wielu: jedno losowanie ma wiele rekordów w tabeli liczb
    @OneToMany(mappedBy = "draw", cascade = CascadeType.ALL)
    private List<LottoNumber> numbers;

    public void setId(Long id) {
        this.id = id;
    }

    public void setDrawDate(LocalDate drawDate) {
        this.drawDate = drawDate;
    }

    public void setNumbers(List<LottoNumber> numbers) {
        this.numbers = numbers;
    }

    // ALT + INSERT -> Getter and Setter

    public Long getId() {
        return id;
    }

    public LocalDate getDrawDate() {
        return drawDate;
    }

    public List<LottoNumber> getNumbers() {
        return numbers;
    }
}