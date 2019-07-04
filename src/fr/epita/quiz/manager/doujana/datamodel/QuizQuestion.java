package fr.epita.quiz.manager.doujana.datamodel;

public class QuizQuestion {
 private int id;
 private int question_id;
 private int quiz_id;
 private Question question;
 private Quiz quiz;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getQuestion_id() {
	return question_id;
}
public void setQuestion_id(int question_id) {
	this.question_id = question_id;
}
public int getQuiz_id() {
	return quiz_id;
}
public void setQuiz_id(int quiz_id) {
	this.quiz_id = quiz_id;
}
public QuizQuestion(int id, int question_id, int quiz_id, Question question, Quiz quiz) {
	super();
	this.id = id;
	this.question_id = question_id;
	this.quiz_id = quiz_id;
	this.question = question;
	this.quiz = quiz;
}
 public QuizQuestion(int id, int question_id, int quiz_id)
 {
	 this.id=id;
	 this.question_id=question_id;
	 this.quiz_id=quiz_id;
	 
 }
 public QuizQuestion()
 {}
 public QuizQuestion(int id,int question_id)
 {
	 this.id=id;
	 this.question_id=question_id;
 }
 public QuizQuestion(int id)
 {
	 this.id=id;
	 
 }
 
}
