package sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GridFormation {
	private ArrayList<Integer> numbers = new ArrayList<Integer>(10);
	private Answer toSolveFormat[][] = new Answer[9][9];
	private Answer toSolve[][] = new Answer[9][9];
	private int complete[][] = new int[9][9];

	// Class constructor
	public GridFormation(int rmGrids) {
		this.initNumbers();
		this.initGrid();

		this.setToSolveFormat(this.setArrayToLinkedList(complete));
		this.removeGrid(rmGrids);
		this.setToSolve(this.copyLinkedList(toSolveFormat));
	}

	public GridFormation(int[][] toSolveFormatArr, int[][] toSolveArr) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				toSolveFormat[i][j] = new Answer(toSolveFormatArr[i][j]);
				toSolve[i][j] = new Answer(toSolveArr[i][j]);
			}
		}
	}

	private void initGrid() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int row = 0; row < 9; row++) { // row loop
			for (int col = 0, ctr = 1; col < 9;) { // col loop
				list = (list.isEmpty()) ? this.copyNumbers() : list;

				if (this.isValidToAddToGrid(this.getComplete(), row, col, list.get(0))) {
					this.setCompleteNum(row, col++, list.get(0));
					list.remove(0);
					ctr = 1;
				} else {
					if (ctr < list.size()) {
						list.add(list.get(0));
						list.remove(0);
					} else { // ctr == list size
						boolean gridReplaced = false;
						// loop to iterate across the row
						int k = 0, n = 0;

						while (k < col && n < list.size() - 1) {
							if (this.isValidToAddToGrid(this.getComplete(), row, k, list.get(n))) {
								// replace complete[row][k] with list.get(n) and set list at (n) to temp
								int temp = this.getCompleteNum(row, k);
								this.setCompleteNum(row, k, list.get(n));
								list.set(n, temp);

								if (this.isValidToAddToGrid(this.getComplete(), row, col, list.get(n))) {
									this.setCompleteNum(row, col++, list.get(n));
									gridReplaced = true;
									list.remove(n);
									break;
								}
							}
							n = (k + 1 == col) ? n + 1 : n;
							k = (k + 1 == col) ? 0 : k + 1;
						} // end of loop

						if (!gridReplaced) {
							this.clearRow(row--);
							this.clearRow(row);
							list.removeAll(list);
							col = 0;
						}
					}
					ctr = (ctr < list.size()) ? ctr + 1 : 1;
				}
			}
		}
	}

	// Clears the row
	private void clearRow(int row) {
		for (int col = 0; col < 9; col++)
			this.setCompleteNum(row, col, 0);
	}

	// Methods for numbers variable
	private void initNumbers() {
		numbers = new ArrayList<Integer>(10);
		for (int i = 1; i <= 9; i++)
			numbers.add(i);
	}

	// Getter and Setter methods
	public int getToSolveNum(int i, int j) {
		return toSolve[i][j].getAns();
	}

	public void setToSolveNum(int i, int j, int num) {
		toSolve[i][j].setAns(num);
	}

	public Answer[][] getToSolve() {
		return toSolve;
	}

	public void setToSolve(Answer[][] temp) {
		toSolve = this.copyLinkedList(temp);
	}

	public int getToSolveFormatNum(int i, int j) {
		return toSolveFormat[i][j].getAns();
	}

	public void setToSolveFormatNum(int i, int j, int num) {
		toSolveFormat[i][j].setAns(num);
	}

	public Answer[][] getToSolveFormat() {
		return toSolveFormat;
	}

	public void setToSolveFormat(Answer[][] temp) {
		toSolveFormat = this.copyLinkedList(temp);
	}

	public Answer[][] setArrayToLinkedList(int[][] temp) {
		Answer linkedList[][] = new Answer[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				linkedList[i][j] = new Answer(temp[i][j]);
			}
		}
		return linkedList;
	}

	public int[][] setLinkedListToArray(Answer[][] temp) {
		int array[][] = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				array[i][j] = temp[i][j].getAns();
			}
		}
		return array;
	}

	public int getCompleteNum(int i, int j) {
		return complete[i][j];
	}

	public void setCompleteNum(int i, int j, int num) {
		complete[i][j] = num;
	}

	public int[][] getComplete() {
		return complete;
	}

	public void setComplete(Answer[][] temp) {
		complete = this.setLinkedListToArray(this.copyLinkedList(temp));
	}

	public ArrayList<Integer> getNumbers() {
		return numbers;
	}

	private ArrayList<Integer> copyNumbers() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++)
			list.add(numbers.get(i));
		Collections.shuffle(list);
		return list;
	}

	// Boolean methods
	// Checks whether a value numToCompr is not present in the grid's (temp) row
	private boolean isNotInRow(int[][] temp, int row, int numToCompr) {
		for (int col = 0; col < 9; col++)
			if (numToCompr == temp[row][col])
				return false;
		return true;
	}

	// Checks whether a value numToCompr is not present in the grid's square
	private boolean isNotInCol(int[][] temp, int col, int numToCompr) {
		for (int row = 0; row < 9; row++)
			if (numToCompr == temp[row][col])
				return false;
		return true;
	}

	// Checks whether a value numToCompr is not present in the grid's column
	private boolean isNotInSquare(int[][] temp, int row, int col, int numToCompr) {
		int i = 0, j = 0;
		if (!(0 <= row && 3 > row))
			i = (3 <= row && 6 > row) ? 3 : 6;
		if (!(0 <= col && 3 > col))
			j = (3 <= col && 6 > col) ? 3 : 6;
		for (int k = i; k < i + 3; k++)
			for (int l = j; l < j + 3; l++)
				if (numToCompr == temp[k][l])
					return false;
		return true;
	}

	private boolean isValidToAddToGrid(int temp[][], int row, int col, int numToCompr) {
		if (this.isNotInCol(temp, col, numToCompr) && this.isNotInRow(temp, row, numToCompr)
				&& this.isNotInSquare(temp, row, col, numToCompr))
			return true;
		return false;
	}

	// Checks whether a value from the grid was inserted by the user or part of the
	// original grid
	public boolean wasInserted(int i, int j) {
		if (toSolve[i][j].getAns() != toSolveFormat[i][j].getAns())
			return true;
		return false;
	}

	// In-Game Action Methods
	public void removeGrid(int ctr) {
		int col = new Random().nextInt(9);
		int row = new Random().nextInt(9);
		if (toSolveFormat[row][col].getAns() != 0) {
			Answer temp[][] = this.setZero(copyLinkedList(toSolveFormat), row, col);
			toSolveFormat = (this.isValidToRemove(complete, temp, row, col)) ? this.setZero(toSolveFormat, row, col)
					: toSolveFormat;
			ctr -= (this.isValidToRemove(complete, temp, row, col)) ? 1 : 0;
		}
		if (ctr > 0)
			this.removeGrid(ctr);
	}

	public int[][] setArrayGrid(int[][] temp, int row, int col, int numToAdd) {
		temp[row][col] = numToAdd;
		return temp;
	}

	private Answer[][] setZero(Answer[][] temp, int row, int col) {
		temp[row][col].setAns(0);
		return temp;
	}

	private boolean isValidToRemove(int[][] tempComplete, Answer[][] temp, int row, int col) {
		for (int i = 0; i < 9; i++) {
			if (i == row)
				continue;
			for (int j = 0; j < 9; j++) {
				if (j == col)
					continue;
				if (tempComplete[row][col] == tempComplete[i][j] && tempComplete[row][j] == tempComplete[i][col])
					if (temp[i][j].getAns() == 0 && temp[row][j].getAns() == 0 && temp[i][col].getAns() == 0)
						return false;
			}
		}
		return true;
	}

	private Answer[][] solveGrid(Answer[][] temp) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (temp[row][col].getAns() == 0) {
					Answer ptr = temp[row][col]; // pointer
					for (int ans = 1; ans <= 9; ans++) {
						if (this.isValidToAddToGrid(this.setLinkedListToArray(temp), row, col, ans)) {
							if (ptr.getAns() != 0) {
								ptr.setNext(new Answer());
								ptr = ptr.getNext();
							}
							ptr.setAns(ans);
						} else {
							if (ans == 9 && ptr.getAns() == 0) {
								int reverseRow = (col == 0) ? row - 1 : row;
								int reverseCol = (col == 0) ? 8 : col - 1;
								while (reverseRow >= 0) {
									while (reverseCol >= 0) {
										if (this.getToSolveFormatNum(reverseRow, reverseCol) == 0) {
											row = reverseRow;
											col = reverseCol;
											if (temp[row][col].getNext() == null)
												temp[row][col].setAns(0);
											else {
												temp[row][col] = temp[row][col].getNext();
												reverseRow = -1;
												reverseCol = -1;
												continue;
											}
										}
										reverseCol--;
									}
									if (reverseCol == -1 && reverseRow > 0)
										reverseCol = 8;
									reverseRow--;
								}
							}
						}
					}

				}
			}
		}

		return temp;
	}

	public void addToGrid(int value, int editableRow, int editableCol) {
		toSolve[editableRow][editableCol].setAns(value);
	}

	public Answer[][] copyLinkedList(Answer[][] toCopy) {
		Answer[][] copies = new Answer[9][9];
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				copies[i][j] = new Answer(toCopy[i][j].getAns());
		return copies;
	}

	public int getHintValue(int row, int col) {
		Answer temp[][] = this.solveGrid(this.copyLinkedList(toSolveFormat));
		return temp[row][col].getAns();
	}

	public Answer[][] getAutosolvedGrid() {
		return this.solveGrid(this.copyLinkedList(toSolveFormat));
	}

}
