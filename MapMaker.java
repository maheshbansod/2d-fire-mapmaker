import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MapMaker implements ActionListener {
	
	JFrame frame;
	JButton savebutton;
	JButton newbutton;
	JButton openbutton;
	JButton addtilebutton;
	//JButton clearbutton;
	
	JPanel tilepanel;
	
	Map map = new Map();
	
	final String TILE_FILE = "tiles.dat";
	final int TILE_WIDTH = 30;

	MapMaker() {
		frame = new JFrame("Map Maker for 2D fire");
		frame.setLayout(new BorderLayout());
		frame.setSize(800, 600);
		
		JPanel mainpanel = new JPanel(new FlowLayout());
		tilepanel = new JPanel();
		tilepanel.setLayout(new BoxLayout(tilepanel,BoxLayout.PAGE_AXIS));
		
		savebutton = new JButton("Save");
		newbutton = new JButton("New");
		openbutton = new JButton("Open");
		addtilebutton = new JButton("Add tile");
		
		frame.add(mainpanel, BorderLayout.NORTH);
		frame.add(tilepanel, BorderLayout.WEST);
		
		mainpanel.add(newbutton);
		mainpanel.add(savebutton);
		mainpanel.add(openbutton);
		mainpanel.add(addtilebutton);
		
		if(map.assets.size()>0) showTiles();
		
		addtilebutton.addActionListener(this);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	void showTiles() {
		for(Asset asset: map.assets) {
			if(asset.getClass() == Tile.class) {
				Tile t = (Tile)asset;
				Image scaledimg = t.image.getScaledInstance(TILE_WIDTH,
						TILE_WIDTH, Image.SCALE_SMOOTH);
				JButton btn = new JButton(new ImageIcon(scaledimg));
				btn.setBorder(BorderFactory.createEmptyBorder());
				btn.setContentAreaFilled(false);
				btn.putClientProperty("id",t.id);
				tilepanel.add(btn);
			}
		}
		tilepanel.validate();
		
		frame.validate();
	}
	
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == addtilebutton) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter imgfilter = new FileNameExtensionFilter("Images","png","jpg");
			chooser.setFileFilter(imgfilter);
			int returnVal = chooser.showOpenDialog(frame);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					map.addAsset(new Tile(chooser.getSelectedFile(),Tile.MEDIUM_DENSITY));
					showTiles();
				} catch(IOException e) {
					JOptionPane.showMessageDialog(frame, "Couldn't open file "+chooser.getSelectedFile().getName(),"I/O Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new MapMaker();
	}

}
