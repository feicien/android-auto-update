package com.loveplusplus.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

/**
 * 文件工具类
 * @author Administrator
 *
 */
public class FileUtils {



	/**
	 * 根据路径删除文件
	 * 
	 * @param path
	 */
	public static void deleteTempFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 把Assert下的文件拷贝到SDCard /Android/data/应用包名/files 目录下
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static File copyAssetFileToSDCard(Context context, String fileName,
			File dirPath) {
		try {

			InputStream is = context.getAssets().open(fileName);

			File file = new File(dirPath, fileName);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			fos.close();
			is.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	
	
	 /**
     * Check if external storage is built-in or removable.
     *
     * @return True if external storage is removable (like an SD card), false
     *         otherwise.
     */
    @TargetApi(9)
    public static boolean isExternalStorageRemovable() {
        if (Utils.hasGingerbread()) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    /**
     * Get the external app cache directory.
     *
     * @param context The context to use
     * @return The external cache dir
     */
    @TargetApi(8)
    public static File getExternalCacheDir(Context context) {
        if (Utils.hasFroyo()) {
            return context.getExternalCacheDir();
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }
    @TargetApi(8)
    public static File getExternalFileDir(Context context,String type) {
    	if (Utils.hasFroyo()) {
    		return context.getExternalFilesDir(type);
    	}
    	
    	StringBuilder sb=new StringBuilder();
    	sb.append("/Android/data/");
    	sb.append(context.getPackageName());
    	sb.append("/files/");
    	
    	if(null!=type){
    		sb.append(type).append("/");
    	}
    	return new File(Environment.getExternalStorageDirectory().getPath() + sb.toString());
    }

    /**
     * Check how much usable space is available at a given path.
     *
     * @param path The path to check
     * @return The space available in bytes
     */
    @SuppressWarnings("deprecation")
	@TargetApi(9)
    public static long getUsableSpace(File path) {
        if (Utils.hasGingerbread()) {
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }
	

	
}
