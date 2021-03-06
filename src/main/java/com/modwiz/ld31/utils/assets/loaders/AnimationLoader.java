package com.modwiz.ld31.utils.assets.loaders;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.modwiz.ld31.utils.annotations.Nullable;
import com.modwiz.ld31.utils.assets.AssetLoader;
import com.modwiz.ld31.entities.draw.Animation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.ArrayList;
import java.awt.Graphics;
import java.util.Set;

/**
 * The class for loading {@link com.modwiz.ld31.entities.draw.Animation} class through
 * text files. The purpose of the text files is to store the information about the
 * animation that one is about to load. 
 */
public class AnimationLoader implements ILoader<Animation> {
	
	private Cache<String, Animation> cache = CacheBuilder.newBuilder().build();
	
	 /**
     * {@inheritDoc}
     */
	 @Override
	public Animation getContent(final InputStream stream, final String key) {
		if (key==null || key.equals("null")) {
			return null;
		}
		try {
            Animation animatation = cache.get(key, new Callable<Animation>() {
                @Override
                @Nullable
                public Animation call() throws Exception {
                    Animation anim = null;
					BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
					BufferedImage[][] tracks = null;
					String nextLine;
					while((nextLine = reader.readLine()) != null) {
						if (nextLine.startsWith("amount")) {
							String[] tokens = nextLine.split(" ");
							tracks = new BufferedImage[Integer.parseInt(tokens[1])][];
							continue;
						}
						if (!nextLine.startsWith("#")) { // otherwise a comment
							String[] tokens = nextLine.split(" ");
							BufferedImage entireImage = AssetLoader.getSingleton().loadAsset(BufferedImage.class, tokens[1]);
							int imgNum = Integer.parseInt(tokens[2]);
							int imgWidth = Integer.parseInt(tokens[3]);
							int imgHeight = Integer.parseInt(tokens[4]);
							BufferedImage[] track = new BufferedImage[imgNum];
							for (int i=0; i<imgNum; i++) {
								track[i] = entireImage.getSubimage(i * imgWidth, 0, imgWidth, imgHeight);
							}
							if (tokens[5].equals("true")) {
								for (int i=0; i<track.length; i++) {
									track[i] = flipImage(track[i], true);
								}
							}
							tracks[Integer.parseInt(tokens[0])] = track;
						}
					}
					anim = new Animation(tracks, 7);
					System.out.println(anim);
                    return anim;
                }
            });
			return animatation;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	public Set<String> getKeys() {
        return cache.asMap().keySet();
    }
	
	private static BufferedImage flipImage(BufferedImage img, boolean horiz) {
		BufferedImage flipped = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TRANSLUCENT);
		Graphics g = flipped.getGraphics();
		if (horiz) {
			for (int i=0; i<img.getWidth(); i++) {
				g.drawImage(img.getSubimage(img.getWidth() - i - 1, 0, 1, img.getHeight()), i, 0, null);
			}
		} else {
			for (int i=0; i<img.getHeight(); i++) {
				g.drawImage(img.getSubimage(0, img.getHeight() - i - 1, img.getWidth(), 1), 0, i, null);
			}
		}
		return flipped;
	}
}