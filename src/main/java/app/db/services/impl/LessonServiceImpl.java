package app.db.services.impl;

import app.db.entities.Car;
import app.db.entities.Lesson;
import app.db.reposotories.LessonRepository;
import app.db.services.interfaces.LessonService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
public class LessonServiceImpl implements LessonService {

    private LessonRepository repository;

    @Autowired
    public LessonServiceImpl(LessonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Lesson> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Lesson> findAll() {
        return (List<Lesson>) repository.findAll();
    }

    @Override
    public void save(Lesson lesson) {
        if (lesson != null) {
            repository.save(lesson);
        }
    }

    @Override
    public void update(Lesson lesson) {
        if (lesson != null && repository.existsById(lesson.getId())) {
            repository.save(lesson);
        }
    }

    @Override
    public void delete(Lesson lesson) {
        if (lesson != null && repository.existsById(lesson.getId())) {
            repository.delete(lesson);
        }
    }

    @Override
    public void deleteById(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
}
