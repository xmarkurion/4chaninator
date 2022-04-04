import javax.swing.*;

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

    public LinkQueGUI(MainGUI main){
        setContentPane(mainJpanel);
        setSize(700, 250);
        setVisible(true);

        listQUE.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        queListModel = new DefaultListModel<>();
        listQUE.setModel(queListModel);
        updateAmountLabel();
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
}
