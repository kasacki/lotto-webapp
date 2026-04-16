package pl.polsl.entities;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class LottoNumber implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer value;

    // Relacja Wiele do jednego: wiele liczb należy do jednego losowania
    @ManyToOne
    @JoinColumn(name = "draw_id")
    private DrawEntity draw;

    // ALT + INSERT -> Getter and Setter

    public Long getId() {
        return id;
    }

    public Integer getValue() {
        return value;
    }

    public DrawEntity getDraw() {
        return draw;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setDraw(DrawEntity draw) {
        this.draw = draw;
    }
    
}