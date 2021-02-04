package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Klasa obsługująca drugi ekran - funkcjonalności dostępne po wybraniu lotniska.
 * Obsługuje przyciski odpowiedzialne za:
 *  <ul>
 *  	<li>restauracje,</li>
 *  	<li>sklepy,</li>
 *  	<li>lounge,</li>
 *  	<li>autobusy,</li>
 *  	<li>pociągi,</li>
 *  	<li>przyloty,</li>
 *  	<li>odloty,</li>
 *  	<li>szukaj,</li>
 *  	<li>opinie.</li>
 *  </ul>
 */
public class Controller
{
	/**
	 * Informacja o aktualnie wybranym lotnisku.
	 */
	@FXML
	public Text airportLabel;

	/**
	 * Główny panel sceny.
	 */
	@FXML
	public BorderPane borderPane;

	/**
	 * Nazwa wybranego lotniska.
	 */
	private String airport;

	/**
	 * Funkcja obsługująca pobranie wartości przekazanych przez poprzednią scenę.
	 * @param selectedAirport nazwa wybranego lotniska
	 */
	public void transferMessage(String selectedAirport)
	{
		airportLabel.setText(selectedAirport);
		airport = selectedAirport;
	}

	/**
	 * Funkcja obsługująca przycisk powrotu do poprzedniego ekranu.
	 */
	public void handleReturnMouseClick()
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
			BorderPane oldPane = loader.load();
			borderPane.getChildren().setAll(oldPane);
		} catch (IOException e) {
			System.out.println("Problem ladowania sceny. " + e);
		}
	}

	/**
	 * Funkcja pomocnicza zmieniająca scenę oraz przekazująca informację o wybranej funkcjonalności.
	 * @param amenity wybrana funkcjonalność
	 */
	private void handleAmenities(String amenity)
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("amenitiesPage.fxml"));
			BorderPane newPane = loader.load();
			borderPane.getChildren().setAll(newPane);
			AmenitiesController controller = loader.getController();
			controller.transferMessage(amenity, airport);
		} catch (IOException e) {
			System.out.println("Problem ladowania sceny. " + e);
		}
	}

	/**
	 * Funkcja obsługująca przycisk odpowiedzialny za przejście do panelu restauracji.
	 */
	public void handleRestauracjeMouseClick()
	{
		handleAmenities("restauracje");
	}

	/**
	 * Funkcja obsługująca przycisk odpowiedzialny za przejście do panelu sklepów.
	 */
	public void handleSklepyMouseClick()
	{
		handleAmenities("sklepy");
	}

	/**
	 * Funkcja obsługująca przycisk odpowiedzialny za przejście do panelu lounge'y.
	 */
	public void handleLoungeMouseClick()
	{
		handleAmenities("lounge");
	}

	/**
	 * Funkcja obsługująca przycisk odpowiedzialny za przejście do panelu autobusów.
	 */
	public void handleAutobusyMouseClick()
	{
		handleAmenities("autobusy");
	}

	/**
	 * Funkcja obsługująca przycisk odpowiedzialny za przejście do panelu pociągów.
	 */
	public void handlePociagiMouseClick()
	{
		handleAmenities("pociagi");
	}

	/**
	 * Funkcja obsługująca przycisk odpowiedzialny za przejście do panelu przylotów.
	 */
	public void handlePrzylotyMouseClick()
	{
		handleAmenities("przyloty");
	}

	/**
	 * Funkcja obsługująca przycisk odpowiedzialny za przejście do panelu odlotów.
	 */
	public void handleOdlotyMouseClick()
	{
		handleAmenities("odloty");
	}

	/**
	 * Funkcja obsługująca przycisk odpowiedzialny za przejście do panelu wyszukiwania szczegółów lotu.
	 */
	public void handleSprawdzLotMouseClick()
	{
		handleAmenities("sprawdzLot");
	}

	/**
	 * Funkcja obsługująca przycisk odpowiedzialny za przejście do panelu opinii.
	 */
	public void handleOpinieMouseClick() { handleAmenities("opinie"); }
}
