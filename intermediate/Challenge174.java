import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.imageio.ImageIO;


public class Challenge174 {
	private String username = null;
	private final String HASHALGO = "MD5";
	private final String ENCODING = "UTF-8";
	private final int WIDTH = 8;
	private final int HEIGHT = 8;
	
	
	public Challenge174(String username) {
		this.username = username;
	}
	
	public byte[] getHash() {
		MessageDigest md = null;
		byte[] hash = {};
		
		try {
			md = MessageDigest.getInstance(HASHALGO);
			hash = md.digest(username.getBytes(ENCODING));
		}
		catch (NoSuchAlgorithmException e) {
			System.out.println(HASHALGO + " not found");
			System.exit(1);
		}
		catch (UnsupportedEncodingException e) {
			System.out.println(ENCODING + " not found");
			System.exit(1);
		}
		
		return hash;
	}
	
	public BufferedImage createImage(byte[] hash) {
		// Create a BufferedImage and get a Graphics object
		BufferedImage image = new BufferedImage(
				WIDTH*8, HEIGHT*8, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		
		// Create a random color based on the 4th, 8th
		// and 12th byte of our MD5 hash.
		// & 0xFF because byte arrays contain signed bytes
		Color color = new Color(hash[4] & 0xFF,
				hash[8] & 0xFF, hash[12] & 0xFF);
		
		// Fill our graphics with a white background
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
		
		// Set color to our randomly generated color
		graphics.setColor(color);
		
		// Fill our image based
		for (int y = 0; y < HEIGHT; y++) {
			// Loop four times per line
			for (int x = 0; x < WIDTH/2; x++) {
				// Bit shift hash[y] x bits, and 0x01 on it
				// to get a 0 or 1, fill two (mirrored) pixels based on that
				if ((hash[y] >> x & 0x01) == 1) {
					graphics.fillRect(x * 8, y * 8, WIDTH, HEIGHT);
					graphics.fillRect((WIDTH- x - 1) * 8, y * 8, WIDTH, HEIGHT);
				}
			}
		}

		return image;
	}
	
	public void saveImage(BufferedImage image, String outfile) {
		try {
			ImageIO.write(image, "png", new File("./" + outfile));
		} catch (IOException e) {
			System.out.println("Error opening file for writing");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length == 1) {
			Challenge174 avatar = new Challenge174(args[0]);
			avatar.saveImage(
					avatar.createImage(avatar.getHash()),
					args[0] + ".png");
		} else {
			System.out.println("Arg should be a nickname");
			System.exit(1);
		}
	}
}
