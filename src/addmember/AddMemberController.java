/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addmember;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author Cédric Cousin-Ruby
 */
public class AddMemberController implements Initializable {

    DatabaseHandler handler;

    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField mobile;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXButton addMember;
    @FXML
    private JFXButton cancel;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        handler = new DatabaseHandler();

    }

    @FXML
    private void addMember(ActionEvent event) {
        String memberName = name.getText();
        String memberId = id.getText();
        String memberMobile = mobile.getText();
        String memberEmail = email.getText();

        Boolean flag = memberName.isEmpty() || memberId.isEmpty() || memberMobile.isEmpty() || memberEmail.isEmpty();
        if (flag) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter in all fields");
            alert.showAndWait();
            return;
        }
        String qu = "INSERT INTO MEMBER VALUES ("
                + "'" + memberId + "',"
                + "'" + memberName + "',"
                + "'" + memberMobile + "',"
                + "'" + memberEmail + "'"
                + ")";
        System.out.println(qu);
        if (handler.execAction(qu)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("SUCCESS ADD MEMBER!!!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("FAILED ADD MEMBER!!!");
            alert.showAndWait();
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
    }

}
