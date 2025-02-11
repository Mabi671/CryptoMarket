package org.example;
import java.awt.*;
import java.awt.List;
import java.io.*;
import java.util.*;
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
        String fileNameForWin = data.getItem(0).replaceAll(reservedCharsRegex, "") + "File.txt";
        fileNameForWin = fileNameForWin.replaceAll("\t", "");
        File test = new File(fileNameForWin);
        if(test.exists() && create){
            return false;
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileNameForWin))) {
            out.writeObject(data);
            out.writeObject(Main.saldoData);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public static void createFile(Map<String, Double> data,  String name, String type){
        String fileNameForWin = type+"HistoryData/" + name + ".txt";
        fileNameForWin = fileNameForWin.replaceAll(":", "_");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileNameForWin))) {
            out.writeObject(data);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @SuppressWarnings (value="unchecked")
    public static List readFile(List data){
        String fileNameForWin = data.getItem(0).replaceAll(reservedCharsRegex, "") + "File.txt";
        fileNameForWin = fileNameForWin.replaceAll("\t", "");
        File test = new File(fileNameForWin);
        if(!test.exists()){
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNameForWin))) {
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
    @SuppressWarnings (value="unchecked")
    public static Map<String, Double> readFile(String fileName){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Map<String, Double>) in.readObject();
        }catch (IOException | ClassNotFoundException e){
            return null;
        }
    }
}

