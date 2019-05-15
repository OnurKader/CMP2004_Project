package com.pool.main.Menus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.pool.network.GameClient;
import com.pool.network.GameListener;

public class MPmenu {

    public static boolean pConnectOnce = false;

    MainMenu mMenu;


    public MPmenu(MainMenu z){
       mMenu = z;
    }

    public JPanel MPpanel(JFrame frame){
        ImageIcon bg2 = new ImageIcon("/home/beronthecolossus/Desktop/CMP2004_Project/AtahanFinalVer/Atahan/data/bg2.jpg");
        JPanel mpPanel = new JPanel();
        mpPanel.setLayout(new BorderLayout());

        JButton mm = new JButton("Main Menu");

        mm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameListener.pCount = 0;
               frame.setVisible(false);
               frame.getContentPane().removeAll();
               frame.add(mMenu.MMpanel(frame));
               frame.setVisible(true);
            }
        });

        JLabel mpBG = new JLabel(bg2);
        JLabel test = new JLabel();
        JLabel status = new JLabel();

        mpBG.setLayout(new BorderLayout());
        test.setLayout(new GridBagLayout());

        GridBagConstraints mp = new GridBagConstraints();

        JTextField userF = new JTextField();
        JTextField ipF = new JTextField();

        JLabel user = new JLabel("Your Username : ");
        JLabel ip = new JLabel("Your Opponent's IP Address : ");
        JButton conn = new JButton("Connect & Play!");



        conn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dotcount = 0;
                pConnectOnce = true;
                GameListener.pCount++;

                for(int i = 0; i<ipF.getText().length(); i++){
                    if(ipF.getText().charAt(i) == '.'){
                        dotcount++;
                    }
                }
                if(!userF.getText().isEmpty() && !ipF.getText().isEmpty() && dotcount == 3) {
                         status.setText("Waiting for Connection...");
                         GameClient.ip = ipF.getText();
                         GameClient.send("START");
                }

            }
        });

        user.setFont(new Font("Serif", Font.PLAIN, 20));
        ip.setFont(new Font("Serif", Font.PLAIN, 20));

        user.setForeground(Color.WHITE);
        ip.setForeground(Color.WHITE);
        status.setForeground(Color.WHITE);

        user.setBackground(Color.BLACK);
        user.setOpaque(true);

        ip.setBackground(Color.BLACK);
        ip.setOpaque(true);

        status.setBackground(Color.magenta);
        status.setOpaque(true);


        userF.setPreferredSize(new Dimension(200,30));
        ipF.setPreferredSize(new Dimension(200,30));
        conn.setPreferredSize(new Dimension(300,80));
        mm.setPreferredSize(new Dimension(300,80));

        conn.setForeground(Color.WHITE);
        mm.setForeground(Color.WHITE);
        conn.setBackground(Color.BLUE);
        mm.setBackground(Color.BLUE);

        mp.weighty = 1;
        mp.weightx = 1;

        mp.anchor = GridBagConstraints.LINE_END;

        mp.gridx = 0;
        mp.gridy = 0;

        test.add(user,mp);

        mp.gridx = 0;
        mp.gridy = 1;

        test.add(ip,mp);

        mp.anchor = GridBagConstraints.LINE_START;

        mp.gridx = 1;
        mp.gridy = 0;

        test.add(userF,mp);

        mp.gridx = 1;
        mp.gridy = 1;

        test.add(ipF,mp);

        mp.anchor = GridBagConstraints.CENTER;

        mp.gridx = 1;
        mp.gridy = 2;

        test.add(conn,mp);

        mp.gridx = 0;
        mp.gridy = 2;

        test.add(mm,mp);

        mp.gridx = 1;
        mp.gridy = 3;

        test.add(status,mp);

        mpBG.add(test,BorderLayout.CENTER);
        mpPanel.add(mpBG,BorderLayout.CENTER);

        return mpPanel;

    }


}
