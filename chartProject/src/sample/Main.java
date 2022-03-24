package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Chart");
        primaryStage.setScene(new Scene(root, 800, 650));
        primaryStage.show();


    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Controller nesne=new Controller();


        launch(args);


    }
}
