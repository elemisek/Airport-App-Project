package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;
import java.util.Locale;

/**
 * Klasa implementująca wszystkie funkcjonalności aplikacji:
 * <ul>
 *     <li>restauracje,</li>
 *     <li>sklepy,</li>
 *     <li>lounge,</li>
 *     <li>autobusy,</li>
 *     <li>pociągi,</li>
 *     <li>przyloty,</li>
 *     <li>odloty,</li>
 *     <li>szukaj,</li>
 *     <li>opinie.</li>
 * </ul>
 */
public class AmenitiesController extends ConnectionController
{
	/**
	 * Główny panel sceny.
	 */
	@FXML
	public BorderPane borderPane;

	/**
	 * Panel restauracji.
	 */
	@FXML
	public BorderPane restauracjeBorderPane;

	/**
	 * Panel sklepów.
	 */
	@FXML
	public BorderPane sklepyBorderPane;

	/**
	 * Panel lounge'y.
	 */
	@FXML
	public BorderPane loungeBorderPane;

	/**
	 * Panel autobusów.
	 */
	@FXML
	public BorderPane autobusyBorderPane;

	/**
	 * Panel pociągów.
	 */
	@FXML
	public BorderPane pociagiBorderPane;

	/**
	 * Panel przylotów.
	 */
	@FXML
	public BorderPane przylotyBorderPane;

	/**
	 * Panel odlotów.
	 */
	@FXML
	public BorderPane odlotyBorderPane;

	/**
	 * Panel wyszukiwania lotów.
	 */
	@FXML
	public BorderPane szukajBorderPane;

	/**
	 * Panel opinii.
	 */
	@FXML
	public BorderPane opinieBorderPane;

	//--------------------------------------------------

	/**
	 * Lista restauracji.
	 */
	@FXML
	public ListView<String> restauracjeListView;

	/**
	 * Lista sklepów.
	 */
	@FXML
	public ListView<String> sklepyListView;

	/**
	 * Lista lounge'y.
	 */
	@FXML
	public ListView<String> loungeListView;

	/**
	 * Lista autobusów.
	 */
	@FXML
	public ListView<String> autobusyListView;

	/**
	 * Lista pociągów.
	 */
	@FXML
	public ListView<String> pociagiListView;

	/**
	 * Lista opinii.
	 */
	@FXML
	public ListView<String> opinieListView;

	/**
	 * Tablica przylotów.
	 */
	@FXML
	public TableView<Arrivals> przylotyTableView;

	/**
	 * Tablica odlotów.
	 */
	@FXML
	public TableView<Departures> odlotyTableView;

	/**
	 * Pole do wpisywania numeru lotu.
	 */
	@FXML
	public TextField idLotu;

	/**
	 * Szczegóły szukanego lotu.
	 */
	@FXML
	public Text szukanyLot;

	/**
	 * Informacja o lotnisku, do którego dodawana jest opinia oraz o liczbie już istniejących o nim opinii.
	 */
	@FXML
	public Text opiniaLotniskoLabel;

	/**
	 * Pole do wpisywania opinii o lotnisku.
	 */
	@FXML
	public TextArea opinieTextArea;

	/**
	 * Lista obsługująca zmiany w liście restauracji.
	 */
	ObservableList<String> itemsRestauracje = FXCollections.observableArrayList();

	/**
	 * Lista obsługująca zmiany w liście sklepów.
	 */
	ObservableList<String> itemsSklepy = FXCollections.observableArrayList();

	/**
	 * Lista obsługująca zmiany w liście lounge'y.
	 */
	ObservableList<String> itemsLounge = FXCollections.observableArrayList();

	/**
	 * Lista obsługująca zmiany w liście autobusów.
	 */
	ObservableList<String> itemsAutobusy = FXCollections.observableArrayList();

	/**
	 * Lista obsługująca zmiany w liście pociągów.
	 */
	ObservableList<String> itemsPociagi = FXCollections.observableArrayList();

	/**
	 * Lista obsługująca zmiany w liście opinii.
	 */
	ObservableList<String> itemsOpinie = FXCollections.observableArrayList();

	/**
	 * Lista obsługująca filtrowanie listy sklepów.
	 */
	FilteredList<String> filteredSklepy = new FilteredList<>(itemsSklepy, s -> true);

	/**
	 * Nazwa lotniska otoczona apostrofami.
	 */
	private String airport;

	/**
	 * Nazwa lotniska.
	 */
	private String airportNoQuotes;

	/**
	 * Funkcja obsługująca pobranie wartości przekazanych przez poprzednią scenę.
	 *
	 * @param amenity       typ funkcjonalności (np. restauracje)
	 * @param chosenAirport nazwa wybranego lotniska
	 */
	public void transferMessage(String amenity, String chosenAirport)
	{
		airport = chosenAirport;
		airportNoQuotes = chosenAirport;
		airport = airport.substring(0, airport.indexOf(','));
		airport = "'" + airport + "'";
		switch (amenity) {
			case "restauracje":
				handleRestauracje(amenity);
				break;
			case "sklepy":
				handleSklepy(amenity);
				break;
			case "lounge":
				handleLounge(amenity);
				break;
			case "autobusy":
				handleAutobusy(amenity);
				break;
			case "pociagi":
				handlePociagi(amenity);
				break;
			case "przyloty":
				handlePrzyloty(amenity);
				break;
			case "odloty":
				handleOdloty(amenity);
				break;
			case "sprawdzLot":
				handleSprawdzLot();
				break;
			case "opinie":
				handleOpinie();
				break;
		}
	}

	/**
	 * Funkcja obsługująca ukrywanie wszystkich paneli poza panelem opinii, ustawianie wartości informacji {@code opiniaLotniskoLabel} funkcją {@link #setLabel()} oraz
	 * wyświetlanie listy opinii.
	 */
	private void handleOpinie()
	{
		pociagiBorderPane.setVisible(false);
		autobusyBorderPane.setVisible(false);
		loungeBorderPane.setVisible(false);
		restauracjeBorderPane.setVisible(false);
		sklepyBorderPane.setVisible(false);
		przylotyBorderPane.setVisible(false);
		odlotyBorderPane.setVisible(false);
		szukajBorderPane.setVisible(false);
		opinieBorderPane.setVisible(true);
		setLabel();
		populizeOpinieWithItems("get");
		opinieListView.setItems(itemsOpinie);
	}

	/**
	 * Funkcja obsługująca ukrywanie wszystkich paneli poza panelem restauracji i wyświetlenie listy restauracji.
	 *
	 * @param amenity typ funkcjonalności
	 */
	private void handleRestauracje(String amenity)
	{
		pociagiBorderPane.setVisible(false);
		autobusyBorderPane.setVisible(false);
		loungeBorderPane.setVisible(false);
		restauracjeBorderPane.setVisible(true);
		sklepyBorderPane.setVisible(false);
		przylotyBorderPane.setVisible(false);
		odlotyBorderPane.setVisible(false);
		szukajBorderPane.setVisible(false);
		opinieBorderPane.setVisible(false);
		restauracjeListView.setItems(itemsRestauracje);
		populizeListWithItems(itemsRestauracje, "nazwa_restauracji", amenity);
	}

	/**
	 * Funkcja obsługująca ukrywanie wszystkich paneli poza panelem sklepów i wyświetlenie ich listy.
	 *
	 * @param amenity typ funkcjonalności
	 */
	private void handleSklepy(String amenity)
	{
		pociagiBorderPane.setVisible(false);
		autobusyBorderPane.setVisible(false);
		loungeBorderPane.setVisible(false);
		restauracjeBorderPane.setVisible(false);
		sklepyBorderPane.setVisible(true);
		przylotyBorderPane.setVisible(false);
		odlotyBorderPane.setVisible(false);
		szukajBorderPane.setVisible(false);
		opinieBorderPane.setVisible(false);
		sklepyListView.setItems(itemsSklepy);
		populizeListWithItems(itemsSklepy, "nazwa_sklepu", amenity);
	}

	/**
	 * Funkcja obsługująca ukrywanie wszystkich paneli poza panelem lounge'y i wyświetlenie ich listy.
	 *
	 * @param amenity typ funkcjonalności
	 */
	private void handleLounge(String amenity)
	{
		pociagiBorderPane.setVisible(false);
		autobusyBorderPane.setVisible(false);
		loungeBorderPane.setVisible(true);
		restauracjeBorderPane.setVisible(false);
		sklepyBorderPane.setVisible(false);
		przylotyBorderPane.setVisible(false);
		odlotyBorderPane.setVisible(false);
		szukajBorderPane.setVisible(false);
		opinieBorderPane.setVisible(false);
		loungeListView.setItems(itemsLounge);
		populizeListWithItems(itemsLounge, "nazwa_lounge", amenity);
	}

	/**
	 * Funkcja obsługująca ukrywanie wszystkich paneli poza panelem autobusów i wyświetlenie ich listy.
	 *
	 * @param amenity typ funkcjonalności
	 */
	private void handleAutobusy(String amenity)
	{
		pociagiBorderPane.setVisible(false);
		autobusyBorderPane.setVisible(true);
		loungeBorderPane.setVisible(false);
		restauracjeBorderPane.setVisible(false);
		sklepyBorderPane.setVisible(false);
		przylotyBorderPane.setVisible(false);
		odlotyBorderPane.setVisible(false);
		szukajBorderPane.setVisible(false);
		opinieBorderPane.setVisible(false);
		autobusyListView.setItems(itemsAutobusy);
		populizeTransportListWithItems(itemsAutobusy, amenity);
	}

	/**
	 * Funkcja obsługująca ukrywanie wszystkich paneli poza panelem pociągów i wyświetlenie ich listy.
	 *
	 * @param amenity typ funkcjonalności
	 */
	private void handlePociagi(String amenity)
	{
		pociagiBorderPane.setVisible(true);
		autobusyBorderPane.setVisible(false);
		loungeBorderPane.setVisible(false);
		restauracjeBorderPane.setVisible(false);
		sklepyBorderPane.setVisible(false);
		przylotyBorderPane.setVisible(false);
		odlotyBorderPane.setVisible(false);
		szukajBorderPane.setVisible(false);
		opinieBorderPane.setVisible(false);
		pociagiListView.setItems(itemsPociagi);
		populizeTransportListWithItems(itemsPociagi, amenity);
	}

	/**
	 * Funkcja obsługująca ukrywanie wszystkich paneli poza panelem przylotów i wyświetlenie ich w tabeli.
	 *
	 * @param amenity typ funkcjonalności
	 */
	private void handlePrzyloty(String amenity)
	{
		pociagiBorderPane.setVisible(false);
		autobusyBorderPane.setVisible(false);
		loungeBorderPane.setVisible(false);
		restauracjeBorderPane.setVisible(false);
		sklepyBorderPane.setVisible(false);
		przylotyBorderPane.setVisible(true);
		odlotyBorderPane.setVisible(false);
		szukajBorderPane.setVisible(false);
		opinieBorderPane.setVisible(false);
		populizeDeparturesArrivalsListWithItems(amenity);
	}

	/**
	 * Funkcja obsługująca ukrywanie wszystkich paneli poza panelem odlotów i wyświetlenie ich w tabeli.
	 *
	 * @param amenity typ funkcjonalności
	 */
	private void handleOdloty(String amenity)
	{
		pociagiBorderPane.setVisible(false);
		autobusyBorderPane.setVisible(false);
		loungeBorderPane.setVisible(false);
		restauracjeBorderPane.setVisible(false);
		sklepyBorderPane.setVisible(false);
		przylotyBorderPane.setVisible(false);
		szukajBorderPane.setVisible(false);
		odlotyBorderPane.setVisible(true);
		opinieBorderPane.setVisible(false);
		populizeDeparturesArrivalsListWithItems(amenity);
	}

	/**
	 * Funkcja obsługująca ukrywanie wszystkich paneli poza panelem sprawdzania lotu.
	 */
	public void handleSprawdzLot()
	{
		szukajBorderPane.setVisible(true);
		pociagiBorderPane.setVisible(false);
		autobusyBorderPane.setVisible(false);
		loungeBorderPane.setVisible(false);
		restauracjeBorderPane.setVisible(false);
		sklepyBorderPane.setVisible(false);
		przylotyBorderPane.setVisible(false);
		odlotyBorderPane.setVisible(false);
		opinieBorderPane.setVisible(false);
	}

	/**
	 * Funkcja obsługująca uzupełnianie informacji {@code opiniaLotniskoLabel} o nazwę lotniska oraz liczbę dotychczasowych opinii.
	 */
	private void setLabel()
	{
		Connection connection = getDatabaseConnection();
		if (connection != null) {
			try {
				Statement firstStatement = connection.createStatement();
				ResultSet firstResultSet = firstStatement.executeQuery("SELECT id_lotniska FROM projekt.lotniska WHERE nazwa_lotniska=" + airport);
				firstResultSet.next();
				String idLotniska = "'" + firstResultSet.getString("id_lotniska") + "'";

				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count FROM projekt.opinie WHERE id_lotniska=" + idLotniska);
				resultSet.next();

				opiniaLotniskoLabel.setText(opiniaLotniskoLabel.getText() + airportNoQuotes + ".\nLiczba opinii: " + resultSet.getString("count") + ".");

				firstResultSet.close();
				firstStatement.close();
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}
	}

	/**
	 * Funkcja obsługująca listę opinii o lotnisku na podstawie wartości pobranych z bazy.
	 * @param getSet flaga określająca czy opinie mają zostać pobrane z bazy, czy do niej zapisane
	 */
	private void populizeOpinieWithItems(String getSet)
	{
		Connection connection = getDatabaseConnection();
		if (connection != null) {
			try {
				if (getSet.equals("get")) {
					getOpinie(connection);
				} else if (getSet.equals("set")) {
					setOpinie(connection);
				}
				connection.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}
	}

	/**
	 * Funkcja obsługująca zapisanie opinii o lotnisku do bazy.
	 * @param connection połączenie z bazą danych
	 */
	private void setOpinie(Connection connection)
	{
		try {
			Statement firstStatement = connection.createStatement();
			ResultSet firstResultSet = firstStatement.executeQuery("SELECT id_lotniska FROM projekt.lotniska WHERE nazwa_lotniska=" + airport);
			firstResultSet.next();

			String statement = "INSERT INTO projekt.opinie(opinia, id_lotniska) VALUES (?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setString(1, opinieTextArea.getText());
			preparedStatement.setString(2, firstResultSet.getString("id_lotniska"));
			preparedStatement.executeUpdate();

			firstResultSet.close();
			firstStatement.close();
			preparedStatement.close();
			opinieTextArea.clear();
			populizeOpinieWithItems("get");
		} catch (SQLException e) {
			System.out.println("Blad podczas przetwarzania danych:" + e);
		}
	}

	/**
	 * Funkcja obsługująca pobranie opinii o lotniskach z bazy oraz zapisanie ich do listy.
	 * @param connection połączenie z bazą danych
	 */
	private void getOpinie(Connection connection)
	{
		try {
			itemsOpinie.clear();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT opinia FROM projekt.lista_opinii WHERE nazwa_lotniska=" + airport);
			while (resultSet.next()) {
				itemsOpinie.add(resultSet.getString("opinia"));
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("Blad podczas przetwarzania danych:" + e);
		}
	}

	/**
	 * Funkcja obsługująca przycisk dodawania opinii za pomocą funkcji {@link #populizeListWithItems(ObservableList, String, String)}.
	 */
	public void handleDodajOpinieMouseClick()
	{
		if (!opinieTextArea.getText().isEmpty()) {
			populizeOpinieWithItems("set");
		}
	}

	/**
	 * Funkcja obsługująca wyszukiwanie szczegółów lotu.
	 */
	public void handleSearch()
	{
		if (!idLotu.getText().matches("([a-z]|[A-Z])+[0-9]+")) {
			idLotu.setText("");
			szukanyLot.setText("Niepoprawny format numeru lotu.");
		} else {
			Connection connection = getDatabaseConnection();
			if (connection != null) {
				try {
					getFlight(connection);
					connection.close();
				} catch (SQLException sqlException) {
					sqlException.printStackTrace();
				}
			}
		}
	}

	/**
	 * Funkcja obsługująca pobieranie z bazy wszystkich szczegółów lotu o numerze podanym w polu {@code idLotu}.
	 * @param connection połączenie z bazą danych
	 */
	private void getFlight(Connection connection)
	{
		try {
			String result = "";
			String idLot = idLotu.getText().toUpperCase(Locale.ROOT);
			String statement = "SELECT * FROM projekt.szczegoly_lotu WHERE id_loty = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setString(1, idLot);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (!resultSet.isBeforeFirst()) {
				result = "Nie odnaleziono lotu o podanym numerze.";
			} else {
				while (resultSet.next()) {
					result = "Numer lotu: " + resultSet.getString("id_loty") + "\n" +
							"linia lotnicza: " + resultSet.getString("nazwa_linie") + "\n" +
							"czas odlotu: " + resultSet.getString("czas_odlotu") + "\n" +
							"czas przylotu: " + resultSet.getString("czas_przylotu") + "\n" +
							"lotnisko odlotu: " + resultSet.getString("nazwa_lotniska_odlotu") + "\n" +
							"lotnisko przylotu: " + resultSet.getString("nazwa_lotniska_przylotu") + "\n" +
							"model samolotu: " + resultSet.getString("model_samolotu") + ".";
				}
			}

			szukanyLot.setText(result);
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			System.out.println("Blad podczas przetwarzania danych:" + e);
		}
	}

	/**
	 * Funkcja obsługująca przycisk powrotu do poprzedniego ekranu.
	 */
	public void handleReturnButton()
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
			BorderPane oldPane = loader.load();
			borderPane.getChildren().setAll(oldPane);
			Controller controller = loader.getController();
			controller.transferMessage(airportNoQuotes);
		} catch (IOException e) {
			System.out.println("Problem ladowania sceny. " + e);
		}
	}

	/**
	 * Funkcja uzupełniająca tabele przylotów lub odlotów na podstawie danych pobranych z bazy.
	 * @param amenity typ funkcjonalności (przyloty/odloty)
	 */
	private void populizeDeparturesArrivalsListWithItems(String amenity)
	{
		Connection connection = getDatabaseConnection();
		if (connection != null) {
			try {
				if (amenity.equals("przyloty")) {
					getArrivals(connection);
				} else if (amenity.equals("odloty")) {
					getDepartures(connection);
				}
				connection.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}
	}

	/**
	 * Funkcja obsługująca pobranie z bazy przylotów do aktualnie wybranego lotniska oraz zapisanie ich do tabeli.
	 * @param connection połączenie z bazą danych
	 */
	private void getArrivals(Connection connection)
	{
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM projekt.lista_przylotow WHERE nazwa_lotniska = " + airport);

			TableColumn<Arrivals, String> czasPrzylotu = new TableColumn<>("czas przylotu");
			czasPrzylotu.setMaxWidth(180);
			czasPrzylotu.setMinWidth(180);
			czasPrzylotu.setCellValueFactory(new PropertyValueFactory<>("czasPrzylotu"));

			TableColumn<Arrivals, String> nazwaLotniskaOdlotu = new TableColumn<>("nazwa lotniska odlotu");
			nazwaLotniskaOdlotu.setMaxWidth(380);
			nazwaLotniskaOdlotu.setMinWidth(380);
			nazwaLotniskaOdlotu.setCellValueFactory(new PropertyValueFactory<>("nazwaLotniskaOdlotu"));

			TableColumn<Arrivals, String> idLotu = new TableColumn<>("id lotu");
			idLotu.setMaxWidth(120);
			idLotu.setMinWidth(120);
			idLotu.setCellValueFactory(new PropertyValueFactory<>("idLotu"));

			TableColumn<Arrivals, String> nazwaLinii = new TableColumn<>("nazwa linii");
			nazwaLinii.setMaxWidth(180);
			nazwaLinii.setMinWidth(180);
			nazwaLinii.setCellValueFactory(new PropertyValueFactory<>("nazwaLinii"));

			TableColumn<Arrivals, String> modelSamolotu = new TableColumn<>("model samolotu");
			modelSamolotu.setCellValueFactory(new PropertyValueFactory<>("modelSamolotu"));

			while (resultSet.next()) {
				Arrivals row = new Arrivals(resultSet.getString("czas_przylotu"),
						resultSet.getString("nazwa_lotniska_odlotu"),
						resultSet.getString("id_loty"),
						resultSet.getString("nazwa_linie"),
						resultSet.getString("model_samolotu"));
				przylotyTableView.getItems().add(row);
			}

			przylotyTableView.getColumns().addAll(czasPrzylotu, nazwaLotniskaOdlotu, idLotu, nazwaLinii, modelSamolotu);

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("Blad podczas przetwarzania danych:" + e);
		}
	}

	/**
	 * Funkcja obsługująca pobranie z bazy odlotów z aktualnie wybranego lotniska oraz zapisanie ich do tabeli.
	 * @param connection połączenie z bazą danych
	 */
	private void getDepartures(Connection connection)
	{
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM projekt.lista_odlotow WHERE nazwa_lotniska = " + airport);

			TableColumn<Departures, String> czasPrzylotu = new TableColumn<>("czas odlotu");
			czasPrzylotu.setMaxWidth(180);
			czasPrzylotu.setMinWidth(180);
			czasPrzylotu.setCellValueFactory(new PropertyValueFactory<>("czasOdlotu"));

			TableColumn<Departures, String> nazwaLotniskaOdlotu = new TableColumn<>("nazwa lotniska docelowego");
			nazwaLotniskaOdlotu.setMaxWidth(380);
			nazwaLotniskaOdlotu.setMinWidth(380);
			nazwaLotniskaOdlotu.setCellValueFactory(new PropertyValueFactory<>("nazwaLotniskaPrzylotu"));

			TableColumn<Departures, String> idLotu = new TableColumn<>("id lotu");
			idLotu.setMaxWidth(100);
			idLotu.setMinWidth(100);
			idLotu.setCellValueFactory(new PropertyValueFactory<>("idLotu"));

			TableColumn<Departures, String> nazwaLinii = new TableColumn<>("nazwa linii");
			nazwaLinii.setMaxWidth(120);
			nazwaLinii.setMinWidth(120);
			nazwaLinii.setCellValueFactory(new PropertyValueFactory<>("nazwaLinii"));

			TableColumn<Departures, String> modelSamolotu = new TableColumn<>("model samolotu");
			modelSamolotu.setMaxWidth(200);
			modelSamolotu.setMinWidth(200);
			modelSamolotu.setCellValueFactory(new PropertyValueFactory<>("modelSamolotu"));

			TableColumn<Departures, String> nrTerminalaOdlotu = new TableColumn<>("terminal");
			nrTerminalaOdlotu.setCellValueFactory(new PropertyValueFactory<>("nrTerminalaOdlotu"));

			TableColumn<Departures, String> nrBramkiOdlotu = new TableColumn<>("bramka");
			nrBramkiOdlotu.setCellValueFactory(new PropertyValueFactory<>("nrBramkiOdlotu"));

			while (resultSet.next()) {
				Departures row = new Departures(resultSet.getString("czas_odlotu"),
						resultSet.getString("nazwa_lotniska_przylotu"),
						resultSet.getString("id_loty"),
						resultSet.getString("nazwa_linie"),
						resultSet.getString("model_samolotu"),
						resultSet.getString("nr_terminala_odlotu"),
						resultSet.getString("nr_bramki_odlotu"));
				odlotyTableView.getItems().add(row);
			}

			odlotyTableView.getColumns().addAll(czasPrzylotu, nazwaLotniskaOdlotu, idLotu, nazwaLinii, modelSamolotu, nrTerminalaOdlotu, nrBramkiOdlotu);

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("Blad podczas przetwarzania danych:" + e);
		}
	}

	/**
	 * Funkcja obsługująca uzupełnianie listy konkretnego transportu.
	 * @param items lista aktualizująca listę transportu
	 * @param amenity typ transportu
	 */
	private void populizeTransportListWithItems(ObservableList<String> items, String amenity)
	{
		Connection connection = getDatabaseConnection();
		if (connection != null) {
			try {
				getTransport(connection, items, amenity);
				connection.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}
	}

	/**
	 * Funkcja obsługująca dodawanie do listy danego transportu elementów pobranych z bazy.
	 * @param connection połączenie z bazą danych
	 * @param items lista aktualizująca listę transportu
	 * @param amenity typ transportu
	 */
	private void getTransport(Connection connection, ObservableList<String> items, String amenity)
	{
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT czas_odjazdu, linie, kierunek" +
					" FROM projekt." + amenity + ", projekt.lotniska " +
					"WHERE " + amenity + ".id_lotniska=lotniska.id_lotniska and nazwa_lotniska=" + airport + " ORDER BY 3, 1");
			while (resultSet.next()) {
				String odjazd = resultSet.getString("czas_odjazdu");
				if (odjazd == null) {
					odjazd = "";
				} else {
					odjazd += ", ";
				}
				items.add(resultSet.getString("linie") + ", " + odjazd + resultSet.getString("kierunek"));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("Blad podczas przetwarzania danych:" + e);
		}
	}

	/**
	 * Funkcja obsługująca pobieranie danych o restauracjach/sklepach/lounge'ach z bazy.
	 * @param items lista aktualizująca listę restauracji/sklepu/lounge'u
	 * @param rowName nazwa kolumny identyfikującej nazwę restauracji/sklepu/lounge'u
	 * @param amenity typ funkcjonalności
	 */
	private void populizeListWithItems(ObservableList<String> items, String rowName, String amenity)
	{
		Connection connection = getDatabaseConnection();
		if (connection != null) {
			try {
				if (amenity.equals("sklepy")) {
					getSklepy(connection);
				} else if (amenity.equals("restauracje") || amenity.equals("lounge")) {
					getAmenities(connection, items, rowName, amenity);
				}
				connection.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}
	}

	/**
	 * Funkcja pobierająca z bazy informacje o restauracji/lounge'ach oraz zapisująca je do listy.
	 * @param connection połączenie z bazą danych
	 * @param items lista aktualizująca listę restauracji/lounge'u
	 * @param rowName nazwa kolumny identyfikującej nazwę restauracji/lounge'u
	 * @param amenity typ funkcjonalności
	 */
	private void getAmenities(Connection connection, ObservableList<String> items, String rowName, String amenity)
	{
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT " + rowName + ", lokalizacja" +
					" FROM projekt." + amenity + ", projekt.lotniska " +
					"WHERE " + amenity + ".id_lotniska=lotniska.id_lotniska and nazwa_lotniska=" + airport + " ORDER BY 2");
			while (resultSet.next()) {
				items.add(resultSet.getString(rowName) + ", " + resultSet.getString("lokalizacja"));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("Blad podczas przetwarzania danych:" + e);
		}
	}

	/**
	 * Funkcja pobierająca z bazy informacje o sklepach oraz zapisująca je do listy.
	 * @param connection połączenie z bazą danych
	 */
	private void getSklepy(Connection connection)
	{
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM projekt.lista_sklepow WHERE nazwa_lotniska=" + airport);
			while (resultSet.next()) {
				itemsSklepy.add(resultSet.getString("nazwa_sklepu") + ", " + resultSet.getString("lokalizacja") +
						", kontrola: " + resultSet.getString("kontrola") + ",\n" + resultSet.getString("produkty"));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("Blad podczas przetwarzania danych:" + e);
		}
	}

	/**
	 * Funkcja obsługująca filtrowanie listy sklepów - brak filtra.
	 */
	public void handleWszystkoMouseClick()
	{
		filteredSklepy.setPredicate(s -> true);
		SortedList<String> sortedSklepy = new SortedList<>(filteredSklepy);
		sklepyListView.setItems(sortedSklepy);
		sklepyListView.scrollTo(0);
	}

	/**
	 * Funkcja obsługująca filtrowanie listy sklepów - artykuły spożywcze.
	 */
	public void handleSpozywczeMouseClick()
	{
		filteredSklepy.setPredicate(s -> s.contains("spożywcze") || s.contains("spozywcze"));
		SortedList<String> sortedSklepy = new SortedList<>(filteredSklepy);
		sklepyListView.setItems(sortedSklepy);
		sklepyListView.scrollTo(0);
	}

	/**
	 * Funkcja obsługująca filtrowanie listy sklepów - artykuły podróżne.
	 */
	public void handlePodrozneMouseClick()
	{
		filteredSklepy.setPredicate(s -> s.contains("podrozne") || s.contains("podróżne"));
		SortedList<String> sortedSklepy = new SortedList<>(filteredSklepy);
		sklepyListView.setItems(sortedSklepy);
		sklepyListView.scrollTo(0);
	}

	/**
	 * Funkcja obsługująca filtrowanie listy sklepów - biżuteria.
	 */
	public void handleBizuteriaMouseClick()
	{
		filteredSklepy.setPredicate(s -> s.contains("biżuteria") || s.contains("bizuteria"));
		SortedList<String> sortedSklepy = new SortedList<>(filteredSklepy);
		sklepyListView.setItems(sortedSklepy);
		sklepyListView.scrollTo(0);
	}

	/**
	 * Funkcja obsługująca filtrowanie listy sklepów - alkohole.
	 */
	public void handleAlkoholeMouseClick()
	{
		filteredSklepy.setPredicate(s -> s.contains("alkohole"));
		SortedList<String> sortedSklepy = new SortedList<>(filteredSklepy);
		sklepyListView.setItems(sortedSklepy);
		sklepyListView.scrollTo(0);
	}

	/**
	 * Funkcja obsługująca filtrowanie listy sklepów - artykuły dla dzieci.
	 */
	public void handleDlaDzieciMouseClick()
	{
		filteredSklepy.setPredicate(s -> s.contains("dla dzieci"));
		SortedList<String> sortedSklepy = new SortedList<>(filteredSklepy);
		sklepyListView.setItems(sortedSklepy);
		sklepyListView.scrollTo(0);
	}

	/**
	 * Funkcja obsługująca filtrowanie listy sklepów - kosmetyki.
	 */
	public void handleKosmetykiMouseClick()
	{
		filteredSklepy.setPredicate(s -> s.contains("kosmetyki"));
		SortedList<String> sortedSklepy = new SortedList<>(filteredSklepy);
		sklepyListView.setItems(sortedSklepy);
		sklepyListView.scrollTo(0);
	}

	/**
	 * Funkcja obsługująca filtrowanie listy sklepów - prasa.
	 */
	public void handlePrasaMouseClick()
	{
		filteredSklepy.setPredicate(s -> s.contains("prasa"));
		SortedList<String> sortedSklepy = new SortedList<>(filteredSklepy);
		sklepyListView.setItems(sortedSklepy);
		sklepyListView.scrollTo(0);
	}

	/**
	 * Funkcja obsługująca filtrowanie listy sklepów - książki/muzyka.
	 */
	public void handleKsiazkiMuzykaMouseClick()
	{
		filteredSklepy.setPredicate(s -> s.contains("książki") || s.contains("ksiazki") || s.contains("muzyka"));
		SortedList<String> sortedSklepy = new SortedList<>(filteredSklepy);
		sklepyListView.setItems(sortedSklepy);
		sklepyListView.scrollTo(0);
	}
}
