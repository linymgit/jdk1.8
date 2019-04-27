package com.forrily.lamda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class LamdaBasicUse {

    private static List<String> abcList = Arrays.asList(new String[]{"C", "A", "B"});


    private static void sortUnLamda(){
        abcList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    /*
   private static void sortlamda(){
        abcList.sort((o1, o2)-> {
            return o1.compareTo(o2));
        }
    }
    */
    /*
    private static void sortlamda(){
        abcList.sort((o1, o2)-> o1.compareTo(o2));
    }
    */
    private static void sortlamda(){
        abcList.sort(Comparator.naturalOrder());
    }

    public static void main(String[] args) {
        sortlamda();
        abcList.forEach(System.err::print);
        sortUnLamda();
        abcList.forEach(System.err::println);
    }
}
