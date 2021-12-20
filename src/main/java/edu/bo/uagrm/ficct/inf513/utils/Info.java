package edu.bo.uagrm.ficct.inf513.utils;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 15/9/21 11:34
 * object Info class for initialize .env file data (SINGLETON PATTERN)
 */
public class Info {
    //    public static final String addressFileENV = "/home/cruz/Escritorio/Semestre 10/Tecnología Web/projects/first project/email-system-tecnoweb/src/main/resources/.env";
    public static final String addressFileENV = "/home/ruddy/IdeaProjects/email-system-tecnoweb/src/main/resources/.env";
    public Dotenv environmentVariables;
    public static Info info;

    public Info() {
        this.environmentVariables = Dotenv.configure()
                // address file .env
                .directory(this.addressFileENV).load();
    }

    public static Info getInstance() {
        if (info == null) {
            System.out.println("INFO ENVIRONMENT VARIABLES INITIALIZED");
            info = new Info();
        }
        return info;
    }
}
