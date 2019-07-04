package fr.epita.quiz.manager.doujana.datamodel;

public class DifficultyLevel {

	private int id;
	private String difficulty_level;
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDifficulty_level() {
		return difficulty_level;
	}
	
	public void setDifficulty_level(String difficulty_level) {
		this.difficulty_level = difficulty_level;
	}

	public DifficultyLevel() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "DifficultyLevel [id=" + id + ", difficulty_level=" + difficulty_level + "]";
	}

	public DifficultyLevel(int id) {
		super();
		this.id = id;
	}
	
	public DifficultyLevel(String difficulty_level) {
		super();
		this.difficulty_level = difficulty_level;
	}
}
