// Ilya Zeldner
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSender {

    private static final String SERIALIZED_PACKET_FILE = "file_packet.dat";

    public static void main(String[] args) {

        String inputFilePath = "c:\\file_transfer\\file_packet.txt";
        File inputFile = new File(inputFilePath);

        if (!inputFile.exists() || !inputFile.isFile()) {
            System.err.println("Error: Input file not found or is not a regular file.");
            return;
        }
        System.out.println("Sender: Reading file: " + inputFilePath);
        try {
            // Read the file content into a byte array 
        	// using the specified streams
            long fileSize = Files.size(Paths.get(inputFilePath));
            if (fileSize > Integer.MAX_VALUE) {
                 throw new IOException("File is too large to read into a single byte array for this example.");
            }
            int fileSizeInt = (int) fileSize;
            byte[] fileContent = new byte[fileSizeInt];

            try (
                FileInputStream fis = new FileInputStream(inputFile);
                BufferedInputStream bis = new BufferedInputStream(fis);
                DataInputStream dis = new DataInputStream(bis)
            ) {
                dis.readFully(fileContent); // Read all bytes into the array
            } // Streams auto-closed

            System.out.println("Sender: Read " + fileContent.length + " bytes.");

            // Calculate the hash of the content
            String fileHash = FileHashUtil.calculateSha256Hash(fileContent);
            if (fileHash == null) {
                 throw new IOException("Failed to calculate file hash.");
            }
            System.out.println("Sender: Calculated hash: " + fileHash);

            // Create the serializable packet object
            FilePacket packet = new FilePacket(inputFile.getName(), fileHash, fileContent);
            System.out.println("Sender: Created FilePacket: " + packet);

            // 4. Serialize the packet object to a file (simulating sending)
            try (
                FileOutputStream fos = new FileOutputStream(SERIALIZED_PACKET_FILE);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
            ) {
                oos.writeObject(packet);
                System.out.println("Sender: Serialized packet to: " + SERIALIZED_PACKET_FILE);
            } // Streams auto-closed

            System.out.println("Sender: File reading, hashing, and serialization complete.");

        } catch (IOException e) {
            System.err.println("Sender Error: " + e.getMessage());
            e.printStackTrace();
            }
        }
}