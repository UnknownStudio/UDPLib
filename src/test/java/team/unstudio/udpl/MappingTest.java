package team.unstudio.udpl;

import team.unstudio.udpl.mapping.MappingHelper;

public class MappingTest {

	public static void main(String[] args) {
		MappingHelper.loadMapping("1.12");
		System.out.println("Advancement f -> "+MappingHelper.getMemberDeobf("Advancement", "f()Ljava/util/Map;"));
		System.out.println("Advancement getCriteria -> "+MappingHelper.getMemberObf("Advancement", "getCriteria()Ljava/util/Map;"));
	}
}
