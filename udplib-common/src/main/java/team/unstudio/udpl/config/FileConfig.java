package team.unstudio.udpl.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class FileConfig implements Config{

    private final File file;
    private YamlConfiguration configuration;

    public FileConfig(File file) {
        this.file = file;
        reload();
    }

    @Override
    public Optional<Object> get(String key) {
        return Optional.ofNullable(configuration.get(key));
    }

    @Override
    public boolean reload() {
        configuration = new YamlConfiguration();
        try {
            configuration.load(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean put(String key, Object value) {
        configuration.set(key,value);
        return true;
    }

    @Override
    public boolean save() {
        try {
             configuration.save(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
