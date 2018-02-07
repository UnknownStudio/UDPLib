package team.unstudio.udpl.command.anno;

import com.google.common.collect.Maps;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import team.unstudio.udpl.command.CommandHelper;
import team.unstudio.udpl.command.anno.CommandWrapper.OptionalWrapper;
import team.unstudio.udpl.command.anno.CommandWrapper.RequiredWrapper;
import team.unstudio.udpl.i18n.I18n;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Optional;

public class AnnoCommandManager implements CommandExecutor, TabCompleter {

	private final JavaPlugin plugin;
	private final String name;

	private final CommandNode root;

	protected CommandResultHandler resultHandler;
	protected CommandParameterManager parameterManager;

	private I18n i18n;
	private String usage;
	private String description;

	/**
	 * 创建指令管理者
	 * 
	 * @param name
	 *            指令名
	 */
	public AnnoCommandManager(@Nonnull String name) {
		this(name, (JavaPlugin) Bukkit.getPluginCommand(name).getPlugin());
	}

	/**
	 * 创建指令管理者
	 * 
	 * @param name
	 *            指令名
	 * @param plugin
	 *            插件
	 */
	public AnnoCommandManager(@Nonnull String name, @Nonnull JavaPlugin plugin) {
		this(name, plugin, null);
	}

	/**
	 * 创建指令管理者
	 * 
	 * @param name
	 *            指令名
	 * @param plugin
	 *            插件
	 * @param parameterHandlers
	 *            指令参数处理器
	 */
	public AnnoCommandManager(@Nonnull String name, @Nonnull JavaPlugin plugin,
			Map<Class<?>, CommandParameterHandler> parameterHandlers) {
		Validate.notNull(name);
		Validate.notNull(plugin);
		this.name = name;
		this.plugin = plugin;
		this.root = new CommandNode(name, null);
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}

	public String getName() {
		return name;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public I18n getI18n() {
		return i18n;
	}

	public void setI18n(I18n i18n) {
		this.i18n = i18n;
	}

	/**
	 * 注册指令
	 */
	public AnnoCommandManager registerCommand() {
		PluginCommand command = plugin.getCommand(name);
		command.setExecutor(this);
		command.setTabCompleter(this);
		plugin.getLogger().info("Register command \"" + name + "\" successful.");
		return this;
	}

	/**
	 * 不安全的注册指令
	 */
	public AnnoCommandManager unsafeRegisterCommand() {
		Optional<PluginCommand> command = CommandHelper.unsafeRegisterCommand(name, plugin);
		if (command.isPresent()) {
			command.get().setExecutor(this);
			command.get().setTabCompleter(this);
			plugin.getLogger().info("Unsafe register command \"" + name + "\" successful.");
		} else {
			plugin.getLogger().warning("Unsafe register command \"" + name + "\" failure.");
		}
		return this;
	}

	/**
	 * 添加指令
	 */
	public AnnoCommandManager addHandler(Object object) {
		for (Method method : object.getClass().getMethods()) {
			team.unstudio.udpl.command.anno.Command command = method
					.getAnnotation(team.unstudio.udpl.command.anno.Command.class);

			if (command != null) {
				addCommand(createOrGetCommandNode(command.value()), object, method, command);

				for (Alias alias : method.getAnnotationsByType(Alias.class))
					addCommand(createOrGetCommandNode(alias.value()), object, method, command);

				continue;
			}

			TabComplete tabComplete = method.getAnnotation(TabComplete.class);

			if (tabComplete != null) {

				addTabComplete(createOrGetCommandNode(tabComplete.value()), object, method, tabComplete);

				for (Alias alias : method.getAnnotationsByType(Alias.class))
					addTabComplete(createOrGetCommandNode(alias.value()), object, method, tabComplete);
			}
		}
		return this;
	}

	protected void addCommand(CommandNode node, Object object, Method method,
			team.unstudio.udpl.command.anno.Command command) {
		node.getCommands().add(new CommandWrapper(node, this, object, method, command));
	}

	protected void addTabComplete(CommandNode node, Object object, Method method, TabComplete tabComplete) {
		node.getTabCompleters().add(new TabCompleteWrapper(node, this, object, method, tabComplete));
	}

	public AnnoCommandManager addHandler(Object... objects) {
		for (Object object : objects)
			addHandler(object);
		return this;
	}

	public Optional<CommandNode> getCommandNode(String[] args) {
		CommandNode node = root;
		for (String arg : args) {
			CommandNode child = node.getChild(arg.toLowerCase());
			if (child == null)
				return Optional.empty();

			node = child;
		}

		return Optional.of(node);
	}

	protected CommandNode createOrGetCommandNode(String[] args) {
		CommandNode node = root;
		for (String arg : args) {
			CommandNode child = node.getChild(arg);
			if (child == null) {
				child = new CommandNode(arg, node);
				node.addChild(child);
			}

			node = child;
		}

		return node;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandNode node = root;

		int i, size;
		for (i = 0, size = args.length; i < size; i++) {
			CommandNode child = node.getChild(args[i]);
			if (child == null)
				break;

			node = child;
		}

		handleCommand(node, sender, Arrays.copyOfRange(args, i, args.length));
		return true;
	}

	protected void handleCommand(CommandNode node, CommandSender sender, String args[]){
		CommandWrapper command = getCommand(node, sender);
		if(command == null) {
			resultHandler.onWrongSender(node, sender, args);
			return;
		}
		
		if(!command.checkPermission(sender)) {
			resultHandler.onNoPermission(command, sender, args);
			return;
		}
		
		RequiredWrapper[] requireds = command.getRequireds();
		OptionalWrapper[] optionals = command.getOptionals();
		int argsLength = requireds.length + optionals.length;
		
		if (requireds.length > args.length) {
			resultHandler.onNoEnoughParameter(command, sender, args);
			return;
		}
		
		if (command.isExactParameterMatching() && args.length > argsLength) {
			resultHandler.onUnknownCommand(sender, args);
			return;
		}
		
		Object[] transformedArgs = new Object[command.hasStringArray() ? argsLength + 1 : argsLength];
		Set<Integer> wrongArgsIndex = new HashSet<>();
		
		for (int i = 0, size = requireds.length; i < size; i++) {
			try {
				transformedArgs[i] = parameterManager.transform(requireds[i].getType(), args[i]);
			} catch (Exception e) {
				wrongArgsIndex.add(i);
			}
		}
		
		for (int i = 0, j = requireds.length, size = optionals.length; i < size; i++, j++) {
			OptionalWrapper optional = optionals[i];
			try {
				transformedArgs[j] = parameterManager.transform(optional.getType(),
						j < args.length ? args[j] : optional.getDefaultValue());
			} catch (Exception e) {
				wrongArgsIndex.add(j);
			}
		}
		
		if(wrongArgsIndex.size() > 0) {
			resultHandler.onWrongParameter(command, sender, args, wrongArgsIndex);
			return;
		}
		
		if(command.hasStringArray()) {
			transformedArgs[argsLength] = argsLength < args.length ? Arrays.copyOfRange(args, argsLength, args.length) : new String[0];
		}
		
		try {
			command.invoke(sender, transformedArgs);
		} catch (Exception e) {
			resultHandler.onRunCommandFailure(command, sender, args, e);
		}
	}

	private CommandWrapper getCommand(CommandNode node, CommandSender sender) {
		for (CommandWrapper command : node.getCommands())
			if (command.checkSender(sender))
				return command;

		return null;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		CommandNode node = root;

		int i, size;
		for (i = 0, size = args.length; i < size; i++) {
			CommandNode child = node.getChild(args[i]);
			if (child == null)
				break;

			node = child;
		}

		return handleTabComplete(node, sender, Arrays.copyOfRange(args, i, args.length));
	}

	protected List<String> handleTabComplete(CommandNode node, CommandSender sender, String args[]) {
		TabCompleteWrapper tabComplete = getTabComplete(node, sender);
		
		if(tabComplete == null)
			return Collections.emptyList();
		
		if(!tabComplete.checkPermission(sender))
			return Collections.emptyList();
		
		return tabComplete.invoke(sender, args);
	}

	private TabCompleteWrapper getTabComplete(CommandNode node, CommandSender sender) {
		for (TabCompleteWrapper tabComplete : node.getTabCompleters())
			if (tabComplete.checkSender(sender))
				return tabComplete;

		return null;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String name;
		private JavaPlugin plugin;
		private Map<Class<?>, CommandParameterHandler> parameterHandlers = Maps.newHashMap();
		private I18n i18n;
		private String usage;
		private String description;

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder plugin(JavaPlugin plugin) {
			this.plugin = plugin;
			return this;
		}

		public Builder usage(String usage) {
			this.usage = usage;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder i18n(I18n i18n) {
			this.i18n = i18n;
			return this;
		}

		public Builder parameterHandler(Class<?> clazz, CommandParameterHandler handler) {
			parameterHandlers.put(clazz, handler);
			return this;
		}

		public AnnoCommandManager build() {
			AnnoCommandManager manager = new AnnoCommandManager(name, plugin, parameterHandlers);
			manager.setI18n(i18n);
			manager.setUsage(usage);
			manager.setDescription(description);
			return manager;
		}
	}
}
