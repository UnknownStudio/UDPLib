package team.unstudio.udpl.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * References:
 * http://wiki.vg/Server_List_Ping
 * https://github.com/jamietech/MinecraftServerPing
 */
public interface ServerListPingUtils {
    Executor asyncPool = ForkJoinPool.getCommonPoolParallelism() > 1 ? ForkJoinPool.commonPool() : command -> new Thread(command).start();

    /**
     * Fetches a {@link Reply} for the supplied hostname asynchronously.
     * <b>Assumed timeout of 2s and port of 25565.</b>
     *
     * @param hostname - a valid String hostname
     * @return {@link Reply}
     */
    static Future<Reply> fetchAsynchronously(final String hostname) {
        return fetchAsynchronously(Options.of(hostname));
    }

    /**
     * Fetches a {@link Reply} for the supplied options asynchronously.
     *
     * @param options - a filled instance of {@link Options}
     * @return {@link Reply}
     */
    static Future<Reply> fetchAsynchronously(final Options options) {
        final CompletableFuture<Reply> future = new CompletableFuture<>();

        asyncPool.execute(() -> {
            try {
                Reply reply = fetch(options);
                future.complete(reply);
            } catch (IOException e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    /**
     * Fetches a {@link Reply} for the supplied hostname.
     * <b>Assumed timeout of 2s and port of 25565.</b>
     *
     * @param hostname - a valid String hostname
     * @return {@link Reply}
     * @throws IOException
     */
    static Reply fetch(final String hostname) throws IOException {
        return fetch(Options.of(hostname));
    }

    /**
     * Fetches a {@link Reply} for the supplied options.
     *
     * @param options - a filled instance of {@link Options}
     * @return {@link Reply}
     * @throws IOException
     */
    static Reply fetch(final Options options) throws IOException, AssertionError {
        String json = fetchRawJson(options);
        JsonElement element = new JsonParser().parse(json);
        if (element.getAsJsonObject().get("description").isJsonObject()) {
            JsonObject description = element.getAsJsonObject().get("description").getAsJsonObject();
            json = json.replace(description.toString(), '"' + description.get("text").getAsString() + '"');
        }
        return new Gson().fromJson(json, Reply.class);
    }

    static String fetchRawJson(final Options options) throws IOException, AssertionError {
        assert options.getHostname() == null : "hostname cannot be null.";
        assert options.getPort() < 0 || options.getPort() > 65535 : "invalid server port.";

        final Socket socket = new Socket();
        socket.connect(new InetSocketAddress(options.getHostname(), options.getPort()), options.getTimeout());

        final DataInputStream in = new DataInputStream(socket.getInputStream());
        final DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        //> Handshake

        ByteArrayOutputStream handshake_bytes = new ByteArrayOutputStream();
        DataOutputStream handshake = new DataOutputStream(handshake_bytes);

        handshake.writeByte(PACKET_HANDSHAKE);
        writeVarInt(handshake, PROTOCOL_VERSION);
        writeVarInt(handshake, options.getHostname().length());
        handshake.writeBytes(options.getHostname());
        handshake.writeShort(options.getPort());
        writeVarInt(handshake, STATUS_HANDSHAKE);

        writeVarInt(out, handshake_bytes.size());
        out.write(handshake_bytes.toByteArray());

        //> Status request

        out.writeByte(0x01); // Size of packet
        out.writeByte(PACKET_STATUS_REQUEST);

        //< Status response

        readVarInt(in); // Size
        int id = readVarInt(in);

        io(id == -1, "Server prematurely ended stream.");
        io(id != PACKET_STATUS_REQUEST, "Server returned invalid packet.");

        int length = readVarInt(in);
        io(length == -1, "Server prematurely ended stream.");
        io(length == 0, "Server returned unexpected value.");

        byte[] data = new byte[length];
        in.readFully(data);
        String json = new String(data, options.getCharset());

        //> Ping

        out.writeByte(0x09); // Size of packet
        out.writeByte(PACKET_PING);
        out.writeLong(System.currentTimeMillis());

        //< Ping

        readVarInt(in); // Size
        id = readVarInt(in);
        io(id == -1, "Server prematurely ended stream.");
        io(id != PACKET_PING, "Server returned invalid packet.");

        // Close

        handshake.close();
        handshake_bytes.close();
        out.close();
        in.close();
        socket.close();

        return json;
    }

    class Reply {
        private String description;
        private Players players;
        private Version version;
        private String favicon;

        /**
         * @return the MOTD
         */
        public String getDescription() {
            return this.description;
        }

        /**
         * @return @{link Players}
         */
        public Players getPlayers() {
            return this.players;
        }

        /**
         * @return @{link Version}
         */
        public Version getVersion() {
            return this.version;
        }

        /**
         * @return Base64 encoded favicon image
         */
        public String getFavicon() {
            return this.favicon;
        }

        class Description {

        }

        public class Players {
            private int max;
            private int online;
            private List<Player> sample;

            /**
             * @return Maximum player count
             */
            public int getMax() {
                return this.max;
            }

            /**
             * @return Online player count
             */
            public int getOnline() {
                return this.online;
            }

            /**
             * @return List of some players (if any) specified by server
             */
            public List<Player> getSample() {
                return this.sample;
            }
        }

        public class Player {
            private String name;
            private String id;

            /**
             * @return Name of player
             */
            public String getName() {
                return this.name;
            }

            /**
             * @return Unknown
             */
            public String getId() {
                return this.id;
            }

        }

        public class Version {
            private String name;
            private int protocol;

            /**
             * @return Version name (ex: 13w41a)
             */
            public String getName() {
                return this.name;
            }

            /**
             * @return Protocol version
             */
            public int getProtocol() {
                return this.protocol;
            }
        }
    }

    class Options {
        private String hostname;
        private int port = 25565;
        private int timeout = 2000; // ms
        private String charset = "UTF-8";

        public Options(String hostName) {
            this.hostname = hostName;
        }

        public static Options of(String hostName) {
            return new Options(hostName);
        }

        public Options setHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Options setPort(int port) {
            this.port = port;
            return this;
        }

        public Options setTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Options setCharset(String charset) {
            this.charset = charset;
            return this;
        }

        public String getHostname() {
            return this.hostname;
        }

        public int getPort() {
            return this.port;
        }

        public int getTimeout() {
            return this.timeout;
        }

        public String getCharset() {
            return this.charset;
        }
    }


    byte PACKET_HANDSHAKE = 0x00, PACKET_STATUS_REQUEST = 0x00, PACKET_PING = 0x01;
    int PROTOCOL_VERSION = 4;
    int STATUS_HANDSHAKE = 1;

    static void io(final boolean b, final String m) throws IOException {
        if (b) {
            throw new IOException(m);
        }
    }

    /**
     * @author thinkofdeath
     * See: https://gist.github.com/thinkofdeath/e975ddee04e9c87faf22
     */
    static int readVarInt(DataInputStream in) throws IOException {
        int i = 0;
        int j = 0;
        while (true) {
            int k = in.readByte();

            i |= (k & 0x7F) << j++ * 7;

            if (j > 5)
                throw new RuntimeException("VarInt too big");

            if ((k & 0x80) != 128)
                break;
        }

        return i;
    }

    /**
     * @throws IOException
     * @author thinkofdeath
     * See: https://gist.github.com/thinkofdeath/e975ddee04e9c87faf22
     */
    static void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
        while (true) {
            if ((paramInt & 0xFFFFFF80) == 0) {
                out.writeByte(paramInt);
                return;
            }

            out.writeByte(paramInt & 0x7F | 0x80);
            paramInt >>>= 7;
        }
    }
}
