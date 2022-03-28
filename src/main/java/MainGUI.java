import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton btnBackJButton;
    private JButton btn_checkLinkButton;
//    private JTextArea mainJTextArea;

    public MainGUI(String s) {
        super(s);
        setContentPane(mainJpanel);
//      Image img = Toolkit.getDefaultToolkit().getImage(MainGUI.class.getResource("icon.ico"));
        java.net.URL imgUrl = getClass().getResource("icon.png");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            setIconImage(icon.getImage());
        }

        setSize(700, 130);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dataPanel.setVisible(false);
        JLabel_Info.setVisible(false);

        String clip = clipboard.getClipboard();
        if (!clip.equals("")) {
            validate.setLink(clip);
            if (validate.validateURL()) {
                textField_Url.setText(clipboard.getClipboard());
            }
        }

        btn_Go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Btn Go Pressed.");
                System.out.println(""+scrape.getStatus());
                validate.setLink(textField_Url.getText());
                setInfo("" + validate.validateURL());

                if (validate.validateURL()) {
                    initializeWindow2();
                    System.out.println("Link correct");
                } else {
                    setSize(700, 160);
                    setInfo("Incorrect link");
                }
            }
        });

        btn_Paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField_Url.setText(clipboard.getClipboard());
                System.out.println("Btn Copy Clicked !");
                System.out.println(""+scrape.getStatus());
            }
        });

        btn_checkLinkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrape.setLink(textField_Url.getText());
                scrape.getData();
            }
        });

        //Window 2 ->
        btnBackJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               displayWindowOne();
            }
        });
    }

    private void setInfo(String message) {
        if (!JLabel_Info.isVisible()) {
            JLabel_Info.setVisible(true);
        }
        JLabel_Info.setText(message);
    }

    private void displayWindowTwo() {
        dataPanel.setVisible(true);
        linkPanel.setVisible(false);
    }

    private void displayWindowOne(){
        textField_Url.setText("");
        scrape.setLink("");
        scrape.emptyArrayList();
        dataPanel.setVisible(false);
        linkPanel.setVisible(true);
    }

    private void initializeWindow2(){
        if(scrape.getStatus()){
            displayWindowTwo();
            w2_pageTitleTextField.setText(scrape.getUrlTitle());
            w2_amountOfImagesJTextField.setText("" + scrape.imagesAmount());
        }
//
//        folderMaster folder = new folderMaster();
//
//        String largeTempString = scrape.getUrlTitle()+"\n";
//        largeTempString += folder.stringSpaceMaker(scrape.getUrlTitle());
//        largeTempString +=  scrape.getLink() + "\n";
//        largeTempString += folder.stringSpaceMaker(scrape.getLink());
//
//        folder.mkDirAt(folder.urlNameProcessor(scrape.getLink()));
//
//        for (String item : scrape.getArrayListOfImages()) {
//            if (folder.saveImage(item)) {
//                largeTempString += item + " - saved \n";
//                System.out.println(item);
//            } else {
//                largeTempString += item + " - file exist \n";
//                continue;
//            }
//            try{
//                TimeUnit.MILLISECONDS.sleep(500);
//            }catch (InterruptedException e){System.out.print("Can't Sleep need more Yerba Mate!");
//            }
//
//        }
//        folder.writeLogFile(largeTempString);
    }

}
