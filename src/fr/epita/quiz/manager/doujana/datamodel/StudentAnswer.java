package fr.epita.quiz.manager.doujana.datamodel;

public class StudentAnswer {

	private int id;
	private int studentId;
	private int validAnswerId;
	private int mcqAnswerId;
	public StudentAnswer(int id, int studentId, int validAnswerId, int mcqAnswerId) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.validAnswerId = validAnswerId;
		this.mcqAnswerId = mcqAnswerId;
	}
	
	public StudentAnswer(int id) {
		super();
		this.id = id;
	}
	
	public StudentAnswer(String id) {
		super();
		this.id = Integer.parseInt(id);
	}
	
	public StudentAnswer(int id, int studentId) {
		super();
		this.id = id;
		this.studentId = studentId;
	}
	
	public StudentAnswer(int student_id, int mcq_answer_id, int valid_answer_id){
		this.studentId = student_id;
		this.mcqAnswerId = mcq_answer_id;
		this.validAnswerId = valid_answer_id;
	}
	
	public StudentAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getValidAnswerId() {
		return validAnswerId;
	}

	public void setValidAnswerId(int validAnswerId) {
		this.validAnswerId = validAnswerId;
	}

	public int getMcqAnswerId() {
		return mcqAnswerId;
	}

	public void setMcqAnswerId(int mcqAnswerId) {
		this.mcqAnswerId = mcqAnswerId;
	}

	@Override
	public String toString() {
		return "StudentAnswer [id=" + id + ", studentId=" + studentId + ", validAnswerId=" + validAnswerId
				+ ", mcqAnswerId=" + mcqAnswerId + "]";
	}
	
	
	
}
