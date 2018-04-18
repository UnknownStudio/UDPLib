package team.unstudio.udpl.util.extra;

public class ASMClassLoader extends ClassLoader {

	public ASMClassLoader() {
	}

	public ASMClassLoader(ClassLoader parent) {
		super(parent);
	}

	public Class<?> loadClass(byte[] b) {
		return loadClass(null, b);
	}

	public Class<?> loadClass(String name, byte[] b) {
		return defineClass(name, b, 0, b.length);
	}

	public Class<?> loadClass(String name, byte[] b, int off, int len) {
		Class<?> clazz = defineClass(name, b, off, len);
		return clazz;
	}

	public boolean hasClass(String name) {
		try {
			Class.forName(name);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
