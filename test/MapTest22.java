package web.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import web.util.FileUtil;

public class MapTest22 extends Thread {

    public static void main(String[] args) throws Exception {

        /*
         * MapTest22 mt22 = new MapTest22();
         * mt22.setDaemon(true);
         * mt22.start();
         */

        String filelocation = "C:\\Users\\denni\\Downloads\\Java HotSpot VM Options.pdf";
        String fileLocation2 = filelocation.replace(filelocation.substring(filelocation.indexOf(".")), ".txt");
        String content = null;
        try {

            File afile = new File(filelocation);
            InputStream is = new FileInputStream(afile);
            byte[] abs = is.readAllBytes();
            // byte[] abs = is.readNBytes(is.available());
            content = new String(abs, Charset.forName("UTF8"));
            is.close();

        } catch (IOException e) {
            System.out.print(e.toString());
        }

        //System.out.println(content);
        FileUtil.writeContentToFile(content, fileLocation2,Charset.forName("UTF8"));

        /*
         * System.out.println("main");
         * TaskA taskA = new TaskA();
         * TaskB taskB = new TaskB();
         * TaskC taskC = new TaskC();
         * 
         * var t1 = new Thread(taskA);
         * t1.setName("thread4taskA");
         * t1.setPriority(MIN_PRIORITY);
         * t1.start();
         * 
         * var t2 = new Thread(taskB);
         * t2.setName("thread4taskB");
         * t2.setPriority(MAX_PRIORITY);
         * t2.start();
         * 
         * var tg = Thread.currentThread().getThreadGroup();
         * 
         * var t3 = new Thread(taskC);
         * t3.setName("thread4taskC");
         * t3.setPriority(MAX_PRIORITY);
         * t3.start();
         */

        // ExecutorService es = new ThreadPoolExecutor(NORM_PRIORITY, MIN_PRIORITY,
        // MAX_PRIORITY, null, null);

    }

}

class TaskA implements Runnable {

    private String taskname;

    private String taskdesc;

    private Date addedon;

    public TaskA() {
        System.out.println("const of TaskA");
    }

    public void run() {
        System.out.println("run of TaskA");
        logItems();
    }

    void logItems() {
        try {

            for (int i = 1; i < 500; i++) {

                if (i == 500 / 2) {
                    var tg = Thread.currentThread().getThreadGroup();
                    tg.activeCount();
                }

                if (i == 499) {
                    System.out.println("from " + getClass().getSimpleName() + ": " + i);
                    // wait(500);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class TaskB implements Runnable {

    public TaskB() {
        System.out.println("const of TaskB");
    }

    public void run() {
        System.out.println("run of TaskB");
        logItems();
    }

    void logItems() {
        try {

            for (int i = 1; i < 500; i++) {

                if (i == 500 / 2) {
                    var tg = Thread.currentThread().getThreadGroup();
                    tg.activeCount();
                    Thread.sleep(500);
                }

                if (i == 499) {
                    System.out.println("from " + getClass().getSimpleName() + ": " + i);
                    // wait(500);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class TaskC implements Runnable {

    public TaskC() {
        System.out.println("const of TaskC");
    }

    public void run() {
        System.out.println("run of TaskC");
        logItems();
    }

    void logItems() {
        try {

            for (int i = 1; i < 500; i++) {

                if (i == 499) {
                    System.out.println("from " + getClass().getSimpleName() + ": " + i);
                    // wait(500);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
