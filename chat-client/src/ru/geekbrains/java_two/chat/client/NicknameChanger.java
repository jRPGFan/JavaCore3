package ru.geekbrains.java_two.chat.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NicknameChanger extends JFrame implements ActionListener{
    private static final int WINDOW_WIDTH = 350;
    private static final int WINDOW_HEIGHT = 100;
    private static final String WINDOW_TITLE = "Nickname changer";
    private ClientGUI clientGUI;

    private final JPanel panelTop = new JPanel(new GridLayout(1, 1));
    private final JPanel panelBottom = new JPanel(new GridLayout(1, 2));
    private final JTextField tfNickname = new JTextField();
    private final JButton btnChangeNickname = new JButton("Change nickname");

    NicknameChanger (ClientGUI clientGUI){
        this.clientGUI = clientGUI;
        setLocationRelativeTo(clientGUI);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setTitle(WINDOW_TITLE);
        btnChangeNickname.addActionListener(this);

        panelTop.add(new JLabel("Change nickname to:"));
        panelBottom.add(tfNickname);
        panelBottom.add(btnChangeNickname);
        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);

        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnChangeNickname){
            clientGUI.changeNickname(tfNickname.getText());
            tfNickname.setText("");
            setVisible(false);
        } else {
            throw new RuntimeException("Undefined source: " + src);
        }
    }
}
