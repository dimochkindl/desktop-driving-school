package app.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 7)
    private String number;

    @NotNull
    @Size(max = 30)
    private String model;

    @Getter
    @Setter
    private int year;

    public @NotNull @Size(max = 7) String getNumber() {
        return number;
    }

    public @NotNull @Size(max = 30) String getModel() {
        return model;
    }

    public void setNumber(@NotNull @Size(max = 7) String number) {
        this.number = number;
    }

    public void setModel(@NotNull @Size(max = 30) String model) {
        this.model = model;
    }
}
