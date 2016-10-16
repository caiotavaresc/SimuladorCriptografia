// ClientGUI.java
// ClientGUI provides a user interface for sending and receiving
// messages to and from the DeitelMessengerServer.
package com.deitel.messenger;

// Java core packages
import java.awt.*;
import java.awt.event.*;

// Java standard extensions
import javax.swing.*;
import javax.swing.border.*;

// Deitel packages

public class ClientGUI extends JFrame {
   
   // JMenu for connecting/disconnecting server
   private JMenu serverMenu;
   
   // JTextAreas for displaying and inputting messages
   private JTextArea messageArea;
   private JTextArea inputArea;   
   
   // JButton for sending messages
   private JButton sendButton;
   
   // JLabel for displaying connection status
   private JLabel statusBar;
   
   // NICK e Id do Usuario para enviar mensagens
   private String userName;
   private int userId;
   
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
      
      // create File JMenu      
      serverMenu = new JMenu ( "Servidor" );   
      serverMenu.setMnemonic( 'S' );
      JMenuBar menuBar = new JMenuBar();
      menuBar.add( serverMenu );
      setJMenuBar( menuBar );           
     
      // create JTextArea for displaying messages
      messageArea = new JTextArea();
      
      // disable editing and wrap words at end of line
      messageArea.setEditable( false );
      messageArea.setWrapStyleWord( true );
      messageArea.setLineWrap( true );
      
      messageArea.setOpaque(false);
      messageArea.setBackground(Color.RED);
      
      // put messageArea in JScrollPane to enable scrolling
      JPanel messagePanel = new JPanel();
      JLabel bg = new JLabel(new ImageIcon(getClass().getResource( "images/bg.png" )));
      
      messagePanel.setLayout( new BorderLayout( 10, 10 ) );
      bg.setSize(messagePanel.getSize());
      
      messageArea.setBackground(Color.red);
      JScrollPane bb = new JScrollPane(messageArea);
      bb.getViewport().setOpaque(false);
      bb.setOpaque(false);
      messagePanel.add(bg);
      messagePanel.add(bb, BorderLayout.CENTER );
      
      // create JTextArea for entering new messages
      inputArea = new JTextArea( 4, 20 );
      inputArea.setWrapStyleWord( true );
      inputArea.setLineWrap( true );
      inputArea.setEditable( true );
      
      // create Icon for sendButton
      Icon sendIcon = new ImageIcon( 
         getClass().getResource( "images/Send.gif" ) );
      
      // create sendButton and disable it
      sendButton = new JButton( "Enviar", sendIcon );
      sendButton.setEnabled( true );
      
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
      statusBar = new JLabel( "Desconectado" );
      statusBar.setBorder( 
         new BevelBorder( BevelBorder.LOWERED ) );

      // lay out components in JFrame
      Container container = getContentPane();
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
      
      // clear messageArea
      messageArea.setText( "" );
      inputArea.requestFocus();  

   } // end ClientGUI constructor

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        statusBar.setText( "Conectado: " + userName ); 
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
   
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