package com.kyle.clientTracker.model;
//done
import com.kami.lookout.Bet;
import javax.net.ssl.*;
import java.io.*;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

/**
 * client object to connect to a server.
 * @author Kyle Gibson
 */
public class Client {
    private SSLSocket sslSocket;
    public ObjectOutputStream out;
    public ObjectInputStream in;
    public Boolean connected = false;

    /**
     * @param host hostname or ip address to connect to
     * @param port port that the server uses.
     */
    public Client(String host, int port) {
        try {
            System.setProperty("javax.net.ssl.trustStore", Config.getClientTrustLocation());
            System.setProperty("javax.net.ssl.trustStorePassword", Config.getClientPassword());

            // Set up the SSL context
            SSLContext sslContext = SSLContext.getInstance("TLS");
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            KeyStore keyStore = KeyStore.getInstance("JKS");


            keyStore.load(null, Config.getClientPassword().toCharArray());
            keyManagerFactory.init(keyStore, Config.getClientPassword().toCharArray());
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            // Create an SSLSocket and connect to the server
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            try {
                sslSocket = (SSLSocket) sslSocketFactory.createSocket(host, port);
                sslSocket.startHandshake();

                out = new ObjectOutputStream(sslSocket.getOutputStream());
                in = new ObjectInputStream(sslSocket.getInputStream());
                connected = true;
            } catch (ConnectException e) {
                System.out.println("Could not connect");
            } catch (SocketException e) {
                System.out.println("Socket exception2");
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        } catch (UnrecoverableKeyException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * gets all bets from the server and returns them as a list
     * @return List of all bets
     */
    public List getAllBets() throws IOException, ClassNotFoundException {
        try {
            if (sslSocket != null && sslSocket.isConnected()) {
                out.writeObject("getBets");
                Object obj = in.readObject();


                if (obj.getClass().equals(ArrayList.class)) {
                    return (List<Bet>) obj;
                } else {
                    return null;
                }
            } else {
                List<Bet> newList = new ArrayList<>();
                return newList;
            }
        } catch (SocketException e) {
            System.out.println("Socket exception");
            List<Bet> newList = new ArrayList<>();
            connected = false;
            return newList;
        }
    }
    /**
     * attempts to delete a bet
     * @return String to show if bet was deleted or not
     */
    public String deleteBet(int betId) throws IOException, ClassNotFoundException {
        try {
            List<Object> sendList = new ArrayList<>();
            sendList.add("deleteBet");
            sendList.add(betId);
            out.writeObject(sendList);
            out.flush();

            Object inObj = in.readObject();
            if (inObj.getClass() == String.class) {
                return (String) inObj;
            } else {
                return null;
            }
        } catch (SocketException e) {
            System.out.println("Could not delete bet: lost connection to server");
            connected = false;
            return "Could not delete bet: lost connection to server";
        }

    }
    /**
     * attempts to add a bet
     * @return int to see if that bet was added successfully
     */
    public int addBet(Bet bet) throws IOException, ClassNotFoundException {
        try {
            List<Object> sendList = new ArrayList<>();
            sendList.add("addBet");
            sendList.add(bet);
            out.writeObject(sendList);
            out.flush();

            Object inObj = in.readObject();
            if (inObj.getClass() == Integer.class) {
                return (int) inObj;
            } else {
                return -4;
            }
        } catch (SocketException e) {
            System.out.println("Socket exception add");
            connected = false;
            return -4;
        }
    }
    /**
     * attempts to udpate a bet
     * @return int to see if that bet was updated successfully
     */
    public int updateBet(Bet bet) throws IOException, ClassNotFoundException {
        try {
            List<Object> sendList = new ArrayList<>();
            sendList.add("updateBet");
            sendList.add(bet);
            out.writeObject(sendList);
            out.flush();

            Object inObj = in.readObject();
            if (inObj.getClass() == Integer.class) {
                return (int) inObj;
            } else {
                return -4;
            }
        } catch (SocketException e) {
            connected = false;
            return -4;
        }
    }
}



