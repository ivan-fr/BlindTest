package sockets;

import jFrame.MainFrame;

import java.io.IOException;
import java.net.Socket;

public class ClientMain {
    public static void main(String args[]) throws IOException {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        Socket socketClientAction = null;
        Socket socketBroadcast = null;
        try {
            socketClientAction = new Socket("localhost", 1500);
            socketBroadcast = new Socket("localhost", 1500);
            ClientHandler clientHandler = new ClientHandler(socketClientAction, socketBroadcast);
            java.awt.EventQueue.invokeLater(() -> {
                MainFrame mainFrame = new MainFrame(clientHandler);
                clientHandler.setMainFrame(mainFrame);
                mainFrame.setVisible(true);
            });
        } catch (IOException e) {
            assert socketBroadcast != null;
            socketBroadcast.close();
            socketClientAction.close();
        }
    }
}
