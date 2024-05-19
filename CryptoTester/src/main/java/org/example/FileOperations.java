package org.example;
import java.awt.*;
import java.io.*;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class FileOperations {
    public static String reservedCharsRegex = "[<>\":/\\\\|?*]";
    public static void createFolder(String folderName){
        Path newFolderPath = Paths.get(folderName);
        try {
            Files.createDirectories(newFolderPath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static File[] getAllFiles(String type)
    {
            File folder = new File(type+"HistoryData/");
            return folder.listFiles();

    }

    public static boolean createFile(List data, boolean create){
        String fileNameforWin = data.getItem(0).replaceAll(reservedCharsRegex, "") + "File.txt";
        fileNameforWin = fileNameforWin.replaceAll("\t", "");
        File test = new File(fileNameforWin);
        if(test.exists() && create){
            return false;
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileNameforWin))) {
            out.writeObject(data);
            out.writeObject(Main.saldoData);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public static void createFile(Map<String, Double> data,  String name, String type){
        String fileNameforWin = type+"HistoryData/" + name + ".txt";
        fileNameforWin = fileNameforWin.replaceAll(":", "_");
        if(type.equals("crypto"))
        {
            data.put("USDT", 1/data.get("USDT"));
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileNameforWin))) {
            out.writeObject(data);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static List readFile(List data){
        String fileNameforWin = data.getItem(0).replaceAll(reservedCharsRegex, "") + "File.txt";
        fileNameforWin = fileNameforWin.replaceAll("\t", "");
        File test = new File(fileNameforWin);
        if(!test.exists()){
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNameforWin))) {
            List readArray = (List) in.readObject();
            Map<String, Double> readDataMap = (Map<String, Double>) in.readObject();
            for (String key : readDataMap.keySet()) {
                Main.saldoData.put(key, readDataMap.get(key));
            }
            return readArray;
        } catch (IOException | ClassNotFoundException e) {
            return null;
     }
    }
    public static Map<String, Double> readFile(String fileName){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Map<String, Double>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
}
}

