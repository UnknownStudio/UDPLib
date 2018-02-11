package team.unstudio.udpl.core.inject;

import java.util.ArrayList;
import java.util.List;

public class Injector {
	
	private List<Class<?>> targetClasses = new ArrayList<>();
	
	public Injector() {}
	
	public void addClass(Class<?> clazz) {
		targetClasses.add(clazz);
	}
	
	public void inject() {
		
	}

}
