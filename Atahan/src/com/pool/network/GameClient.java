package com.pool.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class GameClient {

    public static String ip;
    Thread c,l;

    public GameClient() {

    }

    public static void send(String x){

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        DatagramSocket clientSocket = new DatagramSocket();
                        InetAddress IPAddress = InetAddress.getByName(ip);

                            byte[] sendData = new byte[1024];
                            String sData = x;
                            sendData = sData.getBytes();
                            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5000);

                            clientSocket.send(sendPacket);

                        clientSocket.close();

                    } catch (Exception e) {
                        System.out.println("GameClient : " + e);
                        Thread.currentThread().interrupt();
                    }
                }
            });
            thread.run();

    }


}
