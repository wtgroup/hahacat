package com.wtgroup.test.stop;

/**
 *
 关闭钩子预留一定时间, 可以让主线程将循环跑完
 $ java -jar test-stop.jar
 程序启动
 循环第0次
 循环第10000000次
 循环第20000000次
 循环第30000000次
 循环第40000000次       (按下Ctrl+C)
 停止程序...
 休息10s再停止
 循环第50000000次
 循环第60000000次
 循环第70000000次
 循环第80000000次
 循环第90000000次
 结束循环
 刚休息了3s
 Stopped!
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/9/11 16:26
 */
public class Laucher {

    public static void main(String[] args) {
        System.out.println("程序启动");
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("停止程序...");
               /* System.out.println("休息10s再停止");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                System.out.println("Stopped!");
            }
        }));

//        int i = 0;
//        boolean running = true;
        for (int i = 0; i < 100000000; i++) {
            if (i % 10000000 == 0) {
                System.out.println("循环第" + i + "次");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }

        System.out.println("结束循环");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("刚休息了3s");

    }
}
