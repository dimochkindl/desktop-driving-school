package app.db.controllers;

import app.db.services.ScriptToDbServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@Getter
@Setter
public class ScriptLoaderController {

    private final ScriptToDbServiceImpl service;

    @FXML
    private TextArea scriptTextArea;

    @FXML
    private Button loadButton;

    @Autowired
    public ScriptLoaderController(ScriptToDbServiceImpl service) {
        this.service = service;
    }

    @FXML
    public void initialize() {
        System.out.println("Controller initialized successfully!");
        loadButton.setOnAction(_ -> handleLoadFile());
    }

    @FXML
    private void handleLoadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the file to load");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File file = fileChooser.showOpenDialog(scriptTextArea.getScene().getWindow());
        if (file != null) {
            loadFileContent(file);
        }
    }

    private void loadFileContent(File file) {
        try {
            String content = Files.readString(file.toPath());
            scriptTextArea.setText(service.getScriptResult(content));
        } catch (IOException e) {
            scriptTextArea.setText("Exception while reading the file: " + e.getMessage());
        }
    }
}