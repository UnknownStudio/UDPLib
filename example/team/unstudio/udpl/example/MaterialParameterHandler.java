package team.unstudio.udpl.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Material;

import team.unstudio.udpl.command.anno.CommandParameterHandler;

public class MaterialParameterHandler implements CommandParameterHandler{

	@Override
	public Object transform(String value) {
		return Material.valueOf(value);
	}
	
	@Override
	public List<String> tabComplete(String value) {
		return Arrays.stream(Material.values())
				.map(material->material.name())
				.filter(material->material.startsWith(value))
				.collect(Collectors.toList());
	}

}
