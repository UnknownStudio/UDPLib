package team.unstudio.udpl.api.config;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.Charset;
import java.util.logging.Level;

/**
 * 可以指定编码的 YamlConfiguration， 如不指定编码，默认为UTF-8
 */
public class DecodedYamlConfiguration extends YamlConfiguration {
    /**
     * 文件编码
     */
    protected Charset charset;

    public DecodedYamlConfiguration() {
        this(Charset.forName("UTF-8"));
    }

    public DecodedYamlConfiguration(Charset charset) {
        this.charset = charset;
    }

    public static DecodedYamlConfiguration loadConfiguration(File file) {
        Validate.notNull(file, "File cannot be null");
        DecodedYamlConfiguration config = new DecodedYamlConfiguration();

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
    public void save(File file) throws IOException {
        file.mkdirs();
        String data = this.saveToString();
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), getCharset());
        try {
            writer.write(data);
        } finally {
            writer.close();
        }
    }

    @Override
    public void load(File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        this.load(new InputStreamReader(new FileInputStream(file), getCharset()));
    }

    @Override
    @Deprecated
    public void load(InputStream stream) throws IOException, InvalidConfigurationException {
        this.load(new InputStreamReader(stream, getCharset()));
    }

    public Charset getCharset() {
        return charset;
    }
}