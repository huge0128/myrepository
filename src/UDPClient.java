import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPClient {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        datagramSocket.setSoTimeout(1000);
        datagramSocket.connect(InetAddress.getByName("localhost"), 6666);

        byte[] data = "hello".getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(data, data.length);
        datagramSocket.send(packet);

        byte[] buffer = new byte[1024];
        packet = new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(packet);
        String resp = new String(packet.getData(), packet.getOffset(), packet.getLength());
        datagramSocket.close();
    }
}
