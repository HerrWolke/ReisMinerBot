package de.mrcloud.listeners;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.*;

public class ReactionListener extends ListenerAdapter {
    public String forCmdListener = "";

    FileListener fileListener = new FileListener();
    boolean isDisabled = false;

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent e) {
        //Checkt dass der User kein Bot ist
        if (!e.getUser().isBot()) {
            //Check dass die Nachricht aus dem richtigen Channel kommt
            if (e.getChannel().getName().equalsIgnoreCase("config-download")) {
                if (!isDisabled) {
                    System.out.println(isDisabled);
                    //Variablen
                    String reacEmote = e.getReactionEmote().getName();
                    TextChannel txtChannel = e.getChannel();
                    CommandListener cmdListener = new CommandListener();
                    Guild server = e.getGuild();

                    String read = "";
                    String line = null;
                    int toGet = 0;
                    int i = 0;
                    //Datei in der alle configs gespeichert werden
                    File files = new File("Files.txt");


                    super.onGuildMessageReactionAdd(e);

                    if (files.exists()) {
                        FileReader fileReader = null;
                        try {
                            fileReader = new FileReader(files);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }

                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        try {
                            while ((line = bufferedReader.readLine()) != null) {
                                //Setzt read gleich mit der gelesenen linie
                                read += " " + line;

                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        try {
                            //Schließt die Datei
                            bufferedReader.close();
                        } catch (IOException exp) {
                            exp.printStackTrace();
                        }


                        String[] configFile = read.split("\\s");

                        switch (reacEmote) {
                            case "\uD83C\uDDE6":
                                cmdListener.getFilesByName(configFile[1], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                break;
                            case "\uD83C\uDDE7":
                                toGet = 2;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                break;
                            case "\uD83C\uDDE8":
                                toGet = 3;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                break;
                            case "\uD83C\uDDE9":
                                toGet = 4;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                break;
                            case "\uD83C\uDDEA":
                                toGet = 5;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                break;
                            case "\uD83C\uDDEB":
                                toGet = 6;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                break;
                            case "\uD83C\uDDEC":
                                toGet = 7;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                break;
                            case "\uD83C\uDDED":
                                toGet = 8;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                break;
                            case "\uD83C\uDDEE":
                                toGet = 9;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                break;
                            case "\uD83C\uDDEF":
                                toGet = 10;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                break;
                        }

                        CommandListener.getMsg().delete().queue();


                        //noinspection EqualsBetweenInconvertibleTypes

                    }
                }
            }
        }
    }

}
