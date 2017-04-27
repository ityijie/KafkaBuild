package hello;

import org.joda.time.LocalTime;

/**
 * Created by Administrator on 2017/4/25.
 */
public class HelloWorld {
    public static void main(String[] args) {
        LocalTime currentTime = new LocalTime();
        System.out.println("The current local time is: " + currentTime);

        Greeter greeter = new Greeter();
        System.out.println(greeter.sayHello());

    }
}
