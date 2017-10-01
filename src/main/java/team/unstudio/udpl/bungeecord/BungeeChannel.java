package team.unstudio.udpl.bungeecord;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Documentation copied from https://www.spigotmc.org/wiki/bukkit-bungee-plugin-messaging-channel/
 *
 * @author leonardosnt (leonardosnt@outlook.com)
 * @author trychen
 */
public class BungeeChannel {
    /**
     * the registered bungee channels
     */
    private static WeakHashMap<Plugin, BungeeChannel> registeredInstances = new WeakHashMap<>();

    /**
     * the plugin message listener
     */
    private final PluginMessageListener messageListener;

    /**
     * the user of this bungee channel
     */
    private final Plugin plugin;
    private final Map<String, Queue<CompletableFuture<?>>> callbackMap = new HashMap<>();

    private Map<String, ForwardConsumer> forwardListeners;
    private ForwardConsumer globalForwardListener;

    /**
     * Get or create new BungeeChannelApi instance
     */
    public synchronized static BungeeChannel of(Plugin plugin) {
        return registeredInstances.compute(plugin, (k, v) -> {
            if (v == null) v = new BungeeChannel(plugin);
            return v;
        });
    }

    private BungeeChannel(Plugin plugin) {
        this.plugin = plugin;

        // Prevent dev's from registering multiple channel listeners
        synchronized (registeredInstances) {
            registeredInstances.compute(plugin, (k, oldInstance) -> {
                if (oldInstance != null) oldInstance.unregister();
                return this;
            });
        }

        this.messageListener = this::onPluginMessageReceived;

        Messenger messenger = Bukkit.getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(plugin, "BungeeCord");
        messenger.registerIncomingPluginChannel(plugin, "BungeeCord", messageListener);

        registerForwardListener("Teleport", this::teleportReceived);
    }

    /**
     * Unregister message channels.
     */
    public void unregister() {
        Messenger messenger = Bukkit.getServer().getMessenger();
        messenger.unregisterIncomingPluginChannel(plugin, "BungeeCord", messageListener);
        messenger.unregisterOutgoingPluginChannel(plugin);
        callbackMap.clear();
    }

    private BiFunction<String, Queue<CompletableFuture<?>>, Queue<CompletableFuture<?>>> computeQueueValue(CompletableFuture<?> queueValue) {
        return (key, value) -> {
            if (value == null) value = new ArrayDeque<>();
            value.add(queueValue);
            return value;
        };
    }

    private String cachedServer;

    public String getCachedServer() {
        if (cachedServer == null) {
            cachedServer = getServer().join();
        }
        return cachedServer;
    }

    /**
     * get the first player of now server
     */
    public static Player getFirstPlayer() {
        // If you are running old bukkit versions, you need to change this to
        // Player ret = Bukkit.getOnlinePlayers()[0];
        Player ret = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);

        if (ret == null) {
            throw new IllegalArgumentException("Bungee Messaging Api requires at least one player online.");
        }

        return ret;
    }

    /**
     * Send a custom plugin message to specific player.
     *
     * @param playerName  the name of the player to send to.
     * @param channelName Subchannel for plugin usage.
     * @param dataWriter  data output consumer.
     * @throws IllegalArgumentException if there is no players online.
     */
    public void forwardToPlayer(String playerName, String channelName, Consumer<ByteArrayDataOutput> dataWriter) {
        Player player = getFirstPlayer();

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("ForwardToPlayer");
        output.writeUTF(playerName);
        output.writeUTF(channelName);

        ByteArrayDataOutput data = ByteStreams.newDataOutput();
        dataWriter.accept(data);
        byte[] dataArray = data.toByteArray();
        output.writeShort(dataArray.length);
        output.write(dataArray);

        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
    }

    /**
     * Send a custom plugin message to said server. This is one of the most useful channels ever.
     * <b>Remember, the sending and receiving server(s) need to have a player online.</b>
     *
     * @param server      the name of the server to send to,
     *                    ALL to send to every server (except the one sending the plugin message),
     *                    or ONLINE to send to every server that's online (except the one sending the plugin message).
     * @param channelName Subchannel for plugin usage.
     * @param dataWriter  data output consumer.
     * @throws IllegalArgumentException if there is no players online.
     */
    public void forward(String server, String channelName, Consumer<ByteArrayDataOutput> dataWriter) {
        Player player = getFirstPlayer();

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Forward");
        output.writeUTF(server);
        output.writeUTF(channelName);

        ByteArrayDataOutput data = ByteStreams.newDataOutput();
        dataWriter.accept(data);
        byte[] dataArray = data.toByteArray();
        output.writeShort(dataArray.length);
        output.write(dataArray);

        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
    }

    /**
     * set global forward listener. if has old one, it will be replaced
     *
     * @param globalListener new listener
     */
    public void registerForwardListener(ForwardConsumer globalListener) {
        this.globalForwardListener = globalListener;
    }

    /**
     * add new listener listening targetChannel
     *
     * @param targetChannel your sub channel
     */
    public void registerForwardListener(String targetChannel, ForwardConsumer listener) {
        if (forwardListeners == null) {
            forwardListeners = new HashMap<>();
        }
        synchronized (forwardListeners) {
            forwardListeners.put(targetChannel, listener);
        }
    }

    /**
     * on receiving
     */
    private void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equalsIgnoreCase("BungeeCord")) return;
        ByteArrayDataInput input = ByteStreams.newDataInput(message);

        String subchannel = input.readUTF();

        synchronized (callbackMap) {
            // check if arg-required message
            if (subchannel.equals("PlayerCount") || subchannel.equals("PlayerList") ||
                    subchannel.equals("UUIDOther") || subchannel.equals("ServerIP")) {
                // read the server/player name
                String identifier = input.readUTF();

                // get the callback and poll the future
                CompletableFuture<?> callback = callbackMap.get(subchannel + "-" + identifier).poll();

                if (callback == null) return;

                try {
                    if (subchannel.equals("PlayerCount"))
                        ((CompletableFuture<Integer>) callback).complete(Integer.valueOf(input.readInt()));

                    else if (subchannel.equals("PlayerList"))
                        ((CompletableFuture<List<String>>) callback).complete(Arrays.asList(input.readUTF().split(", ")));

                    else if (subchannel.equals("UUIDOther"))
                        ((CompletableFuture<String>) callback).complete(input.readUTF());

                    else if (subchannel.equals("ServerIP")) {
                        String ip = input.readUTF();
                        int port = input.readUnsignedShort();
                        ((CompletableFuture<InetSocketAddress>) callback).complete(new InetSocketAddress(ip, port));
                    }
                } catch (Exception ex) {
                    callback.completeExceptionally(ex);
                }

                // break next
                return;
            }

            // check forward message
            Queue<CompletableFuture<?>> callbacks = callbackMap.get(subchannel);

            if (callbacks == null) {
                short dataLength = input.readShort();
                byte[] data = new byte[dataLength];
                input.readFully(data);

                // accepting global forward listener
                if (globalForwardListener != null) globalForwardListener.accept(subchannel, player, data);

                if (forwardListeners == null) return;
                synchronized (forwardListeners) {
                    ForwardConsumer listener = forwardListeners.get(subchannel);
                    if (listener != null) {
                        listener.accept(subchannel, player, data);
                    }
                }

                return;
            }

            CompletableFuture<?> callback = callbacks.poll();

            if (callback == null) return;

            try {
                switch (subchannel) {
                    case "GetServers":
                        ((CompletableFuture<List<String>>) callback).complete(Arrays.asList(input.readUTF().split(", ")));
                        break;
                    case "GetServer":
                        ((CompletableFuture<String>) callback).complete(input.readUTF());

                    case "UUID":
                        ((CompletableFuture<String>) callback).complete(input.readUTF());
                        break;
                    case "IP": {
                        String ip = input.readUTF();
                        int port = input.readInt();
                        ((CompletableFuture<InetSocketAddress>) callback).complete(new InetSocketAddress(ip, port));
                        break;
                    }
                }
            } catch (Exception ex) {
                callback.completeExceptionally(ex);
            }
        }
    }

    /**
     * Get the amount of players on a certain server, or on ALL the servers.
     *
     * @param serverName the server name of the server to get the player count of, or ALL to get the global player count
     * @return A {@link CompletableFuture} that, when completed, will return
     *         the amount of players on a certain server, or on ALL the servers.
     * @throws IllegalArgumentException if there is no players online.
     */
    public CompletableFuture<Integer> getPlayerCount(String serverName) {
        Player player = getFirstPlayer();
        CompletableFuture<Integer> future = new CompletableFuture<>();

        synchronized (callbackMap) {
            callbackMap.compute("PlayerCount-" + serverName, this.computeQueueValue(future));
        }

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("PlayerCount");
        output.writeUTF(serverName);
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
        return future;
    }

    /**
     * Get a list of players connected on a certain server, or on ALL the servers.
     *
     * @param serverName the name of the server to get the list of connected players, or ALL for global online player list
     * @return A {@link CompletableFuture} that, when completed, will return a
     *         list of players connected on a certain server, or on ALL the servers.
     * @throws IllegalArgumentException if there is no players online.
     */
    public CompletableFuture<List<String>> getPlayerList(String serverName) {
        Player player = getFirstPlayer();
        CompletableFuture<List<String>> future = new CompletableFuture<>();

        synchronized (callbackMap) {
            callbackMap.compute("PlayerList-" + serverName, this.computeQueueValue(future));
        }

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("PlayerList");
        output.writeUTF(serverName);
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
        return future;
    }

    /**
     * Get a list of server name strings, as defined in BungeeCord's config.yml.
     *
     * @return A {@link CompletableFuture} that, when completed, will return a
     *         list of server name strings, as defined in BungeeCord's config.yml.
     * @throws IllegalArgumentException if there is no players online.
     */
    public CompletableFuture<List<String>> getServers() {
        Player player = getFirstPlayer();
        CompletableFuture<List<String>> future = new CompletableFuture<>();

        synchronized (callbackMap) {
            callbackMap.compute("GetServers", this.computeQueueValue(future));
        }

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("GetServers");
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
        return future;
    }

    /**
     * Connects a player to said subserver.
     *
     * @param player the player you want to teleport.
     * @param serverName the name of server to connect to, as defined in BungeeCord config.yml.
     */
    public void connect(Player player, String serverName) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF(serverName);
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
    }

    /**
     * Connect a named player to said subserver.
     *
     * @param playerName name of the player to teleport.
     * @param server name of server to connect to, as defined in BungeeCord config.yml.
     * @throws IllegalArgumentException if there is no players online.
     */
    public void connectOther(String playerName, String server) {
        Player player = getFirstPlayer();
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("ConnectOther");
        output.writeUTF(playerName);
        output.writeUTF(server);

        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
    }

    /**
     * Get the (real) IP of a player.
     *
     * @param player The player you wish to get the IP of.
     * @return A {@link CompletableFuture} that, when completed, will return the (real) IP of {@code player}.
     */
    public CompletableFuture<InetSocketAddress> getIp(Player player) {
        CompletableFuture<InetSocketAddress> future = new CompletableFuture<>();

        synchronized (callbackMap) {
            callbackMap.compute("IP", this.computeQueueValue(future));
        }

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("IP");
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
        return future;
    }

    /**
     * Send a message (as in, a chat message) to the specified player.
     *
     * @param playerName the name of the player to send the chat message.
     * @param message the message to send to the player.
     * @throws IllegalArgumentException if there is no players online.
     */
    public void sendMessage(String playerName, String message) {
        Player player = getFirstPlayer();
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("Message");
        output.writeUTF(playerName);
        output.writeUTF(message);
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
    }

    /**
     * Get this server's name, as defined in BungeeCord's config.yml
     *
     * @return A {@link CompletableFuture} that, when completed, will return
     *         the {@code server's} name, as defined in BungeeCord's config.yml.
     * @throws IllegalArgumentException if there is no players online.
     */
    public CompletableFuture<String> getServer() {
        Player player = getFirstPlayer();
        CompletableFuture<String> future = new CompletableFuture<>();

        synchronized (callbackMap) {
            callbackMap.compute("GetServer", this.computeQueueValue(future));
        }

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("GetServer");
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
        return future;
    }

    /**
     * Request the UUID of this player.
     *
     * @param player The player whose UUID you requested.
     * @return A {@link CompletableFuture} that, when completed, will return the UUID of {@code player}.
     */
    public CompletableFuture<String> getUUID(Player player) {
        CompletableFuture<String> future = new CompletableFuture<>();

        synchronized (callbackMap) {
            callbackMap.compute("UUID", this.computeQueueValue(future));
        }

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("UUID");
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
        return future;
    }

    /**
     * Request the UUID of any player connected to the BungeeCord proxy.
     *
     * @param playerName the name of the player whose UUID you would like.
     * @return A {@link CompletableFuture} that, when completed, will return the UUID of {@code playerName}.
     * @throws IllegalArgumentException if there is no players online.
     */
    public CompletableFuture<String> getUUID(String playerName) {
        Player player = getFirstPlayer();
        CompletableFuture<String> future = new CompletableFuture<>();

        synchronized (callbackMap) {
            callbackMap.compute("UUIDOther-" + playerName, this.computeQueueValue(future));
        }

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("UUIDOther");
        output.writeUTF(playerName);
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
        return future;
    }

    /**
     * Request the IP of any server on this proxy.
     *
     * @param serverName the name of the server.
     * @return A {@link CompletableFuture} that, when completed, will return the requested ip.
     * @throws IllegalArgumentException if there is no players online.
     */
    public CompletableFuture<InetSocketAddress> getServerIp(String serverName) {
        Player player = getFirstPlayer();
        CompletableFuture<InetSocketAddress> future = new CompletableFuture<>();

        synchronized (callbackMap) {
            callbackMap.compute("ServerIP-" + serverName, this.computeQueueValue(future));
        }

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("ServerIP");
        output.writeUTF(serverName);
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
        return future;
    }

    /**
     * Kick any player on this proxy.
     *
     * @param playerName the name of the player.
     * @param kickMessage the reason the player is kicked with.
     * @throws IllegalArgumentException if there is no players online.
     */
    public void kickPlayer(String playerName, String kickMessage) {
        Player player = getFirstPlayer();
        CompletableFuture<InetSocketAddress> future = new CompletableFuture<>();

        synchronized (callbackMap) {
            callbackMap.compute("KickPlayer", this.computeQueueValue(future));
        }

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("KickPlayer");
        output.writeUTF(playerName);
        output.writeUTF(kickMessage);
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
    }

    /**
     * send player to somewhere
     *
     * @param player the sending player.
     * @param serverLocation the serverLocation where player send to.
     * @throws IllegalArgumentException if there is no players online.
     */
    public void teleport(Player player, ServerLocation serverLocation) {
        // teleport to this server
        if (serverLocation.getServer().equals(getCachedServer())) {
            player.teleport(serverLocation.toLocation());
            return;
        }

        forward(serverLocation.getServer(), "Teleport", data -> {
            data.writeUTF(player.getName());
            data.writeUTF(serverLocation.toString());
        });
    }

    /**
     * while teleport forward received
     */
    private void teleportReceived(String channel, Player player, byte[] data){
        ByteArrayDataInput dataInput = ByteStreams.newDataInput(data);
        String playerName = dataInput.readUTF();
        ServerLocation serverLocation = ServerLocation.deserialize(dataInput.readUTF());
    }
}
