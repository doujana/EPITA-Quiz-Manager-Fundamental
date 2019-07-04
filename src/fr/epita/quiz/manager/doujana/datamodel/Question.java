package fr.epita.quiz.manager.doujana.datamodel;

import java.util.Arrays;

public class Question {

	private int id;
	private String title;
	private int question_type_id;
	private int topic_id;
	private QuestionType questionType;
	private Topic topic;

	private Integer difficulty;

	// getters and setters
	public int getTopic_id() {
		return topic_id;
	}

	public void setTopic_id(int topic_id) {
		this.topic_id = topic_id;
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

	public int getQuestion_type_id() {
		return question_type_id;
	}

	public void setQuestion_type_id(int question_type_id) {
		this.question_type_id = question_type_id;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public Integer getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}
	
	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	//constructors
	
	public Question(String title) {

		this.title = title;
	}

	public Question(int id, String title, int qtype_id) {

		this.id = id;
		this.title = title;
		this.question_type_id = qtype_id;
	}

	public Question() {

	}

	public Question(int id, String title, int question_type_id, int topic_id, QuestionType questionType, Topic topic,
			Integer difficulty) {
		super();
		this.id = id;
		this.title = title;
		this.question_type_id = question_type_id;
		this.topic_id = topic_id;
		this.questionType = questionType;
		this.topic = topic;

		this.difficulty = difficulty;
	}

	
}
