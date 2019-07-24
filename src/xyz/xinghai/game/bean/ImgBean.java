package xyz.xinghai.game.bean;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImgBean {
	public static BufferedImage backgroundImg;
	//ÓÎÏ·Æô¶¯Í¼
	public static BufferedImage startImg;
	//ÓÎÏ·ÔÝÍ£Í¼
	public static BufferedImage pauseImg;
	//ÓÎÏ·½áÊøÍ¼
	public static BufferedImage gameOverImg;
	//µÐ»úÍ¼
	public static BufferedImage enemyImg;
	public static BufferedImage beeImg;
	//×Óµ¯Í¼
	public static BufferedImage bulletImg;
	public static BufferedImage hero0Img;
	public static BufferedImage hero1Img;
	public static BufferedImage bigBeeImg;
	
	static {
		try {
			backgroundImg = ImageIO.read(ImgBean.class.getResource("background.png"));
			startImg = ImageIO.read(ImgBean.class.getResource("start.png"));
			pauseImg = ImageIO.read(ImgBean.class.getResource("pause.png"));
			gameOverImg = ImageIO.read(ImgBean.class.getResource("gameover.png"));
			enemyImg = ImageIO.read(ImgBean.class.getResource("airplane.png"));
			beeImg = ImageIO.read(ImgBean.class.getResource("bee.png"));
			bulletImg = ImageIO.read(ImgBean.class.getResource("bullet.png"));
			hero0Img = ImageIO.read(ImgBean.class.getResource("hero0.png"));
			hero1Img = ImageIO.read(ImgBean.class.getResource("hero1.png"));
			bigBeeImg = ImageIO.read(ImgBean.class.getResource("bigBee.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
