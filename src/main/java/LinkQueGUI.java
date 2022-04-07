import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class LinkQueGUI extends JFrame{
    private static LinkQueGUI INSTANCE;
    private boolean elementFinish = false;
    private final DefaultListModel<String> queListModel;
    private JPanel mainJpanel;
    private JPanel listJpanel;
    private JList listQUE;
    private JPanel btnJpanel;
    private JButton btnDownloadSelected;
    private JButton btnDeleteSelected;
    private JButton btnDeleteAll;
    private JTextField amountOfLinksField;
    private MainGUI mainGui;

    private LinkQueGUI(MainGUI main){
        super("Que Manager - The Singleton One");
        this.mainGui = main;

        setContentPane(mainJpanel);
        setSize(700, 250);
        setVisible(true);

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

    public void setelementFinish(boolean value){
        this.elementFinish = value;
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

//    private void queStart(){
//        Runnable th1 = ()-> {
//            mainGui.setTextField_UrlValue(queListModel.get(0));
//            mainGui.clickCheckButton();
//        };
//        new Thread(th1).start();
//    }

    private void queStart(){
       for(int x=0; x<queListModel.size(); x++){

           this.elementFinish = false;
           mainGui.setTextField_UrlValue(queListModel.get(x));
           mainGui.clickCheckButton();

       }


    }
}
