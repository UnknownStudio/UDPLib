package team.unstudio.udpl.core.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.apache.commons.io.IOUtils;
import org.bukkit.scheduler.BukkitRunnable;
import team.unstudio.udpl.core.UDPLI18n;
import team.unstudio.udpl.core.UDPLib;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class VersionCheck extends BukkitRunnable{
	
	private static final String VERSION_CHECK_GITEE_URL = "http://mousedevelop.gitee.io/webpage/plugins/UDPLib.json";
	private static final String VERSION_CHECK_GITHUB_URL = "https://mousesrc.github.io/plugins/UDPLib.json";
	
	private boolean success = false;
	private String latestVersion;
	private String downloadPageUrl;
	private String[] downloadUrls;
	private String fileName;
	private String md5;
	private String sha1;
	
	@Override
	public void run() {
		JsonObject jsonObject = null;
		try {
			jsonObject = getHttpJson(VERSION_CHECK_GITEE_URL).getAsJsonObject();
		} catch (IOException e) {
			UDPLib.debug(e);
			UDPLib.getLog().warn("Version check failure.");
		}
		
		if(jsonObject == null){
			success = false;
			return;
		}
		
		latestVersion = jsonObject.get("latestVersion").getAsString();
		downloadPageUrl = jsonObject.get("downloadPageUrl").getAsString();

		JsonArray jsonDownloadUrls = jsonObject.get("downloadUrls").getAsJsonArray();
		downloadUrls = new String[jsonDownloadUrls.size()];
		for (int i = 0, size = jsonDownloadUrls.size(); i < size; i++)
			downloadUrls[i] = jsonDownloadUrls.get(i).getAsString();

		fileName = jsonObject.get("fileName").getAsString();
		md5 = jsonObject.get("md5").getAsString();
		sha1 = jsonObject.get("sha1").getAsString();

		success = true;

		if(!latestVersion.equals(UDPLib.getInstance().getDescription().getVersion()))
			UDPLib.getLog().info(String.format(UDPLI18n.format("plugin.found_new_version"),latestVersion,downloadPageUrl));
	}

	public String getLatestVersion() {
		return latestVersion;
	}

	public String getDownloadPageUrl() {
		return downloadPageUrl;
	}

	public String[] getDownloadUrls() {
		return downloadUrls;
	}

	public String getFileName() {
		return fileName;
	}

	public String getMd5() {
		return md5;
	}

	public String getSha1() {
		return sha1;
	}

	public boolean isSuccess() {
		return success;
	}
	
	private static JsonElement getHttpJson(String value) throws IOException{
		JsonReader jsonReader = null;
		try{
			URL url = new URL(value);
			URLConnection conn = url.openConnection();
			InputStream input = conn.getInputStream();
			jsonReader = new JsonReader(new InputStreamReader(input));
			return new JsonParser().parse(jsonReader);
		} finally {
			IOUtils.closeQuietly(jsonReader);
		}
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(getHttpJson(VERSION_CHECK_GITEE_URL));
		System.out.println(getHttpJson(VERSION_CHECK_GITHUB_URL));
	}
}
