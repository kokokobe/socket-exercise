package com.liang.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HttpClient3 {
    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 300;
    private static final String[] books = { "java���������", "Tomcat��javaweb����", "��˿�ɰ׸���", "��������α��", "java2�߼��ӵ����" };

    /**
     * 
     * @ClassName: PostTestFrame
     * @Description:(����post������)
     * @author BriLiang(liangwen.liang@vipshop.com)
     * @date 2014��2��7��
     */
    private static class PostTestFrame extends JFrame {
        private static String doPost(String SERVER_URL, Map<String, String> post) throws IOException {
            // ����http����
/*            PrintWriter out = new PrintWriter(connection.getOutputStream());
            boolean first = true;*/
            String value=null;
            for (Map.Entry<String, String> pair : post.entrySet()) {
                /*
                 * if(first){ first=false; } else{ out.print('&'); } String name=pair.getKey(); out.print(name);
                 */
                // out.print("/#wd");
                value= pair.getValue();
                // out.print("=");
            }
/*            out.close();*/
            // ����HTTP��Ӧ����
            SERVER_URL+="/#wd="+value;
            URL url = new URL(SERVER_URL);
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buff)) != -1) {
                buffer.write(buff, 0, len);
            }
            inputStream.close();
            return buffer.toString();
        }

        public PostTestFrame() {
            setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            setTitle("BriLiangϵ�Ќ�˿");
            JPanel northPanel = new JPanel();
            add(northPanel, BorderLayout.NORTH);
            final JComboBox<Object> comboBox = new JComboBox<>();
            for (int i = 0; i < books.length; i++) {
                comboBox.addItem(books[i]);
            }
            northPanel.add(comboBox);
            final JTextArea result = new JTextArea();
            add(new JScrollPane(result));
            JButton getButton = new JButton("�鿴");
            northPanel.add(getButton);
            getButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final String SERVER_URL = "http://www.baidu.com";
                            result.setText("");
                            Map<String, String> post = new HashMap<>();
                            post.put("title", books[comboBox.getSelectedIndex()]);
                            try {
                                result.setText(doPost(SERVER_URL, post));
                            } catch (IOException e) {
                                result.setText("" + e);
                            }
                        }

                    }).start();
                }
            });
        }
    }

    public static void main(String[] args) {
        JFrame frame = new PostTestFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
