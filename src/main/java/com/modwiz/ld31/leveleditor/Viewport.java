package com.modwiz.ld31.leveleditor;

<<<<<<< HEAD
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Dimensions;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyAdapter;
=======
import java.awt.*;
>>>>>>> b7fd21ee64ddb74d4e0c4d1c954bb46b543a4ff8

/**
	Provides a view to the level that is being edited.
*/
public class Viewport extends Canvas {

	private Cursor2D cursor;
	private float camX, camY;
	private final float camSpeed;
	
	public Viewport(int viewPortWidth, int viewPortHeight) {
		cursor = new Cursor2D();
		camX = 0;
		camY = 0;
		camSpeed = 40;
		setPreferredSize(new Dimension(viewPortWidth, viewPortHeight));
		setSize(viewPortWidth, viewPortHeight);
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
	}
	
	public void setupListeners(JFrame frame) {
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				switch(e.getKeyCode()) {
					case KeyEvent.VK_W:
						camY -= camSpeed;
						break;
					case KeyEvent.VK_A:
						camX -= camSpeed;
						break;
					case KeyEvent.VK_S:
						camY += camSpeed;
						break;
					case KeyEvent.VK_D:
						camX += camSpeed;
						break;
				}
			}
		});
	}
}