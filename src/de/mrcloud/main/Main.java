package de.mrcloud.main;

import de.mrcloud.listeners.CommandListener;
import de.mrcloud.listeners.FileListener;
import de.mrcloud.listeners.ReactionListener;
import de.mrcloud.listeners.STATIC;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

public class Main {

    public ShardManager shardMan;
    FileListener fileListener = new FileListener();

    public Main() throws LoginException {


        String token = STATIC.TOKEN;
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.create(token, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_BANS, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_INVITES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGE_TYPING, GatewayIntent.DIRECT_MESSAGE_REACTIONS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MESSAGE_REACTIONS);
        builder.addEventListeners(new CommandListener());
        builder.addEventListeners(new FileListener());
        builder.addEventListeners(new ReactionListener());
        builder.setActivity(Activity.watching("your Configs -- By Mr Cloud#7895--"));
        builder.setMaxReconnectDelay(32);
        builder.setAutoReconnect(true);
        builder.setRequestTimeoutRetry(true);
        shardMan = builder.build();

        if (!shardMan.getGuildsByName("ReisMiner Community", true).isEmpty()) {
            System.out.println("Du hast den richtigen Key verwendet!");

            if (shardMan.getGuildsByName("ReisMiner Community", true).get(0).getTextChannelById(fileListener.ReisMinerChannelId) != null) {
                System.out.println("Du hast auch die richtige ID verwendet :D");
            }
        }
    }

    public static void main(String[] args) throws LoginException {
        new Main();

    }

    public void TurnOff() {
        if (shardMan != null) {
            shardMan.setStatus(OnlineStatus.OFFLINE);
            shardMan.setActivity(Activity.listening("offline"));
            shardMan.shutdown();
            System.out.println("Bot wird heruntergefahren");
            System.exit(1);
        }
    }
}
