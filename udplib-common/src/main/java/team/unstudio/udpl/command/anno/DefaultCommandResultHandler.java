package team.unstudio.udpl.command.anno;

import java.util.Set;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import team.unstudio.udpl.UDPLib;
import team.unstudio.udpl.command.anno.CommandWrapper.OptionalWrapper;
import team.unstudio.udpl.command.anno.CommandWrapper.RequiredWrapper;
import team.unstudio.udpl.i18n.I18n;

public class DefaultCommandResultHandler implements CommandResultHandler {

	private static final String MESSAGE_UNKNOWN_COMMAND = UDPLib.getI18n().localize("message.unknown_command");
	private static final String MESSAGE_NO_PERMISSION = UDPLib.getI18n().localize("message.no_permission");
	private static final String MESSAGE_NO_ENOUGH_PARAMETER = UDPLib.getI18n().localize("message.no_enough_parameter");
	private static final String MESSAGE_WRONG_SENDER = UDPLib.getI18n().localize("message.wrong_sender");
	private static final String MESSAGE_WRONG_PARAMETER = UDPLib.getI18n().localize("message.wrong_parameter");
	private static final String MESSAGE_FAILURE = UDPLib.getI18n().localize("message.failure");

	protected AnnoCommandManager commandManager;

	@Override
	public void setCommandManager(AnnoCommandManager commandManager) {
		this.commandManager = commandManager;
	}

	@Override
	public void onUnknownCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player)
			sender.sendMessage(UDPLib.getI18n().format((Player) sender, "message.unknown_command", commandManager.getName()));
		else
			sender.sendMessage(String.format(MESSAGE_UNKNOWN_COMMAND, commandManager.getName()));
	}

	@Override
	public void onWrongSender(CommandNode node, CommandSender sender, String[] args) {
		if (sender instanceof Player)
			sender.sendMessage(UDPLib.getI18n().localize((Player) sender, "message.wrong_sender"));
		else
			sender.sendMessage(MESSAGE_WRONG_SENDER);
	}

	@Override
	public void onNoPermission(CommandWrapper command, CommandSender sender, String[] args) {
		if (sender instanceof Player)
			sender.sendMessage(
					String.format(UDPLib.getI18n().format((Player) sender, "message.no_permission"), command.getPermission()));
		else
			sender.sendMessage(String.format(MESSAGE_NO_PERMISSION, command.getPermission()));
	}

	@Override
	public void onNoEnoughParameter(CommandWrapper command, CommandSender sender, String[] args) {
		StringBuilder builder = new StringBuilder(ChatColor.WHITE.toString()).append("/")
				.append(command.getNode().getFullPath());

		{
			RequiredWrapper[] requireds = command.getRequireds();
			for (int i = 0, size = args.length; i < size; i++) {
				builder.append(" <").append(localize(sender, requireds[i].getName())).append(">");
			}
			builder.append(ChatColor.RED);
			for (int i = args.length, size = requireds.length; i < size; i++) {
				builder.append(" <").append(localize(sender, requireds[i].getName())).append(">");
			}
		}

		{
			builder.append(ChatColor.WHITE);
			OptionalWrapper[] optionals = command.getOptionals();
			for (OptionalWrapper optional : optionals) {
				builder.append(" [").append(localize(sender, optional.getName())).append("]");
			}
		}

		if (sender instanceof Player)
			sender.sendMessage(UDPLib.getI18n().format((Player) sender, "message.no_enough_parameter", builder.toString()));
		else
			sender.sendMessage(String.format(MESSAGE_NO_ENOUGH_PARAMETER, builder.toString()));
	}

	@Override
	public void onWrongParameter(CommandWrapper command, CommandSender sender, String[] args,
			Set<Integer> wrongParameterIndexs) {
		StringBuilder builder = new StringBuilder(ChatColor.WHITE.toString()).append("/")
				.append(command.getNode().getFullPath());

		RequiredWrapper[] requireds = command.getRequireds();
		for (int i = 0, size = requireds.length; i < size; i++) {
			builder.append(wrongParameterIndexs.contains(i) ? ChatColor.RED : ChatColor.WHITE).append(" <")
					.append(localize(sender, requireds[i].getName())).append(">");
		}

		OptionalWrapper[] optionals = command.getOptionals();
		for (int i = 0, size = optionals.length, requiredLength = requireds.length; i < size; i++) {
			builder.append(wrongParameterIndexs.contains(requiredLength + i) ? ChatColor.RED : ChatColor.WHITE)
					.append(" [").append(localize(sender, optionals[i].getName())).append("]");
		}

		if (sender instanceof Player)
			sender.sendMessage(UDPLib.getI18n().format((Player) sender, "message.wrong_parameter", builder.toString()));
		else
			sender.sendMessage(String.format(MESSAGE_WRONG_PARAMETER, builder.toString()));
	}

	@Override
	public void onRunCommandFailure(CommandWrapper command, CommandSender sender, String[] args, Throwable t) {
		if (sender instanceof Player)
			sender.sendMessage(UDPLib.getI18n().format((Player) sender, "message.failure"));
		else
			sender.sendMessage(MESSAGE_FAILURE);
		
		commandManager.getPlugin().getLogger().log(Level.WARNING, t.getMessage(), t);
	}

	private String localize(CommandSender sender, String key) {
		I18n i18n = commandManager.getI18n();
		if (i18n == null)
			return key;
		else if (sender instanceof Player)
			return i18n.localize((Player) sender, key);
		else
			return i18n.localize(key);
	}
}
