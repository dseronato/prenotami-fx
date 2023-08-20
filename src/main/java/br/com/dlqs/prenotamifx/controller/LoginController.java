package br.com.dlqs.prenotamifx.controller;

import br.com.dlqs.prenotamifx.player.Mp3Player;
import br.com.dlqs.prenotamifx.thread.TimerThread;
import com.sun.javafx.scene.control.IntegerField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.ImageView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/login.fxml")
public class LoginController {


    @Value("${chrome.path}")
    private String chromePath;
    @Value("${music.path}")
    private String musicPath;

    @Autowired
    private Mp3Player mp3Player;
    @FXML
    private ImageView logo;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    public IntegerField instances;
    @FXML
    public ChoiceBox choiceBox;

    @FXML
    protected void loginClick() throws Exception {
        for (int i = 0; i < instances.getValue(); i++) {
            new Thread(new TimerThread(usernameInput, passwordInput,chromePath, musicPath, instances, choiceBox)).start();
        }
    }
}