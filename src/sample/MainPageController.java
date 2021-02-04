package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.*;

/**
 * Klasa obsługująca pierwszy ekran - wybór lotniska z listy.
 */
public class MainPageController extends ConnectionController
{
	/**
	 * Lista dostępnych lotnisk.
	 */
	@FXML
	public ListView<String> list;

	/**
	 * Lista obsługująca zmiany w liście lotnisk.
	 */
	ObservableList<String> items = FXCollections.observableArrayList();

	/**
	 * Funkcja inicjalizująca scenę - wypełniająca listę lotnisk funkcją {@link #populizeListWithItems()}.
	 */
	@FXML
	protected void initialize()
	{
		populizeListWithItems();
		list.setItems(items);
	}

	/**
	 * Główny panel sceny.
	 */
	@FXML
	public BorderPane borderPane;

	/**
	 * Funkcja obsługująca wybór lotnisk z listy. Nazwa wybranego lotniska zostaje przekazana do kontrolera kolejnej sceny a scena zostaje zmieniona.
	 */
	@FXML
	public void handleMouseClick()
	{
		try {
			if (!list.getSelectionModel().isEmpty()) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
				BorderPane newPane = loader.load();
				borderPane.getChildren().setAll(newPane);

				Controller controller = loader.getController();
				controller.transferMessage(list.getSelectionModel().getSelectedItem());
			}
		} catch (IOException e) {
			System.out.println("Problem ladowania sceny. " + e);
		}
	}

	/**
	 * Funkcja obsługująca pobranie z bazy listy dostępnych lotnisk.
	 */
	private void populizeListWithItems()
	{
		Connection connection = getDatabaseConnection();
		if (connection != null) {
			try {
				getIdLotniska(connection);
				connection.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}
	}

	/**
	 * Funkcja pobierająca z bazy nazwy i kraje dostepnych lotnisk oraz dodająca je do listy.
	 * @param connection połączenie z bazą danych
	 */
	private void getIdLotniska(Connection connection)
	{
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT nazwa_lotniska_kraj FROM projekt.lista_dostepnych_lotnisk");

			while (resultSet.next()) {
				items.add(resultSet.getString("nazwa_lotniska_kraj"));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("Blad podczas przetwarzania danych:" + e);
		}
	}
}
