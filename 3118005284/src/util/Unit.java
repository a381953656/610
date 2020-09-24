package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import simhash.SimHash;
import simhash.Formula;

public class Unit {
	public static double ans(String filepath1, String filepath2) throws Exception {
		// ָ���ļ�
		String oldText = new String();
		String newText = new String();

		// ָ���ļ�·��
		String oldPath = filepath1;
		String newPath = filepath2;

		// �ļ�����
		BufferedReader oldBuff = new BufferedReader(new InputStreamReader(new FileInputStream(oldPath), "UTF8"));
		BufferedReader newBuff = new BufferedReader(new InputStreamReader(new FileInputStream(newPath), "UTF8"));
		String str;
		// ����ԭ�����ļ�
		while ((str = oldBuff.readLine()) != null) {
			oldText += str;
		}
		// �����µ��ļ�
		while ((str = newBuff.readLine()) != null) {
			newText += str;
		}

		oldBuff.close();
		newBuff.close();

		if (oldText.length() == 0 || newText.length() == 0) {
			throw new EmptyException("�ı�Ϊ��");
		}

		double ans;
		SimHash hash1 = new SimHash(oldText, 64);
		SimHash hash2 = new SimHash(newText, 64);
		int d = hash1.hammingDistance(hash2);// ���㺺������
		ans = Formula.getSimliar(d);
		System.out.println(ans);
		return ans;
	}

}
