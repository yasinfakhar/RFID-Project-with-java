/**
 * @autor  Yasin Fakhar
 * edited : 13 March 2020
 * **/

package service.repository;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class repository {
    public static String cardID;
    public static Stage stage;
    public static String message;
    public static String profilePictureName;
    public static HashMap map = new HashMap();

    public static void changeStageContent(String xml) throws IOException {
        Parent root = FXMLLoader.load(repository.class.getClass()
                .getResource("/view/" + xml + ".fxml"));
        stage.setTitle("RFID APPLICATION");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

}
