package at.jku.amp.lepatriinu.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;

import at.cp.jku.teaching.amprocessing.Runner;
import at.jku.amp.lepatriinu.BeatDetector;
import at.jku.amp.lepatriinu.OnsetDetector;
import at.jku.amp.lepatriinu.TempoExtractor;
import at.jku.amp.lepatriinu.ui.utils.FileChooser;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * A GUI for the execution of the <code>Runner</code>. It just provides some
 * clickable interface to start off the analysis. Therefore we had to change the
 * <code>System.exit(1);</code> parts into
 * <code>throw IllegalArgumentExceptions</code>. </br></br>
 * <code>System.out</code> and <code>System.err</code> will be redirected to
 * text panes in the window.
 * 
 * @author Wolfgang Küllinger (0955711)
 * @author Fabian Jordan (0855941)
 * 
 */
public class SwingUI extends JFrame {
	private static final long serialVersionUID = -3015831195484437974L;

	private static final String WAV_FILE = ".\\media\\train1.wav";

	private JTextField tfInput;
	private JButton btInputBrowse;
	private JTextField tfOutput;
	private JButton btOutputBrowse;
	private JCheckBox cbOnset;
	private JCheckBox cbTempo;
	private JCheckBox cbBeat;
	private JButton btRun;
	private JList<OnsetDetector> lOnset;
	private JList<TempoExtractor> lTempo;
	private JList<BeatDetector> lBeat;

	private JTabbedPane logTabPane;
	private JTextArea txtpnLog;
	private JButton btClearLog;
	private JTextArea txtpnDebug;
	private JButton btClearDebug;

	/**
	 * Starts off the <code>Runner</code> by calling its <code>main</code>
	 * method, meeting the calling conventions of the framework mentioned on
	 * Slide 3 of "Exercise_Track.pdf"
	 */
	private void run() {
		String wavefilename = getWAVEFILENAME();
		Runner.main(new String[] { "-i", tfInput.getText(), "-d",
				tfOutput.getText(), cbOnset.isSelected() ? "-o" : "",
				cbOnset.isSelected() ? wavefilename + ".onsets" : "",
				cbTempo.isSelected() ? "-t" : "",
				cbTempo.isSelected() ? wavefilename + ".bpms" : "",
				cbBeat.isSelected() ? "-b" : "",
				cbBeat.isSelected() ? wavefilename + ".beats" : "" });
	}

	private ActionListener runButtonClick = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			run();
		}
	};

	private ActionListener inputBrowseClick = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			File f;
			if ((f = FileChooser.chooseFile(".", "wav", "WAV-Files", null)) != null) {
				tfInput.setText(f.getAbsolutePath());
			}
		}
	};

	private ActionListener outputBrowseClick = new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			File f;
			if ((f = FileChooser.chooseFile(".", null, "Folders", SwingUI.this)) != null) {
				tfOutput.setText(f.getAbsolutePath());
			}
		}
	};

	private ActionListener useOnsetOutputClick = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		}
	};

	private ActionListener useTempoOutputClick = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		}
	};

	private ActionListener useBeatOutputClick = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		}
	};

	/**
	 * Create the frame and all its components.
	 */
	private SwingUI() {
		setTitle("Lepatriinu - Audio and Music Processor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 531, 527);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
	
		System.setOut(getLogWorkaround());
		System.setErr(getDebugWorkaround());
	
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		splitPane.setContinuousLayout(true);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);
	
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setLeftComponent(tabbedPane);
	
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("Files", null, panel_3, null);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 0, 0 };
		gbl_panel_3.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_3.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);
	
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Input WAV-File",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.anchor = GridBagConstraints.NORTH;
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 0;
		panel_3.add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);
	
		tfInput = new JTextField(WAV_FILE);
		GridBagConstraints gbc_tfInput = new GridBagConstraints();
		gbc_tfInput.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfInput.insets = new Insets(0, 0, 0, 5);
		gbc_tfInput.gridx = 0;
		gbc_tfInput.gridy = 0;
		panel_4.add(tfInput, gbc_tfInput);
		tfInput.setColumns(10);
	
		btInputBrowse = new JButton("Browse ...");
		btInputBrowse.addActionListener(inputBrowseClick);
		GridBagConstraints gbc_btInputBrowse = new GridBagConstraints();
		gbc_btInputBrowse.gridx = 1;
		gbc_btInputBrowse.gridy = 0;
		panel_4.add(btInputBrowse, gbc_btInputBrowse);
	
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Output Directory",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		panel_3.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 86, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 20, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);
	
		tfOutput = new JTextField(".");
		tfOutput.setColumns(10);
		GridBagConstraints gbc_tfOutput = new GridBagConstraints();
		gbc_tfOutput.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfOutput.insets = new Insets(0, 0, 0, 5);
		gbc_tfOutput.anchor = GridBagConstraints.NORTH;
		gbc_tfOutput.gridx = 0;
		gbc_tfOutput.gridy = 0;
		panel_1.add(tfOutput, gbc_tfOutput);
	
		btOutputBrowse = new JButton("Browse ...");
		btOutputBrowse.addActionListener(outputBrowseClick);
		GridBagConstraints gbc_btOutputBrowse = new GridBagConstraints();
		gbc_btOutputBrowse.gridx = 1;
		gbc_btOutputBrowse.gridy = 0;
		panel_1.add(btOutputBrowse, gbc_btOutputBrowse);
	
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Output Settings",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 2;
		panel_3.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 20, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);
	
		cbOnset = new JCheckBox("Generate Onset File");
		cbOnset.addActionListener(useOnsetOutputClick);
		GridBagConstraints gbc_cbOnset = new GridBagConstraints();
		gbc_cbOnset.insets = new Insets(0, 0, 0, 5);
		gbc_cbOnset.gridx = 0;
		gbc_cbOnset.gridy = 0;
		panel_2.add(cbOnset, gbc_cbOnset);
	
		cbTempo = new JCheckBox("Generate Tempo File");
		GridBagConstraints gbc_cbTempo = new GridBagConstraints();
		gbc_cbTempo.insets = new Insets(0, 0, 0, 5);
		gbc_cbTempo.gridx = 1;
		gbc_cbTempo.gridy = 0;
		panel_2.add(cbTempo, gbc_cbTempo);
	
		cbBeat = new JCheckBox("Generate  Beats File");
		GridBagConstraints gbc_cbBeat = new GridBagConstraints();
		gbc_cbBeat.gridx = 2;
		gbc_cbBeat.gridy = 0;
		panel_2.add(cbBeat, gbc_cbBeat);
		cbBeat.addActionListener(useBeatOutputClick);
		cbTempo.addActionListener(useTempoOutputClick);
	
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("Settings", null, panel_5, null);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[] { 0, 0 };
		gbl_panel_5.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_5.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_5.rowWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		panel_5.setLayout(gbl_panel_5);
	
		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Onset",
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
	
		lOnset = new JList<OnsetDetector>();
		lOnset.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(lOnset);
	
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new TitledBorder(null, "Tempo",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_10 = new GridBagConstraints();
		gbc_panel_10.insets = new Insets(0, 0, 5, 0);
		gbc_panel_10.fill = GridBagConstraints.BOTH;
		gbc_panel_10.gridx = 0;
		gbc_panel_10.gridy = 1;
		panel_5.add(panel_10, gbc_panel_10);
		panel_10.setLayout(new BorderLayout(0, 0));
	
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_10.add(scrollPane_2, BorderLayout.CENTER);
	
		lTempo = new JList<TempoExtractor>();
		lTempo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_2.setViewportView(lTempo);
	
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new TitledBorder(null, "Beat", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_11 = new GridBagConstraints();
		gbc_panel_11.fill = GridBagConstraints.BOTH;
		gbc_panel_11.gridx = 0;
		gbc_panel_11.gridy = 2;
		panel_5.add(panel_11, gbc_panel_11);
		panel_11.setLayout(new BorderLayout(0, 0));
	
		JScrollPane scrollPane_3 = new JScrollPane();
		panel_11.add(scrollPane_3, BorderLayout.CENTER);
	
		lBeat = new JList<BeatDetector>();
		lBeat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_3.setViewportView(lBeat);
	
		logTabPane = new JTabbedPane(JTabbedPane.TOP);
		logTabPane.addChangeListener(logTabPaneChange);
		splitPane.setRightComponent(logTabPane);
	
		JPanel panel = new JPanel();
		logTabPane.addTab(tabNames[0], null, panel, null);
		panel.setBorder(null);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
	
		btClearLog = new JButton("Clear");
		btClearLog.addActionListener(clearLogButtonClick);
	
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel.add(scrollPane, gbc_scrollPane);
	
		txtpnLog = new JTextArea();
		scrollPane.setViewportView(txtpnLog);
		txtpnLog.setTabSize(4);
		txtpnLog.setEditable(false);
		txtpnLog.setFont(new Font("Courier New", Font.PLAIN, 11));
		GridBagConstraints gbc_btClearLog = new GridBagConstraints();
		gbc_btClearLog.anchor = GridBagConstraints.NORTH;
		gbc_btClearLog.gridx = 1;
		gbc_btClearLog.gridy = 0;
		panel.add(btClearLog, gbc_btClearLog);
	
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(null);
		logTabPane.addTab(tabNames[1], null, panel_6, null);
		GridBagLayout gbl_panel_6 = new GridBagLayout();
		gbl_panel_6.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_6.rowHeights = new int[] { 0, 0 };
		gbl_panel_6.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_6.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_6.setLayout(gbl_panel_6);
	
		JScrollPane scrollPane_4 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_4 = new GridBagConstraints();
		gbc_scrollPane_4.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_4.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_4.gridx = 0;
		gbc_scrollPane_4.gridy = 0;
		panel_6.add(scrollPane_4, gbc_scrollPane_4);
	
		txtpnDebug = new JTextArea();
		txtpnDebug.setForeground(Color.RED);
		scrollPane_4.setViewportView(txtpnDebug);
		txtpnDebug.setTabSize(4);
		txtpnDebug.setFont(new Font("Courier New", Font.PLAIN, 11));
		txtpnDebug.setEditable(false);
	
		btClearDebug = new JButton("Clear");
		btClearDebug.addActionListener(clearDebugButtonClick);
		GridBagConstraints gbc_btClearDebug = new GridBagConstraints();
		gbc_btClearDebug.anchor = GridBagConstraints.NORTH;
		gbc_btClearDebug.gridx = 1;
		gbc_btClearDebug.gridy = 0;
		panel_6.add(btClearDebug, gbc_btClearDebug);
	
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);
	
		btRun = new JButton("Run");
		btRun.addActionListener(runButtonClick);
		toolBar.add(btRun);
		System.out.println("Ready!");
	}

	/**
	 * Extracts the name of the input wave file by truncating the extension.
	 * 
	 * @return <code>Path + FileName - FileExtension</code> of the input file
	 *         name
	 */
	private String getWAVEFILENAME() {
		String s = tfInput.getText();
		int dot = ((dot = s.lastIndexOf('.')) > -1) ? dot : s.length() - 1;
		return s.substring(0, dot);
	}

	private String[] tabNames = new String[] { "Log", "Debug" };

	/**
	 * Creates a <code>Stream</code> that redirects the incoming Data into the
	 * <code>txtpnLog</code> text pane
	 * 
	 * @return a new <code>Stream</code>
	 */
	private PrintStream getLogWorkaround() {
		return new PrintStream(new OutputStream() {
			@Override
			public void write(final int b) throws IOException {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							txtpnLog.getDocument().insertString(
									txtpnLog.getDocument().getLength(),
									new String(new byte[] { (byte) b }), null);
							if (!txtpnLog.isShowing()) {
								logTabPane.setTitleAt(0, tabNames[0] + " - *");
							}
						} catch (BadLocationException e) {
						}
					}
				});
			}
		});
	}

	/**
	 * Creates a <code>Stream</code> that redirects the incoming Data into the
	 * <code>txtpnDebug</code> text pane
	 * 
	 * @return a new <code>Stream</code>
	 */
	private PrintStream getDebugWorkaround() {
		return new PrintStream(new OutputStream() {
			@Override
			public void write(final int b) throws IOException {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							txtpnDebug.getDocument().insertString(
									txtpnDebug.getDocument().getLength(),
									new String(new byte[] { (byte) b }), null);
							if (!txtpnDebug.isShowing()) {
								logTabPane.setTitleAt(1, tabNames[1] + " - *");
							}
						} catch (BadLocationException e) {
						}
					}
				});
			}
		});
	}

	private ActionListener clearLogButtonClick = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			txtpnLog.setText("");
		}
	};

	private ActionListener clearDebugButtonClick = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			txtpnDebug.setText("");
		}
	};

	private ChangeListener logTabPaneChange = new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			logTabPane.setTitleAt(logTabPane.getSelectedIndex(),
					tabNames[logTabPane.getSelectedIndex()]);
		}
	};

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