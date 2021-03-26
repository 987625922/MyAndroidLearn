package com.learn.learn.bassis.反射;

import com.wyt.common.entity.Entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2021/3/26 16:42
 * <p>
 * 获取Class对象的三种方式
 * 1 Object ——> getClass();
 * 2 任何数据类型（包括基本数据类型）都有一个“静态”的class属性
 * 3 通过Class类的静态方法：forName（String  className）(常用)
 * 一个类，只有一个Class对象产生
 */
class Main {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {

        //第一种方式获取Class对象  
        Entity entity1 = new Entity();
        Class entityClass = entity1.getClass();
        System.out.println(entityClass.getName());

        //第二种方式获取Class对象
        Class entityClass2 = Entity.class;
        System.out.println(entityClass == entityClass2);

        //第三种方式获取Class对象
        try {
            Class entityClass3 = Class.forName("com.wyt.common.entity.Entity");
            System.out.println(entityClass3 == entityClass2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /**
         * ========= 获取构造方法
         */
        //获取所有“公共”的方法
        Constructor<?>[] publicConstructors = entityClass.getConstructors();
        //获取所有的构造方法(包括私有、受保护、默认、公有)
        Constructor<?>[] constructors = entityClass.getDeclaredConstructors();
        //获取单个的"公有的"构造方法
        Constructor publicConstructor = entityClass.getConstructor(String.class);
        //获取"某个构造方法"可以是私有的，或受保护、默认、公有
        try {
            Constructor constructor = entityClass.getDeclaredConstructor(null);
        } catch (Exception e) {

        }
        //调用构造函数
        Entity newInstanceEntity = (Entity) publicConstructor.newInstance("名字");
        System.out.println(newInstanceEntity.getName());

        /**
         * ========= 获取成员变量
         */
        //获取所有"公有"的字段
        Field[] publicFields = entityClass.getFields();
        for (Field field : publicFields) {
            System.out.println(field);
        }
        System.out.println("============ ");
        //获取所有的字段(包括私有、受保护、默认的)
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field);
        }
        //获取单个“公有”的字段
        Field publicField = entityClass.getField("id");
        System.out.println(publicField);
        //获取单个字段
        Field field = entityClass.getDeclaredField("name");
        System.out.println(field);
        //设置“公共”的参数
        publicField.set(newInstanceEntity, 1);
        System.out.println(newInstanceEntity.getId());
        //设置参数
        //解除私有限定
        field.setAccessible(true);
        field.set(newInstanceEntity, "修改的名字");
        System.out.println(newInstanceEntity.getName());

        /**
         * ============== 获取成员方法
         */
        //获取所有的公有方法（包含父类的方法也包含Object类）
        Method[] publicMethods = entityClass.getMethods();
        for (Method method : publicMethods) {
            System.out.println(method);
        }
        //获取所有的方法(包括私有、受保护、默认的)
        Method[] methods = entityClass.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
        //获取单个公有的方法
        Method publicMethod = entityClass.getMethod("setName", String.class);
        //获取单个方法(包括私有、受保护、默认的)
        Method method = entityClass.getDeclaredMethod("privateName", null);
        //使用公有的方法
        publicMethod.invoke(newInstanceEntity, "通过反射方法设置的参数");
        //使用方法(包括私有、受保护、默认的)
        //解除私有限定
        method.setAccessible(true);
        String resulet = (String) method.invoke(newInstanceEntity, null);
        System.out.println("调用私有方法获取到的参数 "+resulet);

    }
}
