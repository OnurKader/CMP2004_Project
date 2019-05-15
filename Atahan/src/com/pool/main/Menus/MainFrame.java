package com.pool.main.Menus;

import javax.swing.*;
import java.awt.*;

public class MainFrame{

    public static final int WIDTH=1280,HEIGHT=720;

    MainMenu mm;

    public MainFrame(){

        mm = new MainMenu();
    }

    public void setMFrame(JFrame frame){
        frame.setSize(WIDTH,HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.add(mm.MMpanel(frame));
        frame.setVisible(true);
    }

}
