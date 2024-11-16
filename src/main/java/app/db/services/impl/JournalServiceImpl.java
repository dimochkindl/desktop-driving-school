package app.db.services.impl;

import app.db.entities.Journal;
import app.db.reposotories.JournalRepository;
import app.db.services.interfaces.JournalService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
@Getter
public class JournalServiceImpl implements JournalService {

    private JournalRepository repository;

    @Autowired
    public JournalServiceImpl(JournalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Journal> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Journal> findAll() {
        return (List<Journal>) repository.findAll();
    }

    @Override
    public void save(Journal journal) {
        if (journal != null) {
            repository.save(journal);
        }
    }

    @Override
    public void update(Journal journal) {
        if (journal != null && repository.existsById(journal.getId())) {
            repository.save(journal);
        }
    }

    @Override
    public void delete(Journal journal) {
        if (journal != null && repository.existsById(journal.getId())) {
            repository.delete(journal);
        }
    }

    @Override
    public void deleteById(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
}
