
import javax.imageio.IIOException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainGUI extends JFrame {
    //Window 1
    private JButton btn_Go;
    private JLabel JLabel_Main;
    private JTextField textField_Url;
    private JPanel mainJpanel;
    private JButton btn_Paste;
    private JLabel JLabel_Info;
    private JPanel linkPanel;
    private JPanel progressPanel;
    private urlValidator validate = new urlValidator();
    private clipboardMaster clipboard = new clipboardMaster();
    private scrapeMaster scrape = new scrapeMaster();

    //Window 2
    private JPanel dataPanel;
    private JTextField w2_pageTitleTextField;
    private JTextField w2_amountOfImagesJTextField;
//    private JTextArea mainJTextArea;


    public MainGUI(String s) {
        super(s);
        setContentPane(mainJpanel);
        textField_Url.setText("https://boards.4channel.org/g/thread/76759434/this-board-is-for-the-discussion-of-technology");

//      Image img = Toolkit.getDefaultToolkit().getImage(MainGUI.class.getResource("icon.ico"));
        java.net.URL imgUrl = getClass().getResource("icon.png");
        if(imgUrl != null){
            ImageIcon icon = new ImageIcon(imgUrl);
            setIconImage(icon.getImage());
        }

        setSize(500,130);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dataPanel.setVisible(false);
        JLabel_Info.setVisible(false);

        String clip = clipboard.getClipboard();
        if(!clip.equals("")){
            validate.setLink(clip);
            if(validate.validateURL()){textField_Url.setText(clipboard.getClipboard());}
        }

        btn_Go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Btn paste from clipboard.");
                String urlFieldString = textField_Url.getText();

                validate.setLink(urlFieldString);
                setInfo(""+ validate.validateURL() );

                if(validate.validateURL()){
                    scrape.setLink(urlFieldString);
                    scrape.getData();
                    initializeWindow2();
                }else{
                    setSize(500,160);
                    setInfo("Incorrect link");
                }
            }
        });

        btn_Paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField_Url.setText(clipboard.getClipboard());
                System.out.println("Btn Copy Clicked !");
            }
        });

        //Window 2 ->


    }

    private void setInfo(String message){
        if(!JLabel_Info.isVisible()){JLabel_Info.setVisible(true);}
        JLabel_Info.setText(message);
    }

    private void displayWindowTwo(){
        dataPanel.setVisible(true);
        linkPanel.setVisible(false);
    }

    private void initializeWindow2(){
        displayWindowTwo();
        w2_pageTitleTextField.setText(scrape.getUrlTitle());
        w2_amountOfImagesJTextField.setText(""+scrape.imagesAmount());

        String largeTempString ="";
        for(String item : scrape.getArrayListOfImages() ){
            largeTempString += item + "\n";
        }
//        mainJTextArea.setText(largeTempString);
        folderMaster folder = new folderMaster();

//        folder.writeLogFile(largeTempString);

    }

}
