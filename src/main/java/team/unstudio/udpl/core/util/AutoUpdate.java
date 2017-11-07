package team.unstudio.udpl.core.util;

import java.io.File;

import org.bukkit.scheduler.BukkitRunnable;

public class AutoUpdate extends BukkitRunnable{

	private String[] downloadUrls;
	private String fileName;
	private String md5;
	private String sha1;
	
	public AutoUpdate(String[] downloadUrls, String fileName, String md5, String sha1) {
		this.downloadUrls = downloadUrls;
		this.fileName = fileName;
		this.md5 = md5;
		this.sha1 = sha1;
	}
	
	@Override
	public void run() {
		File file = new File("plugins/".concat(fileName));
		for(String downloadUrl : downloadUrls){
			
		}
	}

}
