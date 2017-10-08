package team.unstudio.udpl.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class ScoreboardHelper {
	
	private ScoreboardHelper() {}
	
	public static void setPlayerScoreboard(Player player,ScoreboardWrapper board){
		player.setScoreboard(board.getScoreboard());
	}
	
	public static void clear(Player player){
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
}
