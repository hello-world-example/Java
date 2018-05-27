package xyz.kail.javase.demo.sort;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kail on 2018/4/17.
 */
@Configuration
@ComponentScan(basePackageClasses = Main.class)
public class Main extends TimerTask {

    @Resource
    private HelloService helloService;

    @Override
    public void run() {
        helloService.print();
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        Main bean = context.getBean(Main.class);

        Timer timer = new Timer();
        timer.schedule(bean, 0, 1000);

        
    }


    @Service
    public static class HelloService {

        public void print() {
            System.out.println("123123");
        }
    }


}
