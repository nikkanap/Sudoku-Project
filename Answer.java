package sudoku;

public class Answer {
	private int ans;
	private Answer next;

	public Answer() {
		this.ans = 0;
		this.next = null;
	}

	public Answer(int ans) {
		this.ans = ans;
	}

	public Answer(int ans, Answer next) {
		this.ans = ans;
		this.next = next;
	}

	public void setAns(int ans) {
		this.ans = ans;
	}

	public void setNext(Answer next) {
		this.next = next;
	}

	public int getAns() {
		return ans;
	}

	public Answer getNext() {
		return next;
	}

}
