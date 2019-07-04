package fr.epita.quiz.manager.doujana.datamodel;

public class Teacher {

	private int id;
	private String first_name;
	private String last_name;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getSecond_name() {
		return last_name;
	}
	public void setSecond_name(String second_name) {
		this.last_name = second_name;
	}
	public Teacher(int id, String first_name, String second_name) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = second_name;
	}
	public Teacher(int id) {
		super();
		this.id = id;
	}
	public Teacher(String first_name, String second_name) {
		super();
		this.first_name = first_name;
		this.last_name = second_name;
	}
	public Teacher()
	{}
	@Override
	public String toString() {
		return "Teacher [id=" + id + ", first_name=" + first_name + ", second_name=" + last_name + "]";
	}
	
	
}
