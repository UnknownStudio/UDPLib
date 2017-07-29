package team.unstudio.udpl.api.config;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.*;
import java.nio.charset.Charset;
import java.util.logging.Level;

/**
 * 本类使用了 cpDetector，以达到自动识别文件编码的
 * <a href="http://cpdetector.sourceforge.net/index.shtml"><i>cpDetector 官网</i></a>
 */
public class AutoCharsetYamlConfiguration extends DecodedYamlConfiguration {
    protected final static Charset defaultCharset = Charset.defaultCharset();

    /**
     * 加载Yaml 同时自动识别文件编码
     *
     * @param file
     * @return
     */
    public static AutoCharsetYamlConfiguration loadConfiguration(File file) {
        Validate.notNull(file, "File cannot be null");
        AutoCharsetYamlConfiguration config = new AutoCharsetYamlConfiguration();

        try {
            config.charset = Charset.forName(EncodingDetect.getJavaEncode(file));
        } catch (Throwable e){
            Bukkit.getLogger().log(Level.WARNING, "Cannot detect the encoding of file " + file.getPath(), e);
        }

        try {
            config.load(file);
        } catch (FileNotFoundException var3) {
        } catch (IOException var4) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, var4);
        } catch (InvalidConfigurationException var5) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, var5);
        }

        return config;
    }

    @Override
    public Charset getCharset() {
        return charset == null?defaultCharset:charset;
    }
}
