package org.example;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class FileOperations {
    public static String reservedCharsRegex = "[<>\":/\\\\|?*]";
    public static boolean createFile(String[] data, boolean create){
        String fileNameforWin = data[0].replaceAll(reservedCharsRegex, "") + "File.txt";
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
    public static String[] readFile(String[] data, JTextArea saldoText){
        String fileNameforWin = data[0].replaceAll(reservedCharsRegex, "") + "File.txt";
        fileNameforWin = fileNameforWin.replaceAll("\t", "");
        File test = new File(fileNameforWin);
        if(!test.exists()){
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNameforWin))) {
            String[] readArray = (String[]) in.readObject();
            Map<String, Double> readDataMap = (Map<String, Double>) in.readObject();
            for (String key : readDataMap.keySet()) {
                Main.saldoData.put(key, readDataMap.get(key));
            }
            saldoText.setText("Money: " + String.format("%.5f", readDataMap.get("Money")));
            return readArray;
        } catch (IOException | ClassNotFoundException e) {
            return null;
    }




}}

