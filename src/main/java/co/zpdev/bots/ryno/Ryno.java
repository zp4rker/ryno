package co.zpdev.bots.ryno;

import co.zpdev.core.discord.command.CommandHandler;
import co.zpdev.core.discord.util.Config;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.AnnotatedEventManager;

import javax.security.auth.login.LoginException;
import java.net.URISyntaxException;

/**
 * @author ZP4RKER
 */
public class Ryno {

    public static Config config;

    public static void main(String[] args) throws LoginException, InterruptedException, URISyntaxException {
        config = new Config("config.json");
        CommandHandler handler = new CommandHandler("/", "co.zpdev.bots.ryno.cmd");

        new JDABuilder(AccountType.BOT).setToken(config.data.getString("token"))
                .setEventManager(new AnnotatedEventManager())
                .addEventListener(handler)
                .setGame(Game.playing("v" + Ryno.class.getPackage().getImplementationVersion()))
                .buildBlocking();
    }

}
