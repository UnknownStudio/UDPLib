package team.unstudio.udpl.api.scoreboard;

import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
/**

 * 
 * @author defoli_ation
 *
 */
public class Board {
	protected final org.bukkit.scoreboard.Scoreboard scoreboard;
	private final Objective objective;
	private final TreeMap<Integer,String> map = new TreeMap<>();
	private Score score;
	/**
	 * 
	 * @param title 记分板标题
	 */
	public Board(String title){
		scoreboard=Bukkit.getScoreboardManager().getNewScoreboard();
		this.objective = scoreboard.registerNewObjective(title, "dummy");
		this.objective.setDisplayName(title);
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	/**设置某一行的文本.
	 * 
	 * 
	 *由于记分板最大只能显示16行，所以建议行数为16以内
	 * @param line 行数
	 * @param text 文本
	 */
	public void set(int line,String text){
		if(map.containsKey(line))
			remove(line);
		map.put(line, text);
		setup();
	}
	/**
	 * 获得记分板上这一行的文本.
	 * 若为空则返回null
	 * @param line 行数
	 * @return String
	 */
	public String get(int line){
		return map.get(line);
	}
	/**
	 * 移除对应行数的文本
	 * @param line 某一行
	 * 
	 */
	public void remove(int line){
		if(line>=16)return;
		if(map.containsKey(line)){
			this.scoreboard.resetScores(map.get(line));
			map.remove(line);
		}
	}

	/**
	 * 移除对应的文本
	 * @param text 要移除的文本
	 * 
	 */
	public void remove(String text){
		this.scoreboard.resetScores(text);
		for(int i=0;i<map.size();i++)
			if(map.get(i).equals(text))
				remove(i);
	}
	/**将数组内的文本一一对应到记分板上.
	 * 最大只能设置16行文本
	 * 
	 * 
	 * 
	 * 
	 * @param text 将要设置的文本
	 */
	public void set(String... text){
		for(int i=0;i<16;i++)
			if(text[i]!=null){
				remove(i);
				map.put(i, text[i]);
			}
		setup();
	}
	/**
	 * 将所有设置提交到对应玩家的记分板上
	 */
	private void setup(){
		for(int i=0;i<16;i++){
			String s = map.get(i);
			if(s==null)continue;
			this.score=this.objective.getScore(s);
			this.score.setScore(1);
			this.score.setScore(i);
		}
	}
	/**Scoreboard的标题
	 * 
	 * @return 标题名
	 */
	public String getTitle(){
		return objective.getDisplayName();
	}
	/**返回记分板所有字符串.
	 * 
	 * 
	 * @return 长度为16的字符串
	 */
	public String[] getText(){
		String[] stringsOfScoreboard = new String[16];
		for(int i=0;i<16;i++){
			stringsOfScoreboard[i]=map.get(i);
		}
		return stringsOfScoreboard;
	}
	/**
	 * setTitle 设置标题.
	 *
	 * 
	 * @param title 标题名称
	 */
	public void setTitle(String title){
		this.objective.setDisplayName(title);
	}
	/**
	 * 
	 * @param line 行数
	 * @return org.bukkit.scoreboard.Score
	 */
	public Score getScore(String line){
		return this.objective.getScore(line);
	}
	/**
	 * 占用为1，没有被占用为-1

	 * 
	 * @return 长度为16的数组
	 */
	public int[] getIdle(){
		int[] value = new int[16];
		for(int i=0;i<16;i++){
			if(map.get(i)!=null)
				value[i] = 1;
			else value[i] =-1;
		}
		return value;
	}
	@Override
	public int hashCode(){
		return map.hashCode()+objective.getDisplayName().hashCode();
	}
	@Override
	public boolean equals(Object o){
		if(o==null)throw new NullPointerException();
		if(!(o instanceof Board))return false;
		Board b = (Board) o;
		if(!(objective.getDisplayName().equals(b.getTitle())))return false;
		if(!(scoreboard.equals(b.scoreboard)))return false;
		if(!(getText().equals(b.getText())))return false;
		return true;
	}
}
