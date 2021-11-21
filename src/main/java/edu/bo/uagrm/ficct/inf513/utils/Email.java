package edu.bo.uagrm.ficct.inf513.utils;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 15/9/21 11:40
 */
public class Email {
    public static final String SUBJECT = "Request respose";
    private String from;
    private String to;
    private String subject;
    private String message;

    public Email(String from, String to, String subject, String message) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    public Email(String to, String subject, String message) {
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    public Email(String from, String subject) {
        this.from = from;
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "[\n" +
                "\tFrom: " + from + ",\n" +
                "\tTo: " + to + ",\n" +
                "\tSubject: " + subject + ",\n" +
                "\tMessage: " + message + "\n" +
                "]";
    }
}