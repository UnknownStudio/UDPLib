package team.unstudio.udpl.scoreboard;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardWrapper {
	
	private final Scoreboard scoreboard;
	private final Objective objective;
	
	public ScoreboardWrapper(){
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		objective = scoreboard.registerNewObjective("ScoreboardAPI", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	public Scoreboard getScoreboard(){
		return scoreboard;
	}
	
	public String getDisplayName(){
		return objective.getDisplayName();
	}
	
	public void setDisplayName(String name){
		objective.setDisplayName(name);
	}
	
	public void put(String key,int score){
		objective.getScore(key).setScore(score);
	}
	
	public void putAll(Map<String,Integer> map){
		map.entrySet().forEach(entry->objective.getScore(entry.getKey()).setScore(entry.getValue()));
	}
	
	public void remove(String key){
		scoreboard.resetScores(key);
	}
	
	public void removeAll(Collection<String> keys){
		keys.forEach(scoreboard::resetScores);
	}
	
	public Set<String> getKeys(){
		return scoreboard.getEntries();
	}
	
	public Set<String> getKeys(int score){
		return getKeys().stream().filter(key->getScore(key) == score).collect(Collectors.toSet());
	}
	
	public int getScore(String key){
		return objective.getScore(key).getScore();
	}
	
	public void display(Player player){
		player.setScoreboard(getScoreboard());
	}
	
	public void reset(Player player){
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
	}
}
