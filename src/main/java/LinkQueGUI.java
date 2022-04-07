import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LinkQueGUI extends JFrame{
    private static LinkQueGUI INSTANCE;
    private final DefaultListModel<String> queListModel;
    private int queConter = 0;

    private scrapeMaster scrapeMaster = new scrapeMaster();

    private JPanel listJpanel;
    private JList listQUE;
    private JPanel btnJpanel;
    private JButton btnDownloadSelected;
    private JButton btnDeleteSelected;
    private JButton btnDeleteAll;
    private JTextField amountOfLinksField;
    private JPanel progressJpanel;
    private JPanel Jpanel_TextArea;
    private JTextArea outTextArea;
    private JProgressBar progressBar;
    private JScrollPane sp;
    private JPanel masterJpanel;
    private JProgressBar totalProgressBar;
    private MainGUI mainGui;

    private LinkQueGUI(MainGUI main){
        super("Que Manager - The Singleton One");
        this.mainGui = main;
        setResizable(false);

//        setContentPane(mainJpanel);
        setContentPane(masterJpanel);
        setSize(700, 500);
        setVisible(true);

        progressJpanel.setVisible(true);
        progressJpanel.repaint();
        revalidate();

        listQUE.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        queListModel = new DefaultListModel<>();
        listQUE.setModel(queListModel);
        updateAmountLabel();

        btnDeleteAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queListModel.clear();
                updateAmountLabel();
            }
        });
        btnDeleteSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(queListModel.size() > 0){
                    queListModel.remove(listQUE.getSelectedIndex());
                }
                updateAmountLabel();
            }
        });
        btnDownloadSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalProgressBar.setValue(0);
                progressBar.setValue(0);
                queStart();
            }
        });
    }

    /**
     * Singleton use - to make sure only one object LinkQueGUI could be created.
     * @param mainGui
     * @return
     */
    public static LinkQueGUI getInstance(MainGUI mainGui){
        if(INSTANCE == null){
            INSTANCE = new LinkQueGUI(mainGui);
        }
        return INSTANCE;
    }

    public static boolean getInstanceStatus(MainGUI mainGui){
        if(INSTANCE == null){
            return false;
        }else{
            return true;
        }
    }

    public void addItemToQue(String item){
        if(!queListModel.contains(item)){
            queListModel.addElement(item);
            updateAmountLabel();
        }
    }

    private void updateAmountLabel(){
        amountOfLinksField.setText(""+queListModel.getSize());
    }

    private void queStart(){
        System.out.println("queListModel = " + this.queConter + "/" + queListModel.size());

        if(this.queConter < queListModel.size()){
            totalProgressBar.setMaximum(queListModel.size());
            oneThread(queListModel.get(this.queConter));
        }else{
            System.out.println("Que Finished.");
            queListModel.clear();
            updateAmountLabel();
            this.queConter = 0;
        }
    }

    private void oneThread(String link){
        scrapeMaster.setLink(link);
        scrapeMaster.getData();

        Runnable r1 = () -> {
            folderMaster folder = new folderMaster();

            String largeTempString = scrapeMaster.getUrlTitle()+"\n";
            largeTempString += folder.stringSpaceMaker(scrapeMaster.getUrlTitle());
            largeTempString +=  scrapeMaster.getLink() + "\n";
            largeTempString += folder.stringSpaceMaker(scrapeMaster.getLink());

            String date = "Date: " + folder.nowDateString() + "\n";
            largeTempString += date;
            largeTempString += folder.stringSpaceMaker(date);

            folder.mkDirAt(folder.urlNameProcessor(link));

            JScrollBar sb = sp.getVerticalScrollBar();
            progressBar.setMaximum(scrapeMaster.imagesAmount());

            int counter = 1;
            for(String item : scrapeMaster.getArrayListOfImages()){
                if(folder.saveImage(item)){
                    largeTempString += item + " - saved \n";
                    outTextArea.append("" + counter + "/" + scrapeMaster.imagesAmount() +" -> "+ item + " - saved \n");

                    // Auto scroll down
                    sb.setValue(sb.getMaximum());
                    progressBar.setValue(counter);
                    counter++;
                }else {
                    largeTempString += item + " - file exist \n";
                    outTextArea.append("" + counter + "/" + scrapeMaster.imagesAmount() +" -> "+ item + " - file exist \n");
                    sb.setValue(sb.getMaximum());
                    progressBar.setValue(counter);
                    counter++;
                    continue;
                }
                mainGui.sleeep(500);
            }
            outTextArea.append("\n Done.....  \n \n");
            folder.writeLogFile(largeTempString);
            sb.setValue(sb.getMaximum());

            scrapeMaster.emptyArrayList();
            mainGui.sleeep(500);

            this.queConter++;
            totalProgressBar.setValue(this.queConter);
            queStart();
        };
        new Thread(r1).start();
    }

}
