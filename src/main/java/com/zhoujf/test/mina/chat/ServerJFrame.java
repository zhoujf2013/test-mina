package com.zhoujf.test.mina.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class ServerJFrame extends JFrame {

    public static Logger logger = LoggerFactory.getLogger(ServerJFrame.class);

    private WindowListener wListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            int response = JOptionPane.showConfirmDialog(null, "确认退出?", "信息提示", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if(response == JOptionPane.OK_OPTION) {
                System.exit(0);
            } else {
                setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }
        }
    };

    public ServerJFrame(){
        JDialog.setDefaultLookAndFeelDecorated(true);
        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }*/
        setTitle("服务器状态");
        setSize(400 , 300);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener(wListener);

        _initMenu();
        _initLayout();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void _initMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu mnuSys = new JMenu("系统(S)");
        mnuSys.setMnemonic('S');
        JMenu mnuAction = new JMenu("操作");
        JMenuItem itemStart = mnuAction.add("启动");
        JMenuItem itemStop = mnuAction.add("停止");

        JMenuItem itemExit = mnuSys.add("退出(X)");

        itemStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuBar.add(mnuSys);
        menuBar.add(mnuAction);
        setJMenuBar(menuBar);
    }

    public void _initLayout(){
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JPanel pTop = new JPanel(new BorderLayout());
        JTextField txtPort = new JTextField("8888");
        JButton btnStart = new JButton("启动");
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton)e.getSource();
                if(btn.getText().equals("启动")){
                    btn.setText("停止");
                } else {
                    btn.setText("启动");
                }
            }
        });

        JTextArea jtaLog = new JTextArea();
        jtaLog.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(jtaLog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel lblPort = new JLabel("端口号:");
        lblPort.setLabelFor(txtPort);

        pTop.add(lblPort, BorderLayout.WEST);
        pTop.add(txtPort, BorderLayout.CENTER);
        pTop.add(btnStart, BorderLayout.EAST);

        contentPane.setLayout(new BorderLayout(10, 10));
        contentPane.add(pTop, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ServerJFrame();
            }
        });
    }
}
