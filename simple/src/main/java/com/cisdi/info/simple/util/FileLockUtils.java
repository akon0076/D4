package com.cisdi.info.simple.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by 向鑫 on 2019/1/16
 */
public class FileLockUtils {
    private final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void writeStringToFile(final File file, final String data,final String encoding) {
        lock.writeLock().lock();
        try{
            OutputStreamWriter writer=new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file)),Charset.forName(encoding));
             writer.write(data);
             writer.flush();
             writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
             lock.writeLock().unlock();
        }
    }

    public static String readFileToString(final File file, final String encoding) {
          lock.readLock().lock();
          try {
              InputStreamReader reader = new InputStreamReader(new BufferedInputStream(new FileInputStream(file)),Charset.forName(encoding));
              char[] buffer = new char[1024];
              StringBuilder builder = new StringBuilder();
              int len=-1;
              while ((len=reader.read(buffer))!=-1){
                  builder.append(buffer,0,len);
              }
              reader.close();
             return builder.toString();
          } catch (FileNotFoundException e) {
              e.printStackTrace();
          } catch (IOException e) {
              e.printStackTrace();
          } finally {
              lock.readLock().unlock();
          }
        return null;
    }
}
