package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class Main {
    private static final int MAX = 30;
    private static final int K_nearest = 3;
    static double[] minArray = new double[K_nearest + 1];
    static int[] charFreq = new int[26];
    static char predictChar = '_';
    static char[] keyArray = new char[K_nearest + 1];
    static int numberOfFeature = 8;
    static String filePath = "src\\Normal.csv";
    static String testPath = "src\\Normal -Test.csv";
    static double accuracy = 1;
    static int timestep = 10;
    static boolean idle = true;
    static boolean previous_idle = true;
    static boolean previous_data_idle = true;
    static boolean data_idle = true;
    static double[] Data = new double[timestep];
    static int dudoanID = 0;
    public static void main(String[] args) {
        // write your code here
        //Init();
        String csvFile = testPath;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csvFile));
            int dem = 0;
            Init();
            while ((line = br.readLine()) != null) {

                // use comma as separator
                if (idle == true && previous_idle == false)
                {

                        predict(Data);
                        printDistanceLabel();
                        getCharProbability();
                        printProbability();
                        System.out.println("Du doan "+ (dudoanID++) + " " + predictChar);

                    Init();
                }
                String[] data = line.split(cvsSplitBy);
                double[] readData = new double[numberOfFeature];
                for (int i = 0; i < numberOfFeature; i++) {
                        readData[i] = Double.parseDouble(data[i].replace("\uFEFF", ""));
                    }
                //System.out.println("Read data" + readData[0]);
                double feature = computeFeature(readData);
                //System.out.println("Feature " + feature);
                if (feature > 0)
                {
                    previous_idle = idle;
                    idle = false;
                    Data[dem] = feature;
                    dem ++;
                    if (dem == timestep) {
                        previous_idle = idle;
                        idle = true;
                        dem = 0;
                    }
                }
                else
                {
                    previous_idle = idle;
                    idle = true;
                    dem = 0;
                }

                }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //double[] array = {0.620253165, 0.704545455, 0.217391304, 0.04};
        // double[] array = {0.620253165, 0.545454545, 0.47826087, 0.4};
        //predict(array);

//        printDistanceLabel();
//        getCharProbability();
//        printProbability();
//        System.out.println("Predicted : "+ predictChar);
    }

    public static void Init()
    {
        for (int i = 0; i < K_nearest + 1; i++) {
            minArray[i] = -1;
            keyArray[i] = 'Z' + 1;
        }
        for (int j = 0; j < 26; j++)
            charFreq[j] = 0;
        for (int z = 0; z < timestep; z++)
            Data[z] = 0;
    }

    public static void predict(double[] array )
    {
        readExcel(array);
    }
    public static void readExcel(double[] array)
    {
        String csvFile = filePath;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            int dem = 0;
            char key = '_';
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(cvsSplitBy);
                double[] readData = new double[numberOfFeature];
                double[] featureData = new double[timestep];
                double[] compareData = new double[timestep];
                compareData = array;
                if (previous_data_idle == false && data_idle == true ) {
                    Insert(computeDistance(featureData, compareData), key);
                    dem = 0;
                }
                for (int i = 0; i < numberOfFeature; i++)
                {
                    readData[i] = Double.parseDouble(data[i].replace("\uFEFF", ""));

                }
                double feature = computeFeature(readData);

                previous_data_idle = data_idle;
                if (feature > 0 && dem < timestep) {
                    data_idle = false;
                    featureData[dem] = feature;

                    key = data[numberOfFeature].charAt(0);
                    dem = dem + 1;
                }
                else
                {
                    data_idle = true;
                }


            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void Insert(double value, char key)
    {
        int j = K_nearest - 1;
        while (j >= 0 && minArray[j] == -1 ) j = j -1;
        while (j >= 0 && value < minArray[j])
        {
            minArray[j+ 1] = minArray[j];
            keyArray[j+1] = keyArray[j];
            j = j - 1;
        }
        minArray[j+1] = value;
        keyArray[j+1] = key;
    }
    public static double computeDistance(double[] arr1, double[] arr2)
    {
        double result = 0;
        for (int i = 0; i < timestep; i++)
        {
            result = result + (arr1[i] - arr2[i]) * (arr1[i] - arr2[i]);
        }
        return result;
    }

    public static double computeFeature(double[] arr)
    {
        double result = 0;
        for (int i = 0; i < numberOfFeature; i++)
        {
            result = result + (arr[i] + 1)*(arr[i] + 1)/4;
           
         }
        double threshold = 3.15;
        if (result <= threshold)
            result = 0;
        else
            result = result - threshold;
        return result;
    }
    public  static void getCharProbability()
    {
        int max = 0;
        for (int i = 0; i < K_nearest; i++)
        {
            charFreq[(int)keyArray[i] - 97] ++;
            if (charFreq[(int)keyArray[i] - 97] > max)
            {
                max = charFreq[(int)keyArray[i] - 97];
                predictChar = keyArray[i];
            }
        }
    }
    static void printProbability()
    {
        getCharProbability();
        for (int i = 0; i < 26; i++)
            System.out.println((char)('a' + i) + ": " + charFreq[i]);
    }
    static void printArray(double[] arr)
    {
        int n = arr.length;
        for (double v : arr) System.out.println(v + " ");
    }
    static void printArray(char[] arr)
    {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.println(arr[i] + " ");
    }
    static void printDistanceLabel()
    {
        int n = K_nearest;
        for (int i = 0; i < n; ++i)
            System.out.println("ID = " + i + ", Khoang cach = " + minArray[i] + ", Label =  " + keyArray[i]);
    }

}
