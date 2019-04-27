package com.forrily.stream;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * java.util.Stream表示了某一种元素的序列.
 * 可以在这些元素上进行各种操作。Stream操作可以是中间操作，也可以是完结操作。
 * 中间操作会返回流对象本身，可以通过多次调用同一个流操作方法来将操作结果串起来(参考方法链)
 * 完结操作会返回一个某种类型的值.
 * stream是在一个源的基础上创建出来的，例如java.util.Collection中的list或者set（map不能作为Stream的源）.
 * Stream操作往往可以通过顺序(Collections.stream())或者并行(Collection.parallelStream())两种方式来执行。
 */
public class Basic {


    static List<String> appList = new ArrayList<>();

    static {
        appList.add("a");
        appList.add("b");
        appList.add("c");
        appList.add("d");
        appList.add("e");
        appList.add("f");
        appList.add("g");
    }

    public static void main(String[] args) {
        //filterEt();

        //sortEt();

        //matchEt();

        //reduceEt();

        compareStreamWithParallelStream();

        //processingSequence();

        //processingSequenceEt();

        //processingSequenceEt2();

       // reuseStream();
    }

    /**
     * Filter接受一个predicate接口类型的变量，
     * 并将所有流对象中的元素进行过滤。该操作是一个中间操作，因此它允许我们在返回结果的基础上再进行其他的流操作（forEach）。
     * ForEach接受一个function接口类型的变量，用来执行对每一个元素的操作。
     * ForEach是一个中止操作。它不返回流，所以我们不能再调用其他的流操作。
     */
    static void filterEt(){
        appList.stream().filter(s->s.equals("a")||s.equals("b")).forEach(System.err::println);
    }

    /**
     *Sorted是一个中间操作，能够返回一个排过序的流对象的视图。
     * 流对象中的元素会默认按照自然顺序进行排序，除非你自己指定一个Comparator接口来改变排序规则。
     */
    static void sortEt(){
        appList.stream().sorted().filter(s->!s.equals("a")).forEach(System.err::println);
    }

    /**
     * map是一个对于流对象的中间操作，通过给定的方法，它能够把流对象中的每一个元素对应到另外一个对象上。
     * 下面的例子就演示了如何把每个string都转换成大写的string.
     * 不但如此，你还可以把每一种对象映射成为其他类型。对于带泛型结果的流对象，具体的类型还要由传递给map的泛型方法来决定。
     */
    static void mapEt(){
        appList.stream().map(String::toUpperCase).forEach(System.err::println);
    }


    /**
     * 匹配操作有多种不同的类型，都是用来判断某一种规则是否与流对象相互吻合的。
     * 所有的匹配操作都是终结操作，只返回一个boolean类型的结果。
     */
    static void matchEt(){
        // 任意一个元素满足match条件
        System.err.println(appList.stream().anyMatch(s -> s.contains("a")));
        // 所有元素满足match条件
        System.err.println(appList.stream().allMatch(s -> s.contains("b")));
        // 所有元素都不满足match条件
        System.err.println(appList.stream().noneMatch(s -> s.contains("c")));
    }

    /**
     * Count是一个终结操作，它的作用是返回一个数值，用来标识当前流对象中包含的元素数量。
     */
    static void countEt(){
        System.err.println(appList.stream().count());
    }

    /**
     * 该操作是一个终结操作，它能够通过某一个方法，对元素进行削减(遍历)操作。
     * 该操作的结果会放在一个Optional变量里返回。
     * 下面的例子,实现倒序
     */
    static void reduceEt(){
        Optional<String> reduce = appList.stream().reduce((s,s1)-> {
            return s1 += s;
        });
        System.err.println(reduce.get());
    }

    /**
     * 顺序流和并行流（多线程）的比较
     */
    static void compareStreamWithParallelStream(){
        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }
        long sT = System.nanoTime();

        values.stream().sorted().count();

        long eT = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(eT - sT);
        System.out.println(String.format("（顺序流耗时:）sequential sort took: %d ms", millis));

        long pST = System.nanoTime();

        values.parallelStream().sorted().count();

        long pET = System.nanoTime();

        long pMillis = TimeUnit.NANOSECONDS.toMillis(pET - pST);
        System.out.println(String.format("(并行流耗时:)parallel sort took: %d ms", pMillis));


    }


    /**
     * 创建流的方式
     */
    static void creatStream(){
        // 使用集合创建
        Arrays.asList("1", "2", "3").stream();
        // 使用流提供的of方法
        Stream.of("1", "2", "3");
        // jdk8自带的流 IntStream、LongStream 和 DoubleStream
        IntStream.range(1, 4).forEach(System.out::println);

        Arrays.stream(new int[] {1, 2, 3})
                .map(n -> 2 * n + 1)
                .average()
                .ifPresent(System.out::println);  // 5.0

        Stream.of("a1", "a2", "a3")
                .map(s -> s.substring(1))
                .mapToInt(Integer::parseInt)
                .max()
                .ifPresent(System.out::println);  // 3

        Stream.of(1.0, 2.0, 3.0)
                .mapToInt(Double::intValue)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);

    }

    /**
     * 流的处理顺序
     */
    static void processingSequence(){
        // 注意！！！执行这段代码时，不向控制台打印任何东西。这是因为衔接操作只在终止操作调用时被执行。
        Stream.of("l", "i", "n", "y", "m").filter(s -> {
                    System.out.println("filter: " + s);
                    return true; });
        /**
         * 注意！！！ 打印的顺序
         * filter: l
         * l
         * filter: i
         * i
         * filter: n
         * n
         * filter: y
         * y
         * filter: m
         * m
         *
         * 目的：减少每个元素上所执行的实际操作数量
         */
        Stream.of("l", "i", "n", "y", "m").filter(s -> {
                    System.out.println("filter: " + s);
                    return true; }).forEach(System.out::println);

        /**
         * 只要提供的数据元素满足了谓词，anyMatch操作就会返回true。
         * 对于第二个传递"i"的元素，它的结果为真。由于数据流的链式调用是垂直执行的，map这里只需要执行两次。
         * 以map会执行尽可能少的次数，而不是把所有元素都映射一遍。
         */
        Stream.of("l", "i", "n", "y", "m").map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase(); }).anyMatch(s -> {
                    System.out.println("anyMatch: " + s);
                    return s.equals("I");
                });
    }

    /**
     * 执行顺序的重要性
     * 注！！！执行结果如下：
     * map: l
     * filter: L
     * map: i
     * filter: I
     * forEach: I
     * map: n
     * filter: N
     * map: y
     * filter: Y
     * map: m
     * filter: M
     * -------------------------
     * filter: l
     * filter: i
     * map: i
     * forEach: I
     * filter: n
     * filter: y
     * filter: m
     */
    static void processingSequenceEt(){

        Stream.of("l", "i", "n", "y", "m").map(s -> {
            System.out.println("map: " + s);
            return s.toUpperCase();
        }).filter(s -> {
            System.out.println("filter: " + s);
            return s.equals("I");
        }).forEach(s ->
                System.out.println("forEach: " + s)
        );

        System.out.println("-------------------------");

        Stream.of("l", "i", "n", "y", "m").filter(s -> {
            System.out.println("filter: " + s);
            return s.equals("i");
        }).map(s -> {
            System.out.println("map: " + s);
            return s.toUpperCase();
        }).forEach(s ->
                System.out.println("forEach: " + s)
        );
    }

    /**
     * 执行顺序的重要性, sort根本没执行!!!
     * 注：观察执行结果
     * filter: l
     * filter: i
     * filter: n
     * filter: y
     * filter: m
     * map: i
     * forEach: I
     */
    static void processingSequenceEt2(){
        Stream.of("l", "i", "n", "y", "m").filter(s -> {
            System.out.println("filter: " + s);
            return s.equals("i");
        }).sorted((o1,o2)->{
            System.out.println("sort: "+o1+" "+o2);
            return o1.compareTo(o2);
        }).map(s -> {
            System.out.println("map: " + s);
            return s.toUpperCase();
        }).forEach(s ->
                System.out.println("forEach: " + s)
        );
    }


    /**
     * 流的复用
     */
    static void reuseStream(){
        Stream<String> stream = Stream.of("l", "i", "n", "y", "m");
        // true;
        stream.anyMatch(s->s.equals("l"));
        /**
         * Java8的数据流不能被复用。一旦你调用了任何终止操作，数据流就关闭了：
         * Exception in thread "main" java.lang.IllegalStateException: stream has already been operated upon or closed
         * 	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:229)
         * 	at java.util.stream.ReferencePipeline.anyMatch(ReferencePipeline.java:449)
         * 	at com.forrily.stream.Basic.reuseStream(Basic.java:293)
         * 	at com.forrily.stream.Basic.main(Basic.java:48)
         */
        // stream.anyMatch(s->s.equals("i"));

        Supplier<Stream<String>> streamSupplier = ()->Stream.of("l", "i", "n", "y", "m");

        System.out.println(streamSupplier.get().anyMatch(s -> s.equals("l")));

        System.out.println(streamSupplier.get().anyMatch(s -> s.equals("i")));

    }

}
