package fr.epita.quiz.manager.doujana.datamodel;

public class QuestionType {
	private int id;
	private String title;

	public QuestionType(int id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public QuestionType(String title) {
		this.title = title;

	}

	
	public QuestionType(int id) {
		this.id = id;
	}

	public QuestionType() {
	}
}
