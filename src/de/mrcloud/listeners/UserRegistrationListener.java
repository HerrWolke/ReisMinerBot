package de.mrcloud.listeners;

import de.mrcloud.Utils;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserRegistrationListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {
        File userFile = new File(e.getAuthor().getId());
        Date date = new Date();
        SimpleDateFormat smd = new SimpleDateFormat("dd.MM.yyyy 'at' k:mm:ss");
        String firstTime = smd.format(date);
        if(userFile.exists()) {

                try {
                    BufferedReader br = new BufferedReader(new FileReader(userFile));

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }


        } else {
            Utils.writeString(userFile, firstTime);
        }
    }
}
