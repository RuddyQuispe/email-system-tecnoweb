package edu.bo.uagrm.ficct.inf513.communication;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 2021-11-20 06:57
 */

import edu.bo.uagrm.ficct.inf513.interfaces.InterfaceEmailEventListener;
import edu.bo.uagrm.ficct.inf513.utils.Command;
import edu.bo.uagrm.ficct.inf513.utils.Email;
import edu.bo.uagrm.ficct.inf513.utils.Extractor;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailVerificationThread implements Runnable {

    private Socket socket;
    private BufferedReader input;
    private DataOutputStream output;
    private InterfaceEmailEventListener emailEventListener;
    private String host, userEmail, password;
    private int port;

    public MailVerificationThread(String host, int port, String userEmail, String password) {
        this.socket = null;
        this.input = null;
        this.output = null;
        this.host = host;
        this.port = port;
        this.userEmail = userEmail;
        this.password = password;
    }

    public InterfaceEmailEventListener getEmailEventListener() {
        return emailEventListener;
    }

    public void setEmailEventListener(InterfaceEmailEventListener emailEventListener) {
        this.emailEventListener = emailEventListener;
    }

    public boolean connectService() {
        try {
            this.socket = new Socket(this.host, this.port);
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.output = new DataOutputStream(socket.getOutputStream());
            System.out.println("CONNECTION ESTABLISHED SUCCESSFULLY");
            return this.socket != null && this.input != null && this.output != null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean authUser(String email, String password) {
        if (this.socket != null && this.input != null && this.output != null) {
            try {
                String messageResponseMessage = this.input.readLine();
                System.out.println("SERVER: " + messageResponseMessage);
                String command = Command.user(email);
                this.output.writeBytes(command);
                System.out.println("CLIENT: " + command);
                messageResponseMessage = this.input.readLine();
                System.out.println("SERVER: " + messageResponseMessage);
                command = Command.pass(password);
                this.output.writeBytes(command);
                System.out.println("CLIENT: " + command);
                messageResponseMessage = this.input.readLine();
                System.out.println("SERVER: " + messageResponseMessage);
                if (Command.err(messageResponseMessage)) {
                    System.out.println("ERROR Authentication!!");
                    return false;
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    @NotNull
    private List<Email> getEmails(int count) {
        List<Email> emailList = new ArrayList<Email>();
        for (int index = 1; index <= count; index++) {
            try {
                String command = Command.retr(index);
                this.output.writeBytes(command);
                System.out.println("CLIENT: " + command);
                String response = this.readMultiline();
                // System.out.println("MESSAGE: \n" + response);
                emailList.add(Extractor.getEmail(response));
            } catch (IOException e) {
                e.printStackTrace();
                return emailList;
            }
        }
        return emailList;
    }

    private void removeEmail(int emails) {
        for (int index = 1; index <= emails; index++) {
            try {
                String command = Command.dele(index);
                this.output.writeBytes(command);
                System.out.println("CLIENT: " + command);
                String responseInput = this.input.readLine();
                System.out.println("SERVER: " + responseInput);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean closeService() {
        try {
            String command = Command.quit();
            this.output.writeBytes(command);
            System.out.println("CLIENT: " + command);
            String responseMessage = input.readLine();
            System.out.println("SERVER: " + responseMessage);
            this.input.close();
            this.output.close();
            this.socket.close();
            System.out.println("CONNECTION CLOSED SUCCESS");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (this.connectService()) {
                    List<Email> emails = null;
                    this.authUser(this.userEmail, this.password);
                    String command = Command.stat();
                    this.output.writeBytes(command);
                    System.out.println("CLIENT: " + command);
                    String responseInput = this.input.readLine();
                    System.out.println("SERVER: " + responseInput);
                    int count = this.getEmailCount(responseInput);
                    // get count emails saved
                    if (count > 0) {
                        emails = this.getEmails(count);
                        // System.out.println(emails.toString());
                        //removeEmail(count);
                        this.emailEventListener.onReceiveEmailEvent(emails);
                    }
                    // close connection socket
                    this.closeService();
                    Thread.sleep(10000);
                } else {
                    System.out.println("we could not connect to the server");
                    Thread.sleep(2000);
                }
            } catch (IOException ex) {
                Logger.getLogger(MailVerificationThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(MailVerificationThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private int getEmailCount(String line) {
        String[] data = line.split(" ");
        return Integer.parseInt(data[1]);
    }

    private String readMultiline() throws IOException {
        String lines = "";
        while (true) {
            String line = this.input.readLine();
            if (line == null) {
                throw new IOException("Server don't response (throw an error to read email)");
            }
            if (line.equals(".")) {
                break;
            }
            lines = lines + "\n" + line;
        }
        return lines;
    }
}
