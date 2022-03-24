import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class clipboardMaster {

    public String getClipboard(){
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            DataFlavor dataFlavor = DataFlavor.stringFlavor;
            return (String) clipboard.getData(DataFlavor.stringFlavor);

        } catch (IOException ex) {
//            ex.printStackTrace();
        } catch (UnsupportedFlavorException e) {
//            e.printStackTrace();
        }
        return "";
    }
}
