package com.wyt.learn.test.base;


import com.wyt.learn.test.base.data.Student;
import com.wyt.learn.test.base.data.UserContract;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * @Author: LL
 * @Description:mockito的学习
 * @Date:Create：in 2021/2/2 14:18
 */
public class Main {

    /**
     * 注入list
     */
    @Mock
    private ArrayList mockList;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * 简单例子
     *
     * @throws Exception
     */
    @Test
    public void sampleTest1() throws Exception {
        /**
         * 使用mock数据执行方法
         */
        mockList.add("one");
        mockList.clear();

        /**
         * 检验方法是否调用
         */
        verify(mockList).add("one");
        verify(mockList).clear();
    }

    /**
     * 直接mock对象
     *
     * @throws Exception
     */
    @Test
    public void sampleTest2() throws Exception {
        //直接mock一个接口
        UserContract contract = mock(UserContract.class);
        //当UserContract调用getUserName时返回设置的值
        when(contract.getUserName()).thenReturn("测试UserName");
        String userName = contract.getUserName();

        //校验 是否contract调用了getUserName方法
        verify(contract).getUserName();
        Assert.assertEquals("测试UserName", userName);

        String password = contract.getPassword();
        //校验 是否调用了getPassword
        verify(contract).getPassword();
        Assert.assertEquals(null, password);

        /**
         * 方法连续调用
         */
        when(contract.getUserName())
                .thenReturn("方法第一次调用")
                .thenThrow(new RuntimeException("方法第二次调用抛出异常"))
                .thenReturn("方法第三次调用");

        String name1 = contract.getUserName();
        try {
            String name2 = contract.getUserName();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String name3 = contract.getUserName();

        System.out.println(name1);
        System.out.println(name3);
    }

    /**
     * 参数匹配器
     *
     * @throws Exception
     */
    @Test
    public void argumentMatchersTest3() throws Exception {
        when(mockList.get(anyInt())).thenReturn("不管请求第几个参数，都返回这句");
        System.out.println(mockList.get(0));
        System.out.println(mockList.get(10));

        /**
         * 匹配器的使用
         * 当mocklist调用addAll方法时，匹配器如果传入的参数list size== 2，返回true
         */
        when(mockList.addAll(argThat(list -> list.size() == 3))).thenReturn(true);

        boolean isAddAllData = mockList.addAll(Arrays.asList("one", "two"));
        boolean isAddAllData1 = mockList.addAll(Arrays.asList("one", "two", "three"));

        verify(mockList).addAll(argThat(list -> list.size() == 3));
        Assert.assertTrue(isAddAllData);
        Assert.assertTrue(!isAddAllData1);
    }

    /**
     * 检测方法调用的次数
     *
     * @throws Exception
     */
    @Test
    public void sampleTest4() throws Exception {
        mockList.add("once");

        mockList.add("twice");
        mockList.add("twice");

        mockList.add("three times");
        mockList.add("three times");
        mockList.add("three times");

        verify(mockList).add("once");  //验证mockList.add("once")调用了一次 - times(1) is used by default
        verify(mockList, times(1)).add("once");//验证mockList.add("once")调用了一次

        //调用多次校验
        verify(mockList, times(2)).add("twice");
        verify(mockList, times(3)).add("three times");

        //从未调用校验
        verify(mockList, never()).add("four times");

        //至少、至多调用校验
        verify(mockList, atLeastOnce()).add("three times");
        verify(mockList, atMost(5)).add("three times");
    }

    /**
     * 异常抛出测试
     */
    @Test
    public void throwTest5() {
        doThrow(new NullPointerException("throwTest5.抛出空指针异常")).when(mockList).clear();
        doThrow(new IllegalArgumentException("你的参数似乎有点问题")).when(mockList).add(anyInt());

        mockList.add("string");
        mockList.add(12);
        mockList.clear();
    }

    /**
     * 校验方法的执行顺序
     *
     * @throws Exception
     */
    @Test
    public void orderTest6() throws Exception {
        List singleMock = mock(List.class);

        singleMock.add("first add");
        singleMock.add("second add");

        InOrder inOrder = inOrder(singleMock);

        //inOrder校验方法执行顺序
        inOrder.verify(singleMock).add("first add");
        inOrder.verify(singleMock).add("second add");

        List firstMock = mock(List.class);
        List secondMock = mock(List.class);

        firstMock.add("first add");
        secondMock.add("second add");
        InOrder inOrder1 = inOrder(firstMock, secondMock);

        //下列代码会确认是否firstmock优先secondMock执行add方法
        inOrder1.verify(firstMock).add("first add");
        inOrder1.verify(secondMock).add("second add");
    }

    /**
     * 确保对象从未进行过交互
     *
     * @throws Exception
     */
    @Test
    public void noInteractedTest7() throws Exception {
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);
        List thirdMock = mock(List.class);

        firstMock.add("one");

        verify(firstMock).add("one");

        verify(firstMock, never()).add("two");

        firstMock.add(thirdMock);
        //确保交互操作不会执行在mock对象上
//        verifyZeroInteractions(firstMock);//test failed 因为firstMock和其他的mock对象有交互
        verifyZeroInteractions(secondMock, thirdMock);
    }

    /**
     * 拦截方法返回值
     */
    @Test
    public void returnTest() throws Exception {
        //当list clear了之后回调
        doAnswer(invocation -> {
            System.out.println("测试无返回值的函数");
            return null;
        }).when(mockList).clear();
        mockList.clear();

        doThrow(new RuntimeException("当list添加 下标为一的参数时 抛出异常"))
                .when(mockList).add(eq(1), anyString());

        doNothing().when(mockList).add(eq(2), anyString());

//        不能把空返回值的对象和doreturn关联，因为mockList是mock对象，并没有数据
//        doReturn("返回测试").when(mockList).add(eq(3),anyString());

        mockList.add(2, "123");
        mockList.add(3, "123");
        mockList.add(4, "123");
        mockList.add(5, "123");
        mockList.add(6, "123");
    }

    /**
     * spy监控真实的对象
     */
    @Test
    public void spyTest() {
        List list = new ArrayList();
        List spyList = spy(list);

        /**
         * 当spyList调用size方法时，返回100
         */
        when(spyList.size()).thenReturn(100);

        spyList.add("one");
        spyList.add("two");

        verify(spyList).add("one");
        verify(spyList).add("two");

        //请注意！下面这行代码会报错！ java.lang.IndexOutOfBoundsException: Index: 10, Size: 2
        //不可能 : 因为当调用spy.get(0)时会调用真实对象的get(0)函数,此时会发生异常，因为真实List对象是空的
//        when(spyList.get(10)).thenReturn("ten");

        //应该这么使用
        doReturn("ten").when(spyList).get(9);
        doReturn("eleven").when(spyList).get(10);

        System.out.println("spyList第十个元素" + spyList.get(9));
        System.out.println("spyList第十一个元素" + spyList.get(10));

        //Mockito并不会为真实对象代理函数调用，
        // 实际上它会拷贝真实对象。因此如果你保留了真实对象并且与之交互
        //不要期望从监控对象得到正确的结果。
        // 当你在监控对象上调用一个没有被stub的函数时并不会调用真实对象的对应函数，你不会在真实对象上看到任何效果。
    }

    /**
     * 捕获参数
     *
     * @throws Exception
     */
    @Test
    public void captorTest() throws Exception {
        Student student = new Student();
        student.setName("学生名字");

        /**
         * 参数捕获器
         */
        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);

        mockList.add(student);
        /**
         * 校验是否添加了student
         */
        verify(mockList).add(captor.capture());

        /**
         * 通过捕获器，获取value
         */
        Student value = captor.getValue();
        Assert.assertEquals(value.getName(),"学生名字");
    }
}
