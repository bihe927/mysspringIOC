package com.spring.core;

public class Main {
    public static void main(String[] args) {
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        Object zhao = app.getBean("zhao");
        System.out.println(zhao);
        Object qian = app.getBean("qian");
        System.out.println(qian);
        Object sun = app.getBean("sun");
        System.out.println(sun);
    }
}
