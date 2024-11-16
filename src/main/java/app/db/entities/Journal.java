package app.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean visit;

    private int grade;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Lesson lesson;

    public String getStudent() {
        return student.getName();
    }

    public String getEmployee() {
        return employee.getName();
    }

    public String getLesson() {
        return lesson.getTheme();
    }

    public void setStudent(long studentId) {
        student = new Student();
        this.student.setId(studentId);
    }

    public void setEmployee(long employeeId) {
        employee = new Employee();
        this.employee.setId(employeeId);
    }

    public void setLesson(long lessonId) {
        lesson = new Lesson();
        this.lesson.setId(lessonId);
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
