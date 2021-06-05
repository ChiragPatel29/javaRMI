package server;

import utilities.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer extends Thread{
        CenterServer centerServer;
        public UDPServer(CenterServer centerServer){
            this.centerServer=centerServer;
        }




    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(Constants.getUDPPort(centerServer.serverName));

            while (true) {
                byte[] buffer = new byte[50];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                socket.receive(packet);
                System.out.println("Text received is: " + new String(buffer, 0, packet.getLength()));

                String recordCount=centerServer.serverName+" "+centerServer.getCurrentServerCount();

                byte[] buffer2 = recordCount.getBytes();
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buffer2, buffer2.length, address, port);
                socket.send(packet);
            }

        } catch (
                SocketException e) {
            System.out.println("SocketException: " + e.getMessage());
        } catch (
                IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

    }


}
