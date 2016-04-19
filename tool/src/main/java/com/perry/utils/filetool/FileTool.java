package com.perry.utils.filetool;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 项目名称:  ProxySet
 * 类描述
 * 创建人:    perry
 * 创建时间:  2016/3/29 16:03
 */

public class FileTool {
    /**
     * 获取一个 database 的方法
     *
     * @param path 路径信息
     * @return 得到的路径
     */
    public static Object readObject(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        Object dataBase = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream bis = new ObjectInputStream(fis);

            dataBase = bis.readObject();

            bis.close();
            fis.close();
        } catch (Exception e) {
        }

        return dataBase;
    }


    /**
     * append 数据到 文件末尾
     *
     * @param fileNamePath
     * @param data
     */
    public static void append(String fileNamePath, String data) {
        try {
            FileWriter writer = new FileWriter(fileNamePath, true);
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            e.toString();
        }
    }

    /**
     * 保存一个 database 的方法
     *
     * @param path     路径信息
     * @param database 数据对象
     */
    public static void writeObject(String path, Object database) {
        if (null == database) {
            return;
        }

        File file = new File(path);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream bos = new ObjectOutputStream(fos);

            bos.writeObject(database);

            bos.close();
            fos.close();
        } catch (Exception e) {
            e.toString();
        }
    }

    /**
     * 拷贝文件
     *
     * @param srcFile
     * @param dstFile
     * @return
     */
    public static boolean copyFile(File srcFile, File dstFile) {
        try {
            // 如果原文件不存在则退出
            if (!srcFile.exists()) {
                return false;
            }

            FileChannel in = null;
            FileChannel out = null;
            FileInputStream fileInputStream = null;
            FileOutputStream fileOutputStream = null;

            try {
                fileInputStream = new FileInputStream(srcFile);
                in = fileInputStream.getChannel();
                fileOutputStream = new FileOutputStream(dstFile);
                out = fileOutputStream.getChannel();
                in.transferTo(0, in.size(), out);
            } finally {
                if (in != null) {
                    in.close();
                    fileInputStream.close();
                }
                if (out != null) {
                    out.close();
                    fileOutputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 拷贝文件夹
     *
     * @param srcDir
     * @param dstDir
     * @throws IOException
     */
    public static boolean copyDir(String srcDir, String dstDir) {
        try {
            File srcFileDir = new File(srcDir);
            File[] files = srcFileDir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    String tmpSrcDir = srcDir + File.separator + file.getName();
                    String tmpDestDir = dstDir + File.separator
                            + file.getName();
                    if (new File(tmpDestDir).mkdir()) {
                        copyDir(tmpSrcDir, tmpDestDir);
                    }
                } else {
                    copyFile(file, new File(dstDir, file.getName()));
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * copy Assert 文件
     *
     * @param context
     * @param fileNamePath
     * @param destPath
     * @param destFileName
     */
    public static boolean copyAssert(Context context, String fileNamePath,
                                     String destPath, String destFileName) {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;

        File dir = new File(destPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File outFile = new File(destPath, destFileName);
        delete(outFile);

        byte[] buffer = new byte[1024];
        int size = 0;
        try {
            in = new BufferedInputStream(context.getAssets().open(fileNamePath));
            out = new BufferedOutputStream(new FileOutputStream(outFile));

            while ((size = in.read(buffer)) != -1) {
                out.write(buffer, 0, size);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * 从assets下复制文件夹到指定路径
     *
     * @param context  程序运行环境
     * @param assetDir 需要复制的文件夹
     * @param dstDir   需要保存的目录
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void copyAssetsDir(Context context, String assetDir,
                                     String dstDir, FileSearchListener listener) {
        try {
            // 获得Assets一共有几多文件
            String[] files = context.getResources().getAssets().list(assetDir);
            for (int i = 0; i < files.length; i++) {
                try {
                    // 获得每个文件的名字
                    String fileName = files[i];
                    // 根据路径判断是文件夹还是文件
                    if (!fileName.contains(".")) {
                        String tmpAssetsDir = new String();
                        if (assetDir.isEmpty()) {
                            tmpAssetsDir = fileName;
                        } else {
                            tmpAssetsDir = assetDir + File.separator
                                    + fileName;
                        }
                        copyAssetsDir(context, tmpAssetsDir, dstDir + File.separator + fileName, listener);
                        continue;
                    }

                    if (listener == null || listener.searchRule(fileName)) {
                        String tmpAssetsFile = new String();
                        if (assetDir.isEmpty()) {
                            tmpAssetsFile = fileName;
                        } else {
                            tmpAssetsFile = assetDir + File.separator
                                    + fileName;
                        }
                        copyAssert(context, tmpAssetsFile, dstDir, fileName);
                    }
                } catch (Exception e) {
                    return;
                }
            }
        } catch (IOException e1) {
            return;
        }
    }

    /**
     * 解压缩 jar 文件的方法
     *
     * @return 是否成功
     */
    public static boolean unzipJarFiles(String jarPath, String unzipPath,
                                        String innerJarFile) {
        try {
            File file = new File(jarPath);
            JarFile jarFile = new JarFile(file);
            int count = 0;

            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                String name = entry.getName();
                if (name.equals(innerJarFile)) {
                    name = unzipPath;

                    InputStream ins = jarFile.getInputStream(entry);
                    File temp = new File(name);
                    FileOutputStream fos = new FileOutputStream(temp);
                    byte[] bytes = new byte[2 * 1024];
                    while (true) {
                        int result = ins.read(bytes);
                        if (result <= 0) {
                            break;
                        }
                        fos.write(bytes, 0, result);
                    }

                    ins.close();
                    fos.close();
                    count++;
                    break;
                }
            }

            return (count >= 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean rename(String oldName, String newName) {
        File newFile = new File(newName);
        if (newFile.exists()) {
            delete(newName);
        }

        File oldFile = new File(oldName);
        return oldFile.renameTo(newFile);
    }

    /**
     * 删除单个文件
     *
     * @param filePath 被删除文件的文件名
     */
    public static boolean delete(String filePath) {
        File file = new File(filePath);
        return delete(file);
    }

    /**
     * 删除单个文件
     *
     * @param
     */
    public static boolean delete(File file) {
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            return file.delete();
        }

        return false;
    }

    /**
     * 删除文件夹及文件夹下文件
     *
     * @param dir
     * @return
     */
    public static void delDir(File dir, FileSearchListener listener) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                delDir(new File(dir, children[i]), listener);
            }
        }

        if (listener == null || listener.searchRule(dir.getName())) {
            dir.delete();
        }
    }

    /**
     * 删除文件夹及文件夹下文件
     *
     * @param dir
     * @return
     */
    public static void delDir(String dir, FileSearchListener listener) {
        File dirFile = new File(dir);
        if (dirFile != null && dirFile.exists()) {
            delDir(dirFile, listener);
        }
    }

    /**
     * 从文件全路径中获取文件名
     *
     * @param fullFilePath
     * @return
     */
    public static String getPathFileName(String fullFilePath) {
        int index = fullFilePath.lastIndexOf(File.separator);
        return fullFilePath.substring(index + 1);
    }

    /**
     * 获取文件扩展名
     *
     * @param filePath
     * @return
     */
    public static String getFileExtName(String filePath) {
        int index = filePath.lastIndexOf(".");

        return filePath.substring(index);
    }

    /**
     * 替换文件扩展名
     *
     * @param filePath
     * @param extName
     * @return
     */
    public static String replaceFileExtName(String filePath, String extName) {
        int index = filePath.lastIndexOf(".");

        String tempString = filePath.substring(0, index + 1);

        return tempString + extName;
    }

    /**
     * 从文件全路径中获取文件名
     *
     * @param
     * @return
     */
    public static String getUrlFileName(String url) {
        return getPathFileName(url);
    }


    /**
     * 查找指定目录下，指定扩展名文件列表
     *
     * @return 得到的文件列表
     */
    public static Vector<String> searchDirectory(String path,
                                                 FileSearchListener fileSearchListener) {
        File file = new File(path);
        String[] names = file.list();
        Vector<String> files = new Vector<String>();

        // 如果指定目录内无动态包
        if (names == null) {
            return files;
        }

        for (int i = 0; i < names.length; i++) {
            if (fileSearchListener == null
                    || fileSearchListener.searchRule(names[i])) {
                files.addElement(names[i]);
            }
        }

        return files;
    }

    /**
     * 创建文件夹
     *
     * @param dirPath
     */
    public static boolean makesureFileExist(String dirPath) {
        if (dirPath == null) {
            return false;
        }

        File fileDir = new File(dirPath);
        if (!fileDir.exists()) {
            return fileDir.mkdirs();
        }

        return true;
    }

    /**
     * 获取APK文件地址路径
     *
     * @param context
     * @return
     */
    public static String getAppPath(Context context) {
        File file = context.getFilesDir();
        return file.getAbsolutePath() + File.separator;
    }

    /**
     * 获取 sd 卡路径的方法
     *
     * @return 得到的 sd 卡路径
     */
    public static String getSdcardPath() {
        String status = Environment.getExternalStorageState();

        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getPath()
                    + File.separator;
        }

        return null;
    }

    public interface FileSearchListener {
        boolean searchRule(String fileName);
    }
}
