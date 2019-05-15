package com.pool.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.pool.game.GameManager;
import com.pool.main.Menu;

public class GameListener implements Runnable
{

	public static boolean excep = false;
	public static int pCount = 0;
	public static boolean player1 = false;
	public static double pow;
	public static double pow_a;
	public static double cue_ang;

	public GameListener()
	{

	}

	public void run()
	{
		while (!Thread.currentThread().isInterrupted())
		{

			try
			{
				Thread.sleep(500);
			} catch (InterruptedException e)
			{
			}

			try
			{

				DatagramSocket serverSocket = new DatagramSocket(5000);
				while (true)
				{
					System.out.println("QEWWEq");
					byte[] receiveData = new byte[1024];
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					serverSocket.receive(receivePacket);
					String recievedThing = new String(receivePacket.getData());
					if (recievedThing.trim().equals("START"))
					{
						Menu.mode = 2;
						GameClient.ip = receivePacket.getAddress().getHostAddress();
						GameClient.send("U2");
					}
					if (recievedThing.trim().equals("U2"))
					{
						player1 = true;
						Menu.mode = 2;
					}
					if (Menu.mode == 2 && !recievedThing.trim().equals("START") && !recievedThing.trim().equals("U2"))
					{
						String[] arrStr = recievedThing.split(" ");
						pow = Double.parseDouble(arrStr[0]);
						pow_a = Double.parseDouble(arrStr[1]);
						cue_ang = Double.parseDouble(arrStr[2]);
						System.out.println(pow + " " + pow_a + " " + cue_ang);
						GameManager.receive = true;

					}

				}

			} catch (Exception e)
			{
				System.out.println("GameListener: " + e);
				excep = true;

			}

		}
	}
}
