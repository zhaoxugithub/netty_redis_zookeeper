package com.crazymakercircle.iodemo.fileDemos;

import com.crazymakercircle.NioDemoConfig;
import com.crazymakercircle.util.IOUtil;
import com.crazymakercircle.util.Logger;
import org.junit.Test;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by 尼恩@ 疯创客圈
 */
public class FileMmapDemo {

    /**
     * 演示程序的入口函数
     *
     * @param args
     */
    public static void main(String[] args) {
//        mmapWriteFile();
        mmapPrivate();
    }



    /**
     * mmap读写文件
     *
     */
    public static void mmapWriteFile() {

        String sourcePath = NioDemoConfig.MMAP_FILE_RESOURCE_SRC_PATH;
        String decodePath = IOUtil.getResourcePath(sourcePath);

        Logger.debug("decodePath=" + decodePath);

        //向文件中存1M的数据
        int length = 1024;//
        try (FileChannel channel = new RandomAccessFile(decodePath, "rw").getChannel();) {

            //一个整数4个字节
            MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, length);
            for (int i = 0; i < length; i++) {
                mapBuffer.put((byte) (Integer.valueOf('a') + i % 26));
            }
            for (int i = 0; i < length; i++) {
                if (i % 50 == 0) System.out.println("");
                //像数组一样访问
                System.out.print((char) mapBuffer.get(i));
            }

            mapBuffer.force();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取文件内容并输出
     *
     */
    public static void mmapPrivate() {

        String sourcePath = NioDemoConfig.MMAP_FILE_RESOURCE_SRC_PATH;
        String decodePath = IOUtil.getResourcePath(sourcePath);

        Logger.debug("decodePath=" + decodePath);

        //向文件中存1M的数据
        int length = 1024;
        //通过RandomAccessFile获取FileChannel。
        try (FileChannel channel = new RandomAccessFile(decodePath, "rw").getChannel();) {

            //通过channel进行内存映射，获取一个虚拟内存区域VMA
            MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.PRIVATE, 0, length);
            for (int i = 0; i < length; i++) {
                mapBuffer.put((byte) (Integer.valueOf('a') + i % 26));
            }
            for (int i = 0; i < length; i++) {
                if (i % 50 == 0) System.out.println("");
                //像数组一样访问
                System.out.print((char) mapBuffer.get(i));
            }

            mapBuffer.force();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
