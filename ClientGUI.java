//in MCAPL

package dataApp;
import javax.crypto.KeyGenerator;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class ClientGUI {
    private JFrame frame;

    private Server server;
    public static boolean buttonPress;
    public static boolean checkButtonPress;
    public static boolean button1Press;
    public static boolean button2Press;

    public static boolean button3Press;
    private static boolean already = false;
	public static boolean acceptBut;
	public static Key key;

    public ClientGUI(boolean x) {
    	if(x) {
        	prepareGUI();
    	}
    }


    private void prepareGUI() {
        frame = new JFrame("Client");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 2)); // 2 rows, 2 column
        JButton startButton = new JButton("No Agent");
        JButton twoButton = new JButton("Server -> Client -> Agent -> Client -> Server");
        JButton threeButton = new JButton("Server -> Client -> Agent -> Server");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonPress = true;
                button1Press = true;                
                frame.setVisible(false);
            }
        });

        twoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonPress = true;
                button2Press = true;
                frame.setVisible(false);
            }
        });

        threeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonPress = true;
                button3Press = true;
                //openSentFrame();
                frame.setVisible(false);
            }
        });




        panel.add(startButton);
        panel.add(twoButton);
        panel.add(threeButton);
        frame.add(panel);
        frame.setVisible(true);
    }

    public static boolean isButtonPress(){
        return buttonPress;
    }

    public void openSentFrame() {
        JFrame newFrame = new JFrame("Client Sent");
        newFrame.setSize(1000, 500);
        User curUser = UserManager.users.get(0);
        JLabel label = new JLabel("Client Data: Name: " + curUser.getName() + ", Age: " + curUser.getAge() + ", " + curUser.getDOB() + ", Married: " + curUser.getMarital() + ", GPS: " + curUser.getGPS() + ", Address: " + curUser.getAddress() );
        JLabel label2 = new JLabel();
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(label);
        panel.add(label2);
        newFrame.add(panel);
        newFrame.setVisible(true);
        }
    


	public static void openCheckFrame(User agUser, String changedStr, boolean returner) {
		// TODO Auto-generated method stub
		
		if (!already) {
			
	        JFrame newFrame = new JFrame("Data");
	        newFrame.setSize(1000, 500);
	        JButton tickButton = new JButton("Agree to new data");
	        User curUser = UserManager.users.get(0);
	        JLabel label = new JLabel("Changed Data: Name: " + agUser.getName() + ", Age: " + agUser.getAge() + ", " + agUser.getDOB() + ", Married: " + agUser.getMarital() + ", GPS: " + agUser.getGPS() + ", Address: " + agUser.getAddress() );       
	        JLabel label2 = new JLabel(changedStr);
	        JPanel panel = new JPanel(new GridLayout(3, 1)); // 3 rows, 1 column
	        panel.add(label);
	        panel.add(label2);
	        if (returner) {
	        	panel.add(tickButton); 
	        }
	             
	        newFrame.add(panel);
	        newFrame.setVisible(true);
	        
	        tickButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	
	            	try {
						sendUser(curUser);
					} catch (NoSuchAlgorithmException e1) {
						
					}
	                
	            }
	        });
		}
		 
		
}


	private static void sendUser(User user) throws NoSuchAlgorithmException {
		
		 Server server = new Server();
         Client client = new Client();            
         
 	    KeyGenerator gen = KeyGenerator.getInstance("AES");
 	    gen.init(128);
 	    key = gen.generateKey();

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
         System.out.println(key);
         client.sendEncObject(key);
         server.receiveEncObject(key);

         try { // wait for a short period
				Thread.sleep(10000);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			} 
         client.disconnect();
         server.stop();
         System.exit(0);
	
	}
	
}