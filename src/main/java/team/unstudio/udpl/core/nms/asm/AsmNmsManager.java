package team.unstudio.udpl.core.nms.asm;

import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.nms.NmsNBT;
import team.unstudio.udpl.nms.ReflectionUtils;

public class AsmNmsManager {
	
	private static final boolean debug = UDPLib.isDebug();
	
	public static final String NMS_VERSION = ReflectionUtils.PackageType.getServerVersion();
	
	private final DynamicClassLoader classLoader;
	public NmsNBT nmsNbt;

	public AsmNmsManager() {
		classLoader = new DynamicClassLoader();
		loadNbt();
	}
	
	private void loadNbt(){
		byte[] b = NmsNbtGenerator.generate(NMS_VERSION);
		try {
			nmsNbt = (NmsNBT) classLoader.loadBytecode("team.unstudio.udpl.core.nms.NMSNBT", b, 0, b.length).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			if(debug)
				e.printStackTrace();
		}
	}
}
