package app.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 30)
    private String name;

    @NotNull
    @Size(max = 40)
    private String email;

    @Size(max = 3)
    private String category;

    @SuppressWarnings({})
    public Long getId() {
        return id;
    }

    public void setName(@NotNull @Size(max = 30) String name) {
        this.name = name;
    }

    public void setEmail(@NotNull @Size(max = 40) String email) {
        this.email = email;
    }

    public void setCategory(@Size(max = 3) String category) {
        this.category = category;
    }

    public void setId(long id) {
        this.id = id;
    }
}
