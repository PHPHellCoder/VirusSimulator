module simulator {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens simulator to javafx.fxml;
    exports simulator;
}