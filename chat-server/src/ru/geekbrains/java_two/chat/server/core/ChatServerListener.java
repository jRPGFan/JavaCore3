package ru.geekbrains.java_two.chat.server.core;

public interface ChatServerListener {
    void onChatServerMessage(String msg);
}
/*
1) Класс ClientThread отвечает за авторизацию участников чата?
        2) Класс SqlClient. Получается, что это, грубо говоря, просто база данных участников чата? Методы данного класса описывают поведение участников?
        3) Не совсем понял для чего нужен класс Protocol?

        1. на мой взгляд, запрос авторизации от пользователя должен быть в методе onSocketReady(). иначе, когда внутри запроса авторизации мы обращаемся к стриму out (SocketThread, 42 строка), вываливается нулл пойнтер, ибо run() SocketThread'a еще не успел добежать до 25 строки и создать этот out. обратил на это внимание, поскольку у меня поначалу чат вообще не хотел логиниться, и выдавал это:
        java.lang.NullPointerException: Cannot invoke "java.io.DataOutputStream.writeUTF(String)" because "this.out" is null
        at ru.geekbrains.java_two.network.SocketThread.sendMessage(SocketThread.java:42)
        at ru.geekbrains.java_two.chat.client.ClientGUI.connect(ClientGUI.java:112)
        at ru.geekbrains.java_two.chat.client.ClientGUI.actionPerformed(ClientGUI.java:99)
        (и да, у меня стоит sdk java 15, так исторически сложилось.)
        2. в методах onRecieveString() и onSocketReady() имеется параметр сокет, который мы, тем не менее, ни разу не использовали. это задел на будущее или мы еще не успели до него добраться?

        1) У нас происходит соединение с БД в момент запуска сервера и закрытие соединения по кнопке «Стоп». Если мы выходим из программы другим способом, то соединение штатно не закрывается. Как добавить обработчик события закрытия программы любым методом: нажатием на крестик, выключение ПК и др.?
        2) Класс [ClientGUI] метод connect(). На моем компьютере после создания нового потока SoketThread сообщение серверу о готовности методом socketThread.sendMessage(Protocol.getAuthRequest(…)) выводилось прежде, чем поток был готов. Тогда out еще был равен null. Я перенес вывод этого сообщения в метод onSocketReady() того же класса и все заработало.
        3) Для чего мы делаем так:
private void putLog(String msg) {
        if ("".equals(msg)) return;
        SwingUtilities.invokeLater(new Runnable() {
@Override
public void run() {
        log.append(msg + "\n");
        log.setCaretPosition(log.getDocument().getLength());
        }
        });

// log.append(msg + "\n"); // так вроде работает?
// log.setCaretPosition(log.getDocument().getLength());
        }
        Мы выводим каждое сообщение в отдельный поток?

        Как определить что данный метод нуждается в try-catch и как выбрать варианта Exeption из всего многообразия?

Не могли бы Вы еще раз рассказать про деятельность метода run() в SocketThread и в ServerSocketThread?
Закрываются ли у нас in и out? Если да, то где? Если нет, то почему?
Чем занимается onSocketReady?
Если коллеги одобрят и Вам не составит труда, не могли бы вы описать по шагам "становление" клиента собственно клиентом. Порой сложновато уследить, например, что клиент вот только создался - он в этой точке - программа так реагирует. Он подключился, он авторизовался... Короче говоря, у клиента немало состояний, и я в них запутался(((

*/