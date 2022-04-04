import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LinkFinderGUI extends JFrame{
    private final DefaultListModel<String> listModel;
//    private final DefaultListModel<String> jComboBoxModel;
    private JButton btnAction;
    private JPanel linkJPanel;
    private JPanel listPanel;

    private JList threadList;
    private JComboBox comboBoxBoardSelect;
    private DefaultListCellRenderer listRenderer;

    private JPanel boardPanel;
    private JButton btnGetListOfThreads;
    private ArrayList<catalogLink> links = new ArrayList<>();
    private ArrayList<catalogLink> selectedLinks = new ArrayList<>();

    public LinkFinderGUI(MainGUI main){
        super("Link Finder");

        scrapeBoardMaster boardMaster = new scrapeBoardMaster();

        setContentPane(linkJPanel);
        setSize(700, 560);
        setVisible(true);

        threadList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listModel = new DefaultListModel<>();
        threadList.setModel(listModel);

        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        comboBoxBoardSelect.setRenderer(listRenderer);

        for(String data : boardMaster.getAllLinkTitle()){
            comboBoxBoardSelect.addItem(data);
        }
        comboBoxBoardSelect.setSelectedIndex(57);


//        comboBoxBoardSelect = new JComboBox<String>(boardListJB);
//        comboBoxBoardSelect.addItem("English");

//        jComboBoxModel = new JComboBox<String>();
//        comboBoxBoardSelect.setModel(jComboBoxModel);

//        Here
//        fillListWithLinks("https://boards.4chan.org/wg/catalog");

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

        btnGetListOfThreads.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(comboBoxBoardSelect.getSelectedIndex());

                String webthing = boardMaster.getAllLinkArray()[comboBoxBoardSelect.getSelectedIndex()];
                String webThingTwo = webthing + "catalog";
                System.out.println(webThingTwo);
                fillListWithLinks(webThingTwo);
            }
        });
    }

    private void getSelectedElements(){
        List<String> stringList = threadList.getSelectedValuesList();
        for( String str : stringList){
            System.out.println(str);
            int itemIndex = Integer.parseInt(str.substring(0,str.indexOf("|") -1));
            System.out.println(links.get(itemIndex).getUrl());

            //Adding selected links to arraylist selected links
            selectedLinks.add(links.get(itemIndex));
        }
    }

    private void addItemToList(String item){
        listModel.addElement(item);
    }

    private void fillListWithLinks(String urlLink){
        scrapeMaster scraper = new scrapeMaster();
        scraper.getCatalogLinks(urlLink);

        links = scraper.getArrayListOfCatalogLinks();
        for(int i=0; i < links.size(); i++){
            listModel.addElement("" + i + " | " + links.get(i).getFullName() );
        }
    }
}
