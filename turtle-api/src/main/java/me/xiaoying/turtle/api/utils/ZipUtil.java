package me.xiaoying.turtle.api.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 工具类 ZIP压缩包
 */
public class ZipUtil {
    /**
     * 获取ZIP中的某个文件
     *
     * @param file 压缩文件路径
     * @param fileName 文件名称
     */
    public static String getFile(String file, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            FileInputStream input = new FileInputStream(file);
            ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(input), StandardCharsets.UTF_8);
            ZipEntry ze = null;

            while ((ze = zipInputStream.getNextEntry()) != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(zipInputStream, StandardCharsets.UTF_8));
                String line;
                if (ze.getName().equals(fileName)) {
                    while ((line = br.readLine()) != null)
                        stringBuilder.append(line).append("\n");

                    break;
                }
            }

            zipInputStream.closeEntry();
            input.close();
        } catch (IOException e) {}
        return String.valueOf(stringBuilder);
    }
}