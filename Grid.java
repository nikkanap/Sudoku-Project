package sudoku;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class Grid extends JPanel implements MouseListener {
	private GridFormation gf;
	private Border borderLine;
	private JPanel square[][] = new JPanel[3][3];
	private JLabel grid[][] = new JLabel[9][9];
	private JLabel editable, previous;
	private Color background;
	private Font font;
	private boolean isComplete, isCorrect;
	private int editableRow, editableCol;

	public Grid(GridFormation gf) {
		// Class setup
		this.gf = gf;

		// AWT Contents
		this.borderLine = BorderFactory.createLineBorder(Color.BLACK);
		this.background = new Color(255, 255, 255);

		// Editable Contents
		this.editableRow = 0;
		this.editableCol = 0;

		// Grid JPanel setup
		this.setPreferredSize(new Dimension(500, 500));
		this.setLayout(new GridLayout(3, 3));
		this.setBackground(Color.black);
		this.initSquares();
		this.initInnerGrids();
	}

	// Setting up 3 by 3 JPanel squares
	private void initSquares() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				square[i][j] = new JPanel();
				square[i][j].setLayout(new GridLayout(3, 3));
				square[i][j].setBorder(borderLine);
				this.add(square[i][j]);
			}
		}
	}

	// Setting up JLabel inner grids where the numbers will be
	private void initInnerGrids() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// JLabel grid[i][j] setup
				String str = (gf.getToSolveNum(i, j) == 0) ? "" : Integer.toString(gf.getToSolveNum(i, j));
				grid[i][j] = new JLabel(str, SwingConstants.CENTER);
				grid[i][j].setForeground(
						(gf.wasInserted(i, j) || gf.getToSolveNum(i, j) == 0) ? Color.BLACK : Color.BLUE);
				grid[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
				grid[i][j].setFont(new Font("Bahnschrift", Font.BOLD, 30));
				grid[i][j].setBackground(background);
				grid[i][j].addMouseListener(this);
				// Adding grid[i][j] to its respective square
				square[this.getSqrCoord(i)][this.getSqrCoord(j)].add(grid[i][j]);
			}
		}
	}

	public JLabel getEditable() {
		return editable;
	}

	public int getEditableRow() {
		return editableRow;
	}

	public int getEditableCol() {
		return editableCol;
	}

	public void setLabelText(char c) {
		if (editable != null) {
			editable.setForeground(Color.BLACK);
			editable.setText((c != '0') ? Character.toString(c) : "");
			gf.addToGrid(Character.getNumericValue(c), editableRow, editableCol);
		}
	}

	// Gets the square coordinate based on which position a grid is in
	private int getSqrCoord(int pos) {
		int coord = 0;
		if (!(0 <= pos && 3 > pos))
			coord = (3 <= pos && 6 > pos) ? 1 : 2;
		return coord;
	}

	public void clearBoard() {
		gf.setToSolve(gf.copyLinkedList(gf.getToSolveFormat()));
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (gf.getToSolveNum(i, j) == 0) {
					grid[i][j].setText("");
				} else {
					grid[i][j].setText(Integer.toString(gf.getToSolveNum(i, j)));
					if (!gf.wasInserted(i, j))
						grid[i][j].setForeground(Color.BLUE);
				}
			}
		}
	}

	public void checkBoard(Answer[][] autoSolvedGrid, Answer[][] toSolveGrid, Answer[][] formatGrid) {
		isCorrect = true;
		isComplete = true;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (toSolveGrid[i][j].getAns() != 0) {
					if (toSolveGrid[i][j].getAns() != autoSolvedGrid[i][j].getAns()) {
						grid[i][j].setForeground(Color.RED);
						isCorrect = (isCorrect) ? false : isCorrect;
					} else if (grid[i][j].getForeground() != Color.BLUE)
						grid[i][j].setForeground(Color.GREEN);
				} else
					isComplete = (isComplete) ? false : isComplete;
			}
		}
	}

	public void getHint() {
		if (editable != null
				&& (gf.wasInserted(editableRow, editableCol) || gf.getToSolveNum(editableRow, editableCol) == 0)) {
			this.setLabelText(Integer.toString(gf.getHintValue(editableRow, editableCol)).charAt(0));
		}
	}

	public boolean puzzleIsComplete() {
		Answer[][] autoSolvedGrid = gf.getAutosolvedGrid();
		Answer[][] toSolveGrid = gf.getToSolve();
		Answer[][] formatGrid = gf.getToSolveFormat();
		this.checkBoard(autoSolvedGrid, toSolveGrid, formatGrid);

		if (isCorrect && isComplete)
			return true;
		else
			return false;
	}

	public void autoSolve() {
		gf.setToSolve(gf.copyLinkedList(gf.getAutosolvedGrid()));
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				if (gf.getToSolveFormatNum(i, j) == 0) {
					grid[i][j].setForeground(Color.GREEN);
					grid[i][j].setText(String.valueOf(gf.getToSolveNum(i, j)));
				}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (previous != null)
			previous.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		editable = (JLabel) e.getComponent();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (grid[i][j] == editable) {
					this.editableRow = i;
					this.editableCol = j;
					break;
				}
			}
		}
		editable.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		previous = editable;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
