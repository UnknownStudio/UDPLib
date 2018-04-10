package team.unstudio.udpl.util.extra;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public final class UnsafeUtils {
	
	private UnsafeUtils() {
	}

	private static final Unsafe UNSAFE;
	static {
		Unsafe unsafe;
		try {
			Class clazz = Class.forName("sun.misc.Unsafe");
			Field theUnsafe = clazz.getDeclaredField("theUnsafe");
			theUnsafe.setAccessible(true);
			unsafe = (Unsafe) theUnsafe.get(null);
		} catch (ClassNotFoundException | NoSuchFieldException | SecurityException e) {
			unsafe = null;
		} catch (IllegalAccessException e) {
			throw new AssertionError(e);
		}
		UNSAFE = unsafe;
	}

	public static boolean isUnsafeSupported() {
		return UNSAFE != null;
	}

	public static Unsafe getUnsafe() {
		return UNSAFE;
	}
}
