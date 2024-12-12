package sudoku;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PausePanel extends JPanel implements ActionListener {
	private JButton resume, restart, mainmenu;
	private JFrame frame;
	private JLabel label;
	private GridBagConstraints gbc;
	private GridFormation gf;
	private int difficulty, hints;
	private int time[];

	public PausePanel(JFrame frame, GridFormation gf, Timer timer, TimerTask task, int difficulty, int hints,
			int time[]) {
		this.frame = frame;
		this.gf = gf;
		this.time = time;
		this.hints = hints;
		this.difficulty = difficulty;
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		this.setLabels();
		this.setButtons();
	}

	private void setLabels() {
		label = new JLabel("Game Paused");
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(label, gbc);
	}

	private void setButtons() {
		resume = new JButton("Resume");
		restart = new JButton("Restart Game");
		mainmenu = new JButton("Main Menu");
		resume.addActionListener(this);
		restart.addActionListener(this);
		mainmenu.addActionListener(this);
		gbc.gridy = 1;
		this.add(resume, gbc);
		gbc.gridy = 2;
		this.add(restart, gbc);
		gbc.gridy = 3;
		this.add(mainmenu, gbc);
	}

	private void writeLinkedListToFile(FileWriter fw, Answer[][] linkedList) throws IOException {
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				fw.write(linkedList[i][j].getAns() + " ");
		fw.write("\n");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.setVisible(false);
		frame.remove(this);
		if (e.getSource() == resume)
			frame.add(new GamePanel(frame, gf, time, difficulty, hints));
		else if (e.getSource() == restart) {
			for (int i = 0; i < 3; i++)
				time[i] = 0;
			gf.setToSolve(gf.copyLinkedList(gf.getToSolveFormat()));
			frame.add(new GamePanel(frame, gf, time, difficulty, 5));
		} else {
			try {
				File file = new File("continuedgame.txt");
				if(file.exists())
					file.delete();
				file.createNewFile();
			} catch(IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				FileWriter fw = new FileWriter("continuedgame.txt", true);
				this.writeLinkedListToFile(fw, gf.getToSolveFormat());
				this.writeLinkedListToFile(fw, gf.getToSolve());
				for (int i = 2; i >= 0; i--) {
					if (time[i] < 10)
						fw.write("0");
					fw.write(time[i] + " ");
				}
				fw.write(Integer.toString(difficulty) + "\n");
				fw.write(Integer.toString(hints) + "\n");
				fw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frame.add(new MainMenu(frame));
		}
	}

}
