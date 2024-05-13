// in MCAPL
package dataApp;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

import java.io.*;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;


public class Client {
    private Socket socket;
    private PrintWriter outRead;
    private BufferedReader inRead;

    private ObjectOutputStream outObject;
    private ObjectInputStream inObject;

    private Socket socket2;
    private PrintWriter outRead2;
    private BufferedReader inRead2;

    private ObjectOutputStream outObject2;
    private ObjectInputStream inObject2;
    
    public void connect(String serverAddress, int port) {
        try {

            socket = new Socket(serverAddress, port);
            outRead = new PrintWriter(socket.getOutputStream(), true);
            inRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outObject = new ObjectOutputStream(socket.getOutputStream());
            inObject = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void connect2(String serverAddress, int port) {
        try {

            socket2 = new Socket(serverAddress, port);
            outRead2 = new PrintWriter(socket2.getOutputStream(), true);
            inRead2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
            outObject2 = new ObjectOutputStream(socket2.getOutputStream());
            inObject2 = new ObjectInputStream(socket2.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public void sendMessage(String message) {
        outRead.println(message);
    }

    public void receiveMessage() {
        try {
            String messageFromServer = inRead.readLine();
            System.out.println("Server says: " + messageFromServer);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void sendObject() {
        try {
            User curUser = null;
            curUser = UserManager.users.get(0);

            outObject.writeObject(curUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEncObject(Key key){
        try {
            User curUser = null;
            curUser = UserManager.users.get(0);
            ClientGUI gui = new ClientGUI(false);
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE,key);
            SealedObject SO = new SealedObject(curUser, c);
            gui.openSentFrame();
            
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
    
    
    public Object receiveObject() {
        try {
            return inObject.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    

    public void disconnect() {
        try {
            inObject.close();
            outObject.close();
            inRead.close();
            outRead.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	public void connectserver(String serverAddress, int port) {
        try {

            socket = new Socket(serverAddress, port);
            outRead = new PrintWriter(socket.getOutputStream(), true);
            inRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outObject = new ObjectOutputStream(socket.getOutputStream());
            inObject = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
}

