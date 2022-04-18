module by.varyvoda.matvey.syntaxmethod {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires kotlin.stdlib.jdk8;
    requires java.desktop;
    requires javafx.graphics;

    opens by.varyvoda.matvey.syntaxmethod to javafx.fxml;
    exports by.varyvoda.matvey.syntaxmethod;
}