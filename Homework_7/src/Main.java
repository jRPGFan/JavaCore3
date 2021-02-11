import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        start(TestOK.class);
        //start(TestDoubleBefore.class); //RuntimeException
        start(TestNoSuites.class);
        //start(TestUnknownAnnotation.class); //RuntimeException
        start(TestJumbledOrder.class);
    }

    public static void start (Class cl){
        Method[] methods = cl.getDeclaredMethods();
        List<Method> testMethods = new ArrayList<>();
        boolean hasBefore = false;
        boolean hasAfter = false;
        String testType = "";

        for (Method method : methods) {
            testType = method.getDeclaredAnnotations()[0].annotationType().getSimpleName();
            if(testType == "BeforeSuite"){
                if (!hasBefore) hasBefore = true;
                else throw new RuntimeException("Only one BeforeSuite per test allowed");
            }

            else if(testType == "AfterSuite") {
                if(!hasAfter) hasAfter = true;
                else throw new RuntimeException("Only one AfterSuite per test allowed");
            }

            else if(testType == "Test") testMethods.add(method);

            else throw new RuntimeException("Unknown test type encountered: " + testType);
        }

        testMethods.sort(Comparator.comparingInt(method -> method.getAnnotation(Test.class).value()));

        if (hasBefore || hasAfter){
            for (Method method: methods) {
                testType = method.getDeclaredAnnotations()[0].annotationType().getSimpleName();
                if(testType == "BeforeSuite") testMethods.add(0, method);

                else if(testType == "AfterSuite") testMethods.add(method);
            }
        }

        for (Method method : testMethods) {
            if (method.getDeclaredAnnotations()[0].annotationType().getSimpleName() != "BeforeSuite" &&
                    method.getDeclaredAnnotations()[0].annotationType().getSimpleName() != "AfterSuite") {
                try {
                    System.out.print("Launching test with priority value = " + method.getDeclaredAnnotation(Test.class).value() + ". ");
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }

            try {
                method.invoke(cl.getDeclaredConstructor().newInstance(), null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        System.out.println();
    }
}
