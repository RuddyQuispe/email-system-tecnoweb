package edu.bo.uagrm.ficct.inf513.services;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 2021-11-20 06:57
 */

import edu.bo.uagrm.ficct.inf513.utils.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class POPService implements Runnable {

    private Socket socket;
    private BufferedReader input;
    private DataOutputStream output;

    public POPService() {
        this.socket = null;
        this.input = null;
        this.output = null;
    }

    public boolean connectService() {
        try {
            Dotenv dotenv = Dotenv.configure()
                    // address file .env
                    .directory(Address.addressFileENV).load();
            this.socket = new Socket(dotenv.get("POP_HOST"), Integer.parseInt(Objects.requireNonNull(dotenv.get("POP_PORT"))));
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
                Dotenv dotenv = Dotenv.configure()
                        // address file .env
                        .directory(Address.addressFileENV).load();
                String messageResponseMessage = this.input.readLine();
                System.out.println("SERVER: " + messageResponseMessage);
                String command = Command.user(dotenv.get("POP_USER"));
                this.output.writeBytes(command);
                System.out.println("CLIENT: " + command);
                messageResponseMessage = this.input.readLine();
                System.out.println("SERVER: " + messageResponseMessage);
                command = Command.pass(dotenv.get("POP_PASSWD"));
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
                    int count = this.getEmailCount();
                    // get count emails saved
                    if (count > 0) {
                        emails = this.getEmails(count);
                        // start thread read email
                        for (Email emailToMake : emails) {
                            Analyzer analyzerEmail = new Analyzer(emailToMake.getSubject());
                            System.out.println(analyzerEmail.toString());
                            // aqui
                            if (emailToMake.getFrom() != "" && emailToMake.getTo() != "") {
                                sendEmailResponse(emailToMake);
                            }
                        }
                        System.out.println(emails.toString());
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

    public static void sendEmailResponse(Email emailToSend) {
        String[] data = {"#", "First", "Last", "Handle", "Actions"};
        List<String[]> listData = Arrays.asList(
                new String[]{"1", "Otto", "Otto", "@mdo", "<a href=\"mailto:ruddy_quispe@tecnologia-web.me?Subject=LISTAR APORTES\" style=\"background-color: yellow; border-radius: 8px;\">LISTAR</a>"},
                new String[]{"2", "Jacob", "Jacob", "@fat", "<a href=\"mailto:ruddy_quispe@tecnologia-web.me?Subject=LISTAR APORTES\" style=\"background-color: blue; border-radius: 8px;\">LISTAR</a>"},
                new String[]{"3", "Carol", "Matheus", "@twiter", "<a href=\"mailto:ruddy_quispe@tecnologia-web.me?Subject=LISTAR APORTES\" style=\"background-color: green; border-radius: 8px;\">LISTAR</a>"},
                new String[]{"4", "Marie", "Carla", "@facebook", "<a href=\"mailto:ruddy_quispe@tecnologia-web.me?Subject=LISTAR APORTES\" style=\"background-color: yellow; border-radius: 8px;\">LISTAR</a>"},
                new String[]{"5", "Curie", "Dario", "@instagram", "<a href=\"mailto:ruddy_quispe@tecnologia-web.me?Subject=LISTAR APORTES\" style=\"background-color: black; border-radius: 8px;\">LISTAR</a>"}
        );
        String html = HTMLBuilder.generateTable("Hola Cabezones", data, listData);
        emailToSend.setMessage(html);
        SMTPService smtpService = new SMTPService(emailToSend);
        Thread smtpThread = new Thread(smtpService);
        smtpThread.run();
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
