package person.ll.foundation.juc;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * CopyOnWriteArrayList只能保证数据的最终一致性，
 * 不能保证数据的实时一致性——读操作读到的数据只是一份快照。
 * 所以如果希望写入的数据可以立刻被读到，那CopyOnWriteArrayList并不适合。
 */
public class CopyOnWriteArrayListTest {

    public static void main(String[] args) throws Exception{

        test(1);
//        test(0);


    }

    /**
     *  CopyOnWriteArrayList 同一线程读多次取数据不一致
     * @param ca  1 数据不一致情况       其他  数组越界
     * @throws InterruptedException
     */
    private static void test(int ca) throws InterruptedException {
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add(0,0);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CountDownLatch countDownLatch2 = new CountDownLatch(1);
        Thread readThread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"第一次读:"+copyOnWriteArrayList.get(0));
            countDownLatch2.countDown();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName()+"第二次读:"+copyOnWriteArrayList.get(0));
        },"readThread");
        Thread wtiteThread = new Thread(() -> {
            try {
                countDownLatch2.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            copyOnWriteArrayList.remove(0);
            if(ca == 1){
                copyOnWriteArrayList.add(0,1);
            }
            countDownLatch.countDown();
        });
        readThread.start();
        wtiteThread.start();
        wtiteThread.join();
        readThread.join();
        System.out.println("over");
    }


}
