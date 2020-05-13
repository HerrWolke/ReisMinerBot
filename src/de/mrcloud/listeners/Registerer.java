package de.mrcloud.listeners;

import de.mrcloud.SQL.DataBaseTest;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Registerer extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {
        super.onGuildMessageReceived(e);

        User user = e.getAuthor();
        Member member = e.getMember();
        Message message = e.getMessage();
        String rawMsg = message.getContentRaw();
        TextChannel TxtChannel = e.getChannel();
        Guild server = e.getGuild();
        FileListener fl = new FileListener();

        Statement myStmt = null;
        try {
            myStmt = Objects.requireNonNull(DataBaseTest.mariaDB()).createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        ResultSet mySet2 = null;
        try {
            assert myStmt != null;
            mySet2 = myStmt.executeQuery("SELECT *" + "\n" +
                    "FROM Users u" + "\n" + "WHERE user = '" + user.getId() + "';");
        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println("Error while connecting to db");
        }


        try {
            if (mySet2 != null && !mySet2.next()) {

                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy 'at' HH:mm:ss");
                String formattedDate = member.getTimeJoined().format(myFormatObj);
                try {
                    myStmt.executeQuery("INSERT into Users (user, firstMessage, configCount, likes, dislikes ) " + "\n" +
                            "VALUES (" + user.getIdLong() + ", " + "'" + formattedDate + "'" + ", " + 0 + ", " + 0 + ", " + 0 + ");");


                } catch (SQLException e1) {
                    e1.printStackTrace();
                    System.out.println("Error while writing to db");
                }

            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent e) {
        super.onGuildMemberJoin(e);

        Member member = e.getMember();
        Guild server = e.getGuild();
        FileListener fl = new FileListener();

        Statement myStmt = null;
        try {
            myStmt = Objects.requireNonNull(DataBaseTest.mariaDB()).createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        ResultSet mySet2 = null;
        try {
            assert myStmt != null;
            mySet2 = myStmt.executeQuery("SELECT *" + "\n" +
                    "FROM Users u" + "\n" + "WHERE user = '" + member.getId() + "';");
        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println("Error while connecting to db");
        }


        try {
            if (mySet2 != null && !mySet2.next()) {

                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy 'at' HH:mm:ss");
                String formattedDate = member.getTimeJoined().format(myFormatObj);
                try {
                    myStmt.executeQuery("INSERT into Users (user, firstMessage, configCount, likes, dislikes ) " + "\n" +
                            "VALUES (" + member.getIdLong() + ", " + "'" + formattedDate + "'" + ", " + 0 + ", " + 0 + ", " + 0 + ");");


                } catch (SQLException e1) {
                    e1.printStackTrace();
                    System.out.println("Error while writing to db");
                }

            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
