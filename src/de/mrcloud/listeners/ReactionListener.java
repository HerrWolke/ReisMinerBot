package de.mrcloud.listeners;

import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class ReactionListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent e) {
        super.onGuildMessageReactionAdd(e);
        MessageReaction.ReactionEmote reacEmote = e.getReactionEmote();
        CommandListener cmdListener = new CommandListener();
        System.out.println(reacEmote);

        String pattern = "\\s";
        String[] splitMsg = cmdListener.read.split(pattern);

        String testString = "abcde 6";




        //noinspection EqualsBetweenInconvertibleTypes

    }
}
