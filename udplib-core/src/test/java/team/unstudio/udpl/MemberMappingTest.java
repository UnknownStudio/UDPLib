package team.unstudio.udpl;

import static org.junit.Assert.*;

import org.junit.Test;
import team.unstudio.udpl.exception.MemberMappingException;
import team.unstudio.udpl.nms.mapping.MemberMapping;

import java.util.Arrays;

public class MemberMappingTest {
    @Test
    public void loader() {
        MemberMapping map = new MemberMapping(Arrays.asList("BiomeBase$EnumTemperature c MEDIUM",
            "Block a (LIBlockData;LEntityHuman;LWorld;LBlockPosition;)F getDamage",
            "Block c ()Ljava/lang/String; getName"));

        assertEquals("MEDIUM", map.getDeobf("BiomeBase$EnumTemperature", "c", ""));
        assertEquals("c", map.getObf("BiomeBase$EnumTemperature", "MEDIUM", ""));
        assertEquals("getName", map.getDeobf("Block", "c", "()Ljava/lang/String;"));
        assertEquals("c", map.getObf("Block", "getName", "()Ljava/lang/String;"));
        assertEquals("getDamage", map.getDeobf("Block", "a", "(LIBlockData;LEntityHuman;LWorld;LBlockPosition;)F"));
        assertEquals("a", map.getObf("Block", "getDamage", "(LIBlockData;LEntityHuman;LWorld;LBlockPosition;)F"));
    }

    @Test
    public void deobf() throws MemberMappingException {
        MemberMapping mapping = MemberMapping.fromClassLoader(MemberMappingTest.class.getClassLoader(), "mappings/1.8.8/members.csrg");
        assertEquals("postBreak", mapping.getDeobf("Block", "d", "(LWorld;LBlockPosition;LIBlockData;)V"));
        assertEquals("durability", mapping.getDeobf("Block", "x", ""));
        assertEquals("dropNaturally", mapping.getDeobf("BlockCrops", "a", "(LWorld;LBlockPosition;LIBlockData;FI)V"));

        mapping = MemberMapping.fromClassLoader(MemberMappingTest.class.getClassLoader(), "mappings/1.12/members.csrg");
        assertEquals("revokeCritera", mapping.getDeobf("AdvancementDataPlayer", "b", "(LAdvancement;Ljava/lang/String;)Z"));
        assertEquals("color", mapping.getDeobf("TileEntityBanner", "f", ""));
        assertEquals("setGameProfile", mapping.getDeobf("TileEntitySkull", "a", "(Lcom/mojang/authlib/GameProfile;)V"));
    }

    @Test
    public void obf() throws MemberMappingException {
        MemberMapping mapping = MemberMapping.fromClassLoader(MemberMappingTest.class.getClassLoader(), "mappings/1.8.8/members.csrg");
        assertEquals("d", mapping.getObf("Block", "postBreak", "(LWorld;LBlockPosition;LIBlockData;)V"));
        assertEquals("x", mapping.getObf("Block", "durability", ""));
        assertEquals("a", mapping.getObf("BlockCrops", "dropNaturally", "(LWorld;LBlockPosition;LIBlockData;FI)V"));

        mapping = MemberMapping.fromClassLoader(MemberMappingTest.class.getClassLoader(), "mappings/1.12/members.csrg");
        assertEquals("b", mapping.getObf("AdvancementDataPlayer", "revokeCritera", "(LAdvancement;Ljava/lang/String;)Z"));
        assertEquals("f", mapping.getObf("TileEntityBanner", "color", ""));
        assertEquals("a", mapping.getObf("TileEntitySkull", "setGameProfile", "(Lcom/mojang/authlib/GameProfile;)V"));
    }
}
