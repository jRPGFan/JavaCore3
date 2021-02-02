package ru.geekbrains.java_two.chat.client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class HistoryHandler {

    public static String readLogFileToLog(String nickname){
        File historyFile = new File(nickname + ".txt");

        if (historyFile.exists()){
            try {
                List<String> historyLines = Files.readAllLines(historyFile.toPath(), StandardCharsets.UTF_8);
                StringBuilder strBuild = new StringBuilder();

                int startingPosition = 0;
                if(historyLines.size() > 100) startingPosition = historyLines.size() - 100;

                for (int i = startingPosition; i<historyLines.size();i++)
                    strBuild.append(historyLines.get(i)).append("\n");
                return strBuild.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            return "log not found and will be created with the next message";
        return "log not found and will be created with the next message";
    }

    public static void wrtMsgToLogFile(String nickname, String msg) {
        try (FileWriter out = new FileWriter(nickname + ".txt", true)) {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
