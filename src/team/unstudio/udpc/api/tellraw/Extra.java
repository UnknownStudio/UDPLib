package team.unstudio.udpc.api.tellraw;

import java.util.ArrayList;
import java.util.List;

public class Extra extends Event{
	
	private List<Object> objs = new ArrayList<>();
	
	public Extra() {}
	
	public Extra addString(String text){
		objs.add(text);
		return this;
	}
	
	public Extra add(String text,Font font,Event event){
		objs.add(new Block(text,font,event));
		return this;
	}
	
	public boolean remove(Object o) {
		return objs.remove(o);
	}
	
	public void clear(){
		objs.clear();
	}
	
	public boolean isEmpty(){
		return objs.isEmpty();
	}
	
	public int size(){
		return objs.size();
	}
	
	public Object set(int index, Object element){
		return objs.set(index, element);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("extra:[");
		for(Object o:objs){
			if(o instanceof String){
				builder.append("\""+o+"\",");
			}else if(o instanceof Block){
				builder.append("{"+o.toString()+"},");
			}
		}
		return builder.substring(0, builder.length()-1)+"]";
	}
	
	public class Block{
		String text;
		Font font;
		Event event;
		
		public Block(String text,Font font,Event event) {
			this.text = text;
			this.font = font;
			this.event = event;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(",text:\""+text+"\"");
			if(font!=null)builder.append(","+font.toString());
			if(event!=null)builder.append(","+event.toString());
			return builder.substring(1);
		}
	}

}
