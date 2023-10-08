package org.example;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Main {
    public static Map<String, Double> prices = new HashMap<>();
    public static Map<String, Double> saldoData = new HashMap<>();
    private static JFrame mainFrame;
    private static UserInterface ui;
    private static String[] symbols = {
            "USDT", "ETH", "LTC", "EOS", "XRP", "KCS",
            "DASH", "DOT", "XTZ", "ZEC", "ADA",
            "ATOM", "LINK", "LUNA", "NEO", "UNI", "ETC", "BNB",
            "TRX", "XLM", "BCH", "USDC", "GRT", "1INCH", "AAVE",
            "SNX", "API3", "CRV", "MIR", "SUSHI", "COMP", "ZIL",
            "YFI", "OMG", "XMR", "WAVES", "MKR", "COTI", "SXP",
            "THETA", "DOGE", "LRC", "FIL", "DAO",
            "KSM", "BAT", "ROSE", "CAKE", "CRO", "MASK",
            "FTM", "IOST", "ALGO", "CHR", "CHZ", "MANA",
            "ENJ", "IOST", "ANKR", "ORN", "SAND", "VELO", "AVAX",
            "DODO", "WIN", "ONE", "ICP", "CKB",
            "SOL", "DYDX", "ENS", "NEAR", "SLP", "AXS",
            "TLM", "ALICE", "QNT", "SUPER", "RUNE",
            "EGLD", "AR", "RNDR", "LTO", "YGG"
    };
    public static void main(String[] args) {
        Main.ui = new UserInterface();
        mainFrame = Main.ui.makeUserInterface();
    }
    public static void makeSaldo(){
        saldoData.put("Money", 100.0);
        for(String symbol : symbols){
            if(symbol.equals("USDT")){
                saldoData.put("BTC", 0.0);
            }else{
                saldoData.put(symbol, 0.0);
            }
        }
    }
    public  void getData() throws InterruptedException {
        int length = symbols.length;
        for (int i = 0; i < length; i++) {
            DownloadingData myThread = new DownloadingData(symbols[i]);
            myThread.start();
            if (i + 1 == length || i == 0) {
                try {
                    myThread.join();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        TimeUnit.SECONDS.sleep(5);

        UserInterface.refreshMainFrame(mainFrame);
    }
}
