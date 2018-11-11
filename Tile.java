import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile extends Asset {
	BufferedImage image;
	double density;

	Tile(String fname, double density) {
		super("tile");
		this.density = density;
		try {
			image = ImageIO.read(new File(fname));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
