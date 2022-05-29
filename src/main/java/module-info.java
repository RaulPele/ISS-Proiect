module com.pelr.employeesmonitor {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.pelr.employeesmonitor to javafx.fxml;
    opens com.pelr.employeesmonitor.controllers to javafx.fxml;
    exports com.pelr.employeesmonitor.controllers to javafx.fxml;
    exports com.pelr.employeesmonitor to javafx.graphics;
}