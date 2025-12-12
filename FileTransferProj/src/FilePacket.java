// Ilya Zeldner
import java.io.Serializable;

public class FilePacket implements Serializable {
    private String fileName;
    private String fileHash; // SHA-256 hash as a hex string
    private byte[] fileData; // The binary content of the file

    // Constructor for the sender to create a packet
    public FilePacket(String fileName, String fileHash, byte[] fileData) {
        this.fileName = fileName;
        this.fileHash = fileHash;
        this.fileData = fileData;
    }

    // Getters for the receiver to access data
    public String getFileName() {
        return fileName;
    }

    public String getFileHash() {
        return fileHash;
    }
    public byte[] getFileData() {
        return fileData;
    }

    @Override
    public String toString() {
        return "FilePacket{" +
               "fileName='" + fileName + '\'' +
               ", fileHash='" + fileHash + '\'' +
               ", fileData size=" + (fileData != null ? fileData.length : 0) + " bytes" +
           '}';
        }
    }