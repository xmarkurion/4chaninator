import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LinkQueGUI extends JFrame{
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

    public LinkQueGUI(MainGUI main){
        super("Que Manager que que que");
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
        Runnable th1 = ()-> {
            mainGui.setTextField_UrlValue(queListModel.get(0));
//           / mainGui.
        };
        new Thread(th1).start();
    }
}
