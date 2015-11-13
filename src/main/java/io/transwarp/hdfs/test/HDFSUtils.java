package io.transwarp.hdfs.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import java.io.IOException;

public final class HDFSUtils {
    // Add configuration.
    public static String[] Resources = {"./conf/core-site.xml", "./conf/hdfs-site.xml"};
    static Configuration configure;
    static FileSystem fs;
    static void initConfiguration() throws IOException {
        configure = new Configuration();
        for (String resource: Resources) {
            configure.addResource(new Path(resource));
        }
        configure.reloadConfiguration();
        fs = FileSystem.get(configure);
    }

    /**
     * Put a file into hdfs
     * @throws Exception
     */
    public static void uploadFile(String s, String d)throws Exception{
        Path src =new Path(s);
        Path dst = new Path(d);
        try {
            if (!fs.exists(dst)) {
                fs.copyFromLocalFile(src, dst);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Put a file into hdfs
     * @throws Exception
     */
    public static void makeDir(String p)throws Exception{
        Path path = new Path(p);
        try {
            if (!fs.exists(path)) {
                fs.mkdirs(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove a file(dir) from hdfs.
     * @throws Exception
     */
    public static void deleteFile(String p)throws Exception{
        FileSystem fs=FileSystem.get(configure);
        // Path p =new Path("hdfs://service/tmp/abc.txt");
        Path path = new Path(p);
        if (fs.exists(path)) {
            fs.delete(path, true);
        }
    }
}