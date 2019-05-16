package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class UI 
{
	private JFrame frame;
	protected JPanel mainPanel, dialoguePanel, comparePanel;
	private JLabel BG;
	private JFileChooser chooser;
	private String ogFolder = "", newFolder = "", outputFolder = "", folder1 = "", folder2 = "", folder3 = "";
	
	private boolean soundToggle = true;
	private JButton btnSound;
	private MusicPlayer music = new MusicPlayer();
	
	private CompareCount compare = new CompareCount();
	
	public void start(MenuManager menu)
	{
		showStart(menu);

		//Initialize Other Menus
		showDialogue(menu);
		dialoguePanel.setVisible(false);
		
		showCompare(menu);
		comparePanel.setVisible(false);
		
		// Initialize Music
		music.playMusic("/Resources/Music/Background Music.wav");
	}
	
	public void showStart(MenuManager menu)
	{
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) 
		{
			System.out.println("Shit");
		}
		
		// File Explorer
		chooser = new JFileChooser();
		String userDir = System.getProperty("user.home"); // Find the user Folder
		chooser.setCurrentDirectory(new File(userDir + "/Desktop")); // Set to User's Desktop
		chooser.setDialogTitle("Select File"); // GUI Title
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		// Main Menu
		frame = new JFrame();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setTitle("SV Tool Kit");
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/Resources/Images/Icon.png")));
		
		btnSound = new JButton("");
		btnSound.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				// Change Toggle
				if(soundToggle == true)
					soundToggle = false;
				
				else
					soundToggle = true;
				
				// Act on Toggle
				if(soundToggle == true)
				{
					btnSound.setIcon(new ImageIcon(this.getClass().getResource("/Resources/Images/volume icon.png")));
					music.resume();
				}
				
				else
				{
					btnSound.setIcon(new ImageIcon(this.getClass().getResource("/Resources/Images/volume icon - muted.png")));
					music.pause();
				}
			}
		}
		);
		btnSound.setBounds(10, 10, 64, 64);
		frame.getContentPane().add(btnSound);
		btnSound.setContentAreaFilled(false);
		btnSound.setFocusPainted(false);
		btnSound.setOpaque(false);
		btnSound.setIcon(new ImageIcon(this.getClass().getResource("/Resources/Images/volume icon.png")));
		
		mainPanel = new JPanel();
		mainPanel.setOpaque(false);
		mainPanel.setBounds(0, 0, 794, 571);
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		JPanel choicePanel = new JPanel();
		choicePanel.setBounds(108, 334, 577, 150);
		mainPanel.add(choicePanel);
		choicePanel.setOpaque(false);
		choicePanel.setLayout(null);
		
		JButton btnDialogueMerger = new JButton("Dialogue Merger");
		btnDialogueMerger.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				menu.showDialogue();
			}
		}
		);
		btnDialogueMerger.setBackground(Color.WHITE);
		btnDialogueMerger.setToolTipText("Merge Dialogue from Content Patcher Files");
		btnDialogueMerger.setFocusPainted(false);
		btnDialogueMerger.setBounds(76, 11, 176, 59);
		btnDialogueMerger.setFont(new Font("Tahoma", Font.PLAIN, 14));
		choicePanel.add(btnDialogueMerger);
		
		JButton btnCompareFiles = new JButton("Compare File Amounts");
		btnCompareFiles.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				menu.showCompare();
			}
		}
		);
		btnCompareFiles.setToolTipText("Compare 2 Mods' File Count");
		btnCompareFiles.setFocusPainted(false);
		btnCompareFiles.setBackground(Color.WHITE);
		btnCompareFiles.setBounds(319, 11, 176, 59);
		btnCompareFiles.setFont(new Font("Tahoma", Font.PLAIN, 14));
		choicePanel.add(btnCompareFiles);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setBounds(340, 470, 113, 41);
		mainPanel.add(btnQuit);
		btnQuit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		}
		);
		btnQuit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnQuit.setFocusPainted(false);
		
		BG = new JLabel("");
		Image bg = new ImageIcon(this.getClass().getResource("/Resources/Images/BG.jpg")).getImage();
		BG.setIcon(new ImageIcon(bg));
		BG.setBounds(0, 0, 794, 571);
		mainPanel.add(BG);
		
		choicePanel.setVisible(true);
	}
	
	public void showDialogue(MenuManager menu)
	{	
		dialoguePanel = new JPanel();
		dialoguePanel.setOpaque(false);
		dialoguePanel.setBounds(0, 0, 794, 571);
		frame.getContentPane().add(dialoguePanel);
		dialoguePanel.setLayout(null);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(91, 93, 612, 439);
		dialoguePanel.add(buttonPanel);
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(54, 192, 504, 187);
		buttonPanel.add(scrollPane);
		
		JTextArea txtrResults = new JTextArea();
		txtrResults.setFont(new Font("Arial", Font.PLAIN, 14));
		txtrResults.setText("Select Folders and then Hit Run!");
		scrollPane.setViewportView(txtrResults);

		JButton btnSelectFile = new JButton("Select a Folder");
		btnSelectFile.setHorizontalAlignment(SwingConstants.LEFT);
		btnSelectFile.setBounds(139, 53, 419, 24);
		buttonPanel.add(btnSelectFile);
		btnSelectFile.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int result = chooser.showOpenDialog(btnSelectFile);

				if(result == JFileChooser.APPROVE_OPTION)
				{
					if(chooser.getSelectedFile() != null)
						ogFolder = chooser.getSelectedFile().getPath();

					if(ogFolder.compareTo("") != 0)
						btnSelectFile.setText(ogFolder);
				}
			}
		}
				);
		btnSelectFile.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSelectFile.setFocusPainted(false);

		JButton btnNewFiles = new JButton("Select a Folder");
		btnNewFiles.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewFiles.setBounds(139, 94, 419, 24);
		buttonPanel.add(btnNewFiles);
		btnNewFiles.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int result = chooser.showOpenDialog(btnNewFiles);

				if(result == JFileChooser.APPROVE_OPTION)
				{
					if(chooser.getSelectedFile() != null)
						newFolder = chooser.getSelectedFile().getPath();

					if(newFolder.compareTo("") != 0)
						btnNewFiles.setText(newFolder);
				}
			}
		}
				);
		btnNewFiles.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewFiles.setFocusPainted(false);
		
		JButton btnOutputFolder = new JButton("Select a Folder");
		btnOutputFolder.setHorizontalAlignment(SwingConstants.LEFT);
		btnOutputFolder.setBounds(139, 133, 419, 24);
		buttonPanel.add(btnOutputFolder);
		btnOutputFolder.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int result = chooser.showOpenDialog(btnOutputFolder);

				if(result == JFileChooser.APPROVE_OPTION)
				{
					if(chooser.getSelectedFile() != null)
						outputFolder = chooser.getSelectedFile().getPath();

					if(outputFolder.compareTo("") != 0)
						btnOutputFolder.setText(outputFolder);
				}
			}
		}
		);
		btnOutputFolder.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnOutputFolder.setFocusPainted(false);
		
		JCheckBox chckbxOverlap = new JCheckBox("Would you like to have the new dialogue overlap the original?");
		chckbxOverlap.setOpaque(false);
		chckbxOverlap.setForeground(Color.WHITE);
		chckbxOverlap.setFont(new Font("Arial", Font.BOLD, 16));
		chckbxOverlap.setBounds(60, 162, 491, 23);
		buttonPanel.add(chckbxOverlap);
		
		JButton back = new JButton("Back");
		back.setBounds(312, 390, 113, 41);
		buttonPanel.add(back);
		back.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				menu.showMain();
				btnSelectFile.setText("Select a Folder");
				btnNewFiles.setText("Select a Folder");
				btnOutputFolder.setText("Select a Folder");
				
				ogFolder = "";
				newFolder = "";
				outputFolder = "";
				txtrResults.setText("Select Folders and then Hit Run!");
			}
		}
		);
		back.setFont(new Font("Tahoma", Font.PLAIN, 16));
		back.setFocusPainted(false);
		
		JButton btnRun = new JButton("Run");
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnRun.setFocusPainted(false);
		btnRun.setBounds(189, 390, 113, 41);
		btnRun.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				txtrResults.setText("OG: " + ogFolder + "\nNew: " + newFolder + "\nOutput: " + outputFolder);
				DialogueMerger merger = new DialogueMerger();
				
				if(ogFolder.compareTo("") != 0 && newFolder.compareTo("") != 0 && outputFolder.compareTo("") != 0 )
					txtrResults.setText(merger.mergeFiles(ogFolder, newFolder, outputFolder, chckbxOverlap.isEnabled()));
			}
		}
		);
		buttonPanel.add(btnRun);
		
		JLabel BG = new JLabel("");
		Image bg = new ImageIcon(this.getClass().getResource("/Resources/Images/BG - Dialogue.jpg")).getImage();
		BG.setBounds(0, 0, 794, 571);
		dialoguePanel.add(BG);
		BG.setIcon(new ImageIcon(bg));
	}
		
	public void showCompare(MenuManager menu)
	{
		comparePanel = new JPanel();
		comparePanel.setOpaque(false);
		comparePanel.setBounds(0, 0, 794, 571);
		frame.getContentPane().add(comparePanel);
		comparePanel.setLayout(null);
		
		JLabel BG = new JLabel("");
		Image bg = new ImageIcon(this.getClass().getResource("/Resources/Images/BG - Compare.jpg")).getImage();
		
		JButton btnFolder1 = new JButton("Click Here To Select A Folder");
		btnFolder1.setHorizontalAlignment(SwingConstants.LEFT);
		btnFolder1.setBounds(182, 169, 519, 24);
		comparePanel.add(btnFolder1);
		btnFolder1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int result = chooser.showOpenDialog(btnFolder1);

				if(result == JFileChooser.APPROVE_OPTION)
				{
					if(chooser.getSelectedFile() != null)
						folder1 = chooser.getSelectedFile().getPath();

					if(folder1.compareTo("") != 0)
						btnFolder1.setText(folder1);
				}
			}
		}
		);
		btnFolder1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnFolder1.setFocusPainted(false);
		
		JButton btnFolder2 = new JButton("Click Here To Select A Folder");
		btnFolder2.setHorizontalAlignment(SwingConstants.LEFT);
		btnFolder2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnFolder2.setFocusPainted(false);
		btnFolder2.setBounds(182, 201, 519, 24);
		comparePanel.add(btnFolder2);
		btnFolder2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int result = chooser.showOpenDialog(btnFolder2);

				if(result == JFileChooser.APPROVE_OPTION)
				{
					if(chooser.getSelectedFile() != null)
						folder2 = chooser.getSelectedFile().getPath();

					if(folder2.compareTo("") != 0)
						btnFolder2.setText(folder2);
				}
			}
		}
		);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(182, 312, 519, 164);
		comparePanel.add(scrollPane);
		
		JTextArea txtrOutput = new JTextArea();
		txtrOutput.setEditable(false);
		txtrOutput.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		txtrOutput.setText("Select 2 Folders and Hit Run!");
		scrollPane.setViewportView(txtrOutput);
		
		JLabel lblCount = new JLabel("0");
		lblCount.setToolTipText("Number of Files that Folder 1 is Missing");
		lblCount.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblCount.setOpaque(true);
		lblCount.setBounds(113, 335, 59, 17);
		comparePanel.add(lblCount);
		
		JButton btnRun = new JButton("Run");
		btnRun.setBounds(173, 498, 113, 41);
		comparePanel.add(btnRun);
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnRun.setFocusPainted(false);
		btnRun.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(folder1.compareTo("") != 0 && folder2.compareTo("") != 0)
				{
					txtrOutput.setText(compare.compare(folder1, folder2));
				}
				
				lblCount.setText(compare.getCount() + "");
			}
		}
		);
		
		JButton btnOut = new JButton("Output");
		btnOut.setToolTipText("Output the missing files to a folder.");
		btnOut.setBounds(339, 498, 113, 41);
		comparePanel.add(btnOut);
		btnOut.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int result = chooser.showOpenDialog(btnOut);
				
				if(result == JFileChooser.APPROVE_OPTION)
				{
					if(chooser.getSelectedFile() != null)
						folder3 = chooser.getSelectedFile().getPath();

					if(folder3 != "" && folder3 != null && txtrOutput.getText().compareTo("Select 2 Folders and Hit Run!") != 0)
					{
						ArrayList<String> output = compare.getExtras();

						if(output.size() != 0)
						{
							FileSaver saver = new FileSaver();
							saver.save(output, folder3);
						}
					}
				}
			}
		}
		);
		btnOut.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnOut.setFocusPainted(false);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(505, 498, 113, 41);
		comparePanel.add(btnBack);
		btnBack.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				menu.showMain();
				btnFolder1.setText("Click Here To Select A Folder");
				btnFolder2.setText("Click Here To Select A Folder");
				txtrOutput.setText("Select 2 Folders and Hit Run!");
				lblCount.setText("0");
				folder1 = "";
				folder2 = "";
			}
		}
		);
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBack.setFocusPainted(false);
		
		BG.setBounds(0, 0, 794, 571);
		comparePanel.add(BG);
		BG.setIcon(new ImageIcon(bg));
	}
}
