package com.company;

public class Main {
    public static void main(String[] args) {
        String [] files = {"a\\a1.txt","a\\a2.txt","a\\a3.txt","a\\a4.txt","a\\a5.txt","b\\b1.txt","b\\b2.txt","b\\b3.txt","b\\b4.txt","b\\b5.txt","c\\c1.txt","c\\c2.txt","c\\c3.txt","c\\c4.txt","c\\c5.txt","d\\d1.txt","d\\d2.txt","d\\d3.txt","d\\d4.txt","d\\d5.txt","e\\e1.txt","e\\e2.txt","e\\e3.txt","e\\e4.txt","e\\e5.txt"};


        char [] label = {'a','a','a','a','a','b','b','b','b','b','c','c','c','c','c','d','d','d','d','d','e','e','e','e','e'};

        double accuracy = 1;
        int dem = 0;
        for (String file: files) {
            //dem ++;
            System.out.println(dem);
            System.out.println("Nhan chinh xac la " + label[dem]);
            String filepath = "src\\dataset\\1_raw\\" + file;
            System.out.println("Dand du doan file " + filepath);
            Predict.PredictSample(filepath);
            if (label[dem++] == (char)('a' + Predict.PredictResultInt))
                accuracy = (accuracy * (dem - 1) + 1)/dem;
            else
                accuracy = (accuracy * (dem - 1))/dem;
            System.out.println("Accuracy = " + accuracy);
        }
    }
}
