package de.mrcloud.listeners;

import de.mrcloud.SQL.DataBaseTest;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReactionListener extends ListenerAdapter {
    public String forCmdListener = "";

    FileListener fileListener = new FileListener();
    boolean isDisabled = false;

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent e) {
        //Checkt dass der User kein Bot ist
        if (!e.getUser().isBot()) {
            //Check dass die Nachricht aus dem richtigen Channel kommt
            if (e.getChannel().getName().equalsIgnoreCase("\uD83D\uDCDD│config-download")) {
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
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\uD83C\uDDE7":
                                toGet = 2;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\uD83C\uDDE8":
                                toGet = 3;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\uD83C\uDDE9":
                                toGet = 4;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\uD83C\uDDEA":
                                toGet = 5;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\uD83C\uDDEB":
                                toGet = 6;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\uD83C\uDDEC":
                                toGet = 7;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\uD83C\uDDED":
                                toGet = 8;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\uD83C\uDDEE":
                                toGet = 9;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\uD83C\uDDEF":
                                toGet = 10;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\\uD83C\\uDDF0":
                                toGet = 11;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\\uD83C\\uDDF1":
                                toGet = 12;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\\uD83C\\uDDF2":
                                toGet = 13;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\\uD83C\\uDDF3":
                                toGet = 14;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\\uD83C\\uDDF4":
                                toGet = 15;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\\uD83C\\uDDF5":
                                toGet = 16;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\\uD83C\\uDDF6":
                                toGet = 17;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\\uD83C\\uDDF7":
                                toGet = 18;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\\uD83C\\uDDF8":
                                toGet = 19;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                            case "\\uD83C\\uDDF9":
                                toGet = 20;
                                cmdListener.getFilesByName(configFile[toGet], txtChannel);
                                CommandListener.setConfigsMessageAlreadyExists(false);
                                System.out.println(CommandListener.configsMessageAlreadyExists);
                                break;
                        }

                        CommandListener.getMsg().delete().queue();
                        CommandListener.setConfigsMessageAlreadyExists(false);

                        //noinspection EqualsBetweenInconvertibleTypes

                    }
                }
            } else if (e.getChannel().getName().equalsIgnoreCase("\uD83D\uDCDD│public-configs")) {


                String reacEmote = e.getReactionEmote().getName();
                TextChannel txtChannel = e.getChannel();
                CommandListener cmdListener = new CommandListener();
                Guild server = e.getGuild();
                ResultSet myRes = null;
                int likes = 0;
                int dislikes = 0;
                final Message[] message = new Message[1];
                txtChannel.retrieveMessageById(e.getMessageId()).queue(message1 -> message[0] = message1);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                    System.out.println(e1.getLocalizedMessage());
                }
                assert message[0] != null;
                User author = message[0].getAuthor();
                Statement myStmt = null;
                try {
                    myStmt = Objects.requireNonNull(DataBaseTest.mariaDB()).createStatement();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                //Aka Like
                if (reacEmote.equals("\uD83D\uDC4D")) {
                    try {
                        assert myStmt != null;
                        myRes = myStmt.executeQuery("SELECT likes FROM Users WHERE user = " + author.getId() + ";");
                        while (myRes.next()) {
                            likes = Integer.parseInt(myRes.getString("likes"));
                            likes++;
                        }

                        myStmt.executeQuery("UPDATE Users SET likes = " + likes + " WHERE user = " + author.getId() + ";");

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                        System.out.println(e1.getSQLState());
                        System.out.println(e1.getLocalizedMessage());
                    }


                    //Aka Dislike
                } else if (reacEmote.equals("\uD83D\uDC4E")) {
                    try {
                        assert myStmt != null;
                        myRes = myStmt.executeQuery("SELECT dislikes FROM Users WHERE user = " + author.getId() + ";");
                        while (myRes.next()) {
                            dislikes = Integer.parseInt(myRes.getString("dislikes"));
                            dislikes++;
                        }

                        myStmt.executeQuery("UPDATE Users SET dislikes = " + dislikes + " WHERE user = " + author.getId() + ";");

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                        System.out.println(e1.getSQLState());
                        System.out.println(e1.getLocalizedMessage());
                    }


                }
            }
        }
    }

    @Override
    public void onGuildMessageReactionRemove(@Nonnull GuildMessageReactionRemoveEvent e) {
        super.onGuildMessageReactionRemove(e);

        if (e.getChannel().getName().equalsIgnoreCase("\uD83D\uDCDD│config-download")) {
            String reacEmote = e.getReactionEmote().getName();
            TextChannel txtChannel = e.getChannel();
            CommandListener cmdListener = new CommandListener();
            Guild server = e.getGuild();
            ResultSet myRes;
            int likes = 0;
            int dislikes = 0;
            final Message[] message = new Message[1];
            txtChannel.retrieveMessageById(e.getMessageId()).queue(message1 -> message[0] = message1);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                System.out.println(e1.getLocalizedMessage());
            }
            assert message[0] != null;
            User author = message[0].getAuthor();

            Statement myStmt = null;
            try {
                myStmt = Objects.requireNonNull(DataBaseTest.mariaDB()).createStatement();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            //Aka Like
            if (reacEmote.equals("\uD83D\uDC4D")) {
                try {
                    assert myStmt != null;
                    myRes = myStmt.executeQuery("SELECT likes FROM Users WHERE user = " + author.getId() + ";");
                    while (myRes.next()) {
                        likes = Integer.parseInt(myRes.getString("likes"));
                        likes--;
                    }

                    myStmt.executeQuery("UPDATE Users SET likes = " + likes + " WHERE user = " + author.getId() + ";");

                } catch (SQLException e1) {
                    e1.printStackTrace();
                    System.out.println(e1.getSQLState());
                    System.out.println(e1.getLocalizedMessage());
                }


                //Aka Dislike
            } else if (reacEmote.equals("\uD83D\uDC4E")) {
                try {
                    assert myStmt != null;
                    myRes = myStmt.executeQuery("SELECT dislikes FROM Users WHERE user = " + author.getId() + ";");
                    while (myRes.next()) {
                        dislikes = Integer.parseInt(myRes.getString("dislikes"));
                        dislikes--;
                    }

                    myStmt.executeQuery("UPDATE Users SET dislikes = " + dislikes + " WHERE user = " + author.getId() + ";");

                } catch (SQLException e1) {
                    e1.printStackTrace();
                    System.out.println(e1.getSQLState());
                    System.out.println(e1.getLocalizedMessage());
                }


            }

        }


    }

    public List<Message> getMessages(MessageChannel channel) {
        return channel.getIterableHistory().stream()
                .limit(200)
                .collect(Collectors.toList());
    }

}
