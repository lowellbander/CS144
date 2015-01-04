import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ComputeSHA{
	public static void main (String[] args){
            if(args.length != 1){
                System.err.println("Usage: java ComputeSHA <filename>");
                System.exit(-1);
            }

            String filename = args[0];
            File file = new File(filename);	
           
            //Convert file to byte array 
            byte[] b = new byte[(int) file.length()];
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(b);
            }
            catch (FileNotFoundException e) {
                System.out.println("File Not Found.");
                e.printStackTrace();
            }
            catch (IOException e1) {
                System.out.println("Error Reading The File.");
                e1.printStackTrace();
            }
            
            //Compute and print checksum
            MessageDigest md ;
            try {
                md = MessageDigest.getInstance("SHA-1");
                md.update(b);
                byte[] hash = md.digest();
                for(byte h : hash){
                    System.out.print(Integer.toHexString(0xff & h));
                }
                System.out.println("");
            }
            catch (NoSuchAlgorithmException e){
                System.err.println("SHA-1 is not a valid message digest algorithm");
            }   
       }     
}

