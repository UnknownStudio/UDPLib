package team.unstudio.udpl.command;

public enum CommandResult {
	UnknownCommand,
	WrongSender,
	NoPermission,
	NoEnoughParameter,
	ErrorParameter,
	Failure,
	Success;
}
