// Ilya Zeldner
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
public class FileReceiver {
    private static final String SERIALIZED_PACKET_FILE = "file_packet.dat"; 
    // Must match sender

    public static void main(String[] args) {
        File serializedFile = new File(SERIALIZED_PACKET_FILE);

        if (!serializedFile.exists() || !serializedFile.isFile()) {
            System.err.println("Error: Serialized packet file not found. Run FileSender first.");
            return;
        }
        System.out.println("Receiver: Reading serialized packet from: " + SERIALIZED_PACKET_FILE);
        try {
            // Read and Deserialize the packet object
            FilePacket receivedPacket;
            try (
                FileInputStream fis = new FileInputStream(serializedFile);
                ObjectInputStream ois = new ObjectInputStream(fis)
            ) {
                // Read the object 
            	// requires the FilePacket class to be available
                Object obj = ois.readObject();
                if (obj instanceof FilePacket) {
                    receivedPacket = (FilePacket) obj;
                    System.out.println("Receiver: Deserialized packet: " + receivedPacket);
                } else {
                    throw new IOException("Received data is not a FilePacket object.");
                }
            } // Streams auto-closed

            // Extract data from the packet
            String receivedFileName = receivedPacket.getFileName();
            String receivedHash = receivedPacket.getFileHash();
            byte[] receivedData = receivedPacket.getFileData();

            if (receivedData == null || receivedHash == null) {
                 System.err.println("Receiver Error: Packet missing data or hash.");
                 return;
            }
            System.out.println("Receiver: Extracted file name: " + receivedFileName);
            System.out.println("Receiver: Extracted data size: " + receivedData.length + " bytes");
            System.out.println("Receiver: Received hash: " + receivedHash);

            // Write the received data to a new file
            String reconstructedFilePath = "reconstructed_" + receivedFileName;
            try (FileOutputStream fos = new FileOutputStream(reconstructedFilePath)) {
                fos.write(receivedData);
            } // Stream auto-closed
            System.out.println("Receiver: Data written to reconstructed file: " + reconstructedFilePath);

            // Calculate the hash of the newly created file
            System.out.println("Receiver: Calculating hash of the reconstructed file...");
            String reconstructedHash = FileHashUtil.calculateSha256HashFromFile(reconstructedFilePath);

            if (reconstructedHash == null) {
                System.err.println("Receiver Error: Failed to calculate hash of reconstructed file.");
                 new File(reconstructedFilePath).delete();
                return;
            }
            System.out.println("Receiver: Hash of reconstructed file: " + reconstructedHash);

            // Compare the hashes for verification
            if (receivedHash.equals(reconstructedHash)) {
                System.out.println("\nReceiver: Verification SUCCESS! The reconstructed file is exactly the same as the original.");
            } else {
                System.err.println("\nReceiver: Verification FAILED! Hashes do NOT match. The reconstructed file may be corrupted.");
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Receiver Error during deserialization or file operations: " + e.getMessage());
            e.printStackTrace();
            }
        }
}