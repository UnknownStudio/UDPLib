package team.unstudio.udpl;

import org.junit.Test;
import static org.junit.Assert.*;
import team.unstudio.udpl.mapping.MappingHelper;

public class MappingTest {
	@Test
	public void mapping() {
		MappingHelper.loadMapping("1.12");
		assertEquals("getCriteria", MappingHelper.getMemberDeobf("Advancement", "f()Ljava/util/Map;"));
		assertEquals("f", MappingHelper.getMemberObf("Advancement", "getCriteria()Ljava/util/Map;"));

		MappingHelper.loadMapping("1.9");
		assertEquals("FACING", MappingHelper.getMemberDeobf("BlockDoor", "a"));
		assertEquals("a", MappingHelper.getMemberObf("BlockDoor", "FACING"));
		assertEquals("a", MappingHelper.getMemberObf("BlockDoubleStepAbstract$EnumStoneSlabVariant", "STONE"));


		MappingHelper.loadMapping("1.8");
		MappingHelper.loadMapping("1.10");
		MappingHelper.loadMapping("1.11");
	}
}
