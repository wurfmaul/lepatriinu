package at.jku.amp.lepatriinu.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicDirectoryModel;

import at.cp.jku.teaching.amprocessing.Runner;

/**
 * 
 * A GUI for the execution of the <code>Runner</code>. It just provides some
 * clickable interface to start off the analysis. Therefore we had to change the
 * <code>System.exit(1);</code> parts into
 * <code>throw IllegalArgumentExceptions</code>. </br></br>
 * <code>System.out</code> and <code>System.err</code> will be redirected to
 * text panes in the window. </br></br> <i><b>Tip:</b> Best to use the
 * WindowBuilder plugin from Google, as the GridBagLayout produces really nasty
 * code but has far the best effects on the layout.</i>
 * 
 * @author Wolfgang Küllinger (0955711)
 * @author Fabian Jordan (0855941)
 * 
 */
public class SwingUI extends JFrame {
	private static final long serialVersionUID = -3015831195484437974L;
	private static final String SEP = System.getProperty("file.separator");
	public static final String INPUT_DIR = "data";
	public static final String OUTPUT_DIR = "output";
	private static final String STARTING_DIRECTORY = INPUT_DIR + SEP;
	private static final String OUTPUT_DIRECTORY = "." + SEP + OUTPUT_DIR + SEP;

	private JButton btRunSel;

	private JCheckBox cbOnset;
	private JCheckBox cbTempo;
	private JCheckBox cbBeat;
	private JButton btnRunAll;

	private JList<Object> lFiles;
	private BasicDirectoryModel bdmInputFiles;

	private JList<Object> lOnOutput;
	private BasicDirectoryModel bdmOnOutputFiles;
	private JTextArea tpOnOutput;
	private JLabel lOnPrecision;
	private JLabel lOnRecall;
	private JLabel lOnFMeasure;

	private JList<Object> lTeOutput;
	private BasicDirectoryModel bdmTeOutputFiles;
	private JTextArea tpTeOutput;
	private JLabel lTeCorrect;
	private JLabel lTeMultiple;
	private JLabel lTeNone;

	private JList<Object> lBeOutput;
	private BasicDirectoryModel bdmBeOutputFiles;
	private JTextArea tpBeOutput;
	private JLabel lBeRecall;
	private JLabel lBeFMeasure;
	private JLabel lBePrecision;

	/**
	 * Starts off the <code>Runner</code> by calling its <code>main</code>
	 * method, meeting the calling conventions of the framework mentioned on
	 * Slide 3 of "Exercise_Track.pdf"
	 */
	private void run(String path, String wavefilename) {
		Runner.main(new String[] { "-i", path + wavefilename + ".wav", "-d",
				OUTPUT_DIRECTORY, cbOnset.isSelected() ? "-o" : "",
				cbOnset.isSelected() ? path + wavefilename + ".onsets" : "",
				cbTempo.isSelected() ? "-t" : "",
				cbTempo.isSelected() ? path + wavefilename + ".bpms" : "",
				cbBeat.isSelected() ? "-b" : "",
				cbBeat.isSelected() ? path + wavefilename + ".beats" : "" });
	}

	private ActionListener runAllButtonClick = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			for (File f : bdmInputFiles.getFiles()) {
				run(STARTING_DIRECTORY, FileUtils.killExtension(f.getName()));
			}
			loadOnsetsOutputBox();
			loadTempoOutputBox();
			loadBeatOutputBox();
		}
	};

	private ActionListener runButtonClick = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			for (int i : lFiles.getSelectedIndices()) {
				run(STARTING_DIRECTORY,
						FileUtils.killExtension(bdmInputFiles.getFiles().get(i)
								.getName()));
			}
			loadOnsetsOutputBox();
			loadTempoOutputBox();
			loadBeatOutputBox();
		}
	};

	/**
	 * Create the frame and all its components.
	 */
	private SwingUI() {
		System.out.println("starting off");

		File p = new File(OUTPUT_DIRECTORY);
		System.out.println("Output directory created: " + p.mkdirs());

		setTitle("lepatriinu - Audio and Music Processor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 757, 527);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Files", null, panel_3, null);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 0, 0 };
		gbl_panel_3.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_3.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Input WAV-Files",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 0;
		panel_3.add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		JScrollPane scrollPane_5 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_5 = new GridBagConstraints();
		gbc_scrollPane_5.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_5.gridx = 0;
		gbc_scrollPane_5.gridy = 0;
		panel_4.add(scrollPane_5, gbc_scrollPane_5);

		lFiles = new JList<>();
		lFiles.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				btRunSel.setEnabled(lFiles.getSelectedIndex() != -1);
			}
		});
		scrollPane_5.setViewportView(lFiles);
		JFileChooser fj = new JFileChooser(STARTING_DIRECTORY);
		fj.setFileFilter(FileUtils.getFileFilter("wav"));
		bdmInputFiles = new BasicDirectoryModel(fj);
		lFiles.setModel(bdmInputFiles);
		lFiles.setSelectedIndex(0);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Output Settings",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel_3.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 20, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		cbOnset = new JCheckBox("Generate Onset File");
		cbOnset.setSelected(true);
		GridBagConstraints gbc_cbOnset = new GridBagConstraints();
		gbc_cbOnset.insets = new Insets(0, 0, 0, 5);
		gbc_cbOnset.gridx = 0;
		gbc_cbOnset.gridy = 0;
		panel_2.add(cbOnset, gbc_cbOnset);

		cbTempo = new JCheckBox("Generate Tempo File");
		cbTempo.setSelected(true);
		GridBagConstraints gbc_cbTempo = new GridBagConstraints();
		gbc_cbTempo.insets = new Insets(0, 0, 0, 5);
		gbc_cbTempo.gridx = 1;
		gbc_cbTempo.gridy = 0;
		panel_2.add(cbTempo, gbc_cbTempo);

		cbBeat = new JCheckBox("Generate  Beats File");
		cbBeat.setSelected(true);
		GridBagConstraints gbc_cbBeat = new GridBagConstraints();
		gbc_cbBeat.gridx = 2;
		gbc_cbBeat.gridy = 0;
		panel_2.add(cbBeat, gbc_cbBeat);

		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("Onsets Output", null, panel_5, null);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[] { 0, 0 };
		gbl_panel_5.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_5.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_5.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		panel_5.setLayout(gbl_panel_5);

		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Onsets",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_9 = new GridBagConstraints();
		gbc_panel_9.insets = new Insets(0, 0, 5, 0);
		gbc_panel_9.fill = GridBagConstraints.BOTH;
		gbc_panel_9.gridx = 0;
		gbc_panel_9.gridy = 0;
		panel_5.add(panel_9, gbc_panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_9.add(scrollPane_1);

		lOnOutput = new JList<>();
		lOnOutput.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					int selI = lOnOutput.getSelectedIndex();
					if (selI != -1) {
						tpOnOutput.setText(FileUtils.readFile(bdmOnOutputFiles
								.getFiles().get(selI).getAbsolutePath()));
					}
				}
			}
		});
		lOnOutput.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		loadOnsetsOutputBox();
		scrollPane_1.setViewportView(lOnOutput);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		panel_5.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(null, "Selected File",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_8 = new GridBagConstraints();
		gbc_panel_8.insets = new Insets(0, 0, 0, 5);
		gbc_panel_8.fill = GridBagConstraints.BOTH;
		gbc_panel_8.gridx = 0;
		gbc_panel_8.gridy = 0;
		panel_1.add(panel_8, gbc_panel_8);
		GridBagLayout gbl_panel_8 = new GridBagLayout();
		gbl_panel_8.columnWidths = new int[] { 6, 0 };
		gbl_panel_8.rowHeights = new int[] { 24, 0 };
		gbl_panel_8.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_8.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_8.setLayout(gbl_panel_8);

		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 0;
		gbc_scrollPane_2.gridy = 0;
		panel_8.add(scrollPane_2, gbc_scrollPane_2);

		tpOnOutput = new JTextArea();
		scrollPane_2.setViewportView(tpOnOutput);

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Summary (Averages)",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.gridx = 1;
		gbc_panel_7.gridy = 0;
		panel_1.add(panel_7, gbc_panel_7);
		GridBagLayout gbl_panel_7 = new GridBagLayout();
		gbl_panel_7.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_7.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_7.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel_7.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_7.setLayout(gbl_panel_7);

		JLabel lbl1 = new JLabel("Precision");
		GridBagConstraints gbc_lbl1 = new GridBagConstraints();
		gbc_lbl1.anchor = GridBagConstraints.BASELINE_TRAILING;
		gbc_lbl1.insets = new Insets(0, 0, 5, 5);
		gbc_lbl1.gridx = 0;
		gbc_lbl1.gridy = 0;
		panel_7.add(lbl1, gbc_lbl1);

		lOnPrecision = new JLabel("0.0%");
		lOnPrecision.setOpaque(true);
		GridBagConstraints gbc_lOnPrecision = new GridBagConstraints();
		gbc_lOnPrecision.fill = GridBagConstraints.HORIZONTAL;
		gbc_lOnPrecision.insets = new Insets(0, 0, 5, 0);
		gbc_lOnPrecision.anchor = GridBagConstraints.BASELINE;
		gbc_lOnPrecision.gridx = 1;
		gbc_lOnPrecision.gridy = 0;
		panel_7.add(lOnPrecision, gbc_lOnPrecision);

		JLabel lbl2 = new JLabel("Recall");
		GridBagConstraints gbc_lbl2 = new GridBagConstraints();
		gbc_lbl2.anchor = GridBagConstraints.BASELINE_TRAILING;
		gbc_lbl2.insets = new Insets(0, 0, 5, 5);
		gbc_lbl2.gridx = 0;
		gbc_lbl2.gridy = 1;
		panel_7.add(lbl2, gbc_lbl2);

		lOnRecall = new JLabel("0.0%");
		lOnRecall.setOpaque(true);
		GridBagConstraints gbc_lOnRecall = new GridBagConstraints();
		gbc_lOnRecall.fill = GridBagConstraints.HORIZONTAL;
		gbc_lOnRecall.insets = new Insets(0, 0, 5, 0);
		gbc_lOnRecall.anchor = GridBagConstraints.BASELINE;
		gbc_lOnRecall.gridx = 1;
		gbc_lOnRecall.gridy = 1;
		panel_7.add(lOnRecall, gbc_lOnRecall);

		JLabel lbl3 = new JLabel("F-Measure");
		GridBagConstraints gbc_lbl3 = new GridBagConstraints();
		gbc_lbl3.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gbc_lbl3.insets = new Insets(0, 0, 0, 5);
		gbc_lbl3.gridx = 0;
		gbc_lbl3.gridy = 2;
		panel_7.add(lbl3, gbc_lbl3);

		lOnFMeasure = new JLabel("0.0%");
		lOnFMeasure.setOpaque(true);
		GridBagConstraints gbc_lOnFMeasure = new GridBagConstraints();
		gbc_lOnFMeasure.fill = GridBagConstraints.BOTH;
		gbc_lOnFMeasure.gridx = 1;
		gbc_lOnFMeasure.gridy = 2;
		panel_7.add(lOnFMeasure, gbc_lOnFMeasure);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Tempo Output", null, panel, null);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tempos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.insets = new Insets(0, 0, 5, 0);
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 0;
		panel.add(panel_6, gbc_panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_4 = new JScrollPane();
		panel_6.add(scrollPane_4, BorderLayout.CENTER);

		lTeOutput = new JList<>();
		lTeOutput.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					int selI = lTeOutput.getSelectedIndex();
					if (selI != -1) {
						tpTeOutput.setText(FileUtils.readFile(bdmTeOutputFiles
								.getFiles().get(selI).getAbsolutePath()));
					}
				}
			}
		});
		loadTempoOutputBox();
		scrollPane_4.setViewportView(lTeOutput);

		JPanel panel_16 = new JPanel();
		GridBagConstraints gbc_panel_16 = new GridBagConstraints();
		gbc_panel_16.fill = GridBagConstraints.BOTH;
		gbc_panel_16.gridx = 0;
		gbc_panel_16.gridy = 1;
		panel.add(panel_16, gbc_panel_16);
		GridBagLayout gbl_panel_16 = new GridBagLayout();
		gbl_panel_16.columnWidths = new int[] { 0, 0 };
		gbl_panel_16.rowHeights = new int[] { 0, 0 };
		gbl_panel_16.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_16.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_16.setLayout(gbl_panel_16);

		JPanel panel_15 = new JPanel();
		panel_15.setBorder(new TitledBorder(null, "Selected File", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_15 = new GridBagConstraints();
		gbc_panel_15.fill = GridBagConstraints.BOTH;
		gbc_panel_15.gridx = 0;
		gbc_panel_15.gridy = 0;
		panel_16.add(panel_15, gbc_panel_15);
		GridBagLayout gbl_panel_15 = new GridBagLayout();
		gbl_panel_15.columnWidths = new int[] { 6, 0, 0 };
		gbl_panel_15.rowHeights = new int[] { 24, 0 };
		gbl_panel_15.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel_15.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_15.setLayout(gbl_panel_15);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel_15.add(scrollPane, gbc_scrollPane);

		tpTeOutput = new JTextArea();
		scrollPane.setViewportView(tpTeOutput);

		JPanel panel_17 = new JPanel();
		panel_17.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Summary",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_17 = new GridBagConstraints();
		gbc_panel_17.fill = GridBagConstraints.BOTH;
		gbc_panel_17.gridx = 1;
		gbc_panel_17.gridy = 0;
		panel_15.add(panel_17, gbc_panel_17);
		GridBagLayout gbl_panel_17 = new GridBagLayout();
		gbl_panel_17.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_17.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_17.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel_17.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panel_17.setLayout(gbl_panel_17);

		JLabel lblCorrectTempo = new JLabel("Correct Tempo");
		GridBagConstraints gbc_lblCorrectTempo = new GridBagConstraints();
		gbc_lblCorrectTempo.anchor = GridBagConstraints.BASELINE_TRAILING;
		gbc_lblCorrectTempo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCorrectTempo.gridx = 0;
		gbc_lblCorrectTempo.gridy = 0;
		panel_17.add(lblCorrectTempo, gbc_lblCorrectTempo);

		lTeCorrect = new JLabel("0.0% (0/0)");
		lTeCorrect.setOpaque(true);
		GridBagConstraints gbc_lblTeCorrect = new GridBagConstraints();
		gbc_lblTeCorrect.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTeCorrect.anchor = GridBagConstraints.BASELINE;
		gbc_lblTeCorrect.insets = new Insets(0, 0, 5, 0);
		gbc_lblTeCorrect.gridx = 1;
		gbc_lblTeCorrect.gridy = 0;
		panel_17.add(lTeCorrect, gbc_lblTeCorrect);

		JLabel lblMultipleTempo = new JLabel("Multiple Tempo");
		GridBagConstraints gbc_lblMultipleTempo = new GridBagConstraints();
		gbc_lblMultipleTempo.anchor = GridBagConstraints.BASELINE_TRAILING;
		gbc_lblMultipleTempo.insets = new Insets(0, 0, 5, 5);
		gbc_lblMultipleTempo.gridx = 0;
		gbc_lblMultipleTempo.gridy = 1;
		panel_17.add(lblMultipleTempo, gbc_lblMultipleTempo);

		lTeMultiple = new JLabel("0.0% (0/0)");
		lTeMultiple.setOpaque(true);
		GridBagConstraints gbc_lblTeMultiple = new GridBagConstraints();
		gbc_lblTeMultiple.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTeMultiple.anchor = GridBagConstraints.BASELINE;
		gbc_lblTeMultiple.insets = new Insets(0, 0, 5, 0);
		gbc_lblTeMultiple.gridx = 1;
		gbc_lblTeMultiple.gridy = 1;
		panel_17.add(lTeMultiple, gbc_lblTeMultiple);

		JLabel lblNoTempo = new JLabel("No Tempo");
		GridBagConstraints gbc_lblNoTempo = new GridBagConstraints();
		gbc_lblNoTempo.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gbc_lblNoTempo.insets = new Insets(0, 0, 0, 5);
		gbc_lblNoTempo.gridx = 0;
		gbc_lblNoTempo.gridy = 2;
		panel_17.add(lblNoTempo, gbc_lblNoTempo);

		lTeNone = new JLabel("0.0% (0/0)");
		lTeNone.setOpaque(true);
		GridBagConstraints gbc_lblTeNone = new GridBagConstraints();
		gbc_lblTeNone.fill = GridBagConstraints.BOTH;
		gbc_lblTeNone.gridx = 1;
		gbc_lblTeNone.gridy = 2;
		panel_17.add(lTeNone, gbc_lblTeNone);

		JPanel panel_10 = new JPanel();
		tabbedPane.addTab("Beat Output", null, panel_10, null);
		GridBagLayout gbl_panel_10 = new GridBagLayout();
		gbl_panel_10.columnWidths = new int[] { 0, 0 };
		gbl_panel_10.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_10.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_10.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		panel_10.setLayout(gbl_panel_10);

		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Beats",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_11 = new GridBagConstraints();
		gbc_panel_11.fill = GridBagConstraints.BOTH;
		gbc_panel_11.insets = new Insets(0, 0, 5, 0);
		gbc_panel_11.gridx = 0;
		gbc_panel_11.gridy = 0;
		panel_10.add(panel_11, gbc_panel_11);
		panel_11.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_6 = new JScrollPane();
		panel_11.add(scrollPane_6, BorderLayout.CENTER);

		lBeOutput = new JList<>();
		lBeOutput.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					int selI = lBeOutput.getSelectedIndex();
					if (selI != -1) {
						tpBeOutput.setText(FileUtils.readFile(bdmBeOutputFiles
								.getFiles().get(selI).getAbsolutePath()));
					}
				}
			}
		});
		lBeOutput.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		loadBeatOutputBox();
		scrollPane_6.setViewportView(lBeOutput);

		JPanel panel_12 = new JPanel();
		GridBagConstraints gbc_panel_12 = new GridBagConstraints();
		gbc_panel_12.fill = GridBagConstraints.BOTH;
		gbc_panel_12.gridx = 0;
		gbc_panel_12.gridy = 1;
		panel_10.add(panel_12, gbc_panel_12);
		GridBagLayout gbl_panel_12 = new GridBagLayout();
		gbl_panel_12.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_12.rowHeights = new int[] { 0, 0 };
		gbl_panel_12.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel_12.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_12.setLayout(gbl_panel_12);

		JPanel panel_13 = new JPanel();
		panel_13.setBorder(new TitledBorder(null, "Selected File",

		TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_13 = new GridBagConstraints();
		gbc_panel_13.fill = GridBagConstraints.BOTH;
		gbc_panel_13.insets = new Insets(0, 0, 0, 5);
		gbc_panel_13.gridx = 0;
		gbc_panel_13.gridy = 0;
		panel_12.add(panel_13, gbc_panel_13);
		GridBagLayout gbl_panel_13 = new GridBagLayout();
		gbl_panel_13.columnWidths = new int[] { 6, 0 };
		gbl_panel_13.rowHeights = new int[] { 24, 0 };
		gbl_panel_13.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_13.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_13.setLayout(gbl_panel_13);

		JScrollPane scrollPane_3 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
		gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_3.gridx = 0;
		gbc_scrollPane_3.gridy = 0;
		panel_13.add(scrollPane_3, gbc_scrollPane_3);

		tpBeOutput = new JTextArea();
		scrollPane_3.setViewportView(tpBeOutput);

		JPanel panel_14 = new JPanel();
		panel_14.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Summary (Averages)",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_14 = new GridBagConstraints();
		gbc_panel_14.fill = GridBagConstraints.BOTH;
		gbc_panel_14.gridx = 1;
		gbc_panel_14.gridy = 0;
		panel_12.add(panel_14, gbc_panel_14);
		GridBagLayout gbl_panel_14 = new GridBagLayout();
		gbl_panel_14.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_14.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_14.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel_14.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panel_14.setLayout(gbl_panel_14);

		JLabel label = new JLabel("Precision");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.BASELINE_TRAILING;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel_14.add(label, gbc_label);

		lBePrecision = new JLabel("0.0%");
		lBePrecision.setOpaque(true);
		GridBagConstraints gbc_lTePrecision = new GridBagConstraints();
		gbc_lTePrecision.fill = GridBagConstraints.HORIZONTAL;
		gbc_lTePrecision.anchor = GridBagConstraints.BASELINE;
		gbc_lTePrecision.insets = new Insets(0, 0, 5, 0);
		gbc_lTePrecision.gridx = 1;
		gbc_lTePrecision.gridy = 0;
		panel_14.add(lBePrecision, gbc_lTePrecision);

		JLabel label_2 = new JLabel("Recall");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.BASELINE_TRAILING;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 1;
		panel_14.add(label_2, gbc_label_2);

		lBeRecall = new JLabel("0.0%");
		lBeRecall.setOpaque(true);
		GridBagConstraints gbc_lTeRecall = new GridBagConstraints();
		gbc_lTeRecall.fill = GridBagConstraints.HORIZONTAL;
		gbc_lTeRecall.anchor = GridBagConstraints.BASELINE;
		gbc_lTeRecall.insets = new Insets(0, 0, 5, 0);
		gbc_lTeRecall.gridx = 1;
		gbc_lTeRecall.gridy = 1;
		panel_14.add(lBeRecall, gbc_lTeRecall);

		JLabel label_4 = new JLabel("F-Measure");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gbc_label_4.insets = new Insets(0, 0, 0, 5);
		gbc_label_4.gridx = 0;
		gbc_label_4.gridy = 2;
		panel_14.add(label_4, gbc_label_4);

		lBeFMeasure = new JLabel("0.0%");
		lBeFMeasure.setOpaque(true);
		GridBagConstraints gbc_lTeFMeasure = new GridBagConstraints();
		gbc_lTeFMeasure.fill = GridBagConstraints.BOTH;
		gbc_lTeFMeasure.gridx = 1;
		gbc_lTeFMeasure.gridy = 2;
		panel_14.add(lBeFMeasure, gbc_lTeFMeasure);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);

		btRunSel = new JButton("Run selected");
		btRunSel.setIcon(new ImageIcon(SwingUI.class
				.getResource("/media/play.png")));
		btRunSel.setEnabled(false);
		btRunSel.addActionListener(runButtonClick);

		btnRunAll = new JButton("Run all");
		btnRunAll.setIcon(new ImageIcon(SwingUI.class
				.getResource("/media/play_all.png")));
		btnRunAll.addActionListener(runAllButtonClick);
		toolBar.add(btnRunAll);
		toolBar.add(btRunSel);

		System.out.println("Ready!");
	}

	private void loadOnsetsOutputBox() {
		JFileChooser fj;
		fj = new JFileChooser(OUTPUT_DIRECTORY);
		fj.setFileFilter(FileUtils.getFileFilter("onsets.eval"));
		bdmOnOutputFiles = new BasicDirectoryModel(fj);
		lOnOutput.setModel(bdmOnOutputFiles);

		bdmOnOutputFiles.addListDataListener(onLDL);
	}

	private void loadTempoOutputBox() {
		JFileChooser fj;
		fj = new JFileChooser(OUTPUT_DIRECTORY);
		fj.setFileFilter(FileUtils.getFileFilter(new String[] { "bpms",
				"bpms.eval" }));
		bdmTeOutputFiles = new BasicDirectoryModel(fj);
		lTeOutput.setModel(bdmTeOutputFiles);

		bdmTeOutputFiles.addListDataListener(teLDL);
	}

	private void loadBeatOutputBox() {
		JFileChooser fj;
		fj = new JFileChooser(OUTPUT_DIRECTORY);
		fj.setFileFilter(FileUtils.getFileFilter("beats.eval"));
		bdmBeOutputFiles = new BasicDirectoryModel(fj);
		lBeOutput.setModel(bdmBeOutputFiles);

		bdmBeOutputFiles.addListDataListener(beLDL);
	}

	private ListDataListener onLDL = new ListDataListener() {
		@Override
		public void intervalRemoved(ListDataEvent e) {
		}

		@Override
		public void intervalAdded(ListDataEvent e) {
			double p = 0, r = 0, fM = 0, temp;
			final int size = bdmOnOutputFiles.getSize();
			for (File f : bdmOnOutputFiles.getFiles()) {
				String bigString = FileUtils.readFile(f.getAbsolutePath());
				String searchString = "Precision: ";
				temp = Double
						.parseDouble(bigString.substring(
								bigString.indexOf(searchString)
										+ searchString.length(),
								bigString.indexOf('\n',
										bigString.indexOf(searchString))));
				p += (!Double.isNaN(temp)) ? temp : 0;
				searchString = "Recall: ";
				temp = Double
						.parseDouble(bigString.substring(
								bigString.indexOf(searchString)
										+ searchString.length(),
								bigString.indexOf('\n',
										bigString.indexOf(searchString))));
				r += (!Double.isNaN(temp)) ? temp : 0;
				searchString = "F-Measure: ";
				temp = Double
						.parseDouble(bigString.substring(
								bigString.indexOf(searchString)
										+ searchString.length(),
								bigString.indexOf('\n',
										bigString.indexOf(searchString))));
				fM += (!Double.isNaN(temp)) ? temp : 0;
			}
			p /= size;
			lOnPrecision.setText(String.format("%f%%", p * 100));
			lOnPrecision.setBackground(getColorValue(p));
			r /= size;
			lOnRecall.setText(String.format("%f%%", r * 100));
			lOnRecall.setBackground(getColorValue(r));
			fM /= size;
			lOnFMeasure.setText(String.format("%f%%", fM * 100));
			lOnFMeasure.setBackground(getColorValue(fM));
		}

		@Override
		public void contentsChanged(ListDataEvent e) {
		}
	};

	private ListDataListener teLDL = new ListDataListener() {
		@Override
		public void intervalRemoved(ListDataEvent e) {
		}

		@Override
		public void intervalAdded(ListDataEvent e) {
			int correct = 0, multiple = 0, none = 0, evalCount = 0;
			for (File f : bdmTeOutputFiles.getFiles()) {
				String path = f.getPath();
				final String text = FileUtils.readFile(path);
				if (path.endsWith("eval")) {
					if (text.length() >= 2) {
						int c = Integer.parseInt(text.substring(0, 1));
						int m = Integer.parseInt(text.substring(2, 3));
						correct += (c == 1) ? 1 : 0;
						multiple += (m == 1 && c == 0) ? 1 : 0;
						none += (m == 0 && c == 0) ? 1 : 0;
						evalCount++;
					}
				} else {

				}
			}
			double percentage = (double) correct / evalCount;
			lTeCorrect.setText(String.format("%f%% (%d/%d)",
					(percentage * 100), correct, evalCount));
			lTeCorrect.setBackground(getColorValue(percentage));
			percentage = (double) multiple / evalCount;
			lTeMultiple.setText(String.format("%f%% (%d/%d)",
					(percentage * 100), multiple, evalCount));
			lTeMultiple.setBackground(getColorValue(percentage));
			percentage = (double) none / evalCount;
			lTeNone.setText(String.format("%f%% (%d/%d)", (percentage * 100),
					none, evalCount));
			lTeNone.setBackground(getColorValue(1 - percentage));
		}

		@Override
		public void contentsChanged(ListDataEvent e) {
		}
	};

	private ListDataListener beLDL = new ListDataListener() {
		@Override
		public void intervalRemoved(ListDataEvent e) {
		}

		@Override
		public void intervalAdded(ListDataEvent e) {
			double p = 0, r = 0, fM = 0, temp;
			final int size = bdmBeOutputFiles.getSize();
			for (File f : bdmBeOutputFiles.getFiles()) {
				String bigString = FileUtils.readFile(f.getAbsolutePath());
				String searchString = "Precision: ";
				temp = Double
						.parseDouble(bigString.substring(
								bigString.indexOf(searchString)
										+ searchString.length(),
								bigString.indexOf('\n',
										bigString.indexOf(searchString))));
				p += (!Double.isNaN(temp)) ? temp : 0;
				searchString = "Recall: ";
				temp = Double
						.parseDouble(bigString.substring(
								bigString.indexOf(searchString)
										+ searchString.length(),
								bigString.indexOf('\n',
										bigString.indexOf(searchString))));
				r += (!Double.isNaN(temp)) ? temp : 0;
				searchString = "F-Measure: ";
				temp = Double
						.parseDouble(bigString.substring(
								bigString.indexOf(searchString)
										+ searchString.length(),
								bigString.indexOf('\n',
										bigString.indexOf(searchString))));
				fM += (!Double.isNaN(temp)) ? temp : 0;
			}
			p /= size;
			lBePrecision.setText(String.format("%f%%", p * 100));
			lBePrecision.setBackground(getColorValue(p));
			r /= size;
			lBeRecall.setText(String.format("%f%%", r * 100));
			lBeRecall.setBackground(getColorValue(r));
			fM /= size;
			lBeFMeasure.setText(String.format("%f%%", fM * 100));
			lBeFMeasure.setBackground(getColorValue(fM));
		}

		@Override
		public void contentsChanged(ListDataEvent e) {
		}
	};

	private Color getColorValue(double p) {
		return new Color((float) (1 - p), (float) p, 0f);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						UIManager.setLookAndFeel(UIManager
								.getSystemLookAndFeelClassName());
					} catch (Exception e) {
					}

					SwingUI frame = new SwingUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}