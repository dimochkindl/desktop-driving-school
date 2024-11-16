package app.db;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

public class JavaFXApp extends Application {

    private static ApplicationContext applicationContext;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(DbApplication.class).run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/windows/main_window.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Parent root = loader.load();
        var scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Driving school");
        primaryStage.setResizable(true);
        ((Stage) scene.getWindow()).setScene(scene);
        scene.getWindow().getScene().getRoot().setStyle("-fx-background-color: #4e4e4e;");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Callback<Class<?>, Object> getBean() {
        return clazz -> applicationContext.getBean(clazz);
    }

    public static Object getBean(Class<?> aClass) {
        return applicationContext.getBean(aClass);
    }
}