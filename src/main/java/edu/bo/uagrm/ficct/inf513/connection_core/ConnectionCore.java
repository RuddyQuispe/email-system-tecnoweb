package edu.bo.uagrm.ficct.inf513.connection_core;

import edu.bo.uagrm.ficct.inf513.communication.MailVerificationThread;
import edu.bo.uagrm.ficct.inf513.interfaces.InterfaceEmailEventListener;
import edu.bo.uagrm.ficct.inf513.utils.Email;

import java.util.List;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 2021-11-20 08:24
 */
public class ConnectionCore {

    public static void main(String[] args) {
        MailVerificationThread mailVerificationThread = new MailVerificationThread();
        mailVerificationThread.setEmailEventListener(new InterfaceEmailEventListener() {
            @Override
            public void onReceiveEmailEvent(List<Email> emailList) {
                for (Email email : emailList) {
                    System.out.println(email.toString());
                }
            }
        });
        Thread thread = new Thread(mailVerificationThread);
        thread.setName("Mail Verification Thread");
        thread.start();
    }
}
