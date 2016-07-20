package team.unstudio.udpc.api.tellraw;

import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Tellraw {

	private String text;
	private Font font;
	private List<Event> events;
	
	public Tellraw() {}
	
	public Tellraw(@Nonnull String text){
		this.setText(text);
	}
	
	public Tellraw(@Nonnull String text,Font font){
		this(text);
		this.setFont(font);
	}

	public String getText() {
		return text;
	}

	public Tellraw setText(String text) {
		this.text = text;
		return this;
	}

	public Font getFont() {
		return font;
	}

	public Tellraw setFont(Font font) {
		this.font = font;
		return this;
	}
	
	public Tellraw addEvent(Event event){
		events.add(event);
		return this;
	}
	
	public boolean removeEvent(Event event){
		return events.remove(event);
	}
	
	public List<Event> getEvents(){
		return events;
	}
	
	public boolean runTellraw(Player player){
		StringBuilder builder = new StringBuilder();
		builder.append("/minecraft:tellraw ");
		builder.append(player.getName());
		builder.append(" {");
		builder.append("text:\"");
		builder.append(text);
		builder.append("\"");
		if(font!=null)builder.append(","+font.toString());
		for(Event e:events){
			builder.append(","+e.toString());
		}
		builder.append("}");
		return Bukkit.dispatchCommand(Bukkit.getConsoleSender(),builder.toString());
	}
}
