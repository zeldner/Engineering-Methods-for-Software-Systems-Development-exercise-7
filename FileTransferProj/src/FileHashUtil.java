//Ilya Zeldner
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * SHA-256 is a cryptographic hash function that produces a 256-bit 
 * (32-byte) hash value. 
 * It's used to verify the integrity of data and is a successor 
 * to SHA-1 due to its enhanced security against collision attacks. 
Key aspects of SHA-256:
Purpose:
Primarily used to verify the integrity of data, 
ensuring it hasn't been tampered with. 
Hash Size:
Generates a 256-bit hash value, which is a long, 
unique string of characters representing the data's "fingerprint". 
One-Way Function:
It's designed to be a one-way function, 
meaning it's extremely difficult to reverse and determine the original 
data from the hash. 
Security:
Considered more secure than its predecessor, 
SHA-1, and resistant to common cryptographic attacks. 
Applications:
Used in various applications like digital signatures, 
ensuring data integrity in blockchain technology, 
and in security protocols like SSL/TLS. 
Implementation:
SHA-256 is widely implemented in various programming languages and tools. 
In simpler terms: Imagine SHA-256 as a digital "fingerprint" for data. 
It's a unique and fixed-length code that represents the original data. If the data is changed in any way, even a small change,
the SHA-256 hash will change significantly,
allowing you to detect if the data has been tampered with. 
 */


public class FileHashUtil {

    /**
     * Calculates the SHA-256 hash of a byte array.
     * @param data The byte array to hash.
     * @return The SHA-256 hash as a hexadecimal string, or null if hashing fails.
     */
    public static String calculateSha256Hash(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data);

            // Convert byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            // This exception is highly unlikely for SHA-256, but good practice to catch
            System.err.println("SHA-256 algorithm not available: " + e.getMessage());
            return null;
        }
    }

    /**
     * Calculates the SHA-256 hash of a file's content by reading it into memory first.
     * Use for verification on the receiver side after writing the file.
     *
     * @param filePath The path to the file.
     * @return The SHA-256 hash as a hexadecimal string, or null if hashing/reading fails.
     */
    public static String calculateSha256HashFromFile(String filePath) {
         try {
             // Re-use the reading logic or simply read all bytes (Java 7+)
             byte[] fileContent = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath));
             return calculateSha256Hash(fileContent);
         } catch (java.io.IOException e) {
             System.err.println("Error reading file for hash calculation: " + e.getMessage());
             return null;
             }
         }
}