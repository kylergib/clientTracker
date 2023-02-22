package com.kyle.clientTracker.controller;
//done
import com.kyle.clientTracker.Main;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.function.Predicate;


import com.kami.lookout.Bet;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import static com.kyle.clientTracker.Main.client;

/**
 * controller for main window on startup
 * @author Kyle Gibson
 */

public class MainController implements Initializable {
    public TableView betTable;
    public TableColumn betIdColumn;
    public TableColumn betLegColumn;
    public TableColumn editColumn;
    public TableColumn sportsbookColumn;
    public TableColumn oddsColumn;
    public TableColumn stakeColumn;
    public TableColumn profitColumn;
    public TableColumn tagsColumn;
    public TableColumn dateColumn;
    public Label kyle_TotalTodayLabel;
    public Label kyle_TotalYesterdayLabel;
    public Label kyle_TotalProfitLabel;
    public Label kyle_StakeTodayLabel;
    public Label kyle_StakeYesterdayLabel;
    public Label kyle_TotalStakedLabel;
    public Label sam_TotalTodayLabel;
    public Label sam_TotalYesterdayLabel;
    public Label sam_TotalProfitLabel;
    public Label sam_StakeTodayLabel;
    public Label sam_StakeYesterdayLabel;
    public Label sam_TotalStakedLabel;
    public Label alex_TotalTodayLabel;
    public Label alex_TotalYesterdayLabel;
    public Label alex_TotalProfitLabel;
    public Label alex_StakeTodayLabel;
    public Label alex_StakeYesterdayLabel;
    public Label alex_TotalStakedLabel;
    public Label posEv_TotalTodayLabel;
    public Label posEv_TotalYesterdayLabel;
    public Label posEv_TotalProfitLabel;
    public Label posEv_StakeTodayLabel;
    public Label posEv_StakeYesterdayLabel;
    public Label posEv_TotalStakedLabel;
    
    public Label monthStakeLabel;
    public Label totalProfitLabel;
    public Label totalStakeLabel;
    public Label moneyPendingLabel;
    public Label todayStakeLabel;
    public Label freeBetPendingLabel;
    public Label yesterdayProfitLabel;
    public Label yesterdayStakeLabel;
    public Label yesterdayROILabel;
    public Label totalROILabel;
    public Label todayProfitLabel;
    public Label monthProfitLabel;
    public Label monthROILabel;
    public Label todayROILabel;
    public Label kyle_roiLabel;
    public Label sam_roiLabel;
    public Label alex_roiLabel;
    public Label posEv_roiLabel;
    public TextField searchTextField;
    public Label errorLabel;

    private ComboBox comboBoxStatus = new ComboBox<>();
    private TextField textFieldSportsbook = new TextField();
    private TextField textFieldDate = new TextField();
    private TextField textFieldOdds = new TextField();
    private TextField textFieldMoneyStake = new TextField();
    private TextField textFieldFreeBetStake = new TextField();
    private TextField textFieldTags = new TextField();
    private TextArea textAreaLegs = new TextArea();
    private Boolean editRow = false;
    private int editRowIndex = -4;
    private Boolean canAddNewBet = true;
    public static ObservableList<Bet> allBets = getAllBets();
    public String currentFilter = "allFilterButtonClicked";
    public Timestamp currentDate = Timestamp.valueOf("3000-01-01 00:00:00");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    ObservableList<String> statusList = FXCollections.observableArrayList();
    Map<String, Label> gridLabels = new HashMap<>();
    Map<Boolean, String> labelColors = new HashMap<>();
    Map<String, Integer> statusIndex = new HashMap<>();
    Map<String, Integer> monthToInteger = new HashMap<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        betTable.setSelectionModel(null);
        allBets.addListener((ListChangeListener<Bet>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    System.out.println("Items added to list: " + change.getAddedSubList());
                    sortBets();

                }
                if (change.wasRemoved()) {
                    System.out.println("Items removed from list: " + change.getRemoved());
                    sortBets();
                }
                try {
                    Method method = this.getClass().getMethod(currentFilter);
                    method.invoke(this);
                } catch (NoSuchMethodException e) {
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                setAllTotalsGrid();
            }
        });


        labelColors.put(true, "#55ff6b");
        labelColors.put(false, "#e06666");
        statusList.addAll("pending","won","lost","void");
        for (int i = 0; i < statusList.size(); i++) {
            statusIndex.put(statusList.get(i),i);
        }
        //maps out labels to a string
        gridLabels.put("kyle_TotalTodayLabel",kyle_TotalTodayLabel);
        gridLabels.put("kyle_TotalYesterdayLabel",kyle_TotalYesterdayLabel);
        gridLabels.put("kyle_TotalProfitLabel",kyle_TotalProfitLabel);
        gridLabels.put("kyle_StakeTodayLabel",kyle_StakeTodayLabel);
        gridLabels.put("kyle_StakeYesterdayLabel",kyle_StakeYesterdayLabel);
        gridLabels.put("kyle_TotalStakedLabel",kyle_TotalStakedLabel);
        gridLabels.put("sam_TotalTodayLabel",sam_TotalTodayLabel);
        gridLabels.put("sam_TotalYesterdayLabel",sam_TotalYesterdayLabel);
        gridLabels.put("sam_TotalProfitLabel",sam_TotalProfitLabel);
        gridLabels.put("sam_StakeTodayLabel",sam_StakeTodayLabel);
        gridLabels.put("sam_StakeYesterdayLabel",sam_StakeYesterdayLabel);
        gridLabels.put("sam_TotalStakedLabel",sam_TotalStakedLabel);
        gridLabels.put("alex_TotalTodayLabel",alex_TotalTodayLabel);
        gridLabels.put("alex_TotalYesterdayLabel",alex_TotalYesterdayLabel);
        gridLabels.put("alex_TotalProfitLabel",alex_TotalProfitLabel);
        gridLabels.put("alex_StakeTodayLabel",alex_StakeTodayLabel);
        gridLabels.put("alex_StakeYesterdayLabel",alex_StakeYesterdayLabel);
        gridLabels.put("alex_TotalStakedLabel",alex_TotalStakedLabel);
        gridLabels.put("posEv_TotalTodayLabel",posEv_TotalTodayLabel);
        gridLabels.put("posEv_TotalYesterdayLabel",posEv_TotalYesterdayLabel);
        gridLabels.put("posEv_TotalProfitLabel",posEv_TotalProfitLabel);
        gridLabels.put("posEv_StakeTodayLabel",posEv_StakeTodayLabel);
        gridLabels.put("posEv_StakeYesterdayLabel",posEv_StakeYesterdayLabel);
        gridLabels.put("posEv_TotalStakedLabel",posEv_TotalStakedLabel);
        gridLabels.put("sam_roiLabel",sam_roiLabel);
        gridLabels.put("alex_roiLabel", alex_roiLabel);
        gridLabels.put("kyle_roiLabel", kyle_roiLabel);
        gridLabels.put("posEv_roiLabel", posEv_roiLabel);
        monthToInteger.put("jan", 1);
        monthToInteger.put("feb", 2);
        monthToInteger.put("mar", 3);
        monthToInteger.put("apr", 4);
        monthToInteger.put("may", 5);
        monthToInteger.put("jun", 6);
        monthToInteger.put("jul", 7);
        monthToInteger.put("aug", 8);
        monthToInteger.put("sep", 9);
        monthToInteger.put("oct", 10);
        monthToInteger.put("nov", 11);
        monthToInteger.put("dec", 12);

        textAreaLegs.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                // consume the event to prevent the default behavior of the Tab key
                event.consume();

                // move focus to the next text field
                textFieldOdds.requestFocus();
            }
        });

        betTable.setEditable(true);
        setBetTable(allBets);
        setAllTotalsGrid();

    }
    /**
     * sets bet table with all bets
     */
    public void setBetTable(ObservableList betList) {


        javafx.util.Callback<TableColumn<Bet, String>, TableCell<Bet, String>> buttonCellFactory=(param) -> {
            //https://www.youtube.com/watch?v=gvko7jLPZT0
            final TableCell<Bet,String> cell = new TableCell<Bet,String>(){
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        Bet b=getTableView().getItems().get(getIndex());
                        // Create a horizontal box layout
                        HBox hBox = new HBox();
                        hBox.setStyle("-fx-alignment: center; -fx-text-alignment: center; -fx-spacing: 5");
                        final Button editButton=new Button("Edit");
                        final Button deleteButton = new Button("Delete");
                        hBox.getChildren().addAll(editButton, deleteButton);
                        if (b.getStatus().equals("") || (editRow == true && editRowIndex == getIndex())) {
                            editButton.setText("Save");
                            editButton.setOnAction(event -> {

                                Timestamp date;
                                int odds;
                                String sportsbook = textFieldSportsbook.getText();
                                double stake = 0;
                                double freeBetStake = 0;

                                if (sportsbook.equals("")) {
                                    setError("Sportsbook cannot be blank");
                                    return;
                                }
                                try {
                                    date = Timestamp.valueOf(textFieldDate.getText() + " 00:00:00");
                                } catch (IllegalArgumentException e) {
                                    setError("Check Date");
                                    return;
                                }


                                String legs = textAreaLegs.getText();
                                try {
                                    odds = Integer.valueOf(textFieldOdds.getText());
                                } catch (NumberFormatException e) {
                                    setError("Check odds.");
                                    return;
                                }


                                if (!textFieldMoneyStake.getText().equals("")) {
                                    try {
                                        stake = Double.valueOf(textFieldMoneyStake.getText());
                                    } catch (NumberFormatException e) {
                                        stake = 0.0;
                                    }
                                }
                                if (!textFieldFreeBetStake.getText().equals("")) {
                                    try {
                                        freeBetStake = Double.valueOf(textFieldFreeBetStake.getText());
                                    } catch (NumberFormatException e) {
                                        freeBetStake = 0.0;
                                    }
                                }
                                if ((stake == 0.0) && (freeBetStake == 0.0)) {
                                    setError("money stake and free bet stake cannot both be blank or 0");
                                    return;
                                }


                                String status = String.valueOf(comboBoxStatus.getValue());
                                if (status.equals("")) {
                                    setError("Status cannot be blank");
                                    return;
                                }
                                //TODO: calc profit
                                String tags = textFieldTags.getText();

                                b.setSportsbook(sportsbook);
                                b.setDate(date);
                                b.setLegs(legs);
                                b.setOdds(odds);
                                b.setStatus(status);
                                b.setStake(stake);

                                b.setTags(tags);
                                b.setFreeBetStake(freeBetStake);

                                b.setProfit(calcProfit(b));

                                try {
                                    if (b.getBetId() > 0) {
                                        int updateBet = client.updateBet(b);
                                        if (updateBet == b.getBetId()) {
                                            setSuccess("Bet updated successfully.");
                                            sortBets();
                                        }
                                    } else {
                                        int betId = client.addBet(b);
                                        if (betId != -4) {
                                            b.setBetId(betId);
                                            sortBets();
                                            setSuccess("Bet added successfully");
                                        }
                                    }

                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                } catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }

                                clearLabels();
                                canAddNewBet = true;
                                setAllTotalsGrid();
                                editRowIndex = -4;
                                editRow = false;
                                betTable.refresh();
                                sortBets();

                            });
                            //gets bet id and sends it to server to delete
                            deleteButton.setText("Cancel");
                            deleteButton.setOnAction(event -> {
                                if (editRow == true && editRowIndex == getIndex()) {
                                    editRow = false;
                                    editRowIndex = -4;
                                    betTable.refresh();
                                } else {
                                    canAddNewBet = true;
                                    allBets.remove(b);
                                    setAllTotalsGrid();
                                }
                            });
                        } else {
                            editButton.setOnAction(event -> {
                                if (canAddNewBet == true || editRow == true) {
                                    editRow = true;
                                    editRowIndex = getIndex();
                                    betTable.refresh();
                                } else {
                                    System.out.println("Please stop adding bet first");
                                }

                            });
                            //gets bet id and sends it to server to delete
                            deleteButton.setOnAction(event -> {

                                System.out.println("Attempt to delete " + b.getBetId());
                                try {
                                    String response = client.deleteBet(b.getBetId());
                                    if (response.equals("Deleted " + b.getBetId())) {
                                        allBets.remove(b);
                                        setAllTotalsGrid();
                                        setError("Deleted successfully");
                                    } else if (response.equals("Could not delete bet: lost connection to server")) {
                                        setError("Disconnected from server. Please try restarting app.");
                                    } else {
                                        setError("Could not delete");
                                    }

                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                } catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }
                        setGraphic(hBox);
                    }
                    setText(null);
                }
            };
            cell.setStyle("-fx-background-color: none;");
            return cell;
        };

        javafx.util.Callback<TableColumn<Bet, String>, TableCell<Bet, String>> statusCellFactory=(param) -> {
            //https://www.youtube.com/watch?v=gvko7jLPZT0
            final TextFieldTableCell<Bet,String> cell = new TextFieldTableCell<>(){
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    Bet b;
                    try {
                        b = getTableView().getItems().get(getIndex());
                    } catch (IndexOutOfBoundsException e) {
                        return;
                    }
                    final String status = b.getStatus();

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else if (status.equals("")){
                        comboBoxStatus.setItems(statusList);
//
                        comboBoxStatus.setValue("pending");
                        setGraphic(comboBoxStatus);
                        setTextAlignment(TextAlignment.CENTER);
                    } else if (editRow == true && getIndex() == editRowIndex) {
                        System.out.println("EDITING status cell now please thanks");
                        comboBoxStatus.setItems(statusList);
//
                        comboBoxStatus.setValue(status);
                        setTextAlignment(TextAlignment.CENTER);
                        setAlignment(Pos.CENTER);
                        setGraphic(comboBoxStatus);
                    } else {
                        ComboBox comboBox = new ComboBox();
                        comboBox.setMinWidth(90);
                        comboBox.setMaxWidth(90);

                        comboBox.setItems(statusList);
                        comboBox.setValue(status);
                        setGraphic(comboBox);
                        comboBox.setOnAction(e -> {
                            if (!b.getStatus().equals("") && (!b.getStatus().equals(String.valueOf(comboBox.getValue())))) {
                                System.out.println(comboBox.getValue());
                                b.setStatus(String.valueOf(comboBox.getValue()));

                                b.setProfit(calcProfit(b));
                                try {
                                    int betId = client.updateBet(b);
                                    System.out.println(betId);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                } catch (ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }
                                setAllTotalsGrid();
                                betTable.refresh();
                        }
                        });
                    }
                    setTextAlignment(TextAlignment.CENTER);
                    setAlignment(Pos.CENTER);
                }
            };
            cell.setConverter(new StringConverter<String>() {
                @Override
                public String toString(String bet) {
                    return bet;
                }
                @Override
                public String fromString(String s) {
                    return s;
                }
            });
            cell.setStyle("-fx-background-color: none;");
            return cell;
        };

        javafx.util.Callback<TableColumn<Bet, String>, TableCell<Bet, String>> sportsbookCellFactory=(param) -> {
            //https://www.youtube.com/watch?v=gvko7jLPZT0
            TextFieldTableCell<Bet,String> cell = new TextFieldTableCell<Bet, String>(){


                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    Bet b;
                    try {
                        b = getTableView().getItems().get(getIndex());
                    } catch (IndexOutOfBoundsException e) {
                        return;
                    }
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else if (b.getStatus().equals("")){
                        textFieldSportsbook.setText("");
                        setStyle("-fx-alignment: center");
                        System.out.println("EDITINGstatus");
                        setGraphic(textFieldSportsbook);
                    } else if (editRow == true && getIndex() == editRowIndex) {
                        textFieldSportsbook.setText(b.getSportsbook());
                        setStyle("-fx-alignment: center");
                        System.out.println("EDITINGstatus");
                        setGraphic(textFieldSportsbook);
                    }  else {
                        final String status = b.getStatus();
                        final String sportsbook = b.getSportsbook();
                        setText(sportsbook);
                        setTextAlignment(TextAlignment.CENTER);
                        if (status.equals("won")) {
                            setStyle("-fx-text-fill: #55ff6b; -fx-alignment: center");
                        } else if (status.equals("lost")) {
                            setStyle("-fx-text-fill: #e06666; -fx-alignment: center");
                        } else if (status.equals("void")) {
                            setStyle("-fx-text-fill: yellow; -fx-alignment: center; -fx-wrap-text: true");
                        } else {
                            setStyle("-fx-text-fill: white; -fx-alignment: center");
                        }
                    }
                }
                @Override
                public void startEdit() {
                    Bet b= getTableView().getItems().get(getIndex());
                    super.startEdit();
                    TextField textField = (TextField) getGraphic();
                    textField.setText(b.getSportsbook());
                    System.out.println("EDITINGsports");
                }
            };
            cell.setStyle("-fx-background-color: none;");
            return cell;
        };


        javafx.util.Callback<TableColumn<Bet, String>, TableCell<Bet, String>> dateCellFactory=(param) -> {
            //https://www.youtube.com/watch?v=gvko7jLPZT0
            final TextFieldTableCell<Bet,String> cell = new TextFieldTableCell<>(){
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    Bet b;
                    try {
                        b = getTableView().getItems().get(getIndex());
                    } catch (IndexOutOfBoundsException e) {
                        return;
                    }

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else if (b.getStatus().equals("")){
                        setStyle("-fx-alignment: center");
                        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                        textFieldDate.setText(String.valueOf(currentTime.toLocalDateTime().toLocalDate()));
                        textFieldDate.setPromptText("yyyy-mm-dd");
                        setGraphic(textFieldDate);
                    } else if (editRow == true && getIndex() == editRowIndex) {
                        LocalDate tempDate = b.getDate().toLocalDateTime().toLocalDate();
                        String dateString = tempDate.toString();
                        textFieldDate.setText(dateString);
                        textFieldDate.setPromptText("yyyy-mm-dd");
                        setStyle("-fx-alignment: center");
                        setGraphic(textFieldDate);
                    } else {
                        final LocalDate tempDate = b.getDate().toLocalDateTime().toLocalDate();
                        final String dateString = tempDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
                        setText(dateString);
                        setTextAlignment(TextAlignment.CENTER);
                        final String status = b.getStatus();
                        if (status.equals("won")) {
                            setStyle("-fx-text-fill: #55ff6b; -fx-alignment: center");
                        } else if (status.equals("lost")) {
                            setStyle("-fx-text-fill: #e06666; -fx-alignment: center");
                        } else if (status.equals("void")) {
                            setStyle("-fx-text-fill: yellow; -fx-alignment: center; -fx-wrap-text: true");
                        } else {
                            setStyle("-fx-text-fill: white; -fx-alignment: center");
                        }
                    }
                }
                @Override
                public void startEdit() {
                    super.startEdit();
                    Bet b= getTableView().getItems().get(getIndex());
                    TextField textField = (TextField) getGraphic();
                    Timestamp betDate = b.getDate();
                    String formattedTimestamp = betDate.toLocalDateTime().format(formatter);
                    textField.setText(formattedTimestamp);
                    }

            };
            cell.setStyle("-fx-background-color: none;");
            return cell;
        };
        javafx.util.Callback<TableColumn<Bet, String>, TableCell<Bet, String>> legCellFactory=(param) -> {
            //https://www.youtube.com/watch?v=gvko7jLPZT0

            final TableCell<Bet,String> cell = new TableCell<>(){
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    Bet b;
                    try {
                        b = getTableView().getItems().get(getIndex());
                    } catch (IndexOutOfBoundsException e) {
                        return;
                    }

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else if (b.getStatus().equals("")){
                        textAreaLegs.setWrapText(true);
                        textAreaLegs.setMaxHeight(60.00);
                        textAreaLegs.setMaxWidth(355.00);
                        setGraphic(textAreaLegs);
                    } else if (editRow == true && getIndex() == editRowIndex) {
                        textAreaLegs.setText(b.getLegs());
                        textAreaLegs.setWrapText(true);
                        textAreaLegs.setMaxHeight(60.00);
                        textAreaLegs.setMaxWidth(355.00);
                        setGraphic(textAreaLegs);
                    }else {
                        final String status = b.getStatus();
                        final String legs = b.getLegs();
                        Label label = new Label();
                        label.setText(legs);
                        label.setWrapText(true);
                        label.setMaxWidth(345);
                        label.setMinWidth(345);
                        label.setMinHeight(50);
                        label.setTextAlignment(TextAlignment.CENTER);
                        label.setAlignment(Pos.CENTER);


                        if (status.equals("won")) {
                            label.setStyle("-fx-text-fill: #55ff6b; -fx-alignment: center; -fx-wrap-text: true; -fx-text-alignment: center");
                        } else if (status.equals("lost")) {
                            label.setStyle("-fx-text-fill: #e06666; -fx-alignment: center; -fx-wrap-text: true; -fx-text-alignment: center");
                        } else if (status.equals("void")) {
                            label.setStyle("-fx-text-fill: yellow; -fx-alignment: center; -fx-wrap-text: true; -fx-text-alignment: center");
                        } else {
                            label.setStyle("-fx-text-fill: white; -fx-alignment: center; -fx-wrap-text: true;  -fx-text-alignment: center");
                        }

                        ScrollPane scrollPane = new ScrollPane();
                        scrollPane.setContent(label);
                        scrollPane.setPrefSize(340, 60);
                        scrollPane.setStyle("-fx-base: transparent; -fx-background-color: transparent; -fx-padding: 0;");



                        ScrollBar scrollbar = new ScrollBar();
                        scrollbar.setOrientation(Orientation.VERTICAL);
                        scrollbar.setManaged(false);
                        scrollbar.setVisible(false);





                        // Wrap the content of the scroll pane in a new pane
                        Pane contentPane = new Pane(scrollPane.getContent());

                        contentPane.setMaxWidth(320);
                        contentPane.setMinWidth(320);
                        contentPane.setStyle("-fx-background-color: transparent;");



                        // Hide the vertical scrollbar of the scroll pane
                        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

                        // Add the custom scrollbar to the content pane
                        //                        contentPane.getChildren().add(scrollbar);

                        // Bind the custom scrollbar to the scroll pane's viewport
                        scrollbar.valueProperty().bindBidirectional(scrollPane.vvalueProperty());
                        scrollbar.visibleProperty().bind(scrollPane.vmaxProperty().greaterThan(scrollPane.vminProperty()));

                        // Set the content of the scroll pane to the wrapped content pane
                        scrollPane.setContent(contentPane);

                        scrollPane.setOnMousePressed( event -> {
                            event.consume();
                        });
                        contentPane.setOnMousePressed( event -> {
                            event.consume();
                        });
                        label.setOnMousePressed( event -> {
                            event.consume();
                        });
                        setOnMousePressed( event -> {
                            event.consume();
                        });

                        setStyle("-fx-background-color: transparent");

                        if (label.getText().split("\n").length < 4) {
                            scrollPane.setPannable(false);
                            scrollPane.setMouseTransparent(true);
                        }
                        setGraphic(scrollPane);

                    }
                }
                @Override
                public void startEdit() {
                    super.startEdit();
                    Bet b= getTableView().getItems().get(getIndex());
                    System.out.println("BETID " + b.getBetId());
                    TextArea textArea = new TextArea();
                    textArea.setText(b.getLegs());
                    textArea.setWrapText(true);
                    textArea.setMaxHeight(60.00);
                    textArea.setMaxWidth(355.00);
                    setGraphic(textArea);
                    textArea.positionCaret(textArea.getLength());
                    textArea.requestFocus();
                    System.out.println("EDITING: " + b.getLegs());
                }
            };
            cell.setStyle("-fx-background-color: none;");
            return cell;
        };
        javafx.util.Callback<TableColumn<Bet, String>, TableCell<Bet, String>> oddsCellFactory=(param) -> {
            //https://www.youtube.com/watch?v=gvko7jLPZT0


            final TextFieldTableCell<Bet,String> cell = new TextFieldTableCell<>(){
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    Bet b;
                    try {
                        b = getTableView().getItems().get(getIndex());
                    } catch (IndexOutOfBoundsException e) {
                        return;
                    }

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else if (b.getStatus().equals("")){
                        setStyle("-fx-alignment: center");
                        setGraphic(textFieldOdds);


                    } else if (editRow == true && getIndex() == editRowIndex) {
                        textFieldOdds.setText(String.valueOf(b.getOdds()));
                        setStyle("-fx-alignment: center");
                        setGraphic(textFieldOdds);
                    }else {
                        final String status = b.getStatus();
                        final int odds = b.getOdds();
                        setText(String.valueOf(odds));
                        setTextAlignment(TextAlignment.CENTER);
                        if (status.equals("won")) {
                            setStyle("-fx-text-fill: #55ff6b; -fx-alignment: center");
                        } else if (status.equals("lost")) {
                            setStyle("-fx-text-fill: #e06666; -fx-alignment: center");
                        } else if (status.equals("void")) {
                            setStyle("-fx-text-fill: yellow; -fx-alignment: center; -fx-wrap-text: true");
                        } else {
                            setStyle("-fx-text-fill: white; -fx-alignment: center");
                        }
                    }

                }
                @Override
                public void startEdit() {
                    super.startEdit();
                    Bet b= getTableView().getItems().get(getIndex());

                    TextField textField = (TextField) getGraphic();
                    textField.setText(String.valueOf(b.getOdds()));
                    System.out.println("EDITINGodds");
                }
            };
            cell.setStyle("-fx-background-color: none;");
            return cell;
        };

        javafx.util.Callback<TableColumn<Bet, String>, TableCell<Bet, String>> stakeCellFactory=(param) -> {
            //https://www.youtube.com/watch?v=gvko7jLPZT0
            final TextFieldTableCell<Bet,String> cell = new TextFieldTableCell<Bet,String>(){
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    Bet b;
                    try {
                        b = getTableView().getItems().get(getIndex());
                    } catch (IndexOutOfBoundsException e) {
                        return;
                    }

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else if (b.getStatus().equals("")){
                        HBox hbox1 = new HBox();
                        Label label1 = new Label();
                        label1.setText("M:");
                        label1.setMinWidth(30);
                        label1.setTextAlignment(TextAlignment.CENTER);
                        label1.setStyle("-fx-text-fill: white");
                        hbox1.setAlignment(Pos.CENTER);
                        hbox1.getChildren().addAll(label1, textFieldMoneyStake);



                        HBox hbox2 = new HBox();
                        Label label2 = new Label();
                        label2.setText("FB:");
                        label2.setMinWidth(30);
                        label2.setTextAlignment(TextAlignment.CENTER);
                        label2.setStyle("-fx-text-fill: white");
                        hbox2.setAlignment(Pos.CENTER);

                        hbox2.getChildren().addAll(label2, textFieldFreeBetStake);

                        VBox vbox = new VBox();
                        vbox.setMinHeight(60);
                        vbox.setMaxHeight(60);


                        vbox.getChildren().addAll(hbox1, hbox2);
                        vbox.setSpacing(10);
                        setGraphic(vbox);
                    } else if (editRow == true && getIndex() == editRowIndex) {
//
                        HBox hbox1 = new HBox();
//
                        Label label1 = new Label();
                        label1.setText("M:");
                        label1.setMinWidth(26);
                        label1.setTextAlignment(TextAlignment.CENTER);
                        label1.setStyle("-fx-text-fill: white");
                        hbox1.setAlignment(Pos.CENTER);
                        textFieldMoneyStake.setText(String.valueOf(b.getStake()));
                        hbox1.getChildren().addAll(label1, textFieldMoneyStake);



                        HBox hbox2 = new HBox();
                        Label label2 = new Label();
                        label2.setText("FB:");
                        label2.setMinWidth(26);
                        label2.setTextAlignment(TextAlignment.CENTER);
                        label2.setStyle("-fx-text-fill: white");
                        hbox2.setAlignment(Pos.CENTER);
                        textFieldFreeBetStake.setText(String.valueOf(b.getFreeBetStake()));
                        hbox2.getChildren().addAll(label2, textFieldFreeBetStake);

                        VBox vbox = new VBox();
                        vbox.setMinHeight(50);
                        vbox.setMaxHeight(50);


                        vbox.getChildren().addAll(hbox1, hbox2);
                        vbox.setSpacing(5);
                        setGraphic(vbox);
                    } else {

                        final double rowOne = b.getStake();
                        final double rowTwo = b.getFreeBetStake();
                        String stakeString = "";
                        if (rowOne != 0.0) {
                            stakeString = stakeString + "M: $" + String.format("%.2f",rowOne);
                        }
                        if (rowTwo != 0.0) {
                            if (stakeString == "") {
                                stakeString = stakeString + "FB: $" + String.format("%.2f",rowTwo);
                            } else {
                                stakeString = stakeString + "\n" + "FB: $" + String.format("%.2f",rowTwo);
                            }
                        }
                        setText(stakeString);
                        setTextAlignment(TextAlignment.CENTER);
                        final String status = b.getStatus();
                        if (status.equals("won")) {
                            setStyle("-fx-text-fill: #55ff6b; -fx-alignment: center");
                        } else if (status.equals("lost")) {
                            setStyle("-fx-text-fill: #e06666; -fx-alignment: center");
                        } else if (status.equals("void")) {
                            setStyle("-fx-text-fill: yellow; -fx-alignment: center; -fx-wrap-text: true");
                        } else {
                            setStyle("-fx-text-fill: white; -fx-alignment: center");
                        }
                    }
                }
                @Override
                public void startEdit() {
                    super.startEdit();

                    Bet b = getTableView().getItems().get(getIndex());
                    HBox hbox1 = new HBox();
                    TextField textField = (TextField) getGraphic();
                    textField.setText(String.valueOf(b.getStake()));
                    Label label1 = new Label();
                    label1.setText("M:");
                    label1.setMinWidth(30);
                    label1.setTextAlignment(TextAlignment.CENTER);
                    label1.setStyle("-fx-text-fill: white");
                    hbox1.setAlignment(Pos.CENTER);
                    hbox1.getChildren().addAll(label1, textField);


                    HBox hbox2 = new HBox();
                    TextField textField2 = new TextField();
                    Label label2 = new Label();
                    label2.setText("FB:");
                    label2.setMinWidth(30);
                    label2.setTextAlignment(TextAlignment.CENTER);
                    label2.setStyle("-fx-text-fill: white");
                    hbox2.setAlignment(Pos.CENTER);

                    hbox2.getChildren().addAll(label2, textField2);
                    textField2.setText(String.valueOf(b.getFreeBetStake()));

                    VBox vbox = new VBox();
                    vbox.setMinHeight(70);


                    vbox.getChildren().addAll(hbox1, hbox2);
                    vbox.setSpacing(20);
                    setGraphic(vbox);
                    System.out.println("EDITINGvox");
                    textField.requestFocus();
                    textField.positionCaret(textField.getLength());
                }
                @Override
                public void cancelEdit() {


                    System.out.println("TEST CANCEL: " + getText());
                    super.cancelEdit();
                }
            };

            cell.setStyle("-fx-background-color: none;");
            return cell;
        };
        javafx.util.Callback<TableColumn<Bet, String>, TableCell<Bet, String>> tagsCellFactory=(param) -> {
            //https://www.youtube.com/watch?v=gvko7jLPZT0

            final TextFieldTableCell<Bet,String> cell = new TextFieldTableCell<>(){
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    Bet b;
                    try {
                        b = getTableView().getItems().get(getIndex());
                    } catch (IndexOutOfBoundsException e) {
                        return;
                    }

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else if (b.getStatus().equals("")){
                        setStyle("-fx-alignment: center");
                        setGraphic(textFieldTags);
                    } else if (editRow == true && getIndex() == editRowIndex) {
                        textFieldTags.setText(b.getTags());
                        setStyle("-fx-alignment: center");
                        setGraphic(textFieldTags);
                    } else {

                        final String status = b.getStatus();
                        final String tags = b.getTags();
                        setText(tags);
                        setTextAlignment(TextAlignment.CENTER);
                        if (status.equals("won")) {
                            setStyle("-fx-text-fill: #55ff6b; -fx-alignment: center");
                        } else if (status.equals("lost")) {
                            setStyle("-fx-text-fill: #e06666; -fx-alignment: center");
                        } else if (status.equals("void")) {
                            setStyle("-fx-text-fill: yellow; -fx-alignment: center; -fx-wrap-text: true");
                        } else {
                            setStyle("-fx-text-fill: white; -fx-alignment: center");
                        }
                    }
                }
                @Override
                public void startEdit() {
                    super.startEdit();
                    Bet b= getTableView().getItems().get(getIndex());
                    TextField textField = (TextField) getGraphic();
                    textField.setText(b.getTags());
                    System.out.println("EDITINGtags");
                }



            };
            cell.setStyle("-fx-background-color: none;");
            return cell;
        };
        javafx.util.Callback<TableColumn<Bet, String>, TableCell<Bet, String>> profitCellFactory=(param) -> {
            //https://www.youtube.com/watch?v=gvko7jLPZT0
            final TextFieldTableCell<Bet,String> cell = new TextFieldTableCell<Bet,String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Bet b = getTableView().getItems().get(getIndex());
                        if (b.getProfit() != 0.0) {
                            final double rowOne = b.getProfit();
                            String stakeString = "";
                            if (rowOne != 0.0) {
                                stakeString = "$" + String.format("%.2f", rowOne);
                            }
                            if (b.getStatus().equals("won")) {
                                setStyle("-fx-text-fill: #55ff6b; -fx-alignment: center;");
                            } else if (b.getStatus().equals("lost")) {
                                setStyle("-fx-text-fill: #e06666; -fx-alignment: center;");
                            }

                            setText(stakeString);
                            setTextAlignment(TextAlignment.CENTER);
                        } else {
                            setTextAlignment(TextAlignment.CENTER);
                            setStyle("-fx-alignment: center;");
                        }

                    }

                }

            }

                ;
            cell.setStyle("-fx-background-color: none;");
            return cell;

        };

        betTable.setRowFactory(tv -> new TableRow<Bet>() {


            @Override
            protected void updateItem(Bet item, boolean empty) {
                super.updateItem(item, empty);
                if (getIndex() % 2 == 0) {
                    setStyle("-fx-background-color: #111111;"); //#161618

                } else {
                    setStyle("-fx-background-color: #333333;");
                }

            }

        });
        betTable.setItems(betList);
        betIdColumn.setCellFactory(statusCellFactory);
        sportsbookColumn.setCellFactory(sportsbookCellFactory);
        dateColumn.setCellFactory(dateCellFactory);
        betLegColumn.setCellFactory(legCellFactory);
        oddsColumn.setCellFactory(oddsCellFactory);
        stakeColumn.setCellFactory(stakeCellFactory);
        profitColumn.setCellFactory(profitCellFactory);
        tagsColumn.setCellFactory(tagsCellFactory);
        editColumn.setCellFactory(buttonCellFactory);
    }
    /**
     * gets profit of current month from tag and sets it in the label
     * @param tagString string that will filter your bets and get all bets based off of certain tag
     */
    public void setProfitMonth(String tagString) throws SQLException {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        LocalDateTime currentTimeDate = currentTime.toLocalDateTime();
        YearMonth yearMonth = YearMonth.of(currentTimeDate.getYear(), currentTimeDate.getMonthValue());
        LocalDateTime lastDayLocal = yearMonth.atEndOfMonth().atTime(11, 59, 00);
        LocalDateTime firstDayLocal = yearMonth.atDay(1).atTime(0, 0, 0);
        Timestamp firstDay = Timestamp.valueOf(firstDayLocal);
        Timestamp lastDay = Timestamp.valueOf(lastDayLocal);
        String finalTagString = tagString;
        FilteredList<Bet> monthBets = new FilteredList<Bet>(allBets, p -> true);
        Predicate<Bet> firstFilter = s -> s.getDate().before(lastDay);
        Predicate<Bet> secondFilter = s -> (s.getDate().after(firstDay) || s.getDate().equals(firstDay));
        Predicate<Bet> thirdFilter = s -> (s.getTags().toLowerCase().contains(finalTagString) || s.getTags().toLowerCase().equals(finalTagString));
        monthBets.setPredicate(secondFilter.and(firstFilter).and(thirdFilter));

        double profit = 0;
        double staked = 0;
        for (Bet bet: monthBets) {
            profit = profit + bet.getProfit();
            staked = staked + bet.getStake();
        }
        if (tagString == "pos-ev") {
            tagString = "posEv";
        }
        String profitString = tagString + "_TotalProfitLabel";
        Label profitLabel = gridLabels.get(profitString);
        profitLabel.setText(String.format("$%,.2f",profit));
        String monthStakedLabel = tagString + "_TotalStakedLabel";
        Label monthStaked = gridLabels.get(monthStakedLabel);
        monthStaked.setText(String.format("$%,.2f",staked));
        String profitColor = "-fx-text-fill: " + labelColors.get(profit>0);
        profitLabel.setStyle(profitColor);

        String roiString = tagString + "_roiLabel";
        Label roiLabel = gridLabels.get(roiString);
        double roi = (profit / staked) * 100;
        roiLabel.setText(String.format("%.2f",roi)+"%");
    }
    /**
     * sets all totals for the grid on top on window
     */
    public void setAllTotalsGrid() {
        System.out.println("Updating totals");
        try {
            setProfitMonth("pos-ev");
            setProfitMonth("kyle");
            setProfitMonth("sam");
            setProfitMonth("alex");
            setProfitToday("pos-ev");
            setProfitToday("kyle");
            setProfitToday("sam");
            setProfitToday("alex");
            setProfitYesterday("pos-ev");
            setProfitYesterday("kyle");
            setProfitYesterday("sam");
            setProfitYesterday("alex");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setTodayTop();
        setMonthTop();
        setYesterdayTop();
        setTotalTop();
    }
    /**
     * gets profit of current day from tag and sets it in the label
     * @param tagString string that will filter your bets and get all bets based off of certain tag
     */
    public void setProfitToday(String tagString) throws SQLException{

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        LocalDateTime currentTimeDate = currentTime.toLocalDateTime().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = currentTimeDate.withHour(23).withMinute(59).withSecond(59);
        Timestamp todayStamp = Timestamp.valueOf(currentTimeDate);
        Timestamp endOfDayStamp = Timestamp.valueOf(endOfDay);

        String finalTagString = tagString;
        FilteredList<Bet> dayProfitList = new FilteredList<Bet>(allBets, p -> true);
        Predicate<Bet> firstFilter = s -> s.getDate().before(endOfDayStamp);
        Predicate<Bet> secondFilter = s -> (s.getDate().after(todayStamp) || s.getDate().equals(todayStamp));
        Predicate<Bet> thirdFilter = s -> (s.getTags().toLowerCase().contains(finalTagString) || s.getTags().toLowerCase().equals(finalTagString));
        dayProfitList.setPredicate(secondFilter.and(firstFilter).and(thirdFilter));

        double profit = 0;
        double staked = 0;
        for (Bet bet: dayProfitList) {
            profit = profit + bet.getProfit();
            staked = staked + bet.getStake();
        }
        if (tagString == "pos-ev") {
            tagString = "posEv";
        }
        String profitString = tagString + "_TotalTodayLabel";
        Label profitLabel = gridLabels.get(profitString);
        profitLabel.setText(String.format("$%,.2f",profit));
        String stakedString = tagString + "_StakeTodayLabel";
        Label stakedLabel = gridLabels.get(stakedString);
        stakedLabel.setText(String.format("$%,.2f",staked));
        String profitColor = "-fx-text-fill: " + labelColors.get(profit>0);
        profitLabel.setStyle(profitColor);
        System.out.println(tagString + ": profit today " + profit);
    }
    /**
     * gets profit of yesterday from tag and sets it in the label
     * @param tagString string that will filter your bets and get all bets based off of certain tag
     */
    public void setProfitYesterday(String tagString) throws SQLException{
        
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        LocalDateTime currentTimeDate = currentTime.toLocalDateTime().withHour(0).withMinute(0).withSecond(0).withNano(0).minusDays(1);
        LocalDateTime endOfDay = currentTimeDate.withHour(23).withMinute(59).withSecond(59);
        Timestamp yesterdayStamp = Timestamp.valueOf(currentTimeDate);
        Timestamp endOfDayStamp = Timestamp.valueOf(endOfDay);

        String finalTagString = tagString;
        FilteredList<Bet> dayProfitList = new FilteredList<Bet>(allBets, p -> true);
        Predicate<Bet> firstFilter = s -> s.getDate().before(endOfDayStamp);
        Predicate<Bet> secondFilter = s -> (s.getDate().after(yesterdayStamp) || s.getDate().equals(yesterdayStamp));
        Predicate<Bet> thirdFilter = s -> (s.getTags().toLowerCase().contains(finalTagString) || s.getTags().toLowerCase().equals(finalTagString));
        dayProfitList.setPredicate(secondFilter.and(firstFilter).and(thirdFilter));

        double profit = 0;
        double staked = 0;
        for (Bet bet: dayProfitList) {
            profit = profit + bet.getProfit();
            staked = staked + bet.getStake();
        }
        if (tagString == "pos-ev") {
            tagString = "posEv";
        }
        String profitString = tagString + "_TotalYesterdayLabel";
        Label profitLabel = gridLabels.get(profitString);
        profitLabel.setText(String.format("$%,.2f",profit));
        String stakedString = tagString + "_StakeYesterdayLabel";
        Label stakedLabel = gridLabels.get(stakedString);
        stakedLabel.setText(String.format("$%,.2f",staked));
        String profitColor = "-fx-text-fill: " + labelColors.get(profit>0);
        profitLabel.setStyle(profitColor);
    }
    /**
     * gets all bets from server
     * @return all bets
     */
    public static ObservableList<Bet> getAllBets() {
        ObservableList allBets = FXCollections.observableArrayList();
        try {
            for (Object bet: client.getAllBets()) {
                allBets.add(bet);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<String> order = new ArrayList<>();
        order.add("new");
        order.add("pending");

        Comparator<Bet> byStatus = (o1, o2) -> {
            int index1 = order.indexOf(o1.getStatus());
            int index2 = order.indexOf(o2.getStatus());

            return Integer.compare(index2,index1);
        };
        allBets.sort(Comparator.comparing(Bet::getDate).reversed().thenComparing(byStatus));

        return allBets;
    }

    /**
     * add a blank Bet object to the row with text fields to be able to add the bet. Can only have 1 bet added at a time.
     */
    public void addBetClicked() {
        Bet item = (Bet) betTable.getItems().get(0);

        System.out.println(item.getDate());
        clearLabels();

        Bet newBet = new Bet(-1, "", currentDate, "", -123456789,
                "", 00.00, 00.00, "", 00.00, 00.00,
                00.00, 00.00, 0);

        if (canAddNewBet) {
            if (editRow == true) {
                editRow = false;
                editRowIndex = -4;
                clearLabels();
            }

            allBets.add(newBet);
            canAddNewBet = false;
        }
    }

    /**
     * calculates profit based off of stake/odds from Bet b
     * @param b Bet object
     */
    public double calcProfit(Bet b) {
        String status = b.getStatus();
        double profit = 0.0;
        int odds = b.getOdds();
        double stake = b.getStake();
        double freeBetStake = b.getFreeBetStake();
        if (status.equals("lost")) {
            profit = stake * -1;
        } else if (status.equals("won")) {
            if (odds > 0) {
                profit = (odds / 100.00) * stake +
                        (odds /  100.00) * freeBetStake;
            } else {
                profit = (100.00 / Math.abs(odds) * stake) +
                        (100.00 / Math.abs(odds) * freeBetStake);
            }
        }
        return profit;
    }
    /**
     * sets bet table to show all bets
     */
    public void allFilterButtonClicked() {
        betTable.refresh();
        betTable.setItems(allBets);
        currentFilter = "allFilterButtonClicked";
        Bet item = (Bet) betTable.getItems().get(0);
        if (item != null) {
            LocalDateTime localDateTime = item.getDate().toLocalDateTime().plusSeconds(1);

            currentDate = Timestamp.valueOf(localDateTime);
        } else {
            Timestamp.valueOf("3000-01-01 00:00:00");
        }

    }
    /**
     * sets bet table to show all bets from previous month
     */
    public void lastMonthFilterButtonClicked() {
        betTable.refresh();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        LocalDateTime currentTimeDate = currentTime.toLocalDateTime();
        YearMonth yearMonth = YearMonth.of(currentTimeDate.getYear(), currentTimeDate.getMonthValue()-1);
        LocalDateTime lastDayLocal = yearMonth.atEndOfMonth().atTime(11, 59, 00);
        LocalDateTime firstDayLocal = yearMonth.atDay(1).atTime(0, 0, 0);
        Timestamp firstDay = Timestamp.valueOf(firstDayLocal);
        Timestamp lastDay = Timestamp.valueOf(lastDayLocal);

        FilteredList<Bet> lastMonthList = new FilteredList<Bet>(allBets, p -> true);
        Predicate<Bet> firstFilter = s -> s.getDate().before(lastDay);
        Predicate<Bet> secondFilter = s -> (s.getDate().after(firstDay) || s.getDate().equals(firstDay));
        lastMonthList.setPredicate(secondFilter.and(firstFilter));

        betTable.setItems(lastMonthList);
        currentFilter = "lastMonthFilterButtonClicked";
        currentDate = Timestamp.valueOf(lastDayLocal.minusMinutes(1));
    }
    /**
     * sets bet table to show all bets for current month
     */
    public void currentMonthFilterButtonClicked() {
        betTable.refresh();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        LocalDateTime currentTimeDate = currentTime.toLocalDateTime();
        YearMonth yearMonth = YearMonth.of(currentTimeDate.getYear(), currentTimeDate.getMonthValue());
        LocalDateTime lastDayLocal = yearMonth.atEndOfMonth().atTime(11, 59, 00);
        LocalDateTime firstDayLocal = yearMonth.atDay(1).atTime(0, 0, 0);
        Timestamp firstDay = Timestamp.valueOf(firstDayLocal);
        Timestamp lastDay = Timestamp.valueOf(lastDayLocal);

        FilteredList<Bet> currentMonthList = new FilteredList<Bet>(allBets, p -> true);
        Predicate<Bet> firstFilter = s -> s.getDate().before(lastDay);
        Predicate<Bet> secondFilter = s -> (s.getDate().after(firstDay) || s.getDate().equals(firstDay));
        currentMonthList.setPredicate(secondFilter.and(firstFilter));
        betTable.setItems(currentMonthList);
        currentFilter = "currentMonthFilterButtonClicked";
        currentDate = Timestamp.valueOf(lastDayLocal.minusMinutes(1));
    }
    /**
     * sets bet table to show all bets for next month
     */
    public void nextMonthFilterButton() {
        betTable.refresh();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        LocalDateTime currentTimeDate = currentTime.toLocalDateTime();
        YearMonth yearMonth = YearMonth.of(currentTimeDate.getYear(), currentTimeDate.getMonthValue()+1);
        LocalDateTime lastDayLocal = yearMonth.atEndOfMonth().atTime(11, 59, 00);
        LocalDateTime firstDayLocal = yearMonth.atDay(1).atTime(0, 0, 0);
        Timestamp firstDay = Timestamp.valueOf(firstDayLocal);
        Timestamp lastDay = Timestamp.valueOf(lastDayLocal);

        FilteredList<Bet> nextMonthList = new FilteredList<Bet>(allBets, p -> true);
        Predicate<Bet> firstFilter = s -> s.getDate().before(lastDay);
        Predicate<Bet> secondFilter = s -> (s.getDate().after(firstDay) || s.getDate().equals(firstDay));
        nextMonthList.setPredicate(secondFilter.and(firstFilter));
        betTable.setItems(nextMonthList);
        currentFilter = "nextMonthFilterButton";
        currentDate = Timestamp.valueOf(lastDayLocal.minusMinutes(1));
    }
    /**
     * sets bet table to show all bets from current week
     */
    public void weekFilterButtonClicked() {
        betTable.refresh();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        LocalDate startTimeDate = currentTime.toLocalDateTime().toLocalDate();
        LocalDate endTimeDate = currentTime.toLocalDateTime().toLocalDate();

        startTimeDate.minusDays(1);
        while (startTimeDate.getDayOfWeek() != DayOfWeek.MONDAY){
            startTimeDate = startTimeDate.minusDays(1);
        }
        while (endTimeDate.getDayOfWeek() != DayOfWeek.SUNDAY){
            endTimeDate = endTimeDate.plusDays(1);
        }
        Timestamp endDateAndTime = Timestamp.valueOf(String.valueOf(endTimeDate)+" "+"11:59:00");
        Timestamp startDateAndTime = Timestamp.valueOf(String.valueOf(startTimeDate)+" "+"0:0:00");
        FilteredList<Bet> currentWeekList = new FilteredList<Bet>(allBets, p -> true);
        Predicate<Bet> firstFilter = s -> s.getDate().before(endDateAndTime);
        Predicate<Bet> secondFilter = s -> (s.getDate().after(startDateAndTime) || s.getDate().equals(startDateAndTime));
        currentWeekList.setPredicate(secondFilter.and(firstFilter));
        betTable.setItems(currentWeekList);
        currentFilter = "weekFilterButtonClicked";
        currentDate = Timestamp.valueOf(String.valueOf(endTimeDate)+" "+"11:50:00");


    }
    /**
     * sets bet table to show all bets for tomorrow
     */
    public void tomorrowFilterButtonClicked() {
        betTable.refresh();

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        LocalDateTime currentTimeDate = currentTime.toLocalDateTime().withHour(0).withMinute(0).withSecond(0).withNano(0).plusDays(1);
        LocalDateTime endOfDay = currentTimeDate.withHour(23).withMinute(59).withSecond(59);
        Timestamp todayStamp = Timestamp.valueOf(currentTimeDate);
        Timestamp endOfDayStamp = Timestamp.valueOf(endOfDay);

        FilteredList<Bet> tomorrowList = new FilteredList<Bet>(allBets, p -> true);
        Predicate<Bet> firstFilter = s -> s.getDate().before(endOfDayStamp);
        Predicate<Bet> secondFilter = s -> (s.getDate().after(todayStamp) || s.getDate().equals(todayStamp));
        tomorrowList.setPredicate(secondFilter.and(firstFilter));
        betTable.setItems(tomorrowList);
        currentFilter = "tomorrowFilterButtonClicked";
        currentDate = Timestamp.valueOf(endOfDay.minusMinutes(1));
    }
    /**
     * sets bet table to show all bets from yesterday
     */
    public void yesterdayFilterButton() {
        betTable.refresh();
        System.out.println("YSEASDFJKASDAJKSDF");

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        LocalDateTime currentTimeDate = currentTime.toLocalDateTime().withHour(0).withMinute(0).withSecond(0).withNano(0).minusDays(1);
        LocalDateTime endOfDay = currentTimeDate.withHour(23).withMinute(59).withSecond(59);
        Timestamp yesterdayStamp = Timestamp.valueOf(currentTimeDate);
        Timestamp endOfDayStamp = Timestamp.valueOf(endOfDay);
        FilteredList<Bet> yesterdayList = new FilteredList<Bet>(allBets, p -> true);
        Predicate<Bet> firstFilter = s -> s.getDate().before(endOfDayStamp);
        Predicate<Bet> secondFilter = s -> (s.getDate().after(yesterdayStamp) || s.getDate().equals(yesterdayStamp));
        yesterdayList.setPredicate(secondFilter.and(firstFilter));
        betTable.setItems(yesterdayList);
        currentFilter = "yesterdayFilterButton";
        currentDate = Timestamp.valueOf(endOfDay.minusMinutes(1));

    }
    /**
     * sets bet table to show all bets for today
     */
    public void todayFilterButtonClicked() {
        betTable.refresh();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        LocalDateTime currentTimeDate = currentTime.toLocalDateTime().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = currentTimeDate.withHour(23).withMinute(59).withSecond(59);
        Timestamp todayStamp = Timestamp.valueOf(currentTimeDate);
        Timestamp endOfDayStamp = Timestamp.valueOf(endOfDay);

        FilteredList<Bet> todayList = new FilteredList<Bet>(allBets, p -> true);
        Predicate<Bet> firstFilter = s -> s.getDate().before(endOfDayStamp);
        Predicate<Bet> secondFilter = s -> (s.getDate().after(todayStamp) || s.getDate().equals(todayStamp));
        todayList.setPredicate(secondFilter.and(firstFilter));


        betTable.setItems(todayList);
        currentFilter = "todayFilterButtonClicked";
        currentDate = Timestamp.valueOf(endOfDay.minusMinutes(1));
    }
    /**
     * sets yesterday's profit/stake and ROI
     */
    public void setYesterdayTop() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        LocalDateTime currentTimeDate = currentTime.toLocalDateTime().withHour(0).withMinute(0).withSecond(0).withNano(0).minusDays(1);
        LocalDateTime endOfDay = currentTimeDate.withHour(23).withMinute(59).withSecond(59);
        Timestamp yesterdayStamp = Timestamp.valueOf(currentTimeDate);
        Timestamp endOfDayStamp = Timestamp.valueOf(endOfDay);
        
        FilteredList<Bet> dayProfitList = new FilteredList<Bet>(allBets, p -> true);
        Predicate<Bet> firstFilter = s -> s.getDate().before(endOfDayStamp);
        Predicate<Bet> secondFilter = s -> (s.getDate().after(yesterdayStamp) || s.getDate().equals(yesterdayStamp));
        dayProfitList.setPredicate(secondFilter.and(firstFilter));

        double profit = 0;
        double staked = 0;
        for (Bet bet: dayProfitList) {
            profit = profit + bet.getProfit();
            staked = staked + bet.getStake();
        }
        
        yesterdayProfitLabel.setText(String.format("$%,.2f",profit));
        yesterdayStakeLabel.setText(String.format("$%,.2f",staked));
        String profitColor = "-fx-text-fill: " + labelColors.get(profit>0);
        yesterdayProfitLabel.setStyle(profitColor);
        double roi = (profit / staked) * 100;
        yesterdayROILabel.setText(String.format("%.2f",roi)+"%");
    }
    /**
     * sets today's profit/stake and ROI
     */
    public void setTodayTop() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        LocalDateTime currentTimeDate = currentTime.toLocalDateTime().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = currentTimeDate.withHour(23).withMinute(59).withSecond(59);
        Timestamp todayStamp = Timestamp.valueOf(currentTimeDate);
        Timestamp endOfDayStamp = Timestamp.valueOf(endOfDay);

        FilteredList<Bet> dayProfitList = new FilteredList<Bet>(allBets, p -> true);
        Predicate<Bet> firstFilter = s -> s.getDate().before(endOfDayStamp);
        Predicate<Bet> secondFilter = s -> (s.getDate().after(todayStamp) || s.getDate().equals(todayStamp));
        dayProfitList.setPredicate(secondFilter.and(firstFilter));

        double profit = 0;
        double staked = 0;
        for (Bet bet: dayProfitList) {
            profit = profit + bet.getProfit();
            staked = staked + bet.getStake();
        }

        
        todayProfitLabel.setText(String.format("$%,.2f",profit));
        todayStakeLabel.setText(String.format("$%,.2f",staked));
        String profitColor = "-fx-text-fill: " + labelColors.get(profit>0);
        todayProfitLabel.setStyle(profitColor);
        double roi = (profit / staked) * 100;
        todayROILabel.setText(String.format("%.2f",roi)+"%");
    }
    /**
     * sets month's profit/stake and ROI
     */
    public void setMonthTop() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        LocalDateTime currentTimeDate = currentTime.toLocalDateTime();
        YearMonth yearMonth = YearMonth.of(currentTimeDate.getYear(), currentTimeDate.getMonthValue());
        LocalDateTime lastDayLocal = yearMonth.atEndOfMonth().atTime(11, 59, 00);
        LocalDateTime firstDayLocal = yearMonth.atDay(1).atTime(0, 0, 0);
        Timestamp firstDay = Timestamp.valueOf(firstDayLocal);
        Timestamp lastDay = Timestamp.valueOf(lastDayLocal);
        
        FilteredList<Bet> monthBets = new FilteredList<Bet>(allBets, p -> true);
        Predicate<Bet> firstFilter = s -> s.getDate().before(lastDay);
        Predicate<Bet> secondFilter = s -> (s.getDate().after(firstDay) || s.getDate().equals(firstDay));
        monthBets.setPredicate(secondFilter.and(firstFilter));

        double profit = 0;
        double staked = 0;
        for (Bet bet: monthBets) {
            profit = profit + bet.getProfit();
            staked = staked + bet.getStake();
        }


        monthProfitLabel.setText(String.format("$%,.2f",profit));
        monthStakeLabel.setText(String.format("$%,.2f",staked));
        String profitColor = "-fx-text-fill: " + labelColors.get(profit>0);
        monthProfitLabel.setStyle(profitColor);
        double roi = (profit / staked) * 100;
        monthROILabel.setText(String.format("%.2f",roi)+"%");
    }
    /**
     * sets total profit/stake and ROI
     */
    public void setTotalTop() {


        double profit = 0;
        double staked = 0;
        double moneyPending = 0;
        double freeBetPending = 0;
        for (Bet bet: allBets) {
            profit = profit + bet.getProfit();
            staked = staked + bet.getStake();
            if (bet.getStatus().equals("pending")) {
                moneyPending = moneyPending + bet.getStake();
                freeBetPending = freeBetPending + bet.getFreeBetStake();
            }
        }


        totalProfitLabel.setText(String.format("$%,.2f",profit));
        totalStakeLabel.setText(String.format("$%,.2f",staked));
        String profitColor = "-fx-text-fill: " + labelColors.get(profit>0);
        totalProfitLabel.setStyle(profitColor);
        double roi = (profit / staked) * 100;
        totalROILabel.setText(String.format("%.2f",roi)+"%");
        moneyPendingLabel.setText(String.format("$%,.2f",moneyPending));
        freeBetPendingLabel.setText(String.format("$%,.2f",freeBetPending));
        //TODO: add ROI for label in grid
    }
    /**
     * clears labels for the edit and add bets
     */
    public void clearLabels() {
        comboBoxStatus.setValue("");
        textFieldSportsbook.setText("");
        textFieldDate.setText("");
        textFieldOdds.setText("");
        textFieldMoneyStake.setText("");
        textFieldFreeBetStake.setText("");
        textFieldTags.setText("");
        textAreaLegs.setText("");
    }
    /**
     * gets clipboard contents to add a bet for pos-ev (Currently only used by me because it takes a certain format of text to copy and split up)
     */
    public void addBetPosClicked() throws IOException, ClassNotFoundException {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        String fullString = clipboard.getString();
        System.out.println(fullString);

        String[] array = fullString.split("\n");
        double percent = Double.valueOf(array[0].split("------")[0].trim().replace("%",""));
        System.out.println(percent);

        String firstDate = array[1].split(",")[1].trim();
        String month = String.valueOf(monthToInteger.get(firstDate.split(" ")[0].toLowerCase()));
        System.out.println(month);

        String day = firstDate.split(" ")[1];


        String year = array[1].split(",")[2].trim().split(" ")[0].trim();
        Timestamp date = Timestamp.valueOf(year + "-" + month + "-" + day + " 00:00:00");
        System.out.println(date);

        String event = array[2].split("-")[0].trim();
        System.out.println(event);

        String market = array[3];
        System.out.println(market);
        String legs = event + " - " + market;
        int odds = 0;
        String books;
        if (array[4].split("-").length > 2) {
            odds = Integer.valueOf(array[4].split("-")[1].trim()) * -1;
            books = array[4].split("-")[2].trim();
        } else {
            odds = Integer.valueOf(array[4].split("-")[0].split(":")[1].trim());
            books = array[4].split("-")[1].trim();
        }

        List<String> kambi = new ArrayList<>();
        kambi.add("barstool");
        kambi.add("unibet");
        for (String site: kambi) {
            if (books.toLowerCase().contains(site) || books.toLowerCase().equals(site)) {
                books = "BetRivers";
            }

        }
        System.out.println(odds);
        System.out.println(books);

        double betSize = Double.valueOf(array[0].split("------")[1].split(":")[1].trim().replace("$",""));
        System.out.println(betSize);
        Bet newBet = new Bet(-1,books,date,legs,odds,"pending",
                betSize,0,"pos-ev",0,0,
                percent,0,0);
        int betId = client.addBet(newBet);
        if (betId != -4) {
            newBet.setBetId(betId);
            allBets.add(newBet);
            setSuccess("Successfully added pos-ev bet");
        }
        betTable.refresh();



    }
    /**
     * sorts bets by date and status (newest date and pending status at the top of table)
     */
    public void sortBets() {

        List<String> order = new ArrayList<>();
        order.add("new");
        order.add("pending");

        Comparator<Bet> byStatus = (o1, o2) -> {
            int index1 = order.indexOf(o1.getStatus());
            int index2 = order.indexOf(o2.getStatus());

            return Integer.compare(index2,index1);
        };
        betTable.refresh();
        allBets.sort(Comparator.comparing(Bet::getDate).reversed().thenComparing(byStatus));

    }
    /**
     * opens calculator window
     */
    public void calcButtonClicked() throws IOException {
        System.out.println("TESTING STAGE " + CalculatorController.calcStage);
        if (CalculatorController.isOpen == false) {
            FXMLLoader root = new FXMLLoader(Main.class.getResource("calculator.fxml"));
            CalculatorController.calcStage = new Stage();
            Scene scene = new Scene(root.load(), 600, 550);

            CalculatorController.calcStage.setTitle("Calculator");
            CalculatorController.calcStage.setScene(scene);
            CalculatorController.calcStage.setOnCloseRequest(event -> {
                System.out.println("setting open to false");
                CalculatorController.isOpen = false;
            });

            CalculatorController.calcStage.show();

        } else {
            System.out.println("should show");
            CalculatorController.calcStage.toFront();
        }



    }
    /**
     * searches through bet table and shows bets if Sportsbook, Legs or Tag matches what was typed.
     */
    public void searchTextFieldTyped() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ObservableList<Bet> searchList = FXCollections.observableArrayList();
        String searchText = searchTextField.getText();
        if (!searchText.equals("")) {
            System.out.println(searchText);
            for (int i=0; i < allBets.size(); i++) {
                Bet currBet = allBets.get(i);
                if (currBet.getLegs().toLowerCase().contains(searchText.toLowerCase()) || currBet.getLegs().toLowerCase().equals(searchText.toLowerCase())) {
                    searchList.add(allBets.get(i));
                } else if (currBet.getTags().toLowerCase().contains(searchText.toLowerCase()) || currBet.getTags().toLowerCase().equals(searchText.toLowerCase())) {
                    searchList.add(allBets.get(i));
                } else if (currBet.getSportsbook().toLowerCase().contains(searchText.toLowerCase()) || currBet.getSportsbook().toLowerCase().equals(searchText.toLowerCase())) {
                    searchList.add(allBets.get(i));
                }
            }
            betTable.setItems(searchList);
            betTable.refresh();
        } else  {
            System.out.println("nothing typed");
            Method method = this.getClass().getMethod(currentFilter);
            method.invoke(this);
        }
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