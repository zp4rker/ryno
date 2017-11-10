package me.zp4rker.discord.ryno;

import me.zp4rker.discord.core.command.handler.CommandHandler;
import me.zp4rker.discord.core.exception.ExceptionHandler;
import me.zp4rker.discord.core.logger.ZLogger;
import me.zp4rker.discord.ryno.lstnr.ReadyListener;
import me.zp4rker.discord.core.util.PBClient;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.AnnotatedEventManager;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ryno {

    public static final String VERSION = "v1.0";

    private static JDA jda;
    public static CommandHandler handler;
    public static Instant startTime;

    public static ExecutorService async = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());

        String discordToken = args[0];
        String pushbulletToken = args[1];

        PBClient.setToken(pushbulletToken);

        handler = new CommandHandler("$");

        jda = new JDABuilder(AccountType.BOT).setToken(discordToken)
                .setEventManager(new AnnotatedEventManager())
                .setBulkDeleteSplittingEnabled(false)
                .addEventListener(handler)
                .addEventListener(new ReadyListener())
                .buildAsync();
    }

}
