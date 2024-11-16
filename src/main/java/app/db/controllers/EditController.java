package app.db.controllers;

import app.db.services.CrudService;
import app.db.utils.Factory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Controller
@Setter
@Getter
public class EditController {
    private Factory factory;

    @Autowired
    public EditController(Factory factory) {
        this.factory = factory;
    }

    public void editObjValue(long id, Object updatedObj) {
        CrudService<Object> service = factory.createService(updatedObj.getClass().getName());
        var oldObjOptional = service.findById(id);

        if (oldObjOptional.isPresent()) {
            Object oldObj = oldObjOptional.get();

            copyProperties(updatedObj, oldObj);

            service.update(updatedObj);
            System.out.println("Объект с ID " + id + " обновлен в базе данных.");
        } else {
            System.out.println("Объект с ID " + id + " не найден.");
        }
    }

    private void copyProperties(Object source, Object target) {
        Field[] fields = source.getClass().getDeclaredFields();
        for (var field : fields) {
            field.setAccessible(true);
            try {
                if (field.getType().equals(String.class)) {
                    String value = (String) field.get(source);
                    field.set(target, value);

                } else if (field.getType().equals(Integer.class)) {
                    int value = (int) field.get(source);
                    field.set(target, value);
                }else if (field.getType().equals(Double.class)) {
                    double value = (double) field.get(source);
                    field.set(target, value);
                } else if (field.getType().equals(LocalDateTime.class)) {
                    var value = (LocalDateTime) field.get(source);
                    field.set(target, value);
                }else if (field.getType().equals(Long.class)) {
                    field.set(target, field.get(source));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
