package com.microservices.microservices.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class sd {
    public static void main(String[] args) {
//        Stream<Integer> s = Stream.of(1,2,3,4,5,6,7,9);
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);

//        s.forEach(obj -> System.out.print(obj));

//        s.forEach(obj -> System.out.print(obj));
        list.stream().forEach(sd-> System.out.println(sd));
        list.stream().forEach(sd-> System.out.println(sd));

    }
}
