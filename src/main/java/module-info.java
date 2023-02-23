module com.kyle.clientTracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires kami.lookout;
    requires java.datatransfer;
    requires org.controlsfx.controls;


    opens com.kyle.clientTracker to javafx.fxml;
    exports com.kyle.clientTracker;
    exports com.kyle.clientTracker.controller;
    opens com.kyle.clientTracker.controller to javafx.fxml;
}