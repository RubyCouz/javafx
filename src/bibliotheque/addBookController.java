/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotheque;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

/**
 *
 * @author Cédric Cousin-Ruby
 */
public class addBookController implements Initializable {

    private Label label;
    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField publisher;
    @FXML
    private JFXButton addBook;
    @FXML
    private JFXButton cancel;

    DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = new DatabaseHandler();

        try {
            checkData();
        } catch (SQLException ex) {
            Logger.getLogger(addBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addBook(ActionEvent event) {
        String bookID = id.getText();
        String bookAuthor = author.getText();
        String bookTitle = title.getText();
        String bookPublisher = publisher.getText();

        if (bookID.isEmpty() || bookTitle.isEmpty() || bookAuthor.isEmpty() || bookPublisher.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter in all fields");
            alert.showAndWait();
            return;
        }
        String qu = "INSERT INTO BOOK VALUES ("
                + "'" + bookID + "',"
                + "'" + bookTitle + "',"
                + "'" + bookAuthor + "',"
                + "'" + bookPublisher + "',"
                + "" + true + ""
                + ")";
        System.out.println(qu);
        if (databaseHandler.execAction(qu)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("SUCCESS !!!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("FAILED !!!");
            alert.showAndWait();
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
    }

    private void checkData() throws SQLException {
        String qu = " SELECT * FROM BOOK";
        ResultSet rs = databaseHandler.execQuery(qu);
        try {
            while (rs.next()) {
                String titleRead = rs.getString("title");
                String idRead = rs.getString("ID");
                String authorRead = rs.getString("author");
                String publisherRead = rs.getString("publisher");
                System.out.println("Titre : " + titleRead);
                System.out.println("ID : " + idRead);
                System.out.println("Auteur : " + authorRead);
                System.out.println("Editeur : " + publisherRead);
                System.out.println("***************************");
            }
        } catch (SQLException ex) {
            Logger.getLogger(addBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
