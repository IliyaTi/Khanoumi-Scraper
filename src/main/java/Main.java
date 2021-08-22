import POJO.CompleteProduct;
import POJO.PriceChangeResponse;
import POJO.Product;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import okhttp3.OkHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
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

//        try {
////            Task.binarySearch("4429", "5675", "5433"); //1
////            Task.binarySearch("15581", "19368", "16610"); //295
////            Task.binarySearch("4490", "6064", "5505"); //36
////            Task.binarySearch("35845", "62406", "36353"); //39
////            Task.binarySearch("49017", "81089", "45844"); //89
//            Task.binarySearch("19712", "27689", "21371"); //89
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }

//        launchExecutor();
        launchGatherer();

    }

    public static void launchGatherer(){
        SQLiteJDBC.createProductsTable();
        SQLiteJDBC.createBrandsTable();

        Connection c = null;
//        Connection c1 = null;
//        Connection c2 = null;
//        Connection c3 = null;
//        Connection c4 = null;
//        Connection c5 = null;
//        Connection c6 = null;
//        Connection c7 = null;
//        Statement stmt = null;

        RetryPolicy<Object> retryPolicy = new RetryPolicy<>()
                .handle(Exception.class)
                .withDelay(Duration.ofSeconds(1))
                .withMaxRetries(3);

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:khanoumiIDDB.sqlite");
//            c1 = DriverManager.getConnection("jdbc:sqlite:khanoumiIDDB.sqlite");
//            c2 = DriverManager.getConnection("jdbc:sqlite:khanoumiIDDB.sqlite");
//            c3 = DriverManager.getConnection("jdbc:sqlite:khanoumiIDDB.sqlite");
//            c4 = DriverManager.getConnection("jdbc:sqlite:khanoumiIDDB.sqlite");
//            c5 = DriverManager.getConnection("jdbc:sqlite:khanoumiIDDB.sqlite");
//            c6 = DriverManager.getConnection("jdbc:sqlite:khanoumiIDDB.sqlite");
//            c7 = DriverManager.getConnection("jdbc:sqlite:khanoumiIDDB.sqlite");
//            c.setAutoCommit(false);
//            c1.setAutoCommit(false);
//            c2.setAutoCommit(false);
//            c3.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        Observable<Integer> observable1 = Observable.range(87500,12500);
        Observable<Integer> observable2 = Observable.range(75000,12500);
        Observable<Integer> observable3 = Observable.range(62500,12500);
        Observable<Integer> observable4 = Observable.range(50000,12500);
        Observable<Integer> observable5 = Observable.range(37500,12500); //37500
        Observable<Integer> observable6 = Observable.range(25000,12500);
        Observable<Integer> observable7 = Observable.range(12500,12500);
        Observable<Integer> observable8 = Observable.range(    1,12500);

        Connection finalC = c;
        observable1.doOnNext(x ->{
            System.out.println(x +"- 1st getting product " + "-------------------------------------");
//            Runnable r = new Task(x, finalC);
            Failsafe.with(retryPolicy).run(new Task(x, finalC)::run);
//            r.run();
        }).subscribeOn(Schedulers.newThread()).doOnError(Throwable::printStackTrace).doOnComplete(() -> System.out.println("1st finished")).subscribe();

        Connection finalC1 = c;
        observable2.doOnNext(x -> {
            System.out.println(x +"- 2nd getting product " + "-------------------------------------");
//            Runnable r = new Task(x, finalC1);
            Failsafe.with(retryPolicy).run(new Task(x,finalC1)::run);
//            r.run();
        }).subscribeOn(Schedulers.newThread()).doOnError(Throwable::printStackTrace).doOnComplete(() -> System.out.println("2nd finished")).subscribe();

        Connection finalC2 = c;
        observable3.doOnNext(x -> {
            System.out.println(x +"- 3rd getting product " + "-------------------------------------");
//            Runnable r = new Task(x, finalC2);
            Failsafe.with(retryPolicy).run(new Task(x, finalC2)::run);
//            r.run();
        }).subscribeOn(Schedulers.newThread()).doOnError(Throwable::printStackTrace).doOnComplete(() -> System.out.println("3rd finished")).subscribe();

        Connection finalC3 = c;
        observable4.doOnNext(x -> {
            System.out.println(x +"- 4th getting product " + "-------------------------------------");
//            Runnable r = new Task(x, finalC3);
            Failsafe.with(retryPolicy).run(new Task(x, finalC3)::run);
//            r.run();
        }).subscribeOn(Schedulers.newThread()).doOnError(Throwable::printStackTrace).doOnComplete(() -> System.out.println("4th finished")).subscribe();

        Connection finalC4 = c;
        observable5.doOnNext(x -> {
            System.out.println(x + "- 5th getting product " + "-------------------------------------");
            Failsafe.with(retryPolicy).run(new Task(x, finalC4)::run);
        }).subscribeOn(Schedulers.newThread()).doOnError(Throwable::printStackTrace).doOnComplete(() -> System.out.println("5th finished")).subscribe();

        Connection finalC5 = c;
        observable6.doOnNext(x -> {
            System.out.println(x + "- 6th getting product " + "-------------------------------------");
            Failsafe.with(retryPolicy).run(new Task(x, finalC5)::run);
        }).subscribeOn(Schedulers.newThread()).doOnError(Throwable::printStackTrace).doOnComplete(() -> System.out.println("6th finished")).subscribe();

        Connection finalC6 = c;
        observable7.doOnNext(x -> {
            System.out.println(x + "- 7th getting product " + "-------------------------------------");
            Failsafe.with(retryPolicy).run(new Task(x, finalC6)::run);
        }).subscribeOn(Schedulers.newThread()).doOnError(Throwable::printStackTrace).doOnComplete(() -> System.out.println("7th finished")).subscribe();

        Connection finalC7 = c;
        observable8.doOnNext(x -> {
            System.out.println(x + "- 8th getting product " + "-------------------------------------");
            Failsafe.with(retryPolicy).run(new Task(x, finalC7)::run);
        }).doOnComplete(() -> {
            System.out.println("8th finished");
            Thread.sleep(999999999);
        }).doOnError(Throwable::printStackTrace).subscribe();


        try {
            c.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public static void launchExecutor(){
        SQLiteJDBC.createProductsTable();
        SQLiteJDBC.createBrandsTable();

        Connection c = null;

        RetryPolicy<Object> retryPolicy = new RetryPolicy<>()
                .handle(Exception.class)
                .withDelay(Duration.ofSeconds(1))
                .withMaxRetries(3);

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:khanoumiIDDB.sqlite");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
       //I THINK I SHOULD USE SUBMIT() SOMEWHERE.
        ExecutorService service = Executors.newFixedThreadPool(8);
        for (int i = 1; i < 99999; i++){

            service.execute(new Task(i,c)::run);
        }

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
