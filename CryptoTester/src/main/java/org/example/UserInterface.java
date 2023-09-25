package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;

public class UserInterface {
    private final JPanel cryptoInfoPanel = new JPanel();
    private final JScrollPane cryptoInfoScroll = new JScrollPane(cryptoInfoPanel);
    private final JFrame mainFrame = new JFrame("Trader");
    protected JTextArea saldoAmountText = new JTextArea();
    public String[] userData = new String[2];
    private final JPanel managePanel = new JPanel();
    private final JPanel saldoPanel = new JPanel();
    protected JFrame makeUserInterface() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(null);
        mainFrame.setSize(1200, 800);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        saldoPanel.setVisible(false);
        saldoPanel.setBackground(Color.blue);
        saldoPanel.setLayout(new FlowLayout());
        saldoPanel.setBounds(0, 200, 1200, 800);
        managePanel.add(saldoPanel);
        JPanel registerPage = new JPanel();
        registerPage.setVisible(false);
        cryptoInfoPanel.setPreferredSize(new Dimension(1200, 1600));
        cryptoInfoPanel.setLayout(new FlowLayout());
        cryptoInfoPanel.setBackground(Color.BLUE);
        cryptoInfoScroll.setBounds(0, 200, 1185, 800);
        cryptoInfoScroll.setLayout(new ScrollPaneLayout());
        cryptoInfoScroll.setBackground(Color.BLUE);
        cryptoInfoScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        cryptoInfoScroll.getVerticalScrollBar().setUnitIncrement(20);

        managePanel.setVisible(false);
        managePanel.setBounds(0, 0, 1200, 800);
        managePanel.setBackground(Color.green);
        managePanel.setLayout(null);
        JButton trade = new JButton("GET PRICES");
        trade.setBounds(50, 50, 200, 50);
        trade.setBackground(Color.red);
        trade.addActionListener(e -> {
            try {
                saldoPanel.removeAll();
                cryptoInfoPanel.removeAll();
                cryptoInfoPanel.setVisible(true);
                cryptoInfoScroll.setVisible(true);
                saldoPanel.setVisible(false);
                managePanel.add(cryptoInfoScroll);
                Main main = new Main();
                main.getData();
                double bitcoinPrice = 0;
                for (Map.Entry<String, Double> entry : Main.prices.entrySet()) {
                    String key = entry.getKey();
                    Double value = entry.getValue();
                    if(key.equals("USDT")){
                        bitcoinPrice = 1/value;
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
        JButton currSaldo = new JButton("YOUR CRYPTO");
        currSaldo.setBackground(Color.red);
        currSaldo.setBounds(600, 50, 200, 50);
        currSaldo.addActionListener(e ->{
            cryptoInfoScroll.setVisible(false);
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
                saldoInfoPanel.add(textSaldo);
                saldoPanel.add(saldoInfoPanel);
            }
        });
        managePanel.add(trade);
        managePanel.add(currSaldo);
        saldoAmountText.setBounds(300, 50, 215, 30);
        saldoAmountText.setBackground(Color.red);
        saldoAmountText.setText("Money: " + "0");
        saldoAmountText.setFont(new Font("Arial", Font.BOLD, 25));
        saldoAmountText.setEditable(false);
        managePanel.add(saldoAmountText);
        mainFrame.add(managePanel);

        JPanel loginPage = new JPanel();
        loginPage.setLayout(null);
        loginPage.setBounds(400, 175, 400, 400);
        loginPage.setBackground(Color.black);
        JTextPane loginText = new JTextPane();
        loginText.setBounds(100, 100, 200, 25);
        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(100, 150, 200, 25);
        JButton loginButton = new JButton("LOGIN");
        JTextPane loginDataError = new JTextPane();

        loginDataError.setText("Wrong Data");
        loginDataError.setEditable(false);
        loginDataError.setBackground(Color.red);
        loginDataError.setBounds(100, 300, 200, 25);
        loginPage.add(loginDataError);
        loginDataError.setVisible(false);
        loginButton.addActionListener(e -> {
            String[] data = new String[2];
            data[0] = loginText.getText();
            data[1] =  passwordText.getText();
            userData[0] = data[0];
            userData[1] = data[1];
            String[] dataFromFile = FileOperations.readFile(data, saldoAmountText);
            loginDataError.setVisible(true);
            if(dataFromFile != null){
                if (data[0].equals(dataFromFile[0]) && data[1].equals(dataFromFile[1])){
                    loginPage.setVisible(false);
                    loginDataError.setVisible(false);
                    managePanel.setVisible(true);
                }
            }
        });
        loginButton.setBounds(100, 200, 200, 25);
        JButton registerPageButton = new JButton("REGISTER");
        registerPageButton.setBounds(100, 250, 200, 25);
        registerPageButton.addActionListener(e -> {
            loginPage.setVisible(false);
            registerPage.setVisible(true);
            loginDataError.setVisible(false);
        });
        loginPage.add(loginText);
        loginPage.add(passwordText);
        loginPage.add(loginButton);
        loginPage.add(registerPageButton);
        mainFrame.getContentPane().add(loginPage);
        mainFrame.getContentPane().add(registerPage);
        registerPage.setLayout(null);
        registerPage.setBounds(400, 175, 400, 400);
        registerPage.setBackground(Color.black);
        JTextPane loginTextRegister = new JTextPane();
        loginTextRegister.setBounds(100, 100, 200, 25);
        JPasswordField passwordTextRegister = new JPasswordField();
        passwordTextRegister.setBounds(100, 150, 200, 25);
        JButton registerButton = new JButton("REGISTER");
        registerButton.setBounds(100, 200, 200, 25);
        JTextPane registerDataError = new JTextPane();
        registerDataError.setVisible(false);
        registerDataError.setText("Try Again");
        registerDataError.setEditable(false);
        registerDataError.setBackground(Color.red);
        registerDataError.setBounds(100, 250, 200, 25);
        registerPage.add(registerDataError);
        registerButton.addActionListener(e -> {
            String[] data = new String[2];
            data[0] = loginTextRegister.getText();
            data[1] =  passwordTextRegister.getText();
            registerDataError.setVisible(true);
            if(FileOperations.createFile(data, true)){
                registerPage.setVisible(false);
                loginPage.setVisible(true);
                registerDataError.setVisible(false);
            }
        });
        registerPage.add(loginTextRegister);
        registerPage.add(passwordTextRegister);
        registerPage.add(registerButton);

        refreshMainFrame(mainFrame);
        return mainFrame;
    }
    public void createCryptoBlock(double value, String name){
        JPanel cryptoPanel = new JPanel();
        cryptoPanel.setBackground(Color.black);
        cryptoPanel.setLayout(null);
        cryptoPanel.setPreferredSize(new Dimension(150, 100));
        JTextArea cryptoText = new JTextArea(name + " : " + String.format("%.5f", value));
        JButton buyButton = new JButton("BUY");
        buyButton.setBackground(Color.green);
        buyButton.addActionListener(e -> {
            cryptoInfoScroll.setVisible(false);
            JPanel buyPanel = new JPanel();
            buyPanel.setBounds(450, 250, 350, 110);
            buyPanel.setBackground(Color.BLACK);
            buyPanel.setLayout(null);
            buyPanel.setVisible(true);
            JButton exitButton = new JButton("X");
            exitButton.setBackground(Color.red);
            exitButton.setBounds( 280, 5, 45, 45);
            exitButton.addActionListener(e1 -> {
                buyPanel.setVisible(false);
                cryptoInfoScroll.setVisible(true);
            });
            buyPanel.add(exitButton);
            refreshMainFrame(mainFrame);
            JTextField amount = new JTextField();

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
            JButton maxAmount = new JButton("Max");
            maxAmount.addActionListener(e1 -> {
                amount.setText(Double.toString(Main.saldoData.get("Money")/value));
            });
            JButton confirm = new JButton("Confirm");
            buyPanel.add(confirm);
            confirm.setBounds(10, 10, 80, 50);
            maxAmount.setBounds(100, 10, 80, 50);
            amount.setBounds(190, 10, 80, 50);
            JTextArea valueError = new JTextArea("NOT ENOUGH MONEY");
            valueError.setBounds(150, 70, 120, 50);
            valueError.setVisible(false);
            valueError.setBackground(Color.red);
            valueError.setEditable(false);
            buyPanel.add(maxAmount);
            buyPanel.add(amount);
            buyPanel.add(valueError);
            managePanel.add(buyPanel);
            refreshMainFrame(mainFrame);
            confirm.addActionListener(e2 ->{
                double maxValue = Main.saldoData.get("Money")/value;
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
                    try{
                        Main.saldoData.put(name, Main.saldoData.get(name) + amountDouble);
                    }catch(NullPointerException e6){
                        Main.saldoData.put(name, amountDouble);
                    }
                    Double calculatedMoney = Main.saldoData.get("Money") - amountDouble * value;
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
        buyButton.setBounds(5, 50 , 65, 30);
        JButton sellButton = new JButton("SELL");
        sellButton.setBackground(Color.red);
        sellButton.setBounds(70, 50 , 65, 30);
        sellButton.addActionListener(e -> {
            cryptoInfoScroll.setVisible(false);
            JPanel sellPanel = new JPanel();
            sellPanel.setBounds(450, 250, 350, 110);
            sellPanel.setBackground(Color.BLACK);
            sellPanel.setLayout(null);
            sellPanel.setVisible(true);
            JButton exitButton = new JButton("X");
            exitButton.setBounds( 280, 5, 45, 45);
            exitButton.setBackground(Color.red);
            exitButton.addActionListener(e2 -> {
                sellPanel.setVisible(false);
                cryptoInfoScroll.setVisible(true);
            });
            sellPanel.add(exitButton);
            refreshMainFrame(mainFrame);
            JTextField amount = new JTextField();

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
            JButton maxAmount = new JButton("Max");
            maxAmount.addActionListener(e1 -> {
                amount.setText(Double.toString(Main.saldoData.get(name)));
            });
            JButton confirm = new JButton("Confirm");
            sellPanel.add(confirm);
            confirm.setBounds(10, 10, 80, 50);
            maxAmount.setBounds(100, 10, 80, 50);
            amount.setBounds(190, 10, 80, 50);
            JTextArea valueError = new JTextArea("DATA ERROR");
            valueError.setBounds(150, 70, 120, 50);
            valueError.setVisible(false);
            valueError.setBackground(Color.red);
            valueError.setEditable(false);
            sellPanel.add(maxAmount);
            sellPanel.add(amount);
            sellPanel.add(valueError);
            managePanel.add(sellPanel);
            refreshMainFrame(mainFrame);
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
        cryptoText.setBounds(10, 20, 100, 20);
        cryptoPanel.add(buyButton);
        cryptoPanel.add(sellButton);
        cryptoPanel.add(cryptoText);
        cryptoPanel.setVisible(true);
        cryptoInfoPanel.add(cryptoPanel);
        refreshMainFrame(mainFrame);
    }


    public static void refreshMainFrame(JFrame frame){
        frame.revalidate();
        frame.repaint();
    }

}