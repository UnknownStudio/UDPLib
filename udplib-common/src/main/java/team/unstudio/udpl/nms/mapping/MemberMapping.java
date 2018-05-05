package team.unstudio.udpl.nms.mapping;

import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import team.unstudio.udpl.exception.MemberMappingException;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Load members mappings data like <a href="https://hub.spigotmc.org/stash/projects/SPIGOT/repos/builddata/browse/mappings/">this</a>
 */
public final class MemberMapping {
    /**
     * map structure:
     * className -> {
     *      obfMethodName + signature -> deobfMethodName,
     *      obfFieldName -> deobfFieldName
     * },
     *
     * e.g.
     * Advancement -> {
     *     f()Ljava/util/Map; -> getCriteria,
     * },
     * AdvancementDataPlayer -> {
     *     f -> data
     * }
     */
    protected final Map<String, Map<String, String>> obf2Deobf = Maps.newHashMap();

    /**
     * map structure:
     * className -> {
     *      deobfMethodName + signature -> obfMethodName,
     *      deobfFieldName -> obfFieldName
     * },
     *
     * e.g.
     * Advancement -> {
     *     getCriteria -> f()Ljava/util/Map;,
     * },
     * AdvancementDataPlayer -> {
     *     data -> f
     * }
     */
    protected final Map<String, Map<String, String>> deobf2Obf = Maps.newHashMap();

    /**
     * see {@link MemberMapping#load(List)}
     */
    public MemberMapping(List<String> lines) {
        load(lines);
    }

    /**
     * load mappings data
     *
     * @param lines mappings data,
     *              each line contain a mapping data, e.g.
     *              for method mapping data: "Advancement f ()Ljava/util/Map; getCriteria"
     *              for field mapping data: "AdvancementDataPlayer f data"
     */
    protected void load(List<String> lines) {
        for (String line : lines) {
            // resolve comment started with '#'
            if (line.isEmpty() || line.startsWith("#")) continue;

            String[] args = line.split(" ");
            if (args.length == 4) {
                loadMethod(args);
            } else if (args.length == 3) {
                loadField(args);
            }
        }
    }

    /**
     * Load Method, e.g. "Advancement f ()Ljava/util/Map; getCriteria"
     * @param data array like ["Advancement", "f", "()Ljava/util/Map;", "getCriteria"]
     */
    protected void loadMethod(String[] data){
        if (data.length != 4) throw new IllegalArgumentException("array length must be 4");
        String clazz           = data[0]; // Advancement
        String obfMethodName   = data[1]; // f
        String signature       = data[2]; // ()Ljava/util/Map;
        String deobfMethodName = data[3]; // getCriteria

        // put data into obf 2 deobf
        if (!obf2Deobf.containsKey(clazz)) obf2Deobf.put(clazz, Maps.newHashMap());
        obf2Deobf.get(clazz).put(obfMethodName + signature, deobfMethodName);

        // put data into deobf 2 obf
        if (!deobf2Obf.containsKey(clazz)) deobf2Obf.put(clazz, Maps.newHashMap());
        deobf2Obf.get(clazz).put(deobfMethodName + signature, obfMethodName);
    }

    /**
     * Load Field, e.g. "AdvancementDataPlayer f data"
     * @param data array like ["AdvancementDataPlayer", "f", "data"]
     */
    protected void loadField(String[] data){
        if (data.length != 3) throw new IllegalArgumentException("array length must be 3");

        String clazz           = data[0]; // AdvancementDataPlayer
        String obfFieldName   = data[1]; // f
        String deobfFieldName = data[2]; // data

        if (!obf2Deobf.containsKey(clazz)) obf2Deobf.put(clazz, Maps.newHashMap());
        obf2Deobf.get(clazz).put(obfFieldName, deobfFieldName);

        if (!deobf2Obf.containsKey(clazz)) deobf2Obf.put(clazz, Maps.newHashMap());
        deobf2Obf.get(clazz).put(deobfFieldName, obfFieldName);
    }

    public String getDeobf(String className, String obfName, String desc) {
        if (!containObf(className, obfName, desc))
            return obfName;

        return obf2Deobf.get(className).get(obfName.concat(desc));
    }

    public String getObf(String className, String deobfName, String desc) {
        if (!containDeobf(className, deobfName, desc))
            return deobfName;

        return deobf2Obf.get(className).get(deobfName.concat(desc));
    }

    public boolean containObf(String className, String obfName, String desc) {
        if (!obf2Deobf.containsKey(className))
            return false;

        return obf2Deobf.get(className).containsKey(obfName.concat(desc));
    }

    public boolean containDeobf(String className, String deobfName, String desc) {
        if (!deobf2Obf.containsKey(className))
            return false;

        return deobf2Obf.get(className).containsKey(deobfName.concat(desc));
    }

    /**
     * load member mappings data from Stream
     *
     * @param inputStream stream with strings
     */
    public static MemberMapping fromInputSteam(@Nonnull InputStream inputStream) throws MemberMappingException {
        List<String> lines;

        try {
            lines = IOUtils.readLines(inputStream);
        } catch (NullPointerException e) {
            throw new MemberMappingException("Couldn't find member mapping data from the inputted steam", e);
        } catch (IOException e) {
            throw new MemberMappingException("Couldn't load member mapping data from the inputted steam", e);
        }

        return new MemberMapping(lines);
    }

    /**
     * load member mappings data from file
     *
     * @param file file to load
     * @throws MemberMappingException If {@code file} doesn't exist
     */
    public static MemberMapping fromFile(@Nonnull File file) throws MemberMappingException {
        List<String> lines;

        try {
            lines = FileUtils.readLines(file);
        } catch (NullPointerException e) {
            throw new MemberMappingException("Couldn't find member mapping data from file " + file.getPath(), e);
        } catch (IOException e) {
            throw new MemberMappingException("Couldn't load member mapping data from file " + file.getPath(), e);
        }

        return new MemberMapping(lines);
    }

    /**
     * load certain version of member mappings data from build-in data (/mappings/{@code version}/members.csrg)
     *
     * @param version file to load
     * @throws MemberMappingException If resource (/mappings/{@code version}/members.csrg) doesn't exist
     */
    public static MemberMapping fromClassLoader(@Nonnull String version) throws MemberMappingException {
        return fromClassLoader(MemberMapping.class.getClassLoader(), "mappings/" + version + "/members.csrg");
    }
    /**
     * load certain version of member mappings data from build-in data (/mappings/{@code version}/members.csrg)
     *
     * @param classLoader ClassLoader to get resource
     * @param path path of file to load
     * @throws MemberMappingException If resource (/mappings/{@code version}/members.csrg) doesn't exist
     */
    public static MemberMapping fromClassLoader(@Nonnull ClassLoader classLoader, @Nonnull String path) throws MemberMappingException {
        InputStream stream = classLoader.getResourceAsStream(path);
        if (stream == null) throw new MemberMappingException("Couldn't find file at path " + path);
        return fromInputSteam(stream);
    }
}
