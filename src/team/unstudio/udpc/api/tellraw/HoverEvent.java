package team.unstudio.udpc.api.tellraw;

import javax.annotation.Nonnull;

public class HoverEvent extends Event{
	
	public static final String show_text = "show_text";
	public static final String show_item = "show_item";
	public static final String show_achievement = "show_achievement";
	
	private String action;
	private String value;
	
	public HoverEvent(@Nonnull String action,@Nonnull String value) {
		this.action = action;
		this.value = value;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(@Nonnull String action) {
		this.action = action;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(@Nonnull String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "hoverEvent:{action:"+action+",value:\""+value+"\"}";
	}
}
