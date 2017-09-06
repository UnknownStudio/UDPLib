package team.unstudio.udpl.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * 记分板的管理类
 * @author AAA
 *
 */
public class ScoreboardManager {
	public static void setPlayerScoreboard(Player p,Board board){
		p.setScoreboard(board.scoreboard);
	}
	public static void clear(Player p){
		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
}
