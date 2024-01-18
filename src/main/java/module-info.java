module cllient.ultimatepksmash {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires lombok;
    
    opens cllient.ultimatepksmash to javafx.fxml;
    exports cllient.ultimatepksmash;
    opens cllient.ultimatepksmash.gui.login to javafx.fxml;
    exports cllient.ultimatepksmash.gui.login;
    opens cllient.ultimatepksmash.gui.arena to javafx.fxml;
    exports cllient.ultimatepksmash.gui.arena;
    exports cllient.ultimatepksmash.gui.menu;
    opens cllient.ultimatepksmash.gui.menu to javafx.fxml;

}
