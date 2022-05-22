/*
    RMIT University Vietnam
    Course: INTE2512 Object-Oriented Programming
    Semester: 2021B
    Assessment: Final Project
    Author:
    - Pham Duy Anh - s3802674
    - Pham Dang Khoa - s3884419
    - Nguyen Minh Hien - s3877996
    - Nathan Candre - s3938364
    Acknowledgement:
    [1]: https://jsoup.org/cookbook/extracting-data/selector-syntax
    [2]: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ThreadPoolExecutor.html
    [3]: https://www.tutorialspoint.com/javafx/javafx_css.htm
    [4]: https://www.javatpoint.com/javafx-playing-video
    [5] All lecture and lab slides from RMIT univeristy
*/

package Main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Main extends Application {
    public static Stage stage;
    @Override
    public void start(Stage PrimaryStage) throws Exception {
        Main.stage = new Stage();
        run(PrimaryStage);
    }

    void run(Stage Stage) throws Exception {
        // Extra function using button F11 to open/exit fullscreen
        Stage.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (KeyCode.F11.equals(e.getCode())) {
                Stage.setFullScreen(!Stage.isFullScreen());
            }
        });
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("HomeScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 640);
        Stage.setTitle("NEWS!");
        Stage.setScene(scene);
        Stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
