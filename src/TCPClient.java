import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 6666);
        try(InputStream inputStream = socket.getInputStream()) {
            try(OutputStream outputStream = socket.getOutputStream()) {
                handle(inputStream, outputStream);
            }
        }
        socket.close();
        System.out.println("disconnected");
    }

    private static void handle(InputStream inputStream, OutputStream outputStream) throws IOException {
        var writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        Scanner sc = new Scanner(System.in);
        System.out.println("[server]" + reader.readLine());
        for(;;) {
            System.out.print(">>>" );
            String s = sc.nextLine();
            writer.write(s);
            writer.newLine();
            writer.flush();
            String resp = reader.readLine();
            System.out.println("<<< " + resp);
            if(resp.equals("bye")) {
                break;
            }
        }
    }
}
