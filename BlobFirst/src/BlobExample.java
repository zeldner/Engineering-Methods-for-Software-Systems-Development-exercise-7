// Ilya Zeldner
import java.sql.*;
import java.io.*;
public class BlobExample {
	public static void main(String[] args) {
		Connection conn = null;
		try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            System.out.println("Driver definition succeed");
        } catch (Exception ex) {
        	/* handle the error*/
        	 System.out.println("Driver definition failed");
        }
        try 
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/test?serverTimezone=IST","root","root");
            System.out.println("SQL connection succeed");
     	} 
        catch (SQLException ex) {/* handle any errors*/
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
        }
		java.sql.Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.execute("CREATE TABLE SampleBLOB(Name VARCHAR(255), FileB BLOB)");
			System.out.println("Table Created");
			String query = "INSERT INTO sampleBLOB(Name, FileB) VALUES (?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			FileInputStream fin = new FileInputStream("files/dataset.csv");
			pstmt.setString(1, "sample FileB");
			pstmt.setBlob(2, fin);
			pstmt.execute();
			ResultSet rs = stmt.executeQuery("SELECT * from SampleBLOB WHERE Name = \"sample FileB\"");
			rs.next();
			Blob blob = rs.getBlob("FileB");
			byte byteArray[] = blob.getBytes(1, (int)blob.length());
			try (FileOutputStream out = new FileOutputStream("files/file_output.csv")) {
				out.write(byteArray);
			}	
		} catch (SQLException e) {e.printStackTrace();} 
		catch (FileNotFoundException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
	}
}
