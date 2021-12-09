package model;

import baza.DataBase;

/**
 * Klasa modelu aplikacj
 * 
 * @author Kryczka_Rancew
 *
 */
public class AppModel {

	private DataBase patientList;

	/**
	 * Bazowy konstruktor klasy
	 */
	public AppModel() {
		try {
			this.patientList = new DataBase();
		} catch (Exception e1) {
			System.out.println("Problem ze stworzeniem bazy danych, o bledzie:" + e1);
		}
	}

	/**
	 * Funkcja, ktora liczy statystyki dla testow danego pacjenta
	 * 
	 * @param p obiekt typu PatientData, ktory przechowuje dane pacjenta
	 * @return tablica wynikow obliczen, ktora zawiera: minimalna wartosc,
	 *         maksymalna wartosc oraz srednia wartosc cisnienia rozkurczowego oraz
	 *         minimalna wartosc, maksymalna wartosc oraz srednia wartosc cisnienia
	 *         skurczowego
	 */
	public double[] bloodPressureStatistics(PatientData p) {

		int[] bP1 = this.patientList.getBP1(p);
		int[] bP2 = this.patientList.getBP2(p);

		int bP1_min = p.minValue(bP1);
		int bP1_max = p.maxValue(bP1);
		double bP1_av = p.averageValue(bP1);

		int bP2_min = p.minValue(bP2);
		int bP2_max = p.maxValue(bP2);
		double bP2_av = p.averageValue(bP2);

		double[] bPstatistics = { bP1_min, bP1_max, bP1_av, bP2_min, bP2_max, bP2_av };
		return bPstatistics;
	}

	/**
	 * Funkcja, ktora liczy statystyki dla testow danego pacjenta
	 * 
	 * @param p obiekt typu PatientData, ktory przechowuje dane pacjenta
	 * @return tablica wynikow obliczen, ktora zawiera: minimalna wartosc,
	 *         maksymalna wartosc oraz srednia wartosc têtna pacjenta
	 */
	public double[] heartRateStatistics(PatientData p) {
		int[] hR = this.patientList.getHR(p);

		int hR_min = p.minValue(hR);
		int hR_max = p.maxValue(hR);
		double hR_av = p.averageValue(hR);

		double[] hRstatistics = { hR_min, hR_max, hR_av };
		return hRstatistics;
	}

	/**
	 * Funkcja, ktora umozliwia poprawn¹ obs³ugê ID pacjentow
	 * 
	 * @return najwy¿sze ID
	 */
	public int size() {
		return patientList.getLastId();
	}

	/**
	 * Funkcja, ktora przez komunikacje modelem z baz¹ danych, zwraca pakiet danych
	 * pacjenta jako obiekt PatientData
	 * 
	 * @param ID ID pacjenta
	 * @return pacjent o podanym ID
	 */
	public PatientData getPatient(int ID) {
		if (ID > 0 && this.getPatientList().checkId(ID) == 1) {

			return (this.patientList.getPatient(ID));
		} else {
			return (null);
		}
	}

	/**
	 * Funkcja, ktora posredniczy w dodawaniu pacjenta
	 * 
	 * @param patient Obiekt patientData, ktory ma byc wprowadzony do bazy
	 * @return Obiekt patientData, ktory zostal wprowadzony do bazy, z aktualnym ID
	 */
	public PatientData addPatient(PatientData patient) {

		return patientList.addPatient(patient);
	}

	/**
	 * Funkcja, ktora posredniczy w usuwaniu pacjenta
	 * 
	 * @param index ID pacjenta
	 */
	public void remove(int index) {
		this.patientList.deletePatient(index);
	}

	/**
	 * Funkcja, ktora pozwala na komunikacje z baza danych
	 * 
	 * @return baza danych
	 */
	public DataBase getPatientList() {

		return this.patientList;
	}

}
