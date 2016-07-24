package team.unstudio.udpc.api.tellraw;

import javax.annotation.Nonnull;

public class ClickEvent extends Event{
	
	public static final String run_command = "run_command";
	public static final String suggest_command = "suggest_command";
	public static final String open_url = "open_url";

	private String action;
	private String value;
	
	public ClickEvent(@Nonnull String action,@Nonnull String value) {
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
		return "clickEvent:{action:"+action+",value:\""+value+"\"}";
	}
}
