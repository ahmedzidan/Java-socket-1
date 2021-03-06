/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket1;

import com.sun.prism.impl.PrismSettings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author ahmedzidan
 */
public class Server {

    private static final int PORT = 9999;
    
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    
    private static OutputData outputData = new OutputData();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        ServerSocket listener = new ServerSocket(PORT);
        outputData.setFinalPrice(1000);
        outputData.setNumSuccessPids(0);
        outputData.setNumSuccessDealers(0);
        int numDealers = 0;
            try {
                while (true) {
                    Socket clientSocket = listener.accept();
                    Scanner input = new Scanner(clientSocket.getInputStream());
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    ClientHandler clientThread = new ClientHandler(clientSocket, out, input, outputData);
                    pool.execute(clientThread);
                   // numDealers++;
                }
            } catch (IOException e) {
               listener.close();
               e.getStackTrace();
            } finally  {
                listener.close();
                
            }    
        
    }
    
    public static void showOutputData()
    {
        System.out.println("Final Price = " + outputData.getFinalPrice());
        System.out.println("count of accepted bids = " + outputData.getNumSuccessPids());
        System.out.println("Count of dealers = "+ outputData.getNumSuccessDealers());
    }
}
