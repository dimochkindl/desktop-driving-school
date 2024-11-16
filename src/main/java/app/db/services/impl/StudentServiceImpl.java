package app.db.services.impl;

import app.db.entities.Student;
import app.db.reposotories.StudentRepository;
import app.db.services.interfaces.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public ObservableList<Object> getStudentsForTable() {
        Iterable<Student> students = studentRepository.findAll();
        return FXCollections.observableArrayList(students);
    }

    @Override
    public Optional<Student> findById(long id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return (List<Student>) studentRepository.findAll(); // Ensure this returns List<Student>
    }

    @Override
    public void save(Student student) {
        if (student != null) {
            studentRepository.save(student);
        }
    }

    @Override
    public void update(Student student) {
        if (student != null && studentRepository.existsById(student.getId())) {
            studentRepository.save(student);
        }
    }

    @Override
    public void delete(Student student) {
        if (student != null && studentRepository.existsById(student.getId())) {
            studentRepository.deleteById(student.getId());
        }
    }

    @Override
    public void deleteById(long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        }
    }
}
