package team.unstudio.udpl.util.extra;

public class ASMClassLoader extends ClassLoader{

	public ASMClassLoader() {
	}
	
	public ASMClassLoader(ClassLoader parent) {
		super(parent);
	}
	
	public Class<?> loadClass(byte[] b) {
		return loadClass(null, b, 0, b.length);
	}
	
	public Class<?> loadClass(String name, byte[] b, int off, int len) {
		return defineClass(name, b, off, len);
	}
}
