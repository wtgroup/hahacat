package com.wtgroup.demo.demospringboot;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.*;

/**
 * 超时测试
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-06-27-0:04
 */
public class TestThreadAndGetIPMac {

    @Test
    public void fun1() throws IOException {
        //获取本地IP的Mac地址
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost);
        byte[] mac = NetworkInterface.getByInetAddress(localHost).getHardwareAddress();
        System.out.println(mac);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");     //Mac地址分隔符 or :
            }
            String s = Integer.toHexString(mac[i] & 0xFF);      //0xFF == 255 == B11111111 (8个1) 取与后, 255最大为ff, 256重新从0开始 -> 所以保证了多大数组都不会高于两位的16进制(i.e.ff)
            sb.append(s.length() == 1 ? 0 + s : s);
        }
        String macAddr = sb.toString().trim().toUpperCase().toUpperCase();
        System.out.println(macAddr);

        //获取非本地IP地址
        String remoteIp = "";
        //执行cmd命令
        Process p = Runtime.getRuntime().exec("nbtstat -A" + remoteIp);
        //拿出执行命令获得的流
        InputStream in = p.getInputStream();
//        读取流, 拿出地址
//        ...

    }


    public static void main(String[] args) throws InterruptedException,ExecutionException {
        final ExecutorService exec = Executors.newFixedThreadPool(1);
        Callable<String> call = new Callable<String>() {
            @Override
            public String call() throws Exception {
                //开始执行耗时操作
                Thread.sleep(1000 * 3);
                return "执行完成!";
            }
        };
        try {
            System.out.println("submit 开始");
            Future<String> future = exec.submit(call);
            System.out.println("submit 结束");
            String obj = future.get(1000, TimeUnit.MILLISECONDS); //任务处理超时时间设为 1 秒 / 获取call()的返回值
            System.out.println("任务成功返回:" + obj);
            System.out.println(obj+"2342342342423");
        } catch (TimeoutException ex) {
            System.out.println("处理超时啦....");
            ex.printStackTrace();
        } catch (Exception e) {
            System.out.println("处理失败.");
            e.printStackTrace();
        }
        // 关闭线程池
        exec.shutdown();
    }


}
