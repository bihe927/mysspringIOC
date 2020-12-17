package com.spring.core;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ClassPathXmlApplicationContext implements ApplicationContext {

    private String ConfigFileName;

    public ClassPathXmlApplicationContext() {
        this.ConfigFileName = "applicationContext.xml";
    }

    public ClassPathXmlApplicationContext(String ConfigFileName) {
        this.ConfigFileName = ConfigFileName;
    }

    private Map<String,EntityBean> springXmlParser() throws Exception{
        //创建解读器
        XmlPullParser pullParser = XmlPullParserFactory.newInstance().newPullParser();
        //读取xml配置文件
        InputStream in = ClassPathXmlApplicationContext.class.getClassLoader().getResourceAsStream(this.ConfigFileName);
        pullParser.setInput(in,"utf-8");
        //基于事件机制编写xml解析，并且组装目标对象
        int everyType = pullParser.getEventType();
        Map<String ,EntityBean> beans = null;
        EntityBean bean = null;
        while (everyType != XmlPullParser.END_DOCUMENT){
            switch (everyType){
                case XmlPullParser.START_DOCUMENT:
                    beans = new HashMap<String, EntityBean>();
                    break;
                case XmlPullParser.START_TAG:
                    if ("bean".equals(pullParser.getName())){
                        bean = new EntityBean();
                        bean.setId(pullParser.getAttributeValue(null,"id"));
                        bean.setClassname(pullParser.getAttributeValue(null,"class"));
                    }
                    if ("property".equals(pullParser.getName())){
                        String attrName = pullParser.getAttributeValue(null,"name");
                        String attrVal = pullParser.getAttributeValue(null,"value");
                        bean.getProperties().put(attrName,attrVal);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("bean".equals(pullParser.getName())){
                        beans.put(bean.getId(),bean);
                    }
                    break;
            }
            everyType = pullParser.next();
        }
        return beans;
    }

    //通过解析来大的信息，通过反射远程对象的组装assemble
    public Map<String,Object> getIOC(Map<String,EntityBean> beansInfo) throws Exception{
        Map<String,Object> results = new HashMap<String, Object>();
        for (Map.Entry<String,EntityBean> beanInfo:beansInfo.entrySet()){
            String resultId = beanInfo.getKey();
            EntityBean bean = beanInfo.getValue();
            String classname = bean.getClassname();
            Map<String,String> properties = bean.getProperties();
            //反射-----输入字符串，返回对象
            Class clazz = Class.forName(classname);
            Object obj = clazz.newInstance();
            for (Map.Entry<String,String> property:properties.entrySet()){
                String propName = property.getKey();
                String propValue = property.getValue();
                StringBuilder builder = new StringBuilder("set");
                builder.append(propName.substring(0,1).toUpperCase());
                builder.append(propName.substring(1));
                String setterMethodName = builder.toString();
                Field field = clazz.getDeclaredField(propName);
                Method setMethod = clazz.getDeclaredMethod(setterMethodName,field.getType());
                if ("int".equals(field.getType().getName())){
                    setMethod.invoke(obj,Integer.parseInt(propValue));
                }else if ("java.lang.String".equals(field.getType().getName())){
                    setMethod.invoke(obj,propValue);
                }
            }
            results.put(resultId,obj);
        }
        return results;
    }

//    public static void main(String[] args) throws Exception {
//        ClassPathXmlApplicationContext cpxa = new ClassPathXmlApplicationContext();
//        Map<String,EntityBean> beans = cpxa.springXmlParser();
//        for (Map.Entry<String,EntityBean> entry:beans.entrySet()){
//            System.out.println(entry.getKey());
//            System.out.println(entry.getValue());
//            System.out.println("------------------------");
//        }
//    }




    @Override
    public Object getBean(String beanId) {
        Object result =null;
        try {
            Map<String,EntityBean> beansInfo = springXmlParser();
            Map<String,Object> results = getIOC(beansInfo);
            result = results.get(beanId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
