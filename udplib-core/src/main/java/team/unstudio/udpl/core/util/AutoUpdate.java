package team.unstudio.udpl.core.util;

import java.io.File;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 自动更新工具
 */
public class AutoUpdate implements Runnable {
    private final String[] downloadUrls;
    private final String fileName;
    private final String md5;
    private final String sha1;

    public AutoUpdate(String[] downloadUrls, String fileName, String md5, String sha1) {
        this.downloadUrls = downloadUrls;
        this.fileName = fileName;
        this.md5 = md5;
        this.sha1 = sha1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        File file = new File("plugins/".concat(fileName));
        for (String downloadUrl : downloadUrls) {
            throw new NotImplementedException("Haven't supported auto update");
        }
    }
}
