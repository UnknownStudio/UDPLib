package team.unstudio.udpl.command.anno;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;

import com.google.common.base.Strings;

import team.unstudio.udpl.util.ServerUtils;

public interface CommandParameterHandler {

	/**
	 * 参数转换
	 * @param value
	 * @return
	 */
	Object transform(String value);
	
	/**
	 * 参数自动补全
	 * @param value
	 * @return
	 */
	default List<String> tabComplete(String value){
		return Collections.emptyList();
	}
	
	public static class PlayerHandler implements CommandParameterHandler{

		@Override
		public Object transform(String value) {
			return Bukkit.getPlayerExact(value);
		}

		@Override
		public List<String> tabComplete(String value) {
			String prefix = Strings.nullToEmpty(value);
			return Arrays.asList(ServerUtils.getOnlinePlayerNamesWithFilter(name->name.startsWith(prefix)));
		}
	}
	
	public static class OfflinePlayerHandler implements CommandParameterHandler{

		@SuppressWarnings("deprecation")
		@Override
		public Object transform(String value) {
			return Bukkit.getOfflinePlayer(value);
		}
	}
}
