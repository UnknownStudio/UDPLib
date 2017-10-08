package team.unstudio.udpl.core.nms.asm;

import java.security.SecureClassLoader;

public class DynamicClassLoader extends SecureClassLoader{
	
	public DynamicClassLoader() {}
	
	public DynamicClassLoader(ClassLoader parent) {
		super(parent);
	}

	public Class<?> loadBytecode(String name,byte[] b,int off,int len){
		return defineClass(name, b, off, len);
	}
}
