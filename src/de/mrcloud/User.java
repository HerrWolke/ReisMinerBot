package de.mrcloud;

public class User{

    public boolean IsRegistred;
    public net.dv8tion.jda.api.entities.User user;
    public int userID = 0;

    public User(int userID, boolean IsRegis, net.dv8tion.jda.api.entities.User user) {
        this.IsRegistred = IsRegis;
        this.user = user;
        this.userID = userID;
    }

}
