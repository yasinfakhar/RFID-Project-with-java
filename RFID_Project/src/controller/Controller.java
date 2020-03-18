/**
 * @autor  Yasin Fakhar
 * edited : 13 March 2020
 **/

package controller;

import javafx.animation.PauseTransition;
import javafx.fxml.Initializable;
import javafx.util.Duration;
import model.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import service.connetion.DBConnection;
import service.dao.UserDAO;
import service.repository.repository;
import utility.JSerial;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

;

public class Controller implements Initializable {

    @FXML
    TextField tf_first_name;
    @FXML
    TextField tf_last_name;
    @FXML
    TextField tf_card_id;
    @FXML
    TextField tf_user_id;
    @FXML
    Button btn_profile_pic;
    @FXML
    Button btn_save;
    @FXML
    ImageView iv_profile_pic;
    @FXML
    Label lb_status;
    @FXML
    Label lb_save;
    @FXML
    Button btn_show_all_users;
    Image image;


    @FXML
    public void showAllUsers(MouseEvent event) throws IOException {
        repository.changeStageContent("ShowUserLayout");

    }


    @FXML
    public void save(MouseEvent event) throws IOException, InterruptedException {
        //getting text fields values
        String firstName = tf_first_name.getText();
        String lastName = tf_last_name.getText();
        String cardID = tf_card_id.getText();
        String userID = tf_user_id.getText();

        //checking for un null data
        if (firstName.equals(null) || firstName.trim().equals("") ||
	    lastName.equals(null) || lastName.trim().equals("") ||
	    userID.equals(null) || userID.trim().equals("")
        ) {
	repository.message = "required fields can not be empty";

	showPopUp();
	return;
        }
        if (repository.cardID == null) {
	repository.message = "please scan the card";
	showPopUp();
	return;
        }

        //Initializing the user object
        User user = new User();
        user.setCardID(cardID);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserID(userID);
        user.setProfilePictureAddress(repository.profilePictureName);

        //create a connection
        DBConnection dbConnection = new DBConnection();
        UserDAO userDAO = new UserDAO(dbConnection);

        //inset user into database
        userDAO.insetUser(user);

        //show the label for two seconds
        lb_save.setText("user saved successfully");
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> lb_save.setText(null));
        pause.play();
    }


    @FXML
    public void scan(MouseEvent event) throws FileNotFoundException, InterruptedException {

        repository.cardID = null;
        String cardID = null;

        //scanning card ID
        JSerial jSerial = new JSerial();
        jSerial.scanCard();
        cardID = repository.cardID;


        if (cardID != null) {
	tf_card_id.setText(cardID);
	lb_status.setText("scanned successfully");

        } else {
	lb_status.setText("scan failed");
	tf_card_id.setText(null);

        }

    }

    @FXML
    public void choosePic(MouseEvent event) {

        //creating a file chooser to get the image file
        Stage stage = new Stage();
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter
	    = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg" ,"*.jfif");
        chooser.getExtensionFilters().add(imageFilter);
        File source = chooser.showOpenDialog(stage);

        if (source != null) {
	//getting file extension
	String ext = FilenameUtils.getExtension(source.getAbsolutePath());

	//generating a unique name for the Image
	String uuid = UUID.randomUUID().toString();

	//set the destination path
	File dest = new File("resources/profilePictures/" + uuid + "." + ext);

	try {

	    //copy the image file into the destination directory
	    FileUtils.copyFile(source, dest);

	    //initial the picture name variable to store in the database
	    repository.profilePictureName = uuid + "." + ext;

	    //show the picture in the Image View
	    iv_profile_pic.setImage(new Image(new FileInputStream(dest.getPath())));

	} catch (IOException e) {
	    e.printStackTrace();
	}
        }
    }


    //        @Override
//    public void initialize(URL location, ResourceBundle resources) {
//	try {
//	    iv_profile_pic.setImage(new Image(new FileInputStream("resources/pictures/nullprofile.jpg")));
//	} catch (FileNotFoundException e) {
//	    e.printStackTrace();
//	}
//
//        }
    public void showPopUp() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../view/popup.fxml"));
        stage.setTitle("WARNING");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
	image = new Image(new FileInputStream("resources/pictures/progress.gif"));
        } catch (FileNotFoundException e1) {
	e1.printStackTrace();
        }
        User user = (User) repository.map.get("selectedUser");
        if (user != null) {
	tf_first_name.setText(user.getFirstName());
	tf_last_name.setText(user.getLastName());
	tf_card_id.setText(user.getCardID());
	tf_user_id.setText(user.getUserID());
	try {
	    iv_profile_pic.setImage(new Image(new FileInputStream("resources/profilePictures/" + user.getProfilePictureAddress())));
	    repository.profilePictureName = user.getProfilePictureAddress();
	} catch (FileNotFoundException e) {
	    try {
	        iv_profile_pic.setImage(new Image(new FileInputStream("resources/pictures/nullprofile.jpg")));
	        repository.profilePictureName = null;
	    } catch (FileNotFoundException e1) {
	        e1.printStackTrace();
	    }
	}
        } else {
	try {
	    iv_profile_pic.setImage(new Image(new FileInputStream("resources/pictures/nullprofile.jpg")));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
        }
    }
}