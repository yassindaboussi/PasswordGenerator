/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordgenerator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author yassin
 */
public class PassGeneratorController implements Initializable {

    private final String[] SYMBOLS = {"!", "@", "#", "$", "%", "&", "*", "(", ")", "=", "+", "<", ">", "?", "/"};
    private final String[] NUMBERS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private final String[] LOWERCase = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private final String[] UPPERCase = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private ArrayList<String> randomPasswordList;
    private Random rand;
    @FXML
    private TextArea areaInputGen;
    @FXML
    private JFXCheckBox SymbolsBox;
    @FXML
    private JFXTextField SymbolsCount;
    @FXML
    private JFXButton btnReset;
    @FXML
    private JFXButton btnGenerate;
    @FXML
    private JFXTextField NumbersCount;
    @FXML
    private JFXTextField UpperCount;
    @FXML
    private JFXTextField LowerCount;
    @FXML
    private JFXCheckBox UpperBox;
    @FXML
    private JFXCheckBox LowerBox;
    @FXML
    private JFXCheckBox NumbersBox;
    @FXML
    private TextField StrengthPass;
    @FXML
    private TextField TextStrengthPass;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        randomPasswordList = new ArrayList<String>();
        OnlyNumber();
        TextStrengthPass.setAlignment(Pos.CENTER);
        TextStrengthPass.setStyle("-fx-text-fill: green;");
        BoxSetValue();
    }

    @FXML
    private void close(MouseEvent event) {
        MainApp.stage.close();
    }

    @FXML
    private void onClickGenerate(ActionEvent event) {
        areaInputGen.setText("");
        randomPasswordList.clear();

        if (SymbolsBox.isSelected()) {
            genRandom(Integer.parseInt(SymbolsCount.getText()), SYMBOLS);
        } else {
            SymbolsCount.setText("");
            SymbolsBox.setSelected(false);
        }
        if (UpperBox.isSelected()) {
            genRandom(Integer.parseInt(UpperCount.getText()), UPPERCase);
        } else {
            UpperCount.setText("");
            UpperBox.setSelected(false);
        }
        if (LowerBox.isSelected()) {
            genRandom(Integer.parseInt(LowerCount.getText()), LOWERCase);
        } else {
            LowerCount.setText("");
            LowerBox.setSelected(false);
        }
        if (NumbersBox.isSelected()) {
            genRandom(Integer.parseInt(NumbersCount.getText()), NUMBERS);
        } else {
            NumbersCount.setText("");
            NumbersBox.setSelected(false);
        }

        displayRandomPassword();
        if (!areaInputGen.getText().isEmpty()) {
            TextStrengthPass.setVisible(true);
            StrengthPass.setVisible(true);
            CheckStrengthPass(areaInputGen.getText());
        }
    }

    private void genRandom(int count, String[] Values) {
        rand = new Random();
        int num;
        for (int i = 0; i < count; i++) {
            num = rand.nextInt(10);
            randomPasswordList.add(Values[num]);
        }
    }

    private void displayRandomPassword() {
        Collections.shuffle(randomPasswordList);
        //passLength.setText(Integer.toString(randomPasswordList.size()));
        String pass = "";
        for (int i = 0; i < randomPasswordList.size(); i++) {
            pass += randomPasswordList.get(i);
        }
        areaInputGen.setText(pass);
    }

    private void OnlyNumber() {
        SetOnlyNumber(SymbolsCount);
        SetOnlyNumber(NumbersCount);
        SetOnlyNumber(UpperCount);
        SetOnlyNumber(LowerCount);
    }

    private void SetOnlyNumber(JFXTextField inputField) {
        inputField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                inputField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 2) {
                Platform.runLater(() -> {
                    inputField.clear();
                });
            }
        });
    }

    @FXML
    private void onClickResetBtn(ActionEvent event) {
        areaInputGen.setText("");
        NumbersCount.setText("");
        UpperCount.setText("");
        LowerCount.setText("");
        SymbolsCount.setText("");
        SymbolsBox.setSelected(false);
        NumbersBox.setSelected(false);
        UpperBox.setSelected(false);
        LowerBox.setSelected(false);
        TextStrengthPass.setVisible(false);
        StrengthPass.setVisible(false);
    }

    /////
    public void SetActionOnBox(JFXCheckBox Box, JFXTextField Count) {
        Platform.runLater(() -> {

            Box.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    // TODO Auto-generated method stub
                    if (newValue) {
                        Count.setText("1");
                    } else {
                        Count.setText("");
                    }
                }
            });
        });
    }

    public void BoxSetValue() {
        SetActionOnBox(SymbolsBox, SymbolsCount);
        SetActionOnBox(NumbersBox, NumbersCount);
        SetActionOnBox(UpperBox, UpperCount);
        SetActionOnBox(LowerBox, LowerCount);
    }

    private void CheckStrengthPass(String input) {
        // Checking lower alphabet in string
        int n = input.length();
        boolean hasLower = false, hasUpper = false,
                hasDigit = false, specialChar = false;
        Set<Character> set = new HashSet<Character>(
                Arrays.asList('!', '@', '#', '$', '%', '&', '*', '(', ')', '=', '+', '<', '>', '?', '/'));
        for (char i : input.toCharArray()) {
            if (Character.isLowerCase(i)) {
                hasLower = true;
            }
            if (Character.isUpperCase(i)) {
                hasUpper = true;
            }
            if (Character.isDigit(i)) {
                hasDigit = true;
            }
            if (set.contains(i)) {
                specialChar = true;
            }
        }
        if (hasDigit && hasLower && hasUpper && specialChar
                && (n >= 8)) {
            System.out.print(" Strong ");
            StrengthPass.setStyle("-fx-background-color:#2BAE17");
            TextStrengthPass.setStyle("-fx-background-color:#2BAE17;-fx-background-radius:25px");
            TextStrengthPass.setText("Strong ");
        } else if ((hasLower || hasUpper || specialChar)
                && (n >= 6)) {
            System.out.print(" Medium ");
            StrengthPass.setStyle("-fx-background-color:#8C8BD4");
            TextStrengthPass.setText("Medium ");
            TextStrengthPass.setStyle("-fx-background-color:#8C8BD4;-fx-background-radius:25px");
        } else {
            System.out.print(" Weak ");
            StrengthPass.setStyle("-fx-background-color:#E00808");
            TextStrengthPass.setText("Very Weak ");
            TextStrengthPass.setStyle("-fx-background-color:#E00808;-fx-background-radius:25px");
        }
    }

}
