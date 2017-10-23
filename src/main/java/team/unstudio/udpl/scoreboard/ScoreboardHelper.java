package team.unstudio.udpl.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public interface ScoreboardHelper {
	static void setPlayerScoreboard(Player player, Scoreboard scoreboard){
		player.setScoreboard(scoreboard);
	}
	
	static void clear(Player player){
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
	
	static void reset(Player player){
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
	}
}
