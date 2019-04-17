package com.wind.androidlearn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class DimenTools_PX {
    private static final String DIRECTORY_RES = "D:\\res\\";
    private final static String TEMPLATE_WIDTH = "<dimen name=\"x{0}\">{1}px</dimen>\n";
    private final static String TEMPLATE_HEIGHT = "<dimen name=\"y{0}\">{1}px</dimen>\n";
    //{0}-HEIGHT
    private final static String TEMPLATE_VALUE = "values-{0}x{1}";

    private int smallSize;
    private int largeSize;
    //是否竖屏
    private boolean isPortrait;

    private String xDimenFileName;
    private String yDimenFileName;

    private String supportDimens = new StringBuilder()
            //***************use toTV or PAD**************
            /*.append("320,480;")
            .append("432,800;")
            .append("480,800;")
            .append("480,854;")
            .append("480,854;")
            .append("540,854;")
            .append("540,960;")
            .append("600,800;")
            .append("600,1024;")
            .append("720,1184;")
            .append("720,1196;")
            .append("720,1280;")
            .append("750,1334;")
            .append("768,1024;")
            .append("800,1280;")
            .append("1080,1700;")
            .append("1080,1776;")
            .append("1080,1800;")
            .append("1080,1812;")
            .append("1080,1920;")
            .append("1200,1920;")
            .append("1440,2560;")
            .append("1600,2560;")*/
            //***************use to mobile**************
            .append("240,278;")
            .append("240,320;")
            .append("320,480;")
            .append("432,800;")
            .append("480,272;")
            .append("480,800;")
            .append("480,854;")
            .append("540,854;")
            .append("540,960;")
            .append("552,1024;")
            .append("600,800;")
            .append("600,1024;")
            .append("624,1280;")
            .append("656,1280;")
            .append("720,1184;")
            .append("720,1196;")
            .append("720,1280;")
            .append("750,1334;")
            .append("768,1024;")
            .append("736,1280;")
            .append("800,1280;")
            .append("800,1216;")
            .append("1080,1700;")
            .append("1080,1776;")
            .append("1080,1800;")
            .append("1080,1812;")
            .append("1080,1920;")
            .append("1137,1920;")
            .append("1200,1920;")
            .append("1440,2560;")
            .append("1440,2392;")
            .append("1600,2560;")
            .append("480,854")
            .toString();

    private DimenTools_PX(int size1, int size2, boolean isPortrait, String dimenFilePrefixName) {
        this.isPortrait = isPortrait;
        this.smallSize = Math.min(size1, size2);
        this.largeSize = Math.max(size1, size2);

        this.xDimenFileName = String.format("%sx.xml", dimenFilePrefixName);
        this.yDimenFileName = String.format("%sy.xml", dimenFilePrefixName);

        String targetSize = smallSize + "," + largeSize;
        if (!this.supportDimens.contains(targetSize)) {
            this.supportDimens += targetSize;
        }

        File dir = new File(DIRECTORY_RES);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        System.out.println(dir.getAbsoluteFile());
    }

    private void generate() {
        String[] values = supportDimens.split(";");
        for (String val : values) {
            String[] wh = val.split(",");
            generateXYXmlFile(Integer.parseInt(wh[0]), Integer.parseInt(wh[1]));
        }
    }

    private void generateXYXmlFile(int size1, int size2) {
        int sSize = Math.min(size1, size2);
        int lSize = Math.max(size1, size2);

        File fileDir = new File(DIRECTORY_RES + File.separator
                + TEMPLATE_VALUE.replace("{0}", String.valueOf(lSize))
                .replace("{1}", String.valueOf(sSize)));

        fileDir.mkdir();

        generateXmlFile(sSize, smallSize, isPortrait, fileDir);
        generateXmlFile(lSize, largeSize, !isPortrait, fileDir);
    }

    private void generateXmlFile(int size, int baseSize, boolean isWidth, File fileDir) {
        String templateSize;
        String fileName;

        if (isWidth) {
            templateSize = TEMPLATE_WIDTH;
            fileName = xDimenFileName;
        } else {
            templateSize = TEMPLATE_HEIGHT;
            fileName = yDimenFileName;
        }

        StringBuffer sizeBuffer = new StringBuffer()
                .append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources xmlns:tools=\"http://schemas.android.com/tools\" tools:ignore=\"ResourceName\">\n");

        float cellSize = size * 1.0f / baseSize;

        System.out.println("size : " + size + "," + baseSize + "," + cellSize);

        for (int i = 1; i < baseSize; i++) {
            sizeBuffer.append(
                    templateSize.replace("{0}", i + "")
                            .replace("{1}", "" + ((int) (cellSize * i * 100)) / 100f));
        }
        sizeBuffer.append(
                templateSize.replace("{0}", baseSize + "")
                        .replace("{1}", size + ""))
                .append("</resources>");

        File fileLay = new File(fileDir.getAbsolutePath(), fileName);
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(fileLay));
            pw.print(sizeBuffer.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //目标设备的屏幕分辨率,size1、size2没有限制要对应宽或高,size1可以是宽或高,size2同理
        int size1 = 720;
        int size2 = 1280;

        boolean isPortrait = false;

        String dimenFilePrefixName = "lay_";

        new DimenTools_PX(size1, size2, isPortrait, dimenFilePrefixName).generate();
    }
}