package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Main {
    private static final int MAX = 100;
    private static final int K_nearest = 50;
    static double[] minArray = new double[K_nearest + 1];
    static int[] charFreq = new int[26];
    static char predictChar = '_';
    static char[] keyArray = new char[K_nearest + 1];
    static int numberOfFeature = 4;
    static String filePath = "src\\Train_data.csv";
    static String testPath = "src\\Test_data.csv";
    static double accuracy = 1;
    public static void main(String[] args) {
	// write your code here
        //Init();
        String csvFile = testPath;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            int dem = 0;
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                dem ++;
                // use comma as separator
                Init();
                String[] data = line.split(cvsSplitBy);
                double[] readData = new double[numberOfFeature];
                for (int i = 0; i < numberOfFeature; i++)
                {
                    readData[i] = Double.parseDouble(data[i]);

                }
                predict(readData);
                printProbability();
                System.out.println("Predicted : "+ predictChar);
                System.out.println("Label : " + data[numberOfFeature]);
                if (predictChar == data[numberOfFeature].charAt(0))
                    accuracy = (accuracy * (dem - 1) + 1)/dem;
                else
                    accuracy = (accuracy * (dem - 1))/dem;
                System.out.println("Accuracy = " + accuracy);
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
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(cvsSplitBy);
                double[] readData = new double[numberOfFeature];
                double[] compareData = new double[numberOfFeature];
                compareData = array;
                for (int i = 0; i < numberOfFeature; i++)
                {
                    readData[i] = Double.parseDouble(data[i]);
                }
                char key = data[numberOfFeature].charAt(0);

                Insert(computeDistance(readData,compareData), key);
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
        for (int i = 0; i < numberOfFeature; i++)
        {
            result = result + (arr1[i] - arr2[i]) * (arr1[i] - arr2[i]);
        }
        return result;
    }
    public  static void getCharProbability()
    {
        int max = 0;
        for (int i = 0; i < K_nearest; i++)
        {
            charFreq[(int)keyArray[i] - 65] ++;
            if (charFreq[(int)keyArray[i] - 65] > max)
            {
                max = charFreq[(int)keyArray[i] - 65];
                predictChar = keyArray[i];
            }
        }
    }
    static void printProbability()
    {
        getCharProbability();
        for (int i = 0; i < 3; i++)
            System.out.println((char)('A' + i) + ": " + charFreq[i]);
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
