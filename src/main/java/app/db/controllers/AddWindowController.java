package app.db.controllers;

import app.db.services.CrudService;
import app.db.utils.ServiceFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class AddWindowController {

    private final ServiceFactory factory;
    public VBox fieldsContainer;

    @FXML
    private List<TextField> fields = new ArrayList<>();
    @FXML
    private Button submitButton;

    @Setter
    private String table;

    @FXML
    Button exitButton;

    @Autowired
    public AddWindowController(ServiceFactory factory) {
        this.factory = factory;
    }

    @FXML
    void initialize() {
        assert factory != null;
        if(!fields.isEmpty()){
            fields.clear();
        }
        exitButton.setFocusTraversable(false);
        exitButton.setOnAction(_ -> handleExitButton());
        submitButton.setFocusTraversable(false);

    }


    @FXML
    private void handleExitButton() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void getColumnsForTable(String tableName) {
        this.table = capitalizeFirstLetter(tableName);
        try {
            Class<?> clazz = Class.forName("app.db.entities." + table);
            Method[] methods = clazz.getDeclaredMethods();
            for (var method : methods) {
                if (method.getName().startsWith("set") && method.getParameterCount() == 1) {
                    String name = method.getName().substring(3);
                    if (name.equals("Id")) {
                        continue;
                    }
                    fields.add(new TextField("Enter" + name));
                }
            }
            for (var field : fields) {
                fieldsContainer.getChildren().add(field);
                field.setPrefHeight(20);
                field.setPrefWidth(60);
                field.setVisible(true);
            }
            submitButton.setOnAction(_ -> handleSubmitButton(table));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleSubmitButton(String tableName) {
        try {
            Class<?> clazz = Class.forName("app.db.entities." + table);
            Object item = clazz.getDeclaredConstructor().newInstance();
            Method[] methods = clazz.getDeclaredMethods();
            int i = 0;
            for (var method : methods) {
                if (method.getName().startsWith("set") && method.getParameterCount() == 1) {
                    String name = method.getName().substring(3);
                    if (name.equals("Id")) {
                        continue;
                    }
                    if (i >= fields.size()) {
                        break;
                    }
                    String value = fields.get(i).getText();
                    Class<?> paramType = method.getParameterTypes()[0];
                    if (paramType.equals(String.class)) {
                        method.invoke(item, value);
                    } else if (paramType.equals(int.class) || paramType.equals(Integer.class)) {
                        method.invoke(item, Integer.parseInt(value));
                    } else if (paramType.equals(long.class) || paramType.equals(Long.class)) {
                        method.invoke(item, Long.parseLong(value));
                    } else if (paramType.equals(boolean.class) || paramType.equals(Boolean.class)) {
                        method.invoke(item, Boolean.parseBoolean(value));
                    } else if (paramType.equals(LocalDateTime.class)) {
                        method.invoke(item, LocalDateTime.parse(value));
                    }

                    i++;
                }
            }
            CrudService<Object> service = factory.createService(table);
            service.save(item);
            handleExitButton();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            e.printStackTrace(System.out);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format: " + e.getMessage());
        }
    }

    public static String capitalizeFirstLetter(String input) {
        return (input == null || input.isEmpty()) ? input :
                input.substring(0, 1).toUpperCase() + input.substring(1);
    }

}
