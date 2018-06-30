package com.example.clinton.chatclientdemo;

import android.os.AsyncTask;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;
import java.util.Collection;


public class Connect extends AsyncTask<Void, Void, Void> {
    AbstractXMPPConnection conn2;

    @Override
    protected Void doInBackground(Void... params) {
        setUpConnection();
        doAll();
        //sendMessage();
        return null;
    }

    private void setUpConnection()
    {
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword("john", "12345")
                .setServiceName("clinton-server.sytes.net")
                .setHost("clinton-server.sytes.net")
                .setPort(5222)
                .setDebuggerEnabled(true)
                .setSendPresence(true)
                .setResource("android")
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .build();
        conn2 = new XMPPTCPConnection(config);
        try {
            conn2.connect();
        } catch (SmackException | XMPPException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connected "+conn2.isConnected());
    }

    private void doAll()
    {


        Roster roster = Roster.getInstanceFor(conn2);
        roster.setSubscriptionMode(Roster.SubscriptionMode.accept_all);
        roster.addRosterListener(new RosterListener() {
            @Override
            public void entriesAdded(Collection<String> clctn) {
                System.out.println("Entries added");
            }

            @Override
            public void entriesUpdated(Collection<String> clctn) {
                System.out.println("Entries updated");

//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void entriesDeleted(Collection<String> clctn) {
                System.out.println("Entries deleted");

//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void presenceChanged(Presence prsnc) {
                System.out.println("Prescence changed " + prsnc.getFrom() + " " + prsnc);

//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

//Login before performing other tasks like messaging etc
        try {


            conn2.login();
            System.out.println("Loggedin "+conn2.isAuthenticated());
        } catch (XMPPException | SmackException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Presence presence = new Presence(Presence.Type.available);
        presence.setStatus("At Home");
        try {
            conn2.sendStanza(presence);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        System.out.println("Presence sent ");
        // Start a new conversation with another account holder caled altanaibisht ( I created 2 user accounts one with my first name and another with fullname)


        ChatManager chatManager = ChatManager.getInstanceFor(conn2);
        chatManager.addChatListener(
                new ChatManagerListener() {
                    @Override
                    public void chatCreated(Chat chat, boolean bln) {
                        if (!bln)
                            chat.addMessageListener(new ChatMessageListener() {
                                @Override
                                public void processMessage(Chat chat, Message msg) {
                                    System.out.println("Received message: " + msg);
                                }
                            });
                    }
                });

    }

    public void sendMessage(String message)
    {
        ChatManager chatmanager = ChatManager.getInstanceFor(conn2);
        Chat newChat = chatmanager.createChat("clinton@clinton-server.sytes.net/laptop", new ChatMessageListener() {
            @Override
            public void processMessage(Chat chat, Message msg) {
                System.out.println("Received message: " + msg);
            }
        });


        try {
            newChat.sendMessage(message);
            System.out.println("Message sent ");
        } catch (SmackException.NotConnectedException ex) {
            ex.printStackTrace();
        }
    }
}


