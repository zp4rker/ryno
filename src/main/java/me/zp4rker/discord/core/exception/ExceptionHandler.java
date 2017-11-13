package me.zp4rker.discord.core.exception;

import me.zp4rker.discord.core.logger.ZLogger;
import me.zp4rker.discord.core.util.PBClient;
import me.zp4rker.discord.core.util.PasteUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author ZP4RKER
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        ZLogger.err("Encountered an exception! Sending stacktrace...");
        PBClient.sendPush("Uncaught Exception", PasteUtil.paste(getStackTrace(e)));
    }

    public static void handleException(String issue, Exception e) {
        ZLogger.err("Encountered an exception! Sending stacktrace...");
        PBClient.sendPush(issue, PasteUtil.paste(getStackTrace(e)));
    }

    private static String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }

}
