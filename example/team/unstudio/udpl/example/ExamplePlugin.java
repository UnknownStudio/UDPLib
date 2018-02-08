package team.unstudio.udpl.example;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import team.unstudio.udpl.command.anno.AnnoCommandManager;
import team.unstudio.udpl.i18n.I18n;
import team.unstudio.udpl.i18n.YamlI18n;
import team.unstudio.udpl.util.PluginUtils;

public class ExamplePlugin extends JavaPlugin{

	public static I18n I18N;
	public static ExampleConfig CONFIG;
	
	@Override
	public void onLoad() {
		// save the default config from resource "config.yml"
		// 保存默认配置文件 "config.yml"
		saveDefaultConfig();

		// init config
		// 初始化配置文件类
		CONFIG = new ExampleConfig(new File(getDataFolder(),"config.yml"));
	}
	
	@Override
	public void onEnable() {
		// 将 Jar 包中的 lang 文件夹全部保存到 JavaPlugin::getDataFolder 中
		PluginUtils.saveDirectory(this, "lang", false);
		// 使用 YamlI18n 加载语言文件
		I18N = YamlI18n.fromFile(new File(getDataFolder(), "lang"));
		// 在日志中输出语言项
		getLogger().info(ExamplePlugin.I18N.localize("example.i18n"));

		// 使用 AnnoCommandManager 构建命令
		AnnoCommandManager
				.builder()
				.name("example") // 命令名
				.plugin(this) // 插件主类
				.parameterHandler(new MaterialParameterHandler()) // 参数处理器
				.build() // 构建 CommandManager
				.addHandler(new ExampleCommand()) // 设置命令处理器
				.registerCommand(); // 注册命令
	}
}
