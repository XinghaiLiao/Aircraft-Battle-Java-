package xyz.xinghai.game;

import xyz.xinghai.game.bean.ImgBean;

/**
 * �ӵ���
 *
 */
public class Bullet extends FlyingObject {
	//�ӵ��ٶ�
	private int speed = 3;
	
	/**
	 * @param x ��Ϸ�����ϵ�x����
	 * @param y ��Ϸ�����ϵ�y����
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
