package simhash;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.HashMap;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class SimHash {
    private String tokens;

    public BigInteger intSimHash;

    private String strSimHash;

    private int hashbits = 64;

    public SimHash(String tokens) throws IOException {
        this.tokens = tokens;
        this.intSimHash = this.simHash();
    }

    public SimHash(String tokens, int hashbits) throws IOException {
        this.tokens = tokens;
        this.hashbits = hashbits;
        this.intSimHash = this.simHash();
    }

    HashMap<String, Integer> wordMap = new HashMap<String, Integer>();

    public BigInteger simHash() throws IOException {
        // 定义特征向量
        int[] v = new int[this.hashbits];
        // 将文本去掉标点符号后分词
        StringReader sr = new StringReader(tokens);// 创建文本输入流，读入文本
        IKSegmenter ik = new IKSegmenter(sr, true);// 导入外部IKSegmenter的jar包
        Lexeme lex = null;
        while ((lex = ik.next()) != null) {
            // 将每一个分词hash为一组固定长度的数列.比如 64bit 的一个整数.
            BigInteger t = this.hash(lex.getLexemeText());
            for (int i = 0; i < this.hashbits; i++) {
                BigInteger bitmask = new BigInteger("1").shiftLeft(i);
                // 建立一个长度为64的整数数组(假设要生成64位的数字指纹,也可以是其它数字),
                // 对每一个分词hash后的数列进行判断,如果是1000...1,那么数组的第一位和末尾一位加1,
                // 中间的62位减1,也就是说,逢1加1,逢0减1.一直到把所有的分词hash数列全部判断完毕.
                if (t.and(bitmask).signum() != 0) {
                    // 这里是计算整个文档的所有特征的向量和
                    // 这里实际使用中需要 +- 权重
                    v[i] += 1;
                } else {
                    v[i] -= 1;
                }
            }
        }
        BigInteger fingerprint = new BigInteger("0");
        StringBuffer simHashBuffer = new StringBuffer();
        for (int i = 0; i < this.hashbits; i++) {
            // 最后对数组进行判断,大于0的记为1,小于等于0的记为0,得到一个 64bit 的数字指纹/签名.
            if (v[i] >= 0) {
                fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
                simHashBuffer.append("1");
            } else {
                simHashBuffer.append("0");
            }
        }
        this.strSimHash = simHashBuffer.toString();
        return fingerprint;
    }

    public BigInteger hash(String source) {
        if (source == null || source.length() == 0) {
            return new BigInteger("0");
        } else {
            char[] sourceArray = source.toCharArray();
            BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
            BigInteger m = new BigInteger("1000003");
            BigInteger mask = new BigInteger("2").pow(this.hashbits).subtract(new BigInteger("1"));
            for (char item : sourceArray) {
                BigInteger temp = BigInteger.valueOf((long) item);
                x = x.multiply(m).xor(temp).and(mask);
            }
            x = x.xor(new BigInteger(String.valueOf(source.length())));
            if (x.equals(new BigInteger("-1"))) {
                x = new BigInteger("-2");
            }
            return x;
        }
    }

    public int hammingDistance(SimHash other) {

        BigInteger x = this.intSimHash.xor(other.intSimHash);
        int tot = 0;

        // 统计x中二进制位数为1的个数
        // 一个二进制数减去1，从最后那个1（包括那个1）后面的数字全都反了，然后，n&(n-1)就相当于把后面的数字清0，
        // 计算n能够完成多少次以上操作

        while (x.signum() != 0) {
            tot += 1;
            x = x.and(x.subtract(new BigInteger("1")));
        }
        return tot;
    }

}
