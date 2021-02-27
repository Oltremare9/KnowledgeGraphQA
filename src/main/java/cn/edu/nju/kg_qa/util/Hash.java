package cn.edu.nju.kg_qa.util;

import com.google.common.hash.Hashing;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * Description: <br/>
 * date: 2021/2/27 0:53<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public class Hash {

    /**
     * murmur hash算法实现
     */
    public static Long hash(byte[] key) {

        ByteBuffer buf = ByteBuffer.wrap(key);
        int seed = 0x1234ABCD;

        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);

        long m = 0xc6a4a7935bd1e995L;
        int r = 47;

        long h = seed ^ (buf.remaining() * m);

        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }

        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(
                    ByteOrder.LITTLE_ENDIAN);
            // for big-endian version, do this first:
            // finish.position(8-buf.remaining());
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        buf.order(byteOrder);
        return h;
    }

    /**
     * murmur哈希
     * @param key
     * @return
     */
    public static Long hash(String key) {
        return hash(key.getBytes());
    }



    /**
     * Long转换成无符号长整型（C中数据类型）
     */
    public static BigDecimal readUnsignedLong(long value) {
        if (value >= 0) {
            return new BigDecimal(value);
        }
        long lowValue = value & 0x7fffffffffffffffL;
        return BigDecimal.valueOf(lowValue).add(BigDecimal.valueOf(Long.MAX_VALUE)).add(BigDecimal.valueOf(1));
    }

    /**
     * 返回无符号murmur hash值
     */
    public static BigDecimal hashUnsigned(String key) {
        return readUnsignedLong(hash(key));
    }
    public static BigDecimal hashUnsigned(byte[] key) {
        return readUnsignedLong(hash(key));
    }

    // 将字符串转成hash值
    public static int stringToHash(String key) {
        int arraySize = 11113; // 数组大小一般取质数
        int hashCode = 0;
        for (int i = 0; i < key.length(); i++) { // 从字符串的左边开始计算
            int letterValue = key.charAt(i) - 96;// 将获取到的字符串转换成数字，比如a的码值是97，则97-96=1
            // 就代表a的值，同理b=2；
            hashCode = ((hashCode << 5) + letterValue) % arraySize;// 防止编码溢出，对每步结果都进行取模运算
        }
        return hashCode;
    }
}
