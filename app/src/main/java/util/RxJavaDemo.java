package util;


import java.util.ArrayList;
import java.util.List;


import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by breeze on 2016/2/2.
 */
public class RxJavaDemo {
    private static final String TAG = "RxJavaDemo";

    public static void main(String... args) {
        //事件源发出一个字符串
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello rx android");
                subscriber.onCompleted();
            }
        });

//        订阅着处理 onNext
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        };
//        绑定事件和订阅者
        observable.subscribe(subscriber);

        //简单使用
        Observable.just("hello rx android 2").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
               System.out.println(s);
            }
        });

        //字符串拼接实例
        Observable.just("hello rx android 3")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + " map 操作符";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });

        //类型转换
        Observable.just("hello rx Android 4")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.hashCode();
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(integer + " = " + "hello rx Android 4".hashCode());
                    }
                });

        //Observable.from()方法，它接收一个集合作为输入，然后每次输出一个元素给subscriber
        Observable.from(new String[]{"hello rx Android 5", "hello rx Android 6"}).subscribe(new Action1<String>() {

            @Override
            public void call(String t) {
               System.out.println(t);//依次打印5， 6
            }
        });

//        Observable.flatMap()接收一个Observable的输出作为输入，同时输出另外一个Observable
        List<String> list = new ArrayList<String>();
        list.add("hello rx Android 7");
        list.add("hello rx Android 8");
        Observable.just(list)
                .flatMap(new Func1<List<String>, Observable<String>>() {

                    @Override
                    public Observable<String> call(List<String> t) {
                        return Observable.from(t);
                    }

                })
                .subscribe(new Action1<String>() {

                    @Override
                    public void call(String t) {
                        System.out.println(t); // 依次打印7,8
                    }
                });

//        filter()输出和输入相同的元素，并且会过滤掉那些不满足检查条件的。
//        take()输出最多指定数量的
//        doOnNext()允许我们在每次输出一个元素之前做一些额外的事情
        list.add("hello rx Android 7");
        list.add("hello rx Android 8");
        list.add(null);

        Observable.just(list)
                .flatMap(new Func1<List<String>, Observable<String>>() {

                    @Override
                    public Observable<String> call(List<String> t) {
                        return Observable.from(t);
                    }

                })
                .filter(new Func1<String, Boolean>() {

                    @Override
                    public Boolean call(String t) {
                        return t != null;
                    }
                })
                .take(2)
                .doOnNext(new Action1<String>() {

                    @Override
                    public void call(String t) {
                       System.out.println(t);
                    }
                })
                .subscribe(new Action1<String>() {

                    @Override
                    public void call(String t) {
                        System.out.println(t);
                    }
                });


    /*
    Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
            Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
            Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
            Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
    另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
    有了这几个 Scheduler ，就可以使用 subscribeOn() 和 observeOn() 两个方法来对线程进行控制了。

    subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。
    observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。*/
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("number:"+ integer);
                    }
                });
    }

    Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("Aloha");
            subscriber.onCompleted();
        }
    });





}
