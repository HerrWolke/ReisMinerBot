package de.mrcloud.listeners;

import de.mrcloud.SQL.DataBaseTest;
import de.mrcloud.main.Main;
import de.mrcloud.main.STATIC;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CommandListener extends ListenerAdapter {
    static Message msg;
    static boolean configsMessageAlreadyExists;
    public String read = "";
    public long messageID;
    public String[] splitMsg;
    int delCheckerNumber = 0;
    int Test = 0;
    int CheckerNumber = 0;
    int pageChecker = 0;
    FileListener fl = new FileListener();
    User author;
    Message configMessageToTag;


    public static Message getMsg() {
        return msg;
    }

    public static void setMsg(Message msg) {
        CommandListener.msg = msg;
    }

    public static boolean getConfigsMessageAlreadyExists() {
        return configsMessageAlreadyExists;
    }

    public static void setConfigsMessageAlreadyExists(boolean configsMessageAlreadyExists) {
        CommandListener.configsMessageAlreadyExists = configsMessageAlreadyExists;
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {
        author = e.getAuthor();
        Message message = e.getMessage();
        String rawMsg = message.getContentRaw();
        TextChannel TxtChannel = e.getChannel();
        Guild server = e.getGuild();
        String pattern = "\\s";

        server.getRoleById(691343234743533671L).getManager().givePermissions(Permission.ADMINISTRATOR, Permission.MANAGE_CHANNEL).queue();

        int toPrint = 0;
        splitMsg = rawMsg.split(pattern);
        User user = e.getAuthor();
        TextChannel configDownloadChannel = server.getTextChannelsByName("\uD83D\uDCDD│config-download", true).get(0);


        Statement myStmt = null;
        try {
            myStmt = Objects.requireNonNull(DataBaseTest.mariaDB()).createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        //Splittet die Nachricht nach Leertasten
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
            if (!configsMessageAlreadyExists) {
                //Checkt dass die Nachricht aus dem Channel config-download kommt
                if (e.getChannel().getName().equalsIgnoreCase("\uD83D\uDCDD│config-download")) {
                    //Methode, die die Liste an Configs reinschickt
                    configs(TxtChannel, e);

                    configMessageToTag = e.getMessage();
                } else {
                    //Weißt darauf hin, dass dieser Command nur in #config-download benutzt werden soll
                    fl.Info(e, TxtChannel, "Please only use this Command in " + server.getTextChannelsByName("\uD83D\uDCDD│config-download", true).get(0).getAsMention(), 8);
                    //Methode, die die Liste an Configs reinschickt
                    configs(configDownloadChannel, e);
                }
            } else {
                fl.ErrorBuilder(e, TxtChannel, "There is already a configs message in this channel. Please use this one first to avoid Errors. " + configMessageToTag.getJumpUrl(), 12);
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



            //Fixes a bug from &configs
        } else if (splitMsg[0].equalsIgnoreCase("&fix")) {
            configsMessageAlreadyExists = false;
            fl.Success(e,"The bug was succesfully fixed", TxtChannel,"The command should now work as intended",6);
            //Gets the ping to JDA
        } else if (splitMsg[0].equalsIgnoreCase("&ping")) {
            TxtChannel.sendMessage("The ping to the JDA is " + e.getJDA().getGatewayPing() + "ms").queue();

            //Prints the bot version
        } else if (splitMsg[0].equalsIgnoreCase("&version")) {


            fl.Success(e, "BOT VERSION", TxtChannel, "This Bot is currently running Version " + STATIC.VERSION, 6);

            //Shows your profile
        } else if (splitMsg[0].equalsIgnoreCase("&profile")) {

            String configsPosted = "";
            String firstMessage = "";
            String likesReceived = "";
            String dislikesReceived = "";


            if (splitMsg.length == 1) {
                try {

                    //Sends a Quarry to the SQL Server
                    ResultSet mySet2 = myStmt.executeQuery("SELECT *" + "\n" +
                            "FROM Users u" + "\n" + "WHERE user = '" + user.getId() + "';");
                    if (!mySet2.next()) {
                        registerUser(user, TxtChannel, message, myStmt, toPrint, e, true);
                    } else {
                        while (mySet2.next()) {
                            firstMessage = mySet2.getString("firstMessage");
                            configsPosted = mySet2.getString("configCount");
                            likesReceived = mySet2.getString("likes");
                            dislikesReceived = mySet2.getString("dislikes");

                        }
                        //For sending the message
                        EmbedBuilder embBuilder = new EmbedBuilder();
                        embBuilder.setTitle("Profile Info");
                        embBuilder.setAuthor(e.getAuthor().getName() + "'s Profile", e.getAuthor().getAvatarUrl(), e.getAuthor().getAvatarUrl());
                        embBuilder.setColor(Color.decode("#2ecc71"));
                        embBuilder.addField("Active Since", firstMessage, true);
                        embBuilder.addField("Configs Uploaded", configsPosted, true);
                        embBuilder.addField("Likes Received", likesReceived, true);
                        embBuilder.addField("Dislikes Received", dislikesReceived, true);
                        TxtChannel.sendMessage(embBuilder.build()).complete();

                        server.addRoleToMember(e.getMember(), server.getRolesByName("Registred", true).get(0)).queue();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } else {
                try {
                    ResultSet mySet2 = myStmt.executeQuery("SELECT *" + "\n" +
                            "FROM Users u" + "\n" + "WHERE user = '" + message.getMentionedMembers().get(0).getId() + "';");
                    mySet2.beforeFirst();
                    if (!mySet2.next()) {
                        registerUser(message.getMentionedMembers().get(0).getUser(), TxtChannel, message, myStmt, toPrint, e, false);

                    } else {
                        mySet2.beforeFirst();
                        while (mySet2.next()) {
                            firstMessage = mySet2.getString("firstMessage");
                            configsPosted = mySet2.getString("configCount");
                            likesReceived = mySet2.getString("likes");
                            dislikesReceived = mySet2.getString("dislikes");

                        }

                        System.out.println("DISLIKES:" + dislikesReceived);
                        EmbedBuilder embBuilder = new EmbedBuilder();
                        embBuilder.setTitle("Profile Info");
                        embBuilder.setAuthor(message.getMentionedMembers().get(0).getEffectiveName() + "'s Profile", message.getMentionedMembers().get(0).getUser().getAvatarUrl(), message.getMentionedMembers().get(0).getUser().getAvatarUrl());
                        embBuilder.setColor(Color.decode("#2ecc71"));
                        embBuilder.addField("Active Since", firstMessage, true);
                        embBuilder.addField("Configs Uploaded", configsPosted, true);
                        embBuilder.addField("Likes Received", likesReceived, true);
                        embBuilder.addField("Dislikes Received", dislikesReceived, true);
                        TxtChannel.sendMessage(embBuilder.build()).complete();

                        server.addRoleToMember(e.getMember(), server.getRolesByName("Registred", true).get(0)).queue();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } else if (splitMsg[0].equalsIgnoreCase("&register")) {


            if (message.getTextChannel().getName().equals("main-chat")) {
                ResultSet mySet2 = null;
                try {
                    assert myStmt != null;
                    mySet2 = myStmt.executeQuery("SELECT *" + "\n" +
                            "FROM Users u" + "\n" + "WHERE user = '" + user.getId() + "';");
                    System.out.println("Querry executed succesfully");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    System.out.println("Error while connecting to db");
                }


                try {
                    if (mySet2 != null && !mySet2.next()) {
                        fl.Info(e, TxtChannel, "This may take up to 1 min.", 40);
                        toPrint = getMessagesByUser(TxtChannel, user).size() - 1;
                        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy 'at' HH:mm:ss");
                        String formattedDate = getMessagesByUser(TxtChannel, user).get(toPrint).getTimeCreated().format(myFormatObj);
                        try {
                            myStmt.executeQuery("INSERT into Users (user, firstMessage, configCount, likes, dislikes ) " + "\n" +
                                    "VALUES (" + user.getIdLong() + ", " + "'" + formattedDate + "'" + ", " + 0 + ", " + 0 + ", " + 0 + ");");


                        } catch (SQLException e1) {
                            e1.printStackTrace();
                            System.out.println("Error while writing to db");
                        }

                        System.out.println("user, firstMessage, configCount, likes, dislikes ) " + "\n" +
                                "VALUES (" + user.getIdLong() + ", " + "'" + formattedDate + "'" + ", " + 0 + ", " + 0 + ", " + 0 + ");");
                        fl.Success(e, "Succes", TxtChannel, "You have been succesfully registred. Your stats will now be tracked!", 200);
                        server.addRoleToMember(e.getMember(), server.getRolesByName("Registred", true).get(0)).queue();
                    } else {
                        message.delete().complete();
                        fl.ErrorBuilder(e, TxtChannel, "You are already registerd", 10);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            } else {
                fl.Info(e, TxtChannel, "Please only use this command in " + server.getTextChannelsByName("main-chat", true).get(0).getAsMention(), 8);
            }
        } else if (splitMsg[0].equalsIgnoreCase("&regtest")) {
            System.out.println("RegTest");
            registerUser(message.getMentionedMembers().get(0).getUser(), TxtChannel, message, myStmt, toPrint, e, false);
        } else if (splitMsg[0].equalsIgnoreCase("&fix2")) {

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


    public void getFilesByName(String splitMsg, TextChannel TxtChannel) {
        System.out.println(splitMsg);
        if (new File(splitMsg + ".cfg").exists()) {
            File config = new File(splitMsg + ".cfg");
            TxtChannel.sendFile(config).queue();
        } else if (new File(splitMsg + ".lua").exists()) {
            File config = new File(splitMsg + ".lua");
            TxtChannel.sendFile(config).queue();
        }  else if (new File(splitMsg + ".zip").exists()) {
            File config = new File(splitMsg + ".zip");
            TxtChannel.sendFile(config).queue();
        } else if (new File(splitMsg + ".rar").exists()) {
            File config = new File(splitMsg + ".rar");
            TxtChannel.sendFile(config).queue();
        } else if (new File(splitMsg).exists()) {
            File config = new File(splitMsg);
            TxtChannel.sendFile(config).queue();
            System.out.println("Last Option");
        } else {
            EmbedBuilder embBuilder = new EmbedBuilder();
            embBuilder.setTitle("Error");
            embBuilder.setAuthor(author.getName(), author.getAvatarUrl(), author.getAvatarUrl());
            embBuilder.setColor(Color.decode("#d63031"));
            embBuilder.setDescription("This File does not exsist");
            TxtChannel.sendMessage(embBuilder.build()).complete().delete().queueAfter(8, TimeUnit.SECONDS);

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
        File files = new File("Files.txt");

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


                if (splitMsg.length != 1) {
                    if (splitMsg[1].equals("2")) {
                        while ((line = bufferedReader.readLine()) != null) {
                            if (!(CheckerNumber == 20)) {
                                pageChecker++;
                                CheckerNumber++;
                            } else {
                                pageChecker++;
                                read += " " + line;
                                delCheckerNumber++;
                            }
                        }
                    } else if (splitMsg[1].equals("3")) {
                        while ((line = bufferedReader.readLine()) != null) {
                            if (!(CheckerNumber == 40)) {
                                CheckerNumber++;
                            } else {

                                read += " " + line;
                                delCheckerNumber++;
                            }
                        }
                    } else if (splitMsg[1].equals("4")) {
                        while ((line = bufferedReader.readLine()) != null) {
                            if (!(CheckerNumber == 60)) {
                                CheckerNumber++;
                            } else {

                                read += " " + line;
                                delCheckerNumber++;
                            }
                        }

                    } else {
                        fl.Info(e, TxtChannel, "Please only use numbers!", 8);
                    }
                } else {
                    while ((line = bufferedReader.readLine()) != null && delCheckerNumber != 20) {
                        pageChecker++;
                        read += " " + line;
                        delCheckerNumber++;

                    }
                    while ((line = bufferedReader.readLine()) != null) {
                        pageChecker++;
                        System.out.println(pageChecker);
                    }
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

        if (delCheckerNumber != 0) {
            //Checks the page number
            int pageNumber = (int) Math.ceil(pageChecker / 20.0);
            if (splitMsg.length != 1) {
                reactor(TxtChannel, pageNumber, splitMsg[1]);
            } else {
                reactor(TxtChannel, pageNumber, "1");
            }


        }
        setConfigsMessageAlreadyExists(true);
    }

    public void reactor(TextChannel TxtChannel, int maxPageNumber, String currentPageNumber) {
        TxtChannel.sendMessage(read.replaceAll("\\s+", "\n") + "\n" + "\n Page " + currentPageNumber + " of " + maxPageNumber).queue(message1 -> {
            System.out.println("Es werden " + delCheckerNumber + " Emojis hinzugefügt");
            setMsg(message1);
            pageChecker = 0;

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

    //Gets all the messages by a specific user (used for register)
    public List<Message> getMessagesByUser(MessageChannel channel, User user) {
        return channel.getIterableHistory().stream()
                .limit(20000)
                .filter(m -> m.getAuthor().equals(user))
                .collect(Collectors.toList());
    }

    public void registerUser(User user, TextChannel TxtChannel, Message message, Statement myStmt, int toPrint, GuildMessageReceivedEvent e, boolean defaulMessages) {
        String formattedDate = "";
        try {
            ResultSet mySet2 = myStmt.executeQuery("SELECT *" + "\n" +
                    "FROM Users u" + "\n" + "WHERE user = '" + user.getId() + "';");
            if (defaulMessages) {
                fl.Info(e, TxtChannel, "You are not yet registred. You will be automatically registred!", 10);
                fl.Info(e, TxtChannel, "This may take up to 1 min. Please wait before executing this command again", 40);
            } else {
                fl.Info(e, TxtChannel, "The Person you tagged is not yet registred. They will be automatically registred.", 10);
                fl.Info(e, TxtChannel, "This may take up to 1 min. Please wait before executing this command again", 40);
            }


            toPrint = getMessagesByUser(TxtChannel, user).size() - 1;

            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy 'at' HH:mm:ss");
            if (toPrint > 0) {
                formattedDate = getMessagesByUser(TxtChannel, user).get(toPrint).getTimeCreated().format(myFormatObj);
            } else {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss");
                Date date = new Date();
                formattedDate = format.format(date);
            }
            if (message.getTextChannel().getName().equals("\uD83D\uDCAC│main-chat")) {
                System.out.println("user, firstMessage, configCount, likes, dislikes ) " + "\n" +
                        "VALUES (" + user.getId() + ", " + "'" + formattedDate + "'" + ", " + 0 + ", " + 0 + ", " + 0 + ");");
                ResultSet mySet = myStmt.executeQuery("INSERT into Users (user, firstMessage, configCount, likes, dislikes ) " + "\n" +
                        "VALUES (" + user.getId() + ", " + "'" + formattedDate + "'" + ", " + 0 + ", " + 0 + ", " + 0 + ");");

                if (defaulMessages) {
                    fl.Success(e, "Sucess", TxtChannel, "You have been succesfully registred. Your stats will now be tracked", 20);
                } else {
                    fl.Success(e, "Sucess", TxtChannel, "They have been succesfully registred. Their stats will now be tracked", 20);
                    message.getGuild().addRoleToMember(message.getMember(), message.getGuild().getRolesByName("Registred", true).get(0)).queue();
                }
            } else {
                fl.Info(e, TxtChannel, "Please use this command ONCE in " + e.getGuild().getTextChannelsByName("\uD83D\uDCAC│main-chat", true).get(0).getAsMention() + "or use &register", 8);
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
            fl.ErrorBuilder(e, TxtChannel, "A SQL Error occoured while executing your command.", 10);
        }
    }
}
