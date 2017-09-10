package team.unstudio.udpl.config;

import org.apache.commons.lang.Validate;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.*;
import java.nio.charset.Charset;

/**
 * 本类使用了 cpDetector，以达到自动识别文件编码的
 * <a href="http://cpdetector.sourceforge.net/index.shtml"><i>cpDetector 官网</i></a>
 */
public class AutoCharsetYamlConfiguration extends DecodedYamlConfiguration {
    public final static Charset defaultCharset = Charset.defaultCharset();

    /**
     * 加载Yaml 同时自动识别文件编码
     *
     * @param file
     * @return
     * @throws InvalidConfigurationException 
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public static AutoCharsetYamlConfiguration loadConfiguration(File file){
        Validate.notNull(file, "File cannot be null");
        AutoCharsetYamlConfiguration config = new AutoCharsetYamlConfiguration();

        try{
        	config.charset = Charset.forName(EncodingDetect.getJavaEncode(file));
        }catch(Exception e){}

        if (config.charset == null) 
        	config.charset = defaultCharset;

        try {
			config.load(file);
			return config;
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			return null;
		}
    }
}
