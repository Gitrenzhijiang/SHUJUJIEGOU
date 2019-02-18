package algorithm2;

public class BloomFilter {

    public static void main(String[] args) {
        int n = 500;
        BloomFilter bf = new BloomFilter();
        for (int i = 0;i < n;i++) {
            bf.insert(3 + i);
        }
        for (int i = n/2;i < n+3;i++) {
            if (bf.exist(3 + i)==false) {
                System.out.println("false:" + i);
            }
        }
        
    }
    public void insert(Object key) {
        m[hash1(key)] = 1;
        m[hash2(key)] = 1;
        m[hash3(key)] = 1;
        m[hash4(key)] = 1;
    }
    /**
     * 返回存在也许是假的，也许是真的；
     * 返回不存在一定是真的
     * @param key
     * @return
     */
    public boolean exist(Object key) {
        if (m[hash1(key)] == 1 && m[hash2(key)] == 1 && m[hash3(key)] == 1 && m[hash4(key)] == 1) {
            return true;
        }else {
            return false;
        }
    }
    private static byte[] m = new byte[1024]; // m数组的长度
    static int m_len = m.length;
    // m_len 必须是2的幂
    static int hash1(Object obj) {
        int hash = obj.hashCode();
        return ((hash >>> 16) ^ hash) & (m_len-1);
    }
    static int hash2(Object obj) {
        int hash = obj.hashCode();
        return ((2^hash >>> 15) | hash) & (m_len-1);
    }
    static int hash3(Object obj) {
        int hash = obj.hashCode();
        return ((hash >>> 14) & hash) & (m_len-1);
    }
    static int hash4(Object obj) {
        int hash = obj.hashCode();
        return ((hash >>> 13) ^~ hash) & (m_len-1);
    }
}
