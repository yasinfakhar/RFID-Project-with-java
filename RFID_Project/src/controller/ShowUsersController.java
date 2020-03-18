package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.entity.User;
import service.connetion.DBConnection;
import service.dao.UserDAO;
import service.repository.repository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShowUsersController implements Initializable {

    @FXML
    TableView table;
    @FXML
    Button edit;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //create connection to get the users
        DBConnection dbConnection = new DBConnection();
        UserDAO userDAO = new UserDAO(dbConnection);
        try {

            //initializing the table view
	ObservableList<User> list = FXCollections.observableArrayList(userDAO.getUsers());
	TableColumn userID = new TableColumn("UserID");
	userID.setCellValueFactory(new PropertyValueFactory<User,String>("userID"));

	TableColumn firstName = new TableColumn("First Name");
	firstName.setCellValueFactory(new PropertyValueFactory<User,String>("firstName"));

	TableColumn lastName = new TableColumn("Last Name");
	lastName.setCellValueFactory(new PropertyValueFactory<User,String>("lastName"));

	TableColumn cardID = new TableColumn("CardID");
	cardID.setCellValueFactory(new PropertyValueFactory<User,String>("cardID"));

	TableColumn picture = new TableColumn("Picture");
	picture.setCellValueFactory(new PropertyValueFactory<User,String>("profilePictureAddress"));

	table.setItems(list);
	table.getColumns().addAll(userID,firstName,lastName,cardID,picture);

        } catch (SQLException e) {
	e.printStackTrace();
        }
    }

    @FXML
    public void back (MouseEvent e) throws IOException {
        repository.map.put("selectedUser" , null);
        repository.changeStageContent("MainLayout");
    }

    @FXML
    public void edit(MouseEvent event) throws IOException {
        User user = (User)table.getSelectionModel().getSelectedItem();
        if (user == null) {
	repository.message = "Select a row";
	showPopUp();
        }
        else{
	repository.map.put("selectedUser",user);
	repository.changeStageContent("MainLayout");
	repository.cardID = user.getCardID();
        }

    }

    public void  showPopUp() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../view/popup.fxml"));
        stage.setTitle("WARNING");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

    }
}
