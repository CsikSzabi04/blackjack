package com.example.blackjack;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.*;

public class HelloController {

    private DatagramSocket socket = null;

    public void initialize() {
        try {
            socket = new DatagramSocket(678);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        Thread fogad = new Thread(new Runnable() {
            @Override
            public void run() {fogad();}
        });
        fogad.start();
    }


    private void kuld(String uzenet, String ip, int port) {
        try {
            byte[] adat = uzenet.getBytes("utf-8");
            InetAddress ipv4 = Inet4Address.getByName(ip);
            DatagramPacket packet = new DatagramPacket(adat, adat.length, ipv4, port);

            System.out.printf("%s:%d -> %s\n", ip, port, uzenet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fogad(){
        byte[] adat = new byte[256];
        DatagramPacket packet = new DatagramPacket(adat, adat.length);
        while (true) {
            try {
                socket.receive(packet);
                String uzenet = new String(adat, 0, packet.getLength(), "utf-8");
                String ip = packet.getAddress().getHostAddress();
                int port = packet.getPort();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}