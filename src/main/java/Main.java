import POJO.CompleteProduct;
import POJO.PriceChangeResponse;
import POJO.Product;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sun.nio.ch.ThreadPool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public static void main(String[] args) {
        SQLiteJDBC.createTable();
        /// https://www.khanoumi.com/newproduct?thisID={id}


//        try {
//            Task.binarySearch("4429", "5675", "5433");
//            Task.binarySearch("15581", "19368", "16610");
//            Task.binarySearch("4490", "6064", "5505");
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }


        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:khanoumiIDDB.db");
            c.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        Observable<Integer> observable1 = Observable.range(75000,99999);
        Observable<Integer> observable2 = Observable.range(50000,74999);
        Observable<Integer> observable3 = Observable.range(25000,49999);
        Observable<Integer> observable4 = Observable.range(    1,24999);

        Connection finalC = c;
        observable1.doOnNext(x ->{
            System.out.println(ANSI_CYAN + x +"- 1st getting product " + "-------------------------------------"+ ANSI_RESET);
            Runnable r = new Task(x, finalC);
            r.run();
        }).subscribeOn(Schedulers.newThread()).subscribe();

        Connection finalC1 = c;
        observable2.doOnNext(x -> {
            System.out.println(ANSI_CYAN + x +"- 2nd getting product " + "-------------------------------------"+ ANSI_RESET);
            Runnable r = new Task(x, finalC1);
            r.run();
        }).subscribeOn(Schedulers.newThread()).subscribe();

        Connection finalC2 = c;
        observable3.doOnNext(x -> {
            System.out.println(ANSI_CYAN + x +"- 3rd getting product " + "-------------------------------------"+ ANSI_RESET);
            Runnable r = new Task(x, finalC2);
            r.run();
        }).subscribeOn(Schedulers.newThread()).subscribe();

        Connection finalC3 = c;
        Disposable observer = observable4.doOnNext(x -> {
            System.out.println(ANSI_CYAN + x +"- 4th getting product " + "-------------------------------------"+ ANSI_RESET);
            Runnable r = new Task(x, finalC3);
            r.run();
        }).doOnComplete(() -> Thread.sleep(999999999)).subscribe();


        try {
            c.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//        ExecutorService service = Executors.newFixedThreadPool(4);
//
//        for (int i = 1; i < 99999; i++){
//
//            Runnable runnable = new Task(i,c);
//            service.execute(runnable);
//        }

//        observer.notify();


//        Observable<Integer> o1 = Observable.create(emitter -> {
//            emitter.onNext(1);
//            Thread.sleep(1000);
//
//            emitter.onNext(1);
//            Thread.sleep(1000);
//            emitter.onNext(1);
//            Thread.sleep(1000);
//            emitter.onNext(1);
//            Thread.sleep(1000);
//            emitter.onNext(1);
//            Thread.sleep(1000);
//        });
//
//        Observable<Integer> o2 = Observable.create(emitter -> {
//            emitter.onNext(2);
//            Thread.sleep(1000);
//
//            emitter.onNext(2);
//            Thread.sleep(1000);
//            emitter.onNext(2);
//            Thread.sleep(1000);
//            emitter.onNext(2);
//            Thread.sleep(1000);
//            emitter.onNext(2);
//            Thread.sleep(1000);
//        });
//
//        Observable<Integer> o3 = Observable.create(emitter -> {
//            emitter.onNext(3);
//            Thread.sleep(1000);
//
//            emitter.onNext(3);
//            Thread.sleep(1000);
//            emitter.onNext(3);
//            Thread.sleep(1000);
//            emitter.onNext(3);
//            Thread.sleep(1000);
//            emitter.onNext(3);
//            Thread.sleep(1000);
//        });
//
//
//        o1.doOnNext(System.out::println).subscribeOn(Schedulers.newThread()).subscribe();
//
//        o2.doOnNext(System.out::println).subscribeOn(Schedulers.newThread()).subscribe();
//
//        o3.doOnNext(System.out::println).subscribe();

//        Observable<Integer> observable = Observable.range(1,99999);
//
//
//        observable.observeOn(Schedulers.io()).subscribe(id -> {
//            System.out.println(ANSI_CYAN + id +"- getting product " + "-------------------------------------"+ ANSI_RESET);
//            Runnable r = new Task(id);
//            Thread thread = new Thread(r);
//            thread.run();
//        });
//
//        try {
//            Thread.sleep(100000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


    }
}
