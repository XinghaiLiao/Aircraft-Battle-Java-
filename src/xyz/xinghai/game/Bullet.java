package xyz.xinghai.game;

import xyz.xinghai.game.bean.ImgBean;

/**
 * 子弹类
 *
 */
public class Bullet extends FlyingObject {
	//子弹速度
	private int speed = 3;
	
	/**
	 * @param x 游戏界面上的x坐标
	 * @param y 游戏界面上的y坐标
	 */
	public Bullet(int x, int y) {
		this.image = ImgBean.bulletImg;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
		this.x = x;
		this.y = y;
	}

	@Override
	public void move() {
		this.y -= speed;
	}

	@Override
	public boolean outOfBounds() {
		return this.y <= -this.height;
	}
}
