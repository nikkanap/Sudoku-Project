package sudoku;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RankLevelsPanel extends JPanel implements ActionListener {
	private JFrame frame;
	private JLabel difficulty;
	private JButton easy, medium, hard, back;
	private GridBagConstraints gbc;

	public RankLevelsPanel(JFrame frame) {
		this.frame = frame;
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();

		this.setLabels();
		this.setButtons();
		// TODO Auto-generated constructor stub
	}

	private void setLabels() {
		difficulty = new JLabel("Choose rank difficulty");
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(difficulty, gbc);
	}

	private void setButtons() {
		easy = new JButton("Easy");
		easy.addActionListener(this);
		medium = new JButton("Medium");
		medium.addActionListener(this);
		hard = new JButton("Hard");
		hard.addActionListener(this);
		back = new JButton("Go Back");
		back.addActionListener(this);
		gbc.gridy = 1;
		this.add(easy, gbc);
		gbc.gridy = 2;
		this.add(medium, gbc);
		gbc.gridy = 3;
		this.add(hard, gbc);
		gbc.gridy = 4;
		this.add(back, gbc);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.setVisible(false);
		frame.remove(this);
		if (e.getSource() == back) {
			frame.add(new MainMenu(frame));
		} else {
			int num = 0;
			if (e.getSource() == easy)
				num = 1;
			else if (e.getSource() == medium)
				num = 2;
			else if (e.getSource() == hard)
				num = 3;
			frame.add(new Ranking(frame, num));
		}
	}

}
