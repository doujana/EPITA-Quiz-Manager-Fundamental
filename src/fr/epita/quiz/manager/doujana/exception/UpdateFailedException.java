package fr.epita.quiz.manager.doujana.exception;

import java.sql.SQLException;

public class UpdateFailedException extends Exception{
	public Object getFaultInstance() {
		return faultInstance;
	}

	private Object faultInstance;
	
	
	public UpdateFailedException(Object faultInstance) {
		this.faultInstance = faultInstance;
	}
	
	public UpdateFailedException(Object faultInstance, Exception initialCause) {
		this.faultInstance = faultInstance;
		this.initCause(initialCause);
	}


}
