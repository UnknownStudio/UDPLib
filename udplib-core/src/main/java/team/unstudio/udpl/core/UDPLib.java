package team.unstudio.udpl.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.base.Strings;

import team.unstudio.udpl.area.Area;
import team.unstudio.udpl.area.AreaDataContainer;
import team.unstudio.udpl.bungeecord.ServerLocation;
import team.unstudio.udpl.command.anno.AnnoCommandManager;
import team.unstudio.udpl.core.command.PluginManager;
import team.unstudio.udpl.core.command.UDPLCommand;
import team.unstudio.udpl.core.inject.Injector;
import team.unstudio.udpl.nms.NmsManager;
import team.unstudio.udpl.core.util.VersionCheck;
import team.unstudio.udpl.nms.NmsHelper;
import team.unstudio.udpl.nms.mapping.MappingHelper;
import team.unstudio.udpl.nms.nbt.NBTUtils;
import team.unstudio.udpl.ui.Container;
import team.unstudio.udpl.util.CacheUtils;
import team.unstudio.udpl.util.PlayerUtils;
import team.unstudio.udpl.util.PluginUtils;
import team.unstudio.udpl.util.SignUtils;
import team.unstudio.udpl.util.reflect.NMSReflectionUtils;

import java.io.File;

public final class UDPLib extends JavaPlugin{

	private static UDPLib INSTANCE;
	private static UDPLConfiguration CONFIG;
	private static Logger LOGGER = LogManager.getLogger("UDPLib");
	private static VersionCheck VERSION_CHECK;

	public UDPLib() {
		INSTANCE = this;
	}

	@Override
	public void onLoad() {
		ConfigurationSerialization.registerClass(AreaDataContainer.class);
		ConfigurationSerialization.registerClass(Area.class);
		ConfigurationSerialization.registerClass(ServerLocation.class);
		ConfigurationSerialization.registerClass(Container.class);
		
		NBTUtils.registerAllNBTSerilizable();
		
		saveDefaultConfig();
		CONFIG = new UDPLConfiguration(new File(getDataFolder(), "config.yml"));
		if (!CONFIG.reload())
			UDPLib.getLog().error("Config not loaded, please check console.");
		
		team.unstudio.udpl.UDPLib.setDebug(CONFIG.debug);
		
		PluginUtils.saveDirectory(getInstance(), "lang", false);
		
		if (Strings.isNullOrEmpty(CONFIG.language))
			UDPLI18n.setLocale(CONFIG.language);
		
		if(CONFIG.enableTest)
			onLoadTest();
	}

	@Override
	public void onEnable() {
		Injector injector = new Injector();
		injector.addInjectObject("core_i18n", UDPLI18n.I18N);
		injector.addInjectObject("core_instance", this);
		injector.addInjectObject("core_logger", LOGGER);
		injector.addInjectObject("nms_manager", createNmsManager());
		
		injector.addClass(team.unstudio.udpl.UDPLib.class);
		injector.addClass(MappingHelper.class);
		injector.addClass(NMSReflectionUtils.class);
		injector.addClass(NmsHelper.class);
		
		injector.addClass(SignUtils.class);
		injector.addClass(PlayerUtils.class);
		injector.addClass(CacheUtils.class);
		
		injector.inject();
		
		loadPluginManager();
		
		new AnnoCommandManager("udpl", this).addHandler(new UDPLCommand()).registerCommand();
		
		if(CONFIG.enableTest)
			onEnableTest();
		
		runVersionCheck();
	}

	@Override
	public void onDisable() {
		//防止玩家未关闭界面造成刷物品
		for(Player player:Bukkit.getOnlinePlayers())
			player.closeInventory();
	}
	
	private void loadPluginManager(){
		new AnnoCommandManager("pm", this).addHandler(new PluginManager()).registerCommand();
	}

	public static UDPLConfiguration getUDPLConfig(){
		return CONFIG;
	}

	public static UDPLib getInstance(){
		return INSTANCE;
	}
	
	public static boolean isDebug(){
		return team.unstudio.udpl.UDPLib.isDebug();
	}

	public static Logger getLog() {
		return LOGGER;
	}

	public static void debug(String value){
		if(isDebug())
			getInstance().getLogger().info(value);
	}
	
	public static void debug(Throwable e){
		if(isDebug())
			e.printStackTrace();
	}
	
	private static void runVersionCheck(){
		if(VERSION_CHECK == null){
			VERSION_CHECK = new VersionCheck();
		}
		VERSION_CHECK.runTaskAsynchronously(getInstance());
	}
	
	private static NmsManager createNmsManager() {
		try {
			return new team.unstudio.udpl.core.nms.v1_11_R1.NmsManager();
		} catch (Exception e) {
			debug(e);
		}
		return null;
	}
	
	private static void onLoadTest() {
		try {
			Class.forName("team.unstudio.udpl.core.test.TestLoader")
			.getDeclaredMethod("onLoad")
			.invoke(null);
		} catch (Exception ignored) {
		}
	}
	
	private static void onEnableTest() {
		try {
			Class.forName("team.unstudio.udpl.core.test.TestLoader")
			.getDeclaredMethod("onEnable")
			.invoke(null);
		} catch (Exception ignored) {
		}
	}
}
