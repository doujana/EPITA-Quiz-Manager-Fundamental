package fr.epita.quiz.manager.doujana.exception;

public class CreateFailedException extends DataAccessException{



	public CreateFailedException(Object beanThatWasNotCreated) {
		super(beanThatWasNotCreated);
	}
	
	public CreateFailedException(Object beanThatWasNotCreated, Exception initialCause) {
		super(beanThatWasNotCreated, initialCause);
	}

}
