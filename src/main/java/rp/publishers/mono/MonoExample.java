package rp.publishers.mono;

import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MonoExample {

    //Mono just,subscribe , onNext onError ,onComplete

    public static Map<Integer, String> studentMap = new HashMap<>();

    static {
        studentMap.put(1, "George");
        studentMap.put(2, "Putin");
        studentMap.put(3, "Nelson");
        studentMap.put(4, "Alisha");
        studentMap.put(5, "Bertha");
        studentMap.put(6, "Jason");
    }

    public static void main(String[] args) {
        monoReactiveExample();
    }

    static Mono<String> studentName(int rollNo) {
               return Mono.justOrEmpty(studentMap.get(rollNo));
    }

    static Mono<String> studentNameError(int rollNo) {
        return (studentMap.containsKey(rollNo)) ?
                Mono.just(studentMap.get(rollNo))
                : Mono.error(new RuntimeException("roll number is not present"));
    }


    static Mono<String> studentNameFromSupplier(int rollNo) {
        return (studentMap.containsKey(rollNo)) ?
                Mono.fromSupplier(() -> studentMap.get(rollNo))
                : Mono.empty();
    }

    static CompletableFuture<String> getInfo() {
        return CompletableFuture.supplyAsync(() -> "its a beautiful day !");
    }


    static Consumer<Object> onNext() {
        return item -> System.out.println(" item is : " + item);
    }

    static Consumer<Throwable> onError() {
        return err -> System.out.println(err.getMessage());
    }

    static Runnable onComplete() {
        return () -> System.out.println("  Task Completed ");
    }

    static void monoReactiveExample() {

        System.out.println(" monoReactiveExample : way 1 ");
        int rollNo = 1;
        studentName(rollNo).subscribe(onNext(),
                onError(),
                onComplete());

        rollNo = 7;
        System.out.println(" monoReactiveExample : way Empty");
        studentName(rollNo).subscribe(onNext(),
                onError(),
                onComplete());
        rollNo = 10;
        System.out.println(" monoReactiveExample : way Error");
        studentNameError(rollNo).subscribe(onNext(),
                onError(),
                onComplete());

        rollNo = 2;
        System.out.println(" monoReactiveExample : way From Supplier");
        studentNameFromSupplier(rollNo).subscribe(onNext(),
                onError(),
                onComplete());


        System.out.println(" monoReactiveExample : way From Supplier 2");
        Mono<String> mono = Mono.fromSupplier(() -> studentMap.get(3));
        mono.subscribe(onNext(),
                onError(),
                onComplete());

        System.out.println(" monoReactiveExample : way From Supplier 3");
        Supplier<String> supply = () -> studentMap.get(4);
        Mono<String> mon = Mono.fromSupplier(supply);
        mon.subscribe(onNext(),
                onError(),
                onComplete());


        System.out.println(" monoReactiveExample : way From Callable 1");
        Callable<String> callable = () -> studentMap.get(5);
        Mono<String> moncall = Mono.fromCallable(callable);
        moncall.subscribe(onNext(),
                onError(),
                onComplete());


        System.out.println(" monoReactiveExample : way From Callable 2");
        Mono<String> moncall2 = Mono.fromCallable(() -> studentMap.get(6));
        moncall2.subscribe(onNext(),
                onError(),
                onComplete());

        System.out.println(" monoReactiveExample : way From CompletableFuture");
        Mono.fromFuture(getInfo()).subscribe(onNext(),
                onError(),
                onComplete());
    }



}
