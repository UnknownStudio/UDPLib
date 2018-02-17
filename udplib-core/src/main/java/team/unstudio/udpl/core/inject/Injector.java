package team.unstudio.udpl.core.inject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.annotation.Init;

public class Injector {
	
	private final List<Class<?>> targetClasses = new ArrayList<>();
	private final Map<String, Object> injectObject = new HashMap<>();
	
	public Injector() {}
	
	public void addClass(Class<?> clazz) {
		targetClasses.add(clazz);
	}
	
	public void addInjectObject(String name, Object obj) {
		injectObject.put(name, obj);
	}
	
	public void inject() {
		for(Class<?> clazz : targetClasses) {
			for(Field field : clazz.getDeclaredFields()) {
				int modifiers = field.getModifiers();
				if(Modifier.isFinal(modifiers))
					continue;
				if(!Modifier.isStatic(modifiers))
					continue;
				Init init = field.getAnnotation(Init.class);
				if(init == null)
					continue;
				field.setAccessible(true);
				try {
					field.set(null, injectObject.get(init.value()));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					UDPLib.getLog().warn(e.getMessage(), e);
				}
			}
		}
		for(Class<?> clazz : targetClasses) {
			for(Method method : clazz.getDeclaredMethods()) {
				int modifiers = method.getModifiers();
				if(!Modifier.isStatic(modifiers))
					continue;
				Init init = method.getAnnotation(Init.class);
				if(init == null)
					continue;
				method.setAccessible(true);
				try {
					method.invoke(null);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					UDPLib.getLog().warn(e.getMessage(), e);
				}
			}
		}
	}

}
