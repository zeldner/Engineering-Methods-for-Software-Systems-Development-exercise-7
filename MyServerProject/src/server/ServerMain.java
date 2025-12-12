// Ilya Zeldner
package server;

import java.io.IOException;

public class ServerMain {
    
    // The default OCSF port
    final public static int DEFAULT_PORT = 5555;
    

    public static void main(String[] args) {
        int port = DEFAULT_PORT;

        System.setProperty("kryo.unsafe", "false");

        MyServer server = new MyServer(port);

        try {
            // Start listening for connections
            server.listen(); 
            System.out.println("Server is listening for connections on port " + port);
            
        } catch (IOException e) {
            System.err.println("Error: Could not listen on port " + port);
            e.printStackTrace();
        }
    }
}
