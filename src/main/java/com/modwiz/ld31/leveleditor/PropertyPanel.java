package com.modwiz.ld31.leveleditor;

import com.modwiz.ld31.entities.*;
import com.modwiz.ld31.entities.draw.Animation;

import com.modwiz.ld31.utils.assets.AssetLoader;
import com.modwiz.ld31.utils.assets.loaders.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;


/**
	Edit the properties of an object from the property panel.
	Loading an object changes what the properties are, depending on 
	the type of the game entity.
*/
public class PropertyPanel extends JPanel {
	
	/* Indices for common text fields */
	private final int
		POSITION = 0,
		SIZE = 1,
		NAME = 2,
		STATIC = 0,
		CAN_COLLIDE = 1,
		IMAGE = 3,
		ANIMATION = 4,
		PATROL_PATH = 5,
		WEAPON = 6,
		TEXT_LABEL = 7,
		FONT_SIZE = 8,
		MESSAGE_LIST = 9,
		DIMENSION_TO = 10,
		HEALTH = 11
	;
	
	private JViewport view;
	private JTextField[] fields;
	private JCheckBox[] checkboxes;
	private JPanel fieldsPanel;
	private GameObject editing;
	private JButton imageBrowse, animationBrowse;
	private LevelEditorMain lem;
	private JButton updateAll;
	
	public PropertyPanel(final LevelEditorMain lem) {
		this.lem = lem;
		editing = null;
		setLayout(new GridLayout(8, 2, 10, 10));
		checkboxes = new JCheckBox[2];
		fields = new JTextField[12];
		
		fields[POSITION] = new JTextField(6);
		fields[POSITION].addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setPosition();  } });
		fields[SIZE] = new JTextField(6);
		fields[SIZE].addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setSize();  } });
		fields[NAME] = new JTextField(6);
		fields[NAME].addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setName();  } });
		checkboxes[STATIC] = new JCheckBox("Static");
		checkboxes[STATIC].addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setStatic();  } });
		checkboxes[CAN_COLLIDE] = new JCheckBox("Can collide");
		checkboxes[CAN_COLLIDE].addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setCanCollide();  } });
		fields[IMAGE] = new JTextField(6);
		fields[IMAGE].addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setImage();  } });
		fields[ANIMATION] = new JTextField(6);
		fields[ANIMATION].addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setAnimation();  } });
		fields[PATROL_PATH] = new JTextField(6);
		fields[PATROL_PATH].addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setPatrolPath();  } });
		fields[WEAPON] = new JTextField(6);
		fields[WEAPON].addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setWeapon();  } });
		fields[TEXT_LABEL] = new JTextField(6);
		fields[TEXT_LABEL].addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setTextLabel();  } });
		fields[FONT_SIZE] = new JTextField(6);
		fields[FONT_SIZE].addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setFontSize();  } });
		fields[MESSAGE_LIST] = new JTextField(6);
		fields[MESSAGE_LIST].addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setMessageList();  } });
		fields[DIMENSION_TO] = new JTextField(6);
		fields[DIMENSION_TO].addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setDimensionTo();  } });
		fields[HEALTH] = new JTextField(6);
		fields[HEALTH].addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setHealth();  } });
		
		imageBrowse = new JButton("Browse");
		animationBrowse = new JButton("Browse");
		updateAll = new JButton("Update");
		
		imageBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openImageDialog();
			}
		});
		
		animationBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openAnimationDialog();
			}
		});
	}
	
	class ImageDialog extends JDialog implements ActionListener {
		
		private JList<String> list;
		
		public ImageDialog() {
			super(lem);
			list = new JList<String>(
				(String[])((BufferedImageLoader)AssetLoader.getSingleton().getLoader(BufferedImage.class)).getKeys().toArray(new String[0])
			);
			setLayout(new BorderLayout());
			JScrollPane pane = new JScrollPane(
				list,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
			);
			add(pane, BorderLayout.CENTER);
			JButton button = new JButton("Accept");
			button.addActionListener(this);
			add(button, BorderLayout.SOUTH);
			setVisible(true);
			pack();
			setLocationRelativeTo(lem);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (editing!=null) {
				String str = list.getSelectedValue();
				if (str!=null) {
					fields[IMAGE].setText(str);
					setImage();
				}
			}
			dispose();
		}
	}
	
	class AnimationDialog extends JDialog implements ActionListener {
		
		private JList<String> list;
		
		public AnimationDialog() {
			super(lem);
			list = new JList<String>(
				(String[])((AnimationLoader)AssetLoader.getSingleton().getLoader(Animation.class)).getKeys().toArray(new String[0])
			);
			setLayout(new BorderLayout());
			JScrollPane pane = new JScrollPane(
				list,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
			);
			add(pane, BorderLayout.CENTER);
			JButton button = new JButton("Accept");
			button.addActionListener(this);
			add(button, BorderLayout.SOUTH);
			setVisible(true);
			pack();
			setLocationRelativeTo(lem);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (editing!=null) {
				String str = list.getSelectedValue();
				if (str!=null) {
					fields[ANIMATION].setText(str);
					setAnimation();
				}
			}
			dispose();
		}
	}
	
	private void openImageDialog() {
		ImageDialog log = new ImageDialog();
	}
	
	private void setPosition() {
		try {
			String[] pos = fields[POSITION].getText().split(Pattern.quote(","));
			editing.setX(Float.parseFloat(pos[0]));
			editing.setY(Float.parseFloat(pos[1]));
		} catch(Exception e) {
			fields[POSITION].setText("" + editing.getX() + "," + editing.getY());
		}
		lem.repaintViewport();
	}
	
	private void setDimensionTo() {
		((DimensionChangeBlock)editing).setJumpingToDimension(fields[DIMENSION_TO].getText());
	}
	
	private void addDimensionTo() {
		add(new JLabel("Jumps to Dimension"));
		add(fields[DIMENSION_TO]);
		try {
			fields[DIMENSION_TO].setText(((DimensionChangeBlock)editing).getJumpingToDimension());
		} catch(Exception e) { }
	}
	
	private void setMessageList() {
		try {
			String[] messages = fields[MESSAGE_LIST].getText().split(Pattern.quote(","));
			((MessageBlock)editing).setMessages(messages);
		} catch(Exception e) {
			fields[MESSAGE_LIST].setText("" + editing.getX() + "," + editing.getY());
		}
		lem.repaintViewport();
	}
	
	private void setPatrolPath() {
		try {
			String[] path = fields[PATROL_PATH].getText().split(Pattern.quote(","));
			((Enemy)editing).setPatrolPath(Integer.parseInt(path[0]), Integer.parseInt(path[1]));
		} catch(Exception e) {
			fields[PATROL_PATH].setText("" + editing.getX() + "," + editing.getX());
		}
		lem.repaintViewport();
	}
	
	private void setSize() {
		try {
			String[] size = fields[SIZE].getText().split(Pattern.quote(","));
			((GameBlock)editing).setWidth(Float.parseFloat(size[0]));
			((GameBlock)editing).setHeight(Float.parseFloat(size[1]));
		} catch(Exception e) {
			fields[SIZE].setText("" + ((GameBlock)editing).getWidth() + "," + ((GameBlock)editing).getHeight());
		}
		lem.repaintViewport();
	}
	
	private void setName() {
		editing.setName(fields[NAME].getText());
		lem.repaintViewport();
	}
	
	public void setImage() {
		try {
			((GameBlock)editing).setImageString(fields[IMAGE].getText());
		} catch(Exception e) {
			((GameBlock)editing).noImage();
		}
		lem.repaintViewport();
	}
	
	private void addHealth() {
		add(new JLabel("Health"));
		add(fields[HEALTH]);
		fields[HEALTH].setText("" + ((Creature)editing).getHealth());
	}
	
	private void setHealth() {
		try {
			((Creature)editing).setHealth(Double.parseDouble(fields[HEALTH].getText()));
			((Creature)editing).setMaxHealth(Double.parseDouble(fields[HEALTH].getText()));
		} catch(Exception e) {
			
		}
	}
	
	private void openAnimationDialog() {
		AnimationDialog log = new AnimationDialog();
	}
	
	private void setAnimation() {
		try {
			((Creature)editing).setAnimationString(fields[ANIMATION].getText());
		} catch(Exception e) {
			((Creature)editing).noAnimation();
		}
		lem.repaintViewport();
	}
	
	private void setTextLabel() {
		((TextBlock)editing).setTextLabel(fields[TEXT_LABEL].getText());
		lem.repaintViewport();
	}
	
	private void setFontSize() {
		try {
			((TextBlock)editing).setFontSize(
				Integer.parseInt(fields[FONT_SIZE].getText())
			);
		} catch(Exception e) {
			
		}
		lem.repaintViewport();
	}
	
	private void setStatic() {
		((GameBlock)editing).setStaticBlock(checkboxes[STATIC].isSelected());
		lem.repaintViewport();
	}
	
	private void setCanCollide() {
		((GameBlock)editing).setCanCollide(checkboxes[CAN_COLLIDE].isSelected());
		lem.repaintViewport();
	}
	
	private void addMessageList() {
		add(new JLabel("Message List (msg1,msg2,...)"));
		add(fields[MESSAGE_LIST]);
	}
	
	public void loadObject(GameObject entity) {
		this.editing = entity;
		//Add more fields to this
		removeAll();
		if (entity instanceof Enemy) {
			addName();
			addPosition();
			addSize();
			addAnimation();
			addStatic();
			add(new JLabel());
			addHealth();
			addWeapon();
			addPatrolPath();
		} else if (entity instanceof Creature) {
			addName();
			addPosition();
			addSize();
			addAnimation();
			addStatic();
			add(new JLabel());
			addHealth();
			addWeapon();
		}  else if (entity instanceof TextBlock) {
			addName();
			addPosition();
			addSize();
			addTextLabel();
			addFontSize();
		}  else if (entity instanceof MessageBlock) {
			addName();
			addPosition();
			addSize();
			addImage();
			addMessageList();
		}   else if (entity instanceof DNARepairCell) {
			addName();
			addPosition();
		}   else if (entity instanceof RadiationSucker) {
			addName();
			addPosition();
		}  else if (entity instanceof DimensionChangeBlock) {
			addName();
			addPosition();
			addSize();
			addImage();
			addDimensionTo();
		}  else if (entity instanceof GameBlock) {
			addName();
			addPosition();
			addSize();
			addImage();
			addStatic();
			addCanCollide();
		} 
		lem.revalidate();
		lem.repaintViewport();
	}
	
	private void addPosition() {
		add(new JLabel("Position (x,y)"));
		add(fields[POSITION]);
		fields[POSITION].setText("" + editing.getX() + "," + editing.getY());
	}
	
	private void addPatrolPath() {
		add(new JLabel("Patrol Path (minX,manX)"));
		add(fields[PATROL_PATH]);
		fields[PATROL_PATH].setText("" + ((Enemy)editing).getInitialPoint() + "," + ((Enemy)editing).getFinalPoint());
	}
	
	public void updatePositionField() {
		fields[POSITION].setText("" + editing.getX() + "," + editing.getY());
	}
	
	private void addSize() {
		add(new JLabel("Size (width,height)"));
		add(fields[SIZE]);
		fields[SIZE].setText("" + ((GameBlock)editing).getWidth() + "," + ((GameBlock)editing).getHeight());
	}
	
	private void addTextLabel() {
		add(new JLabel("Text Label"));
		add(fields[TEXT_LABEL]);
		fields[TEXT_LABEL].setText(((TextBlock)editing).getTextLabel());
	}
	
	private void addFontSize() {
		add(new JLabel("Font Size"));
		add(fields[FONT_SIZE]);
		fields[FONT_SIZE].setText("" + ((TextBlock)editing).getFontSize());
	}
	
	public void updateSizeField() {
		fields[SIZE].setText("" + ((GameBlock)editing).getWidth() + "," + ((GameBlock)editing).getHeight());
	}
	
	public void updateFields() {
		updatePositionField();
		updateSizeField();
	}
	
	private void addName() {
		add(new JLabel("Name"));
		add(fields[NAME]);
		fields[NAME].setText(editing.getName());
	}
	
	private void addStatic() {
		add(checkboxes[STATIC]);
		checkboxes[STATIC].setSelected(((GameBlock)editing).isStaticBlock());
	}
	
	private void addCanCollide() {
		add(checkboxes[CAN_COLLIDE]);
		checkboxes[CAN_COLLIDE].setSelected(((GameBlock)editing).getCanCollide());
	}
	
	private void addWeapon() {
		add(new JLabel("Weapon (Range,Damage,Cooldown)"));
		add(fields[WEAPON]);
		fields[WEAPON].setText( "" + 
			((Enemy)editing).getWeapon().getRange() + "," +
			((Enemy)editing).getWeapon().getDamage() + "," + 
			((Enemy)editing).getWeapon().getCooldown()
		);
	}
	
	private void setWeapon() {
		try {
			String[] wep = fields[WEAPON].getText().split(Pattern.quote(","));
			((Enemy)editing).setWeapon(new Weapon(
				((Enemy)editing),
				Double.parseDouble(wep[0]),
				Double.parseDouble(wep[1]),
				Integer.parseInt(wep[2])
			));
		} catch(Exception e) {
			//Nothing
		}	
	}
	
	private void addImage() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 2));
		p.add(new JLabel("Image"));
		p.add(imageBrowse);
		add(p);
		add(fields[IMAGE]);
		if (((GameBlock)editing).getImageForString() != null) {
			fields[IMAGE].setText(((GameBlock)editing).getImageForString());
		}
	}
	
	private void addAnimation() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 2));
		p.add(new JLabel("Animation"));
		p.add(animationBrowse);
		add(p);
		add(fields[ANIMATION]);
		if (((Creature)editing).getAnimationString() != null) {
			fields[ANIMATION].setText(((Creature)editing).getAnimationString());
		}	
	}
	
	public void clear() {
		editing = null;
		removeAll();
		revalidate();
	}
}