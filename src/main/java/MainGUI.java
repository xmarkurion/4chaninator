
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class MainGUI extends JFrame {
    private JButton btn_Go;
    private JLabel JLabel_Main;
    private JTextField textField_Url;
    private JPanel mainJpanel;
    private JButton btn_Paste;
    private JProgressBar progressBar1;
    private JLabel JLabel_Info;
    private JPanel linkPanel;
    private JPanel progressPanel;
    private JPanel dataPanel;
    private urlValidator validate = new urlValidator();
    private clipboardMaster clipboard = new clipboardMaster();



    public MainGUI(String s) {
        super(s);
        setContentPane(mainJpanel);

//      Image img = Toolkit.getDefaultToolkit().getImage(MainGUI.class.getResource("icon.ico"));
        java.net.URL imgUrl = getClass().getResource("icon.png");
        if(imgUrl != null){
            ImageIcon icon = new ImageIcon(imgUrl);
            setIconImage(icon.getImage());
        }

        setSize(500,200);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        progressPanel.setVisible(false);
        dataPanel.setVisible(false);

        String clip = clipboard.getClipboard();
        if(!clip.equals("")){
            validate.setLink(clip);
            if(validate.validateURL()){textField_Url.setText(clipboard.getClipboard());}
        }

        btn_Go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Btn Clicked !");
                String urlFieldString = textField_Url.getText();

                validate.setLink(urlFieldString);
                setInfo(""+ validate.validateURL() );

                int value = (progressBar1.getValue()<=90) ? progressBar1.getValue() + 10 : 100;
                progressBar1.setValue(value);
                setInfo("Barr value: " + value);
                progressPanel.setVisible(true);

                if(value == 100 && validate.validateURL()){displayWindowTwo();}
            }
        });

        btn_Paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField_Url.setText(clipboard.getClipboard());
                System.out.println("Btn Copy Clicked !");
            }
        });
    }

    private void setInfo(String message){
        JLabel_Info.setText(message);
    }

    private void displayWindowTwo(){
        dataPanel.setVisible(true);
        progressPanel.setVisible(false);
        linkPanel.setVisible(false);
    }

}
