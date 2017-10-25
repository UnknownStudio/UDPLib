package team.unstudio.udpl.scoreboard;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * 一个名称与分数唯一对应的计分板
 */
public class BiScoreboard extends ScoreboardWrapper {

	private final BiMap<String,Integer> keyToScore = HashBiMap.create();
	
	public BiScoreboard(){
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		objective = scoreboard.registerNewObjective("ScoreboardAPI", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	@Override
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
	
	public void putAllInverse(Map<Integer,String> map){
		map.entrySet().forEach(entry->put(entry.getKey(),entry.getValue()));
	}

	@Override
	public void remove(String key){
		super.remove(key);
		keyToScore.remove(key);
	}
	
	public void remove(int score){
		BiMap<Integer,String> scoreToKey = keyToScore.inverse();
		if(scoreToKey.containsKey(score)){
			super.remove(scoreToKey.get(score));
			scoreToKey.remove(score);
		}
	}
	
	public void removeAllInverse(Collection<Integer> scores){
		scores.forEach(this::remove);
	}

	@Override
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
}
