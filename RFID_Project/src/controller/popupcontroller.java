/**
 * @autor  Yasin Fakhar
 * edited : 13 March 2020
 * **/

package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import service.repository.repository;

import java.net.URL;
import java.util.ResourceBundle;


public class popupcontroller implements Initializable {
    @FXML
    Label lb_popup;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lb_popup.setText(repository.message);
    }
}
