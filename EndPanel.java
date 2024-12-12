package sudoku;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EndPanel extends JPanel implements ActionListener {
	private JFrame frame;
	private JButton skip, save;
	private JLabel congrats, difficultyLabel, timerLabel, usernamePrompt;
	private JTextField username;
	private GridBagConstraints gbc;
	private String difficultyStr, timeStr;

	public EndPanel(JFrame frame, int difficulty, String timeStr) {
		File file = new File("continuedgame.txt");
		if (file.exists())
			file.delete();

		if (difficulty == 1)
			difficultyStr = "Easy";
		else if (difficulty == 2)
			difficultyStr = "Medium";
		else
			difficultyStr = "Hard";
		this.timeStr = timeStr;
		this.frame = frame;

		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();

		this.setLabels();
		this.setFields();
		this.setButtons();
	}

	public void setLabels() {
		congrats = new JLabel("Congratulations! You completed the puzzle!");

		difficultyLabel = new JLabel("Difficulty: " + difficultyStr);
		timerLabel = new JLabel(timeStr);
		usernamePrompt = new JLabel("Enter your username to have a chance in the ranking!");
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(congrats, gbc);
		gbc.gridy = 1;
		this.add(difficultyLabel, gbc);
		gbc.gridy = 2;
		this.add(timerLabel, gbc);
		gbc.gridy = 3;
		this.add(usernamePrompt, gbc);
	}

	public void setButtons() {
		skip = new JButton("Skip");
		skip.addActionListener(this);
		save = new JButton("Save");
		save.addActionListener(this);

		gbc.gridy = 5;
		gbc.ipadx = 100;
		this.add(skip, gbc);
		gbc.gridx = 1;
		this.add(save, gbc);
	}

	public void setFields() {
		username = new JTextField();
		gbc.gridy = 4;
		gbc.ipadx = 200;
		this.add(username, gbc);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == save) {
			String filename = difficultyStr + ".txt";
			try {
				if (new File(filename).createNewFile())
					System.out.println("File created: " + filename);
				else
					System.out.println("File already exists");
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				FileWriter fw = new FileWriter(filename, true);
				fw.write(username.getText() + ", " + timeStr + "\n");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		this.setVisible(false);
		frame.remove(this);
		frame.add(new MainMenu(frame));
	}

}
