package team.unstudio.udpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.google.common.io.CharStreams;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;
import org.junit.runner.RunWith;

public class OnlineMappingGetter {
    public static final File PATH = new File("mappings");

    @Test
    public void getOnlineMap() throws Exception {
        String[] versions = new String[]{"1.8", "1.9", "1.10", "1.11", "1.11.1", "1.11.2", "1.12", "1.12.1", "1.12.2"};
        for (String version : versions) {
            File versionPath = new File(PATH, version);
            versionPath.mkdirs();
            String versionInfoString = get("https://hub.spigotmc.org/versions/" + version + ".json");
            System.out.println(versionInfoString);
            JsonObject versionInfo = new JsonParser().parse(versionInfoString).getAsJsonObject();
            String buildDataRef = versionInfo.get("refs").getAsJsonObject().get("BuildData").getAsString();

//				URL membersUrl = new URL(
//						"https://hub.spigotmc.org/stash/projects/SPIGOT/repos/builddata/raw/mappings/bukkit-" + version
//								+ "-members.csrg?at=" + buildDataRef);
            URL membersUrl = new URL("https://gitee.com/MouseDevelop/builddata/raw/" + buildDataRef + "/mappings/bukkit-" + version + "-members.csrg");


            HttpsURLConnection membersConn = (HttpsURLConnection) membersUrl.openConnection();
            InputStream membersInput = membersConn.getInputStream();
            FileUtils.writeLines(new File(versionPath, "members.csrg"), IOUtils.readLines(membersInput));
            membersInput.close();
        }
    }

    public static final String get(String url) throws IOException {
        URLConnection con = new URL(url).openConnection();
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        InputStreamReader r = null;
        try {
            r = new InputStreamReader(con.getInputStream());

            return CharStreams.toString(r);
        } finally {
            if (r != null) {
                r.close();
            }
        }
    }
}
