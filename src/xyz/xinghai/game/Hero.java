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
	 * ����doubleFire��ֵ��ͬ��Ŀ���ӵ�
	 * @return �ӵ�����
	 */
	public Bullet[] shoot() {
		int xSpeed = this.width / 4;
		int ySpeed = 20;
		
		if (doubleFire >= 100) {
			//�����ӵ� �ӵ��ĳ�ʼλ�ò�ͬ
			Bullet[] bs=new Bullet[3];
			bs[0] = new Bullet(this.x + 1 * xSpeed, this.y - ySpeed);
			bs[1] = new Bullet(this.x + 2 * xSpeed, this.y - ySpeed);
			bs[2] = new Bullet(this.x + 3 * xSpeed, this.y - ySpeed);
			doubleFire -= 2;
			return bs;
		} else if (doubleFire < 100 && doubleFire > 0) {
			Bullet[] bs=new Bullet[2];
			//�����ӵ�
			bs[0]=new Bullet(this.x + 1* xSpeed, this.y - ySpeed);
			bs[1]=new Bullet(this.x + 3* xSpeed, this.y - ySpeed);
			doubleFire -= 2;
			return bs;
		} else{
			//һ���ӵ�
			Bullet[] bs = new Bullet[1];
			bs[0] = new Bullet(this.x + 2 * xSpeed, this.y - ySpeed);
			return bs;
		}
	}
	
	/**
	 * �ɻ��Ƶ�ָ������ȥ
	 * @param x x����
	 * @param y y����
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
	 * �ӻ���
	 */
	public void addDoubleFire(){
		doubleFire += 20;
	}
	
	public int getDoubleFire(){
		return doubleFire;
	}
	
	/**
	 * ��������
	 */
	public void subtractLife(){
		life--;
	}
	
	/**
	 * �ص�����ķ��ӵ��ٶ�
	 */
	public void clealDoubleFire(){
		doubleFire = 0;
	}
	
	/**
	 * �Ƿ�����ײ
	 * @param other �����̳���FlyingObject�ķ�����
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
