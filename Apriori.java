import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Apriori {
    public static int support = 2;//支持度
    public static String itemSplit = ",";//项分隔符

    public static List<List<String>> data1;

    public static void read_Data1(String fileName) throws Exception {//从文件中读取数据
        File file = new File(fileName);
        FileReader read = new FileReader(file);
        BufferedReader bReader = new BufferedReader(read);
        StringBuilder sb = new StringBuilder();
        String s = "";
        data1 = new LinkedList<>();
        while((s = bReader.readLine())!=null){
            sb.append(s+"\n");
            String temp[] = s.split(" ");
            List<String> tempList = new ArrayList<>();
            for(int i=0;i<temp.length;i++){
                tempList.add(temp[i]);
            }
            data1.add(tempList);
        }
        bReader.close();
        String str = sb.toString();
        System.out.println(data1);
    }

    public static Map<String,Integer> countFrequent(List<String> itemSet){//用来计算项集的频度
        Map<String,Integer> itemCount = new HashMap<String,Integer>();
        for(int i=0;i<data1.size();i++){
            String dataList = list_ToString(data1.get(i));
            for(int j=0;j<itemSet.size();j++){
                String key = itemSet.get(j);
                if(isContain(dataList,key)){//项数等于2是无法正确的contain
                    if(itemCount.containsKey(key)){
                        int temp = itemCount.get(key);
                        itemCount.put(key,++temp);
                    }else{
                        itemCount.put(key,1);
                    }
                }
            }
        }
        System.out.println(itemCount);
        return itemCount;
    }

    public static String list_ToString(List<String> dataList){
        StringBuffer s = new StringBuffer();
        for(int i=0;i<dataList.size();i++){
            s.append(dataList.get(i));
        }
        return s.toString();
    }

    public static boolean isContain(String dataStr,String itemStr){//判断是否含有项集
        int flagOfData = 0;
        int flagOfItem = 0;
        while(flagOfData<dataStr.length()&&flagOfItem<itemStr.length()){
            if(dataStr.charAt(flagOfData)==itemStr.charAt(flagOfItem)){
                flagOfData++;
                flagOfItem++;
            }else{
                flagOfData++;
            }
        }
        if(flagOfItem<itemStr.length()){
            return false;
        }else {
            return true;
        }
    }

    public static List<String> scan(Map<String,Integer> itemCount,List<String> key){
        List<String> res = new ArrayList<>();
        //key是与itemCount相对应的键值
        int i = 0;
        while(i<itemCount.size()){
            String keyValue = key.get(i);
            if(itemCount.get(keyValue)<support){
                itemCount.remove(keyValue);
                key.remove(i);
                i = 0;
            }else{
                i++;
            }
        }
        //完成剪枝
        System.out.println("频繁项集为"+key);
        res = allSort(key);
        return res;
    }

    public static List<String> allSort(List<String> key){//需要剪枝后的列表，生成频繁key.size()+1项集,查重
        List<String> newList = new ArrayList<>();
        for(int i=0;i<key.size();i++){
            for(int j=i+1;j<key.size();j++){
                String temp = marger2String(key.get(i),key.get(j));
                temp = reSort(temp);
                if(!newList.contains(temp)&&temp.length()<5){
                    newList.add(temp);
                }
            }
        }
        return newList;
    }

    public static String reSort(String numSort){
        int temp[] = new int[numSort.length()];
        for(int i = 0;i<numSort.length();i++){
            temp[i] = numSort.charAt(i)-'0';
        }
        SelectSort ss = new SelectSort();
        ss.minSort(temp);
        StringBuffer s = new StringBuffer();
        for(int i=0;i<temp.length;i++){
            s.append(temp[i]);
        }
        return s.toString();
    }

    public static String marger2String(String str1,String str2){//已排好序
        StringBuffer s = new StringBuffer();
        int indexOfStr1 = 0;
        int indexOfStr2 = 0;
        while(indexOfStr1<str1.length()&&indexOfStr2<str2.length()){
            if(str1.charAt(indexOfStr1)==str2.charAt(indexOfStr2)){
                s.append(str1.charAt(indexOfStr1));
                indexOfStr1++;
                indexOfStr2++;
            }else if(str1.charAt(indexOfStr1)!=str2.charAt(indexOfStr2)&&(indexOfStr1<=indexOfStr2)){
                s.append(str1.charAt(indexOfStr1));
                indexOfStr1++;
            }else if(str1.charAt(indexOfStr1)!=str2.charAt(indexOfStr2)&&(indexOfStr1>=indexOfStr2)){
                s.append(str2.charAt(indexOfStr2));
                indexOfStr2++;
            }
        }
        if(indexOfStr2<str2.length()){
            for(int i=indexOfStr2;i<str2.length();i++){
                s.append(str2.charAt(i));
            }
        }else if(indexOfStr1<str1.length()){
            for(int i=indexOfStr1;i<str1.length();i++){
                s.append(str1.charAt(i));
            }
        }
        return s.toString();
    }

    public static void apriori(List<String> itemSet){
        Map<String,Integer> itemFrequent = new HashMap<>();
        while(true){
            itemFrequent = countFrequent(itemSet);
            itemSet = scan(itemFrequent,itemSet);
            if(itemFrequent.size()==0){
                break;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        read_Data1("src/Data/data1.txt");
        List<String> itemSet = new ArrayList<>();
        itemSet.add("1");
        itemSet.add("2");
        itemSet.add("3");
        itemSet.add("5");
        apriori(itemSet);
    }
}
