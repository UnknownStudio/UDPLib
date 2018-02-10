package team.unstudio.udpl.nms.util;

import team.unstudio.udpl.util.ReflectionUtils;
import team.unstudio.udpl.util.ServerUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureClassLoader;

public class NmsClassLoader extends SecureClassLoader {

	private final NmsClassTransformer transformer;
	
	public NmsClassLoader(){
		this(ReflectionUtils.PackageType.getServerVersion(), ServerUtils.getMinecraftVersion());
	}
	
	public NmsClassLoader(String targetNmsVersion, String targetMinecraftVersion) {
		transformer = new NmsClassTransformer(targetNmsVersion, targetMinecraftVersion);
	}
	
	public NmsClassLoader(ClassLoader parent) {
		this(parent, ReflectionUtils.PackageType.getServerVersion(), ServerUtils.getMinecraftVersion());
	}

	public NmsClassLoader(ClassLoader parent, String targetNmsVersion, String targetMinecraftVersion) {
		super(parent);
		transformer = new NmsClassTransformer(targetNmsVersion, targetMinecraftVersion);
	}
	
	public Class<?> loadClass(InputStream input, String bukkitVersion, String minecraftVersion) throws IOException{
		byte[] b = transformer.transform(input, bukkitVersion, minecraftVersion);
		return loadClass(null, b, 0, b.length);
	}
	
	public Class<?> loadClass(byte[] bytes, String bukkitVersion, String minecraftVersion) throws IOException{
		byte[] b = transformer.transform(bytes, bukkitVersion, minecraftVersion);
		return loadClass(null, b, 0, b.length);
	}

	public Class<?> loadClass(String name, byte[] b, int off, int len) {
		return defineClass(name, b, off, len);
	}
}
