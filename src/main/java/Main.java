import edu.bo.uagrm.ficct.inf513.services.POPService;
import edu.bo.uagrm.ficct.inf513.utils.Info;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 4/9/21 21:09
 */

public class Main {
    public static void main(String[] args) {
        /**
         * si quieres habiltar tu IP en el servidor tecnologia-web.me para enviar emails
         * COMMAND: cat ~/lista-comandos-habilitar-ip.txt
         * ejecuta esa lista de comandos deese archivo
         */
        POPService popService = new POPService();
        Thread thread = new Thread(popService);
        thread.setName("Mail System Associate Merchants Thread");
        System.out.println("Initialize: " + thread.getName());
        thread.start();
    }
}
