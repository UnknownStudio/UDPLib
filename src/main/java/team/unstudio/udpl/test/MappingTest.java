package team.unstudio.udpl.test;

import team.unstudio.udpl.mapping.MappingHelper;

public class MappingTest {

	public static void main(String[] args) {
		MappingHelper.INSTANCE.loadMapping("1.12");
		System.out.println("a -> "+MappingHelper.INSTANCE.getClassMapping().getDeobf("a"));
		System.out.println("EnumChatFormat -> "+MappingHelper.INSTANCE.getClassMapping().getObf("EnumChatFormat"));
		System.out.println("Advancement f -> "+MappingHelper.INSTANCE.getMemberDeobf("Advancement", "f()Ljava/util/Map;"));
		System.out.println("Advancement getCriteria -> "+MappingHelper.INSTANCE.getMemberObf("Advancement", "getCriteria()Ljava/util/Map;"));
	}
}
