package sudoku;

public class Rank {
	String username, timer;
	Rank next;

	public Rank(String username, String timer) {
		this.username = username;
		this.timer = timer;
		this.next = null;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setTimer(String timer) {
		this.timer = timer;
	}

	public void setNextRank(Rank next) {
		this.next = next;
	}

	public String getUsername() {
		return this.username;
	}

	public String getTimer() {
		return this.timer;
	}

	public Rank getNextRank() {
		return this.next;
	}

}
