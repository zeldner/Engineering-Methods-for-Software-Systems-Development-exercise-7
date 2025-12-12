// Ilya Zeldner
package client;

import java.io.IOException;

import entities.*;
public class ClientMain {
    
    final public static int DEFAULT_PORT = 5555;
    final public static String DEFAULT_HOST = "localhost";    

    public static void main(String[] args) {
        System.setProperty("kryo.unsafe", "false"); 
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;
        MyClient client = new MyClient(host, port);

        try {
            client.openConnection();

            // Create a Classroom
            Classroom softwareEng = new Classroom("Software Engineering");

            // Add multiple students
            softwareEng.addStudent(new Student("Ilya", 1, 95));
            softwareEng.addStudent(new Student("Alice", 2, 88));
            softwareEng.addStudent(new Student("Bob", 3, 91));

            System.out.println("Sending: " + softwareEng.toString());

            // Send the WHOLE LIST at once
            client.sendComplexObject(softwareEng);

           // For this test, we sleep so we have time to see the server reply before quitting.
            Thread.sleep(5000); 
            
            // Clean up
            client.closeConnection();

        } catch (IOException e) {
            System.out.println("Could not connect to server. Is it running?");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
