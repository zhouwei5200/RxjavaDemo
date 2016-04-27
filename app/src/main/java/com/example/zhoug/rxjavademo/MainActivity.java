package com.example.zhoug.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // demo1();
        // demo2();
         //demo3();
      //  demo4();
        demo5();
    }

    private void demo5() {
        /**
         * 线程控制
         *  在不指定线程的情况下， RxJava 遵循的是线程不变的原则，即：在哪个线程调用 subscribe()，
         *  就在哪个线程生产事件；在哪个线程生产事件，
         *  就在哪个线程消费事件。如果需要切换线程，就需要用到 Scheduler （调度器）。
         *
         *  Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
            Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
            Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
            Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
            另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
            有了这几个 Scheduler ，就可以使用 subscribeOn() 和 observeOn() 两个方法来对线程进行控制了。

         subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。
         observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
         */
        Action1<String> action1 = new Action1<String>() {


            @Override
            public void call(String s) {
                Log.e("flag",s);

            }
        };
        Observable.just("he","111").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(action1);


    }

    private void demo4() {
        /**
         * 自定义创建Subscriber   用Action1代替onNext()  on
         */
        Action1<String> action1 = new Action1<String>() {


            @Override
            public void call(String s) {
                Log.e("flag",s);

            }
        };

        Action1<Throwable> actionerror = new Action1() {
            @Override
            public void call(Object o) {
                Log.e("flag","error");
            }
        };
/**
 * Action0  onCompleted() 的内容打包起来将自己作为一个参数传入 subscribe() 以实现不完整定义的回调
 */
        Action0  commplet = new Action0() {
            @Override
            public void call() {
                Log.e("flag","commelt");
            }
        };

        String[] words = {"Hello", "Hi", "Aloha"};
        Observable<String> obervable = Observable.from(words);
        obervable.subscribe(action1,actionerror,commplet);
       // obervable.map()

    }

    private void demo3() {
        /**
         * 将数组或者迭代器 拆分，然后传给观察者
         */
        String[] words = {"Hello", "Hi", "Aloha"};
        Observable<String> obervable = Observable.from(words);
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e("flag",s);
            }
        };
        obervable.subscribe(subscriber);
    }

    private void demo2() {
        /**
         * 快捷创建事件队列
         */
        Observable<String> observable = Observable.just("hello","rx","v  java");
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e("flag",s);

            }
        };
        observable.subscribe(subscriber);

    }

    private void demo1() {
        /**
         * 最基本的用法
         */
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello1");
                subscriber.onNext("hello2");
                subscriber.onNext("hello3");
                subscriber.onCompleted();
                subscriber.onNext("hello4");
            }
        });
        /**
         * Subscriber是Observable的父类，基本用法和Observable一样,增加了onStart()方法和
         */
        Subscriber<String> subscribe = new Subscriber<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("flag","error1");
            }
            @Override
            public void onCompleted() {
                Log.e("flag","error2");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e("flag",s);
            }



        };


        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.e("flag","error");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e("flag",s);

            }
        };

        observable.subscribe(subscribe);


    }
}
