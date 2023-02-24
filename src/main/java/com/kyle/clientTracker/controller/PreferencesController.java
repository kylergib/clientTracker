package com.kyle.clientTracker.controller;

import com.kami.lookout.Bet;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.kyle.clientTracker.Main.client;

public class PreferencesController implements Initializable {
    public ListView allTagsListView;
    public ListView tagsMonitorListView;
    public Label errorLabel;
    private ObservableList<String> tagsMonitorList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        allTagsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tagsMonitorListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<String> allTags = FXCollections.observableArrayList();

        for (Bet bet: MainController.allBets) {
            List<String> tags = List.of(bet.getTags().split(" "));
            for (String tag: tags) {
                if (!allTags.contains(tag.toLowerCase())) {
                    allTags.add(tag.toLowerCase());
                }
            }



        }

        allTagsListView.setItems(allTags);
    }

    public void addTagButtonClicked(ActionEvent actionEvent) {


        if ((allTagsListView.getSelectionModel().getSelectedItems().stream().count() + tagsMonitorListView.getItems().stream().count()) > 4 ) {
            setError("Can only have 4 items in monitor list.");
        } else {
            tagsMonitorList.addAll(allTagsListView.getSelectionModel().getSelectedItems());
            tagsMonitorListView.setItems(tagsMonitorList);
        }
    }

    public void removeTagButtonClicked(ActionEvent actionEvent) {
    }

    public void setError(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: #e06666");
    }
    public void setSuccess(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: #55ff6b");
    }
}
