package team.unstudio.udpl.api.nbt.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import team.unstudio.udpl.api.nbt.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Mcdarc
 */
public class NBTools {

    public static ArrayList<NBTBase> transformJson(String json) {
        ArrayList<NBTBase> list = new ArrayList<>();
        JsonObject jsonObject = toJsonObject(json);
        for (Object o : jsonObject.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String s = (String) entry.getValue();
            String sign = String.valueOf(s.charAt(s.length()-1));
            String j;
            switch (sign) {
                case "b":
                    j = buildJsonRaw((String)entry.getKey(), (String)entry.getValue());
                    list.add(parseJson(j, NBTTagByte.class));
                    break;
                case "d":
                    j = buildJsonRaw((String)entry.getKey(), (String)entry.getValue());
                    list.add(parseJson(j, NBTTagDouble.class));
                    break;
                case "f":
                    j = buildJsonRaw((String)entry.getKey(), (String)entry.getValue());
                    list.add(parseJson(j, NBTTagFloat.class));
                    break;
                case "L":
                    j = buildJsonRaw((String)entry.getKey(), (String)entry.getValue());
                    list.add(parseJson(j, NBTTagLong.class));
                    break;
                case "s":
                    j = buildJsonRaw((String)entry.getKey(), (String)entry.getValue());
                    list.add(parseJson(j, NBTTagShort.class));
                    break;
                default:
                    j = buildJsonRaw((String)entry.getKey(), (String)entry.getValue());
                    list.add(parseJson(j, NBTTagString.class));
                    break;
            }
        }
        return list;
    }

    private static <T extends NBTBase> T parseJson(String json, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    private static JsonObject toJsonObject(String json) {
        return new JsonParser().parse(json).getAsJsonObject();
    }

    private static String buildJsonRaw(String key, String value) {
        return String.format("{'%s':'%s'}", key, value);
    }
}
