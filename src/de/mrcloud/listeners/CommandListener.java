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
    static Message msg;
    public String read = "";
    public long messageID;
    int delCheckerNumber = 0;
    int Test = 0;

    public static Message getMsg() {
        return msg;
    }

    public static void setMsg(Message msg) {
        CommandListener.msg = msg;
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {
        Message message = e.getMessage();
        String rawMsg = message.getContentRaw();
        TextChannel TxtChannel = e.getChannel();
        Guild server = e.getGuild();

        User user = e.getAuthor();
        TextChannel configDownloadChannel = server.getTextChannelsByName("config-download", true).get(0);

        String pattern = "\\s";
        //Splittet die Nachricht nach Leertasten
        String[] splitMsg = rawMsg.split(pattern);
        super.onGuildMessageReceived(e);
        if (splitMsg[0].equalsIgnoreCase("&Config")) {
            //Überprüft dass eine Config angegeben wurde
            if (!splitMsg[1].isEmpty()) {
                getFilesByName(splitMsg[1], TxtChannel);
                System.out.println("User " + e.getAuthor().getName() + " hat die Datei " + splitMsg[1] + " angefordert.");
                //Schau ob eine Datei mit dem 1.Argument + .cfg existiert

            } else {
                TxtChannel.sendMessage("Please use &Config CONFIGNAME(.lua/.cfg)").queue();
            }
            //Get all Configs Command
        } else if (splitMsg[0].equalsIgnoreCase("&Configs")) {
            //Checkt dass die Nachricht aus dem Channel config-download kommt
            if (e.getChannel().getName().equalsIgnoreCase("config-download")) {
                //Methode, die die Liste an Configs reinschickt
                configs(TxtChannel, e);
            } else {
                //Weißt darauf hin, dass dieser Command nur in #config-download benutzt werden soll
                TxtChannel.sendMessage("Please only use this Command in " + server.getTextChannelsByName("config-download", true).get(0).getAsMention()).queue();
                //Methode, die die Liste an Configs reinschickt
                configs(configDownloadChannel, e);
            }

            //Stoppt den Bot (Fehlerhaft)
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

        // METHODS //
        //         //
        //         //
        // METHODS //
        //         //
        //         //
        // METHODS //

        //Sucht nach der Datei und läd sie dann hoch
    }

    public void getFilesByName(String splitMsg, TextChannel TxtChannel) {
        if (new File(splitMsg + ".cfg").exists()) {
            File config = new File(splitMsg + ".cfg");
            TxtChannel.sendFile(config).queue();
        } else if (new File(splitMsg + ".lua").exists()) {
            File config = new File(splitMsg + ".lua");
            TxtChannel.sendFile(config).queue();
        } else if (new File(splitMsg).exists()) {
            File config = new File(splitMsg);
            TxtChannel.sendFile(config).queue();

        } else {
            TxtChannel.sendMessage("This File does not exist!").queue();
        }
    }

    public String getRead() {
        return read;
    }

    public void checker() {
        if (delCheckerNumber == Test) {
            delCheckerNumber = 0;
            Test = 0;
        }
    }

    public void configs(TextChannel TxtChannel, GuildMessageReceivedEvent e) {
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
            System.out.println("Es werden " + delCheckerNumber + " Emojis hinzugefügt");
            setMsg(message1);


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
                message1.addReaction("U+1f1f" + counter).queue();
                counter++;
                Test++;

            }


            checker();
            read = "";
        });
    }
}
