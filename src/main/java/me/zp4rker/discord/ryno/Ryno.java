package me.zp4rker.discord.ryno;

import me.zp4rker.discord.core.command.handler.CommandHandler;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.AnnotatedEventManager;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ryno {

    private static JDA jda;
    private static CommandHandler handler;
    private static Instant startTime;

    public static ExecutorService async = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {
        String token = args[0];

        handler = new CommandHandler("$");

        jda = new JDABuilder(AccountType.BOT).setToken(token)
                .setEventManager(new AnnotatedEventManager())
                .setBulkDeleteSplittingEnabled(false)
                .addEventListener(handler)
                .buildBlocking();

        startTime = Instant.now();

        updateStatus();
    }

    private static void updateStatus() {
        jda.getPresence().setGame(Game.of("$<command> | " + serverCount() + " servers."));
    }

    private static int serverCount() {
        return jda.getGuilds().size();
    }

}
