// Ilya Zeldner
package client;

import common.KryoUtil;
import ocsf.client.AbstractClient;
import entities.*;

public class MyClient extends AbstractClient {

 public MyClient(String host, int port) {
     super(host, port);
 }

 // RECEIVING FROM SERVER
 @Override
 protected void handleMessageFromServer(Object msg) {
     // Check if it's binary
     if (msg instanceof byte[]) {
         // Deserialize
         Object obj = KryoUtil.deserialize((byte[]) msg);
         
         // Check if it is our Classroom returning
         if (obj instanceof Classroom) {
             Classroom responseClass = (Classroom) obj;
             System.out.println("Client got response:");
             
             // Print the modified students
             for(Student s : responseClass.getStudents()) {
                 System.out.println(" - " + s.getName()); 
             }
         }
     }
 }

 // SENDING TO SERVER
 // Create a custom method to send complex objects safely
 public void sendComplexObject(Object obj) {
     try {
         // Convert to bytes using Kryo
         byte[] payload = KryoUtil.serialize(obj);
         
         // Send the byte array using OCSF
         sendToServer(payload); 
         
     } catch (Exception e) {
         e.printStackTrace();
     }
 }
}
