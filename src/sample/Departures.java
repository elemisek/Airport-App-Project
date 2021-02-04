package sample;

/**
 * Klasa obsługująca tworzenie obiektów typu odloty na potrzeby uzupełniania tabeli odlotów.
 */
public class Departures
{
	/**
	 * Zmienna przechowująca czas odlotu.
	 */
	private String czasOdlotu;

	/**
	 * Zmienna przechowująca nazwę lotniska przylotu.
	 */
	private String nazwaLotniskaPrzylotu;

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
	 * Zmienna przechowująca numer terminala odlotu.
	 */
	private String nrTerminalaOdlotu;

	/**
	 * Zmienna przechowująca numer bramki odlotu.
	 */
	private String nrBramkiOdlotu;

	/**
	 * Konstruktor obiektu typu odloty inicjalizujący wartości zmiennych składowych klasy.
	 * @param czasOdlotu czas odlotu
	 * @param nazwaLotniskaPrzylotu nazwa lotniska przylotu
	 * @param idLotu numer lotu
	 * @param nazwaLinii nazwa linii lotniczej
	 * @param modelSamolotu model samolotu
	 * @param nr_terminala_odlotu numer terminala odlotu
	 * @param nr_bramki_odlotu numer bramki odlotu
	 */
	public Departures(String czasOdlotu, String nazwaLotniskaPrzylotu, String idLotu, String nazwaLinii, String modelSamolotu, String nr_terminala_odlotu, String nr_bramki_odlotu)
	{
		this.czasOdlotu = czasOdlotu;
		this.nazwaLotniskaPrzylotu = nazwaLotniskaPrzylotu;
		this.idLotu = idLotu;
		this.nazwaLinii = nazwaLinii;
		this.modelSamolotu = modelSamolotu;
		this.nrTerminalaOdlotu = nr_terminala_odlotu;
		this.nrBramkiOdlotu = nr_bramki_odlotu;
	}

	/**
	 * Getter zmiennej składowej czas odlotu.
	 * @return czas odlotu
	 */
	public String getCzasOdlotu()
	{
		return czasOdlotu;
	}


	/**
	 * Getter zmiennej składowej nazwa lotniska przylotu.
	 * @return nazwa lotniska przylotu
	 */
	public String getNazwaLotniskaPrzylotu()
	{
		return nazwaLotniskaPrzylotu;
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

	/**
	 * Getter zmiennej składowej numer terminala odlotu.
	 * @return numer terminala odlotu
	 */
	public String getNrTerminalaOdlotu()
	{
		return nrTerminalaOdlotu;
	}

	/**
	 * Getter zmiennej składowej numer bramki odlotu.
	 * @return numer bramki odlotu
	 */
	public String getNrBramkiOdlotu()
	{
		return nrBramkiOdlotu;
	}
}
