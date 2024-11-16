package app.db.controllers;

import app.db.services.CrudService;
import app.db.utils.Factory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Getter
@Setter
public class DeleteController {
    private Factory factory;

    @Autowired
    public DeleteController(Factory factory) {
        this.factory = factory;
    }

    public void deleteFromDb(long id, String className) {
        CrudService<Object> service = factory.createService(className);
        var oldObjOptional = service.findById(id);

        if (oldObjOptional.isPresent()) {
            Object oldObj = oldObjOptional.get();
            service.delete(oldObj);
            System.out.println("Deleted obg: " + oldObj);
        } else {
            System.out.println("Couldn't find obj with id " + id );
        }
    }
}
