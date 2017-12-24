package team.unstudio.udpl.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ScoreboardWrapper {
	
	protected Scoreboard scoreboard;
	protected Objective objective;

	public ScoreboardWrapper() {
	}

	public ScoreboardWrapper(Scoreboard scoreboard, Objective objective){
		this.scoreboard = scoreboard;
		this.objective = objective;
	}

	public ScoreboardWrapper(Objective objective) {
		this(Bukkit.getScoreboardManager().getNewScoreboard(), objective);
	}
	
	public Scoreboard getScoreboard(){
		return scoreboard;
	}
	
	public Objective getObjective(){
		return objective;
	}
	
	public void setDisplayerSlot(DisplaySlot slot){
		objective.setDisplaySlot(slot);
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
		map.forEach((key, value) -> put(key, value));
	}
	
	public void remove(String key){
		scoreboard.resetScores(key);
	}
	
	public void removeAll(Collection<String> keys){
		keys.forEach(this::remove);
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
