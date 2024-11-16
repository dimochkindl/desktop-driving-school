package app.db.controllers;

import app.db.JavaFXApp;
import app.db.entities.*;
import app.db.enums.Modes;
import app.db.enums.Tables;
import app.db.services.impl.*;
import app.db.utils.Factory;
import app.db.utils.PdfExporter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;
import static org.springframework.util.StringUtils.capitalize;

@Setter
@Getter
@Controller
public class MainWindowController {

    private final EditController editController;
    private final DeleteController deleteController;
    private Factory factory;

    @FXML
    private Button print;
    @FXML
    private MenuButton modeChoice;
    @FXML
    private MenuButton tableChoice;
    @FXML
    private Button deleteButton;

    @FXML
    private Button packetModeButton;

    @FXML
    private TableView<Object> mainTable;

    private Modes currentMode;
    private Tables currentTable;

    @Autowired
    public MainWindowController(Factory factory, EditController editController, DeleteController deleteController) {
        this.factory = factory;
        this.editController = editController;
        this.deleteController = deleteController;
    }

    @FXML
    void initialize() {
        currentMode = Modes.view;
        currentTable = Tables.student;
        initializeButtons();
        initializeTableEdit();
        handleTableChoice(currentTable);
    }

    private void initializeTablesButton() {
        List<MenuItem> tableItems = new ArrayList<>();
        for (var table : Tables.values()) {
            if (table.equals(currentTable)) {
                continue;
            }
            var item = new MenuItem(table.name());
            item.setOnAction(_ -> handleTableChoice(table));
            tableItems.add(item);
        }
        tableChoice.getItems().addAll(tableItems);
    }

    private void initializeModesButton() {
        List<MenuItem> modeItems = new ArrayList<>();
        for (var mode : Modes.values()) {
            if (mode.equals(currentMode)) {
                continue;
            }
            var item = new MenuItem(mode.name());
            item.setOnAction(_ -> handleButtonModes(mode));
            modeItems.add(item);
        }
        modeChoice.getItems().addAll(modeItems);
    }

    private void initializeButtons() {
        print.setOnAction(_ -> exportToPdf());
        deleteButton.setOnAction(_ -> handleDeleteButton());
        packetModeButton.setOnAction(_ -> openPacketModeWindow());

        initializeModesButton();
        initializeTablesButton();
    }

    private void handleTableChoice(Tables table) {
        switch (table) {
            case student:
                StudentServiceImpl studentService = factory.createService(StudentServiceImpl.class);
                addColumnsToTable(Student.class);
                List<Student> students = studentService.findAll();
                mainTable.setItems(FXCollections.observableArrayList(students));
                currentTable = Tables.student;
                break;
            case car:
                CarServiceImpl carService = factory.createService(CarServiceImpl.class);
                addColumnsToTable(Car.class);
                List<Car> cars = carService.findAll();
                mainTable.setItems(FXCollections.observableArrayList(cars));
                currentTable = Tables.car;
                break;
            case post:
                PostServiceImpl postService = factory.createService(PostServiceImpl.class);
                addColumnsToTable(Post.class);
                List<Post> posts = postService.findAll();
                mainTable.setItems(FXCollections.observableArrayList(posts));
                currentTable = Tables.post;
                break;
            case employee:
                EmployeeServiceImpl employeeService = factory.createService(EmployeeServiceImpl.class);
                addColumnsToTable(Employee.class);
                List<Employee> employees = employeeService.findAll();
                mainTable.setItems(FXCollections.observableArrayList(employees));
                currentTable = Tables.employee;
                break;
            case lesson:
                LessonServiceImpl lessonService = factory.createService(LessonServiceImpl.class);
                addColumnsToTable(Lesson.class);
                List<Lesson> lessons = lessonService.findAll();
                mainTable.setItems(FXCollections.observableArrayList(lessons));
                currentTable = Tables.lesson;
                break;
            case journal:
                JournalServiceImpl journalService = factory.createService(JournalServiceImpl.class);
                addColumnsToTable(Journal.class);
                List<Journal> journal = journalService.findAll();
                mainTable.setItems(FXCollections.observableArrayList(journal));
                currentTable = Tables.journal;
                break;
        }
        if (currentMode.equals(Modes.fix)) {
            initializeTableEdit();
        }
        tableChoice.getItems().clear();
        initializeTablesButton();
    }

    private void addColumnsToTable(Class<?> model) {
        List<TableColumn<Object, ?>> columns = new ArrayList<>();

        for (Method method : model.getDeclaredMethods()) {
            if (isGetter(method)) {
                String fieldName = getFieldName(method);
                if (fieldName.equals("id")) continue;

                Class<?> returnType = method.getReturnType();
                TableColumn<Object, ?> column = new TableColumn<>(fieldName);
                column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
                column.setEditable(true);

                if (returnType == String.class) {
                    TableColumn<Object, String> stringColumn = new TableColumn<>(fieldName);
                    stringColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
                    stringColumn.setCellFactory(forTableColumn());
                    columns.add(stringColumn);
                } else if (returnType == Integer.class || returnType == int.class) {
                    TableColumn<Object, Integer> integerColumn = new TableColumn<>(fieldName);
                    integerColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
                    integerColumn.setCellFactory(forTableColumn(new IntegerStringConverter()));
                    columns.add(integerColumn);
                } else {
                    column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
                    columns.add(column);
                }
            }
        }

        mainTable.getColumns().clear();
        mainTable.getColumns().addAll(columns);
        //mainTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    private boolean isGetter(Method method) {
        return method.getName().startsWith("get") && method.getParameterCount() == 0;
    }

    private String getFieldName(Method method) {
        String name = method.getName();
        return name.substring(3, 4).toLowerCase() + name.substring(4);
    }


    @FXML
    private void exportToPdf() {
        List<Object> values = mainTable.getItems();
        try {
            PdfExporter.exportToPdf(values, values.getFirst().toString() + ".pdf");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    private void handleButtonModes(Modes mode) {
        switch (mode) {
            case fix:
                deleteButton.setVisible(false);
                initializeTableEdit();
                mainTable.requestFocus();
                mainTable.setEditable(true);
                for (var column : mainTable.getColumns()) {
                    column.setEditable(true);
                }
                currentMode = Modes.fix;
                break;
            case add:
                deleteButton.setVisible(false);
                openAddWindow();
                break;
            case delete:
                mainTable.requestFocus();
                deleteButton.setVisible(true);
                currentMode = Modes.delete;
                break;
            case view:
                deleteButton.setVisible(false);
                mainTable.requestFocus();
                mainTable.setEditable(false);
                for (var column : mainTable.getColumns()) {
                    column.setEditable(false);
                }
                currentMode = Modes.view;
                break;
        }
        modeChoice.getItems().clear();
        initializeModesButton();
    }


    private void initializeTableEdit() {
        for (var column : mainTable.getColumns()) {
            column.setOnEditCommit(event -> {

                Object item = event.getRowValue();
                String newValue = event.getNewValue().toString();

                String fieldName = column.getText();
                Class<?> returnType = column.getCellData(event.getTablePosition().getRow()).getClass();

                String setterName = "set" + capitalize(fieldName);
                try {
                    Method setterMethod = item.getClass().getMethod(setterName, returnType);
                    if (returnType == Integer.class) {
                        setterMethod.invoke(item, Integer.parseInt(newValue));
                    } else {
                        setterMethod.invoke(item, newValue);
                    }

                    long id = getIdFromItem(item);
                    editController.editObjValue(id, item);
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
            });
        }
    }

    private void handleDeleteButton() {
        var item = mainTable.getSelectionModel().getSelectedItem();
        if (item != null) {
            System.out.println("item: " + item);
            deleteController.deleteFromDb(getIdFromItem(item), item.getClass().getName());
            handleTableChoice(currentTable);
        }
    }

    private long getIdFromItem(Object item) {
        try {
            Method getIdMethod = item.getClass().getMethod("getId");
            return (long) getIdMethod.invoke(item);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return -1;
        }
    }

    private void openAddWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/windows/add_window.fxml"));
            loader.setControllerFactory(JavaFXApp::getBean);
            Parent root = loader.load();

            AddWindowController controller = loader.getController();
            controller.setTable(String.valueOf(currentTable));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(String.valueOf(currentTable));
            stage.setResizable(true);
            controller.getColumnsForTable(String.valueOf(currentTable));
            Stage mainStage = (Stage) mainTable.getScene().getWindow();
            mainStage.hide();
            stage.show();

            stage.setOnHidden(_ -> {
                mainStage.show();
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void openPacketModeWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/windows/load_file.fxml"));
        loader.setControllerFactory(JavaFXApp::getBean);
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Upload file");
            stage.setResizable(true);
            Stage mainStage = (Stage) mainTable.getScene().getWindow();
            mainStage.hide();
            stage.show();

            stage.setOnHidden(_ -> {
                mainStage.show();
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
