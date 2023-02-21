package com.kyle.clientTracker.controller;

import com.kami.lookout.Bet;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.kyle.clientTracker.Main.client;
import static com.kyle.clientTracker.controller.MainController.allBets;

/**
 * controller for the calculator window
 * @author Kyle Gibson
 */
public class CalculatorController implements Initializable {
    public static Boolean isOpen = false;
    public static Stage calcStage;
    public Button closeButton;
    public DatePicker calcDate;
    public TextField legTextField1;
    public TextField legTextField2;
    public TextField legTextField3;
    public TextField legTextField4;
    public TextField legTextField5;
    public TextField legTextField6;
    public TextField legTextField8;
    public TextField legTextField7;
    public TextField legTextField9;
    public TextField legTextField10;
    public Button addBetButton;
    public Label legWinPercentageLabel1;
    public Label legWinPercentageLabel2;
    public Label legWinPercentageLabel3;
    public Label legWinPercentageLabel4;
    public Label legWinPercentageLabel5;
    public Label legWinPercentageLabel6;
    public Label legWinPercentageLabel7;
    public Label legWinPercentageLabel8;
    public Label legWinPercentageLabel9;
    public Label legWinPercentageLabel10;
    public TextField sportsbookLabel;
    public TextField stakeLabel;
    public TextField tagsLabel;
    public TextField oddsLabel;
    public Label fkLabel;
    public Label tkLabel;
    public Label qkLabel;
    public TextField oddsTextField1;
    public TextField oddsTextField2;
    public TextField oddsTextField3;
    public TextField oddsTextField4;
    public TextField oddsTextField5;
    public TextField oddsTextField6;
    public TextField oddsTextField7;
    public TextField oddsTextField8;
    public TextField oddsTextField9;
    public TextField oddsTextField10;
    public Label fairOddsLabel;
    public Label fullWinPercentageLabel;
    public Button addBetLabel1;
    public Label evLabel;
    Map<String, TextField> oddLabels = new HashMap<>();
    Map<String, Label> percentLabels = new HashMap<>();
    Map<String, TextField> legTextFields = new HashMap<>();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        calcDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("New date selected: " + newValue);
        });
        calcDate.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            // Parse the new text as a LocalDate
            LocalDate newValue = null;
            System.out.println("FIRST " + newText);
            try {

                newValue = LocalDate.of(Integer.valueOf(newText.split("/")[2]),Integer.valueOf(newText.split("/")[0]),Integer.valueOf(newText.split("/")[1]));
            } catch (DateTimeParseException e) {
                System.out.println("second " + newText);
                // If the new text is not a valid date, ignore it
                return;
            }
            // Set the DatePicker's value to the parsed LocalDate
            calcDate.setValue(newValue);
        });


        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        LocalDate currentDate = currentTime.toLocalDateTime().toLocalDate();
        calcDate.setValue(currentDate);
        oddLabels.put("oddsTextField1",oddsTextField1);
        oddLabels.put("oddsTextField2",oddsTextField2);
        oddLabels.put("oddsTextField3",oddsTextField3);
        oddLabels.put("oddsTextField4",oddsTextField4);
        oddLabels.put("oddsTextField5",oddsTextField5);
        oddLabels.put("oddsTextField6",oddsTextField6);
        oddLabels.put("oddsTextField7",oddsTextField7);
        oddLabels.put("oddsTextField8",oddsTextField8);
        oddLabels.put("oddsTextField9",oddsTextField9);
        oddLabels.put("oddsTextField10",oddsTextField10);

        percentLabels.put("oddsTextField1",legWinPercentageLabel1);
        percentLabels.put("oddsTextField2",legWinPercentageLabel2);
        percentLabels.put("oddsTextField3",legWinPercentageLabel3);
        percentLabels.put("oddsTextField4",legWinPercentageLabel4);
        percentLabels.put("oddsTextField5",legWinPercentageLabel5);
        percentLabels.put("oddsTextField6",legWinPercentageLabel6);
        percentLabels.put("oddsTextField7",legWinPercentageLabel7);
        percentLabels.put("oddsTextField8",legWinPercentageLabel8);
        percentLabels.put("oddsTextField9",legWinPercentageLabel9);
        percentLabels.put("oddsTextField10",legWinPercentageLabel10);

        legTextFields.put("legTextField1",legTextField1);
        legTextFields.put("legTextField2",legTextField2);
        legTextFields.put("legTextField3",legTextField3);
        legTextFields.put("legTextField4",legTextField4);
        legTextFields.put("legTextField5",legTextField5);
        legTextFields.put("legTextField6",legTextField6);
        legTextFields.put("legTextField8",legTextField7);
        legTextFields.put("legTextField7",legTextField8);
        legTextFields.put("legTextField9",legTextField9);
        legTextFields.put("legTextField10",legTextField10);
        isOpen = true;

    }
    /**
     * closes out of the application
     */
    public void closeButtonClicked() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * attempts to add a bet
     */
    public void addBetButtonClicked() throws IOException, ClassNotFoundException {
        System.out.println("Add bet clicked");
        String legs = "";

        for (int i = 1; i < 11; i++) {
            TextField currentTextField = legTextFields.get("legTextField" + i);
            if (!currentTextField.getText().equals("")) {
                if (!legs.equals("")) {
                    legs = legs + ", " + currentTextField.getText();
                } else {
                    legs = currentTextField.getText();
                }
            }
        }
        Double stake;
        int odds;
        Timestamp date;

        String sportsbook = sportsbookLabel.getText();
        try {
            stake = Double.valueOf(stakeLabel.getText());
        } catch (NumberFormatException e) {
            System.out.println("Stake can only contain numbers");
            return;
        }
        try {
            date = Timestamp.valueOf(calcDate.getValue() + " 00:00:00");
        } catch (IllegalArgumentException e) {
            System.out.println("Check date please");
            return;
        }

        String tags = tagsLabel.getText();
        try {
            odds = Integer.parseInt(oddsLabel.getText());
        } catch (NumberFormatException e) {
            System.out.println("Odds can only contain numbers");
            return;
        }

        System.out.println(date);
        legs = legs + " " + "Fair: " + fairOddsLabel.getText() + "    Win:" + fullWinPercentageLabel.getText();
        Bet newBet = new Bet(-1,sportsbook,date,legs,odds,"pending",
                stake,0,tags,0,0,
                0,0,0);
        int betId = client.addBet(newBet);
        if (betId != -4) {
            newBet.setBetId(betId);
            allBets.add(newBet);
        }
        //TODO: clear all text labels if bet added successfully

//        calcStage.close();
//        FXMLLoader root = new FXMLLoader(Main.class.getResource("calculator.fxml"));
//        CalculatorController.calcStage = new Stage();
//        Scene scene = new Scene(root.load(), 600, 550);
//
//        CalculatorController.calcStage.setTitle("Calculator");
//        CalculatorController.calcStage.setScene(scene);
//        CalculatorController.calcStage.setOnCloseRequest(event -> {
//            System.out.println("setting open to false");
//            CalculatorController.isOpen = false;
//        });
//
//        CalculatorController.calcStage.show();

    }
    /**
     * when you type in the odds text fields it recalculates if the bet is profitable or not
     */
    public void oddsChanged() {
        double totalPercent = 1.0;

        for (int i = 1; i < 11; i++) {
            TextField current = oddLabels.get("oddsTextField" + i);
            if (!(current.getText().equals("") || current.getText().equals("-"))) {
                int odds = Integer.valueOf(current.getText());
                double percent = americanToPercent(odds);
                totalPercent = totalPercent * percent;
                Label currentLabel = percentLabels.get("oddsTextField" + i);
                currentLabel.setText(String.format("%.2f", percent*100) + "%");
            }
        }
        if (totalPercent < 1.0) {
            fairOddsLabel.setText(String.format("%.0f", percentToAmerican(totalPercent)));
            fullWinPercentageLabel.setText(String.format("%.2f",totalPercent*100) + "%");
        }
        if (!(oddsLabel.getText().equals("") || oddsLabel.getText().equals("-")) && !(fairOddsLabel.getText().equals(""))) {
            int odds = Integer.valueOf(oddsLabel.getText());
            double myOddsDecimal = americanToDecimal(odds);
            double fullKelly = kellyCriteriaStake(myOddsDecimal,totalPercent);
            double thirdKelly = fullKelly/3;
            double quadKelly = fullKelly/4;

            fkLabel.setText(String.format("$%,.2f",fullKelly));
            tkLabel.setText(String.format("$%,.2f",thirdKelly));
            qkLabel.setText(String.format("$%,.2f",quadKelly));

            Double profit = calcProfit(odds,100);
            Double expectedValue = (totalPercent * profit) - ((1.0-totalPercent)*100.0);
            evLabel.setText(String.format("%,.2f",expectedValue) + "%");
        }
    }


    /**
     * @param oddsOld an integer of odds that you want to convert to be a percent
     * @return a double number that is the percent equal to the odds in param
     */
    public double americanToPercent(int oddsOld) {
        double odds = Double.valueOf(oddsOld);
        double percent;
        if (odds > 0) {
            percent = 100/(odds+100);
        } else if (odds < 0) {
            percent = Math.abs(odds)/(Math.abs(odds)+100);
        } else {
            percent = 1.0;
        }

        return percent;
    }
    /**
     * @param percent a double that is the percent you want to convert to american odds
     * @return a double that is american odds
     */
    public double percentToAmerican(double percent) {
        double odds;
        if (percent > .5) {
            odds = -1*((percent*100)/(1-percent));
        } else {
            odds = (100/percent)-100;
        }
        return odds;
    }
    /**
     * @param myDecimal your odds for a particular bet
     * @param fairPercent the fair value of the bet
     * @return how much you should bet based off of the kelly criteria formula
     */
    public double kellyCriteriaStake(double myDecimal, double fairPercent) {
        double bankroll = 2500.00;
        double bankRollAmountDecimal = (((myDecimal - 1.0) * fairPercent) - (1.0-fairPercent))/(myDecimal - 1.0);

        return bankRollAmountDecimal*bankroll;
    }

    /**
     * @param myOdds your odds that you want to convert to being a decimal
     * @return a double that is a decimal in odds
     */

    public double americanToDecimal(int myOdds) {
        double decimal = 0.0;
        double odds = Double.valueOf(myOdds);
        if (odds > 0) {
            decimal = 1 + (odds / 100);
        }
        else if (odds < 0) {
            decimal = 1 - (100 / odds);
        }
        return decimal;
    }

    /**
     * @param odds the final odds of a bet
     * @param stake how much you are placing on the bet
     * @return returns the profit amount if the bet wins
     */
    public double calcProfit(int odds, double stake) {
        double profit;
        if (odds > 0) {
            profit = (odds / 100.00) * stake;
        } else {
            profit = (100.00 / Math.abs(odds) * stake);
        }
        return profit;
    }
}
