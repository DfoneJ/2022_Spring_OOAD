package componment;

public class HomeworkDescription {
	private int similarity;
	private String title;
	private String content;
	
	public HomeworkDescription(int similarity, String title, String content) {
		setSimilarity(similarity);
		this.title = title;
		this.content = content;
	}
	
	public void setSimilarity(int similarity) {
		if (similarity < 0)
			similarity = 0;
		else if (similarity > 100)
			similarity = 100;
		this.similarity = similarity;
	}
	
	public int getSimilarity() {
		return this.similarity;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return this.content;
	}
}
