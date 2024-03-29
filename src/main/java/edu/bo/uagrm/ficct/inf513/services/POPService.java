package edu.bo.uagrm.ficct.inf513.services;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 2021-11-20 06:57
 */

import edu.bo.uagrm.ficct.inf513.utils.*;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class POPService implements Runnable {

    private Socket socket;
    private BufferedReader input;
    private DataOutputStream output;
    private static Info info = Info.getInstance();
    private Analyzer analyzer;
    private Core coreProcess;

    public POPService() {
        this.socket = null;
        this.input = null;
        this.output = null;
    }

    public boolean connectService() {
        try {
            this.socket = new Socket(info.environmentVariables.get("POP_HOST"), Integer.parseInt(Objects.requireNonNull(info.environmentVariables.get("POP_PORT"))));
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.output = new DataOutputStream(socket.getOutputStream());
            System.out.println("CONNECTION ESTABLISHED SUCCESSFULLY");
            return this.socket != null && this.input != null && this.output != null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean authUser() {
        if (this.socket != null && this.input != null && this.output != null) {
            try {
                String messageResponseMessage = this.input.readLine();
                System.out.println("SERVER: " + messageResponseMessage);
                String command = Command.user(info.environmentVariables.get("POP_USER"));
                this.output.writeBytes(command);
                System.out.println("CLIENT: " + command);
                messageResponseMessage = this.input.readLine();
                System.out.println("SERVER: " + messageResponseMessage);
                command = Command.pass(info.environmentVariables.get("POP_PASSWD"));
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
                    this.authUser();
                    int countEmails = this.getEmailCount();
                    // get count emails saved
                    if (countEmails > 0) {
                        emails = this.getEmails(countEmails);
                        // start thread read email
                        for (Email emailToMake : emails) {
                            if (emailToMake.getFrom() != "" && emailToMake.getTo() != "") {
                                this.sendEmailResponse(emailToMake);
                            }
                        }
                        System.out.println(emails.toString());
                        this.removeEmail(countEmails);
                    }
                    // close connection socket
                    this.closeService();
                    Thread.sleep(10000);
                } else {
                    System.out.println("we could not connect to the server");
                    Thread.sleep(2000);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(POPService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void sendEmailResponse(Email emailToSend) {
        try {
            System.out.println("Initialize: " + emailToSend.getSubject());
            // get action, use case and test token
            this.analyzer = new Analyzer(emailToSend.getSubject().trim());
            if (analyzer.hasError()) {
                emailToSend.setMessage(HTMLBuilder.buildMessageError(
                        "Hubo error en identificar el Token </br>" +
                                "Porfavor envienos un email con subject:\"HELP\" para ayuda con la funcionalidad del sistema"));
                // send email error, error into token
            } else {
                this.coreProcess = new Core(this.analyzer.getUseCase(), this.analyzer.getAction(), this.analyzer.getParameters());
                String htmlResponse = this.coreProcess.processApplication();
                emailToSend.setMessage(htmlResponse);
            }
            SMTPService smtpService = new SMTPService(emailToSend);
            Thread smtpThread = new Thread(smtpService);
            smtpThread.start();
        } catch (Exception exception) {
            System.out.println("ERROR into sendEmailResponse Application: " + exception + "...\nEnviando email de error");
            emailToSend.setMessage(HTMLBuilder.buildMessageError("ERROR en analizar el subject de tu email"));
            SMTPService smtpService = new SMTPService(emailToSend);
            Thread smtpThread = new Thread(smtpService);
            smtpThread.start();
        }
    }

    private int getEmailCount() {
        try {
            String command = Command.stat();
            this.output.writeBytes(command);
            System.out.println("CLIENT: " + command);
            String line = this.input.readLine();
            System.out.println("SERVER: " + line);
            String[] data = line.split(" ");
            return Integer.parseInt(data[1]);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
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
