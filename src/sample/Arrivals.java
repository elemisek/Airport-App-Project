package sample;

/**
 * Klasa obsługująca tworzenie obiektów typu przyloty na potrzeby uzupełniania tabeli przylotów.
 */
public class Arrivals
{
	/**
	 * Zmienna przechowująca czas przylotu.
	 */
	private String czasPrzylotu;

	/**
	 * Zmienna przechowująca nazwę lotniska odlotu.
	 */
	private String nazwaLotniskaOdlotu;

	/**
	 * Zmienna przechowująca numer lotu.
	 */
	private String idLotu;

	/**
	 * Zmienna przechowująca nazwę linii lotniczej.
	 */
	private String nazwaLinii;

	/**
	 * Zmienna przechowująca model samolotu.
	 */
	private String modelSamolotu;

	/**
	 * Konstruktor obiektu typu przyloty inicjalizujący wartości zmiennych składowych klasy.
	 * @param czasPrzylotu czas przylotu
	 * @param nazwaLotniskaOdlotu nazwa lotniska odlotu
	 * @param idLotu numer lotu
	 * @param nazwaLinii nazwa linii lotniczej
	 * @param modelSamolotu model samolotu
	 */
	public Arrivals(String czasPrzylotu, String nazwaLotniskaOdlotu, String idLotu, String nazwaLinii, String modelSamolotu)
	{
		this.czasPrzylotu = czasPrzylotu;
		this.nazwaLotniskaOdlotu = nazwaLotniskaOdlotu;
		this.idLotu = idLotu;
		this.nazwaLinii = nazwaLinii;
		this.modelSamolotu = modelSamolotu;
	}

	/**
	 * Getter zmiennej składowej czas przylotu.
	 * @return czas przylotu
	 */
	public String getCzasPrzylotu()
	{
		return czasPrzylotu;
	}

	/**
	 * Getter zmiennej składowej nazwa lotniska odlotu.
	 * @return nazwa lotniska odlotu
	 */
	public String getNazwaLotniskaOdlotu()
	{
		return nazwaLotniskaOdlotu;
	}

	/**
	 * Getter zmiennej składowej numer lotu.
	 * @return numer lotu
	 */
	public String getIdLotu()
	{
		return idLotu;
	}

	/**
	 * Getter zmiennej składowej nazwa linii lotniczej.
	 * @return nazwa linii lotniczej
	 */
	public String getNazwaLinii()
	{
		return nazwaLinii;
	}

	/**
	 * Getter zmiennej składowej model samolotu.
	 * @return model samolotu
	 */
	public String getModelSamolotu()
	{
		return modelSamolotu;
	}
}
