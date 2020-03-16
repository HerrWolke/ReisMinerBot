package de.mrcloud.listeners;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.*;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {
        Message message = e.getMessage();
        String rawMsg = message.getContentRaw();
        TextChannel TxtChannel = e.getChannel();
        Guild server = e.getGuild();

        User user = e.getAuthor();

        String pattern = "\\s";
        String[] splitMsg = rawMsg.split(pattern);
        super.onGuildMessageReceived(e);
        if (splitMsg[0].equalsIgnoreCase("&Config")) {

            System.out.println(splitMsg[1]);
            if (!splitMsg[1].isEmpty()) {
                if (new File(splitMsg[1] + ".cfg").exists()) {
                    File config = new File(splitMsg[1] + ".cfg");
                    TxtChannel.sendFile(config).queue();
                } else if (new File(splitMsg[1] + ".lua").exists()) {
                    File config = new File(splitMsg[1] + ".lua");
                    TxtChannel.sendFile(config).queue();
                } else {
                    TxtChannel.sendMessage("This File does not exist!").queue();
                }
            }
        } else if (splitMsg[0].equalsIgnoreCase("&Configs")) {
            File files = new File("Files.txt");

            try (FileWriter FileWriterName = new FileWriter(files, true);
                 BufferedWriter Writer = new BufferedWriter(FileWriterName)) {

                String read = null;
                String line = null;

                if (files.exists()) {
                    FileReader fileReader = null;
                    try {
                        fileReader = new FileReader(files);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }


                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    while ((line = bufferedReader.readLine()) != null) {
                        read = line;
                        TxtChannel.sendMessage(line).queue();
                    }


                    try {
                        bufferedReader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}