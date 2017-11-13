package me.zp4rker.discord.core.util;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class PBClient {

    private static String token;
    private static String latest = "";

    public static void setToken(String token) {
        PBClient.token = token;
    }

    public static void start(PBTask handler) {
        try {
            JSONArray pushes = getPushes("");
            if (pushes == null) return;
            JSONObject latest = pushes.getJSONObject(0);
            PBClient.latest = latest.getDouble("modified") + "";
        } catch (Exception e) {
            System.out.println("Something went wrong!");
            e.printStackTrace();
        }

        try {
            WebSocketClient wc = new WebSocketClient(new URI("wss://stream.pushbullet.com/websocket/" + token)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    // Connection successful
                }

                @Override
                public void onMessage(String s) {
                    JSONObject data = new JSONObject(s);
                    if (!data.getString("type").equals("tickle")) return;
                    if (data.getString("subtype") == null || !data.getString("subtype").equals("push")) return;

                    JSONArray pushes = getPushes(latest);
                    if (pushes == null) return;
                    if (pushes.length() < 1) return;

                    JSONObject latest = pushes.getJSONObject(0);
                    if (Double.parseDouble(PBClient.latest) < latest.getDouble("modified"))
                        PBClient.latest = latest.getDouble("modified") + "";

                    if (!latest.getBoolean("active")) return;
                    if (latest.getString("source_device_iden").equals("ujzkQhMD8LcsjAdCRXjVjU")) return;

                    // Handle recieved message
                    String msg = latest.getString("body");
                    handler.handle(msg);
                }

                @Override
                public void onClose(int i, String s, boolean b) {

                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            };
            wc.connect();
        } catch (Exception e) {
            System.out.println("Something went wrong!");
            e.printStackTrace();
        }
    }

    private static JSONArray getPushes(String modifiedAfter) {
        try {
            String u = "https://api.pushbullet.com/v2/pushes?active=false";
            if (!modifiedAfter.isEmpty()) {
                u += "&modified_after=" + modifiedAfter;
            }
            URL url = new URL(u);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Access-Token", token);
            con.setDoInput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();

            JSONObject data = new JSONObject(sb.toString().trim());
            return data.getJSONArray("pushes");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sendPush(String title, String message) {
        try {
            URL url = new URL("https://api.pushbullet.com/v2/pushes");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Access-Token", token);

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes("type=note&title=" + title + "&body=" + message + "&source_device_iden=ujzkQhMD8LcsjAdCRXjVjU");
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            in.readLine();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
