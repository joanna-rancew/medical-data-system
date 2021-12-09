package baza;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import model.PatientData;
import model.TestData;

/**
 * Klasa odpowiadajaca za komunikacje z baza danych
 * 
 * @author Kryczka_Rancew
 *
 */
public class DataBase {

	public static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String DB_URL = "jdbc:derby:MojaBazaDanych;create=true";
	public static final String DB_CLOSE_URL = "jdbc:derby:MojaBazaDanych;shutdown=true";
	public static final String DB_USER = "";
	public static final String DB_PASSWORD = "";

	private int currentPatientId = 1;

	/**
	 * Bazowy konstruktor dla klasy
	 */
	public DataBase() {
		this.createDataBase();
	}

	/**
	 * Funkcja tworzaca baze danych, jesli nie istnieje
	 */
	public void createDataBase() {
		/*
		 * Rejestrowanie sterownika bazy danych
		 */
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Sterownik bazy danych zostal wczytany\n");
		} catch (ClassNotFoundException e) {
			System.out.println("Blad przy wczytywaniu sterownika\n" + e);
			e.printStackTrace();
			System.exit(1);
		}

		// Tworzenie polaczenia z baza danych
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			System.out.println("Polaczenie z baza danych: " + conn.getMetaData().getURL());

			// Wykonywanie polecen SQL (tworzenie tabel)
			try (Statement stmt = conn.createStatement()) {
				// UWAGA USUWAMY, JESLI ISTNIEJA TABELE - opcja nieidealna
				try {
					if (ifTableExists(conn, "Tests")) {
//						this.currentPatientId = this.getLastId() + 1; //przygotowane pod prace z gotowymi tabelami
						stmt.execute("DROP TABLE Tests");
					}
					if (ifTableExists(conn, "Patients")) {
						stmt.execute("DROP TABLE Patients");
					}

				} catch (Exception e) {
					System.out.println("Blad przy pobieraniu danych z istniejacej bazy danych\n");
				}

				if (!ifTableExists(conn, "Patients")) {
					stmt.execute("CREATE TABLE Patients" + "(" + "PatientId		INTEGER NOT NULL PRIMARY KEY"
							+ "				GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)," + // Derby
							"Name			VARCHAR(128) NOT NULL," + "Surname			VARCHAR(128) NOT NULL,"
							+ "BirthPlace			VARCHAR(128) NOT NULL,"
							+ "Pesel			CHAR(11) NOT NULL UNIQUE," + "TestNumber			INTEGER" + ")");

					System.out.println("Utworzono tabele Patients\n");
				}

				if (!ifTableExists(conn, "Tests")) {
					stmt.execute("CREATE TABLE Tests" + "(" + "TestId		INTEGER NOT NULL PRIMARY KEY"
							+ "				GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),"
							+ "BloodPressure1		INTEGER NOT NULL," + "BloodPressure2		INTEGER NOT NULL,"
							+ "HeartRate		INTEGER NOT NULL,"
							+ "PatientId		INTEGER NOT NULL REFERENCES Patients(PatientId)" + ")");

					System.out.println("Utworzono tabele Tests\n");
				}
			} catch (

			SQLException e) {
				System.out.println("Blad przy wykonywaniu polecenia tworzenia tabel\n");
				e.printStackTrace();
			}
			conn.close();
		} catch (

		SQLException se) {

			System.out.println("Blad przy tworzeniu polaczenia\n");
			se.printStackTrace();
		}

	}

	/**
	 * Funkcja przygotowana pod zamykanie bazy danych
	 */
	public void closeDataBase() {

		try {
			DriverManager.getConnection(DB_CLOSE_URL);
		} catch (SQLException se) {
			if (!(se.getErrorCode() == 50000) && (se.getSQLState().equals("XJ015")))
				System.err.println(se);
		}

	}

	/**
	 * Funkcja sprawdzajaca czy istnieje tabela o podanej nazwie
	 * 
	 * @param conn      otwarte polaczenie
	 * @param tableName nazwa tabeli
	 * @return wartosc logiczna, true - istnieje, false - nie istnieje
	 * @throws SQLException blad komunikacji z baza w jezyku SQL
	 */
	public static boolean ifTableExists(Connection conn, String tableName) throws SQLException {
		boolean exists = false;

		DatabaseMetaData dbmd = conn.getMetaData();

		try (ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase(), null)) {
			exists = rs.next();
		}

		return (exists);
	}

	/**
	 * Funkcja dodajaca nowegu pacjenta
	 * 
	 * @return patient obiekt typu PatientData, ktory zostal wprowadzony do bazy
	 * @param patient obiekt typu PatientData, ktory ma zostac wprowadzony do bazy
	 */
	public PatientData addPatient(PatientData patient) {

		// Tworzenie polaczenia z baza danych

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			// dodawanie wpisow do tabeli pacjentow

			try (PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO Patients (Name,Surname,BirthPlace,Pesel,TestNumber) VALUES (?,?,?,?,?)")) {
				pstmt.setString(1, patient.getName());
				pstmt.setString(2, patient.getSurname());
				pstmt.setString(3, patient.getBirthPlace());
				pstmt.setString(4, patient.getPesel());
				pstmt.setInt(5, patient.getTestNumber());
				pstmt.executeUpdate();
				System.out.println("\nPoprawnie wykonano polecenie dodawania nowego pacjenta o peselu: "
						+ patient.getPesel() + "\n");
				patient.setPatientID(this.currentPatientId);
				this.incremetCurrentPatientId();

				this.printDataBase();
				conn.close();
				return patient;

			} catch (SQLIntegrityConstraintViolationException e) {
				System.out.println("Blad \n");
				e.printStackTrace();
				conn.close();
				return null;

			} catch (SQLException e) {
				System.out.println("Blad przy wykonywaniu polecenia dodawania nowego pacjenta\n");
				e.printStackTrace();
				conn.close();
				return null;

			}

		} catch (SQLException e) {
			System.out.println("Blad przy tworzeniu polaczenia\n");
			e.printStackTrace();
			return null;

		}

	}

	/**
	 * 
	 * @param patientPesel pesel do sprawdzenia
	 * @return parametr bledu: 1 - pesel sie powtarza, 2 - wystapil blad dzialania,
	 *         0 - poprawne dzialanie i dane
	 */
	public int checkPesel(String patientPesel) {

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			/*
			 * Wykonywanie polecen SQL (wstawianie danych)
			 */
			try (Statement stmt = conn.createStatement()) {

				try (ResultSet rs = stmt.executeQuery("SELECT * FROM Patients")) {

					while (rs.next()) {
						String x = rs.getString("Pesel");
						if (x.equals(patientPesel)) {
							conn.close();
							return 1;
						}
					}
					conn.close();
					return 0;
				} catch (SQLException e) {
					System.out.println("Blad przy wykonywaniu polecenia\n");
					e.printStackTrace();
					conn.close();
					return 2;
				}
			} catch (SQLException e) {
				System.out.println("Blad przy wykonywaniu polecenia\n");
				e.printStackTrace();
				conn.close();
				return 2;
			}

		} catch (SQLException e1) {
			System.out.println("Blad przy wykonywaniu polecenia\n");
			e1.printStackTrace();

			return 2;
		}

	}

	public int checkId(int patientId) {

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			try (Statement stmt = conn.createStatement()) {

				try (ResultSet rs = stmt.executeQuery("SELECT * FROM Patients")) {

					while (rs.next()) {
						int x = rs.getInt("PatientId");
						if (x == patientId) {
							conn.close();
							return 1;
						}
					}
					conn.close();
					return 0;
				} catch (SQLException e) {
					System.out.println("Blad przy wykonywaniu polecenia pobierania ostatniego indeksu\n");
					e.printStackTrace();
					conn.close();
					return 2;
				}
			} catch (SQLException e) {
				System.out.println("Blad przy wykonywaniu polecenia\n");
				e.printStackTrace();
				conn.close();
				return 2;
			}

		} catch (SQLException e1) {
			System.out.println("Blad przy wykonywaniu polecenia\n");
			e1.printStackTrace();
			return 2;
		}

	}

	/**
	 * funkcja, ktora ma wprowadzic test do bazy danych
	 * 
	 * @param test    obiekt typu TestData - test do wprowadzenia
	 * 
	 * @param patient obiekt typu PatientData od pacjenta, ktorego test wprowadzamy
	 */
	public void addTest(TestData test, PatientData patient) {

		// Tworzenie polaczenia z baza danych

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			// dodawanie wpisu do tabeli testow
			try (PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO Tests (BloodPressure1,BloodPressure2,HeartRate,PatientId) VALUES (?,?,?,?)")) {
				stmt.setInt(1, test.getBloodPressure1());
				stmt.setInt(2, test.getBloodPressure2());
				stmt.setInt(3, test.getHeartRate());
				stmt.setInt(4, patient.getPatientID());
				stmt.executeUpdate();

				System.out.println("Poprawnie wykonano polecenie dodawania nowego testu dla pacjenta o ID: "
						+ patient.getPatientID() + "\n");
				int tests = patient.getTestNumber();

				tests = tests + 1;

				patient.setTestNumber(tests);

			} catch (SQLException e) {
				System.out.println("Blad przy wykonywaniu polecenia dodawania nowego testu\n");
				e.printStackTrace();
			}

			try (PreparedStatement stmt = conn
					.prepareStatement("UPDATE Patients SET TestNumber = ? WHERE PatientId = ?")) {
				stmt.setInt(1, patient.getTestNumber());
				stmt.setInt(2, patient.getPatientID());

				stmt.executeUpdate();
				System.out.println("Poprawnie wykonano polecenie aktualizowania TestNumber dla pacjenta o ID: "
						+ patient.getPatientID() + ", testy: " + patient.getTestNumber() + "\n");
				this.printDataBase();

			} catch (SQLException e) {
				System.out.println("Blad przy wykonywaniu polecenia\n");
				e.printStackTrace();
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println("Blad przy tworzeniu polaczenia\n");
			e.printStackTrace();
		}

	}

	/**
	 * funkcja, ktora usuwa dane pacjenta oraz jego testy z bazy w oparciu o jego ID
	 * 
	 * @param patientId ID pacjenta, ktorego dane maja byc usuniete
	 */

	public void deletePatient(int patientId) {

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			// usuwanie z obu tabel wszystkich rekordow zwiazanych z pacjentem
			if (patientId != 0) {
				try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Tests WHERE PatientId=?")) {
					stmt.setInt(1, patientId);
					int r = stmt.executeUpdate();
					System.out.println("Liczba usunietych rekordow tabeli Tests: " + r);
				} catch (SQLException e) {
					System.out.println("Blad przy wykonywaniu polecania usuwania testow dla pacjenta\n");
					e.printStackTrace();
				}

				try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Patients WHERE PatientId=?")) {
					stmt.setInt(1, patientId);

					int r = stmt.executeUpdate();
					System.out.println("Liczba usunietych rekordow tabeli Patients: " + r);

				} catch (SQLException e) {
					System.out.println("Blad przy wykonywaniu polecania usuwania danych pacjenta\n");
					e.printStackTrace();
				}
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println("Blad przy tworzeniu polaczenia\n");
			e.printStackTrace();
		}

	}

	/**
	 * funkcja, ktora uaktualnia podstawowe dane pacjenta o jego ID
	 * 
	 * @param patientId      ID pacjenta, ktorego dane sa aktualizowane
	 * @param updatedPatient obiekt pacjenta, ktorego dane maja byc zaktualizowane
	 */
	public void updatePatient(int patientId, PatientData updatedPatient) {

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			// pobieranie danych
			try (PreparedStatement stmt = conn.prepareStatement("UPDATE Patients SET Name = '"
					+ updatedPatient.getName() + "', Surname = '" + updatedPatient.getSurname() + "', BirthPlace = '"
					+ updatedPatient.getBirthPlace() + "', Pesel = '" + updatedPatient.getPesel() + "', TestNumber = "
					+ updatedPatient.getTestNumber() + " WHERE PatientId = ?")) {

				stmt.setInt(1, patientId);
				stmt.executeUpdate();
				System.out.println("Poprawnie wykonano polecenie aktualizowania pacjenta o ID: " + patientId + "\n");
				this.printDataBase();

			} catch (SQLException e) {
				System.out.println("Blad przy wykonywaniu polecenia aktualizowania danych pacjenta\n");
				e.printStackTrace();
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println("Blad przy tworzeniu polaczenia\n");
			e.printStackTrace();
		}

	}

	/**
	 * Pomocnicza funkca, ktora pozwala na biezaco sprawdzic wartosci w bazie danych
	 */
	public void printDataBase() {

		/*
		 * Tworzenie polaczenia z baza danych
		 */
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			/*
			 * Wykonywanie polecen SQL (zapytania)
			 */
			try (Statement stmt = conn.createStatement()) {
				// pobranie rekordow tabeli pacjentow
				try (ResultSet rs = stmt.executeQuery("SELECT * FROM Patients")) {
					System.out.println("\n--------------------------------------------------------");
					System.out.println("Rekordy tabeli Patients:");
					while (rs.next())
						System.out.println(rs.getString("PatientId") + "\t" + rs.getString("Name") + "\t"
								+ rs.getString("Surname") + "\t" + rs.getString("BirthPlace") + "\t"
								+ rs.getString("Pesel") + "\t" + rs.getString("TestNumber"));
					System.out.println("--------------------------------------------------------\n");
				}

			} catch (SQLException e) {
				System.out.println("Blad przy wykonywaniu polecania wyswietlania tabeli pacjentow\n");
				e.printStackTrace();
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println("Blad przy tworzeniu polaczenia\n");
			e.printStackTrace();
		}
	}

	/**
	 * Funkcja zwracajaca wyniki pomiaru têtna pacjenta
	 * 
	 * @param patient obiekt PatientData wybranego pacjenta
	 * @return tabela wyników pomiaru têtna w formacie int
	 */
	public int[] getHR(PatientData patient) {
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Sterownik bazy danych zostal wczytany\n");
		} catch (ClassNotFoundException e) {
			System.out.println("Blad przy wczytywaniu sterownika\n" + e);
			e.printStackTrace();
			System.exit(1);
		}

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			try (Statement stmt = conn.createStatement()) {
				int tNumber = patient.getTestNumber();
				int[] data = new int[tNumber];

				try (ResultSet rs = stmt
						.executeQuery("SELECT * FROM Tests WHERE PatientId = " + patient.getPatientID())) {
					int i = 0;

					while (rs.next()) {
						data[i] = rs.getInt("HeartRate");

						i++;
					}
					conn.close();
					return data;
				} catch (SQLException e) {
					System.out.println("Blad przy wykonywaniu polecania wybierania wynikow tetna\n");
					e.printStackTrace();
				}
			} catch (SQLException e) {
				System.out.println("Blad przy wykonywaniu polecania\n");
				e.printStackTrace();
			}

		}

		catch (

		SQLException e) {
			System.out.println("Blad przy tworzeniu polaczenia\n");
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Funkcja zwracajaca wyniki pomiaru cisnienia skurczowego pacjenta
	 * 
	 * @param patient obiekt PatientData wybranego pacjenta
	 * @return tabela wyników pomiaru cisnienia skurczowego w formacie int
	 */
	public int[] getBP1(PatientData patient) {
		int tNumber = patient.getTestNumber();
		int[] data = new int[tNumber];

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			try (Statement stmt = conn.createStatement()) {
				try (ResultSet rs = stmt
						.executeQuery("SELECT * FROM Tests WHERE PatientId = " + patient.getPatientID())) {
					int i = 0;

					while (rs.next()) {
						data[i] = rs.getInt("BloodPressure1");
						i++;
					}
				} catch (SQLException e) {
					System.out.println("Blad przy wykonywaniu polecania wybierania wynikow cisnienia\n");
					e.printStackTrace();
				}
			} catch (SQLException e) {
				System.out.println("Blad przy wykonywaniu polecania\n");
				e.printStackTrace();
			}

		}

		catch (SQLException e) {
			System.out.println("Blad przy tworzeniu polaczenia\n");
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * Funkcja zwracajaca wyniki pomiaru cisnienia rozkurczowego pacjenta
	 * 
	 * @param patient obiekt PatientData wybranego pacjenta
	 * @return tabela wyników pomiaru cisnienia rozkurczowego w formacie int
	 */
	public int[] getBP2(PatientData patient) {
		int tNumber = patient.getTestNumber();
		int[] data = new int[tNumber];

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			try (Statement stmt = conn.createStatement()) {

				try (ResultSet rs = stmt
						.executeQuery("SELECT * FROM Tests WHERE PatientId = " + patient.getPatientID())) {
					int i = 0;

					while (rs.next()) {
						data[i] = rs.getInt("BloodPressure2");
						i++;
					}
				} catch (SQLException e) {
					System.out.println("Blad przy wykonywaniu polecania wybierania wynikow cisnienia\n");
					e.printStackTrace();
				}
			} catch (SQLException e) {
				System.out.println("Blad przy wykonywaniu polecania\n");
				e.printStackTrace();
			}

			conn.close();
		}

		catch (SQLException e) {
			System.out.println("Blad przy tworzeniu polaczenia\n");
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * Funkcja, ktora pozwala na sprawdzenie ostatniego ID w bazie pacjentow
	 * 
	 * @return najwyzsze ID
	 */
	public int getLastId() {

		int idx = 0;

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			try (Statement stmt = conn.createStatement()) {

				try (ResultSet rs = stmt.executeQuery(
						"SELECT * FROM Patients WHERE PatientId = (" + "SELECT max(PatientId) FROM Patients)")) {

					while (rs.next()) {
						idx = rs.getInt("PatientId");

					}

				} catch (SQLException e) {
					System.out.println("Blad przy wykonywaniu polecenia pobierania ostatniego indeksu\n");
					e.printStackTrace();
				}
			} catch (SQLException e) {
				System.out.println("Blad przy wykonywaniu polecania\n");
				e.printStackTrace();
			}
			conn.close();
		}

		catch (SQLException e) {
			System.out.println("Blad przy tworzeniu polaczenia\n");
			e.printStackTrace();
		}

		return idx;
	}

	/**
	 * Funkcja, ktora zwraca wszystkie dane pacjenta
	 * 
	 * @param idx ID Pacjenta
	 * @return obiekt PatientData zawierajace wszystkie dane pacjenta o wybranym ID
	 */
	public PatientData getPatient(int idx) {
		PatientData patient = new PatientData();
		patient.setPatientID(idx);

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			try (Statement stmt = conn.createStatement()) {

				try (ResultSet rs = stmt.executeQuery("SELECT * FROM Patients WHERE PatientId = " + idx)) {

					while (rs.next()) {
						patient.setName(rs.getString("Name"));

						patient.setSurname(rs.getString("Surname"));
						patient.setBirthPlace(rs.getString("BirthPlace"));
						patient.setPesel(rs.getString("Pesel"));
						patient.setTestNumber(rs.getInt("TestNumber"));

					}
					conn.close();
					return patient;

				} catch (SQLException e) {
					System.out.println("Blad przy wykonywaniu polecania wybierania pacjenta\n");
					e.printStackTrace();
				}
			} catch (SQLException e) {
				System.out.println("Blad przy wykonywaniu polecenia\n");
				e.printStackTrace();
			}
			conn.close();
		}

		catch (SQLException e) {
			System.out.println("Blad przy tworzeniu polaczenia\n");
			e.printStackTrace();
		}

		return null;
	}

	public void incremetCurrentPatientId() {
		this.currentPatientId = this.currentPatientId + 1;
	}
}
