package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


public class UserInterface {
    private final Color darkRed = new Color(150, 0, 0);
    private final JFrame mainFrame = BetterJFrame.BJFrame(1200, 800, Color.BLACK,true,null);
    private final JPanel managePanel = BetterJFrame.BJPanel(0, 0, 1200, 800, new Color(0, 0, 0), false, null, mainFrame);
    private final JPanel cryptoInfoPanel = new JPanel();
    private final JScrollPane cryptoInfoScroll = new JScrollPane(cryptoInfoPanel);
    public List userData = new List();
    private final JPanel saldoPanel = BetterJFrame.BJPanel(0, 100, 1200, 800, new Color(12, 12, 125), false, new FlowLayout(), managePanel);
    private final JButton sortAsc = BetterJFrame.BJButton(0, 100, 50, 50, darkRed, true, managePanel, "");
    private final JButton sortDesc = BetterJFrame.BJButton(0, 150, 50, 50, darkRed, true, managePanel, "");
    private final JButton sortAscStocks = BetterJFrame.BJButton(0, 100, 50, 50, darkRed, false, managePanel, "");
    private final JButton sortDescStocks = BetterJFrame.BJButton(0, 150, 50, 50, darkRed, false, managePanel, "");
    private final JButton downloadButton = BetterJFrame.BJButton(0, 210, 50, 50, darkRed, true, managePanel, "");
    private final JButton historyButton = BetterJFrame.BJButton(0, 270, 50, 50, Color.black, false, managePanel, "H");
    private final JButton trade = BetterJFrame.BJButton(0, 0, 300, 100, new Color(0, 130, 0), true, managePanel, "CRYPTOCURRENCIES");
    private final JButton stockPrices = BetterJFrame.BJButton(300, 0, 300, 100, new Color(0, 130, 0), true, managePanel, "STOCK MARKET");
    private final JButton logOut = BetterJFrame.BJButton(900, 0, 300, 100, new Color(0, 130, 0), true, managePanel, "LOGOUT");
    private final JButton currSaldo = BetterJFrame.BJButton(600, 0, 300, 100, new Color(0, 130, 0), true, managePanel, "YOUR ASSETS");
    private final JPanel historyPanel = BetterJFrame.BJPanel(100, 100, 1000, 800, new Color(12, 12, 125), false, new FlowLayout(), managePanel);
    private final JPanel analysisPanel = BetterJFrame.BJPanel(100, 200, 1000, 800, new Color(12, 128, 12), false, null, managePanel);
    private boolean isDownloadedCrypto = false;
    private boolean isDownloadedStock = false;
    public JFrame makeUserInterface() {
        historyPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        Background backgroundPanel = new Background("kotek.jpg");
        backgroundPanel.setVisible(true);
        backgroundPanel.setBounds(0,0, 1200, 800);
        JPanel registerPage = BetterJFrame.BJPanel(460, 235, 280, 280, new Color(0, 0, 0), false, null, mainFrame);
        JPanel loginPage = BetterJFrame.BJPanel(460, 235, 280, 280, new Color(0, 0, 0),true, null, mainFrame);
        mainFrame.add(backgroundPanel);
        cryptoInfoPanel.setLayout(new FlowLayout());
        cryptoInfoPanel.setBackground(Color.BLUE);
        cryptoInfoScroll.setBounds(0, 100, 1185, 800);
        cryptoInfoScroll.remove(cryptoInfoScroll.getVerticalScrollBar());
        cryptoInfoScroll.setLayout(new ScrollPaneLayout());
        cryptoInfoScroll.setBackground(Color.BLUE);
        cryptoInfoScroll.addMouseWheelListener(e ->{
            refreshMainFrame(mainFrame);
        });
        cryptoInfoScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        cryptoInfoScroll.getVerticalScrollBar().setUnitIncrement(20);
        ImageIcon ascIcon = new ImageIcon("ASC.jpg");
        ImageIcon descIcon = new ImageIcon("DESC.jpg");
        sortAsc.setForeground(Color.WHITE);
        sortAsc.setIcon(ascIcon);
        sortAsc.addActionListener(e -> {
            cryptoInfoPanel.removeAll();
            creatingBlocks(Main.prices, "Crypto", 2, !isDownloadedCrypto);
        });
        sortDesc.setIcon(descIcon);
        sortDesc.addActionListener(e -> {
            cryptoInfoPanel.removeAll();
            creatingBlocks(Main.prices, "Crypto", 1, !isDownloadedCrypto);
        });
        sortDesc.setForeground(Color.WHITE);
        sortAscStocks.setIcon(ascIcon);
        sortAscStocks.setForeground(Color.WHITE);
        sortAscStocks.addActionListener(e -> {
            cryptoInfoPanel.removeAll();
            creatingBlocks(Main.stockPrices, "stocks", 2, !isDownloadedStock);
        });
        sortDescStocks.setForeground(Color.WHITE);
        sortDescStocks.setIcon(descIcon);
        sortDescStocks.addActionListener(e1 -> {
            cryptoInfoPanel.removeAll();
            creatingBlocks(Main.stockPrices, "stocks", 1, !isDownloadedStock);
        });
        downloadButton.setForeground(Color.white);
        downloadButton.setIcon(new ImageIcon("REFRESH.jpg"));
        downloadButton.addActionListener(e -> {
            saldoPanel.removeAll();
            cryptoInfoPanel.removeAll();
            cryptoInfoPanel.setVisible(true);
            cryptoInfoScroll.setVisible(true);
            saldoPanel.setVisible(false);
            managePanel.add(cryptoInfoScroll);
            try {
                if (sortAsc.isVisible()) {
                    cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1600));
                    sortingSwitch("Crypto");
                    Main main = new Main();
                    main.getData();
                    Main.prices.put("USDT", 1/Main.prices.get("USDT"));
                    isDownloadedCrypto = true;
                    creatingBlocks(Main.prices, "kot12", 2231, false);
                }
                else{
                    cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1000));
                    sortingSwitch("Stock");
                    Main main = new Main();
                    main.getStocks();
                    isDownloadedStock = true;
                    creatingBlocks(Main.stockPrices, "stocks", 213, false);
                }
            }catch(ConcurrentModificationException ex){
                System.out.println(ex.getMessage());
            }
            catch(InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        historyButton.setForeground(Color.green);
        historyButton.setFont(new Font("Arial Bold", Font.PLAIN, 22));
        historyButton.addActionListener(e -> {
            if(historyPanel.isVisible())
            {
                historyPanel.setVisible(false);
                cryptoInfoScroll.setVisible(true);
                historyPanel.removeAll();
                return;
            }
            analysisPanel.setVisible(false);
            historyPanel.setVisible(true);
            cryptoInfoScroll.setVisible(false);
            String type = sortAsc.isVisible() ? "crypto" : "stocks";
            File[] files = FileOperations.getAllFiles(type);
            historyPanel.removeAll();
            for(File file : files)
            {
                JButton savedDataButton = BetterJFrame.BJButton(0, 0, 200, 60, Color.BLACK, true, historyPanel, "");
                savedDataButton.setFont(new Font("Arial Regular", Font.PLAIN, 14));
                savedDataButton.setForeground(Color.WHITE);
                savedDataButton.addActionListener(e1 -> {
                    saldoPanel.removeAll();
                    cryptoInfoPanel.removeAll();
                    cryptoInfoPanel.setVisible(true);
                    cryptoInfoScroll.setVisible(true);
                    saldoPanel.setVisible(false);
                    managePanel.add(cryptoInfoScroll);
                    cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1600));
                    if(type.equals("crypto"))
                    {
                        Main.prices = FileOperations.readFile(file.toString());
                        assert Main.prices != null;
                        creatingBlocks(Main.prices, type, 2231, true);
                    }else{
                        Main.stockPrices = FileOperations.readFile(file.toString());
                        assert Main.stockPrices != null;
                        creatingBlocks(Main.stockPrices, type, 2231, true);
                    }

                    historyPanel.setVisible(false);
                    isDownloadedCrypto = false;
                });
                String name = file.toString().substring(18).replaceAll(".txt", "").replaceAll("_", ":");
                savedDataButton.setText(name);
            }
        });
        JTextField currUser = new JTextField("Username");
        logOut.setLayout(null);
        currUser.setBounds(110, 60, 80, 20);
        currUser.setHorizontalAlignment(0);
        currUser.setBackground(new Color(0, 130, 0));
        currUser.setEditable(false);
        currUser.setForeground(Color.WHITE);
        currUser.setFont(new Font("Arial Bold", Font.PLAIN, 13 ));
        currUser.setBorder(null);
        logOut.add(currUser);
        currUser.setText("Username");
        trade.setFont(new Font("Arial Bold", Font.PLAIN, 23 ));
        trade.setForeground(Color.WHITE);
        trade.addActionListener(e -> {
            offEverything();
            cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1600));
            trade.setBackground(Color.BLACK);
            currSaldo.setBackground(new Color(0, 130, 0));
            stockPrices.setBackground(new Color(0, 130, 0));
            sortingSwitch("Crypto");
            if(isDownloadedCrypto)
            {
                creatingBlocks(Main.prices, "kot12", 2231, false);
                return;
            }
            try{
                String plik = Arrays.stream(FileOperations.getAllFiles("crypto")).toList().get(0).toString();
                if(!plik.isEmpty())
                {
                    saldoPanel.removeAll();
                    cryptoInfoPanel.removeAll();
                    cryptoInfoPanel.setVisible(true);
                    cryptoInfoScroll.setVisible(true);
                    saldoPanel.setVisible(false);
                    managePanel.add(cryptoInfoScroll);
                    cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1600));
                    Main.prices = FileOperations.readFile(plik);
                    assert Main.prices != null;
                    creatingBlocks(Main.prices, "kot12", 2231, true);
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
            currSaldo.setBackground(new Color(0, 130, 0));
            trade.setBackground(new Color(0, 130, 0));
            sortingSwitch("Stock");
            if(isDownloadedStock)
            {
                creatingBlocks(Main.stockPrices, "stocks", 2231, false);
                return;
            }
            try{
                String file = Arrays.stream(FileOperations.getAllFiles("stocks")).toList().get(0).toString();
                if(!file.isEmpty())
                {
                    saldoPanel.removeAll();
                    cryptoInfoPanel.removeAll();
                    cryptoInfoPanel.setVisible(true);
                    cryptoInfoScroll.setVisible(true);
                    saldoPanel.setVisible(false);
                    managePanel.add(cryptoInfoScroll);
                    cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1600));
                    Main.stockPrices = FileOperations.readFile(file);
                    assert Main.stockPrices != null;
                    creatingBlocks(Main.stockPrices, "stocks", 2231, true);
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
            sortingSwitch("fries maker");
        });
        currSaldo.setFont(new Font("Arial Bold", Font.PLAIN, 23 ));
        currSaldo.setForeground(Color.WHITE);
        currSaldo.addActionListener(e ->{
            offEverything();
            sortingSwitch("fries maker");
            saldoPanel.setVisible(true);
            currSaldo.setBackground(Color.BLACK);
            trade.setBackground(new Color(0, 130, 0));
            stockPrices.setBackground(new Color(0, 130, 0));
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
                saldoPanel.add(saldoInfoPanel);
            }
        });
        JTextField loginText = BetterJFrame.BJTextField(40, 50, 200, 25, Color.WHITE, true, loginPage, true, "Username", null);
        loginText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                loginText.setText("");
            }
        });
        JPasswordField passwordText = new JPasswordField("Password");
        passwordText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordText.setText("");
            }
        });
        passwordText.setBounds(40, 100, 200, 25);
        JTextField loginDataError = BetterJFrame.BJTextField(40, 250, 200, 25, darkRed, false, loginPage, false, "Wrong Data", Color.WHITE);
        JButton loginButton = BetterJFrame.BJButton(40, 150, 200, 25, Color.WHITE, true, loginPage, "LOGIN");
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
                        saldoPanel.removeAll();
                        cryptoInfoPanel.removeAll();
                        cryptoInfoPanel.setVisible(true);
                        cryptoInfoScroll.setVisible(true);
                        saldoPanel.setVisible(false);
                        managePanel.add(cryptoInfoScroll);
                        cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1600));
                        Main.prices = FileOperations.readFile(plik);
                        assert Main.prices != null;
                        creatingBlocks(Main.prices, "kot12", 2231, true);
                    }
                    refreshMainFrame(mainFrame);
                }
            }
        });
        JButton registerPageButton = BetterJFrame.BJButton(40, 200, 200, 25, Color.WHITE, true, loginPage, "REGISTER");
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
        passwordTextRegister.setBounds(40, 100, 200, 25);
        JTextField registerDataError = BetterJFrame.BJTextField(40, 200, 200, 25, darkRed, false, registerPage, false, "Try Again", Color.WHITE);
        JButton registerButton = BetterJFrame.BJButton(40, 150, 200, 25, Color.WHITE, true, registerPage, "REGISTER");
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
        return mainFrame;
    }
    public void createCryptoBlock(double value, String name, boolean isHistory){
        JPanel cryptoPanel = BetterJFrame.BJPanel(0, 0, 150, 100, Color.black, true, null, cryptoInfoPanel);
        JTextField cryptoText = BetterJFrame.BJTextField(5, 15, 140, 20, Color.white, true, cryptoPanel, false,name + " : " + String.format("%.5f", value), Color.WHITE);
        cryptoText.setFont(new Font("Arial regular", Font.PLAIN, 13 ));
        cryptoText.setBackground(darkRed);
        cryptoText.setHorizontalAlignment(0);

        JButton buyButton = BetterJFrame.BJButton(10, 45 , 65, 30, new Color(0, 155, 0), true, cryptoPanel, "BUY");
        buyButton.setForeground(Color.WHITE);
        buyButton.addActionListener(e -> {
            currSaldo.setEnabled(false);
            trade.setEnabled(false);
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
            maxAmount.addActionListener(e1 -> {
                amount.setText(Double.toString(Main.saldoData.get("Money")/value));
            });
            JButton exitButton = BetterJFrame.BJButton(285, 30, 45, 45, darkRed, true, buyPanel, "X");
            exitButton.setForeground(Color.WHITE);
            exitButton.addActionListener(e1 -> {
                buyPanel.revalidate();
                buyPanel.repaint();
                amount.setText("");
                buyPanel.setVisible(false);
                cryptoInfoScroll.setVisible(true);
                currSaldo.setEnabled(true);
                trade.setEnabled(true);
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
                    currSaldo.setEnabled(true);
                    trade.setEnabled(true);
                    logOut.setEnabled(true);
                    stockPrices.setEnabled(true);
                }
            });
        });
        JButton sellButton = BetterJFrame.BJButton(75, 45 , 65, 30, darkRed, true, cryptoPanel, "SELL");
        sellButton.setForeground(Color.WHITE);
        sellButton.addActionListener(e -> {
            currSaldo.setEnabled(false);
            trade.setEnabled(false);
            logOut.setEnabled(false);
            stockPrices.setEnabled(false);
            cryptoInfoScroll.setVisible(false);
            JPanel sellPanel = BetterJFrame.BJPanel(450, 250, 350, 110, new Color(180, 2, 2), true, null, managePanel);
            JButton exitButton = BetterJFrame.BJButton(280, 30, 45, 45, darkRed, true, sellPanel, "X");
            exitButton.setForeground(Color.WHITE);
            exitButton.addActionListener(e2 -> {
                sellPanel.setVisible(false);
                cryptoInfoScroll.setVisible(true);
                currSaldo.setEnabled(true);
                trade.setEnabled(true);
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
            maxAmount.addActionListener(e1 -> {
                amount.setText(Double.toString(Main.saldoData.get(name)));
            });
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
                    currSaldo.setEnabled(true);
                    trade.setEnabled(true);
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
        });
        JButton hisButton = BetterJFrame.BJButton(20, 80, 20, 20, Color.WHITE, true, cryptoPanel, "H");
        hisButton.setRolloverEnabled(false);
        hisButton.addActionListener(e -> {
            if(analysisPanel.isVisible())
            {
                analysisPanel.setVisible(false);
                cryptoInfoScroll.setVisible(true);
                analysisPanel.removeAll();
                return;
            }
            cryptoInfoScroll.setVisible(false);
            analysisPanel.setVisible(true);
            String type = Main.prices.containsKey(name) || name.equals("BTC") ? "crypto" : "stocks";
            File[] files = FileOperations.getAllFiles(type);
            managePanel.setComponentZOrder(analysisPanel, 0);
            Map<String, Double> map = new LinkedHashMap<>();
            for(File file :files)
            {
                if(type.equals("stocks")) {
                    try {
                        double valueFromFile = Objects.requireNonNull(FileOperations.readFile(file.toString())).get(name);
                        map.put(file.toString(), valueFromFile);
                    } catch (NullPointerException z) {
                        z.getMessage();
                    }
                }else{
                    if(!name.equals("BTC"))
                    {
                        double bitcoinPrice = Objects.requireNonNull(FileOperations.readFile(file.toString())).get("USDT");
                        try{
                            double valueFromFile = Objects.requireNonNull(FileOperations.readFile(file.toString())).get(name) * bitcoinPrice;
                            map.put(file.toString(), valueFromFile);
                        }catch(NullPointerException z)
                        {
                            z.getMessage();
                        }
                        continue;
                    }
                    map.put(file.toString(), Objects.requireNonNull(FileOperations.readFile(file.toString())).get("USDT"));
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
                    JButton date = BetterJFrame.BJButton(20 + (900 / values.size()) * i, 520, 900/values.size(), 15, Color.black, true, analysisPanel, "");
                    String formattedDate = key.replace(".txt", "").replace("cryptoHistoryData", "").replace("stocksHistoryData", "").replace("_", ":");
                    date.setToolTipText(formattedDate);
                    JButton valueAtDate = BetterJFrame.BJButton(20 + (900 / values.size()) * i, y, (900 / values.size()), 520 - y, color, true, analysisPanel, "");
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
        saldoPanel.removeAll();
        analysisPanel.setVisible(false);
        analysisPanel.removeAll();
        cryptoInfoPanel.removeAll();
        cryptoInfoPanel.setVisible(true);
        cryptoInfoScroll.setVisible(true);
        saldoPanel.setVisible(false);
    }
    // any normal, 1 desc, 2 asc
    private void creatingBlocks(Map<String, Double> map, String type, int sort, boolean isHistory){
        LinkedHashMap<String, Double> sortedMap;
        sortedMap = map.entrySet()
                .stream()
                .sorted(selectSort(sort))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        if(type.equals("stocks")){
            for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                if(value == 0.0){continue;}
                createCryptoBlock(value, key, isHistory);
            }
        }else{
            double bitcoinPrice = map.get("USDT");
            for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                if(value == 0.0){continue;}
                if(key.equals("USDT")){
                    createCryptoBlock(bitcoinPrice, "BTC", isHistory);
                }else{
                    value *= bitcoinPrice;
                    createCryptoBlock(value, key, isHistory);
                }
            }
        }
    }
    private void sortingSwitch(String type)
    {
        sortAsc.setVisible(false);
        sortDesc.setVisible(false);
        sortAscStocks.setVisible(false);
        sortDescStocks.setVisible(false);
        downloadButton.setVisible(false);
        historyButton.setVisible(false);
        if(type.equals("Stock"))
        {
            sortAscStocks.setVisible(true);
            sortDescStocks.setVisible(true);
            downloadButton.setVisible(true);
            historyButton.setVisible(true);
        }else if (type.equals("Crypto")){
            sortAsc.setVisible(true);
            sortDesc.setVisible(true);
            downloadButton.setVisible(true);
            historyButton.setVisible(true);
        }
        refreshMainFrame(mainFrame);
    }
    public static void refreshMainFrame(JFrame frame){
        frame.revalidate();
        frame.repaint();
    }
}