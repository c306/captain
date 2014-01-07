package com.c306.code.utils;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageSizeUtils {
	private static Logger logger = LoggerFactory.getLogger(ImageSizeUtils.class);
    
    /**
     * 计算图片单位尺寸
     * @param src
     * @return
     */
    public static int[] getUnitDimention(String src) {
        Image img;
        File f=null;
		try {
			f = new File(src);
			img = ImageIO.read(f);
            int w = img.getWidth(null);
            int h = img.getHeight(null);
	        return new int[]{calcUnitOfWith(w), calcUnitOfHeigh(h)};
		} catch (IOException e) {
			logger.error("get unit dimentions err. " + (f!=null?f.getAbsolutePath():""), e);
		}
		return null;
    }
    
    /**
     * return 96*n+15*(n-1);
     * @param w
     * @return
     */
	public static int calcPixOfWith(int w) {
		return SongConstans.WIDTH_INDEXIMAGE*w + SongConstans.SPACING_HORIZONTAL*(w-1);
	}
	/**
	 * return 92*n+14*(n-1);
	 * @param h
	 * @return
	 */
	public static int calcPixOfHeigh(int h) {
		return SongConstans.HEIGH_INDEXIMAGE*h + SongConstans.SPACING_VERTICAL*(h-1);
	}
	
	/**
	 * return Math.round((width + 15 + 96/2)/111);
	 * @param w
	 * @return
	 */
	public static int calcUnitOfWith(int w) {
		return Math.round((w + SongConstans.SPACING_HORIZONTAL + SongConstans.WIDTH_INDEXIMAGE/2)/(SongConstans.SPACING_HORIZONTAL + SongConstans.WIDTH_INDEXIMAGE));
	}
	/**
	 * return Math.round((height + 14 + 92/2)/106);
	 * @param h
	 * @return
	 */
	public static int calcUnitOfHeigh(int h) {
		return Math.round((h + SongConstans.SPACING_VERTICAL + SongConstans.HEIGH_INDEXIMAGE/2)/(SongConstans.SPACING_VERTICAL + SongConstans.HEIGH_INDEXIMAGE));
	}
    
    
}
