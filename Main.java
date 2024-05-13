//in mcapl

package dataApp;

//import jdk.nashorn.internal.runtime.regexp.joni.constants.Arguments;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.crypto.KeyGenerator;
import javax.swing.*;
import dataAgent.*;

public class Main {

    public static Key key;

	public static boolean main(String[] args) throws NoSuchAlgorithmException {

	    KeyGenerator gen = KeyGenerator.getInstance("AES");
	    gen.init(128);
	    key = gen.generateKey();
		
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClientGUI(true);
            }
        });
             

        while (!ClientGUI.isButtonPress()) {
            try {
                Thread.sleep(100); // wait for a short period
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }




        boolean returner;
		if (ClientGUI.button1Press){
            // No 3rd Party involvement
            Server server = new Server();
            Client client = new Client();            
            

            // Start server in a separate thread
            Thread serverThread = new Thread(() -> {
                server.start(5000);
            });
            serverThread.start();

            // Wait for a moment to let the server start
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Connect client to server
            client.connect("localhost", 5000);
        	
            long startTime = System.nanoTime();
            server.sendMessage("Please send over your details.");
            client.receiveMessage();
            client.sendEncObject(key);
            server.receiveEncObject(key);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);  // Duration in nanoseconds
            double milliseconds = (double) duration / 1_000_000.0;
            System.out.println("Time taken: " + milliseconds + " milliseconds");
            try { // wait for a short period
				Thread.sleep(10000);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			} 
            client.disconnect();
            server.stop();
            System.exit(0);
            
            
        } else if (ClientGUI.button2Press) {
            //Server -> client -> Agent -> client -> Server

        	return returner = true;
                                	
        } else if(ClientGUI.button3Press) {
        	//Server -> client -> Agent -> server
        	
        	return returner = false;        	
        }
		
        return returner = true;



    }

}




