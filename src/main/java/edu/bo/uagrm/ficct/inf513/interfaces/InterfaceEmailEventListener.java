package edu.bo.uagrm.ficct.inf513.interfaces;

import edu.bo.uagrm.ficct.inf513.utils.Email;

import java.util.List;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 2021-11-20 06:50
 */
public interface InterfaceEmailEventListener {

    void onReceiveEmailEvent(List<Email> emailList);
    
}
