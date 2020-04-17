package de.mrcloud.listeners;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class ShitResponder extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {
        super.onGuildMessageReceived(e);

        User author = e.getAuthor();
        Message message = e.getMessage();
        String rawMsg = message.getContentRaw();
        TextChannel txtChannel = e.getChannel();
        Guild server = e.getGuild();

        if(rawMsg.equalsIgnoreCase("Du bist dumm")) {
            txtChannel.sendMessage("Nö").queue();
        } else if(rawMsg.equalsIgnoreCase("Do you have " + "\\w*" )) {
txtChannel.sendMessage("You can download configs in " + server.getTextChannelsByName("config-download",true).get(0) + "by using &configs").queue();
        } else if(rawMsg.equalsIgnoreCase("\\[ha]*")) {
            txtChannel.sendMessage("luschtig").queue();
        }
    }
}
