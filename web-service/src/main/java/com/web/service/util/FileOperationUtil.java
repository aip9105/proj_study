package com.web.service.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * FileChannel 文件操作工具
 */
public class FileOperationUtil {
    private FileOperationUtil() {
    }

    /**
     * 默认缓冲区大小
     */
    private static final int DEFAULT_CAPACITY = 8192;

    /**
     * 获取当前系统换行符
     */
    private static final String LINE_SEPARATOR = System.lineSeparator();

    /**
     * 方法说明 : 按行读取文件，使用 FileChannel，缓冲区容量 8KB，字符串编码字符集 UTF-8
     *
     * @param filePath 要读取的文件路径

     * @date 2017/8/2
     */
    public static List<String> readFileByLine(String filePath) throws IOException {
        return readFileByLine(filePath, DEFAULT_CAPACITY, StandardCharsets.UTF_8);
    }

    /**
     * 方法说明 : 按行读取文件，使用 FileChannel，缓冲区容量 8KB，字符串编码字符集 UTF-8
     *
     * @param file 要读取的文件

     * @date 2017/8/2
     */
    public static List<String> readFileByLine(File file) throws IOException {
        return readFileByLine(file, DEFAULT_CAPACITY, StandardCharsets.UTF_8);
    }

    /**
     * 方法说明 : 按行读取文件，使用 FileChannel，缓冲区容量 8KB，字符串编码字符集 UTF-8
     *
     * @param filePath 要读取的文件路径
     * @param charset  字符串编码字符集

     * @date 2017/8/2
     */
    public static List<String> readFileByLine(String filePath, Charset charset) throws IOException {
        return readFileByLine(filePath, DEFAULT_CAPACITY, charset);
    }

    /**
     * 方法说明 : 按行读取文件，使用 FileChannel，缓冲区容量 8KB，字符串编码字符集 UTF-8
     *
     * @param file    要读取的文件
     * @param charset 字符串编码字符集

     * @date 2017/8/2
     */
    public static List<String> readFileByLine(File file, Charset charset) throws IOException {
        return readFileByLine(file, DEFAULT_CAPACITY, charset);
    }

    /**
     * 方法说明 : 按行读取文件，使用 FileChannel，缓冲区大小 8KB，字符串编码字符集 UTF-8
     *
     * @param filePath 要读取的文件路径
     * @param capacity 缓冲区容量
     * @param charset  字符串编码字符集

     * @date 2017/8/2
     */
    private static List<String> readFileByLine(String filePath, int capacity, Charset charset) throws IOException {
        // 只读模式开启 FileChannel
        try (FileChannel fileChannel = FileChannel.open(Paths.get(filePath), EnumSet.of(StandardOpenOption.READ))
        ) {
            // 缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
            return readFileByLine(fileChannel, byteBuffer, charset);
        }
    }

    /**
     * 方法说明 : 按行读取文件，使用 FileChannel，缓冲区大小 8KB，字符串编码字符集 UTF-8
     *
     * @param file     要读取的文件
     * @param capacity 缓冲区容量
     * @param charset  字符串编码字符集

     * @date 2017/8/2
     */
    private static List<String> readFileByLine(File file, int capacity, Charset charset) throws IOException {
        // 只读模式开启 FileChannel
        try (FileChannel fileChannel = new RandomAccessFile(file, "r").getChannel()) {
            // 缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
            return readFileByLine(fileChannel, byteBuffer, charset);
        }
    }

    /**
     * 方法说明 : 按行读取文件，使用 FileChannel
     *
     * @param fileChannel 打开的文件通道
     * @param byteBuffer  缓冲区
     * @param charset     字符串编码字符集

     * @date 2017/8/2
     */
    private static List<String> readFileByLine(FileChannel fileChannel, ByteBuffer byteBuffer, Charset charset) throws IOException {
        List<String> dataList = new ArrayList<>(); // 存储读取的每行数据
        // halfLine 由于是按固定字节读取，在一次读取中，第一行和最后一行经常是不完整的行，因此定义此变量来存储上次的最后一行和这次的第一行的内容，
        // 并将之连接成完成的一行，否则会出现汉字被拆分成2个字节，并被提前转换成字符串而乱码的问题
        byte[] halfLine = new byte[0];
        while (fileChannel.read(byteBuffer) != -1) { // fileChannel.read(byteBuffer) 从文件管道读取内容到缓冲区(byteBuffer)
            int bufferSize = byteBuffer.position(); // 读取结束后的位置，相当于读取的长度
            byte[] bs = new byte[bufferSize]; // 用来存放读取的内容的数组
            byteBuffer.rewind(); // 将position设回0,所以你可以重读Buffer中的所有数据,此处如果不设置,无法使用下面的get方法
            byteBuffer.get(bs); // byteBuffer.get(bs, 0, bs.length())：从position初始位置开始相对读,读bs.length个byte,并写入bs[0]到bs[bs.length-1]的区域
            byteBuffer.clear(); // 清空 byteBuffer，以便下次再用

            int startNum = 0;
            int LF = '\n'; // 换行符 line feed 10
            int CR = '\r'; // 回车符 carriage return 13
            boolean hasLF = false; // 是否有换行符
            halfLine = getBytes(charset, dataList, halfLine, bufferSize, bs, startNum, LF, CR, hasLF);
        }
        if (halfLine.length > 0) { // 兼容文件最后一行没有换行的情况
            String line = new String(halfLine, 0, halfLine.length, charset);
            dataList.add(line);
        }
        return dataList;
    }

    private static byte[] getBytes(Charset charset, List<String> dataList, byte[] halfLine, int bufferSize, byte[] bs, int startNum, int LF, int CR, boolean hasLF) {
        for (int i = 0; i < bufferSize; i++) {
            if (bs[i] == LF) { // 单次读取超出一行的情况，拆行
                hasLF = true;
                int tempNum = halfLine.length;
                int lineNum = i - startNum;
                byte[] lineByte = new byte[tempNum + lineNum]; // 数组大小，已经去掉换行符

                System.arraycopy(halfLine, 0, lineByte, 0, tempNum); // 填充了lineByte[0]~lineByte[tempNum-1]，将上次读取的不足一行的部分放在数组前部分
                halfLine = new byte[0]; // 重置数组
                System.arraycopy(bs, startNum, lineByte, tempNum, lineNum); // 填充lineByte[tempNum]~lineByte[tempNum+lineNum-1]，将本次读取的部分放在数组后部分

                String line = new String(lineByte, 0, lineByte.length, charset); // 一行完整的字符串(过滤了换行和回车)
                dataList.add(line);

                // 过滤回车符和换行符
                if (i + 1 < bufferSize && bs[i + 1] == CR) {
                    startNum = i + 2;
                } else {
                    startNum = i + 1;
                }

            }
        }
        if (hasLF) { // 将本次读取的最后一个换行符之后的不足一行的部分，放到数组中保存
            halfLine = new byte[bs.length - startNum];
            System.arraycopy(bs, startNum, halfLine, 0, halfLine.length);
        } else { // 兼容单次读取的内容不足一行的情况
            byte[] temp = new byte[halfLine.length + bs.length];
            System.arraycopy(halfLine, 0, temp, 0, halfLine.length); // 将上次读取的剩余部分存入数组前部分
            System.arraycopy(bs, 0, temp, halfLine.length, bs.length); // 将本次读取的剩余部分存入数组后部分
            halfLine = temp;
        }
        return halfLine;
    }

    /**
     * 方法说明 : 方法说明 : 按行写入文件，使用 FileChannel，字符串编码字符集 UTF-8
     *
     * @param filePath 要写入的文件路径
     * @param lines    lines 行数据集合

     * @date 2017/8/2
     */
    public static void writeFileByLine(String filePath, List<String> lines) throws IOException {
        writeFileByLine(filePath, lines, StandardCharsets.UTF_8);
    }

    /**
     * 方法说明 : 方法说明 : 按行写入文件，使用 FileChannel，字符串编码字符集 UTF-8
     *
     * @param file  要写入的文件
     * @param lines lines 行数据集合

     * @date 2017/8/2
     */
    public static void writeFileByLine(File file, List<String> lines) throws IOException {
        writeFileByLine(file, lines, StandardCharsets.UTF_8);
    }

    /**
     * 方法说明 : 方法说明 : 按行写入文件，使用 FileChannel
     *
     * @param filePath 要写入的文件路径
     * @param lines    lines 行数据集合
     * @param charset  charset 字符串编码字符集

     * @date 2017/8/2
     */
    public static void writeFileByLine(String filePath, List<String> lines, Charset charset) throws IOException {
        // 读写模式开启 FileChannel，开启同步
        try (FileChannel fileChannel = FileChannel.open(Paths.get(filePath), EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.SYNC, StandardOpenOption.CREATE))) {
            writeFileByLine(fileChannel, lines, charset);
        }
    }

    /**
     * 方法说明 : 方法说明 : 按行写入文件，使用 FileChannel
     *
     * @param file    要写入的文件
     * @param lines   lines 行数据集合
     * @param charset charset 字符串编码字符集

     * @date 2017/8/2
     */
    public static void writeFileByLine(File file, List<String> lines, Charset charset) throws IOException {
        // 读写模式开启 FileChannel，开启同步
        try (FileChannel fileChannel = new RandomAccessFile(file, "rws").getChannel()) {
            writeFileByLine(fileChannel, lines, charset);
        }
    }

    /**
     * 方法说明 : 按行写入文件，使用 FileChannel
     *
     * @param fileChannel 打开的文件通道
     * @param lines       行数据集合
     * @param charset     字符串编码字符集

     * @date 2017/8/2
     */
    private static void writeFileByLine(FileChannel fileChannel, List<String> lines, Charset charset) throws IOException {
        for (String line : lines) {
            // 拼接换行符，使用指定字符集获取字节数组
            byte[] bytes = (line + LINE_SEPARATOR).getBytes(charset);
            // 构建 ByteChannel
            try (ReadableByteChannel readableByteChannel = Channels.newChannel(new ByteArrayInputStream(bytes))) {
                // 在 position 处追加写入
                fileChannel.transferFrom(readableByteChannel, fileChannel.position(), bytes.length);
            }
            // 更新 fileChannel 的 position
            fileChannel.position(fileChannel.position() + bytes.length);
        }
    }

}
