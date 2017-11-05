package team.unstudio.udpl.nms;

public class NmsException extends RuntimeException{
	
	public NmsException() {}

	public NmsException(String message) {
		super(message);
	}
	
	public NmsException(Throwable throwable){
		super(throwable);
	}
	
	public NmsException(String message, Throwable throwable){
		super(message, throwable);
	}
}
