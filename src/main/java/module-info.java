module com.example.lab4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires pdfbox;
    requires itextpdf;
    requires com.opencsv;
    requires org.apache.poi.ooxml;


    opens com.example.lab4 to javafx.fxml;
    exports com.example.lab4;
}