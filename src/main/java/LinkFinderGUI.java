import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LinkFinderGUI extends JFrame{
    private final DefaultListModel<String> listModel;
    private JButton btnAction;
    private JPanel linkJPanel;
    private JPanel listPanel;

    private JList threadList;

    public LinkFinderGUI(MainGUI main){
        super("Link Finder");
        setContentPane(linkJPanel);
        setSize(700, 160);
        setVisible(true);

        threadList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listModel = new DefaultListModel<>();
        threadList.setModel(listModel);

        fillListWithRandomThreadNames();

        btnAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("btn Action Pressed \n");
//                main.setInfo("Link data transfer complete");
//                main.setTextField_UrlValue("https://boards.4channel.org/g/thread/76759434");
//                fillListWithRandomThreadNames();

//                System.out.println((String) threadList.getSe);
                getSelectedElements();

                scrapeMaster scraper = new scrapeMaster();
                scraper.getCatalogLinks();
            }
        });
        threadList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println((String) threadList.getSelectedValue());
            }
        });
    }

    private void getSelectedElements(){
        List<String> stringList = threadList.getSelectedValuesList();
        for( String str : stringList){
            System.out.println(str);
        }
    }

    private void addItemToList(String item){
        listModel.addElement(item);
    }

    private void fillListWithRandomThreadNames(){
        listModel.addElement("Something1");
        listModel.addElement("Something2");
        listModel.addElement("Something3");
        listModel.addElement("Something4");
        listModel.addElement("Something5");
    }
}
