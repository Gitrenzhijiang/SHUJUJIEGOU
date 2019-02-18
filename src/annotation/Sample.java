package annotation;

public class Sample {
    @Test
    public static void m1() {
        // Test should pass
    }
    
    @Test
    public static void m2() {
        // Test should fail
        throw new RuntimeException("Test m2");
    }
    
    @Test
    public void m3() {
        // Test no static 
    }
    
    public void m4() {
        
    }
    
    public static void m5() {
        
    }
    @Test
    private static void m6() {
        System.out.println("m6");
    }
    @Test
    private static void m7(String str) {
        System.out.println("m7");
    }
    
    @Test
    public static void m8(String str) {
        System.out.println("m8");
    }
}
