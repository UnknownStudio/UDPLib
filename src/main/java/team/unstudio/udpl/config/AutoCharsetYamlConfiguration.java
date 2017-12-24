package team.unstudio.udpl.config;

import org.apache.commons.lang.Validate;
import org.bukkit.configuration.InvalidConfigurationException;
import team.unstudio.udpl.core.UDPLib;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 本类使用了 cpDetector，以达到自动识别文件编码的
 * <a href="http://cpdetector.sourceforge.net/index.shtml"><i>cpDetector 官网</i></a>
 */
public class AutoCharsetYamlConfiguration extends DecodedYamlConfiguration {
    public final static Charset defaultCharset = Charset.defaultCharset();

    private AutoCharsetYamlConfiguration() {}

    /**
     * 加载Yaml 同时自动识别文件编码
     */
    @Nullable
    public static AutoCharsetYamlConfiguration loadConfiguration(@Nonnull File file){
        Validate.notNull(file, "File cannot be null");
        AutoCharsetYamlConfiguration config = new AutoCharsetYamlConfiguration();
        setConfigCharsetName(config,file);
        return loadFile(config,file);
    }
    
    private static void setConfigCharsetName(AutoCharsetYamlConfiguration config,File file) {
    	 try{
         	config.charset = Charset.forName(EncodingDetect.getJavaEncode(file));
         	if (config.charset == null) 
            	config.charset = defaultCharset;
         }catch(Exception ignored){}
    }
    
    private static AutoCharsetYamlConfiguration loadFile(AutoCharsetYamlConfiguration config,File file) {
    	try {
			config.load(file);
			return config;
		} catch (IOException | InvalidConfigurationException e) {
            UDPLib.getLog().error(e);
			return null;
		}
    }
    
}
