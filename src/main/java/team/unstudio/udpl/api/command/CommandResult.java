package team.unstudio.udpl.api.command;

public enum CommandResult {
	WrongSender,NoPermission,NoEnoughParameter,ErrorParameter,Failure,Success;



	public String getLangMessage(){
		return null;
	}
}
