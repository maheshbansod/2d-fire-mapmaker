import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile extends Asset {
	BufferedImage image;
	double density;
	
	static final double DENSE = 5.0;
	static final double SPARSE = 0.0;
	static final double MEDIUM_DENSITY = (DENSE+SPARSE)/2;
	
	static final int TILE_WIDTH = 30;
	static final int TILE_HEIGHT = 30;

	Tile(String fname, double density) throws IOException {
		super("tile");
		this.density = density;
		setImageFile(new File(fname));
	}
	
	Tile(File file, double density) throws IOException {
		super("tile");
		this.density = density;
		setImageFile(file);
	}
	
	void setImageFile(File file) throws IOException {
		image = ImageIO.read(file);
	}

}
