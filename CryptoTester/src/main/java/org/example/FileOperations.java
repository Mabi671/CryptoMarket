package org.example;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Map;


public class FileOperations {
    public static String reservedCharsRegex = "[<>\":/\\\\|?*]";
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
    public static List readFile(List data, JTextField saldoText){
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
            saldoText.setText("Money: " + String.format("%.5f", readDataMap.get("Money")));
            return readArray;
        } catch (IOException | ClassNotFoundException e) {
            return null;
    }




}}

