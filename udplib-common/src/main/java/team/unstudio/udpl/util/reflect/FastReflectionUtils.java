package team.unstudio.udpl.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FastReflectionUtils {
	
	public static FieldAccessor create(Field field) throws Exception {
		return create(field, null);
	}
	
	public static FieldAccessor create(Field field, Object obj) throws Exception{
		return new ReflectFieldAccessor(field, obj);
	}
	
	public static MethodAccessor create(Method method) throws Exception {
		return new ReflectMethodAccessor(method, null);
	}
	
	public static MethodAccessor create(Method method, Object obj) throws Exception {
		return new ReflectMethodAccessor(method, obj);
	}
	
}
