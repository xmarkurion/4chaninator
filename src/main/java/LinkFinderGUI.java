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
    private ArrayList<catalogLink> links = new ArrayList<>();

    public LinkFinderGUI(MainGUI main){
        super("Link Finder");
        setContentPane(linkJPanel);
        setSize(700, 160);
        setVisible(true);

        threadList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listModel = new DefaultListModel<>();
        threadList.setModel(listModel);

        fillListWithLinks();

        btnAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("btn Action Pressed \n");
//                main.setInfo("Link data transfer complete");
//                main.setTextField_UrlValue("https://boards.4channel.org/g/thread/76759434");
                getSelectedElements();
            }
        });
//        threadList.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                System.out.println((String) threadList.getSelectedValue());
//            }
//        });
    }

    private void getSelectedElements(){
        List<String> stringList = threadList.getSelectedValuesList();
        for( String str : stringList){
            System.out.println(str);
            int itemIndex = Integer.parseInt(str.substring(0,str.indexOf("|") -1));
            System.out.println(links.get(itemIndex).getUrl());
        }
    }

    private void addItemToList(String item){
        listModel.addElement(item);
    }

    private void fillListWithLinks(){
        scrapeMaster scraper = new scrapeMaster();
        scraper.getCatalogLinks();

        links = scraper.getArrayListOfCatalogLinks();
        for(int i=0; i < links.size(); i++){
            listModel.addElement("" + i + " | " + links.get(i).getFullName() );
        }
    }
}
