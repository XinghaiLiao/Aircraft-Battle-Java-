package xyz.xinghai.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import xyz.xinghai.game.bean.GameStateBean;
import xyz.xinghai.game.bean.ImgBean;

/**
 * 主程序类
 * @author 15321
 *
 */
public class AircraftBattle extends JPanel {
	private static final long serialVersionUID = 1L;
	//游戏界面宽度
	public static final int WIDTH = 400;
	//游戏界面高度
	public static final int HEIGHT = 645;
	
	private int state = GameStateBean.START;
	//主飞机
	private Hero hero = new Hero();
	//敌机数组
	private FlyingObject[] flyings = {};
	//子弹数组
	private Bullet[] bullets = {};
	//得分
	private int score = 0;
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(ImgBean.backgroundImg, 0, 0, null);
		paintHero(g);
		paintFlyingObjects(g);
		paintBullets(g);
		paintScoreAndLife(g);
		patintState(g);
	}
	
	/**
	 * 画得分和生命
	 * @param g 画笔
	 */
	private void paintScoreAndLife(Graphics g) {
		g.setColor(new Color(0xFF0000));
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
		g.drawString("SCORE:" + score, 10, 25);
		g.drawString("LIFE:"+ hero.getLife(), 10, 50);
		g.drawString("DoubleFire:" + hero.getDoubleFire(), 10, 75);
	}
	
	/**
	 * 画主飞机
	 * @param g 画笔
	 */
	private void paintHero(Graphics g) {
		g.drawImage(hero.image, hero.x, hero.y, null);
	}
	
	/**
	 * 画屏幕中的敌机等飞行物体
	 * @param g 画笔
	 */
	private void paintFlyingObjects(Graphics g){
		for(FlyingObject f : flyings)
			g.drawImage(f.image, f.x, f.y, null);
	}
	
	/**
	 * 画子弹
	 * @param g 画笔
	 */
	private void paintBullets(Graphics g){
		for(Bullet b : bullets)
			g.drawImage(b.image, b.x, b.y, null);
	}
	
	/**
	 * 绘制游戏状态 
	 * @param g 画笔
	 */
	private void patintState(Graphics g) {
    	switch(state){
    		case GameStateBean.START:
    			g.drawImage(ImgBean.startImg, 0, 0, null);
    			break;
    		case GameStateBean.PAUSE:
    			g.drawImage(ImgBean.pauseImg, 0, 0, null);
    			break;
    		case GameStateBean.GAMEOVER:
    			g.drawImage(ImgBean.gameOverImg, 0, 0, null);
    	}
    }
	
	/**
	 * 生成敌机
	 * @return 敌机
	 */
	private FlyingObject nextOne() {
		Random rand = new Random();
		int type = rand.nextInt(20);
		if(type < 4) {
			return new BigBee();
		} else if (type == 5) {
			return new Bee();
		} else {
			return new EnemyPlane();
		}
	}
	
	//敌机入场的index
	int flyEnteredIndex = 0;
	/**
	 * 敌机入场的action
	 */
	private void enterAction() {
		flyEnteredIndex++;
		if(flyEnteredIndex % 40 ==0){
			FlyingObject obj = nextOne();
			//更新敌机数组	
			flyings = Arrays.copyOf(flyings, flyings.length+1);
			flyings[flyings.length-1] = obj;
		}
	}
	
	//子弹入场的index
	int shootIndex = 0;
	/**
	 * 主飞机发射子弹的Action 主飞机返回子弹数组 把返回的数组加到游戏子弹数组即可
	 */
	private void shootAction(){
		shootIndex++;
		if (shootIndex % 30 == 0) {
		   Bullet[] bs = hero.shoot();
		   //更新子弹数组
		   bullets = Arrays.copyOf(bullets, bullets.length+bs.length);
		   System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length);
		} 
	}
	
	/**
	 *飞行物移动的action 调用各自的移动方法即可
	 */
	private void moveAction(){
		hero.move();
		for(FlyingObject f : flyings) 
			f.move();
		for(Bullet b : bullets)
			b.move();
	}
	
	/**
	 * 飞行物飞出界面的action 就是把飞出界面的飞行物从数组中去掉
	 */
	private void outOfBoundsAction() {
		int index = 0;
		FlyingObject[] flyingLives = new FlyingObject[flyings.length];
		for(FlyingObject f : flyings) 
			if(!f.outOfBounds()) 
				flyingLives[index++] = f;
		flyings=Arrays.copyOf(flyingLives, index);
		index = 0;
		Bullet[] bulletLives = new Bullet[bullets.length];
		for(Bullet b : bullets) 
			if(!b.outOfBounds()) 
				bulletLives[index++] = b;
		bullets=Arrays.copyOf(bulletLives, index);
	}
	
	int j = 0;
	/**
	 * 碰撞的action
	 */
	private void collisionAction() {
		for(int i=0;i < bullets.length;i++){
			collision(bullets[i],i);
		}
	}
	
	/**
	 * 碰撞
	 * @param b 子弹
	 * @param bu 碰撞的子弹在子弹数组中的index
	 */
	private void collision(Bullet b, int bu) {
		int index = -1;
		
		for(int i=0;i<flyings.length;i++){
			FlyingObject f = flyings[i];
			if (f.shootBy(b)) {
				index = i;
				break;
			} 
		}
		
		if(index != -1) {
			FlyingObject one = flyings[index];
			
			if(one instanceof EnemyPlane){
				Enemy e = (Enemy)one;
				score += e.getScore();
				FlyingObject t = flyings[index];
				flyings[index] = flyings[flyings.length-1];
				flyings[flyings.length-1] = t;
				flyings=Arrays.copyOf(flyings, flyings.length-1);//去掉被子弹撞上的敌人
			}
			if(one instanceof BigBee){//打五下才会挂
				j++;			
				if(j==5){
					Enemy e = (Enemy)one;
					score += e.getScore();
					j = 0;
					FlyingObject t = flyings[index];
					flyings[index] = flyings[flyings.length-1];
					flyings[flyings.length-1] = t;
					flyings = Arrays.copyOf(flyings, flyings.length-1);//去掉被子弹撞上的敌人
				}
			}
			if(one instanceof Award){
				Award a = (Award)one;
				int type = a.getType();
				switch(type){
					case Award.DOUBLE_FIRE://奖励为火力值
						hero.addDoubleFire();
						break;
					case Award.LIFE://奖励为生命
						hero.addLife();
						break;
				}
				FlyingObject t = flyings[index];
				flyings[index] = flyings[flyings.length-1];
				flyings[flyings.length-1] = t;
				flyings = Arrays.copyOf(flyings, flyings.length-1);//去掉被子弹撞上的敌人
			}
			
			Bullet tBullet = bullets[bu];
			bullets[bu] = bullets[bullets.length-1];
			bullets[bullets.length-1] = tBullet;
			bullets=Arrays.copyOf(bullets, bullets.length-1);//去掉子弹
		}
	}
	
	/**
	 * 检查游戏是否结束
	 */
	public void checkGameOverAction(){
		if(GameOver()){
			state = GameStateBean.GAMEOVER;
		}
	}
	
	private boolean GameOver() {
		for(int i=0; i<flyings.length; i++){
			FlyingObject f = flyings[i];
			if(hero.hit(f)){
				hero.subtractLife();
				hero.clealDoubleFire();
				FlyingObject t = flyings[i];//将被撞敌人与最后的一个元素交换
				flyings[i] = flyings[flyings.length-1];
				flyings[flyings.length-1] = t;
				flyings = Arrays.copyOf(flyings, flyings.length-1);//去掉被子弹撞上的敌人
			}
		}
		return hero.getLife() <= 0;//命数小于等于0结束
	}
	
	/**
	 * 启动程序的action
	 */
	public void action(){
		MouseAdapter l = new MouseAdapter() {
			//重写鼠标移动事件
			public void mouseMoved(MouseEvent e){
				if(state == GameStateBean.RUNNING){
					hero.moveTo(e.getX(),e.getY());
				}
			}
			//鼠标点击事件
			public void mouseClicked(MouseEvent e){
				switch(state){
					case GameStateBean.START:
						state= GameStateBean.RUNNING;
						break;
					case GameStateBean.GAMEOVER:
						state = GameStateBean.START;
						//清理现场
						score = 0;
						hero = new Hero();
						flyings = new FlyingObject[0];
						bullets = new Bullet[0];
						break;
				}
			}
			//鼠标移除
			public void mouseExited(MouseEvent e){
				if(state == GameStateBean.RUNNING){
					state = GameStateBean.PAUSE;
				}
			}
			//鼠标移入
			public void mouseEntered(MouseEvent e){
				if(state == GameStateBean.PAUSE){
					state = GameStateBean.RUNNING;
				}
			}
		};
		this.addMouseListener(l);//处理鼠标操作事件
		this.addMouseMotionListener(l);//鼠标的滑动
		Timer timer = new Timer();//定时器
		int intervel = 10;//定时间隔（以毫秒为单位）
		timer.schedule(new TimerTask() {
			public void run(){
				if(state == GameStateBean.RUNNING){
					enterAction();//敌人入场
					moveAction();//敌人移动
					shootAction();//子弹入场
					collisionAction();//子弹与敌人相撞
					outOfBoundsAction();//删除越界的敌人和子弹
					checkGameOverAction();//敌人和英雄机相撞					
				}
				repaint();//重新绘制界面
			}
		}, intervel, intervel);
	}
	
	public static void main(String[] args) {
		JFrame gameFrame = new JFrame();
		gameFrame.setTitle("AircraftBattle");
		AircraftBattle game = new AircraftBattle();
		gameFrame.getContentPane().add(game);
		gameFrame.setSize(new Dimension(WIDTH, HEIGHT));
		gameFrame.setAlwaysOnTop(true);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setVisible(true);
		
		game.action();
	}
}
