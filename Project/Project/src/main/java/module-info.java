module newsproject.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jsoup;
    requires javafx.media;

    opens newsproject.project to javafx.fxml;
    exports newsproject.project;
    opens Main to javafx.fxml;
    exports Main;
    opens ProjectArticle to javafx.fxml;
    exports ProjectArticle;
    opens ProjectController to javafx.fxml;
    exports ProjectController;
}