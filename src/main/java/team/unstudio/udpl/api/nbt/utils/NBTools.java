package team.unstudio.udpl.api.nbt.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import team.unstudio.udpl.api.nbt.NBTBase;

import java.util.ArrayList;

/**
 * @author Mcdarc
 */
public class NBTools {

    public static ArrayList<NBTBase> transformJson(String json) {
        return null;
    }

    private <T extends NBTBase> T parseJson(String json, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    private JsonObject toJsonObject(String json) {
        return new JsonParser().parse(json).getAsJsonObject();
    }
}
