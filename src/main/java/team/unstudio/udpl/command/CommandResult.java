package team.unstudio.udpl.command;

public enum CommandResult {
	WrongSender,NoPermission,NoEnoughParameter,ErrorParameter,Failure,Success;



	public String getLangMessage(){
		return null;
	}
}
