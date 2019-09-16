/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookList;

import bibliotheque.addBookController;
import database.DatabaseHandler;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Cédric Cousin-Ruby
 */
public class BooklistController implements Initializable {

    ObservableList<Book> list = FXCollections.observableArrayList();
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> idCol;
    @FXML
    private TableColumn<Book, String> authorCol;
    @FXML
    private TableColumn<Book, String> publisherCol;
    @FXML
    private TableColumn<Book, Boolean> availableCol;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }

    private void initCol() {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availableCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
    }

    private void loadData() {

        DatabaseHandler handler = new DatabaseHandler();
        String qu = " SELECT * FROM BOOK";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String titleRead = rs.getString("title");
                String idRead = rs.getString("id");
                String authorRead = rs.getString("author");
                String publisherRead = rs.getString("publisher");
                Boolean avail = rs.getBoolean("isAvail");

                list.add(new Book(titleRead, idRead, authorRead, publisherRead, avail));
            }
        } catch (SQLException ex) {
            Logger.getLogger(addBookController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.getItems().setAll(list);

    }

    public static class Book {

        private final SimpleStringProperty title;
        private final SimpleStringProperty id;
        private final SimpleStringProperty author;
        private final SimpleStringProperty publisher;
        private final SimpleStringProperty availability;

        public Book(String title, String id, String author, String pub, boolean avail) {
            this.title = new SimpleStringProperty(title);
            this.id = new SimpleStringProperty(id);
            this.author = new SimpleStringProperty(author);
            this.publisher = new SimpleStringProperty(pub);
            if (avail) {
                this.availability = new SimpleStringProperty("Available");
            } else {
                this.availability = new SimpleStringProperty("Issued");
            }
        }

        public String getTitle() {
            return title.get();
        }

        public String getId() {
            return id.get();
        }

        public String getAuthor() {
            return author.get();
        }

        public String getPublisher() {
            return publisher.get();
        }

        public String getAvailabilty() {
            return availability.get();
        }
    }

}
