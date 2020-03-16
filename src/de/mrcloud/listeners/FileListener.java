package de.mrcloud.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FileListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {
        if (e.getChannel().getIdLong() == 647754397409083412l) {
            super.onGuildMessageReceived(e);
            Message message = e.getMessage();
            String rawMsg = message.getContentRaw();
            TextChannel TxtChannel = e.getChannel();
            Guild server = e.getGuild();
            User user = e.getAuthor();
            TextChannel console;
            List<Permission> deny = Arrays.asList(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY);

            if (!server.getTextChannelsByName("Console", true).isEmpty()) {
                console = server.getTextChannelsByName("Console", true).get(0);

            } else {
                server.createTextChannel("Console").addRolePermissionOverride(358287893225013248l, null, deny).complete();


                console = server.getTextChannelsByName("Console", true).get(0);

            }
            if (!e.getMessage().getAttachments().isEmpty()) {
                int LineNumber = 0;
                List<Message.Attachment> attachment = e.getMessage().getAttachments();
                attachment.get(0).downloadToFile();
                EmbedBuilder embBuilder = new EmbedBuilder();
                embBuilder.setColor(Color.CYAN).setTitle("Test").build();
                console.sendMessage("Downloaded File " + "```" + attachment.get(0).getFileName() + "```").queue();


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
                        LineNumber++;
                        System.out.println("Der Inhalt der Linie ist" + line);
                    }



                        try {
                            bufferedReader.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }
                    int i = 0;
                    Writer.newLine();
                    Writer.write(attachment.get(0).getFileName());



                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (!message.getAuthor().isBot()) {
                //Löscht die Nachricht wenn es keine Datei ist
                message.delete().queue();

            }

        }
    }
}


