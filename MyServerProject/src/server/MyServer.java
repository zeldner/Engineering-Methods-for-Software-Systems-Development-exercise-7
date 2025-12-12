// Ilya Zeldner
package server;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import java.io.IOException;

import common.KryoUtil;
import entities.*;
public class MyServer extends AbstractServer {

 public MyServer(int port) {
     super(port);
 }

 @Override
 protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
     if (msg instanceof byte[]) {
         Object received = KryoUtil.deserialize((byte[]) msg);

         if (received instanceof Classroom) {
             Classroom cls = (Classroom) received;
             System.out.println("Server Received: " + cls.toString());
             
             // Modify the list (mark them as "Graded")
             for(Student s : cls.getStudents()) {
                 s.setName(s.getName() + " (Checked)");
             }

             // SEND BACK: Serialize again and send
             try {
                 byte[] response = KryoUtil.serialize(cls);
                 client.sendToClient(response); 
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
     }
 }
 
 @Override
 protected void serverStarted() {
     System.out.println("Server listening for connections...");
 }
 
 @Override
 protected void serverStopped() {
     System.out.println("Server has stopped listening.");
 }
}
