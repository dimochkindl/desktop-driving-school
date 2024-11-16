package app.db.services;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {
    Optional<T> findById(long id);
    List<T> findAll();

    void save(T t);
    void update(T t);

    void delete(T t);
    void deleteById(long id);
}
