package model;

/**
 * Klasa reprezentujaca dane pacjenta
 * 
 * @author Kryczka_Rancew
 *
 */
public class PatientData {

	private String name = null;
	private String surname = null;
	private String birthPlace = null;
	private String pesel = null;
	private int patientID = 1;
	private int testNumber = 0;

	/**
	 * Konstruktor dla klasy
	 * 
	 * @param name       imie jako String
	 * @param surname    nazwisko jako String
	 * @param birthPlace miejsce urodzenia jako String
	 * @param p          pesel jako String
	 */
	public PatientData(String name, String surname, String birthPlace, String p) {
		super();
		this.name = name;
		this.surname = surname;
		this.birthPlace = birthPlace;

		this.pesel = p;

	}

	/**
	 * Bazowy konstruktor
	 */
	public PatientData() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public int getPatientID() {
		return patientID;
	}

	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}

	public int getTestNumber() {
		return this.testNumber;
	}

	public void setTestNumber(int testNumber) {
		this.testNumber = testNumber;
	}

	@Override
	/**
	 * Funkcja, ktora zwraca obiekt PatientData jako String
	 */
	public String toString() {
		return "PatientData [name=" + name + ", surname=" + surname + ", birthPlace=" + birthPlace + ", pesel=" + pesel
				+ ", testNumber=" + testNumber + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthPlace == null) ? 0 : birthPlace.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + patientID;
		result = prime * result + ((pesel == null) ? 0 : pesel.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + testNumber;
		return result;
	}

	@Override
	/**
	 * Funkcja, ktora umozliwia porywnywanie dwoch pacjentow
	 * 
	 * @param obj obiekt klasy PatientData
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatientData other = (PatientData) obj;

		if (birthPlace == null) {
			if (other.birthPlace != null)
				return false;
		} else if (!birthPlace.equals(other.birthPlace))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (patientID != other.patientID)
			return false;
		if (!pesel.equals(other.pesel))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (testNumber != other.testNumber)
			return false;
		return true;
	}

	/**
	 * Funkcja, ktora liczy najmniejsza wartosc z tablicy badan w postaci zmiennych
	 * int
	 * 
	 * @param inputData tablica badan w postaci zmiennych int
	 * @return najmniejsza wartosc z tablicy, jako int
	 */
	public int minValue(int[] inputData) {
		int length = inputData.length;
		int minVal = inputData[0];
		for (int i : inputData) {
			if (i < minVal) {
				minVal = i;
			}
		}

		return (length == 0) ? 0 : minVal;
	}

	/**
	 * Funkcja, ktora liczy najwieksza wartosc z tablicy badan w postaci zmiennych
	 * int
	 * 
	 * @param inputData tablica badan w postaci zmiennych int
	 * @return najwieksza wartosc z tablicy, jako int
	 */
	public int maxValue(int[] inputData) {
		int length = inputData.length;
		int maxVal = inputData[0];
		for (int i : inputData) {
			if (i > maxVal) {
				maxVal = i;
			}
		}

		return (length == 0) ? 0 : maxVal;
	}

	/**
	 * Funkcja, ktora liczy srednia wartosc z tablicy badan w postaci zmiennych int
	 * 
	 * @param inputData tablica badan w postaci zmiennych int
	 * @return srednia wartosc z tablicy, jako int
	 */
	public double averageValue(int[] inputData) {
		int length = inputData.length;
		double sum = 0;

		for (int i : inputData) {
			sum += i;
		}
		double averageVal = sum / length;
		return (length == 0) ? 0 : averageVal;
	}

}
