package team.unstudio.udpl.config;

import java.util.Map;
import java.util.Optional;

public interface Config {
    Optional<Object> get(String key);
    boolean reload();
    boolean put(String key,Object value);
    default boolean putAll(Map<String,Object> map){
        for(Map.Entry<String,Object> entry : map.entrySet()){
            if(!put(entry.getKey(),entry.getValue()))
                return false;
        }
        return true;
    }
    boolean save();
}
