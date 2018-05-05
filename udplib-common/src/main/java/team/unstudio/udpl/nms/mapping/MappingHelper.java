package team.unstudio.udpl.nms.mapping;

import team.unstudio.udpl.UDPLib;
import team.unstudio.udpl.annotation.Init;
import team.unstudio.udpl.exception.MemberMappingException;
import team.unstudio.udpl.util.ServerUtils;

public final class MappingHelper {

    private MappingHelper() {
    }

    private static MemberMapping memberMapping;

    public static MemberMapping getMemberMapping() {
        return memberMapping;
    }

    @Init
    public static void loadMapping() {
        loadMapping(ServerUtils.getMinecraftVersion());
    }

    private static void loadMapping(String version) {
        try {
            memberMapping = MemberMapping.fromClassLoader(version);
            UDPLib.getLogger().info("Loaded mapping " + version);
        } catch (MemberMappingException e) {
            UDPLib.debug(e);
            memberMapping = null;
        }
    }
}
