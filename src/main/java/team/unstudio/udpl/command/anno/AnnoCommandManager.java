package team.unstudio.udpl.command.anno;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import team.unstudio.udpl.command.CommandHelper;
import team.unstudio.udpl.core.UDPLI18n;
import team.unstudio.udpl.i18n.I18n;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Optional;

public class AnnoCommandManager implements CommandExecutor,TabCompleter{
	
	private static final String MESSAGE_UNKNOWN_COMMAND = UDPLI18n.format("message.unknown_command");
	private static final String MESSAGE_NO_PERMISSION = UDPLI18n.format("message.no_permission");
	private static final String MESSAGE_NO_ENOUGH_PARAMETER = UDPLI18n.format("message.no_enough_parameter");
	private static final String MESSAGE_WRONG_SENDER = UDPLI18n.format("message.wrong_sender");
	private static final String MESSAGE_WRONG_PARAMETER = UDPLI18n.format("message.wrong_parameter");
	private static final String MESSAGE_FAILURE = UDPLI18n.format("message.failure");
	
	private final JavaPlugin plugin;
	private final String name;
	private final CommandWrapper defaultHandler;
	
	protected final Map<Class<?>,CommandParameterHandler> parameterHandlers;
	
	private I18n i18n;
	private String usage;
	private String description;
	
	/**
	 * 创建指令管理者
	 * @param name 指令名
	 */
	public AnnoCommandManager(@Nonnull String name){
		this(name,(JavaPlugin) Bukkit.getPluginCommand(name).getPlugin());
	}

	/**
	 * 创建指令管理者
	 * @param name 指令名
	 * @param plugin 插件
	 */
	public AnnoCommandManager(@Nonnull String name, @Nonnull JavaPlugin plugin){
		this(name,plugin,null);
	}
	
	/**
	 * 创建指令管理者
	 * @param name 指令名
	 * @param plugin 插件
	 * @param parameterHandlers 指令参数处理器
	 */
	public AnnoCommandManager(@Nonnull String name, @Nonnull JavaPlugin plugin, Map<Class<?>,CommandParameterHandler> parameterHandlers){
		Validate.notNull(name);
		Validate.notNull(plugin);
		this.name = name;
		this.plugin = plugin;
		this.defaultHandler = new CommandWrapper(name,this,null);
		this.parameterHandlers = initParameter(parameterHandlers);
	}
	
	protected Map<Class<?>,CommandParameterHandler> initParameter(Map<Class<?>,CommandParameterHandler> parameterHandlers){
		Map<Class<?>,CommandParameterHandler> map = Maps.newHashMap();
		
		map.put(boolean.class, new CommandParameterHandler.BooleanHandler());
		map.put(Player.class, new CommandParameterHandler.PlayerHandler());
		map.put(OfflinePlayer.class, new CommandParameterHandler.OfflinePlayerHandler());
		
		if(parameterHandlers != null)
			map.putAll(parameterHandlers);
		
		return ImmutableMap.copyOf(map);
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
	public AnnoCommandManager registerCommand(){
		PluginCommand command = plugin.getCommand(name);
		command.setExecutor(this);
		command.setTabCompleter(this);
		plugin.getLogger().info("Register command \""+name+"\" successful.");
		return this;
	}
	
	/**
	 * 不安全的注册指令
	 */
	public AnnoCommandManager unsafeRegisterCommand(){
		Optional<PluginCommand> command = CommandHelper.unsafeRegisterCommand(name, plugin);
		if(command.isPresent()){
			command.get().setExecutor(this);
			command.get().setTabCompleter(this);
			plugin.getLogger().info("Unsafe register command \""+name+"\" successful.");
		}else{
			plugin.getLogger().warning("Unsafe register command \""+name+"\" failure.");
		}
		return this;
	}
	
	/**
	 * 添加指令
	 */
	public AnnoCommandManager addHandler(Object object) {
		for (Method method : object.getClass().getDeclaredMethods()) {
			team.unstudio.udpl.command.anno.Command annoCommand = method
					.getAnnotation(team.unstudio.udpl.command.anno.Command.class);

			if (annoCommand != null) {
				createCommandWrapper(annoCommand.value()).setCommandMethod(object, annoCommand, method);

				for (Alias annoAlias : method.getAnnotationsByType(Alias.class))
					createCommandWrapper(annoAlias.value()).setCommandMethod(object, annoCommand, method);

				continue;
			}
			
			TabComplete annoTabComplete = method.getAnnotation(TabComplete.class);
			
			if(annoTabComplete != null){
				getCommandWrapper(annoTabComplete.value()).ifPresent(wrapper->wrapper.setTabCompleteMethod(object, annoTabComplete, method));
			}
		}
		return this;
	}
	
	public AnnoCommandManager addHandler(Object... object){
		Arrays.stream(object).forEach(this::addHandler);
		return this;
	}

	protected void onUnknownCommand(CommandSender sender, Command command, String label, String[] args, CommandWrapper handler) {
		if (sender instanceof Player)
			sender.sendMessage(UDPLI18n.format((Player) sender, "message.unknown_command"));
		else
			sender.sendMessage(String.format(MESSAGE_UNKNOWN_COMMAND, label));
	}

	protected void onNoPermission(CommandSender sender, Command command, String label, String[] args, CommandWrapper handler) {
		if (sender instanceof Player)
			sender.sendMessage(
					String.format(UDPLI18n.format((Player) sender, "message.no_permission"), handler.getPermission()));
		else
			sender.sendMessage(String.format(MESSAGE_NO_PERMISSION, handler.getPermission()));
	}
	
	protected void onNoEnoughParameter(CommandSender sender, Command command, String label, String[] args, CommandWrapper handler){
		StringBuilder builder = new StringBuilder(ChatColor.WHITE.toString()).append("/").append(handler.getFullCommand());
		
		{
			String[] requiredNames = handler.getRequiredNames();
			for (int i = 0, size = args.length; i < size; i++) {
				builder.append(" <").append(localize(sender,requiredNames[i])).append(">");
			}
			builder.append(ChatColor.RED);
			for (int i = args.length, size = requiredNames.length; i < size; i++) {
				builder.append(" <").append(localize(sender,requiredNames[i])).append(">");
			}
		}
		
		{
			builder.append(ChatColor.WHITE);
			String[] optionalNames = handler.getOptionalNames();
            for (String optionalName : optionalNames) {
                builder.append(" [").append(localize(sender, optionalName)).append("]");
            }
		}
		
		if (sender instanceof Player)
			sender.sendMessage(
					String.format(UDPLI18n.format((Player) sender, "message.no_enough_parameter"), builder.toString()));
		else
			sender.sendMessage(String.format(MESSAGE_NO_ENOUGH_PARAMETER, builder.toString()));
	}
	
	protected void onWrongSender(CommandSender sender, Command command, String label, String[] args, CommandWrapper handler){
		if (sender instanceof Player)
			sender.sendMessage(UDPLI18n.format((Player) sender, "message.wrong_sender"));
		else
			sender.sendMessage(MESSAGE_WRONG_SENDER);
	}
	
	protected void onErrorParameter(CommandSender sender, Command command, String label, String[] args, CommandWrapper handler, int[] errorParameterIndexs){
		StringBuilder builder = new StringBuilder(ChatColor.WHITE.toString()).append("/").append(handler.getFullCommand());
		
		List<Integer> errorParameterIndexsList = Lists.newArrayList();
		for(int i:errorParameterIndexs)
			errorParameterIndexsList.add(i);
		
		{
			String[] requiredNames = handler.getRequiredNames();
			for (int i = 0, size = requiredNames.length; i < size; i++) {
				builder.append(errorParameterIndexsList.contains(i) ? ChatColor.RED : ChatColor.WHITE)
				.append(" <").append(localize(sender,requiredNames[i])).append(">");
			}
		}
		
		{
			String[] optionalNames = handler.getOptionalNames();
			for (int i = 0, size = optionalNames.length,requiredLength = handler.getRequiredNames().length; i < size; i++) {
				builder.append(errorParameterIndexsList.contains(requiredLength+i) ? ChatColor.RED : ChatColor.WHITE)
				.append(" [").append(localize(sender,optionalNames[i])).append("]");
			}
		}
		
		if (sender instanceof Player)
			sender.sendMessage(
					String.format(UDPLI18n.format((Player) sender, "message.wrong_parameter"), builder.toString()));
		else
			sender.sendMessage(String.format(MESSAGE_WRONG_PARAMETER,builder.toString()));
	}
	
	protected void onRunCommandFailure(CommandSender sender, Command command, String label, String[] args, CommandWrapper handler){
		if (sender instanceof Player)
			sender.sendMessage(UDPLI18n.format((Player) sender, "message.failure"));
		else
		sender.sendMessage(MESSAGE_FAILURE);
	}
	
	String localize(CommandSender sender, String key){
		if(i18n == null)
			return key;
		else if(sender instanceof Player)
			return i18n.localize((Player)sender, key);
		else 
			return i18n.localize(key);
	}
	
	/**
	 * 参数转换
	 * @param clazz 目标类型
	 * @param value 参数
	 */
	protected Object transformParameter(Class<?> clazz, String value) {
		if (value == null)
			return null;
		else if (clazz.equals(String.class))
			return value;
		else if (clazz.equals(int.class))
			return Integer.parseInt(value);
		else if (clazz.equals(float.class))
			return Float.parseFloat(value);
		else if (clazz.equals(double.class))
			return Double.parseDouble(value);
		else if (clazz.equals(long.class))
			return Long.parseLong(value);
		else if (clazz.equals(byte.class))
			return Byte.parseByte(value);
		else if (clazz.equals(short.class))
			return Short.parseShort(value);
		else if (parameterHandlers.containsKey(clazz))
			return parameterHandlers.get(clazz).transform(value);
		else
			throw new IllegalArgumentException(String.format("Can't transform parameter. Class: %1$s. Value: \"%2$s\".",clazz.getName(),value));
	}
	
	/**
	 * 补全参数
	 */
	protected List<String> tabCompleteParameter(Class<?> clazz, String value) {
		if (value == null)
			return Collections.emptyList();
		else if (parameterHandlers.containsKey(clazz))
			return parameterHandlers.get(clazz).tabComplete(value);
		else
			return Collections.emptyList();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String[] toLowerCaseArgs = Arrays.stream(args).map(String::toLowerCase).toArray(String[]::new);
		
		CommandWrapper parent = defaultHandler;
		int i, size;
		for (i = 0,size = toLowerCaseArgs.length; i < size; i++) {
			CommandWrapper wrapper = parent.getChildren().get(toLowerCaseArgs[i]);
			if(wrapper == null)
				break;
			
			parent = wrapper;
		}
		
		handleCommand(parent, sender, command, label, Arrays.copyOfRange(args, i, args.length));
		return true;
	}
	
	protected void handleCommand(CommandWrapper wrapper,CommandSender sender,Command command,String label,String args[]){
		switch (wrapper.onCommand(sender,command,label,args)) {
		case UNKNOWN_COMMAND:
			onUnknownCommand(sender, command, label, args, wrapper);
			break;
		case ERROR_PARAMETER:
			break;
		case NO_ENOUGH_PARAMETER:
			onNoEnoughParameter(sender, command, label, args, wrapper);
			break;
		case NO_PERMISSION:
			onNoPermission(sender, command, label, args, wrapper);
			break;
		case WRONG_SENDER:
			onWrongSender(sender, command, label, args, wrapper);
			break;
		case FAILURE:
			onRunCommandFailure(sender, command, label, args, wrapper);
			break;
		case SUCCESS:
			break;
		}
	}
	
	public Optional<CommandWrapper> getCommandWrapper(String[] args){
		CommandWrapper parent = defaultHandler;
        for (String arg : args) {
            CommandWrapper wrapper = parent.getChildren().get(arg.toLowerCase());
            if (wrapper == null)
                return Optional.empty();

            parent = wrapper;
        }
		
		return Optional.of(parent);
	}
	
	protected CommandWrapper createCommandWrapper(String[] args){
		String[] toLowerCaseArgs = Arrays.stream(args).map(String::toLowerCase).toArray(String[]::new);
		
		CommandWrapper parent = defaultHandler;
        for (String toLowerCaseArg : toLowerCaseArgs) {
            CommandWrapper wrapper = parent.getChildren().get(toLowerCaseArg);
            if (wrapper == null) {
                wrapper = new CommandWrapper(toLowerCaseArg, this, parent);
                parent.getChildren().put(wrapper.getNode(), wrapper);
            }

            parent = wrapper;
        }
		
		return parent;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> list = new ArrayList<>();
		
		CommandWrapper parent = defaultHandler;
		int i, size;
		for (i = 0,size = args.length; i < size; i++) {
			CommandWrapper wrapper = parent.getChildren().get(args[i].toLowerCase());
			if(wrapper == null)
				break;
			
			parent = wrapper;
		}
		list.addAll(parent.onTabComplete(Arrays.copyOfRange(args, i, args.length)));

		return list;
	}
	
	public static Builder builder(){
		return new Builder();
	}
	
	public static class Builder{
		private String name;
		private JavaPlugin plugin;
		private Map<Class<?>,CommandParameterHandler> parameterHandlers = Maps.newHashMap();
		private I18n i18n;
		private String usage;
		private String description;
		
		public Builder name(String name){
			this.name = name;
			return this;
		}
		
		public Builder plugin(JavaPlugin plugin){
			this.plugin = plugin;
			return this;
		}
		
		public Builder usage(String usage){
			this.usage = usage;
			return this;
		}
		
		public Builder description(String description){
			this.description = description;
			return this;
		}
		
		public Builder i18n(I18n i18n){
			this.i18n = i18n;
			return this;
		}
		
		public Builder parameterHandler(Class<?> clazz,CommandParameterHandler handler){
			parameterHandlers.put(clazz, handler);
			return this;
		}
		
		public AnnoCommandManager build(){
			AnnoCommandManager manager = new AnnoCommandManager(name, plugin, parameterHandlers);
			manager.setI18n(i18n);
			manager.setUsage(usage);
			manager.setDescription(description);
			return manager;
		}
	}
}
