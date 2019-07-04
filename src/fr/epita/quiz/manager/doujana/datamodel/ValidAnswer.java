package fr.epita.quiz.manager.doujana.datamodel;

public class ValidAnswer {
private int id;
private String name;
private Quiz quiz;
private Question question;
private Student student;
public ValidAnswer(int id, String name) {
	super();
	this.id = id;
	this.name = name;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public ValidAnswer()
{
	
}
public ValidAnswer(String name)
{
	this.name = name;
}

}
