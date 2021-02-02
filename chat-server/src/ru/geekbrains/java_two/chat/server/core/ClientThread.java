package ru.geekbrains.java_two.chat.server.core;

import ru.geekbrains.java_two.chat.library.Protocol;
import ru.geekbrains.java_two.network.SocketThread;
import ru.geekbrains.java_two.network.SocketThreadListener;

import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class ClientThread extends SocketThread {

    private String nickname;
    private boolean isAuthorized;
    private boolean isReconnecting;
    private ExecutorService executorService;

    public ClientThread(SocketThreadListener listener, String name, Socket socket, ExecutorService executorService) {
    //public ClientThread(SocketThreadListener listener, String name, Socket socket) {
        super(listener, name, socket);
        executorService.submit(this);
//        this.executorService = executorService;
    }

    public String getNickname() {
        return nickname;
    }

    public void changeNickname(String nickname){
        this.nickname = nickname;
        sendMessage(Protocol.getUserNicknameChange(nickname));
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void reconnect() {
        isReconnecting = true;
        close();
    }

    void authAccept(String nickname) {
//        executorService.submit(this);
        isAuthorized = true;
        this.nickname = nickname;
        sendMessage(Protocol.getAuthAccept(nickname));
    }

    void authFail() {
        sendMessage(Protocol.getAuthDenied());
        close();
    }

    void msgFormatError(String msg) {
        sendMessage(Protocol.getMsgFormatError(msg));
        close();
    }

    void nickNameAlreadyInUse(String msg){
        sendMessage(Protocol.nickNameAlreadyInUse(msg));
    }

    public boolean isReconnecting() {
        return isReconnecting;
    }
}
