package org.example;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserInterface {
    private final Color darkRed = new Color(150, 0, 0);
    private final JFrame mainFrame = BetterJFrame.BJFrame(1200, 800, Color.BLACK,true,null);
    private final JPanel managePanel = BetterJFrame.BJPanel(0, 0, 1200, 800, new Color(0, 155, 0), false, null, mainFrame);
    private final JPanel cryptoInfoPanel = new JPanel();
    private final JScrollPane cryptoInfoScroll = new JScrollPane(cryptoInfoPanel);
    protected JTextField saldoAmountText = BetterJFrame.BJTextField(300, 10, 215, 30, darkRed, true, managePanel, false);
    public List userData = new List();
    private final JPanel saldoPanel = BetterJFrame.BJPanel(0, 200, 1200, 800, Color.blue, false, new FlowLayout(), managePanel);
    public JFrame makeUserInterface() {
        JPanel registerPage = BetterJFrame.BJPanel(400, 175, 400, 400, Color.BLACK, false, null, mainFrame);
        JPanel loginPage = BetterJFrame.BJPanel(400, 175, 400, 400, Color.black,true, null, mainFrame);
        cryptoInfoPanel.setLayout(new FlowLayout());
        cryptoInfoPanel.setBackground(Color.BLUE);
        cryptoInfoScroll.setBounds(0, 200, 1185, 800);
        cryptoInfoScroll.setLayout(new ScrollPaneLayout());
        cryptoInfoScroll.setBackground(Color.BLUE);
        cryptoInfoScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        cryptoInfoScroll.getVerticalScrollBar().setUnitIncrement(20);
        JButton sortAsc = BetterJFrame.BJButton(50, 100, 200, 25, darkRed, false, managePanel, "HIGHEST");
        sortAsc.setForeground(Color.WHITE);
        sortAsc.addActionListener(e -> {
            cryptoInfoPanel.removeAll();
            double bitcoinPrice = Main.prices.get("USDT");
            LinkedHashMap<String, Double> sortedMap = Main.prices.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                if(key.equals("USDT")){
                    createCryptoBlock(bitcoinPrice, "BTC");
                }else{
                    value *= bitcoinPrice;
                    createCryptoBlock(value, key);
                }
            }
        });
        JButton sortAscStocks = BetterJFrame.BJButton(260, 100, 200, 25, darkRed, false, managePanel, "HIGHEST");
        sortAscStocks.setForeground(Color.WHITE);
        sortAscStocks.addActionListener(e -> {
            cryptoInfoPanel.removeAll();
            LinkedHashMap<String, Double> sortedMap = Main.stockPrices.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                createCryptoBlock(value, key);
            }
        });
        JButton sortDescStocks = BetterJFrame.BJButton(260, 125, 200, 25, darkRed, false, managePanel, "LOWEST");
        sortDescStocks.setForeground(Color.WHITE);
        sortDescStocks.addActionListener(e1 -> {
            cryptoInfoPanel.removeAll();
            LinkedHashMap<String, Double> sortedMap = Main.stockPrices.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                createCryptoBlock(value, key);
            }
        });
        JButton sortDesc = BetterJFrame.BJButton(50, 125, 200, 25, darkRed, false, managePanel, "LOWEST");
        sortDesc.addActionListener(e -> {
            cryptoInfoPanel.removeAll();
            double bitcoinPrice = Main.prices.get("USDT");
            LinkedHashMap<String, Double> sortedMap = Main.prices.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                if(key.equals("USDT")){
                    createCryptoBlock(bitcoinPrice, "BTC");
                }else{
                    value *= bitcoinPrice;
                    createCryptoBlock(value, key);
                }
            }
        });

        sortDesc.setForeground(Color.WHITE);
        JButton trade = BetterJFrame.BJButton(50, 50, 200, 50, darkRed, true, managePanel, "GET PRICES");
        trade.setForeground(Color.WHITE);
        trade.addActionListener(e -> {
            try {
                saldoPanel.removeAll();
                cryptoInfoPanel.removeAll();
                cryptoInfoPanel.setVisible(true);
                cryptoInfoScroll.setVisible(true);
                saldoPanel.setVisible(false);
                managePanel.add(cryptoInfoScroll);
                cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1600));
                sortAsc.setVisible(true);
                sortDesc.setVisible(true);
                sortAscStocks.setVisible(false);
                sortDescStocks.setVisible(false);
                Main main = new Main();
                main.getData();
                double bitcoinPrice = 1/Main.prices.get("USDT");
                Main.prices.put("USDT", bitcoinPrice);
                for (Map.Entry<String, Double> entry : Main.prices.entrySet()) {
                    String key = entry.getKey();
                    Double value = entry.getValue();
                    if(key.equals("USDT")){
                        createCryptoBlock(bitcoinPrice, "BTC");
                    }else{
                        value *= bitcoinPrice;
                        createCryptoBlock(value, key);
                    }
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        JButton stockPrices = BetterJFrame.BJButton(260, 50, 200, 50, darkRed, true, managePanel, "GET STOCKS");
        stockPrices.setForeground(Color.WHITE);
        stockPrices.addActionListener(e -> {
            try{
                saldoPanel.removeAll();
                cryptoInfoPanel.removeAll();
                cryptoInfoPanel.setVisible(true);
                cryptoInfoScroll.setVisible(true);
                saldoPanel.setVisible(false);
                managePanel.add(cryptoInfoScroll);
                cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1000));
                sortAsc.setVisible(false);
                sortDesc.setVisible(false);
                sortAscStocks.setVisible(true);
                sortDescStocks.setVisible(true);
                Main main = new Main();
                main.getStocks();
                for (Map.Entry<String, Double> entry : Main.stockPrices.entrySet()) {
                    String key = entry.getKey();
                    Double value = entry.getValue();
                    createCryptoBlock(value, key);
                }
            }catch(ConcurrentModificationException ex){
                System.out.println(ex.getMessage());
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }

        });
        JButton logOut = BetterJFrame.BJButton(900, 50, 200, 50, Color.darkGray, true, managePanel, "LOGOUT");
        logOut.setForeground(Color.WHITE);
        logOut.addActionListener(e -> {
            loginPage.setVisible(true);
            managePanel.setVisible(false);
            saldoPanel.removeAll();
            cryptoInfoPanel.removeAll();
            cryptoInfoPanel.setVisible(false);
            cryptoInfoScroll.setVisible(false);
            sortAsc.setVisible(false);
            sortDesc.setVisible(false);
            sortAscStocks.setVisible(false);
            sortDescStocks.setVisible(false);
            saldoPanel.setVisible(false);
        });
        JButton currSaldo = BetterJFrame.BJButton(600, 50, 200, 50, darkRed, true, managePanel, "YOUR ASSETS");
        currSaldo.setForeground(Color.WHITE);
        currSaldo.addActionListener(e ->{
            cryptoInfoScroll.setVisible(false);
            sortAsc.setVisible(false);
            sortDesc.setVisible(false);
            sortAscStocks.setVisible(false);
            sortDescStocks.setVisible(false);
            cryptoInfoPanel.removeAll();
            saldoPanel.setVisible(true);
            for (Map.Entry<String, Double> entry : Main.saldoData.entrySet()){
                String key = entry.getKey();
                Double value = entry.getValue();
                if(value == 0){continue;}
                if(key.equals("Money")){continue;}
                JPanel saldoInfoPanel = new JPanel();
                saldoInfoPanel.setBackground(Color.black);
                saldoInfoPanel.setPreferredSize(new Dimension(150, 40));
                JTextField textSaldo = new JTextField();
                textSaldo.setBounds(5, 5, 140, 40);
                textSaldo.setText(key + ": " + String.format("%.5f", value));
                textSaldo.setEditable(false);
                saldoInfoPanel.add(textSaldo);
                saldoPanel.add(saldoInfoPanel);
            }
        });
        saldoAmountText.setForeground(Color.WHITE);
        saldoAmountText.setText("Money: " + "0");
        saldoAmountText.setFont(new Font("Arial", Font.BOLD, 25));
        JTextField loginText = BetterJFrame.BJTextField(100, 100, 200, 25, Color.WHITE, true, loginPage, true);
        loginText.setToolTipText("Username");
        JPasswordField passwordText = new JPasswordField();
        passwordText.setToolTipText("Password");
        passwordText.setBounds(100, 150, 200, 25);
        JButton loginButton = BetterJFrame.BJButton(100, 200, 200, 25, Color.WHITE, true, loginPage, "LOGIN");
        JTextField loginDataError = BetterJFrame.BJTextField(100, 300, 200, 25, darkRed, false, loginPage, false);
        loginDataError.setText("Wrong Data");
        loginDataError.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> {
            userData.add(loginText.getText(), 0);
            userData.add(passwordText.getText(), 1);
            loginDataError.setVisible(true);
            List dataFromFile = FileOperations.readFile(userData, saldoAmountText);
            if(userData.getItem(0).equals("") || userData.getItem(1).equals("")){return;}
            if(dataFromFile != null){
                if (userData.getItem(0).equals(dataFromFile.getItem(0)) && userData.getItem(1).equals(dataFromFile.getItem(1))){
                    loginPage.setVisible(false);
                    loginDataError.setVisible(false);
                    managePanel.setVisible(true);
                    refreshMainFrame(mainFrame);
                    System.out.println(userData.getItem(1));
                }
            }
        });
        JButton registerPageButton = BetterJFrame.BJButton(100, 250, 200, 25, Color.WHITE, true, loginPage, "REGISTER");
        registerPageButton.addActionListener(e -> {
            loginPage.setVisible(false);
            registerPage.setVisible(true);
            loginDataError.setVisible(false);
        });
        loginPage.add(passwordText);
        JTextField loginTextRegister = BetterJFrame.BJTextField(100, 100, 200, 25, Color.WHITE, true, registerPage, true);
        loginTextRegister.setToolTipText("Username");
        JPasswordField passwordTextRegister = new JPasswordField();
        passwordTextRegister.setToolTipText("Password");
        passwordTextRegister.setBounds(100, 150, 200, 25);
        JButton registerButton = BetterJFrame.BJButton(100, 200, 200, 25, Color.WHITE, true, registerPage, "REGISTER");
        JTextField registerDataError = BetterJFrame.BJTextField(100, 250, 200, 25, darkRed, false, registerPage, false);
        registerDataError.setText("Try Again");
        registerDataError.setForeground(Color.WHITE);
        registerButton.addActionListener(e -> {
            List data = new List();
            data.add(loginTextRegister.getText(), 0);
            data.add(passwordTextRegister.getText(), 1);
            registerDataError.setVisible(true);
            if(data.getItem(0).equals("") || data.getItem(1).equals("")){return;}
            Main.makeSaldo();
            if(FileOperations.createFile(data, true)){
                registerPage.setVisible(false);
                loginPage.setVisible(true);
                registerDataError.setVisible(false);
            }
        });
        registerPage.add(passwordTextRegister);
        refreshMainFrame(mainFrame);
        return mainFrame;
    }
    public void createCryptoBlock(double value, String name){
        JPanel cryptoPanel = BetterJFrame.BJPanel(0, 0, 150, 100, Color.black, true, null, cryptoInfoPanel);
        JTextField cryptoText = BetterJFrame.BJTextField(5, 20, 140, 20, Color.white, true, cryptoPanel, false);
        cryptoText.setText(name + " : " + String.format("%.5f", value));
        JButton buyButton = BetterJFrame.BJButton(5, 50 , 65, 30, new Color(0, 155, 0), true, cryptoPanel, "BUY");
        buyButton.setForeground(Color.WHITE);
        buyButton.addActionListener(e -> {
            cryptoInfoScroll.setVisible(false);
            JPanel buyPanel = BetterJFrame.BJPanel(450, 250, 350, 110, Color.black, true, null, managePanel);
            JButton exitButton = BetterJFrame.BJButton(280, 10, 45, 45, darkRed, true, buyPanel, "X");
            exitButton.setForeground(Color.WHITE);
            exitButton.addActionListener(e1 -> {
                buyPanel.setVisible(false);
                cryptoInfoScroll.setVisible(true);
            });
            JTextField amount = BetterJFrame.BJTextField(190, 10, 80, 50, Color.WHITE, true, buyPanel, true);
            amount.addActionListener(e1 -> {
                double maxValue = Main.saldoData.get("Money")/value;
                if (Double.parseDouble(amount.getText()) > maxValue){
                    amount.setText(Double.toString(maxValue));
                }
            });
            amount.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                        e.consume();
                    }
                }
            });
            JButton maxAmount = BetterJFrame.BJButton(100, 10, 80, 50, Color.WHITE, true, buyPanel, "Max");
            maxAmount.addActionListener(e1 -> {
                amount.setText(Double.toString(Main.saldoData.get("Money")/value));
            });
            JButton confirm = BetterJFrame.BJButton(10, 10, 80, 50,Color.WHITE, true, buyPanel, "Confirm");
            JTextField valueError = BetterJFrame.BJTextField(107, 70, 135, 35, darkRed, false, buyPanel, false);
            valueError.setText("NOT ENOUGH MONEY");
            valueError.setForeground(Color.WHITE);
            confirm.addActionListener(e2 ->{
                double maxValue = Main.saldoData.get("Money")/value;
                try{
                    double inputAmount = Double.parseDouble(amount.getText());
                    if (inputAmount > maxValue){
                        valueError.setVisible(true);
                        return;}
                }catch(NumberFormatException e3){
                    return;
                }
                valueError.setVisible(false);
                double amountDouble = Double.parseDouble(amount.getText());
                if(amountDouble != 0){
                    try{
                        Main.saldoData.put(name, Main.saldoData.get(name) + amountDouble);
                    }catch(NullPointerException e6){
                        Main.saldoData.put(name, amountDouble);
                    }
                    double calculatedMoney = Main.saldoData.get("Money") - amountDouble * value;
                    if(calculatedMoney < 0){
                        calculatedMoney = 0.0;}
                    Main.saldoData.put("Money", calculatedMoney);
                    FileOperations.createFile(userData,false);
                    FileOperations.readFile(userData, saldoAmountText);
                    buyPanel.setVisible(false);
                    cryptoInfoScroll.setVisible(true);
                    amount.setText("");
                }
            });
        });
        JButton sellButton = BetterJFrame.BJButton(70, 50 , 65, 30, darkRed, true, cryptoPanel, "SELL");
        sellButton.setForeground(Color.WHITE);
        sellButton.addActionListener(e -> {
            cryptoInfoScroll.setVisible(false);
            JPanel sellPanel = BetterJFrame.BJPanel(450, 250, 350, 110, Color.black, true, null, managePanel);
            JButton exitButton = BetterJFrame.BJButton(280, 10, 45, 45, darkRed, true, sellPanel, "X");
            exitButton.setForeground(Color.WHITE);
            exitButton.addActionListener(e2 -> {
                sellPanel.setVisible(false);
                cryptoInfoScroll.setVisible(true);
            });
            JTextField amount = BetterJFrame.BJTextField(190, 10, 80, 50, Color.WHITE, true, sellPanel, true);
            amount.addActionListener(e1 -> {
                double maxValue = Main.saldoData.get(name);
                if (Double.parseDouble(amount.getText()) > maxValue){
                    amount.setText(Double.toString(maxValue));
                }
            });
            amount.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                        e.consume();
                    }
                }
            });
            JButton maxAmount = BetterJFrame.BJButton(100, 10, 80, 50, Color.WHITE, true, sellPanel, "Max");
            maxAmount.addActionListener(e1 -> {
                amount.setText(Double.toString(Main.saldoData.get(name)));
            });
            JButton confirm = BetterJFrame.BJButton(10, 10, 80, 50,Color.WHITE, true, sellPanel, "Confirm");
            JTextField valueError = BetterJFrame.BJTextField(110, 70, 140, 35, darkRed, false, sellPanel, false);
            valueError.setText("NOT ENOUGH CRYPTO");
            valueError.setForeground(Color.WHITE);
            confirm.addActionListener(e2 ->{
                double maxValue = Main.saldoData.get(name);
                try{
                    double inputAmount = Double.parseDouble(amount.getText());
                    if (inputAmount > maxValue){
                        valueError.setVisible(true);
                        return;
                    }
                }catch(NumberFormatException e3){
                    return;
                }
                valueError.setVisible(false);
                double amountDouble = Double.parseDouble(amount.getText());
                if(amountDouble != 0){
                    Main.saldoData.put(name, Main.saldoData.get(name) - amountDouble);
                    Main.saldoData.put("Money", Main.saldoData.get("Money") + amountDouble * value);
                    FileOperations.createFile(userData,false);
                    FileOperations.readFile(userData, saldoAmountText);
                    sellPanel.setVisible(false);
                    cryptoInfoScroll.setVisible(true);
                    amount.setText("");
                }
            });
        });
        refreshMainFrame(mainFrame);
    }
    public static void refreshMainFrame(JFrame frame){
        frame.revalidate();
        frame.repaint();
    }
}