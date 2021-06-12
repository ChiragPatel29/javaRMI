package Server;

import Utilities.Configurations;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer extends Thread {
    CenterServer centerServer;

    public UDPServer(CenterServer centerServer) {
        this.centerServer = centerServer;
    }

    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(Configurations.getUDPPort(centerServer.serverName));

            while (true) {
                byte[] buffer = new byte[50];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                socket.receive(packet);
                System.out.println("Getting counts from " + centerServer.serverName);

                String recordCount = centerServer.serverName + " " + centerServer.getCurrentServerCount();

                byte[] buffer2 = recordCount.getBytes();
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buffer2, buffer2.length, address, port);
                socket.send(packet);
            }

        } catch (IOException e) {
        }
    }
}
