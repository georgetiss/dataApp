// in MCAPL 

package dataApp;

import java.io.BufferedReader;
import java.util.Date;
import java.util.Random;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

import ail.syntax.Term;


public class Agent123 {
	
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream outObject;
    private ObjectInputStream inObject;
	public static User AgUser = null;
    public static String changedStr = "";
	
    public void start(int port, boolean returned) {
        try {
    		AgUser = UserManager.users.get(0); 
        	returned = returned;
        	System.out.println(returned);
            serverSocket = new ServerSocket(port);
            System.out.println("Agent is running and waiting for a client...");
            clientSocket = serverSocket.accept();
            System.out.println("Agent and client connected.");
            outObject = new ObjectOutputStream(clientSocket.getOutputStream());
            inObject = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    public void receiveEncObject(Key key) {
        try {
            SealedObject SO = (SealedObject) inObject.readObject();

            User curUser = (User) SO.getObject(key);
            String strUser = ("Unaltered in Agent: Name: " + curUser.getName() + ", Age: " + curUser.getAge() + " " + curUser.getDOB() + ", Married: " + curUser.getMarital() + ", GPS: " + curUser.getGPS() + ", Address: " + curUser.getAddress() );
            System.out.println( strUser);
            AgUser = curUser; 

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void sendEncObject(Key key){
        try {
            User curUser = null;
            curUser = UserManager.users.get(0);

            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE,key);
            SealedObject SO = new SealedObject(curUser, c);

            outObject.writeObject(SO);

        } catch (IOException | IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
    
    private static boolean randomBoolean(){
        boolean status = true;
        int num = createRandomIntBetween(1,2);
        if (num == 1){
            status = true;
        } else {
          status = false;
        }
        return status;
    }
    
    public static void removedatapoint(Term term, boolean returns) {
    			
    	String point = String.valueOf(term);
    	
    	AgUser = UserManager.users.get(0);
    	
    	if (point.equals("dob")) {
    		
            int day = createRandomIntBetween(1, 28);
            int month = createRandomIntBetween(1, 12);
            int year = createRandomIntBetween(1970, 2000);
            LocalDate date = LocalDate.of(year, month, day);
    		
       		changedStr = changedStr + " Date of Birth";
    		AgUser.setdob(date);
    		//create screen
    		ClientGUI.openCheckFrame(AgUser, changedStr, returns);
    		
    	}else if (point.equals("full_name")) {
    		changedStr = changedStr + " Name";
    		AgUser.setName(null);
    		//create screen
    		ClientGUI.openCheckFrame(AgUser, changedStr, returns);
    		
    	}else if (point.equals("gps")) {
    		changedStr = changedStr + " GPS";
    		boolean status = false;
    		AgUser.setgps(status);
    		//create screen
    		ClientGUI.openCheckFrame(AgUser, changedStr, returns);
    		
    	}else if (point.equals("age")) {
    		changedStr = changedStr + " age";
    		int age = createRandomIntBetween(20,50);
    		AgUser.setAge(age);
    		// create popup screen
    		ClientGUI.openCheckFrame(AgUser, changedStr, returns);
    		
    	} else if (point.equals("marital_status")) {
    		changedStr = changedStr + " Marital Status";
    		boolean status = randomBoolean();
    		AgUser.setMarital(status);
    		//Create popup change screen
    		ClientGUI.openCheckFrame(AgUser, changedStr, returns);
    		
    	} else if (point.equals("address")) {
    		changedStr = changedStr + " Address";
    		String address = null;
    		AgUser.setAddress(address);
    		//create screen
    		ClientGUI.openCheckFrame(AgUser, changedStr, returns);
    		
    	}else {
    		System.out.println("Incorrect data type input " + point);
    	}
    }
    
    public void stop() {
        try {
            outObject.close();
            inObject.close();
            clientSocket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public static void changeData(Term term, boolean returner) {
		
		String data = String.valueOf(term);
		
		AgUser = UserManager.users.get(0); 
		
		if (data.equals("full_name")) {
			String ogName = AgUser.getName();
			
			if (ogName.contains(" ")) {
				int index = ogName.indexOf(' ');
				
		        String cutString;
		      
		        cutString = ogName.substring(0, index);
				AgUser.setName(cutString);
				changedStr = changedStr + "Name";					
				ClientGUI.openCheckFrame(AgUser, changedStr, returner);
			}			
		}
	}	
}
