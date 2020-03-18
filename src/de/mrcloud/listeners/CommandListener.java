package de.mrcloud.listeners;

import de.mrcloud.main.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.io.*;

public class CommandListener extends ListenerAdapter {
    int delCheckerNumber = 0;
    int Test = 0;
    String read = "";

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


//            //            if (delCheckerNumber > 0) {
////                MessageHistory msgHistory = new MessageHistory(TxtChannel);
////                List<Message> toDelMsgs;
////
////                toDelMsgs = msgHistory.retrievePast(delCheckerNumber + 1).complete();
////                delCheckerNumber = 0;
////                TxtChannel.deleteMessages(toDelMsgs).queue();
//
//            }
            System.out.println(splitMsg[1]);


            //Überprüft dass eine Config angegeben wurde
            if (!splitMsg[1].isEmpty()) {

                System.out.println("User " + e.getAuthor().getName() + " hat die Datei " + splitMsg[1] + " angefordert.");
                //Schau ob eine Datei mit dem 1.Argument + .cfg existiert
                if (new File(splitMsg[1] + ".cfg").exists()) {
                    File config = new File(splitMsg[1] + ".cfg");
                    TxtChannel.sendFile(config).queue();
                } else if (new File(splitMsg[1] + ".lua").exists()) {
                    File config = new File(splitMsg[1] + ".lua");
                    TxtChannel.sendFile(config).queue();
                } else {

                    TxtChannel.sendMessage("This File does not exist!").queue();
                    message.getTextChannel().getLatestMessageId();
                }
            }
        } else if (splitMsg[0].equalsIgnoreCase("&Configs")) {
            File files = new File("../Files.txt");

            System.out.println("User " + e.getAuthor().getName() + " requested the all Configs List!");


            String line = null;
            try (FileWriter FileWriterName = new FileWriter(files, true);
                 BufferedWriter Writer = new BufferedWriter(FileWriterName)) {


                if (files.exists()) {
                    FileReader fileReader = null;
                    try {
                        fileReader = new FileReader(files);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }


                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    while ((line = bufferedReader.readLine()) != null) {

                        read += " " + line;
                        delCheckerNumber++;

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


            TxtChannel.sendMessage(read.replaceAll("\\s+", "\n")).queue(message1 -> {
                System.out.println(delCheckerNumber);

                while (Test < delCheckerNumber && Test < 4) {
                    message1.addReaction("U+1f1e" + (6 + Test)).queue();
                    Test++;
                }

                if (Test != delCheckerNumber) {
                    message1.addReaction("U+1f1ea").queue();
                    Test++;
                }
                if (Test != delCheckerNumber) {
                    message1.addReaction("U+1f1eb").queue();
                    Test++;
                }
                if (Test != delCheckerNumber) {
                    message1.addReaction("U+1f1ec").queue();
                    Test++;
                }
                if (Test != delCheckerNumber) {
                    message1.addReaction("U+1f1ed").queue();
                    Test++;
                }
                if (Test != delCheckerNumber) {
                    message1.addReaction("U+1f1ee").queue();
                    Test++;
                }
                if (Test != delCheckerNumber) {
                    message1.addReaction("U+1f1ef").queue();
                    Test++;
                }

                int counter = 0;
                while (Test < delCheckerNumber && Test < 20) {
                    message.addReaction("U+1f1f" + counter).queue();
                    counter++;
                    Test++;
                }

            });



        } else if (splitMsg[0].equalsIgnoreCase("&Stop")) {
            Main main = null;
            try {
                main = new Main();
            } catch (LoginException e1) {
                e1.printStackTrace();
            }
            if (main != null) {
                main.TurnOff();
            }
        }

    }
}
