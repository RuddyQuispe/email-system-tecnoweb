package edu.bo.uagrm.ficct.inf513.services;

import edu.bo.uagrm.ficct.inf513.utils.Address;
import edu.bo.uagrm.ficct.inf513.utils.Command;
import edu.bo.uagrm.ficct.inf513.utils.Email;
import io.github.cdimascio.dotenv.Dotenv;

import javax.security.sasl.AuthenticationException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 15/9/21 10:23
 */
public class POPService implements Runnable {
    private Socket socket;
    private BufferedReader input;
    private DataOutputStream output;
    private static POPService service;

    public POPService() {
        try {
            // get info to .env file
            Dotenv dotenv = Dotenv.configure()
                    // address file .env
                    .directory(Address.addressFileENV)
                    .load();
            // open service socket
            this.socket = new Socket(dotenv.get("POP_HOST"), Integer.parseInt(Objects.requireNonNull(dotenv.get("POP_PORT"))));
            this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.output = new DataOutputStream(this.socket.getOutputStream());
            if (this.socket.isConnected()) {
                System.out.println("POP service established!!!!");
            } else {
                System.out.println("Error to connect POP service");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static POPService getInstance() {
        if (service == null) {
            service = new POPService();
        }
        return service;
    }

    private void signIn() throws IOException {
        if (this.socket != null && this.input != null && this.output != null) {
            // get info to .env file
            Dotenv dotenv = Dotenv.configure()
                    // address file .env
                    .directory(Address.addressFileENV)
                    .load();
            this.input.readLine();
            this.output.writeBytes(Command.user(dotenv.get("POP_USER")));
            this.input.readLine();
            this.output.writeBytes(Command.pass(dotenv.get("POP_PASSWD")));
            String message = this.input.readLine();
            if (message.contains("-ERR")) {
                throw new AuthenticationException();
            }
        }
    }

    private void deleteEmails(int emails) throws IOException {
        for (int index = 1; index <= emails; index++) {
            this.output.writeBytes(Command.dele(index));
        }
    }

    private int getEmailCount() throws IOException {
        output.writeBytes(Command.stat());
        String line = input.readLine();
        String[] data = line.split(" ");
        return Integer.parseInt(data[1]);
    }

    private List<Email> getEmails(int count) throws IOException {
        List<Email> emails = new ArrayList<>();
        for (int index = 1; index <= count; index++) {
            output.writeBytes(Command.retr(index));
            String text = readMultiline();
            //emails.add(Extractor.getEmail(text));
        }
        return emails;
    }

    private String readMultiline() throws IOException {
        String lines = "";
        while (true) {
            String line = input.readLine();
            if (line == null) {
                throw new IOException("Server no responde (ocurrio un error al abrir el correo)");
            }
            if (line.equals(".")) {
                break;
            }
            lines = lines + "\n" + line;
        }
        return lines;
    }

    @Override
    public void run() {

    }
}
