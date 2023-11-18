package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.stream.Collectors;

public class UserInterface {
    private final Color darkRed = new Color(150, 0, 0);
    private final JFrame mainFrame = BetterJFrame.BJFrame(1200, 800, Color.BLACK,true,null);
    private final JPanel managePanel = BetterJFrame.BJPanel(0, 0, 1200, 800, new Color(0, 155, 0), false, null, mainFrame);
    private final JPanel cryptoInfoPanel = new JPanel();
    private final JScrollPane cryptoInfoScroll = new JScrollPane(cryptoInfoPanel);
    public List userData = new List();
    private final JPanel saldoPanel = BetterJFrame.BJPanel(0, 200, 1200, 800, Color.blue, false, new FlowLayout(), managePanel);
    protected JTextField saldoAmountText = BetterJFrame.BJTextField(600, 100, 300, 100, darkRed, false, managePanel, false);
    private final JButton sortAsc = BetterJFrame.BJButton(0, 100, 300, 50, darkRed, false, managePanel, "HIGHEST");
    private final JButton sortDesc = BetterJFrame.BJButton(0, 150, 300, 50, darkRed, false, managePanel, "LOWEST");
    private final JButton sortAscStocks = BetterJFrame.BJButton(300, 100, 300, 50, darkRed, false, managePanel, "HIGHEST");
    private final JButton sortDescStocks = BetterJFrame.BJButton(300, 150, 300, 50, darkRed, false, managePanel, "LOWEST");
    private final JButton downloadButton = BetterJFrame.BJButton(900, 100, 300, 100, darkRed, false, managePanel, "LOAD PRICES");
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
        sortAsc.setForeground(Color.WHITE);
        sortAsc.setFont(new Font("Arial Bold", Font.PLAIN, 18 ));
        sortAsc.addActionListener(e -> {
            cryptoInfoPanel.removeAll();
            LinkedHashMap<String, Double> sortedMap = Main.prices.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            creatingBlocks(sortedMap, "Crypto", 2);
        });
        sortDesc.setFont(new Font("Arial Bold", Font.PLAIN, 18 ));
        sortDesc.addActionListener(e -> {
            cryptoInfoPanel.removeAll();
            LinkedHashMap<String, Double> sortedMap = Main.prices.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            creatingBlocks(sortedMap, "Crypto", 1);
        });
        sortDesc.setForeground(Color.WHITE);
        sortAscStocks.setFont(new Font("Arial Bold", Font.PLAIN, 18 ));
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
            creatingBlocks(sortedMap, "Stock", 2);
        });
        sortDescStocks.setForeground(Color.WHITE);
        sortDescStocks.setFont(new Font("Arial Bold", Font.PLAIN, 18 ));
        sortDescStocks.addActionListener(e1 -> {
            cryptoInfoPanel.removeAll();
            LinkedHashMap<String, Double> sortedMap = Main.stockPrices.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            creatingBlocks(sortedMap, "Stock", 1);
        });
        downloadButton.setForeground(Color.white);
        downloadButton.setFont(new Font("Arial Bold", Font.PLAIN, 26 ));
        downloadButton.addActionListener(e -> {
            saldoPanel.removeAll();
            cryptoInfoPanel.removeAll();
            cryptoInfoPanel.setVisible(true);
            cryptoInfoScroll.setVisible(true);
            saldoPanel.setVisible(false);
            saldoAmountText.setVisible(false);
            managePanel.add(cryptoInfoScroll);
            try {
                if (sortAsc.isVisible()) {
                    cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1600));
                    sortingSwitch("Crypto");
                    Main main = new Main();
                    main.getData();
                    Main.prices.put("USDT", 1/Main.prices.get("USDT"));
                    creatingBlocks(Main.prices, "kot12", 2231);
                }
                else{
                    cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1000));
                    sortingSwitch("Stock");
                    Main main = new Main();
                    main.getStocks();
                    creatingBlocks(Main.stockPrices, "Stock", 213);
                }
            }catch(ConcurrentModificationException ex){
                System.out.println(ex.getMessage());
            }
            catch(InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        JButton trade = BetterJFrame.BJButton(0, 0, 300, 100, Color.BLACK, true, managePanel, "CRYPTOCURRENCIES");
        trade.setFont(new Font("Arial Bold", Font.PLAIN, 23 ));
        trade.setForeground(Color.WHITE);
        trade.addActionListener(e -> {
            saldoPanel.removeAll();
            cryptoInfoPanel.removeAll();
            cryptoInfoPanel.setVisible(true);
            cryptoInfoScroll.setVisible(true);
            saldoPanel.setVisible(false);
            saldoAmountText.setVisible(false);
            managePanel.add(cryptoInfoScroll);
            cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1600));
            sortingSwitch("Crypto");
            creatingBlocks(Main.prices, "kot12", 2231);
        });
        JButton stockPrices = BetterJFrame.BJButton(300, 0, 300, 100, Color.BLACK, true, managePanel, "STOCK MARKET");
        stockPrices.setForeground(Color.WHITE);
        stockPrices.setFont(new Font("Arial Bold", Font.PLAIN, 23 ));
        stockPrices.addActionListener(e -> {
            saldoPanel.removeAll();
            cryptoInfoPanel.removeAll();
            cryptoInfoPanel.setVisible(true);
            cryptoInfoScroll.setVisible(true);
            saldoPanel.setVisible(false);
            saldoAmountText.setVisible(false);
            managePanel.add(cryptoInfoScroll);
            cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1000));
            sortingSwitch("Stock");
            creatingBlocks(Main.stockPrices, "Stock", 213);
        });
        JButton logOut = BetterJFrame.BJButton(900, 0, 300, 100, Color.BLACK, true, managePanel, "LOGOUT");
        logOut.setForeground(Color.WHITE);
        logOut.setFont(new Font("Arial Bold", Font.PLAIN, 23 ));
        logOut.addActionListener(e -> {
            loginPage.setVisible(true);
            managePanel.setVisible(false);
            saldoPanel.removeAll();
            cryptoInfoPanel.removeAll();
            cryptoInfoPanel.setVisible(false);
            cryptoInfoScroll.setVisible(false);
            sortingSwitch("fries maker");
            saldoPanel.setVisible(false);
            saldoAmountText.setVisible(false);
        });
        JButton currSaldo = BetterJFrame.BJButton(600, 0, 300, 100, Color.BLACK, true, managePanel, "YOUR ASSETS");
        currSaldo.setFont(new Font("Arial Bold", Font.PLAIN, 23 ));
        currSaldo.setForeground(Color.WHITE);
        currSaldo.addActionListener(e ->{
            cryptoInfoScroll.setVisible(false);
            sortingSwitch("fries maker");
            cryptoInfoPanel.removeAll();
            saldoPanel.setVisible(true);
            saldoAmountText.setVisible(true);
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
    // any normal, 1 desc, 2 asc
    private void creatingBlocks(Map<String, Double> map, String type, int sort){
        LinkedHashMap<String, Double> sortedMap;
        sortedMap = map.entrySet()
                .stream()
                .sorted(selectSort(sort))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        if(type.equals("Stock")){
            for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                createCryptoBlock(value, key);
            }
        }else{
            double bitcoinPrice = Main.prices.get("USDT");
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
        }
    }
    private void sortingSwitch(String type)
    {
        if(type.equals("Stock"))
        {
            sortAsc.setVisible(false);
            sortDesc.setVisible(false);
            sortAscStocks.setVisible(true);
            sortDescStocks.setVisible(true);
            downloadButton.setVisible(true);
        }else if (type.equals("Crypto")){
            sortAsc.setVisible(true);
            sortDesc.setVisible(true);
            sortAscStocks.setVisible(false);
            sortDescStocks.setVisible(false);
            downloadButton.setVisible(true);
        }else{
            sortAsc.setVisible(false);
            sortDesc.setVisible(false);
            sortAscStocks.setVisible(false);
            sortDescStocks.setVisible(false);
            downloadButton.setVisible(false);
        }
    }
    public static void refreshMainFrame(JFrame frame){
        frame.revalidate();
        frame.repaint();
    }
}