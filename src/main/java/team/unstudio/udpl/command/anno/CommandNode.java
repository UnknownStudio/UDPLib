package team.unstudio.udpl.command.anno;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.*;

public class CommandNode {

	private final String name;
	private final String fullPath;
	private final CommandNode parent;
	
	private final Map<String,CommandNode> children = new HashMap<>();
	
	private final List<CommandWrapper> commands = new LinkedList<>();
	private final List<TabCompleteWrapper> tabCompleters = new LinkedList<>();
	
	public CommandNode(String name, CommandNode parent) {
		this.name = requireNonNull(name).toLowerCase();
		this.parent = parent;
		this.fullPath = parent == null ? name : parent.getFullPath() + " " + name;
	}

	public String getName() {
		return name;
	}
	
	public String getFullPath() {
		return fullPath;
	}

	public CommandNode getParent() {
		return parent;
	}

	public CommandNode getChild(String name) {
		return children.get(name.toLowerCase());
	}
	
	public void addChild(CommandNode node) {
		children.put(node.getName(), node);
	}

	public List<CommandWrapper> getCommands() {
		return commands;
	}

	public List<TabCompleteWrapper> getTabCompleters() {
		return tabCompleters;
	}
}
