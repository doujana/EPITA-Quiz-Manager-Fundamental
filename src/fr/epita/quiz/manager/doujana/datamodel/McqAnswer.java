package fr.epita.quiz.manager.doujana.datamodel;

public class McqAnswer {
	private int id;
	private String title;
	private int mcqchoice_id;
	private int student_id;
	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	private Student student;
	private Quiz quiz;
	private McqChoice mcqchoise;
	
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
	public int getMcqchoice_id() {
		return mcqchoice_id;
	}
	public void setMcqchoice_id(int mcqchoice_id) {
		this.mcqchoice_id = mcqchoice_id;
	}
	public McqAnswer(int id, String title, int mcqchoice_id) {
		super();
		this.id = id;
		this.title = title;
		this.mcqchoice_id = mcqchoice_id;
	}
	public McqAnswer(String title) {
	
		this.title = title;
		
	}
	
	public McqAnswer(String title, int mcqchoice_id) {
		this.title = title;
		this.mcqchoice_id = mcqchoice_id;
	}
	
	public McqAnswer(int id,String title) {
		this.id=id;
		this.title=title;
	}
	public McqAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
