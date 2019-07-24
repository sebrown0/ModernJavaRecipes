package helpers;

public class Golfer {
	private String first;    
	private String last;    
	private int score;
	
	public Golfer(String first, String last, int score) {
		super();
		this.first = first;
		this.last = last;
		this.score = score;
	}
	
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Golfer [first=" + first + ", last=" + last + ", score=" + score + "]";
	}
}
