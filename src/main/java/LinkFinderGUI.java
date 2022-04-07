import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
    private JButton btnOpenInBrowser;
    private ArrayList<catalogLink> links = new ArrayList<>();
    private ArrayList<catalogLink> selectedLinks = new ArrayList<>();
    Desktop desk =Desktop.getDesktop();

    public LinkFinderGUI(MainGUI main){
        super("Link Finder");
        setResizable(false);

        scrapeBoardMaster boardMaster = new scrapeBoardMaster();
//        LinkQueGUI linkQueGUI = new LinkQueGUI(main);
        LinkQueGUI linkQueGUI = LinkQueGUI.getInstance(main);

        setContentPane(linkJPanel);
        setSize(700, 560);
        setVisible(true);

        threadList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listModel = new DefaultListModel<>();
        threadList.setModel(listModel);

        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        comboBoxBoardSelect.setRenderer(listRenderer);

        boolean nsfw = false;
        if(nsfw){
            for(String data : boardMaster.getAllLinkTitle()){
                comboBoxBoardSelect.addItem(data);
            }
            comboBoxBoardSelect.setSelectedIndex(57);
        }else{
            for(String data : boardMaster.getSafeLinkTitle()){
                comboBoxBoardSelect.addItem(data);
            }
        }

        btnAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("btn Action Pressed \n");
//                main.setInfo("Link data transfer complete");
//                main.setTextField_UrlValue("https://boards.4channel.org/g/thread/76759434");

                selectedLinks.clear();
                getSelectedElements();
                selectedLinks.forEach(link -> {
                    linkQueGUI.addItemToQue(link.getUrl());
                });
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
                selectedLinks.clear();
                String webthing = "";
                if(nsfw){
                    webthing = boardMaster.getAllLinkArray()[comboBoxBoardSelect.getSelectedIndex()];
                }else{
                    webthing =  boardMaster.getSafeLinkArray()[comboBoxBoardSelect.getSelectedIndex()];
                }

                String webThingTwo = webthing + "catalog";
                System.out.println(webThingTwo);
                fillListWithLinks(webThingTwo);
            }
        });

        btnOpenInBrowser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getSelectedElements();
                selectedLinks.forEach(q->{
                    openLink(q.getUrl());
                });
            }
        });
    }

    private boolean openLink(String link){
        try {
            URI target = new URI(link);
            desk.browse(target);
            return true;
        } catch (URISyntaxException | IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void getSelectedElements(){
        List<String> stringList = threadList.getSelectedValuesList();
        for( String str : stringList){
            System.out.println(str);
            int itemIndex = Integer.parseInt(str.substring(0,str.indexOf("|") -1));
            System.out.println(links.get(itemIndex).getUrl());

            //Check if array list already contain a link
            if(!(selectedLinks.contains(links.get(itemIndex)))){
                selectedLinks.add(links.get(itemIndex));
            }
        }
    }

    private void addItemToList(String item){
        listModel.addElement(item);
    }

    private void fillListWithLinks(String urlLink){
        scrapeMaster scraper = new scrapeMaster();
        scraper.getCatalogLinks(urlLink);

        listModel.clear();
        links = scraper.getArrayListOfCatalogLinks();
        for(int i=0; i < links.size(); i++){
            listModel.addElement("" + i + " | " + links.get(i).getFullName() );
        }
    }
}
