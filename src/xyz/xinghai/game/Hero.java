package xyz.xinghai.game;

import java.awt.image.BufferedImage;

import xyz.xinghai.game.bean.ImgBean;

public class Hero extends FlyingObject {
	private int life;
	private int doubleFire;
	private BufferedImage[] images;
	private int index;
	
	public Hero() {
		this.image = ImgBean.hero0Img;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
		x = 150;
		y = 400;
		life = 3;
		doubleFire = 0;
		images = new BufferedImage[]{ImgBean.hero0Img,ImgBean.hero1Img};
		index = 0;
	}
	
	@Override
	public void move() {
		image = images[index++/10%images.length];
	}
	
	@Override
	public boolean outOfBounds() {
		return false;
	}
	
	/**
	 * 根据doubleFire的值打不同数目的子弹
	 * @return 子弹数组
	 */
	public Bullet[] shoot() {
		int xSpeed = this.width / 4;
		int ySpeed = 20;
		
		if (doubleFire >= 100) {
			//三粒子弹 子弹的初始位置不同
			Bullet[] bs=new Bullet[3];
			bs[0] = new Bullet(this.x + 1 * xSpeed, this.y - ySpeed);
			bs[1] = new Bullet(this.x + 2 * xSpeed, this.y - ySpeed);
			bs[2] = new Bullet(this.x + 3 * xSpeed, this.y - ySpeed);
			doubleFire -= 2;
			return bs;
		} else if (doubleFire < 100 && doubleFire > 0) {
			Bullet[] bs=new Bullet[2];
			//两粒子弹
			bs[0]=new Bullet(this.x + 1* xSpeed, this.y - ySpeed);
			bs[1]=new Bullet(this.x + 3* xSpeed, this.y - ySpeed);
			doubleFire -= 2;
			return bs;
		} else{
			//一粒子弹
			Bullet[] bs = new Bullet[1];
			bs[0] = new Bullet(this.x + 2 * xSpeed, this.y - ySpeed);
			return bs;
		}
	}
	
	/**
	 * 飞机移到指定坐标去
	 * @param x x坐标
	 * @param y y坐标
	 */
	public void moveTo(int x, int y) {
		this.x=x-this.width/2;
		this.y=y-this.height/2;	
	}
	
	public void addLife(){
		life+=2;
		
	}
	
	public int getLife(){
		return life;
	}
	
	/**
	 * 加火力
	 */
	public void addDoubleFire(){
		doubleFire += 20;
	}
	
	public int getDoubleFire(){
		return doubleFire;
	}
	
	/**
	 * 生命减少
	 */
	public void subtractLife(){
		life--;
	}
	
	/**
	 * 回到最初的发子弹速度
	 */
	public void clealDoubleFire(){
		doubleFire = 0;
	}
	
	/**
	 * 是否发生碰撞
	 * @param other 其他继承自FlyingObject的飞行物
	 * @return
	 */
	public boolean hit(FlyingObject other) {
		int x1 = other.x - this.width/2;
		int x2 = other.x + this.width/2 + other.width;
		int y1 = other.y - this.height/2;
		int y2 = other.y + this.height/2 + other.height;
		int x = this.x + this.width/2;
		int y = this.y + this.height;
		return (x>x1&&x<x2) && (y>y1&&y<y2);
	}
}
