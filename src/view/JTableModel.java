package view;

import javax.swing.table.AbstractTableModel;

import model.AppModel;
import model.PatientData;

/**
 * Klasa modelu tabeli Table
 * 
 * @author Kryczka_Rancew
 *
 */

public class JTableModel extends AbstractTableModel {
	/** Tablica nazw kolumn */
	private final String COLUMN_NAMES[] = { "ID", "Imie", "Nazwisko", "PESEL", "Miejsce urodzenia" };
	/** Model danych */
	private AppModel model = null;

	/**
	 * Bazowy konstruktor
	 * 
	 * @param m model danych
	 */
	public JTableModel(AppModel m) {
		this.model = m;
	}

	@Override
	/**
	 * Obliczanie liczby wierszy
	 */
	public int getRowCount() {
		if (this.model.size() < 10)
			return (10);
		else
			return (this.model.size());
	}

	@Override
	/**
	 * Obliczanie liczby kolumn
	 */
	public int getColumnCount() {
		return (COLUMN_NAMES.length);
	}

	public String getColumnName(int columnIndex) {
		return (COLUMN_NAMES[columnIndex]);
	}

	@Override
	/**
	 * Uzupelnianie tabeli
	 * 
	 * @param index       numer wiersza, ktory odpowiada PatientId - 1
	 * @param columnIndex numer kolumny
	 */
	public Object getValueAt(int index, int columnIndex) {
		index = index + 1;
		if (index <= this.model.size() && this.model.getPatientList().checkId(index) == 1) {
			PatientData p = this.model.getPatient(index);

			if (columnIndex == 0)
				return (p.getPatientID());
			else if (columnIndex == 1)
				return (p.getName());
			else if (columnIndex == 2)
				return (p.getSurname());
			else if (columnIndex == 3)
				return (p.getPesel());
			else if (columnIndex == 4)
				return (p.getBirthPlace());

		}
		return null;
	}

}
