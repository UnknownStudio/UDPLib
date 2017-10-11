package team.unstudio.udpl.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public final class ScoreboardHelper {
	
	private ScoreboardHelper() {}
	
	public static void setPlayerScoreboard(Player player,Scoreboard scoreboard){
		player.setScoreboard(scoreboard);
	}
	
	public static void clear(Player player){
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
	
	public static void reset(Player player){
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
	}
}
