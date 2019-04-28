package com.forrily.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Advanced {


    static List<User> users = Arrays.asList(new User[]{new User(1,"linym1"),new User(2,"linym2"),new User(3,"linym3"), });


    static{
        for(User u:users){
            for(int i=0;i<5;i++){
                u.cars.add(new Car(u.getName()+"-car-"+i));
            }
        }
    }

    public static void main(String[] args) {

        // collectEt();

        // flatMapEt();

        //reduceDetailsEt();

        parallelStreamEt();
    }


    /**
     * collect是终止操作，将流中的元素存放在不同类型的结果中，例如List、Set或者Map。
     * collect接受收集器（Collector），
     * 它由四个不同的操作组成：供应器（supplier）、累加器（accumulator）、组合器（combiner）和终止器（finisher）。
     * Java8通过内置的Collectors类支持多种内置的收集器,适应大部分常见操作。
     */
    static void collectEt(){

        List<User> collect = users.stream().collect(Collectors.toList());
        //for (User user : collect) System.err.println(user.getId());

        Map<Integer, List<User>> colMap = users.stream().collect(Collectors.groupingBy(User::getId));
        //colMap.forEach((k,v)-> System.err.println(k));


        // 自定义收集器
        Collector<User, List<User>, List<User>> userNameCollector = Collector.of(
                    ArrayList::new,
                    (list, u) ->{
                        list.add(u); },
                    (left, right) -> {
                        left.addAll(right);
                        return left;
                    },
                    us -> us
        );
        users.stream().collect(userNameCollector);

    }


    /**
     * flatMap将流中的每个元素，转换为其它对象的流。
     * 所以每个对象会被转换为零个、一个或多个其它对象，以流的形式返回
     * map只能转换为一个对象。
     */
    static void flatMapEt(){
        users.stream().flatMap(u->u.cars.stream()).forEach(c-> System.out.println(c.getName()));
    }


    /**
     * 归约操作将所有流中的元素组合为单一结果。
     * 下面是使用它的三种方式；
     */
    static void reduceBasicEt(){
        // 形式1
        boolean present = users.stream().reduce((user, user2) -> user.getId() < user2.getId() ? user : user2).isPresent();
        System.err.println(present);
        // 形式2
        User result = users.stream().reduce(new User(0, "init"), (user1, user2) -> {
            user1.id += user2.id;
            return user1;
        });
        System.out.println(result.getId());
        // 形式3
        Integer idSum = users
                .stream()
                .reduce(0, (sum, u) -> sum += u.getId(), (sum1, sum2) -> sum1 + sum2);

        System.out.println(idSum);
    }

    static void reduceDetailsEt(){
        /**
         * 注：观察执行结果，组合器没有执行。这是为什么？
         * accumulator linym1 1
         * accumulator linym2 2
         * accumulator linym3 3
         */
        Integer idSum = users
                .stream()
                .reduce(0, (sum, u) -> {
                    System.out.format("accumulator %s %s \r\n",u.getName(), u.getId());
                    sum += u.getId();
                    return sum;
                }, (sum1, sum2) -> {
                    System.out.format("combiner %s %s \r\n",sum1, sum2);
                    return sum1 + sum2;
                });

        System.err.println("-----------------------------");

        /**
         * 注：并行流
         * accumulator linym2 2
         * accumulator linym3 3
         * combiner 2 3
         * accumulator linym1 1
         * combiner 1 5
         */
        Integer pIdSum = users
                .parallelStream()
                .reduce(0, (sum, u) -> {
                    System.out.format("accumulator %s %s \r\n",u.getName(), u.getId());
                    sum += u.getId();
                    return sum;
                }, (sum1, sum2) -> {
                    System.out.format("combiner %s %s \r\n",sum1, sum2);
                    return sum1 + sum2;
                });

    }

    static void parallelStreamEt(){
        // -Djava.util.concurrent.ForkJoinPool.common.parallelism=5 修改底层线程池的大小。
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println(commonPool.getParallelism());    // 3
        Integer pIdSum = users
                .parallelStream()
                .reduce(0, (sum, u) -> {
                    System.out.format("accumulator %s %s %s \r\n",u.getName(), u.getId(), Thread.currentThread().getName());
                    sum += u.getId();
                    return sum;
                }, (sum1, sum2) -> {
                    System.out.format("combiner %s %s %s \r\n",sum1, sum2, Thread.currentThread().getName());
                    return sum1 + sum2;
                });

    }


    static class User{

        int id;
        String name;
        ArrayList<Car>cars = new ArrayList<>();

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public ArrayList<Car> getCars() {
            return cars;
        }

        public void setCars(ArrayList<Car> cars) {
            this.cars = cars;
        }
    }

    static class Car{
        String name;

        public Car(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
