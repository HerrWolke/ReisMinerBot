package de.mrcloud.listeners;

import de.mrcloud.main.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.List;

public class CommandListener extends ListenerAdapter {
    int delCheckerNumber = 0;
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
            if(delCheckerNumber > 0) {
                MessageHistory msgHistory = new MessageHistory(TxtChannel);
                List<Message> toDelMsgs;

                toDelMsgs = msgHistory.retrievePast(delCheckerNumber + 1).complete();
                TxtChannel.deleteMessages(toDelMsgs).queue();

            }
            System.out.println(splitMsg[1]);
            //Überprüft dass eine Config angegeben wurde
            if (!splitMsg[1].isEmpty()) {

                //Schau ob eine Datei mit dem 1.Argument + .cfg existiert
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
            File files = new File("../Files.txt");
            System.out.println(files.getAbsolutePath());
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
                        delCheckerNumber++;
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
        } else if(splitMsg[0].equalsIgnoreCase("&Stop")) {
            Main main = null;
            try {
               main  = new Main();
            } catch (LoginException e1) {
                e1.printStackTrace();
            }
            if(main != null) {
                main.TurnOff();
            }
        }

        }
    }
