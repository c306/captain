package com.c306.utils;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;



public class FileUtil {

	private static String ENCODING = "UTF-8";

	/**
	 * write the content to a file;
	 * 
	 * @param output
	 * @param content
	 * @throws Exception
	 */
	public static void createFile(String output, String content)
			throws Exception {
		OutputStreamWriter fw = null;
		PrintWriter out = null;
		try {
/*			if (ENCODING == null)
				ENCODING = PropsUtil.ENCODING;*/

			fw = new OutputStreamWriter(new FileOutputStream(output), ENCODING);
			out = new PrintWriter(fw);
			out.print(content);
		} catch (Exception ex) {
			throw new Exception(ex);
		} finally {
			if (out != null)
				out.close();
			if (fw != null)
				fw.close();
		}

	}
	public static boolean exists(String fileName){
		try {
	    	System.out.println("ooooooooooooooooooo" + fileName);
	    	System.out.println("ooooooooooooooooooo" + new File(fileName).exists());
			return new File(fileName).exists();
		} catch (Exception ex) {
		}
		return false;
	}

	/**
	 * read the content from a file;
	 * 
	 * @param output
	 * @param content
	 * @throws Exception
	 */
	public static String readFile(String input) throws Exception {
		char[] buffer = new char[4096];
		int len = 0;
		StringBuffer content = new StringBuffer(4096);

/*		if (ENCODING == null)
			ENCODING = PropsUtil.ENCODING;*/
		InputStreamReader fr = null;
		BufferedReader br = null;
		try {
			fr = new InputStreamReader(new FileInputStream(input), ENCODING);
			br = new BufferedReader(fr);
			while ((len = br.read(buffer)) > -1) {
				content.append(buffer, 0, len);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (br != null)
				br.close();
			if (fr != null)
				fr.close();
		}
		return content.toString();
	}

	/**
	 * This class moves an input file to output file
	 * 
	 * @param String
	 *            input file to move from
	 * @param String
	 *            output file
	 * 
	 */
	public static void move(String input, String output) throws Exception {
		File inputFile = new File(input);
		File outputFile = new File(output);
		try {
			inputFile.renameTo(outputFile);
		} catch (Exception ex) {
			throw new Exception("Can not mv" + input + " to " + output
					+ ex.getMessage());
		}
	}

	/**
	 * This class copies an input file to output file
	 * 
	 * @param String
	 *            input file to copy from
	 * @param String
	 *            output file
	 */
	public static boolean copy(String input, String output) throws Exception {
		int BUFSIZE = 65536;
		FileInputStream fis = new FileInputStream(input);
		FileOutputStream fos = new FileOutputStream(output);

		try {
			int s;
			byte[] buf = new byte[BUFSIZE];
			while ((s = fis.read(buf)) > -1) {
				fos.write(buf, 0, s);
			}

		} catch (Exception ex) {
			throw new Exception("makehome" + ex.getMessage());
		} finally {
			fis.close();
			fos.close();
		}
		return true;
	}
    
	/**
	 * 更新文件，若文件日期小于时，不更新
	 * @param input
	 * @param output
	 * @return
	 * @throws Exception
	 */
	public static boolean update(String input, String output) throws Exception {
		File _infile=new File(input);
		File _outfile=new File(output);
		
		if (!_infile.exists())//输入文件不存在
			return false;
		if (!_outfile.exists())//若不存在，创建文件
		{
			if (!_outfile.getParentFile().exists())
			{
				_outfile.getParentFile().mkdirs();
			}
		}
		if(!_outfile.exists()||_infile.lastModified()>_outfile.lastModified())//复制文件
		{
			// 复制文件
			FileUtil.copy(input,output);
		}		
		return true;
	}
	/**
	 * create a directory
	 * 
	 * @param home
	 * @throws Exception
	 */
	public static void makehome(String home) throws Exception {
		File homedir = new File(home);
		if (!homedir.exists()) {
			try {
				homedir.mkdirs();
			} catch (Exception ex) {
				throw new Exception("Can not mkdir :" + home
						+ " Maybe include special charactor!");
			}
		}
	}

	/**
	 * This class copies an input files of a directory to another directory not
	 * include subdir
	 * 
	 * @param String
	 *            sourcedir the directory to copy from such as:/home/bqlr/images
	 * @param String
	 *            destdir the target directory
	 */
	public static void CopyDir(String sourcedir, String destdir)
			throws Exception {
		File dest = new File(destdir);
		File source = new File(sourcedir);

		String[] files = source.list();
		try {
			makehome(destdir);
		} catch (Exception ex) {
			throw new Exception("CopyDir:" + ex.getMessage());
		}

		for (int i = 0; i < files.length; i++) {
			String sourcefile = source + File.separator + files[i];
			String destfile = dest + File.separator + files[i];
			File temp = new File(sourcefile);
			if (temp.isFile()) {
				try {
					copy(sourcefile, destfile);
				} catch (Exception ex) {
					throw new Exception("CopyDir:" + ex.getMessage());
				}
			}
		}
	}
	public static void writeTo(File f, File newFile) throws IOException{
		
		  FileInputStream fis = new FileInputStream(f);
		  FileOutputStream fos = new FileOutputStream(newFile);
		  try{
		   byte[] buf = new byte[8192];
		   do{
		    int rc = fis.read(buf);
		    if(rc == -1)
		     break;
		    fos.write(buf, 0, rc);
		    if(rc < buf.length)
		     break;
		   }while(true);
		   
		   //newFile.setLastModified((new Date()).getTime());
		  }finally{
		   fis.close();
		   fos.close();
		  }
		 }



	/**
	 * This class del a directory recursively,that means delete all files and
	 * directorys.
	 * 
	 * @param File
	 *            directory the directory that will be deleted.
	 */
	public static void recursiveRemoveDir(File directory) throws Exception {
		if (!directory.exists())
			throw new IOException(directory.toString() + " do not exist!");

		String[] filelist = directory.list();
		File tmpFile = null;
		for (int i = 0; i < filelist.length; i++) {
			tmpFile = new File(directory.getAbsolutePath(), filelist[i]);
			if (tmpFile.isDirectory()) {
				recursiveRemoveDir(tmpFile);
			} else if (tmpFile.isFile()) {
				try {
					tmpFile.delete();
				} catch (Exception ex) {
					throw new Exception(tmpFile.toString()
							+ " can not be deleted " + ex.getMessage());
				}
			}
		}
		try {
			directory.delete();
		} catch (Exception ex) {
			throw new Exception(directory.toString() + " can not be deleted "
					+ ex.getMessage());
		} finally {
			filelist = null;
		}
	}

	/**
	 * �0�9�1�7�1�7
	 * 
	 * @param buffer
	 * @return
	 */
	public byte[] compress(byte[] buffer) {
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			GZIPOutputStream stream = new GZIPOutputStream(output);
			stream.write(buffer, 0, buffer.length);
			stream.finish();
			stream.close();
			buffer = output.toByteArray();
			output.close();
			return buffer;
		} catch (IOException e) {
			 //Log.error(e);
			return null;
		}
	}

	/**
	 * �1�7�1�7�0�9
	 * 
	 * @param buffer
	 * @return
	 */
	public byte[] uncompress(byte[] buffer) {
		try {
			ByteArrayInputStream input = new ByteArrayInputStream(buffer);
			GZIPInputStream stream = new GZIPInputStream(input);

			ArrayList trunks = new ArrayList();
			int read;
			int size = 0;
			while (true) {
				byte[] trunk = new byte[_uncompressBufferSize];
				read = stream.read(trunk, 0, _uncompressBufferSize);
				if (read < 0)
					break;
				size += read;
				trunks.add(new Trunk(trunk, read));
			}
			stream.close();
			input.close();

			// Reconstruct the full contents
			byte[] contents = new byte[size];
			Iterator iter = trunks.iterator();
			int index = 0;
			while (iter.hasNext()) {
				Trunk trunk = (Trunk) iter.next();
				System.arraycopy(trunk.content, 0, contents, index, trunk.size);
				index += trunk.size;
			}
			return contents;
		} catch (IOException e) {
			//Log.error(e);
			return null;
		}
	}
	public static boolean saveUrlAs(String photoUrl, String fileName) { 
//		此方法只能用户HTTP协议 
		  try { 
		    URL url = new URL(photoUrl); 
		    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		    DataInputStream in = new DataInputStream(connection.getInputStream());
		   
		    FileOutputStream fileOutstream=new FileOutputStream(fileName);
		   
		    DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName)); 
		    int count = 0; 
		    byte[] buffer = new byte[4096];
		    while ((count = in.read(buffer)) > 0) {   
		    out.write(buffer, 0, count); 
		    } 
		    out.close();
		    in.close();
		    return true; 
		  } 
		  catch (Exception e) { 
			  e.printStackTrace();
		    return false;
		  }
		} 	
/*	public static boolean saveFile(FormFile file, String filePathName) {
		int BUFFER = 8192;
		boolean result = false;
		try {
			
			InputStream streamIn = file.getInputStream();
			OutputStream streamOut = new FileOutputStream(filePathName);

			int bytesRead = 0;
			byte[] buffer = new byte[BUFFER];
			while ((bytesRead = streamIn.read(buffer, 0, BUFFER)) != -1) {
				streamOut.write(buffer, 0, bytesRead);
			}

			streamOut.close();
			streamIn.close();
		} catch (FileNotFoundException ex) {
			//Log.debug("uploadFile FileNotFoundException:" + ex.getMessage());
			
			return result;
		} catch (IOException ex) {
			//Log.debug("uploadFile IOException:" + ex.getMessage());
			return result;
		} finally {
			file.destroy();
		}
		return true;
	}*/

	static private final class Trunk {
		public Trunk(byte[] content, int size) {
			this.content = content;
			this.size = size;
		}

		public final byte[] content;

		public final int size;
	}
	
	 public static boolean delAllFile(String path) {
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	             delFolder(path + "/" + tempList[i]);//再删除空文件夹
	             flag = true;
	          }
	       }
	       return flag;
	     }


public static void delFolder(String folderPath) {
    try {
       delAllFile(folderPath); //删除完里面所有内容
       String filePath = folderPath;
       filePath = filePath.toString();
       java.io.File myFilePath = new java.io.File(filePath);
       myFilePath.delete(); //删除空文件夹
    } catch (Exception e) {
      e.printStackTrace(); 
    }
}

public static File getFileFromBytes(byte[] b, String outputFile) {
    BufferedOutputStream stream = null;
    File file = null;
    try {
        file = new File(outputFile);
        if(!file.getParentFile().isDirectory())
        	file.getParentFile().mkdir();
        FileOutputStream fstream = new FileOutputStream(file);
        stream = new BufferedOutputStream(fstream);
        stream.write(b);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    return file;
}

	final private int _uncompressBufferSize=10000;

}
