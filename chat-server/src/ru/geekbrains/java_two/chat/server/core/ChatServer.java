package ru.geekbrains.java_two.chat.server.core;

import ru.geekbrains.java_two.chat.library.Protocol;
import ru.geekbrains.java_two.network.ServerSocketThread;
import ru.geekbrains.java_two.network.ServerSocketThreadListener;
import ru.geekbrains.java_two.network.SocketThread;
import ru.geekbrains.java_two.network.SocketThreadListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {
    private ServerSocketThread server;
    private final DateFormat DATE_FORMAT = new SimpleDateFormat("[HH:mm:ss] ");
    private ChatServerListener listener;
    private Vector<SocketThread> clients;
    private ExecutorService executorService;

    public ChatServer(ChatServerListener listener) {
        this.listener = listener;
        clients = new Vector<>();
    }

    public void start(int port) {
        if (server != null && server.isAlive()) {
            putLog("Server already started");
        } else {
            server = new ServerSocketThread(this, "Server", port, 2000);
        }
    }

    public void stop() {
        if (server == null || !server.isAlive()) {
            putLog("Server is not running");
        } else {
            executorService.shutdown();
            server.interrupt();
        }
    }

    private void putLog(String msg) {
        msg = DATE_FORMAT.format(System.currentTimeMillis()) + Thread.currentThread().getName() + ": " + msg;
        listener.onChatServerMessage(msg);
    }

    /**
     * Server socket thread listener methods implementation
     * */
    @Override
    public void onServerStart(ServerSocketThread thread) {
        putLog("Server socket thread started");
        SqlClient.connect();
        executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void onServerStop(ServerSocketThread thread) {
        putLog("Server socket thread stopped");
        SqlClient.disconnect();
        for (int i = 0; i < clients.size(); i++) {
            clients.get(i).close();
        }
        executorService.shutdown();
        putLog("Executor service shut down");
    }

    @Override
    public void onServerSocketCreated(ServerSocketThread thread, ServerSocket server) {
        putLog("Server socket created");
    }

    @Override
    public void onServerTimeout(ServerSocketThread thread, ServerSocket server) {
//        putLog("Server socket thread accept timeout");
    }

    @Override
    public void onSocketAccepted(ServerSocketThread thread, ServerSocket server, Socket socket) {
        putLog("Client connected");
        String name = "SocketThread" + socket.getInetAddress() + ":" + socket.getPort();

        new ClientThread(this, name, socket, executorService);
//        new ClientThread(this, name, socket);
    }

    @Override
    public void onServerException(ServerSocketThread thread, Throwable exception) {
        exception.printStackTrace();
    }

    /**
     * Socket thread listener methods implementation
     * */

    @Override
    public synchronized void onSocketStart(SocketThread thread, Socket socket) {
        putLog("Socket created");
    }

    @Override
    public synchronized void onSocketStop(SocketThread thread) {
        ClientThread client = (ClientThread) thread;
        clients.remove(thread);
        if (client.isAuthorized() && !client.isReconnecting()) {
            sendToAllAuthorizedClients(Protocol.getTypeBroadcast(
                    "Server", client.getNickname() + " disconnected"));
        }
        sendToAllAuthorizedClients(Protocol.getUserList(getUsers()));
    }

    @Override
    public synchronized void onSocketReady(SocketThread thread, Socket socket) {
        clients.add(thread);
    }

    @Override
    public synchronized void onReceiveString(SocketThread thread, Socket socket, String msg) {
        ClientThread client = (ClientThread) thread;
        if (client.isAuthorized()) {
            handleAuthClientMessage(client, msg);
        } else {
            handleNonAuthClientMessage(client, msg);
        }
    }

    private void handleAuthClientMessage(ClientThread client, String msg) {
        String[] arr = msg.split(Protocol.DELIMITER);
        String msgType = arr[0];
        switch (msgType) {
            case Protocol.USER_BROADCAST:
                sendToAllAuthorizedClients(Protocol.getTypeBroadcast(client.getNickname(), arr[1]));
                break;
            case Protocol.USER_NICKNAME_CHANGE:
                String currentNickname = client.getNickname();
                int result = SqlClient.changeNickname(currentNickname, arr[1]);

                if (result == 1) {
                    client.changeNickname(arr[1]);
                    sendToAllAuthorizedClients(Protocol.getTypeBroadcast("Server", currentNickname +
                            " is now known as " + arr[1]));
                    sendToAllAuthorizedClients(Protocol.getUserList(getUsers()));
                } else if (result == -1){
                        client.nickNameAlreadyInUse("nickname " + arr[1] + " is already in use");
                }

                break;
            default:
                client.msgFormatError(msg);
        }
    }

    private void handleNonAuthClientMessage(ClientThread client, String msg) {
        String[] arr = msg.split(Protocol.DELIMITER);
        if (arr.length != 3 || !arr[0].equals(Protocol.AUTH_REQUEST)) {
            client.msgFormatError(msg);
            return;
        }
        String login = arr[1];
        String password = arr[2];
        String nickname = SqlClient.getNickname(login, password);
        if (nickname == null) {
            putLog("Invalid credentials attempt for login = " + login);
            client.authFail();
            return;
        } else {
            ClientThread oldClient = findClientByNickname(nickname);
            client.authAccept(nickname);
            if (oldClient == null) {
                sendToAllAuthorizedClients(Protocol.getTypeBroadcast("Server", nickname + " connected"));
            } else {
                oldClient.reconnect();
                clients.remove(oldClient);
            }
            //executorService.submit(client);
        }
        sendToAllAuthorizedClients(Protocol.getUserList(getUsers()));
    }

    private void sendToAllAuthorizedClients(String msg) {
        for (int i = 0; i < clients.size(); i++) {
            ClientThread recipient = (ClientThread) clients.get(i);
            if (!recipient.isAuthorized()) continue;
            recipient.sendMessage(msg);
        }
    }

    @Override
    public synchronized void onSocketException(SocketThread thread, Exception exception) {
        exception.printStackTrace();
    }

    private String getUsers() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < clients.size(); i++) {
            ClientThread client = (ClientThread) clients.get(i);
            if (!client.isAuthorized()) continue;
            sb.append(client.getNickname()).append(Protocol.DELIMITER);
        }
        return sb.toString();
    }


    private synchronized ClientThread findClientByNickname(String nickname) {
        for (int i = 0; i < clients.size(); i++) {
            ClientThread client = (ClientThread) clients.get(i);
            if (!client.isAuthorized()) continue;
            if (client.getNickname().equals(nickname))
                return client;
        }
        return null;
    }
}
