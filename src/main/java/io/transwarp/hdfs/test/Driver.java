package io.transwarp.hdfs.test;

import java.util.Random;

/**
 * Put and Delete file crazilly to hdfs.
 */
public class Driver {
    public static void main(String[] args)throws Exception {
        HDFSUtils.initConfiguration();
        Random random = new Random();
        if (args.length != 1) {
            System.err.println("Host name is needed.");
        }
        String hostName = args[0];
        for (int i = 0; i < 7; ++i) {
            new Putter("putter_" + hostName + "_" + i).start();
        }
        for (int i = 0; i < 4; ++i) {
            // new Remover("remover_" + i).start();
        }
    }
}

class Putter extends Thread {
    Random random = new Random();
    public Putter(String name) {
        super(name);
    }
    public void run() {
        while (true) {
            try {
                String threadName = getName();
                HDFSUtils.makeDir("hdfs://service/tmp/" + threadName);
                for (int i = 0; i < 4096; ++i) {
                    // int r = Math.abs(random.nextInt()) % 4096;
                    HDFSUtils.uploadFile("/tmp/hdfs_test/data_" + i, "hdfs://service/tmp/" + threadName + "/data_" + i);
                }
                HDFSUtils.deleteFile("hdfs://service/tmp/" + getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class Remover extends Thread {
    public Remover(String name) {
        super(name);
    }
    public void run() {
        while (true) {
            try {
                HDFSUtils.deleteFile("hdfs://service/tmp/data_*");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}