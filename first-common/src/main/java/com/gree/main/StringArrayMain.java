package com.gree.main;

/**
 * Create by yang_zzu on 2020/4/12 on 14:40
 */
public class StringArrayMain {

    public static void main(String[] args) {

        String s = "180619.txt,180620.doc,180621.xlsx,180622.pdf";

        String txt="", doc="", xlsx="", pdf="";
        String[] split = s.split(",");

        for (String s1 : split) {
            System.out.println("--------->" + s1);
            String[] split1 = s1.split("\\.");
            for (String s2 : split1) {
//                System.out.println(s2);
                if ("txt".equals(s2)) {
                    txt = txt + "_" + s1;
                } else if("doc".equals(s2)){
                    doc = doc + "_" + s1;
                }
            }
        }
        System.out.println(split);

        System.out.println("----------->>>>>>>>>>>>>>>" + txt);
        System.out.println("----------->>>>>>>>>>>>>>>" + doc);


        String a = "180619";





    }
}
