package sudoku;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu extends JPanel implements ActionListener {
	// javax.swing
	private JButton start, lastGame, rank, exit;
	private JFrame frame;
	private JLabel title;
	// java.awt
	private GridBagConstraints gbc;

	// Class Constructor
	public MainMenu(JFrame frame) {
		this.frame = frame;
		this.initLayout();
		this.initTitle();
		this.initButtons();
	}

	// Init Methods
	private void initLayout() {
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
	}

	private void initTitle() {
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipadx = 200;
		gbc.ipady = 20;
		title = new JLabel("Classic Sudoku");
		title.setFont(new Font("Arial", Font.PLAIN, 30));
		this.add(title, gbc);
	}

	private void initButtons() {
		lastGame = new JButton("Continue Last Game");
		lastGame.setEnabled((new File("continuedgame.txt")).exists() ? true : false);
		start = new JButton("Start Game");
		rank = new JButton("Ranking");
		exit = new JButton("Exit");
		lastGame.addActionListener(this);
		start.addActionListener(this);
		rank.addActionListener(this);
		exit.addActionListener(this);

		gbc.gridy = 1;
		gbc.ipadx = 50;
		gbc.ipady = 10;
		this.add(lastGame, gbc);
		gbc.gridy = 2;
		gbc.ipadx = 100;
		this.add(start, gbc);
		gbc.gridy = 3;
		gbc.ipadx = 120;
		this.add(rank, gbc);
		gbc.gridy = 4;
		gbc.ipadx = 150;
		this.add(exit, gbc);
	}

	private int[][] getFileContent(Scanner scn) {
		int array[][] = new int[9][9];
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				array[i][j] = Integer.parseInt(scn.next());
		return array;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.setVisible(false);
		frame.remove(this);
		if (e.getSource() == lastGame) {
			try {
				Scanner scn = new Scanner(new File("continuedgame.txt"));
				int toSolveFormatArr[][] = this.getFileContent(scn);
				int toSolveArr[][] = this.getFileContent(scn);
				int time[] = new int[3];
				for (int i = 2; i >= 0; i--)
					time[i] = Integer.parseInt(scn.next());
				frame.add(new GamePanel(frame, new GridFormation(toSolveFormatArr, toSolveArr), time,
						Integer.parseInt(scn.next()), Integer.parseInt(scn.next())));
				scn.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == start)
			frame.add(new LevelsPanel(frame));
		else if (e.getSource() == rank)
			frame.add(new RankLevelsPanel(frame));
		else if (e.getSource() == exit)
			frame.dispose();
	}
}
