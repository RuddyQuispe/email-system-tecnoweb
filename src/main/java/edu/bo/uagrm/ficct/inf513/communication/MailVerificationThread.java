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

import javax.security.sasl.AuthenticationException;
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

    public MailVerificationThread() {
        this.socket = null;
        this.input = null;
        this.output = null;
    }

    public InterfaceEmailEventListener getEmailEventListener() {
        return emailEventListener;
    }

    public void setEmailEventListener(InterfaceEmailEventListener emailEventListener) {
        this.emailEventListener = emailEventListener;
    }

    @Override
    public void run() {
        while (true){
            try {
                List<Email> emails = null;
                socket = new Socket("SERVER", 110);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new DataOutputStream(socket.getOutputStream());
                System.out.println("**************** Conexion establecida *************");

                authUser("EMAIL_NAME_ONLY", "PASSW");
                this.output.writeBytes(Command.stat());
                String responseInput = input.readLine();
                int count = getEmailCount(responseInput);
                if(count > 0 ) {
                    emails = getEmails(count);
                    System.out.println(emails);
                    removeEmail(count);
                }
                output.writeBytes(Command.quit());
                input.readLine();
                input.close();
                output.close();
                socket.close();
                System.out.println("************** Conexion cerrada ************");

                if(count > 0) {
                    emailEventListener.onReceiveEmailEvent(emails);
                }

                Thread.sleep(10000);

            } catch (IOException ex) {
                Logger.getLogger(MailVerificationThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(MailVerificationThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void authUser(String email, String password) {
        if (this.socket != null && this.input != null && this.output != null) {
            try {
                String messageResponseMessage = this.input.readLine();
                System.out.println("SERVER: " + messageResponseMessage);
                String command = Command.user(email);
                this.output.writeBytes(command);
                messageResponseMessage = this.input.readLine();
                System.out.println("SERVER: " + messageResponseMessage);
                command = Command.pass(password);
                this.output.writeBytes(command);
                messageResponseMessage = this.input.readLine();
                System.out.println("SERVER: " + messageResponseMessage);
                if (Command.err(messageResponseMessage)) {
                    throw new AuthenticationException();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int getEmailCount(String line) {
        String[] data = line.split(" ");
        return Integer.parseInt(data[1]);
    }

    private List<Email> getEmails(int count) {
        List<Email> emailList = new ArrayList<Email>();
        for (int index = 1; index <= count; index++) {
            try {
                System.out.println("CLIENT: " + Command.retr(index));
                output.writeBytes(Command.retr(index));
                String response = readMultiline();
                emailList.add(Extractor.getEmail(response));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return emailList;
    }

    private String readMultiline() throws IOException {
        String lines = "";
        while (true) {
            String line = input.readLine();
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

    private void removeEmail(int emails){
        for (int index = 1; index <= emails; index++) {
            try {
                String command = Command.dele(index);
                this.output.writeBytes(command);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
