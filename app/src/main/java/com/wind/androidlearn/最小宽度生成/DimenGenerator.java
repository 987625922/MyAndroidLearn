package com.wind.androidlearn.最小宽度生成;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DimenGenerator {

    /**
     * 设计稿尺寸(将自己设计师的设计稿的宽度填入)
     */
    private static final int DESIGN_WIDTH = 1280;

    /**
     * 设计稿的高度  （将自己设计师的设计稿的高度填入）
     */
    private static final int DESIGN_HEIGHT = 800;

    public static void main(String[] args) {
        int smallest = DESIGN_WIDTH>DESIGN_HEIGHT? DESIGN_HEIGHT:DESIGN_WIDTH;  //     求得最小宽度
        DimenTypes[] values = DimenTypes.values();
        for (DimenTypes value : values) {
            File file = new File("");
            System.out.println(file.getAbsolutePath()+"\\app\\src\\main\\res");
            MakeUtils.makeAll(smallest, value, file.getAbsolutePath()+"\\app\\src\\main\\res");
        }
    }

}
