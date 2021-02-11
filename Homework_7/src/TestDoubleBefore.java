public class TestDoubleBefore {
    @BeforeSuite
    public void testFirst(){
        System.out.println(getClass().getSimpleName() + " testFirst complete");
    }

    @BeforeSuite
    public void testFirst2(){
        System.out.println(getClass().getSimpleName() + " testFirst complete");
    }
    @Test(10)
    public void test1(){
        System.out.println(getClass().getSimpleName() + " test1 complete");
    }

    @Test(1)
    public void test2(){
        System.out.println(getClass().getSimpleName() + " test2 complete");
    }
}
