package com.example.collect.util;

import jeasy.analysis.MMAnalyzer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class SimilarHelper {
    private static List<String> fileList = new ArrayList<String>();
    private static HashMap<String, HashMap<String, Double>> allTheTf = new HashMap<String, HashMap<String, Double>>();
    private static HashMap<String, HashMap<String, Integer>> allTheNormalTF = new HashMap<String, HashMap<String, Integer>>();
    private static LinkedHashMap<String, Double[]> vectorMap = new LinkedHashMap<String, Double[]>();

    public static float calculateTextSimilar(String text1,String text2){
        initalize();
        String key1="FIRST";
        String key2="SECOND";
        try{
            tfidf(key1,key2,text1,text2);
        }catch(IOException e){
            return 0.0F;
        }
        return sim(key1,key2).floatValue();
    }

    /**
     * 分词
     *
     * @author create by rock
     */
    public static String[] TextcutWord(String text) throws IOException {
        String[] cutWordResult = null;
        MMAnalyzer analyzer = new MMAnalyzer();
        String tempCutWordResult = analyzer.segment(text, " ");
        cutWordResult = tempCutWordResult.split(" ");
        return cutWordResult;
    }

    public static Map<String, HashMap<String, Integer>> NormalTFOfAll(String key1, String key2, String text1,
                                                                      String text2) throws IOException {
        if (allTheNormalTF.get(key1) == null) {
            HashMap<String, Integer> dict1 = new HashMap<String, Integer>();
            dict1 = normalTF(TextcutWord(text1));
            allTheNormalTF.put(key1, dict1);
        }
        if (allTheNormalTF.get(key2) == null) {
            HashMap<String, Integer> dict2 = new HashMap<String, Integer>();
            dict2 = normalTF(TextcutWord(text2));
            allTheNormalTF.put(key2, dict2);
        }
        return allTheNormalTF;
    }

    public static Map<String, HashMap<String, Double>> tfOfAll(String key1, String key2, String text1, String text2)
            throws IOException {
        allTheTf.clear();
        HashMap<String, Double> dict1 = new HashMap<String, Double>();
        HashMap<String, Double> dict2 = new HashMap<String, Double>();
        dict1 = tf(TextcutWord(text1));
        dict2 = tf(TextcutWord(text2));
        allTheTf.put(key1, dict1);
        allTheTf.put(key2, dict2);
        return allTheTf;
    }

    /**
     * 计算词频
     *
     * @author create by rock
     */
    public static HashMap<String, Double> tf(String[] cutWordResult) {
        HashMap<String, Double> tf = new HashMap<String, Double>();// 正规化
        int wordNum = cutWordResult.length;
        int wordtf = 0;
        for (int i = 0; i < wordNum; i++) {
            wordtf = 0;
            if (!Objects.equals(cutWordResult[i], " ")) {
                for (int j = 0; j < wordNum; j++) {
                    if (i != j) {
                        if (cutWordResult[i].equals(cutWordResult[j])) {
                            cutWordResult[j] = " ";
                            wordtf++;
                        }
                    }
                }
                tf.put(cutWordResult[i], (new Double(++wordtf)) / wordNum);
                cutWordResult[i] = " ";
            }
        }
        return tf;
    }

    public static HashMap<String, Integer> normalTF(String[] cutWordResult) {
        HashMap<String, Integer> tfNormal = new HashMap<String, Integer>();// 没有正规化
        int wordNum = cutWordResult.length;
        int wordtf = 0;
        for (int i = 0; i < wordNum; i++) {
            wordtf = 0;
            if (!Objects.equals(cutWordResult[i], " ")) {
                for (int j = 0; j < wordNum; j++) {
                    if (i != j) {
                        if (cutWordResult[i].equals(cutWordResult[j])) {
                            cutWordResult[j] = " ";
                            wordtf++;
                        }
                    }
                }
                tfNormal.put(cutWordResult[i], ++wordtf);
                cutWordResult[i] = " ";
            }
        }
        return tfNormal;
    }

    public static Map<String, Double> idf(String key1, String key2, String text1, String text2)
            throws FileNotFoundException, UnsupportedEncodingException, IOException {
        // 公式IDF＝log((1+|D|)/|Dt|)，其中|D|表示文档总数，|Dt|表示包含关键词t的文档数量。
        Map<String, Double> idf = new HashMap<String, Double>();
        List<String> located = new ArrayList<String>();

        NormalTFOfAll(key1, key2, text1, text2);

        float Dt = 1;
        float D = allTheNormalTF.size();// 文档总数
        List<String> key = fileList;// 存储各个文档名的List

        String[] keyarr = new String[2];
        keyarr[0] = key1;
        keyarr[1] = key2;

        for(String item :keyarr) {
            if (!fileList.contains(item)) {
                fileList.add(item);
            }
        }

        Map<String, HashMap<String, Integer>> tfInIdf = allTheNormalTF;// 存储各个文档tf的Map

        for (int i = 0; i < D; i++) {
            HashMap<String, Integer> temp = tfInIdf.get(key.get(i));
            for (String word : temp.keySet()) {
                Dt = 1;
                if (!(located.contains(word))) {
                    for (int k = 0; k < D; k++) {
                        if (k != i) {
                            HashMap<String, Integer> temp2 = tfInIdf.get(key.get(k));
                            if (temp2.containsKey(word)) {
                                located.add(word);
                                Dt = Dt + 1;
                            }
                        }
                    }
//                    idf.put(word, (double) Log.log((1 + D) / Dt, 10));
                    idf.put(word, Math.log10(1 + D) / Dt);
                }
            }
        }
        return idf;
    }

    public static Map<String, HashMap<String, Double>> tfidf(String key1, String key2, String text1, String text2)
            throws IOException {
        Map<String, Double> idf = idf(key1, key2, text1, text2);
        tfOfAll(key1, key2, text1, text2);
        for (String key : allTheTf.keySet()) {
            Map<String, Double> singelFile = allTheTf.get(key);
            int length = idf.size();
            Double[] arr = new Double[length];
            int index = 0;

            for (String word : singelFile.keySet()) {
                singelFile.put(word, (idf.get(word)) * singelFile.get(word));
            }
            for (String word : idf.keySet()) {
                arr[index] = singelFile.get(word) != null ?singelFile.get(word):0d;
                index++;
            }
            vectorMap.put(key, arr);
        }
        return allTheTf;
    }

    /* 得到词向量以后，用余弦相似度匹配 */
    public static Double sim(String key1, String key2) {
        Double[] arr1 = vectorMap.get(key1);
        Double[] arr2 = vectorMap.get(key2);
        int length = arr1.length;
        Double result1 = 0.00; // 向量1的模
        Double result2 = 0.00; // 向量2的模
        Double sum = 0d;
        if (length == 0) {
            return 0d;
        }
        for (int i = 0; i < length; i++) {
            result1 += arr1[i] * arr1[i];
            result2 += arr2[i] * arr2[i];
            sum += arr1[i] * arr2[i];
        }
        Double result = Math.sqrt(result1 * result2);
//        System.out.println(key1 + "和" + key2 + "相似度" + sum / result);

        return sum / result;
    }

    /**
     * 初始化成员变量
     */
    private static void initalize(){
        fileList = new ArrayList<String>();
        allTheTf = new HashMap<String, HashMap<String, Double>>();
        allTheNormalTF = new HashMap<String, HashMap<String, Integer>>();
        vectorMap = new LinkedHashMap<String, Double[]>();
    }
}
