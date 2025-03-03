package org.example;
import jdk.jfr.Frequency;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class UserInterface {
    private final Color darkRed = new Color(150, 0, 0);
    private final Color darkGreen = new Color(0, 128, 0);
    private final Color grey = new Color(52, 52, 52);
    private final Color lightBlack = new Color(135, 200, 160, 125);
    private final Color redBlack = new Color(55, 22, 22);
    private final JFrame mainFrame = BetterJFrame.BJFrame(1200, 800, Color.BLACK,true,null);
    private final JPanel managePanel = BetterJFrame.BJPanel(0, 0, 1200, 800, grey, false,null, mainFrame);
    private final JPanel cryptoInfoPanel = new JPanel();
    private final JScrollPane cryptoInfoScroll = new JScrollPane(cryptoInfoPanel);
    public List userData = new List();

    private final JPanel balancePanel = BetterJFrame.BJPanel(0, 100, 1200, 800, new Color(12, 12, 125), false, new FlowLayout(), managePanel);

    private final JButton cryptoPage = BetterJFrame.BJButton(0, 0, 300, 100 ,darkGreen, true, managePanel, "CRYPTOCURRENCIES");
    private final JButton stockPrices = BetterJFrame.BJButton(300, 0, 300, 100, darkGreen, true, managePanel, "STOCK MARKET");
    private final JButton logOut = BetterJFrame.BJButton(900, 0, 300, 100, darkGreen, true, managePanel, "LOGOUT");
    private final JButton currentBalance = BetterJFrame.BJButton(600, 0, 300, 100, darkGreen, true, managePanel, "YOUR ASSETS");

    private final JPanel toolsPanel = BetterJFrame.BJPanel(0,100,1200, 50, grey, true, new FlowLayout(), managePanel);

    private final JButton sortAsc = BetterJFrame.BJButton(0, 100, 150, 40, redBlack, true, toolsPanel, "SORT UP");
    private final JButton sortDesc = BetterJFrame.BJButton(0, 150, 150, 40, redBlack, true, toolsPanel, "SORT DOWN");
    private final JButton downloadButton = BetterJFrame.BJButton(0, 210, 150, 40, redBlack, true, toolsPanel, "DOWNLOAD");
    private final JButton historyButton = BetterJFrame.BJButton(0, 270, 150, 40, redBlack, false, toolsPanel, "HISTORY");
    private final JTextField searchInput = BetterJFrame.BJTextField(850,7, 80, 25, Color.white, true, toolsPanel, true, "", Color.BLACK);
    private final JButton searchIcon = BetterJFrame.BJButton(0, 0, 25, 25, darkRed, true, toolsPanel, "");

    private final JPanel historyPanel = BetterJFrame.BJPanel(100, 100, 1000, 800, lightBlack, false, new FlowLayout(), managePanel);
    private final JPanel analysisPanel = BetterJFrame.BJPanel(100, 200, 1000, 800, new Color(12, 128, 12), false, null, managePanel);

    private boolean isDownloadedCrypto = false;
    private boolean isDownloadedStock = false;
    private String currentPage = "neither";
    public void makeUserInterface() {
        historyPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        Background backgroundPanel = new Background("C:\\Users\\karol\\IdeaProjects\\CryptoTester\\src\\main\\java\\org\\example\\wallpaper.jpg");
        backgroundPanel.setVisible(true);
        backgroundPanel.setBounds(0,0, 1200, 800);
        JPanel registerPage = BetterJFrame.BJPanel(460, 235, 280, 280, new Color(255, 255, 199, 125), false, null, mainFrame);
        JPanel loginPage = BetterJFrame.BJPanel(460, 235, 280, 280, new Color(255, 255, 199, 125),true, null, mainFrame);
        mainFrame.add(backgroundPanel);
        cryptoInfoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 14, 14));
        cryptoInfoPanel.setBackground(grey);
        cryptoInfoScroll.setBounds(-10, 140, 1210, 740);
        cryptoInfoScroll.remove(cryptoInfoScroll.getVerticalScrollBar());
        cryptoInfoScroll.setLayout(new ScrollPaneLayout());
        cryptoInfoScroll.setBackground(grey);
        cryptoInfoScroll.addMouseWheelListener(e -> refreshMainFrame(mainFrame));
        cryptoInfoScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        cryptoInfoScroll.getVerticalScrollBar().setUnitIncrement(20);
        ImageIcon searchIconImage = new ImageIcon("C:\\Users\\karol\\IdeaProjects\\CryptoTester\\src\\main\\java\\org\\example\\lupa.png");
        searchIcon.setIcon(searchIconImage);
        sortAsc.setToolTipText("Sort ascending");
        sortAsc.setFont(new Font("Arial Regular", Font.BOLD, 17));
        sortAsc.setForeground(Color.WHITE);
        sortAsc.addActionListener(e -> {
            cryptoInfoPanel.removeAll();
            creatingBlocks(2, currentPage.equals("Crypto") ? !isDownloadedCrypto : !isDownloadedStock);
        });
        sortDesc.setToolTipText("Sort descending");
        sortDesc.setFont(new Font("Arial Regular", Font.BOLD, 17));
        sortDesc.addActionListener(e -> {
            cryptoInfoPanel.removeAll();
            creatingBlocks(1, !currentPage.equals("Crypto") ? !isDownloadedCrypto : !isDownloadedStock);
        });
        sortDesc.setForeground(Color.WHITE);
        downloadButton.setForeground(Color.white);
        downloadButton.setFont(new Font("Arial Regular", Font.BOLD, 17));
        downloadButton.addActionListener(e -> {
            balancePanel.removeAll();
            cryptoInfoPanel.removeAll();
            cryptoInfoPanel.setVisible(true);
            cryptoInfoScroll.setVisible(true);
            balancePanel.setVisible(false);
            managePanel.add(cryptoInfoScroll);
            try {
                Main main = new Main();
                cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1600));
                if (currentPage.equals("Crypto")) {
                    main.getCrypto();
                    isDownloadedCrypto = true;
                }
                else{
                    main.getStocks();
                    isDownloadedStock = true;
                }
                creatingBlocks(7, false);
            }
            catch(InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        historyButton.setForeground(Color.white);
        historyButton.setFont(new Font("Arial Regular", Font.BOLD, 17));
        historyButton.addActionListener(e -> {
            if(historyPanel.isVisible())
            {
                historyPanel.setVisible(false);
                cryptoInfoScroll.setVisible(true);
                historyPanel.removeAll();
                switchButtons(true);
                return;
            }
            switchButtons(false);
            analysisPanel.setVisible(false);
            historyPanel.setVisible(true);
            cryptoInfoScroll.setVisible(false);
            String type = currentPage;
            File[] files = FileOperations.getAllFiles(type);
            historyPanel.removeAll();
            for(File file : files)
            {
                JButton savedDataButton = BetterJFrame.BJButton(0, 0, 200, 60, redBlack, true, historyPanel, "");
                savedDataButton.setFont(new Font("Arial Regular", Font.PLAIN, 14));
                savedDataButton.setForeground(Color.WHITE);
                savedDataButton.addActionListener(e1 -> {
                    balancePanel.removeAll();
                    cryptoInfoPanel.removeAll();
                    cryptoInfoPanel.setVisible(true);
                    cryptoInfoScroll.setVisible(true);
                    balancePanel.setVisible(false);
                    managePanel.add(cryptoInfoScroll);
                    cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1600));
                    switchButtons(true);
                    if(type.equals("crypto"))
                    {
                        Main.prices = FileOperations.readFile(file.toString());
                    }else{
                        Main.stockPrices = FileOperations.readFile(file.toString());
                    }
                    creatingBlocks(2231, true);

                    historyPanel.setVisible(false);
                    isDownloadedCrypto = false;
                });
                String name = file.toString().substring(18).replaceAll(".txt", "").replaceAll("_", ":");
                savedDataButton.setText(name);
            }
        });
        searchInput.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                searchFunction();
            }
            public void removeUpdate(DocumentEvent e) {
                searchFunction();
            }
            public void insertUpdate(DocumentEvent e) {
                searchFunction();
            }});

            JTextField currUser = new JTextField("Username");
        logOut.setLayout(null);
        currUser.setBounds(110, 60, 80, 20);
        currUser.setHorizontalAlignment(0);
        currUser.setBackground(darkGreen);
        currUser.setEditable(false);
        currUser.setForeground(Color.WHITE);
        currUser.setFont(new Font("Arial Bold", Font.PLAIN, 13 ));
        currUser.setBorder(null);
        logOut.add(currUser);
        currUser.setText("Username");
        cryptoPage.setFont(new Font("Arial Bold", Font.PLAIN, 23 ));
        cryptoPage.setForeground(Color.WHITE);
        cryptoPage.addActionListener(e -> {
            offEverything();
            cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1600));
            cryptoPage.setBackground(Color.BLACK);
            currentBalance.setBackground(darkGreen);
            stockPrices.setBackground(darkGreen);
            currentPage = "Crypto";
            if(isDownloadedCrypto)
            {
                creatingBlocks(2231, false);
                return;
            }
            try{
                String plik = Arrays.stream(FileOperations.getAllFiles("crypto")).toList().get(0).toString();
                if(!plik.isEmpty())
                {
                    balancePanel.removeAll();
                    cryptoInfoPanel.removeAll();
                    cryptoInfoPanel.setVisible(true);
                    cryptoInfoScroll.setVisible(true);
                    balancePanel.setVisible(false);
                    managePanel.add(cryptoInfoScroll);
                    cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1600));
                    Main.prices = FileOperations.readFile(plik);
                    assert Main.prices != null;
                    creatingBlocks( 2231, true);
                }
            }catch (ArrayIndexOutOfBoundsException e1)
            {
                System.out.println(e1.getMessage());
            }
        });
        stockPrices.setForeground(Color.WHITE);
        stockPrices.setFont(new Font("Arial Bold", Font.PLAIN, 23 ));
        stockPrices.addActionListener(e -> {
            offEverything();
            cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1000));
            stockPrices.setBackground(Color.BLACK);
            currentBalance.setBackground(darkGreen);
            cryptoPage.setBackground(darkGreen);
            currentPage = "Stock";
            if(isDownloadedStock)
            {
                creatingBlocks(2231, false);
                return;
            }
            try{
                String file = Arrays.stream(FileOperations.getAllFiles("stocks")).toList().get(0).toString();
                if(!file.isEmpty())
                {
                    balancePanel.removeAll();
                    cryptoInfoPanel.removeAll();
                    cryptoInfoPanel.setVisible(true);
                    cryptoInfoScroll.setVisible(true);
                    balancePanel.setVisible(false);
                    managePanel.add(cryptoInfoScroll);
                    cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1600));
                    Main.stockPrices = FileOperations.readFile(file);
                    assert Main.stockPrices != null;
                    creatingBlocks(2231, true);
                }
            }catch (ArrayIndexOutOfBoundsException e1)
            {
                System.out.println(e1.getMessage());
            }
        });
        logOut.setForeground(Color.WHITE);
        logOut.setFont(new Font("Arial Bold", Font.PLAIN, 23 ));
        logOut.addActionListener(e -> {
            offEverything();
            loginPage.setVisible(true);
            managePanel.setVisible(false);
            currentPage = "anything";
        });
        currentBalance.setFont(new Font("Arial Bold", Font.PLAIN, 23 ));
        currentBalance.setForeground(Color.WHITE);
        currentBalance.addActionListener(e ->{
            offEverything();
            currentPage = "anything";
            balancePanel.setVisible(true);
            currentBalance.setBackground(Color.BLACK);
            cryptoPage.setBackground(darkGreen);
            stockPrices.setBackground(darkGreen);
            for (Map.Entry<String, Double> entry : Main.saldoData.entrySet()){
                String key = entry.getKey();
                Double value = entry.getValue();
                if(value == 0){continue;}
                JPanel saldoInfoPanel = new JPanel();
                saldoInfoPanel.setBackground(Color.BLACK);
                saldoInfoPanel.setPreferredSize(new Dimension(200, 60));
                saldoInfoPanel.setLayout(null);
                JTextField textSaldo = new JTextField();
                textSaldo.setBounds(25, 15,140, 30);
                textSaldo.setText(key + ": " + String.format("%.5f", value));
                textSaldo.setFont(new Font("Arial Bold", Font.PLAIN, 15));
                textSaldo.setEditable(false);
                textSaldo.setHorizontalAlignment(JTextField.CENTER);
                saldoInfoPanel.add(textSaldo);
                balancePanel.add(saldoInfoPanel);
            }
        });
        JTextField loginText = BetterJFrame.BJTextField(40, 50, 200, 25, grey, true, loginPage, true, "Username", null);
        loginText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                loginText.setText("");
            }
        });
        loginText.setForeground(Color.WHITE);
        loginText.setFont(new Font("Arial regular", Font.PLAIN, 15));
        loginText.setBorder(null);
        JPasswordField passwordText = new JPasswordField("Password");
        passwordText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordText.setText("");
            }
        });
        passwordText.setBounds(40, 100, 200, 25);
        passwordText.setBackground(grey);
        passwordText.setForeground(Color.WHITE);
        passwordText.setFont(new Font("Arial regular", Font.PLAIN, 15));
        passwordText.setBorder(null);

        JTextField loginDataError = BetterJFrame.BJTextField(40, 250, 200, 25, darkRed, false, loginPage, false, "Wrong Data", Color.WHITE);
        JButton loginButton = BetterJFrame.BJButton(40, 150, 200, 25, grey, true, loginPage, "LOGIN");
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial regular", Font.PLAIN, 15));
        loginButton.addActionListener(e -> {
            userData.add(loginText.getText(), 0);
            userData.add(passwordText.getText(), 1);
            loginDataError.setVisible(true);
            List dataFromFile = FileOperations.readFile(userData);
            if(userData.getItem(0).isEmpty() || userData.getItem(1).isEmpty()){return;}
            if(dataFromFile != null){
                if (userData.getItem(0).equals(dataFromFile.getItem(0)) && userData.getItem(1).equals(dataFromFile.getItem(1))){
                    loginPage.setVisible(false);
                    loginDataError.setVisible(false);
                    managePanel.setVisible(true);
                    currUser.setText(loginText.getText());
                    String plik = Arrays.stream(FileOperations.getAllFiles("crypto")).toList().get(0).toString();
                    if(!plik.isEmpty())
                    {
                        balancePanel.removeAll();
                        cryptoInfoPanel.removeAll();
                        cryptoInfoPanel.setVisible(true);
                        cryptoInfoScroll.setVisible(true);
                        balancePanel.setVisible(false);
                        managePanel.add(cryptoInfoScroll);
                        cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1600));
                        Map<String, Double> isNull = FileOperations.readFile(plik);
                        if (isNull != null){
                            Main.prices = FileOperations.readFile(plik);
                        }
                        assert Main.prices != null;
                        currentPage = "Crypto";
                        creatingBlocks(2231, true);
                    }
                    refreshMainFrame(mainFrame);
                }
            }
        });
        JButton registerPageButton = BetterJFrame.BJButton(40, 200, 200, 25, grey, true, loginPage, "REGISTER");
        registerPageButton.setForeground(Color.WHITE);
        registerPageButton.setFont(new Font("Arial regular", Font.PLAIN, 15));
        registerPageButton.addActionListener(e -> {
            loginPage.setVisible(false);
            registerPage.setVisible(true);
            loginDataError.setVisible(false);
        });
        loginPage.add(passwordText);
        JTextField loginTextRegister = BetterJFrame.BJTextField(40, 50, 200, 25, Color.WHITE, true, registerPage, true, "Username", null);
        loginTextRegister.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                loginTextRegister.setText("");
            }
        });
        JPasswordField passwordTextRegister = new JPasswordField();
        passwordTextRegister.setText("Password");
        passwordTextRegister.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordTextRegister.setText("");
            }
        });
        JButton backButton = BetterJFrame.BJButton(0, 0, 40, 40, Color.red, true, registerPage, "<");
        backButton.setForeground(Color.white);
        backButton.setBorder(null);
        backButton.setFont(new Font("Arial", Font.PLAIN, 22));
        passwordTextRegister.setBounds(40, 100, 200, 25);
        JTextField registerDataError = BetterJFrame.BJTextField(40, 200, 200, 25, darkRed, false, registerPage, false, "Try Again", Color.WHITE);
        JButton registerButton = BetterJFrame.BJButton(40, 150, 200, 25, grey, true, registerPage, "REGISTER");
        backButton.addActionListener(e ->{
            registerPage.setVisible(false);
            loginPage.setVisible(true);
            registerDataError.setVisible(false);
        });
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial regular", Font.PLAIN, 15));
        registerButton.addActionListener(e -> {
            List data = new List();
            data.add(loginTextRegister.getText(), 0);
            data.add(passwordTextRegister.getText(), 1);
            registerDataError.setVisible(true);
            if(data.getItem(0).isEmpty() || data.getItem(1).isEmpty()){return;}
            Main.makeSaldo();
            if(FileOperations.createFile(data, true)){
                registerPage.setVisible(false);
                loginPage.setVisible(true);
                registerDataError.setVisible(false);
            }
        });
        registerPage.add(passwordTextRegister);
        refreshMainFrame(mainFrame);
    }
    public void createCryptoBlock(double value, String name, boolean isHistory){
        if(name.equals("failed"))return;
        JPanel cryptoPanel = BetterJFrame.BJPanel(0, 0, 150, 120, lightBlack, true, null, cryptoInfoPanel);
        JTextField cryptoText = BetterJFrame.BJTextField(5, 15, 140, 20, Color.white, true, cryptoPanel, false,name + " : " + String.format("%.5f", value), Color.WHITE);
        cryptoText.setFont(new Font("Arial regular", Font.PLAIN, 13 ));
        cryptoText.setBackground(redBlack);
        cryptoText.setHorizontalAlignment(0);
        JButton buyButton = BetterJFrame.BJButton(10, 45 , 65, 30, darkGreen, true, cryptoPanel, "BUY");
        buyButton.setForeground(Color.WHITE);
        buyButton.addActionListener(e -> {
            currentBalance.setEnabled(false);
            cryptoPage.setEnabled(false);
            logOut.setEnabled(false);
            stockPrices.setEnabled(false);
            cryptoInfoScroll.setVisible(false);
            JPanel buyPanel = BetterJFrame.BJPanel(450, 250, 350, 110, new Color(180, 2, 2), true, null, managePanel);
            JTextField amount = BetterJFrame.BJTextField(195, 30, 80, 50, Color.WHITE, true, buyPanel, true, null , null);
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
            JButton maxAmount = BetterJFrame.BJButton(105, 30, 80, 50, Color.WHITE, true, buyPanel, "Max");
            maxAmount.addActionListener(e1 -> amount.setText(Double.toString(Main.saldoData.get("Money")/value)));
            JButton exitButton = BetterJFrame.BJButton(285, 30, 45, 45, darkRed, true, buyPanel, "X");
            exitButton.setForeground(Color.WHITE);
            exitButton.addActionListener(e1 -> {
                buyPanel.revalidate();
                buyPanel.repaint();
                amount.setText("");
                buyPanel.setVisible(false);
                cryptoInfoScroll.setVisible(true);
                currentBalance.setEnabled(true);
                cryptoPage.setEnabled(true);
                logOut.setEnabled(true);
                stockPrices.setEnabled(true);
            });
            JTextField valueError = BetterJFrame.BJTextField(105, 82, 135, 20, Color.BLACK, false, buyPanel, false, "NOT ENOUGH MONEY", Color.WHITE);
            JButton confirm = BetterJFrame.BJButton(15, 30, 80, 50,Color.WHITE, true, buyPanel, "Confirm");
            confirm.addActionListener(e2 ->{
                double maxValue = Main.saldoData.get("Money")/value;
                try{
                    double inputAmount = Double.parseDouble(amount.getText());
                    if (inputAmount > maxValue){
                        valueError.setVisible(true);
                        return;}
                }catch(NumberFormatException e3){
                    valueError.setVisible(true);
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
                    FileOperations.readFile(userData);
                    buyPanel.setVisible(false);
                    cryptoInfoScroll.setVisible(true);
                    amount.setText("");
                    currentBalance.setEnabled(true);
                    cryptoPage.setEnabled(true);
                    logOut.setEnabled(true);
                    stockPrices.setEnabled(true);
                }
            });
        });
        JButton sellButton = BetterJFrame.BJButton(75, 45 , 65, 30, darkRed, true, cryptoPanel, "SELL");
        sellButton.setForeground(Color.WHITE);
        sellButton.addActionListener(e -> {
            currentBalance.setEnabled(false);
            cryptoPage.setEnabled(false);
            logOut.setEnabled(false);
            stockPrices.setEnabled(false);
            cryptoInfoScroll.setVisible(false);
            JPanel sellPanel = BetterJFrame.BJPanel(450, 250, 350, 110, new Color(180, 2, 2), true, null, managePanel);
            JButton exitButton = BetterJFrame.BJButton(280, 30, 45, 45, darkRed, true, sellPanel, "X");
            exitButton.setForeground(Color.WHITE);
            exitButton.addActionListener(e2 -> {
                sellPanel.setVisible(false);
                cryptoInfoScroll.setVisible(true);
                currentBalance.setEnabled(true);
                cryptoPage.setEnabled(true);
                logOut.setEnabled(true);
                stockPrices.setEnabled(true);
            });
            JTextField amount = BetterJFrame.BJTextField(190, 30, 80, 50, Color.WHITE, true, sellPanel, true, null, null);
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
            JButton maxAmount = BetterJFrame.BJButton(100, 30, 80, 50, Color.WHITE, true, sellPanel, "Max");
            maxAmount.addActionListener(e1 ->
                    amount.setText(Double.toString(Main.saldoData.get(name))));
            JButton confirm = BetterJFrame.BJButton(10, 30, 80, 50,Color.WHITE, true, sellPanel, "Confirm");
            JTextField valueError = BetterJFrame.BJTextField(110, 82, 140, 20, Color.BLACK, false, sellPanel, false, "NOT ENOUGH ASSETS", Color.WHITE);
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
                    FileOperations.readFile(userData);
                    sellPanel.setVisible(false);
                    cryptoInfoScroll.setVisible(true);
                    amount.setText("");
                    currentBalance.setEnabled(true);
                    cryptoPage.setEnabled(true);
                    logOut.setEnabled(true);
                    stockPrices.setEnabled(true);
                }
                });
            });
        JButton exitAnalysisButton = BetterJFrame.BJButton(0, 0, 40, 40, Color.red, true, analysisPanel, "X");
        exitAnalysisButton.setFont(new Font("Arial Bold", Font.PLAIN, 9));
        exitAnalysisButton.addActionListener(e1 ->{
            analysisPanel.setVisible(false);
            cryptoInfoScroll.setVisible(true);
            analysisPanel.removeAll();
            analysisPanel.add(exitAnalysisButton);
            switchButtons(true);
            historyButton.setEnabled(true);
        });
        JButton chartButton = BetterJFrame.BJButton(35, 80, 80, 35, redBlack, true, cryptoPanel, "CHART");
        chartButton.setRolloverEnabled(false);
        chartButton.setForeground(Color.WHITE);
        chartButton.addActionListener(e -> {
            if(analysisPanel.isVisible())
            {
                analysisPanel.setVisible(false);
                cryptoInfoScroll.setVisible(true);
                analysisPanel.removeAll();
                return;
            }
            switchButtons(false);
            historyButton.setEnabled(false);
            cryptoInfoScroll.setVisible(false);
            analysisPanel.setVisible(true);
            String type = Main.prices.containsKey(name) || name.equals("BTC") ? "crypto" : "stocks";
            File[] files = FileOperations.getAllFiles(type);
            managePanel.setComponentZOrder(analysisPanel, 0);
            Map<String, Double> map = new LinkedHashMap<>();
            for(File file :files)
            {
                try {
                    double valueFromFile = Objects.requireNonNull(FileOperations.readFile(file.toString())).get(name);
                    map.put(file.toString(), valueFromFile);
                } catch (NullPointerException z) {
                    z.getMessage();
                }
            }
            Collection<Double> values = map.values();
            double minValue = Collections.min(values);
            double maxValue = Collections.max(values);
            int i = 0;
            BetterJFrame.BJTextField(915, 500, 80, 25,Color.BLACK, true, analysisPanel, false, String.format("%.3f",minValue), Color.WHITE);
            BetterJFrame.BJTextField(915, 12, 80, 25,Color.BLACK, true, analysisPanel, false, String.format("%.3f",maxValue), Color.WHITE);
            for (Map.Entry<String, Double> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Double vall = entry.getValue();
                    double val = (vall - minValue) / (maxValue - minValue);
                    int y = 20 + (int) (460 - 460 * val);
                    Color color = i % 2 == 0 ? Color.green : Color.black;
                    JButton date = BetterJFrame.BJButton(40 + (860 / values.size()) * i, 520, 860/values.size(), 15, Color.black, true, analysisPanel, "");
                    String formattedDate = key.replace(".txt", "").replace("cryptoHistoryData", "").replace("stocksHistoryData", "").replace("_", ":");
                    date.setToolTipText(formattedDate);
                    JButton valueAtDate = BetterJFrame.BJButton(40 + (860 / values.size()) * i, y, (860 / values.size()), 520 - y, color, true, analysisPanel, "");
                    valueAtDate.setToolTipText(vall.toString());
                    i++;
                }
                refreshMainFrame(mainFrame);
        });
        if(isHistory)
        {
            sellButton.setEnabled(false);
            buyButton.setEnabled(false);
            historyButton.setVisible(true);
        }
        refreshMainFrame(mainFrame);
    }
    private Comparator<? super Map.Entry<String, Double>> selectSort(int sort)
    {
        if(sort == 1){
            return Map.Entry.comparingByValue();
        }else if(sort == 2){
            return Collections.reverseOrder(Map.Entry.comparingByValue());
        }else{
            return Comparator.comparing(e -> 0);
        }
    }
    private void offEverything()
    {
        analysisPanel.removeAll();
        analysisPanel.setVisible(false);
        historyPanel.removeAll();
        historyPanel.setVisible(false);
        balancePanel.removeAll();
        analysisPanel.setVisible(false);
        analysisPanel.removeAll();
        cryptoInfoPanel.removeAll();
        cryptoInfoPanel.setVisible(true);
        cryptoInfoScroll.setVisible(true);
        balancePanel.setVisible(false);
    }
    // any normal, 1 desc, 2 asc
    private void creatingBlocks( int sort, boolean isHistory){
        LinkedHashMap<String, Double> sortedMap;
        Map<String, Double> map = currentPage.equals("Crypto") ? Main.prices : Main.stockPrices;
        sortedMap = map.entrySet()
                .stream()
                .sorted(selectSort(sort))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            if(value == 0.0){continue;}
            createCryptoBlock(value, key, isHistory);
            }
        }
    private static void refreshMainFrame(JFrame frame){
        frame.revalidate();
        frame.repaint();
    }
    private void switchButtons(boolean bool)
    {
        sortAsc.setEnabled(bool);
        sortDesc.setEnabled(bool);
        downloadButton.setEnabled(bool);
    }
    public void searchFunction()
    {
        boolean page = currentPage.equals("Crypto");
        Set<Map.Entry<String, Double>> entrySet = page ? Main.prices.entrySet() : Main.stockPrices.entrySet();
        boolean cryptoOrStock = page ? !isDownloadedCrypto : !isDownloadedStock;
        cryptoInfoPanel.removeAll();
        for (Map.Entry<String, Double> entry : entrySet) {
            String key = entry.getKey();
            Double value = entry.getValue();
            if(value == 0.0){continue;}
            if(!key.contains(searchInput.getText())){continue;}
            createCryptoBlock(value, key, cryptoOrStock);
        }
    }
}