// ClientGUI.java
// ClientGUI provides a user interface for sending and receiving
// messages to and from the DeitelMessengerServer.
package com.deitel.messenger;

// Java core packages
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

// Java standard extensions
import javax.swing.*;
import javax.swing.border.*;

// Deitel packages
import com.deitel.messenger.*;

public class ClientGUI extends JFrame {
   
   // JMenu for connecting/disconnecting server
   private JMenu serverMenu;
   
   // JTextAreas for displaying and inputting messages
   private JTextArea messageArea;
   private JTextArea inputArea;   
 
   // JButtons and JMenuItems for connecting and disconnecting
   private JButton connectButton;
   private JMenuItem connectMenuItem;   
   private JButton disconnectButton;
   private JMenuItem disconnectMenuItem;
   
   // JButton for sending messages
   private JButton sendButton;
   
   // JLabel for displaying connection status
   private JLabel statusBar;
   
   // userName to add to outgoing messages
   private String userName;
   
   // MessageManager for communicating with server
   private MessageManager messageManager;
   
   // MessageListener for receiving incoming messages
   private MessageListener messageListener;
 
   // ClientGUI constructor
   public ClientGUI(MessageManager manager) 
   {       
      super( "Deitel Messenger" );
      
      // set the MessageManager
      messageManager = manager;
      
      // create MyMessageListener for receiving messages
      messageListener = new MyMessageListener();
      
      // create File JMenu      
      serverMenu = new JMenu ( "Server" );   
      serverMenu.setMnemonic( 'S' );
      JMenuBar menuBar = new JMenuBar();
      menuBar.add( serverMenu );
      setJMenuBar( menuBar );  
      
      // create ImageIcon for connect buttons
      Icon connectIcon = new ImageIcon( 
         getClass().getResource( "images/Connect.gif" ) );
      
      // create connectButton and connectMenuItem
      connectButton = new JButton( "Connect", connectIcon );
      connectMenuItem = new JMenuItem( "Connect", connectIcon );  
      connectMenuItem.setMnemonic( 'C' );
      
      // create ConnectListener for connect buttons
      ActionListener connectListener = new ConnectListener();
      connectButton.addActionListener( connectListener );
      connectMenuItem.addActionListener( connectListener ); 
      
      // create ImageIcon for disconnect buttons
      Icon disconnectIcon = new ImageIcon( 
         getClass().getResource( "images/Disconnect.gif" ) );
      
      // create disconnectButton and disconnectMenuItem
      disconnectButton = new JButton( "Disconnect", 
         disconnectIcon );
      disconnectMenuItem = new JMenuItem( "Disconnect", 
         disconnectIcon );      
      disconnectMenuItem.setMnemonic( 'D' );
      
      // disable disconnect buttons
      disconnectButton.setEnabled( false );
      disconnectMenuItem.setEnabled( false );
      
      // create DisconnectListener for disconnect buttons
      ActionListener disconnectListener = 
         new DisconnectListener();
      disconnectButton.addActionListener( disconnectListener );
      disconnectMenuItem.addActionListener( disconnectListener );
      
      // add connect and disconnect JMenuItems to fileMenu
      serverMenu.add( connectMenuItem );
      serverMenu.add( disconnectMenuItem );           
  
      // add connect and disconnect JButtons to buttonPanel
      JPanel buttonPanel = new JPanel();
      buttonPanel.add( connectButton );
      buttonPanel.add( disconnectButton );
     
      // create JTextArea for displaying messages
      messageArea = new JTextArea();
      
      // disable editing and wrap words at end of line
      messageArea.setEditable( false );
      messageArea.setWrapStyleWord( true );
      messageArea.setLineWrap( true );
      
      // put messageArea in JScrollPane to enable scrolling
      JPanel messagePanel = new JPanel();
      messagePanel.setLayout( new BorderLayout( 10, 10 ) );
      messagePanel.add( new JScrollPane( messageArea ), 
         BorderLayout.CENTER );
      
      // create JTextArea for entering new messages
      inputArea = new JTextArea( 4, 20 );
      inputArea.setWrapStyleWord( true );
      inputArea.setLineWrap( true );
      inputArea.setEditable( false );
      
      // create Icon for sendButton
      Icon sendIcon = new ImageIcon( 
         getClass().getResource( "images/Send.gif" ) );
      
      // create sendButton and disable it
      sendButton = new JButton( "Send", sendIcon );
      sendButton.setEnabled( false );
      
      // create ActionListener for sendButton
      sendButton.addActionListener(
         new ActionListener() {
            
            // send new message when user activates sendButton
            public void actionPerformed( ActionEvent event )
            {
               messageManager.sendMessage( userName, 
                  inputArea.getText());
               
               // clear inputArea
               inputArea.setText("");
            }
         } // end ActionListener
      );
      
      // lay out inputArea and sendButton in BoxLayout and 
      // add Box to messagePanel
      Box box = new Box( BoxLayout.X_AXIS );
      box.add( new JScrollPane( inputArea ) );
      box.add( sendButton );
      messagePanel.add( box, BorderLayout.SOUTH );
      
      // create JLabel for statusBar with a recessed border
      statusBar = new JLabel( "Not Connected" );
      statusBar.setBorder( 
         new BevelBorder( BevelBorder.LOWERED ) );

      // lay out components in JFrame
      Container container = getContentPane();
      container.add( buttonPanel, BorderLayout.NORTH );
      container.add( messagePanel, BorderLayout.CENTER );
      container.add( statusBar, BorderLayout.SOUTH );
      
      // add WindowListener to disconnect when user quits
      addWindowListener ( 
         new WindowAdapter () {
            
            // disconnect from server and exit application
            public void windowClosing ( WindowEvent event ) 
            {
               messageManager.disconnect( messageListener );
               System.exit( 0 );
            }
         }
      );

   } // end ClientGUI constructor
   
   // ConnectListener listens for user requests to connect to
   // DeitelMessengerSever
   private class ConnectListener implements ActionListener {
      
      // connect to server and enable/disable GUI components
      public void actionPerformed( ActionEvent event )
      {
         // connect to server and route messages to 
         // messageListener
         messageManager.connect( messageListener ); 

         // prompt for userName
         userName = JOptionPane.showInputDialog( 
            ClientGUI.this, "Enter user name:" );
         
         // clear messageArea
         messageArea.setText( "" );

         // update GUI components
         connectButton.setEnabled( false );
         connectMenuItem.setEnabled( false );
         disconnectButton.setEnabled( true );
         disconnectMenuItem.setEnabled( true );
         sendButton.setEnabled( true );
         inputArea.setEditable( true );
         inputArea.requestFocus();  
         statusBar.setText( "Connected: " + userName );                
      }   
      
   } // end ConnectListener inner class
   
   // DisconnectListener listens for user requests to disconnect
   // from DeitelMessengerServer
   private class DisconnectListener implements ActionListener {
      
      // disconnect from server and enable/disable GUI components
      public void actionPerformed( ActionEvent event )
      {
         // disconnect from server and stop routing messages
         // to messageListener
         messageManager.disconnect( messageListener );

         // update GUI componets
         sendButton.setEnabled( false );
         disconnectButton.setEnabled( false );
         disconnectMenuItem.setEnabled( false );
         inputArea.setEditable( false );
         connectButton.setEnabled( true );         
         connectMenuItem.setEnabled( true );
         statusBar.setText( "Not Connected" );         
      }
      
   } // end DisconnectListener inner class
   
   // MyMessageListener listens for new messages from the
   // MessageManager and displays the messages in messageArea
   // using a MessageDisplayer.
   private class MyMessageListener implements MessageListener {

      // when received, display new messages in messageArea
      public void messageReceived( String from, String message ) 
      {
         // append message using MessageDisplayer and
         // invokeLater, ensuring thread-safe access messageArea
         SwingUtilities.invokeLater( 
            new MessageDisplayer( from, message ) );

      } // end method messageReceived  
      
   }  // end MyMessageListener inner class 
   
   // MessageDisplayer displays a new messaage by
   // appending the message to the messageArea JTextArea. This
   // Runnable object should be executed only on the Event 
   // thread, because it modifies a live Swing component.
   private class MessageDisplayer implements Runnable {
      
      private String fromUser;
      private String messageBody;
      
      // MessageDisplayer constructor
      public MessageDisplayer( String from, String body )
      {
         fromUser = from;
         messageBody = body;
      }
      
      // display new message in messageArea
      public void run() 
      {
         // append new message
         messageArea.append( "\n" + fromUser + "> " + 
            messageBody );   

         // move caret to end of messageArea to ensure new 
         // message is visible on screen
         messageArea.setCaretPosition( 
            messageArea.getText().length() );                          
      }      
      
   } // end MessageDisplayer inner class
}


/**************************************************************************
 * (C) Copyright 2002 by Deitel & Associates, Inc. and Prentice Hall.     *
 * All Rights Reserved.                                                   *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/