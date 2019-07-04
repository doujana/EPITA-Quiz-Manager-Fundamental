package fr.epita.quiz.manager.doujana.datamodel;

public class McqChoice {

	private int id;
	private String title;
	private int questionId;
	
	
	public McqChoice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public McqChoice(int id, String title, int questionId) {
		super();
		this.id = id;
		this.title = title;
		this.questionId = questionId;
	}
	
	public McqChoice(String title, int questionId) {
		super();
		this.title = title;
		this.questionId = questionId;
	}
	
	
	public McqChoice(String title) {
		super();
		this.title = title;
	}
	
	//	getters and setters
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
	
	public int getQuestionId() {
		return questionId;
	}
	
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	
}
