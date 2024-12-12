package sudoku;

import javax.swing.JFrame;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void println(String str) {
		System.out.println(str);
	}

	public static void print(String str) {
		System.out.print(str);
	}

	public static void main(String[] args) {
		println("Starting program...");
		JFrame frame = new JFrame("Classic Sudoku");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, 750, 600);
		frame.setVisible(true);
		frame.add(new MainMenu(frame));
		frame.validate();
		// new GridFormation(25);
	}
}
