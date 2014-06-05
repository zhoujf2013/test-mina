package com.zhoujf.test.mina.chat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerJFrame extends JFrame {

    private static final long serialVersionUID = 3574112566398495317L;
    
    public static int PORT = 8888;
    
    public static String PORT_KEY = "chat.port";

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
        setTitle("服务器状态");
        setSize(500 , 300);
        setMinimumSize(new Dimension(400, 300));
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
        
        itemStop.addActionListener(new ActionListener() {
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

        JPanel pTop = new JPanel(new BorderLayout(5,0));
        pTop.setBorder(new CompoundBorder(new TitledBorder("输入信息"), new EmptyBorder(5,10,5,10)));
        txtPort = new JTextField("8888");
        JButton btnStart = new JButton("启动");
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton)e.getSource();
                if(btn.getText().equals("启动")){
                    btn.setText("停止");
                    startServer();
                } else {
                    btn.setText("启动");
                }
            }
        });

        jtaLog = new JTextArea();
        jtaLog.setLineWrap(true);
        scrollPane = new JScrollPane(jtaLog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setAutoscrolls(true);
        scrollPane.setBorder(BorderFactory.createTitledBorder("日志"));

        JLabel lblPort = new JLabel("端口号:");
        lblPort.setLabelFor(txtPort);

        pTop.add(lblPort, BorderLayout.WEST);
        pTop.add(txtPort, BorderLayout.CENTER);
        pTop.add(btnStart, BorderLayout.EAST);

        contentPane.setLayout(new BorderLayout(10, 10));
        contentPane.add(pTop, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }
    
    public void startServer(){
        txtPort.setEditable(false);
        
        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        SocketSessionConfig ssc = acceptor.getSessionConfig();
        ssc.setIdleTime(IdleStatus.BOTH_IDLE, 3);
        
        // 添加 filter
        if(System.getProperty("chat.logging") != null) {
            acceptor.getFilterChain().addLast("logging", new LoggingFilter());
        }
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        acceptor.setHandler(new ServerIoHandler(new ServerCallback(){
            @Override
            public void callback(String message) {
                jtaLog.append(message);
                jtaLog.append("\n");
            }

            @Override
            public void log(String message) {
                jtaLog.append(message);
                jtaLog.append("\n");
            }

            @Override
            public void broadcast(String message) {
                // TODO Auto-generated method stub
            }

            @Override
            public void sendMessage(IoSession session, String message) {
                // TODO Auto-generated method stub
            }}
        ));
        
        // 设置监听端口
        int port = 8888;
        try{
            if(System.getProperty(PORT_KEY) != null) {
                port = Integer.parseInt(System.getProperty(PORT_KEY));
            }
            acceptor.bind(new InetSocketAddress(port));
            logger.info("服务器启动成功 port:{}", port);
        } catch(IOException ex) {
            logger.error("端口一杯占用  port:{}", port);
            ex.printStackTrace();
            System.exit(1);
        } catch(Exception ex) {
            logger.error("端口错误:{}", System.getProperty(PORT_KEY));
            ex.printStackTrace();
            System.exit(1);
        }
        jtaLog.append("服务器启动成功\n");
    }
    
    public void stopServer(){
        txtPort.setEditable(true);
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ServerJFrame();
            }
        });
    }
    
    private JTextField txtPort;
    private JTextArea jtaLog;
    private JScrollPane scrollPane;
}
