/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

/**
 *
 * @author Michael Liondy
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    @FXML
    private TextArea txtArea;
    @FXML
    private Label lblWords;
    @FXML
    private Label lblLines;
    @FXML
    private Label lblChars;
    private HBox pnlAutosave;
    private String location;
    @FXML
    private Label lblSentences;
    @FXML
    private Label lblHbox;

    @FXML
    private void typed() {
        Thread thread = new Thread(() -> {
            synchronized (txtArea) {
                String text = txtArea.getText().trim();
                int words = text.isEmpty() ? 0 : text.split("\\s").length;
                int lines = text.isEmpty() ? 0 : text.split("\\n").length;
                int sentences = text.isEmpty() ? 0 : text.split("\\.").length;
                Platform.runLater(() -> {
                    lblLines.setText(Integer.toString(lines) + " lines");
                    lblWords.setText(Integer.toString(words) + " words");
                    lblChars.setText(Integer.toString(txtArea.getText().length()) + " chars");
                    lblSentences.setText(Integer.toString(sentences) + " sentences");
                });
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void delete(ActionEvent event) {
        txtArea.setText("");
        this.lblChars.setText("0 chars");
        this.lblLines.setText("0 lines");
        this.lblWords.setText("0 words");
        this.lblSentences.setText("0 sentences");
        this.lblHbox.setText("");
    }

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private void autosave() {
        Runnable autoSave = () -> {
            if (location == null || location.isEmpty()) {
                System.out.println("Autosave");
                return;
            }
            Platform.runLater(() -> {
                pnlAutosave.setVisible(true);
            });
            Platform.runLater(() -> {
                pnlAutosave.setVisible(false);
            });
        };
        // autosave setiap 5 detik
        executor.scheduleAtFixedRate(autoSave, 0, 5, TimeUnit.SECONDS);
        this.lblHbox.setText("Autosaved to autosave.txt");
        loadFile("autosave.txt");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
        txtArea.setText("");
        this.lblChars.setText("0 chars");
        this.lblLines.setText("0 lines");
        this.lblWords.setText("0 words");
        this.lblSentences.setText("0 sentences");
        this.lblHbox.setText("Welcome to 2017730007 Application");
    }

    @FXML
    private void save(ActionEvent event) {
        FileChooser fc = new FileChooser();
        //Set extension filter
            FileChooser.ExtensionFilter ex = 
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fc.getExtensionFilters().add(ex);
            //Show save file dialog
            File file = fc.showSaveDialog(this.lblLines.getScene().getWindow());
            if(file != null){
                saveFile(txtArea.getText(), file);
            }
    }

    private void saveFile(String text, File file) {
        try {
            this.lblHbox.setText("Saving....");
            FileWriter fw;
            fw = new FileWriter(file);
            fw.write(text);
            this.lblHbox.setText("Saved to "+ file);
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class
                .getName()).log(Level.SEVERE, null, ex);
            this.lblHbox.setText("Failed to save file");
        }
    }

    @FXML
    private void open(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open File");
        FileChooser.ExtensionFilter ex = new FileChooser.ExtensionFilter("TXT Files (*.txt)", "*.txt");
        fc.getExtensionFilters().add(ex);

        location = fc.showOpenDialog(this.lblLines.getScene()
                .getWindow()).getAbsolutePath();
        if (location == null || location.isEmpty()) {
            (new Alert(Alert.AlertType.ERROR,"Unable to open file. Location not set.")).show();
            return;
        }
        loadFile(location);
        typed();
    }

    private void loadFile(String location) {
        Logger.getLogger(Editor.class.getName());
        this.lblHbox.setText("Opening from "+location);
        txtArea.setText(readFile(location));
    }
    
    public String readFile(String fileName){
        String text = "";
        try{
            text = new String(Files.readAllBytes(Paths.get(fileName)));
        }catch(IOException e){
            e.printStackTrace();
        }
        return text;
    }
}
