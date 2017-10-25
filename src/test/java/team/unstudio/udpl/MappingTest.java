package team.unstudio.udpl;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import team.unstudio.udpl.mapping.MappingHelper;

import java.io.File;
import java.io.IOException;

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

	@AfterClass
	public static void delFile() throws IOException {
		File file = new File("mappings");
		FileUtils.deleteDirectory(file);
	}
}
