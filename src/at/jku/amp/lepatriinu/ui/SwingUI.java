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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicDirectoryModel;
import javax.swing.text.BadLocationException;

import at.cp.jku.teaching.amprocessing.Runner;
import at.jku.amp.lepatriinu.utils.FileUtils;

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

	private static final String STARTING_DIRECTORY = "data\\";
	private static final String OUTPUT_DIRECTORY = ".\\output\\";
	private JCheckBox cbOnset;
	private JCheckBox cbTempo;
	private JCheckBox cbBeat;
	private JButton btnRunAll;

	private JButton btRunSel;

	private JList<Object> lFiles;
	private BasicDirectoryModel bdmInputFiles;
	private JList<Object> lOutput;
	private BasicDirectoryModel bdmOutputFiles;

	private JTextArea txtpnOutput;

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
	private void run(String path, String wavefilename) {
		Runner.main(new String[] { "-i", path + wavefilename + ".wav", "-d",
				OUTPUT_DIRECTORY, cbOnset.isSelected() ? "-o" : "",
				cbOnset.isSelected() ? path + wavefilename + ".onsets" : "",
				cbTempo.isSelected() ? "-t" : "",
				cbTempo.isSelected() ? path + wavefilename + ".bpms" : "",
				cbBeat.isSelected() ? "-b" : "",
				cbBeat.isSelected() ? path + wavefilename + ".beats" : "" });
		loadOutputBox();
	}

	private ActionListener runAllButtonClick = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			for (File f : bdmInputFiles.getFiles()) {
				run(STARTING_DIRECTORY, killExtension(f.getName()));
			}
		}
	};

	private ActionListener runButtonClick = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			for (int i : lFiles.getSelectedIndices()) {
				run(STARTING_DIRECTORY, killExtension(bdmInputFiles.getFiles()
						.get(i).getName()));
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
		System.out.println("starting off");
		
		File p = new File(OUTPUT_DIRECTORY + "\\data\\");
		System.err.println(p.getAbsolutePath());
		System.err.println(p.mkdirs());

		setTitle("Lepatriinu - Audio and Music Processor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 757, 527);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

//		JSplitPane splitPane = new JSplitPane();
//		splitPane.setResizeWeight(0.5);
//		splitPane.setContinuousLayout(true);
//		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
//		contentPane.add(splitPane, BorderLayout.CENTER);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
//		splitPane.setLeftComponent(tabbedPane);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EmptyBorder(5, 5, 5, 5));
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
		tabbedPane.addTab("Output", null, panel_5, null);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[] { 0, 0 };
		gbl_panel_5.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_5.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_5.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
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

		lOutput = new JList<>();
		lOutput.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					int selI = lOutput.getSelectedIndex();
					if (selI != -1) {
						txtpnOutput.setText(FileUtils.readFile(bdmOutputFiles
								.getFiles().get(selI).getAbsolutePath()));
					}
				}
			}
		});
		lOutput.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		loadOutputBox();
		scrollPane_1.setViewportView(lOutput);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "File content",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		panel_5.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 0;
		gbc_scrollPane_2.gridy = 0;
		panel_1.add(scrollPane_2, gbc_scrollPane_2);

		txtpnOutput = new JTextArea();
		scrollPane_2.setViewportView(txtpnOutput);

		logTabPane = new JTabbedPane(JTabbedPane.BOTTOM);
		logTabPane.addChangeListener(logTabPaneChange);
//		splitPane.setRightComponent(logTabPane);

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

		btRunSel = new JButton("Run selected");
		btRunSel.setEnabled(false);
		btRunSel.addActionListener(runButtonClick);

		btnRunAll = new JButton("Run all");
		btnRunAll.addActionListener(runAllButtonClick);
		toolBar.add(btnRunAll);
		toolBar.add(btRunSel);
		//
		// System.setOut(getLogWorkaround());
		// System.setErr(getDebugWorkaround());

		System.out.println("Ready!");
	}

	private void loadOutputBox() {
		JFileChooser fj;
		fj = new JFileChooser(OUTPUT_DIRECTORY + "\\data");
		fj.setFileFilter(FileUtils.getFileFilter("eval"));
		bdmOutputFiles = new BasicDirectoryModel(fj);
		lOutput.setModel(bdmOutputFiles);
	}

	protected String killExtension(String name) {
		return name.substring(0, name.lastIndexOf('.'));
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