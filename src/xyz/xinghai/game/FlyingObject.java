package xyz.xinghai.game;

import java.awt.image.BufferedImage;

/**
 * 飞行物体的抽象类
 */
public abstract class FlyingObject {
	//飞行物的图片
	protected BufferedImage image; 
	//飞行物的宽度
	protected int width;
	//飞行物的高度
	protected int height;
	//飞行物的x坐标
	protected int x;
	//飞行物的y坐标
	protected int y;
	
	//飞行物的移动
	public abstract void move();
	
	//是否飞出游戏界面
	public abstract boolean outOfBounds();
	
	//判断是否被击中
	public boolean shootBy(Bullet bullet) {
		int x = bullet.x;
		int y = bullet.y;
		
		return (x > this.x && x < (this.x + this.width)) && (y > this.y && y < (this.y + this.height));
	}
}
