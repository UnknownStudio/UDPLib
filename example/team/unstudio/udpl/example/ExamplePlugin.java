package team.unstudio.udpl.example;
import java.io.File;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import team.unstudio.udpl.command.anno.AnnoCommandManager;
import team.unstudio.udpl.i18n.I18n;
import team.unstudio.udpl.i18n.SimpleI18n;
import team.unstudio.udpl.util.PluginUtils;

public class ExamplePlugin extends JavaPlugin{

	public static I18n I18N;
	@Override
	public void onEnable() {
		PluginUtils.saveDirectory(this, "lang", false);
		I18N = new SimpleI18n(new File(getDataFolder(), "lang"));
		Player player = null;
		player.sendMessage(ExamplePlugin.I18N.localize(player, "example.i18n"));
		AnnoCommandManager.builder().name("example").plugin(this).parameterHandler(Material.class, new MaterialParameterHandler()).build()
		.addHandler(new ExampleCommand()).registerCommand();
	}
}
