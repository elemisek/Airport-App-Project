package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *	Główna klasa programu inicjalizująca początkowe okno.
 */
public class Main extends Application
{

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("assets/airplane.png")));
		primaryStage.setTitle("Lotniska");
		primaryStage.setScene(new Scene(root, 1390, 970));
		primaryStage.show();
	}

	/**
	 * Główna funkcja programu.
	 * @param args argumenty wywołania
	 */
	public static void main(String[] args)
	{
		launch(args);
	}
}
