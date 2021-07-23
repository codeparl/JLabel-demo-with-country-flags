
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;

public class LabelDemo extends JPanel {

    private final HashMap<String, String> COUNTRIES_MAP;
    private int counter = 0;

    public LabelDemo() {

        COUNTRIES_MAP = transformToMap();

        int rows = 5, coulmns = 8, gap = 10;
        GridLayout gridLayout = new GridLayout(rows, coulmns, gap, gap);

        COUNTRIES_MAP.forEach((key, value) -> {
            if (counter == (rows * coulmns))
                return;

            String name = COUNTRIES_MAP.get(key);
            ImageIcon  icon =  getFlag(key);

            //add a label only if the icon is found
            if(icon != null){
                JLabel label = new JLabel(name);
                label.setToolTipText(name);
                label.setIcon(icon);
                label.setVerticalTextPosition(JLabel.BOTTOM);
                label.setHorizontalTextPosition(JLabel.CENTER);

                add(label);
                ++counter;
            }
           
        });

        setLayout(gridLayout);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    }

    /**
     * This method reads a simple json file and transforms it into a HashMap object
     * 
     * @return A HashMap containing country names
     */
    public HashMap<String, String> transformToMap() {
        HashMap<String, String> countryMap = new HashMap<>();
        Path path = Paths.get("countries.json");
        try (BufferedReader bReader = Files.newBufferedReader(path)) {
            while (bReader.readLine() != null) {
                var line = bReader.readLine().trim().replaceAll("(\\{\\}|\")", "");
                String[] pieces = line.split("(:)");
                // remove the last char ,
                countryMap.put(pieces[0].toLowerCase().trim(), pieces[1].substring(0, pieces[1].length() - 1));
            }
        } catch (Exception e) {
        }

        return countryMap;
    }

    private ImageIcon getFlag(String key) {
        java.net.URL iconUrl = this.getClass().getResource("flags/" + key + ".png");
        if (iconUrl != null) {
            return new ImageIcon(iconUrl);
        } else {
            System.err.println(iconUrl + " not found");
        }
        return null;
    }
}
