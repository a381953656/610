package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import simhash.SimHash;
import simhash.Formula;

public class Unit {
	public static double ans(String filepath1, String filepath2) throws Exception {
		// 指定文件
		String oldText = new String();
		String newText = new String();

		// 指定文件路径
		String oldPath = filepath1;
		String newPath = filepath2;

		// 文件读入
		BufferedReader oldBuff = new BufferedReader(new InputStreamReader(new FileInputStream(oldPath), "UTF8"));
		BufferedReader newBuff = new BufferedReader(new InputStreamReader(new FileInputStream(newPath), "UTF8"));
		String str;
		// 读入原来的文件
		while ((str = oldBuff.readLine()) != null) {
			oldText += str;
		}
		// 读入新的文件
		while ((str = newBuff.readLine()) != null) {
			newText += str;
		}

		oldBuff.close();
		newBuff.close();

		if (oldText.length() == 0 || newText.length() == 0) {
			throw new EmptyException("文本为空");
		}

		double ans;
		SimHash hash1 = new SimHash(oldText, 64);
		SimHash hash2 = new SimHash(newText, 64);
		int d = hash1.hammingDistance(hash2);// 计算汉明距离
		ans = Formula.getSimliar(d);
		System.out.println(ans);
		return ans;
	}

}
