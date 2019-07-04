package fr.epita.quiz.manager.doujana.exception;

public class DeleteFailedException extends Exception{

	public Object getFaultInstance() {
		return faultInstance;
	}

	private Object faultInstance;
	
	
	public DeleteFailedException(Object faultInstance) {
		this.faultInstance = faultInstance;
	}
	
	public DeleteFailedException(Object faultInstance, Exception initialCause) {
		this.faultInstance = faultInstance;
		this.initCause(initialCause);
	}


}
