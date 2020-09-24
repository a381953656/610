package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import org.junit.Test;

import util.EmptyException;
import util.Unit;

class Test1 {

    public static void main(String[] args) throws Exception {
        if(args.length!=3){
            System.out.println("输入更多");
            return;
        }
        String path1 = args[0];// 原文本绝对路径  E:\360MoveData\Users\L\Desktop\tests\orig.txt
        String path2 = args[1];// 抄袭文本路径    E:\360MoveData\Users\L\Desktop\tests\orig_0.8_add.txt、orig_0.8_del.txt、orig_0.8_dis_1.txt
        String path3 = args[2];// 结果文件路径    E:\360MoveData\Users\L\Desktop\tests\res.txt
        System.out.println(path1.substring(37) + "文本与" + path2.substring(37) + "文本的相似度为：");
        double ans = Unit.ans(path1, path2);
        String data = "" + ans;
        File outputFile = new File(path3);
        FileWriter output = new FileWriter(outputFile);
        char[] chars = data.toCharArray();
        output.write(chars);
        output.close();
        System.out.println("-----------------------------------------");
        //Thread.sleep(20000);
    }

}
