package me.zp4rker.discord.ryno;

import me.zp4rker.discord.core.command.handler.CommandHandler;
import me.zp4rker.discord.core.exception.ExceptionHandler;
import me.zp4rker.discord.ryno.listeners.Ready;
import me.zp4rker.discord.core.util.PBClient;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.AnnotatedEventManager;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ryno {

    public static final String VERSION = "v1.0";

    private static JDA jda;
    public static CommandHandler handler;
    public static Instant startTime;

    public static Color embedColour = new Color(173, 69, 178);

    public static ExecutorService async = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());

        if (!Config.valid()) {
            System.out.println("Config missing.");
            return;
        }

        String discordToken = Config.val("discord-token");
        String pushbulletToken = Config.val("pushbullet-token");

        PBClient.setToken(pushbulletToken);

        handler = new CommandHandler("$");

        jda = new JDABuilder(AccountType.BOT).setToken(discordToken)
                .setEventManager(new AnnotatedEventManager())
                .setBulkDeleteSplittingEnabled(false)
                .addEventListener(handler)
                .addEventListener(new Ready())
                .buildAsync();
    }

    public static class Config {

        public static boolean valid() {
            System.out.println(Arrays.toString(new File(".").listFiles()));
            JSONObject config = readFile(new File(getDir(), "config.json"));
            return config.keySet().containsAll(Arrays.asList("discord-token", "pushbullet-token", "db-host", "db-name",
                    "db-username", "db-password"));
        }

        public static String val(String key) {
            return readFile(new File(getDir(), "config.json")).getString(key);
        }

        private static JSONObject readFile(File file) {
            String data = "";
            try {
                FileReader rd = new FileReader(file);
                StringBuilder sb = new StringBuilder();
                int c;
                while ((c = rd.read()) != -1) {
                    sb.append((char) c);
                }
                data = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data.isEmpty() ? new JSONObject() : new JSONObject(data);
        }

        private static File getDir() {
            return new File(Ryno.class.getProtectionDomain().getCodeSource().getLocation().toString()).getParentFile();
        }

    }

}
