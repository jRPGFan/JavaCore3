package ru.geekbrains.java_two.chat.library;

public class Protocol {
    //common data
    // ±
    //client-to-server
    // /auth_request±login±password
    // /user_broadcast±msg
    // create user, private msg, change nick, ....

    //server-to-client(s)
    // /auth_accept±nickname
    // /auth_denied
    // /broadcast±src±msg
    // /msg_format_error
    // /user_list±u1±u2±u3±....

    public static final String DELIMITER = "±"; //alt+num0177
    public static final String AUTH_REQUEST = "/auth_request";
    public static final String AUTH_ACCEPT = "/auth_accept";
    public static final String AUTH_DENIED = "/auth_denied";
    public static final String MSG_FORMAT_ERROR = "/msg_format_error"; // если мы вдруг не поняли, что за сообщение и не смогли разобрать
    public static final String TYPE_BROADCAST = "/bcast"; // то есть сообщение, которое будет посылаться всем
    public static final String USER_LIST = "/user_list";
    public static final String USER_BROADCAST = "/user_bcast";
    public static final String USER_NICKNAME_CHANGE = "/user_nickname_change";
    public static final String USER_NICKNAME_ALREADY_IN_USE = "/nickname_in_use";

    public static String getUserBroadcast(String msg) {
        return USER_BROADCAST + DELIMITER + msg;
    }

    public static String getUserList(String users) {
        //u1±u2±u3±...
        return USER_LIST + DELIMITER + users;
    }

    public static String getAuthRequest(String login, String password) {
        return AUTH_REQUEST + DELIMITER + login + DELIMITER + password;
    }

    public static String getAuthAccept(String nickname) {
        return AUTH_ACCEPT + DELIMITER + nickname;
    }

    public static String getAuthDenied() {
        return AUTH_DENIED;
    }

    public static String getMsgFormatError(String message) {
        return MSG_FORMAT_ERROR + DELIMITER + message;
    }

    public static String getTypeBroadcast(String src, String message) {
        return TYPE_BROADCAST + DELIMITER + System.currentTimeMillis() +
                DELIMITER + src + DELIMITER + message;
    }

    public static String getUserNicknameChange(String nickname){
        return USER_NICKNAME_CHANGE + DELIMITER + nickname;
    }

    public static String nickNameAlreadyInUse(String msg){
        return USER_NICKNAME_ALREADY_IN_USE + DELIMITER + msg;
    }
}
