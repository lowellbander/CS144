import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Formatter;

public class ComputeSHA{
	public static void main (String[] args){
            if(args.length != 1){
                System.err.println("Usage: java ComputeSHA <filename>");
                System.exit(-1);
            }

            String filename = args[0];
            File file = new File(filename);	
           
            //Convert file to byte array 
            byte[] bytes = new byte[(int) file.length()];
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(bytes);
            }
            catch (FileNotFoundException e) {
                System.out.println("File Not Found.");
                e.printStackTrace();
                System.exit(-1);
            }
            catch (IOException e1) {
                System.out.println("Error Reading The File.");
                e1.printStackTrace();
                System.exit(-1);
            }
            
            //Compute and print checksum
            MessageDigest md ;
            try {
                md = MessageDigest.getInstance("SHA-1");
                md.update(bytes);
                byte[] hash = md.digest();
                Formatter formatter = new Formatter();
                for(byte b : hash){
                    formatter.format("%02x", b);
                }
                System.out.println(formatter.toString());
            }
            catch (NoSuchAlgorithmException e){
                System.err.println("SHA-1 is not a valid message digest algorithm");
                System.exit(-1);
            }   
       }     
}

