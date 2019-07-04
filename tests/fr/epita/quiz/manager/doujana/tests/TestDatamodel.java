package fr.epita.quiz.manager.doujana.tests;

import fr.epita.quiz.manager.doujana.datamodel.Answer;
import fr.epita.quiz.manager.doujana.datamodel.Difficulty;
import fr.epita.quiz.manager.doujana.datamodel.Question;
import fr.epita.quiz.manager.doujana.datamodel.Quiz;

public class TestDatamodel {

	public static void main(String[] args) {
		Quiz quiz = new Quiz("Java Fundamentals - 2019 - Final exam");
		
		
		Answer answer = new Answer("It is a general structure that represents common characteristics of a set of individuals (or instances)"
				+ ". It is defined by 3 main descriptions : Name, State, Behavior");
		
		
		
		
		
		System.out.println(answer);
	
	}

}
