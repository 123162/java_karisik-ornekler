package helloworld;

public class HelloWorld {

    public static void main(String[] args) {

        try {
            throwException();
        } catch (Exception exception) {
            System.err.println("Exception handled in main..\n");//4444444444444444
        }
        doesNotThrowException();
    }

    private static void throwException() throws Exception {
        try {
            System.err.println("Method throwException..\n");//1111111111111
            throw new Exception();
        } catch (Exception exception) {
            System.err.println("Exception handled in method throwException..\n");//2222222222222
            throw exception;
        } finally {
            System.err.println("Finally executed in throwException..\n");//33333333333333333
        }
    }

    private static void doesNotThrowException() {
        try {
            System.out.println("Method doesNotThrowException..\n");//5555555555555
        } catch (Exception exception) {
            System.err.println("exception\n");
        } finally {
            System.err.println("Finally executed in doesNotThrowException.\n");//666666666666666
        }
    }
}
