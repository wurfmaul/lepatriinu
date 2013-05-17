package at.jku.amp.lepatriinu.ui;

import java.awt.BorderLayout;
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
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;

import at.cp.jku.teaching.amprocessing.Runner;
import at.jku.amp.lepatriinu.BeatDetector;
import at.jku.amp.lepatriinu.OnsetDetector;
import at.jku.amp.lepatriinu.TempoExtractor;

public class SwingUI extends JFrame {
	private static final long serialVersionUID = 3738266768401303836L;

	private JPanel contentPane;
	private JTextField tfInput;
	private JTextArea txtpnLog;
	private JButton btClear;
	private JPanel panel;
	private JTabbedPane tabbedPane;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JScrollPane scrollPane;
	private JPanel panel_1;
	private JTextField tfOutput;
	private JButton btOutputBrowse;
	private JPanel panel_2;
	private JTextField tfOnsetFile;
	private JButton btOnsetBrowse;
	private JPanel panel_7;
	private JTextField tfTempoFile;
	private JButton btTempoBrowse;
	private JPanel panel_8;
	private JTextField tfBeatFile;
	private JButton btBeatBrowse;
	private JCheckBox cbBeat;
	private JCheckBox cbTempo;
	private JCheckBox cbOnset;
	private final JSplitPane splitPane = new JSplitPane();
	private JPanel panel_9;
	private JPanel panel_10;
	private JPanel panel_11;
	private JScrollPane scrollPane_1;
	private JList<OnsetDetector> lOnset;
	private JScrollPane scrollPane_2;
	private JList<TempoExtractor> lTempo;
	private JScrollPane scrollPane_3;
	private JList<BeatDetector> lBeat;
	private JToolBar toolBar;
	private JButton btRun;

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

	/**
	 * Create the frame.
	 */
	private SwingUI() {
		setTitle("Lepatriinu - Audio and Music Processor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 531, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		System.setOut(new PrintStream(new OutputStream() {
			@Override
			public void write(final int b) throws IOException {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {

							txtpnLog.getDocument().insertString(
									txtpnLog.getDocument().getLength(),
									new String(new byte[] { (byte) b }), null);
						} catch (BadLocationException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}));
		splitPane.setResizeWeight(0.5);
		splitPane.setContinuousLayout(true);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setLeftComponent(tabbedPane);

		panel_3 = new JPanel();
		panel_3.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("Files", null, panel_3, null);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 0, 0 };
		gbl_panel_3.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel_3.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		panel_4 = new JPanel();
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

		tfInput = new JTextField(".\\WavFile.wav");
		GridBagConstraints gbc_tfInput = new GridBagConstraints();
		gbc_tfInput.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfInput.insets = new Insets(0, 0, 0, 5);
		gbc_tfInput.gridx = 0;
		gbc_tfInput.gridy = 0;
		panel_4.add(tfInput, gbc_tfInput);
		tfInput.setColumns(10);

		JButton btInputBrowse = new JButton("Browse ...");
		btInputBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fj = new JFileChooser(tfInput.getText());
				fj.setFileFilter(new FileFilter() {
					@Override
					public String getDescription() {
						return "WAV Audio Files";
					}

					@Override
					public boolean accept(File f) {
						if (f.isDirectory())
							return true;
						String name = f.getName();
						int dot = name.lastIndexOf('.');
						if (dot > 0) {
							return name.substring(dot + 1, name.length())
									.equalsIgnoreCase("wav");
						}
						return false;
					}
				});
				if (fj.showOpenDialog(SwingUI.this) == JFileChooser.APPROVE_OPTION)
					tfInput.setText(fj.getSelectedFile().getAbsolutePath());
			}
		});
		GridBagConstraints gbc_btInputBrowse = new GridBagConstraints();
		gbc_btInputBrowse.gridx = 1;
		gbc_btInputBrowse.gridy = 0;
		panel_4.add(btInputBrowse, gbc_btInputBrowse);

		panel_1 = new JPanel();
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
		GridBagConstraints gbc_btOutputBrowse = new GridBagConstraints();
		gbc_btOutputBrowse.gridx = 1;
		gbc_btOutputBrowse.gridy = 0;
		panel_1.add(btOutputBrowse, gbc_btOutputBrowse);

		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Onsest Ground Truth",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 2;
		panel_3.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 86, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 20, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		cbOnset = new JCheckBox("Use");
		cbOnset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfOnsetFile.setEnabled(cbOnset.isSelected());
				btOnsetBrowse.setEnabled(cbOnset.isSelected());
			}
		});
		GridBagConstraints gbc_cbOnset = new GridBagConstraints();
		gbc_cbOnset.insets = new Insets(0, 0, 0, 5);
		gbc_cbOnset.gridx = 0;
		gbc_cbOnset.gridy = 0;
		panel_2.add(cbOnset, gbc_cbOnset);

		tfOnsetFile = new JTextField(".\\");
		tfOnsetFile.setEnabled(false);
		tfOnsetFile.setColumns(10);
		GridBagConstraints gbc_tfOnsetFile = new GridBagConstraints();
		gbc_tfOnsetFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfOnsetFile.anchor = GridBagConstraints.NORTH;
		gbc_tfOnsetFile.insets = new Insets(0, 0, 0, 5);
		gbc_tfOnsetFile.gridx = 1;
		gbc_tfOnsetFile.gridy = 0;
		panel_2.add(tfOnsetFile, gbc_tfOnsetFile);

		btOnsetBrowse = new JButton("Browse ...");
		btOnsetBrowse.setEnabled(false);
		GridBagConstraints gbc_btOnsetBrowse = new GridBagConstraints();
		gbc_btOnsetBrowse.gridx = 2;
		gbc_btOnsetBrowse.gridy = 0;
		panel_2.add(btOnsetBrowse, gbc_btOnsetBrowse);

		panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Tempo Ground Truth",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.insets = new Insets(0, 0, 5, 0);
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 3;
		panel_3.add(panel_7, gbc_panel_7);
		GridBagLayout gbl_panel_7 = new GridBagLayout();
		gbl_panel_7.columnWidths = new int[] { 0, 86, 0, 0 };
		gbl_panel_7.rowHeights = new int[] { 20, 0 };
		gbl_panel_7.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_7.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_7.setLayout(gbl_panel_7);

		cbTempo = new JCheckBox("Use");
		cbTempo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfTempoFile.setEnabled(cbTempo.isSelected());
				btTempoBrowse.setEnabled(cbTempo.isSelected());
			}
		});
		GridBagConstraints gbc_cbTempo = new GridBagConstraints();
		gbc_cbTempo.insets = new Insets(0, 0, 0, 5);
		gbc_cbTempo.gridx = 0;
		gbc_cbTempo.gridy = 0;
		panel_7.add(cbTempo, gbc_cbTempo);

		tfTempoFile = new JTextField(".");
		tfTempoFile.setEnabled(false);
		tfTempoFile.setColumns(10);
		GridBagConstraints gbc_tfTempoFile = new GridBagConstraints();
		gbc_tfTempoFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTempoFile.anchor = GridBagConstraints.NORTH;
		gbc_tfTempoFile.insets = new Insets(0, 0, 0, 5);
		gbc_tfTempoFile.gridx = 1;
		gbc_tfTempoFile.gridy = 0;
		panel_7.add(tfTempoFile, gbc_tfTempoFile);

		btTempoBrowse = new JButton("Browse ...");
		btTempoBrowse.setEnabled(false);
		GridBagConstraints gbc_btTempoBrowse = new GridBagConstraints();
		gbc_btTempoBrowse.gridx = 2;
		gbc_btTempoBrowse.gridy = 0;
		panel_7.add(btTempoBrowse, gbc_btTempoBrowse);

		panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Beat Ground Truth",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_8 = new GridBagConstraints();
		gbc_panel_8.fill = GridBagConstraints.BOTH;
		gbc_panel_8.gridx = 0;
		gbc_panel_8.gridy = 4;
		panel_3.add(panel_8, gbc_panel_8);
		GridBagLayout gbl_panel_8 = new GridBagLayout();
		gbl_panel_8.columnWidths = new int[] { 0, 86, 0, 0 };
		gbl_panel_8.rowHeights = new int[] { 20, 0 };
		gbl_panel_8.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_8.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_8.setLayout(gbl_panel_8);

		cbBeat = new JCheckBox("Use");
		cbBeat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfBeatFile.setEnabled(cbBeat.isSelected());
				btBeatBrowse.setEnabled(cbBeat.isSelected());
			}
		});
		GridBagConstraints gbc_cbBeat = new GridBagConstraints();
		gbc_cbBeat.insets = new Insets(0, 0, 0, 5);
		gbc_cbBeat.gridx = 0;
		gbc_cbBeat.gridy = 0;
		panel_8.add(cbBeat, gbc_cbBeat);

		tfBeatFile = new JTextField(".");
		tfBeatFile.setEnabled(false);
		tfBeatFile.setColumns(10);
		GridBagConstraints gbc_tfBeatFile = new GridBagConstraints();
		gbc_tfBeatFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfBeatFile.anchor = GridBagConstraints.NORTH;
		gbc_tfBeatFile.insets = new Insets(0, 0, 0, 5);
		gbc_tfBeatFile.gridx = 1;
		gbc_tfBeatFile.gridy = 0;
		panel_8.add(tfBeatFile, gbc_tfBeatFile);

		btBeatBrowse = new JButton("Browse ...");
		btBeatBrowse.setEnabled(false);
		GridBagConstraints gbc_btBeatBrowse = new GridBagConstraints();
		gbc_btBeatBrowse.gridx = 2;
		gbc_btBeatBrowse.gridy = 0;
		panel_8.add(btBeatBrowse, gbc_btBeatBrowse);

		panel_5 = new JPanel();
		panel_5.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("Settings", null, panel_5, null);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[] { 0, 0 };
		gbl_panel_5.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_5.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_5.rowWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		panel_5.setLayout(gbl_panel_5);

		panel_9 = new JPanel();
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

		scrollPane_1 = new JScrollPane();
		panel_9.add(scrollPane_1);

		lOnset = new JList<OnsetDetector>();
		lOnset.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(lOnset);

		panel_10 = new JPanel();
		panel_10.setBorder(new TitledBorder(null, "Tempo",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_10 = new GridBagConstraints();
		gbc_panel_10.insets = new Insets(0, 0, 5, 0);
		gbc_panel_10.fill = GridBagConstraints.BOTH;
		gbc_panel_10.gridx = 0;
		gbc_panel_10.gridy = 1;
		panel_5.add(panel_10, gbc_panel_10);
		panel_10.setLayout(new BorderLayout(0, 0));

		scrollPane_2 = new JScrollPane();
		panel_10.add(scrollPane_2, BorderLayout.CENTER);

		lTempo = new JList<TempoExtractor>();
		lTempo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_2.setViewportView(lTempo);

		panel_11 = new JPanel();
		panel_11.setBorder(new TitledBorder(null, "Beat", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_11 = new GridBagConstraints();
		gbc_panel_11.fill = GridBagConstraints.BOTH;
		gbc_panel_11.gridx = 0;
		gbc_panel_11.gridy = 2;
		panel_5.add(panel_11, gbc_panel_11);
		panel_11.setLayout(new BorderLayout(0, 0));

		scrollPane_3 = new JScrollPane();
		panel_11.add(scrollPane_3, BorderLayout.CENTER);

		lBeat = new JList<BeatDetector>();
		lBeat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_3.setViewportView(lBeat);

		panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setBorder(new TitledBorder(null, "Log", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		btClear = new JButton("Clear");
		btClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtpnLog.setText("");
			}
		});

		scrollPane = new JScrollPane();
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
		GridBagConstraints gbc_btClear = new GridBagConstraints();
		gbc_btClear.anchor = GridBagConstraints.NORTH;
		gbc_btClear.gridx = 1;
		gbc_btClear.gridy = 0;
		panel.add(btClear, gbc_btClear);

		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);

		btRun = new JButton("Run");
		btRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Runner.main(new String[] {
						"-i " + tfInput.getText(),
						"-d " + tfOutput.getText(),
						cbOnset.isSelected() ? "-o " + tfOnsetFile.getText()
								: "",
						cbTempo.isSelected() ? "-t " + tfTempoFile.getText()
								: "",
						cbBeat.isSelected() ? "-b " + tfBeatFile.getText() : "" });
			}
		});
		toolBar.add(btRun);
		System.out.println("Ready!");
	}
}