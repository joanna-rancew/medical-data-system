package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import controller.AppController;
import model.AppModel;
import model.PatientData;
import model.TestData;

/**
 * Widok aplikacji
 * 
 * @author Kryczka_Rancew
 *
 */
public class AppView extends JFrame {
	private AppModel appModel = null;

	private JTextField nameTextField, surnameTextField, peselTextField, birthPlaceTextField, updateIDTextField,
			updateNameTextField, updateSurnameTextField, updatePeselTextField, updateBirthPlaceTextField,
			deleteIDTextField, addTestIDTextField, bloodPressureTextField, heartRateTextField;
	/** Przyciski sluzace do akceptacji wprowadzanych danych */
	private JButton addingButton, updateButton, deleteButton, addTestButton;
	private JTable listTable = null;
	private JPanel mainPanel;
	int currentPatientNumber = 1;
	private JLabel showNameTextField, showSurnameTextField, showPeselTextField, showBirthPlaceTextField;

	private JLabel uBP_min, uBP_max, uBP_av, lBP_min, lBP_max, lBP_av, HR_min, HR_max, HR_av;

	/**
	 * Konstruktor dla JFrame - kontenera nadrzednego
	 * 
	 * @param m model danych
	 */

	public AppView(AppModel m) {
		this.appModel = m;
		this.createGui();
	}

	private void createGui() {

		// dodawanie glownego panelu
		mainPanel = new JPanel();
		getMainPanel().setLayout(new BoxLayout(getMainPanel(), BoxLayout.Y_AXIS));
		this.getContentPane().add(getMainPanel());

		// panel dodawania nowego pacjenta
		getMainPanel().add(Box.createRigidArea(new Dimension(10, 10)));
		JPanel addingPanel = new JPanel();
		addingPanel.setBackground(new Color(243, 243, 243));
		addingPanel.setBorder(BorderFactory.createTitledBorder("Dodaj nowego pacjenta"));
		addingPanel.setLayout(new BoxLayout(addingPanel, BoxLayout.X_AXIS));

		this.addingButton = new JButton("Zatwierdz");
		this.addingButton.setActionCommand("addPatient");
		addingPanel.add(addingButton);
		addingPanel.add(Box.createRigidArea(new Dimension(5, 5)));

		addingPanel.add(new JLabel("Imiê"));
		addingPanel.add(Box.createRigidArea(new Dimension(3, 3)));

		this.nameTextField = new JTextField();
		this.nameTextField.setPreferredSize(new Dimension(80, 25));
		this.nameTextField.setMinimumSize(new Dimension(80, 25));
		this.nameTextField.setMaximumSize(new Dimension(80, 25));
		addingPanel.add(this.nameTextField);
		addingPanel.add(Box.createRigidArea(new Dimension(5, 5)));

		addingPanel.add(new JLabel("Nazwisko"));
		addingPanel.add(Box.createRigidArea(new Dimension(3, 3)));
		this.surnameTextField = new JTextField();
		this.surnameTextField.setPreferredSize(new Dimension(80, 25));
		this.surnameTextField.setMinimumSize(new Dimension(80, 25));
		this.surnameTextField.setMaximumSize(new Dimension(80, 25));
		addingPanel.add(this.surnameTextField);

		addingPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		addingPanel.add(new JLabel("Pesel"));
		addingPanel.add(Box.createRigidArea(new Dimension(3, 3)));
		this.peselTextField = new JTextField(11);
		this.peselTextField.setPreferredSize(new Dimension(80, 25));
		this.peselTextField.setMinimumSize(new Dimension(80, 25));
		this.peselTextField.setMaximumSize(new Dimension(80, 25));
		addingPanel.add(this.peselTextField);

		addingPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		addingPanel.add(new JLabel("Miejsce urodzenia"));
		addingPanel.add(Box.createRigidArea(new Dimension(3, 3)));
		this.birthPlaceTextField = new JTextField();
		this.birthPlaceTextField.setPreferredSize(new Dimension(80, 25));
		this.birthPlaceTextField.setMinimumSize(new Dimension(80, 25));
		this.birthPlaceTextField.setMaximumSize(new Dimension(80, 25));
		addingPanel.add(this.birthPlaceTextField);
		getMainPanel().add(addingPanel);

		// panel aktualizacji danych
		getMainPanel().add(Box.createRigidArea(new Dimension(10, 10)));

		JPanel updatePanel = new JPanel();
		updatePanel.setBackground(new Color(243, 243, 243));
		updatePanel.setBorder(BorderFactory.createTitledBorder("Aktualizuj dane pacjenta"));
		updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.X_AXIS));

		this.updateButton = new JButton("Zatwierdz");
		this.updateButton.setActionCommand("updatePatient");
		updatePanel.add(updateButton);
		updatePanel.add(Box.createRigidArea(new Dimension(5, 5)));
		updatePanel.add(new JLabel("ID"));

		updatePanel.add(Box.createRigidArea(new Dimension(3, 3)));
		this.updateIDTextField = new JTextField();
		this.updateIDTextField.setPreferredSize(new Dimension(80, 25));
		this.updateIDTextField.setMinimumSize(new Dimension(80, 25));
		this.updateIDTextField.setMaximumSize(new Dimension(80, 25));
		updatePanel.add(this.updateIDTextField);

		updatePanel.add(Box.createRigidArea(new Dimension(5, 5)));
		updatePanel.add(new JLabel("Imiê"));
		updatePanel.add(Box.createRigidArea(new Dimension(3, 3)));

		this.updateNameTextField = new JTextField();
		this.updateNameTextField.setPreferredSize(new Dimension(80, 25));
		this.updateNameTextField.setMinimumSize(new Dimension(80, 25));
		this.updateNameTextField.setMaximumSize(new Dimension(80, 25));
		updatePanel.add(this.updateNameTextField);

		updatePanel.add(Box.createRigidArea(new Dimension(5, 5)));
		updatePanel.add(new JLabel("Nazwisko"));
		updatePanel.add(Box.createRigidArea(new Dimension(3, 3)));

		this.updateSurnameTextField = new JTextField();
		this.updateSurnameTextField.setPreferredSize(new Dimension(80, 25));
		this.updateSurnameTextField.setMinimumSize(new Dimension(80, 25));
		this.updateSurnameTextField.setMaximumSize(new Dimension(80, 25));
		updatePanel.add(this.updateSurnameTextField);

		updatePanel.add(Box.createRigidArea(new Dimension(5, 5)));
		updatePanel.add(new JLabel("Pesel"));
		updatePanel.add(Box.createRigidArea(new Dimension(3, 3)));

		this.updatePeselTextField = new JTextField(11);
		this.updatePeselTextField.setPreferredSize(new Dimension(80, 25));
		this.updatePeselTextField.setMinimumSize(new Dimension(80, 25));
		this.updatePeselTextField.setMaximumSize(new Dimension(80, 25));
		updatePanel.add(this.updatePeselTextField);

		updatePanel.add(Box.createRigidArea(new Dimension(5, 5)));
		updatePanel.add(new JLabel("Miejsce urodzenia"));
		updatePanel.add(Box.createRigidArea(new Dimension(3, 3)));

		this.updateBirthPlaceTextField = new JTextField();
		this.updateBirthPlaceTextField.setPreferredSize(new Dimension(80, 25));
		this.updateBirthPlaceTextField.setMinimumSize(new Dimension(80, 25));
		this.updateBirthPlaceTextField.setMaximumSize(new Dimension(80, 25));
		updatePanel.add(this.updateBirthPlaceTextField);

		getMainPanel().add(updatePanel);
		getMainPanel().add(Box.createRigidArea(new Dimension(10, 10)));

		// panel usuwania danych pacjenta
		JPanel deletePanel = new JPanel();
		deletePanel.setBackground(new Color(243, 243, 243));
		deletePanel.setBorder(BorderFactory.createTitledBorder("Usuñ dane pacjenta"));
		deletePanel.setLayout(new BoxLayout(deletePanel, BoxLayout.X_AXIS));
		this.deleteButton = new JButton("Zatwierdz");
		this.deleteButton.setActionCommand("deletePatient");
		deletePanel.add(this.deleteButton);
		deletePanel.add(Box.createRigidArea(new Dimension(5, 5)));
		deletePanel.add(new JLabel("ID"));
		deletePanel.add(Box.createRigidArea(new Dimension(3, 3)));
		this.deleteIDTextField = new JTextField();
		this.deleteIDTextField.setPreferredSize(new Dimension(80, 25));
		this.deleteIDTextField.setMinimumSize(new Dimension(80, 25));
		this.deleteIDTextField.setMaximumSize(new Dimension(80, 25));
		deletePanel.add(this.deleteIDTextField);
		getMainPanel().add(deletePanel);
		getMainPanel().add(Box.createRigidArea(new Dimension(10, 10)));

		// panel dodawania nowego pomiaru (badania)
		JPanel addTestPanel = new JPanel();
		addTestPanel.setBackground(new Color(243, 243, 243));
		addTestPanel.setBorder(BorderFactory.createTitledBorder("Dodaj nowy pomiar"));
		addTestPanel.setLayout(new BoxLayout(addTestPanel, BoxLayout.X_AXIS));
		this.addTestButton = new JButton("Zatwierdz");
		this.addTestButton.setActionCommand("addTest");
		addTestPanel.add(this.addTestButton);
		addTestPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		addTestPanel.add(new JLabel("ID pacjenta"));
		addTestPanel.add(Box.createRigidArea(new Dimension(3, 3)));
		this.addTestIDTextField = new JTextField();
		this.addTestIDTextField.setPreferredSize(new Dimension(80, 25));
		this.addTestIDTextField.setMinimumSize(new Dimension(80, 25));
		this.addTestIDTextField.setMaximumSize(new Dimension(80, 25));
		addTestPanel.add(this.addTestIDTextField);
		addTestPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		addTestPanel.add(new JLabel("Ciœnienie"));
		addTestPanel.add(Box.createRigidArea(new Dimension(3, 3)));
		this.bloodPressureTextField = new JTextField();
		this.bloodPressureTextField.setPreferredSize(new Dimension(80, 25));
		this.bloodPressureTextField.setMinimumSize(new Dimension(80, 25));
		this.bloodPressureTextField.setMaximumSize(new Dimension(80, 25));
		addTestPanel.add(this.bloodPressureTextField);
		addTestPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		addTestPanel.add(new JLabel("Têtno"));
		addTestPanel.add(Box.createRigidArea(new Dimension(3, 3)));
		this.heartRateTextField = new JTextField();
		this.heartRateTextField.setPreferredSize(new Dimension(80, 25));
		this.heartRateTextField.setMinimumSize(new Dimension(80, 25));
		this.heartRateTextField.setMaximumSize(new Dimension(80, 25));
		addTestPanel.add(this.heartRateTextField);

		getMainPanel().add(addTestPanel);
		getMainPanel().add(Box.createRigidArea(new Dimension(20, 20)));

		// panel listy pacjentow
		JPanel listPanel = new JPanel();
		listPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Lista pacjentow do wyboru", TitledBorder.CENTER, TitledBorder.TOP));
		listPanel.setBackground(new Color(243, 243, 243));

		JTableModel tableModel = new JTableModel(this.appModel);
		this.listTable = new JTable(tableModel);
		listPanel.add(listTable);
		getMainPanel().add(listPanel);

		getMainPanel().add(Box.createRigidArea(new Dimension(50, 20)));

		JScrollPane scroll = new JScrollPane(this.listTable);
		scroll.setPreferredSize(new Dimension(550, 180));
		scroll.setMinimumSize(new Dimension(550, 180));
		listPanel.add(scroll);

		this.setTitle("System do badan");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();

		try {

			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

			SwingUtilities.updateComponentTreeUI(this);
			this.pack();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Funkcja, ktora przygotowana byla pod poprawne zamykanie bazy danych
	 */
	public void close() {
		this.appModel.getPatientList().closeDataBase();
		System.out.println("Zamykam program");

	}

	/**
	 * Funkcja, ktora prezentuje w GUI wyniki badan pacjenta
	 * 
	 * @param p obiekt klasy PatientData, ktory przechowuje dane wybranego pacjenta
	 */
	public void showTestWindow(PatientData p) {
		JOptionPane.showMessageDialog(this.getMainPanel(), "Dane pacjenta zostan¹ wyœwietlone");

		JFrame showTestWindow = new JFrame("Wyniki badañ wybranego pacjenta");
		showTestWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		showTestWindow.setVisible(true);

		JPanel showMainPanel = new JPanel();
		showMainPanel.setLayout(new BoxLayout(showMainPanel, BoxLayout.Y_AXIS));
		showMainPanel.setVisible(true);

		showMainPanel.setPreferredSize(new Dimension(900, 500));
		showTestWindow.add(showMainPanel);

		showMainPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		JPanel patientDataPanel = new JPanel();
		patientDataPanel.setBorder(BorderFactory.createTitledBorder("Dane pacjenta"));
		patientDataPanel.setLayout(new BoxLayout(patientDataPanel, BoxLayout.X_AXIS));
		showMainPanel.add(patientDataPanel);

		patientDataPanel.add(new JLabel(" Imiê:  "));
		patientDataPanel.add(Box.createRigidArea(new Dimension(3, 3)));

		this.showNameTextField = new JLabel();
		this.showNameTextField.setPreferredSize(new Dimension(80, 25));
		this.showNameTextField.setMinimumSize(new Dimension(80, 25));
		this.showNameTextField.setMaximumSize(new Dimension(80, 25));
		this.showNameTextField.setBackground(Color.WHITE);
		this.showNameTextField.setVerticalAlignment(JLabel.CENTER);
		this.showNameTextField.setHorizontalAlignment(JLabel.CENTER);
		this.showNameTextField.setOpaque(true);

		patientDataPanel.add(this.showNameTextField);
		patientDataPanel.add(Box.createRigidArea(new Dimension(5, 5)));

		patientDataPanel.add(new JLabel(" Nazwisko:  "));
		patientDataPanel.add(Box.createRigidArea(new Dimension(3, 3)));
		this.showSurnameTextField = new JLabel();
		this.showSurnameTextField.setPreferredSize(new Dimension(80, 25));
		this.showSurnameTextField.setMinimumSize(new Dimension(80, 25));
		this.showSurnameTextField.setMaximumSize(new Dimension(80, 25));
		this.showSurnameTextField.setBackground(Color.WHITE);
		this.showSurnameTextField.setVerticalAlignment(JLabel.CENTER);
		this.showSurnameTextField.setHorizontalAlignment(JLabel.CENTER);
		this.showSurnameTextField.setOpaque(true);
		patientDataPanel.add(this.showSurnameTextField);

		patientDataPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		patientDataPanel.add(new JLabel(" Pesel:  "));
		patientDataPanel.add(Box.createRigidArea(new Dimension(3, 3)));
		this.showPeselTextField = new JLabel();
		this.showPeselTextField.setPreferredSize(new Dimension(80, 25));
		this.showPeselTextField.setMinimumSize(new Dimension(80, 25));
		this.showPeselTextField.setMaximumSize(new Dimension(80, 25));
		this.showPeselTextField.setBackground(Color.WHITE);
		this.showPeselTextField.setVerticalAlignment(JLabel.CENTER);
		this.showPeselTextField.setHorizontalAlignment(JLabel.CENTER);
		this.showPeselTextField.setOpaque(true);
		patientDataPanel.add(this.showPeselTextField);

		patientDataPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		patientDataPanel.add(new JLabel(" Miejsce urodzenia:  "));
		patientDataPanel.add(Box.createRigidArea(new Dimension(3, 3)));
		this.showBirthPlaceTextField = new JLabel();
		this.showBirthPlaceTextField.setPreferredSize(new Dimension(80, 25));
		this.showBirthPlaceTextField.setMinimumSize(new Dimension(80, 25));
		this.showBirthPlaceTextField.setMaximumSize(new Dimension(80, 25));
		this.showBirthPlaceTextField.setBackground(Color.WHITE);
		this.showBirthPlaceTextField.setVerticalAlignment(JLabel.CENTER);
		this.showBirthPlaceTextField.setHorizontalAlignment(JLabel.CENTER);
		this.showBirthPlaceTextField.setOpaque(true);

		patientDataPanel.add(this.showBirthPlaceTextField);

		// dolny panel na prezentacje wyników badañ
		JPanel testDataPanel = new JPanel();

		testDataPanel.setLayout(new BoxLayout(testDataPanel, BoxLayout.X_AXIS));
		showMainPanel.add(testDataPanel);

		// lewy panel: dane pacjenta, statystyki ciœnienia, statystyki pulsu
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		testDataPanel.add(leftPanel);

		// panel do prezentacji statystyk ciœnienia
		JPanel leftTopPanel = new JPanel();
		leftTopPanel.setBorder(BorderFactory.createTitledBorder("Statystyki ciœnienia"));
		leftPanel.add(leftTopPanel);

		leftTopPanel.setLayout(new GridLayout(6, 2, 2, 2));
		leftTopPanel.add(new JLabel("Upper Blood Pressure- Min: "));
		uBP_min = new JLabel();
		uBP_min.setVerticalAlignment(JLabel.CENTER);
		uBP_min.setHorizontalAlignment(JLabel.CENTER);
		uBP_min.setBackground(Color.WHITE);
		uBP_min.setOpaque(true);
		uBP_min.setText("100");
		leftTopPanel.add(uBP_min);

		leftTopPanel.add(new JLabel("Upper Blood Pressure- Max: "));
		uBP_max = new JLabel();
		uBP_max.setVerticalAlignment(JLabel.CENTER);
		uBP_max.setHorizontalAlignment(JLabel.CENTER);
		uBP_max.setBackground(Color.WHITE);
		uBP_max.setOpaque(true);
		uBP_max.setText("180");
		leftTopPanel.add(uBP_max);

		leftTopPanel.add(new JLabel("Upper Blood Pressure- Average: "));
		uBP_av = new JLabel();
		uBP_av.setVerticalAlignment(JLabel.CENTER);
		uBP_av.setHorizontalAlignment(JLabel.CENTER);
		uBP_av.setBackground(Color.WHITE);
		uBP_av.setOpaque(true);
		uBP_av.setText("140");
		leftTopPanel.add(uBP_av);

		leftTopPanel.add(new JLabel("Lower Blood Pressure- Min: "));
		lBP_min = new JLabel();
		lBP_min.setVerticalAlignment(JLabel.CENTER);
		lBP_min.setHorizontalAlignment(JLabel.CENTER);
		lBP_min.setBackground(Color.WHITE);
		lBP_min.setOpaque(true);
		lBP_min.setText("80");
		leftTopPanel.add(lBP_min);

		leftTopPanel.add(new JLabel("Lower Blood Pressure- Max: "));
		lBP_max = new JLabel();
		lBP_max.setVerticalAlignment(JLabel.CENTER);
		lBP_max.setHorizontalAlignment(JLabel.CENTER);
		lBP_max.setBackground(Color.WHITE);
		lBP_max.setOpaque(true);
		lBP_max.setText("120");
		leftTopPanel.add(lBP_max);

		leftTopPanel.add(new JLabel("Lower Blood Pressure- Avergae: "));
		lBP_av = new JLabel();
		lBP_av.setVerticalAlignment(JLabel.CENTER);
		lBP_av.setHorizontalAlignment(JLabel.CENTER);
		lBP_av.setBackground(Color.WHITE);
		lBP_av.setOpaque(true);
		lBP_av.setText("100");
		leftTopPanel.add(lBP_av);

		// panel do prezentacji statystyk pulsu
		JPanel leftBottomPanel = new JPanel();
		leftBottomPanel.setBorder(BorderFactory.createTitledBorder("Statystyki pulsu"));

		leftPanel.add(leftBottomPanel);

		leftBottomPanel.setLayout(new GridLayout(3, 2, 8, 2));
		leftBottomPanel.add(new JLabel("Heart Rate- Min: "));
		HR_min = new JLabel("20");
		HR_min.setVerticalAlignment(JLabel.CENTER);
		HR_min.setHorizontalAlignment(JLabel.CENTER);
		HR_min.setBackground(Color.WHITE);
		HR_min.setOpaque(true);
		HR_min.setText("70");

		leftBottomPanel.add(HR_min);

		leftBottomPanel.add(new JLabel("Heart Rate- Max: "));
		HR_max = new JLabel();
		HR_max.setVerticalAlignment(JLabel.CENTER);
		HR_max.setHorizontalAlignment(JLabel.CENTER);
		HR_max.setBackground(Color.WHITE);
		HR_max.setOpaque(true);
		HR_max.setText("110");

		leftBottomPanel.add(HR_max);

		leftBottomPanel.add(new JLabel("Heart Rate- Average: "));
		HR_av = new JLabel();
		HR_av.setVerticalAlignment(JLabel.CENTER);
		HR_av.setHorizontalAlignment(JLabel.CENTER);
		HR_av.setBackground(Color.WHITE);
		HR_av.setOpaque(true);
		HR_av.setText("140");

		leftBottomPanel.add(HR_av);

		this.setShowTests(p);

		// prawy panel: wykres ciœnienia, wykres pulsu
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		testDataPanel.add(rightPanel);

		JPanel rightTopPanel = new JPanel();
		rightTopPanel.setLayout(new BorderLayout());
		rightTopPanel.setBorder(BorderFactory.createTitledBorder("Wykres ciœnienia"));

		XYSeriesCollection datasetBP1 = getPlotDataBP(this.appModel.getPatientList().getBP1(p),
				this.appModel.getPatientList().getBP2(p));

		JFreeChart chartPressure = ChartFactory.createScatterPlot("", // Tytul
				"Numer badania", // Nazwa x
				"Cisnienie, mm Hg", // Nazwa y
				datasetBP1, // Dane
				PlotOrientation.VERTICAL, // Orientacja wykresu
				true, // Legenda
				true, // Uzywanie tooltips
				false);

		XYShapeRenderer renderer = new XYShapeRenderer();

		renderer.setSeriesPaint(0, Color.red);
		renderer.setSeriesPaint(1, Color.blue);

		XYSeriesCollection datasetHR = getPlotDataHR(this.appModel.getPatientList().getHR(p));

		JFreeChart chartHeartData = ChartFactory.createScatterPlot("", // Tytul
				"Numer badania", // Nazwa x
				"Têtno, bpm", // Nazwa y
				datasetHR, // Dane
				PlotOrientation.VERTICAL, // Orientacja wykresu
				true, // Legenda
				true, // Uzywanie tooltips
				false);

		ChartPanel cPanel1 = new ChartPanel(chartPressure);
		rightTopPanel.add(cPanel1, BorderLayout.CENTER);
		rightTopPanel.validate();

		rightPanel.add(rightTopPanel);

		JPanel rightBottomPanel = new JPanel();
		rightBottomPanel.setLayout(new BorderLayout());
		rightBottomPanel.setBorder(BorderFactory.createTitledBorder("Wykres pulsu"));

		ChartPanel cPanel2 = new ChartPanel(chartHeartData);
		rightBottomPanel.add(cPanel2, BorderLayout.CENTER);
		rightBottomPanel.validate();

		rightPanel.add(rightBottomPanel);

		showTestWindow.pack();

	}

	/**
	 * Funkcja, ktora uzupelnia pola statystykami wynikwo badan pacjenta
	 * 
	 * @param p obiekt typu PatientData, ktory przechowuje podstawowe dane wybranego
	 *          pacjenta
	 */
	public void setShowTests(PatientData p) {
		if (p != null) {
			this.showNameTextField.setText(p.getName());
			this.showSurnameTextField.setText(p.getSurname());
			this.showBirthPlaceTextField.setText(p.getBirthPlace());
			this.showPeselTextField.setText(p.getPesel().toString());

			double[] bPStats = appModel.bloodPressureStatistics(p);

			this.uBP_min.setText(String.valueOf(bPStats[0]));
			this.uBP_max.setText(String.valueOf(bPStats[1]));
			this.uBP_av.setText(String.valueOf(bPStats[2]));

			this.lBP_min.setText(String.valueOf(bPStats[3]));
			this.lBP_max.setText(String.valueOf(bPStats[4]));
			this.lBP_av.setText(String.valueOf(bPStats[5]));

			double[] hRStats = appModel.heartRateStatistics(p);
			this.HR_min.setText(String.valueOf(hRStats[0]));
			this.HR_max.setText(String.valueOf(hRStats[1]));
			this.HR_av.setText(String.valueOf(hRStats[2]));

		} else {
			this.showNameTextField.setText("");
			this.showSurnameTextField.setText("");
			this.showBirthPlaceTextField.setText("");
			this.showPeselTextField.setText("");

			this.uBP_min.setText("");
			this.uBP_max.setText("");
			this.uBP_av.setText("");

			this.lBP_min.setText("");
			this.lBP_max.setText("");
			this.lBP_av.setText("");

			this.HR_min.setText("");
			this.HR_min.setText("");
			this.HR_av.setText("");

		}

	}

	/**
	 * Ustawienia kontrolera
	 * 
	 * @param c kontroler
	 */
	public void setController(AppController c) {
		this.addingButton.addActionListener(c);
		this.updateButton.addActionListener(c);
		this.deleteButton.addActionListener(c);
		this.addTestButton.addActionListener(c);
		this.listTable.getSelectionModel().addListSelectionListener(c);

	}

	/**
	 * Pobieranie danych pacjenta z pol
	 * 
	 * @return obiekt typu PatienData stworzony z uzupelnionych pol
	 */
	public PatientData getPatientData() {

		PatientData patient = null;
		String name = this.nameTextField.getText();
		String surname = this.surnameTextField.getText();
		String birthPlace = this.birthPlaceTextField.getText();
		int ID = currentPatientNumber;
		currentPatientNumber = currentPatientNumber + 1;

		String pesel = this.peselTextField.getText();

		if (!name.isBlank() && !surname.isBlank() && !birthPlace.isBlank() && pesel.length() == 11)
			patient = new PatientData(name, surname, birthPlace, pesel);

		return (patient);

	}

	/**
	 * Funkcja, ktora zbiera ID do aktualizacji danych pacjenta
	 * 
	 * @return ID pacjenta
	 */
	public int getIDtoUpdate() {

		try {
			int IDtoUpdate = Integer.valueOf(this.updateIDTextField.getText());
			return IDtoUpdate;
		} catch (NumberFormatException e) {
			System.out.println("Blad ID do aktualizacji: " + e);
			return 0;
		}
	}

	/**
	 * Funkcja, ktora zbiera dane do uzupelnienia danych pacjenta przy aktualizacji
	 * 
	 * @return obiekt typu PatientData zaktualizowany
	 */
	public PatientData getUpdatedData() {
		try {
			PatientData updatedPatient = null;
			int ID = this.getIDtoUpdate();
			String name = this.updateNameTextField.getText();
			String surname = this.updateSurnameTextField.getText();
			String birthPlace = this.updateBirthPlaceTextField.getText();
			String pesel = this.updatePeselTextField.getText();

			if (!String.valueOf(ID).isBlank() && !name.isBlank() && !surname.isBlank() && !birthPlace.isBlank()
					&& !pesel.isBlank())
				updatedPatient = new PatientData(name, surname, birthPlace, pesel);
			updatedPatient.setTestNumber(appModel.getPatient(ID).getTestNumber());

			return (updatedPatient);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this.getMainPanel(), "Wprowadz dane ponownie");
			System.out.println("Blad aktualizacji danych: " + e);
			return null;
		}
	}

	/**
	 * Funkcja, ktora zbiera ID pacjenta do usuniecia
	 * 
	 * @return ID pacjenta
	 */
	public int getIDtoDelete() {
		try {
			int IDtoDelete = Integer.parseInt(this.deleteIDTextField.getText());

			return IDtoDelete;
		} catch (NumberFormatException e) {
			System.out.println("Blad ID usuwania danych: " + e);
			return 0;
		}
	}

	/**
	 * Funkcja, ktora zbiera ID pacjenta do dodania testow
	 * 
	 * @return ID pacjenta
	 */
	public int getIDtoAddTest() {
		try {
			int IDtoAddTest = Integer.valueOf(this.addTestIDTextField.getText());
			if (this.appModel.getPatientList().checkId(IDtoAddTest) == 1)
				return IDtoAddTest;
			else
				JOptionPane.showMessageDialog(this.getMainPanel(), "Pacjent o podanym ID nie istnieje w bazie");
			return 0;
		} catch (NumberFormatException e) {
			System.out.println("Blad przy ID testu" + e);
			return 0;
		}
	}

	/**
	 * Funkcja, ktora zbiera dane do testu wprowadzone w pola
	 * 
	 * @return obiekt TestData z aktualna dat¹
	 */
	public TestData getTestData() {
		try {
			TestData test = null;
			int patientID = this.getIDtoAddTest();
			if (patientID == 0)
				return null;

			String bP = this.bloodPressureTextField.getText();
			String[] bloodPressure = bP.split("/");
			int bP1 = Integer.valueOf(bloodPressure[0]);
			int bP2 = Integer.valueOf(bloodPressure[1]);
			int heartRate = Integer.valueOf(this.heartRateTextField.getText());

			if (!Integer.toString(patientID).isBlank() && !Integer.toString(bP1).isBlank()
					&& !Integer.toString(bP2).isBlank() && !Integer.toString(heartRate).isBlank())
				test = new TestData(bP1, bP2, heartRate, patientID);

			return test;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this.getMainPanel(), "Wprowadz dane ponownie");
			System.out.println("Blad wprowadzania testu: " + e);
			return null;
		}
	}

	/**
	 * Funkcja, ktora zbiera wybrany wiersz
	 * 
	 * @return ID pacjenta, ktory wyswietlony jest w danym wierszu
	 */
	public int getSelectedIndex() {
		return (this.listTable.getSelectedRow() + 1);
	}

	/**
	 * Aktualizacja listy pacjentow
	 */
	public void updatePatientListTable() {
		((JTableModel) this.listTable.getModel()).fireTableDataChanged();
		this.listTable.repaint();

	}

	/**
	 * Funkcja, ktora czysci pola do wprowadzania danych
	 */
	public void clearTextFields() {
		this.nameTextField.setText("");
		this.surnameTextField.setText("");
		this.peselTextField.setText("");
		this.birthPlaceTextField.setText("");

		this.updateIDTextField.setText("");
		this.updateNameTextField.setText("");
		this.updateSurnameTextField.setText("");
		this.updatePeselTextField.setText("");
		this.updateBirthPlaceTextField.setText("");

		this.deleteIDTextField.setText("");

		this.addTestIDTextField.setText("");
		this.bloodPressureTextField.setText("");
		this.heartRateTextField.setText("");
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	/**
	 * Funkcja, ktora zwraca wyniki badan jako serie do wykresow
	 * 
	 * @param table pojedyncza tabela int, zawierajaca wyniki pomiarów têtna
	 * @return pakiet danych prezentowanych na wykresie
	 */
	public XYSeriesCollection getPlotDataHR(int[] table) {
		XYSeries series = new XYSeries("Puls");
		for (int i = 0; i < table.length; i++) {
			series.add(i + 1, table[i]);
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);

		return dataset;
	}

	/**
	 * Funkcja, ktora zwraca wyniki badan jako serie do wykresow
	 * 
	 * @param table1 pojedyncza tabela int, zawierajaca wyniki pomiarów cisnienia
	 *               skurczowego
	 * @param table2 pojedyncza tabela int, zawierajaca wyniki pomiarów cisnienia
	 *               rozkurczowego
	 * @return pakiet danych prezentowanych na wykresie
	 */
	public XYSeriesCollection getPlotDataBP(int[] table1, int[] table2) {
		XYSeries series = new XYSeries("Cisnienie skurczowe");
		XYSeries series2 = new XYSeries("Cisnienie rozkurczowe");
		for (int i = 0; i < table1.length; i++) {
			series.add(i + 1, table1[i]);
		}
		for (int i = 0; i < table2.length; i++) {
			series2.add(i + 1, table2[i]);
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		dataset.addSeries(series2);

		return dataset;
	}

}
