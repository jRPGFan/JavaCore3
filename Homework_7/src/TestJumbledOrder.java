public class TestJumbledOrder {
    @Test(10)
    public void test1(){
        System.out.println(getClass().getSimpleName() + " test1 complete");
    }

    @AfterSuite
    public void testLast(){
        System.out.println(getClass().getSimpleName() + " testLast complete");
    }

    @Test(1)
    public void test2(){
        System.out.println(getClass().getSimpleName() + " test2 complete");
    }

    @BeforeSuite
    public void testFirst(){
        System.out.println(getClass().getSimpleName() + " testFirst complete");
    }

    @Test(5)
    public void test3(){
        System.out.println(getClass().getSimpleName() + " test3 complete");
    }
}
