package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Read2Matrix {
    public static int line = 0;
    public static String SplitBy = ",";

    public static double[][] GetSample(String filepath) {
        //this.filepath = filepath;
        //double[][] Matrix = new double[25][8];
        double[][] Temp = new double[25][8];
        line = 0;
        try {
            File myObj = new File(filepath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                if (line >= 3) {
                    String data = myReader.nextLine();
                   // System.out.println(data);
                    String[] elements = data.split(SplitBy);
                    for (int i = 0; i < 8; i++)
                        Temp[line-3][i] = Double.parseDouble(elements[i].replace("\uFEFF", ""));
                }
                line++;
                if (line>=28) return Temp;
            }
            while (line < 28)
            {
                for (int i = 0; i < 8; i++)
                    Temp[line-3][i] = Temp[line-4][i];
            }
            myReader.close();
            //return
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return Temp;
    }

    public static double[][] GetAllSampleOneFeature(String filepath) {
        //this.filepath = filepath;
        //double[][] Matrix = new double[25][8];
        int NUMBER_OF_SAMPLE = 188;
        double[][] Temp = new double[25][NUMBER_OF_SAMPLE];
        line = 0;
        try {

            File myObj = new File(filepath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {

                    String data = myReader.nextLine();
                   // System.out.println(data);
                    String[] elements = data.split(SplitBy);
                    for (int i = 0; i < NUMBER_OF_SAMPLE; i ++)
                    Temp[line][i] = Double.parseDouble(elements[i].replace("\uFEFF", ""));
                    line ++;
                    if (line == 25)
                    return Temp;
            }

            myReader.close();
            //return
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return Temp;
    }


}
