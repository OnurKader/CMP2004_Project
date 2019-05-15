package com.pool.main.Menus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.pool.main.Menu;
import com.pool.network.GameListener;

public class MainMenu
{

	MPmenu mpMENU;
	public static boolean first = false;
	Thread c, l;

	public MainMenu()
	{

		mpMENU = new MPmenu(this);
	}

	public JPanel MMpanel(JFrame frame)
	{
		JPanel panel = new JPanel();
		ImageIcon bg = new ImageIcon(
				"/home/beronthecolossus/Desktop/CMP2004_Project/AtahanFinalVer/Atahan/data/bg1.jpg");

		JButton b1 = new JButton("Practice Mode");
		JButton b2 = new JButton("Multiplayer Mode");
		JButton b3 = new JButton("PvP Mode");
		JButton b4 = new JButton("Exit");

		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Practice Mode!");
				frame.setVisible(false);
				frame.removeAll();
				frame.dispose();
				Menu.mode = 0;
			}
		});

		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (first == false)
				{
					Runnable listener = new GameListener();
					l = new Thread(listener);
					l.start();
					first = true;
				}
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				frame.add(mpMENU.MPpanel(frame));
				frame.setVisible(true);
			}
		});

		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("PvP Offline Mode!");
				frame.setVisible(false);
				frame.removeAll();
				frame.dispose();
				Menu.mode = 1;
			}
		});

		b4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

		JLabel mainBG = new JLabel(bg);
		JLabel label1 = new JLabel();

		label1.setPreferredSize(new Dimension(0, 230));

		mainBG.setLayout(new BorderLayout());
		label1.setLayout(new GridBagLayout());

		panel.setLayout(new BorderLayout());

		b1.setPreferredSize(new Dimension(600, 50));
		b2.setPreferredSize(new Dimension(600, 50));
		b3.setPreferredSize(new Dimension(600, 50));
		b4.setPreferredSize(new Dimension(600, 50));

		b1.setForeground(Color.white);
		b2.setForeground(Color.white);
		b3.setForeground(Color.white);
		b4.setForeground(Color.white);

		b1.setBackground(Color.blue);
		b2.setBackground(Color.blue);
		b3.setBackground(Color.blue);
		b4.setBackground(Color.blue);

		GridBagConstraints gr1 = new GridBagConstraints();

		gr1.gridx = 0;
		gr1.gridy = 0;
		gr1.weightx = 1;
		gr1.weighty = 1;

		label1.add(b1, gr1);

		gr1.gridy = 1;

		label1.add(b2, gr1);

		gr1.gridy = 2;

		label1.add(b3, gr1);

		gr1.gridy = 3;

		label1.add(b4, gr1);

		mainBG.add(label1, BorderLayout.SOUTH);
		panel.add(mainBG, BorderLayout.CENTER);
		return panel;
	}
}
