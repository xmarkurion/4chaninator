import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LinkFinderGUI extends JFrame{
    private String dataString;
    private JButton btnAction;
    private JPanel linkJPanel;

    public LinkFinderGUI(){
        super("Link Finder");
        setContentPane(linkJPanel);
        setSize(700, 160);
        setVisible(true);

        btnAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("btn Action Pressed");
                dataString = "sdfsdfsdfsdf";
            }
        });
    }

    public String getData(){
        return this.dataString;
    }
}
