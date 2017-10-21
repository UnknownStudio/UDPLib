package team.unstudio.udpl.scoreboard;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * 一个名称与分数唯一对应的计分板
 */
public class BiScoreboard {
	
	private final Scoreboard scoreboard;
	private final Objective objective;
	private final BiMap<String,Integer> keyToScore = HashBiMap.create();
	
	public BiScoreboard(){
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
	
	public void put(String key, int score){
		String oldKey = getKey(score);
		if(oldKey != null)
			scoreboard.resetScores(oldKey);
		objective.getScore(key).setScore(score);
		keyToScore.forcePut(key, score);
	}
	
	public void put(int score, String key){
		this.put(key, score);
	}
	
	public void putAll(Map<String,Integer> map){
		map.entrySet().forEach(entry->put(entry.getKey(),entry.getValue()));
	}
	
	public void putAllInverse(Map<Integer,String> map){
		map.entrySet().forEach(entry->put(entry.getKey(),entry.getValue()));
	}
	
	public void remove(String key){
		scoreboard.resetScores(key);
		keyToScore.remove(key);
	}
	
	public void remove(int score){
		BiMap<Integer,String> scoreToKey = keyToScore.inverse();
		if(scoreToKey.containsKey(score)){
			scoreboard.resetScores(scoreToKey.get(score));
			scoreToKey.remove(score);
		}
	}
	
	public void removeAll(Collection<String> keys){
		keys.forEach(this::remove);
	}
	
	public void removeAllInverse(Collection<Integer> scores){
		scores.forEach(this::remove);
	}
	
	public Set<String> getKeys(){
		return keyToScore.keySet();
	}
	
	public Set<Integer> getScores(){
		return keyToScore.inverse().keySet();
	}
	
	public String getKey(int score){
		return keyToScore.inverse().get(score);
	}
	
	public int getScore(String key){
		return keyToScore.get(key);
	}
	
	public boolean containKey(String key){
		return keyToScore.containsKey(key);
	}
	
	public boolean containScore(int score){
		return keyToScore.inverse().containsKey(score);
	}
	
	public void display(Player player){
		player.setScoreboard(getScoreboard());
	}
	
	public void reset(Player player){
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
	}
}
