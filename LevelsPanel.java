package sudoku;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LevelsPanel extends JPanel implements ActionListener {
	private JFrame frame;
	private JButton easy, medium, hard, back;
	private JLabel levelsLabel;
	private GridBagConstraints gbc;

	public LevelsPanel(JFrame frame) {
		this.frame = frame;
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		this.setButtons();
		this.setLabel();
	}

	private void setLabel() {
		levelsLabel = new JLabel("Choose a difficulty");
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(levelsLabel, gbc);
	}

	private void setButtons() {
		easy = new JButton("EASY");
		medium = new JButton("MEDIUM");
		hard = new JButton("HARD");
		back = new JButton("Go Back");
		easy.addActionListener(this);
		medium.addActionListener(this);
		hard.addActionListener(this);
		back.addActionListener(this);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.ipadx = 100;
		gbc.ipady = 10;
		this.add(easy, gbc);
		gbc.gridy = 2;
		gbc.ipadx = 100;
		gbc.ipady = 10;
		this.add(medium, gbc);
		gbc.gridy = 3;
		gbc.ipadx = 100;
		gbc.ipady = 10;
		this.add(hard, gbc);
		gbc.gridy = 4;
		gbc.ipadx = 50;
		gbc.ipady = 10;
		this.add(back, gbc);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.setVisible(false);
		frame.remove(this);
		if (e.getSource() == back)
			frame.add(new MainMenu(frame));
		else {
			int difficulty = 1, rmGrids = 25;
			if (e.getSource() != easy) {
				difficulty = (e.getSource() == medium) ? 2 : 3;
				rmGrids = (e.getSource() == medium) ? 35 : 45;
			}
			frame.add(new GamePanel(frame, new GridFormation(rmGrids + (new Random()).nextInt(10)), new int[3],
					difficulty, 5));
		}
	}

}
