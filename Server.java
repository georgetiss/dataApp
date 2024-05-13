// in MCAPL
package dataApp;

import javax.crypto.SealedObject;
import javax.swing.*;

import java.awt.FlowLayout;
import java.io.*;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;


public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter outRead;
    private BufferedReader inRead;
    private ObjectOutputStream outObject;
    private ObjectInputStream inObject;

    public String Key = "770A8A65DA156D24EE2A093277530142";
    public String Salt = "92AE31A79FEEB2A3";
    public String iv= "F5502320F8429037B8DAEF761B189D12";

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running and waiting for a client...");
            clientSocket = serverSocket.accept();
            System.out.println("Client connected.");
            outRead = new PrintWriter(clientSocket.getOutputStream(), true);
            inRead = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outObject = new ObjectOutputStream(clientSocket.getOutputStream());
            inObject = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        outRead.println(message);
    }

    public String receiveMessage() {
        try {
            return inRead.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendObject(Object obj) {
        try {
            outObject.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    public void stop() {
        try {
            inRead.close();
            outRead.close();
            outObject.close();
            inObject.close();
            clientSocket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveEncObject(Key key) {
        try {
            SealedObject SO = (SealedObject) inObject.readObject();

            User curUser = (User) SO.getObject(key);
            String strUser = ("SERVER: Name: " + curUser.getName() + ", Age: " + curUser.getAge() + " " + curUser.getDOB() + ", Married: " + curUser.getMarital() + ", GPS: " + curUser.getGPS() + ", Address: " + curUser.getAddress() );
            System.out.println( strUser);
            serverGUI(curUser);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
    
	public static void serverGUI(User user) {
        JFrame newFrame = new JFrame("Server recived");
        newFrame.setSize(1000, 500);
        User curUser = user;
        JLabel label = new JLabel("Server: Name: " + curUser.getName() + ", Age: " + curUser.getAge() + ", " + curUser.getDOB() + ", Married: " + curUser.getMarital() + ", GPS: " + curUser.getGPS() + ", Address: " + curUser.getAddress() );

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(label);

        newFrame.add(panel);
        newFrame.setVisible(true);
	}

}

