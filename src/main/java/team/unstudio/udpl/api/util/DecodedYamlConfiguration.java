package team.unstudio.udpl.api.util;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.Charset;

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