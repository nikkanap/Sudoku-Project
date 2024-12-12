package sudoku;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
	// Swing Components
	private JButton pause, erase, hint, clear, check, autocomplete;
	private JButton num[] = new JButton[9];
	private JFrame frame;
	private JLabel difficultyLabel, timerLabel;
	private JPanel headerPanel, buttonsPanel;
	// AWT Contents
	private GridBagConstraints gbc;
	private GridFormation gf;
	private TimerTask task;
	private Timer timer;
	// Classes
	private Grid grid;
	// Usual Data Types
	private String difficultyStr, timeStr;
	private int difficulty, hints;
	private int time[];

	public GamePanel(JFrame frame, GridFormation gf, int time[], int difficulty, int hints) {
		this.frame = frame; // JFrame
		this.time = time; // Class
		this.gf = gf; // Class
		this.difficulty = difficulty; // int
		this.hints = hints;

		// KeyListener to Frame
		this.frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (!(autocomplete.getText().equals("END GAME") || grid.getEditable() == null)) {
					if (grid.getEditable().getForeground() != Color.BLUE) {
						if ((int) e.getKeyChar() >= 49 && (int) e.getKeyChar() <= 57)
							grid.setLabelText(e.getKeyChar());
						else if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE)
							grid.setLabelText(' ');
					}
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// editable.setText(KeyEvent.getKeyText(e.getKeyCode()));
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}

		});
		frame.setFocusable(true);
		frame.requestFocusInWindow();

		// GamePanel Layout And Setup
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		setPanels();
	}

	private void setPanels() {
		this.setHeaderPanel();
		this.setButtonsPanel();
		this.grid = new Grid(gf); // Class
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipadx = 400;
		gbc.ipady = 10;
		this.add(headerPanel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.ipadx = 375;
		gbc.ipady = 150;
		this.add(grid, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.ipadx = 40;
		gbc.ipady = 100;
		this.add(buttonsPanel, gbc);
		this.validate();
	}

	private void setHeaderPanel() {
		// headerPanel Setup
		this.headerPanel = new JPanel();
		this.headerPanel.setLayout(new GridLayout(0, 2));
		this.headerPanel.setBackground(Color.CYAN);

		// Difficulty Setup
		this.difficultyStr = "Difficulty: ";
		switch (difficulty) {
		case 1:
			difficultyStr = difficultyStr + "Easy";
			break;
		case 2:
			difficultyStr = difficultyStr + "Medium";
			break;
		case 3:
			difficultyStr = difficultyStr + "Hard";
			break;
		}
		difficultyLabel = new JLabel(difficultyStr);
		headerPanel.add(difficultyLabel);

		// Timer Setup
		this.timerLabel = new JLabel("00:00:00");
		this.timer = new Timer();
		JPanel gamepanel = this;
		this.task = new TimerTask() {
			public void run() {
				String seconds = "00", minutes = "00", hours = "00";
				seconds = ((time[0] < 10) ? "0" : "") + time[0];
				if (time[0] == 60) {
					time[0] = 0;
					seconds = "00";
					time[1] += 1;
				}
				minutes = ((time[1] < 10) ? "0" : "") + time[1];
				if (time[1] == 60) {
					time[1] = 0;
					minutes = "00";
					time[2] += 1;
				}
				hours = ((time[2] < 10) ? "0" : "") + time[2];
				if (time[2] == 60) {
					this.cancel();
					gamepanel.setVisible(false);
					frame.remove(gamepanel);
					JOptionPane.showMessageDialog(gamepanel,
							"You have been playing for over 60 hours. We have stopped your game.");
				}

				timeStr = hours + ":" + minutes + ":" + seconds;
				time[0] += 1;
				timerLabel.setText(timeStr);
			}
		};
		timer.schedule(task, 0, 1000);
		headerPanel.add(timerLabel);
	}

	private void setButtonsPanel() {
		// Buttons Setup
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(2, 0));
		buttonsPanel.setBackground(Color.PINK);

		// JPanel p1 for Numbers Label and Buttons
		JPanel p1 = new JPanel();
		p1.setLayout(new GridBagLayout());
		gbc.gridy = 0;
		p1.add(new JLabel("Numbers:"), gbc);

		JPanel numpad = new JPanel();
		numpad.setLayout(new GridLayout(3, 3));
		for (int i = 0; i < 9; i++) {
			num[i] = new JButton(Integer.toString(i + 1));
			num[i].addActionListener(this);
			numpad.add(num[i]);
		}
		gbc.gridy = 1;
		p1.add(numpad, gbc);
		buttonsPanel.add(p1);

		// JPanel p2 for other Game Buttons
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(6, 0));
		hint = new JButton("Hint (" + hints + ")");
		autocomplete = new JButton("Solve Board");
		check = new JButton("Check Board");
		clear = new JButton("Clear Board");
		pause = new JButton("Pause Game");
		erase = new JButton("Erase");
		autocomplete.addActionListener(this);
		erase.addActionListener(this);
		pause.addActionListener(this);
		check.addActionListener(this);
		clear.addActionListener(this);
		hint.addActionListener(this);
		p2.add(pause);
		p2.add(erase);
		p2.add(hint);
		p2.add(clear);
		p2.add(check);
		p2.add(autocomplete);
		buttonsPanel.add(p2);
	}

	private void frameActions(JPanel panel) {
		this.setVisible(false);
		frame.remove(this);
		frame.add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pause) {
			task.cancel();
			this.frameActions(new PausePanel(frame, gf, timer, task, difficulty, hints, time));
		} else if (e.getSource() == erase) {
			if (grid.getEditable() != null)
				grid.setLabelText('0');
		} else if (e.getSource() == hint) {
			if (hints > 0 && grid.getEditable().getText() == "") {
				grid.getHint();
				hint.setText("Hint (" + --hints + ")");
			}
		} else if (e.getSource() == clear) {
			grid.clearBoard();
		} else if (e.getSource() == check) {
			if (grid.puzzleIsComplete())
				this.frameActions(new EndPanel(frame, difficulty, timeStr));
		} else if (e.getSource() == autocomplete) {
			if (autocomplete.getText().equals("Solve Board")) {
				int selection = JOptionPane.showConfirmDialog(frame,
						"Autosolve will not save your game for the ranking. Are you sure you want to proceed?",
						"Are you sure?", JOptionPane.YES_NO_OPTION);
				if (selection == JOptionPane.YES_OPTION) {
					task.cancel();
					grid.autoSolve();
					autocomplete.setText("END GAME");
					timerLabel.setForeground(Color.RED);
					timerLabel.setText(timerLabel.getText() + " (STOPPED)");

					if ((new File("continuedgame.txt")).exists())
						(new File("continuedgame.txt")).delete();
					for (int i = 0; i < 9; i++)
						num[i].setEnabled(false);
					pause.setEnabled(false);
					erase.setEnabled(false);
					hint.setEnabled(false);
					clear.setEnabled(false);
					check.setEnabled(false);
				}
			} else
				this.frameActions(new MainMenu(frame));
		} else {
			for (int i = 0; i < 9; i++)
				if (num[i] == e.getSource())
					grid.setLabelText(num[i].getText().charAt(0));
		}
		frame.setFocusable(true);
		frame.requestFocusInWindow();
	}

}
