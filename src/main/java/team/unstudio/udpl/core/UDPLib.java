package team.unstudio.udpl.core;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import team.unstudio.udpl.area.Area;
import team.unstudio.udpl.area.AreaDataContainer;
import team.unstudio.udpl.bungeecord.ServerLocation;
import team.unstudio.udpl.command.anno.AnnoCommandManager;
import team.unstudio.udpl.core.test.TestLoader;
import team.unstudio.udpl.mapping.MappingHelper;
import team.unstudio.udpl.nms.NmsHelper;
import team.unstudio.udpl.nms.nbt.NBTUtils;
import team.unstudio.udpl.util.CacheUtils;
import team.unstudio.udpl.util.PlayerUtils;
import team.unstudio.udpl.util.PluginUtils;
import team.unstudio.udpl.util.SignUtils;

import java.io.File;

public final class UDPLib extends JavaPlugin{

	public static final String NAME = "UDPLib";
	public static final String VERSION = "1.0.0";

	private static UDPLib INSTANCE;
	private static boolean DEBUG;
	private static UDPLConfiguration CONFIG;
	private static Logger LOGGER = LogManager.getLogger("UDPLib");

	public UDPLib() {
		INSTANCE = this;
	}

	@Override
	public void onLoad() {
//		LOGGER = getLogger();
		ConfigurationSerialization.registerClass(AreaDataContainer.class);
		ConfigurationSerialization.registerClass(Area.class);
		ConfigurationSerialization.registerClass(ServerLocation.class);
		
		NBTUtils.registerAllNBTSerilizable();
		
		saveDefaultConfig();
		CONFIG = new UDPLConfiguration(new File(getDataFolder(), "config.yml"));
		CONFIG.reload();
		
		setDebug(CONFIG.debug);
		
		PluginUtils.saveDirectory(getInstance(), "lang", false);
		
		if (StringUtils.isNotEmpty(CONFIG.language))
			UDPLI18n.setLocale(CONFIG.language);
		
		MappingHelper.loadMapping();
		NmsHelper.loadNmsHelper();
		
		SignUtils.initSignUtils();
		PlayerUtils.initPlayerUtils();
		
		if(CONFIG.enableTest)
			TestLoader.INSTANCE.onLoad();
	}

	@Override
	public void onEnable() {
		CacheUtils.initCacheUtils();
		
		loadPluginManager();
		
		new AnnoCommandManager("udpl").addCommand(new UDPLCommand()).registerCommand();
		
		if(CONFIG.enableTest)
			TestLoader.INSTANCE.onEnable();
	}

	@Override
	public void onDisable() {
		//防止玩家未关闭界面造成刷物品
		for(Player player:Bukkit.getOnlinePlayers())
			player.closeInventory();
	}
	
	private void loadPluginManager(){
		new AnnoCommandManager("pm").addCommand(new PluginManager()).registerCommand();
	}

	public static UDPLConfiguration getUDPLConfig(){
		return CONFIG;
	}

	public static UDPLib getInstance(){
		return INSTANCE;
	}
	
	public static boolean isDebug(){
		return DEBUG;
	}
	
	public static void setDebug(boolean debug){
		DEBUG = debug;
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
}
