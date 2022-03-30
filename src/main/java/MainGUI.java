import javax.swing.*;
import javax.swing.JScrollPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.concurrent.TimeUnit;

public class MainGUI extends JFrame {
    //Vars

    //Window 1
    private JLabel JLabel_Main;
    public JTextField textField_Url;
    private JPanel mainJpanel;
    private JPanel progressPanel;
    private JButton btn_Paste;
    private JLabel JLabel_Info;
    private JPanel linkPanel;

    private urlValidator validate = new urlValidator();
    private clipboardMaster clipboard = new clipboardMaster();
    private scrapeMaster scrape = new scrapeMaster();

    //Window 2
    private JPanel dataPanel;
    private JTextField w2_pageTitleTextField;
    private JTextField w2_amountOfImagesJTextField;
    private JButton btnBackJButton;
    private JButton btn_checkLinkButton;

    private JTextArea outTextArea;
    private JPanel jPanel_TextArea;
    private JScrollPane sp;
    private JProgressBar progressBar;
    private JButton infoButton;
    private JButton btnLinkFinder;

    public MainGUI(String s) {
        super(s);
        setContentPane(mainJpanel);
        setLocationRelativeTo(null);

        java.net.URL imgUrl = getClass().getResource("icon.png");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            setIconImage(icon.getImage());
        }

        setSize(700, 160);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dataPanel.setVisible(false);
        JLabel_Info.setVisible(false);
        setDefaultInfoLabel();

        String clip = clipboard.getClipboard();
        if (!clip.equals("")) {
            validate.setLink(clip);
            if (validate.validateURL()) {
                textField_Url.setText(clipboard.getClipboard());
            }else{
                setDefaultInfoLabel();
            }
        }

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
                setInfo("");
                validate.setLink(textField_Url.getText());

                if (validate.validateURL()) {
                    scrape.setLink(textField_Url.getText());
                    System.out.println("Link correct");

                    scrape.getData();
                    setInfo("Found: " + scrape.imagesAmount() + " images. Click Go to start download.");

                    setSize(730, 410);
                    displayWindowTwo();
                } else {
                    setInfo("Incorrect link");
                }
            }
        });

        //Window 2 ->
        btnBackJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               displayWindowOne();
            }
        });

        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               validate.openWebpage(URI.create("https://github.com/xmarkurion/4chaninator"));
            }
        });

        btnLinkFinder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("btnInfo Pressed");
                LinkFinderGUI link = new LinkFinderGUI();
                setInfo(link.getData());
            }
        });
    }

    public void setInfo(String message) {
        if (!JLabel_Info.isVisible()) {
            JLabel_Info.setVisible(true);
        }
        JLabel_Info.setText(message);
    }

    private void setDefaultInfoLabel(){
        setInfo("Please enter a link then -> Check Link.");
    }

    private void displayWindowOne(){
        setSize(700, 160);
        outTextArea.setText("");
        textField_Url.setText("");
        scrape.setLink("");
        scrape.emptyArrayList();
        dataPanel.setVisible(false);
        linkPanel.setVisible(true);
        setDefaultInfoLabel();
    }

    private void displayWindowTwo() {
        initializeWindow2();
    }

    private void initializeWindow2(){
        dataPanel.setVisible(true);
        linkPanel.setVisible(false);

        if(scrape.getStatus()){
            sp.setAutoscrolls(true);
            setupDownloader();
        }
    }

    private void setupDownloader(){
        Runnable r1 = () -> {
            w2_pageTitleTextField.setText(scrape.getUrlTitle());
            w2_amountOfImagesJTextField.setText("" + scrape.imagesAmount());
        };

        Runnable r2 = () ->{
            folderMaster folder = new folderMaster();

            String largeTempString = scrape.getUrlTitle()+"\n";
            largeTempString += folder.stringSpaceMaker(scrape.getUrlTitle());
            largeTempString +=  scrape.getLink() + "\n";
            largeTempString += folder.stringSpaceMaker(scrape.getLink());

            folder.mkDirAt(folder.urlNameProcessor(scrape.getLink()));

            JScrollBar sb = sp.getVerticalScrollBar();
            progressBar.setMaximum(scrape.imagesAmount());

            int counter = 1;
            for (String item : scrape.getArrayListOfImages()) {
                if (folder.saveImage(item)) {
                    largeTempString += item + " - saved \n";
                    System.out.println(item);
                    outTextArea.append("" + counter + "/" + scrape.imagesAmount() +" -> "+ item + " - saved \n");
                    sb.setValue(sb.getMaximum());
                    progressBar.setValue(counter);
                    counter++;
                } else {
                    largeTempString += item + " - file exist \n";
                    outTextArea.append("" + counter + "/" + scrape.imagesAmount() +" -> "+ item + " - file exist \n");
                    sb.setValue(sb.getMaximum());
                    progressBar.setValue(counter);
                    counter++;
                    continue;
                }
                try{
                    TimeUnit.MILLISECONDS.sleep(500);
                }catch (InterruptedException e){System.out.print("Can't Sleep need more Yerba Mate!");
                }
            }
            outTextArea.append("\n All done..... ");

            folder.writeLogFile(largeTempString);
            sb.setValue(sb.getMaximum());
        };
        new Thread(r1).start();
        new Thread(r2).start();
    }

}
