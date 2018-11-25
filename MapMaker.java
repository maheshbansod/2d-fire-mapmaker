import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MapMaker implements ActionListener {
	
	JFrame frame;
	JButton savebutton;
	JButton newbutton;
	JButton openbutton;
	JButton addtilebutton;
	//JButton clearbutton;
	
	JPanel tilepanel;
	
	int selectedtile = -1;
	int selectedtool = -1;
	
	Map map = new Map();
	
	final String TILE_FILE = "tiles.dat";
	final int TILE_WIDTH = 30;
	final Border selectedtool_border = BorderFactory.createMatteBorder(1, 5, 1, 1, Color.red);

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
		frame.add(map, BorderLayout.CENTER);
		
		mainpanel.add(newbutton);
		mainpanel.add(savebutton);
		mainpanel.add(openbutton);
		mainpanel.add(addtilebutton);
		
		if(map.assets.size()>0) showTiles();
		
		addtilebutton.addActionListener(this);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void showTiles() {
		tilepanel.removeAll();
		for(int i=0;i<map.assets.size();i++) {
				Tile t = (Tile)map.assets.get(i);
				Image scaledimg = t.image.getScaledInstance(TILE_WIDTH,
						TILE_WIDTH, Image.SCALE_SMOOTH);
				JButton btn = new JButton(new ImageIcon(scaledimg));
				if(i != selectedtile) btn.setBorder(BorderFactory.createEmptyBorder());
				else btn.setBorder(selectedtool_border);
				btn.setContentAreaFilled(false);
				btn.putClientProperty("id",i);
				btn.addActionListener(new ToolSelectHandler(this));
				tilepanel.add(btn);
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
		} else if(evt.getSource() == newbutton) {
			JFrame newframe = new JFrame("New map");
			JLabel namelabel = new JLabel("Map name");
			JLabel widthlabel = new JLabel("Width");
			JLabel heightlabel = new JLabel("Height");
			JTextField nametf = new JTextField(10);
			JTextField widthtf = new JTextField(3);
			JTextField heighttf = new JTextField(3);
			JButton createbtn = new JButton("Create");
			
			newframe.setLayout(new GridLayout(4,2));
			newframe.add(namelabel);
			newframe.add(nametf);
			newframe.add(widthlabel);
			newframe.add(widthtf);
			newframe.add(heightlabel);
			newframe.add(heighttf);
			newframe.add(new JPanel());
			newframe.add(createbtn);
			
			createbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					map = new Map();
					map.setSize(Integer.parseInt(widthtf.getText()),
							Integer.parseInt(heighttf.getText()));
					map.setName(namelabel.getText());
					//redisplay map
				}
			});
			
			newframe.setVisible(true);
		}
	}
	
	public static void main(String[] args) {
		new MapMaker();
	}

}

class ToolSelectHandler implements ActionListener {

	MapMaker m;
	
	ToolSelectHandler(MapMaker m) {
		this.m = m;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		int id = (int)btn.getClientProperty("id");
		m.selectedtile=id;
		m.showTiles();
	}
	

}