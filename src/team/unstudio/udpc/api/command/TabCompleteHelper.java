package team.unstudio.udpc.api.command;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TabCompleteHelper implements TabCompleter{
	
	private final CommandManager commandManager;
	private final Map<String,List<String>> map;
	
	public TabCompleteHelper(CommandManager commandManager) {
		this.commandManager = commandManager;
		this.map = new HashMap<>();
		
		for(CommandHandler h:commandManager.getCommandHandlers()){
			for(Method m:h.getClass().getMethods()){
				m.setAccessible(true);
				team.unstudio.udpc.api.command.Command anno = m.getAnnotation(team.unstudio.udpc.api.command.Command.class);
				if(anno==null) continue;
				
				StringBuilder builder = new StringBuilder("");
				for(String s:anno.value()){
					if(!map.containsKey(builder.toString())) map.put(builder.toString(), new ArrayList<String>());
					map.get(builder.toString()).add(s);
					builder.append(s+" ");
				}
			}
		}
	}

	public CommandManager getCommandManager() {
		return commandManager;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		StringBuilder builder = new StringBuilder("");
		
		for(int i=0;i<args.length-1;i++){
			builder.append(args[i]+" ");
		}
		
		if(map.containsKey(builder.toString())){
			if(args[args.length-1].isEmpty())return map.get(builder.toString());
			else{
				List<String> list = new ArrayList<>();
				String start = args[args.length-1];
				for(String s:map.get(builder.toString())){
					if(s.startsWith(start))list.add(s);
				}
				return list;
			}
		}else{
			List<String> list = new ArrayList<>();
			for(Player player:Bukkit.getOnlinePlayers())list.add(player.getName());
			return list;
		}
	}
}
