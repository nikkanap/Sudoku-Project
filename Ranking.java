package sudoku;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Ranking extends JPanel implements ActionListener {
	// javax.swing
	private JLabel rankLabel, usernameLabel, timeLabel;
	private JLabel username[] = new JLabel[10];
	private JLabel rank[] = new JLabel[10];
	private JLabel time[] = new JLabel[10];
	private JPanel innerPanel;
	private JFrame frame;
	private JButton back;
	// java.awt
	private GridBagConstraints gbc;
	private String difficultyStr;

	public Ranking(JFrame frame, int difficulty) {
		this.frame = frame;

		if (difficulty == 1)
			this.difficultyStr = "Easy";
		else if (difficulty == 2)
			this.difficultyStr = "Medium";
		else
			this.difficultyStr = "Hard";

		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();

		this.setOuterLabel();
		this.setInnerPanel();
		this.setOuterButton();
	}

	private void setOuterLabel() {
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(new JLabel("Difficulty: "), gbc);
	}

	private void setInnerPanel() {
		innerPanel = new JPanel();
		innerPanel.setLayout(new GridLayout(11, 3));
		this.setInnerLabels();
		gbc.gridy = 1;
		this.add(innerPanel, gbc);
	}

	private void setInnerLabels() {
		innerPanel.add(new JLabel("Rank"));
		innerPanel.add(new JLabel("Player Name"));
		innerPanel.add(new JLabel("               Time"));

		try {
			if ((new File(difficultyStr + ".txt")).createNewFile())
				System.out.println("File created: " + difficultyStr + ".txt");
			else
				System.out.println("File exists: " + difficultyStr + ".txt");
			Scanner scn = new Scanner(new File(difficultyStr + ".txt"));

			Rank ptr = null, start = null;
			while (scn.hasNext()) {
				String usernameStr = scn.next();
				String timerStr = scn.next();
				usernameStr = usernameStr.substring(0, usernameStr.length() - 1);
				if (start == null)
					start = new Rank(usernameStr, timerStr);
				else {
					int secs1 = this.getTotalSeconds(timerStr);
					Rank prev = null;
					while (true) {
						String ptrTimer = ptr.getTimer();
						int secs2 = this.getTotalSeconds(ptrTimer);
						if (secs1 >= secs2) {
							if (ptr.getNextRank() == null) {
								ptr.setNextRank(new Rank(usernameStr, timerStr));
								break;
							} else {
								prev = ptr;
								ptr = ptr.getNextRank();
							}
						} else {
							Rank temp = new Rank(usernameStr, timerStr);
							if (ptr == start)
								start = temp;
							else
								prev.setNextRank(temp);
							temp.setNextRank(ptr);
							ptr = temp;
							break;
						}
					}
				}
				ptr = start;
			}
			scn.close();

			for (int i = 0; i < 10; i++) {
				time[i] = (start != null) ? new JLabel("               " + start.getTimer())
						: new JLabel("               " + "--:--:--");
				username[i] = (start != null) ? new JLabel(start.getUsername()) : new JLabel("Empty");
				rank[i] = new JLabel(Integer.toString(i + 1));
				innerPanel.add(rank[i]);
				innerPanel.add(username[i]);
				innerPanel.add(time[i]);
				start = (start != null) ? start.getNextRank() : start;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int getTotalSeconds(String str) {
		int secs = 0;
		for (int i = 0; i < 3; i++) {
			int num = Integer.parseInt(str.substring(i * 3, str.length() - (6 - i * 3)));
			secs += num * Math.pow(60, 2 - i);
		}
		return secs;
	}

	private void setOuterButton() {
		back = new JButton("Go Back");
		back.addActionListener(this);
		gbc.gridy = 2;
		this.add(back, gbc);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.setVisible(false);
		frame.remove(this);
		frame.add(new RankLevelsPanel(frame));

	}

}
