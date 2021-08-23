import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Form extends JFrame implements VisualInterface{
    private final JTextField jtfMessage=new JTextField("Введите ваше сообщение: ");
    private final JTextField jtfName;
    private final JTextArea jtaTextAreaMessage;
    private final JLabel jlNumberOfClients;

    Client client;

    public Form(ServerSettings serverSettings) {
        this.client=new Client(serverSettings,this);

        Action actionSendMsg = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jtfMessage.getText().trim().isEmpty() && !jtfName.getText().trim().isEmpty()) {
                    try {
                        send();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    // фокус на текстовое поле с сообщением
                    jtfMessage.grabFocus();
                }
            }
        };
        // Задаём настройки элементов на форме
        setBounds(600, 300, 600, 500);
        setTitle("Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jtaTextAreaMessage = new JTextArea();
        jtaTextAreaMessage.setEditable(false);
        jtaTextAreaMessage.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(jtaTextAreaMessage);
        add(jsp, BorderLayout.CENTER);

        JPanel bottomPanel1 = new JPanel(new BorderLayout());
        add(bottomPanel1, BorderLayout.NORTH);
        jlNumberOfClients = new JLabel("Количество клиентов в чате: ");
        bottomPanel1.add(jlNumberOfClients, BorderLayout.NORTH);
        JLabel nickText = new JLabel("Ваш ник: ");
        bottomPanel1.add(nickText, BorderLayout.WEST);
        jtfName = new JTextField("Введите ваш ник: ");
        bottomPanel1.add(jtfName, BorderLayout.CENTER);
        jtfName.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jtfName.getText().trim().isEmpty()) {
                    try {
                        send();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    // фокус на текстовое поле с сообщением
                    jtfMessage.grabFocus();
                }
            }
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        JButton jbSendMessage = new JButton("Отправить");
        bottomPanel.add(jbSendMessage, BorderLayout.EAST);
        //jtfMessage = new JTextField("Введите ваше сообщение: ");
        bottomPanel.add(jtfMessage, BorderLayout.CENTER);
        jtfMessage.addActionListener(actionSendMsg);

        jbSendMessage.addActionListener(actionSendMsg);
        // при фокусе поле сообщения очищается
        jtfMessage.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfMessage.setText("");
            }
        });
        // при фокусе поле имя очищается
        jtfName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfName.setText("");
            }
        });

        // добавляем обработчик события закрытия окна клиентского приложения
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                client.exitActions();
            }
        });
        // отображаем форму
        setVisible(true);

    }

    @Override
    public void printUsersQuantity(String text) {
        jlNumberOfClients.setText(text);
    }

    @Override
    public void printText(String text) {
        jtaTextAreaMessage.append(text);
    }

    @Override
    public void closeInterface() {
        super.dispose();
    }

    @Override
    public void correctNick() {
        jtfName.grabFocus();
    }

    private void send() throws IOException {
        if (!jtfName.getText().equals(client.getName())){
            client.setName(jtfName.getText());
        }
        client.send(jtfMessage.getText());
        jtfMessage.setText("");
    }


}
