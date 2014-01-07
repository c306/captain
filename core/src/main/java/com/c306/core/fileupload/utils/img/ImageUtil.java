package com.c306.core.fileupload.utils.img;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;



public class ImageUtil {
    public final static String ACTION_TYPE_CHANGESIZE = "-c";
    public final static String ACTION_TYPE_ADDWATERMARK = "-a";

    public final static String IMAGE_TYPE_BMP = "BMP";
    public final static String IMAGE_TYPE_GIF = "GIF";
    public final static String IMAGE_TYPE_JPEG = "JPEG";
    public final static String IMAGE_TYPE_PNG = "PNG";
    public final static String IMAGE_TYPE_PNM = "PNM";
    public final static String IMAGE_TYPE_TIFF = "TIFF";

    public final static String[] IMAGE_TYPE_BMP_EXTENSION = {
            "bmp", "dib", "rle"};
    public final static String[] IMAGE_TYPE_GIF_EXTENSION = {
            "gif"};
    public final static String[] IMAGE_TYPE_JPEG_EXTENSION = {
            "jpg", "jpeg", "jpe", "jif", "jfif"};
    public final static String[] IMAGE_TYPE_PNG_EXTENSION = {
            "png"};
    public final static String[] IMAGE_TYPE_PNM_EXTENSION = {
            "pnm"};
    public final static String[] IMAGE_TYPE_TIFF_EXTENSION = {
            "tif", "tiff", "xif"};

    private static Map imageExtension = null;

    public static String[] supportExtension = null;

    static {
        imageExtension = new HashMap();
        //imageExtension.put(IMAGE_TYPE_BMP_EXTENSION, IMAGE_TYPE_BMP);
        imageExtension.put(IMAGE_TYPE_GIF_EXTENSION, IMAGE_TYPE_GIF);
        imageExtension.put(IMAGE_TYPE_JPEG_EXTENSION, IMAGE_TYPE_JPEG);
        imageExtension.put(IMAGE_TYPE_PNG_EXTENSION, IMAGE_TYPE_PNG);
        //imageExtension.put(IMAGE_TYPE_PNM_EXTENSION, IMAGE_TYPE_PNM);
        //imageExtension.put(IMAGE_TYPE_TIFF_EXTENSION, IMAGE_TYPE_TIFF);

        Iterator it = imageExtension.keySet().iterator();
        java.util.List list = new ArrayList();
        while (it.hasNext()) {
            String[] buf = (String[]) it.next();
            for (int i = 0; i < buf.length; i++) {
                list.add(buf[i]);
            }
        }
        Object[] objs = list.toArray();
        supportExtension = new String[objs.length];
        for (int i = 0; i < objs.length; i++) {
            supportExtension[i] = (String) objs[i];
        }
    }

    public static void main(String[] args) {
        ImageUtil iu = new ImageUtil();

        String src="F:/temp/tt/1234.jpg", dest="F:/temp/tt/12346.jpg";
        
        changeSize("jpeg", new File(src), new File(dest), 200, 150);

//            iu.batchChangeSize(new File(args[1]), args[2],
//                               Integer.parseInt(args[3]),
//                               Integer.parseInt(args[4]));
//            iu.batchAddWaterMark(new File(args[1]), new File(args[2]),
//                                 Integer.parseInt(args[3]));
    }

    public String renameTo(String fileName, String attach) {
        if (fileName == null || attach == null) {
            return fileName;
        }
        /*
             int pos = fileName.lastIndexOf(".");
             if (pos >= 0) {
          return fileName.substring(0, pos) + attach + fileName.substring(pos);
             }

             return fileName + attach;
         */
        return attach + fileName;
    }

    /**
     * 根据文件后缀返回相应的图片类型
     *
     * @param fileName 文件名
     * @return String
     */
    public static String getImageTypeByFileName(String fileName) {
        String imageType = null;

        int pos = fileName.lastIndexOf(".");
        if (pos < 0) {
            return imageType;
        }

        String extension = fileName.substring(pos + 1);

        Iterator it = imageExtension.keySet().iterator();
        while (it.hasNext()) {
            String[] temp = (String[]) it.next();
            for (int i = 0; i < temp.length; i++) {
                if (extension.equalsIgnoreCase(temp[i])) {
                    return (String)imageExtension.get(temp);
                }
            }
        }

        return imageType;
    }

    /**
     * 改变图片大小
     *
     * @param imageType 图片类型,支持的图片类型在常量里定义
     * @param src File 源文件
     * @param dest File 目的文件
     * @param width int 宽
     * @param height int 高
     * @throws Exception
     */
    public static void changeSize(String imageType, File src, File dest,
                           int width, int height) {
        try {
            if (imageType == null || src == null || dest == null || width <= 0 ||
                height <= 0) {
                throw new Exception("changeSize() parameter is error");
            }
            Image img = ImageIO.read(src);

            int w = img.getWidth(null);
            int h = img.getHeight(null);

            if (w <= width && h <= height) {
            } else {
                if (w == h) {
                    if (width >= height) {
                        h = height;
                        w = h;
                    } else {
                        w = width;
                        h = w;
                    }
                } else if (w > h) {
                    float scale = (float) h / w;
                    w = width;
                    h = (int) (w * scale);
                } else {
                    float scale = (float) w / h;
                    h = height;
                    w = (int) (h * scale);
                }
            }

            img = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);

            BufferedImage bi = new BufferedImage(w, h,
                                                 BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bi.createGraphics();
            g.setBackground(Color.WHITE);
            g.fillRect(0, 0, w, h);

            bi.getGraphics().drawImage(img, 0, 0, w, h, null);

            if (!imageType.equalsIgnoreCase(IMAGE_TYPE_GIF)) {
                ImageIO.write(bi, imageType, dest);
            } else {
                AnimatedGifEncoder age = new AnimatedGifEncoder();
                age.setQuality(1);
                age.start(dest.toString());
                age.addFrame(bi);
                age.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void changeSize(String imageType, String src, String dest, int width, int height) {
    	changeSize(imageType, new File(src), new File(dest), width, height);
    }
    
    public static void changeSize(String src, String dest, int width, int height) {
    	changeSize(getImageTypeByFileName(src), new File(src), new File(dest), width, height);
    }
    
    public static void changeSize(File src, File dest,
    		int width, int height) {
    	changeSize(getImageTypeByFileName(src.getName()), src, dest, width, height);
    }

    /**
     * 批量修改图片大小
     *
     * @param dir 文件或目录
     * @param renameAttachValue 批量改名所加的后缀
     * @param width 宽
     * @param height 高
     */
    public void batchChangeSize(File dir, String renameAttachValue,
                                int width, int height) {
        if (dir.isDirectory()) {
            ExtensionFilenameFilter eff = new ExtensionFilenameFilter(this.
                    supportExtension, false);
            String[] list = dir.list(eff);
            for (int i = 0; i < list.length; i++) {
                batchChangeSize(new File(dir, list[i]), renameAttachValue,
                                width, height);
            }
        } else {
            if (dir.getName().indexOf(renameAttachValue) != 0) {
                File dest = new File(dir.getParent(),
                                     renameTo(dir.getName(), renameAttachValue));
                changeSize(getImageTypeByFileName(dir.getName()), dir, dest,
                           width,
                           height);
                System.out.println(dir.getAbsolutePath() + " >> " +
                                   dest.getAbsolutePath() +
                                   " change size to " + width + ", " + height);
            }
        }
    }

    /**
     * 这段代码是图片初始化导入时所用,代码耦合性太高,有时间需要改进
     * CQC
     * @param dir 文件或目录
     * @param renameAttachValue 批量改名所加的后缀
     * @param width 宽
     * @param height 高
     */
    public void batchChangeSize(File dir, String renameAttachValue,
                                String newPicPath,
                                int width, int height) {
        if (dir.getName().indexOf(renameAttachValue) != 0) {
            File dest = new File(newPicPath,
                                 renameTo(dir.getName(),
                                          renameAttachValue));
            changeSize(getImageTypeByFileName(dir.getName()), dir, dest,
                       width, height);

            if (width == 600 || width == 240 || width == 120) {
String waterMarkPath="";
                String uploadRealPath = "";
                this.batchAddWaterMark(dest,
                                       new File(uploadRealPath + "/" +
                                                waterMarkPath), 100);
            }
        }

    }


    /**
     * 为图片加上水印
     *
     * @param imageType String
     * @param src File
     * @param waterMarkSrc File
     * @param alpha int
     */
    public static void addWaterMark(String imageType, File src, File waterMarkSrc,
                             int alpha) {
        try {
            Image iSrc = ImageIO.read(src);
            int width = iSrc.getWidth(null);
            int height = iSrc.getHeight(null);

            BufferedImage bi = null;
            bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bi.createGraphics();
            g.setBackground(Color.WHITE);
            g.fillRect(0, 0, width, height);

            g.drawImage(iSrc, 0, 0, width, height, null);

            Image iWMSrc = ImageIO.read(waterMarkSrc);
            int wmWidth = iWMSrc.getWidth(null);
            int wmHeight = iWMSrc.getHeight(null);
            int w = 0;
            int h = 0;

            //得到水印图高宽比例
            double scale = 1;
            if (wmWidth > wmHeight) {
                scale = ((double) wmHeight) / ((double) wmWidth);
            } else {
                scale = ((double) wmWidth) / ((double) wmHeight);
            }
            //改变水印图高宽以适应原图
            if (width > height) {
                if (wmWidth > wmHeight) {
                    if (wmWidth > width) {
                        wmWidth = width;
                    }
                    wmHeight = new Double(wmWidth * scale).intValue();
                } else {
                    if (wmHeight > height) {
                        wmHeight = height;
                    }
                    wmWidth = new Double(wmHeight * scale).intValue();
                }
            } else {
                if (wmHeight > wmWidth) {
                    if (wmHeight > height) {
                        wmHeight = height;
                    }
                    wmWidth = new Double(wmHeight * scale).intValue();
                } else {
                    if (wmWidth > width) {
                        wmWidth = width;
                    }
                    wmHeight = new Double(wmWidth * scale).intValue();
                }
            }
            //取得水印图在原图的起始坐标
            if (width > wmWidth) {
                w = (width - wmWidth) / 2;
            }
            if (height > wmHeight) {
                h = (height - wmHeight) / 2;
            }
            System.out.println("src width=" + Integer.toString(width) +
                               " height=" +
                               Integer.toString(height) + " water width=" +
                               Integer.toString(wmWidth) + " height=" +
                               Integer.toString(wmHeight) + " left=" +
                               Integer.toString(w) + " top=" +
                               Integer.toString(h));

            iWMSrc = iWMSrc.getScaledInstance(wmWidth, wmHeight,
                                              Image.SCALE_SMOOTH);

            g.drawImage(iWMSrc, w, h, wmWidth, wmHeight, null);
            g.dispose();
            if (!imageType.equalsIgnoreCase(IMAGE_TYPE_GIF)) {
                ImageIO.write(bi, imageType, src);
            } else {
                AnimatedGifEncoder age = new AnimatedGifEncoder();
                age.setQuality(1);
                age.start(src.toString());
                age.addFrame(bi);
                age.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量添加水印
     *
     * @param dir File
     * @param waterMarkSrc File
     * @param alpha int
     */
    public static void batchAddWaterMark(File dir, File waterMarkSrc, int alpha) {
        if (dir.isDirectory()) {
            ExtensionFilenameFilter eff = new ExtensionFilenameFilter(supportExtension, false);
            String[] list = dir.list(eff);
            for (int i = 0; i < list.length; i++) {
                File f = new File(dir, list[i]);
                batchAddWaterMark(f, waterMarkSrc, alpha);
            }
        } else {
            addWaterMark(getImageTypeByFileName(dir.getName()), dir,
                         waterMarkSrc,
                         alpha);
        }
    }
}


/**
 * <p>Title: </p>
 *
 * <p>Description: 抽象类RGBImageFilter是ImageFilter的子类，继承它实现图象ARGB的处理
 * TransparentImageFilter
 * 类继承自RGBImageFilter，它的构造函数要求传入原始图象的宽度和高度。该类实现了filterRGB抽象函数，缺省的方式下，该函数将x，
 * y所标识的象素的ARGB值传入，程序员按照一定的程序逻辑处理后返回该象素新的ARGB值</p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: Infoscape</p>
 *
 * @author zzh
 * @version 1.0
 */
class MyImageFilter extends RGBImageFilter {

    int width = 0;
    int height = 0;
    int alpha = 0;

    /**
     * 构造器，用来接收需要过滤图象的尺寸，以及透明度
     *
     * @param width int
     * @param height int
     * @param alpha int
     */
    public MyImageFilter(int width, int height, int alpha) {
        this.canFilterIndexColorModel = true;
        this.width = width;
        this.height = height;
        this.alpha = alpha;
    }

    public int filterRGB(int x, int y, int rgb) {
        DirectColorModel dcm = (DirectColorModel) ColorModel.getRGBdefault();
        //DirectColorModel类用来将ARGB值独立分解出来
        int red = dcm.getRed(rgb);
        int green = dcm.getGreen(rgb);
        int blue = dcm.getBlue(rgb);
        //如果像素为白色，则让它透明
        if (red == 255 && green == 255 && blue == 255) {
            alpha = 0;
        }
        //进行标准ARGB输出以实现图象过滤
        return alpha << 24 | red << 16 | green << 8 | blue;
    }
}
