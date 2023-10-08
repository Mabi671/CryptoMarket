package org.example;

import javax.swing.*;
import java.awt.*;

public class BetterJFrame{
    public static JFrame BJFrame(int width, int height, Color color, boolean visibility, LayoutManager layout){
        JFrame frame = new JFrame();
        frame.setSize(width, height);
        frame.setBackground(color);
        frame.setVisible(visibility);
        frame.setLayout(layout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        return frame;
    }
    public static JPanel BJPanel(int x, int y, int width, int height, Color color, boolean visibility, LayoutManager layout, JPanel parent){
        JPanel panel = new JPanel();
        panel.setBounds(x, y, width, height);
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBackground(color);
        panel.setVisible(visibility);
        panel.setLayout(layout);
        parent.add(panel);
        return panel;

    }
    public static JPanel BJPanel(int x, int y, int width, int height, Color color, boolean visibility, LayoutManager layout, JFrame parent){
        JPanel panel = new JPanel();
        panel.setBounds(x, y, width, height);
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBackground(color);
        panel.setVisible(visibility);
        panel.setLayout(layout);
        parent.add(panel);
        return panel;
    }
    public static JTextField BJTextField(int x, int y, int width, int height, Color color, boolean visibility, JPanel parent, boolean editable){
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        textField.setPreferredSize(new Dimension(width, height));
        textField.setBackground(color);
        textField.setVisible(visibility);
        textField.setEditable(editable);
        parent.add(textField);
        return textField;
    }
    public static JButton BJButton(int x, int y, int width, int height, Color color, boolean visibility, JPanel parent, String text){
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(color);
        button.setVisible(visibility);
        parent.add(button);
        return button;
    }
}
