package com.example.common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.lingala.zip4j.ZipFile;

/**
 * @Description 解压缩文件工具类
 * @Author Mr.nobody
 * @Date 2021/3/8
 * @Version 1.0.0
 */
public class ZipUtils {

    static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(16);

    public static void unzipEncrypted(String zipFilePath, String desDirectory, String password) throws Exception {
        Long startTime = System.nanoTime();
        ZipFile zipFile = new ZipFile(zipFilePath);
        zipFile.setCharset(Charset.forName("GBK"));
        // 如果解压需要密码
        if (zipFile.isEncrypted()) {
            zipFile.setPassword(password.toCharArray());
        }
        zipFile.extractAll(desDirectory);
        Long endTime = System.nanoTime();
        System.out.println("文件：" + zipFilePath + ", Time: " + (endTime - startTime)  / 1000000000 );
    }

    /**
     * 解压
     *
     * @param zipFilePath 带解压文件
     * @param desDirectory 解压到的目录
     * @throws Exception
     */
    public static void unzip(String zipFilePath, String desDirectory) throws Exception {

        File desDir = new File(desDirectory);
        if (!desDir.exists()) {
            boolean mkdirSuccess = desDir.mkdir();
            if (!mkdirSuccess) {
                throw new Exception("创建解压目标文件夹失败");
            }
        }
        // 读入流
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));
        // 遍历每一个文件
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        while (zipEntry != null) {
            if (zipEntry.isDirectory()) { // 文件夹
                String unzipFilePath = desDirectory + File.separator + zipEntry.getName();
                // 直接创建
                mkdir(new File(unzipFilePath));
            } else { // 文件
                String unzipFilePath = desDirectory + File.separator + zipEntry.getName();
                File file = new File(unzipFilePath);
                // 创建父目录
                mkdir(file.getParentFile());
                // 写出文件流
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                    new FileOutputStream(unzipFilePath));
                byte[] bytes = new byte[1024];
                int readLen;
                while ((readLen = zipInputStream.read(bytes)) != -1) {
                    bufferedOutputStream.write(bytes, 0, readLen);
                }
                bufferedOutputStream.close();
            }
            zipInputStream.closeEntry();
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }

    // 如果父目录不存在则创建
    private static void mkdir(File file) {
        if (null == file || file.exists()) {
            return;
        }
        mkdir(file.getParentFile());
        file.mkdir();
    }
    private static void batchUnzipFile(String desDirectory, String password, boolean isDelete) throws Exception {
        Long startTime = System.nanoTime();

        File file = new File(desDirectory);
        List<CompletableFuture<String>> completableFutureList = new ArrayList<>();
        if (file.isDirectory() && file.listFiles() != null && file.listFiles().length > 0) {
            for (File file1 : file.listFiles()) {
                if (file1.getName()
                    .contains("zip")) {
                    if (file1.getName()
                        .contains("downloading")) {
                        file1.delete();
                        // System.out.println("文件：" + file1.getName() + "解压并删除成功");
                        continue;
                    }
                    CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
                        try {
                            unzipEncrypted(file1.getAbsolutePath(), desDirectory, password);
                            if (isDelete) {
                                file1.delete();
                            }
                            // System.out.println("文件：" + file1.getName() + "解压并删除成功");
                        } catch (Exception e) {
                            return e.getMessage();
                        }
                        return "文件：" + file1.getName() + "解压并删除成功";
                    }, fixedThreadPool);
                    completableFutureList.add(stringCompletableFuture);
                }

            }
        }
        System.out.println("completableFutureList: " + completableFutureList);
        try {
            if (completableFutureList.size() != 0) {
                for (CompletableFuture<String> v : completableFutureList) {
                    // System.out.println(v.get());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fixedThreadPool.shutdown();

            Long endTime = System.nanoTime();
            System.out.println("Time: " + (endTime - startTime));
        }
    }
    private static void unzipFile(String desDirectory, String password, boolean isDelete) throws Exception {


        File file = new File(desDirectory);
        if (file.isDirectory() && file.listFiles() != null && file.listFiles().length > 0) {
            for (File file1 : file.listFiles()) {
                if (file1.getName()
                    .contains("zip")) {
                    if (file1.getName()
                        .contains("downloading")) {
                        file1.delete();
                        System.out.println("文件：" + file1.getName() + "解压并删除成功");
                        continue;
                    }
                    try {
                        unzipEncrypted(file1.getAbsolutePath(), desDirectory, password);
                        if (isDelete) {
                            file1.delete();
                        }
                        // System.out.println("文件：" + file1.getName() + "解压并删除成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    public static void nioCpoy(String source, String target, int allocate) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(allocate);
        FileInputStream inputStream = new FileInputStream(source);
        FileChannel inChannel = inputStream.getChannel();

        FileOutputStream outputStream = new FileOutputStream(target);
        FileChannel outChannel = outputStream.getChannel();

        int length = inChannel.read(byteBuffer);
        while(length != -1){
            byteBuffer.flip();//读取模式转换写入模式
            outChannel.write(byteBuffer);
            byteBuffer.clear(); //清空缓存，等待下次写入
            // 再次读取文本内容
            length = inChannel.read(byteBuffer);
        }
        outputStream.close();
        outChannel.close();
        inputStream.close();
        inChannel.close();
    }
    public static void fileChannelCopy(String sfPath, String tfPath) {

        File sf = new File(sfPath);
        File tf = new File(tfPath);
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try{
            fi = new FileInputStream(sf);
            fo = new FileOutputStream(tf);
            in = fi.getChannel();//得到对应的文件通道
            out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                fi.close();
                in.close();
                fo.close();
                out.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // nioCpoy("D:\\Study\\test\\132JVM常见参数总结--.mp4", "D:\\Study\\test\\132JVM常见参数总结--1.mp4", 1024*4);
//         fileChannelCopy("D:\\Study\\test\\132JVM常见参数总结--.mp4", "D:\\Study\\test\\132JVM常见参数总结--2.mp4");
// File file = new File("D:\\Study\\test\\132JVM常见参数总结--.mp4");
// file..compareTo(new File("D:\\Study\\test\\132JVM常见参数总结--2.mp4"));
//         FileReader fileReader = new FileReader(file);
//         int read = fileReader.();
        //Time: 5510098300
        //Time: 7406199600
        // String desDirectory = "D:\\Study\\test";
        //
        Long startTime = System.nanoTime();
        String desDirectory = "E:\\mca\\004-【马士兵VIP课程】java高级互联网MCA架构师\\01-精英1班";
        unzipFile(desDirectory, "shikey.com-39725", true);
        Long endTime = System.nanoTime();
        System.out.println("Total Time: " + (endTime - startTime) / 1000000000 );

        // 文件：E:\mca\test\132JVM常见参数总结--.zip, Time: 34
        // 文件：E:\mca\test\146消息中间件RocketMQ06--.zip, Time: 33
        // 文件：E:\mca\test\148硬技能之上的软技巧3--.zip, Time: 32
        // Total Time: 101
        // 文件：E:\mca\test\132JVM常见参数总结--.zip, Time: 46
        // 文件：E:\mca\test\146消息中间件RocketMQ06--.zip, Time: 30
        // 文件：E:\mca\test\148硬技能之上的软技巧3--.zip, Time: 46
        // Total Time: 122
        // 文件：E:\mca\test\132JVM常见参数总结--.zip, Time: 40
        // 文件：E:\mca\test\146消息中间件RocketMQ06--.zip, Time: 24
        // 文件：E:\mca\test\148硬技能之上的软技巧3--.zip, Time: 29
        // Total Time: 94
    }
}
