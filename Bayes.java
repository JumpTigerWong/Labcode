import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Bayes {
    public List<HashMap<String,String>> data;
    public String listHand[];
    public ArrayList<Integer> taxableIncomesNo = new ArrayList<>();
    public ArrayList<Integer> taxableIncomesYes = new ArrayList<>();

    public int countOfEvadeNo = 0;
    public int countOfEvadeYes = 0;

    public double Pro_OfVNRN = 0;
    public double Pro_OfVNRY = 0;
    public double Pro_OfVYRN = 0;
    public double Pro_OfVYRY = 0;

    public double Pro_OfVNMS = 0;
    public double Pro_OfVNMM = 0;
    public double Pro_OfVNMD = 0;
    public double Pro_OfVYMS = 0;
    public double Pro_OfVYMM = 0;
    public double Pro_OfVYMD = 0;

    public void read_Data(String fileName) throws Exception{//从文件中读取和处理数据
        File file = new File(fileName);
        FileReader read = new FileReader(file);
        BufferedReader bReader = new BufferedReader(read);
        StringBuilder sb = new StringBuilder();
        String s = "";
        listHand = bReader.readLine().split(" ");
        data = new LinkedList<>();

        while((s = bReader.readLine())!=null){
            sb.append(s+"\n");
            String temp[] = s.split(" ");
            HashMap<String,String> tempHashMap = new HashMap<String,String>();
            for(int i=0;i<listHand.length;i++){
                tempHashMap.put(listHand[i],temp[i]);
            }
            data.add(tempHashMap);
        }
        bReader.close();
        String str = sb.toString();
        for(int i=0;i<data.size();i++){
            System.out.println(data.get(i));
        }
    }

    public void GetProbabilityNo(){

        int numOfRefundNo = 0;
        int numOfRefundYes = 0;

        int numOfmaritalStatusNoSingle = 0;
        int numOfmaritalStatusNoMarried = 0;
        int numOfmaritalStatusNoDivorced = 0;

        String refundInData;
        String maritalStatusInData;
        String taxableIncameInData;
        String evadeInData;

        for(int i=0;i<data.size();i++){
            refundInData = data.get(i).get("refund");
            evadeInData = data.get(i).get("evade");
            maritalStatusInData = data.get(i).get("maritalStatus");
            taxableIncameInData = data.get(i).get("taxableIncome");

            if(evadeInData.equals("no")){
                countOfEvadeNo++;
                taxableIncomesNo.add(Integer.parseInt(taxableIncameInData));//获得taxableIncome|no的所有数据

                if(refundInData.equals("no")){//获得evade为no中refund各类的数量
                    numOfRefundNo++;
                }else if(refundInData.equals("Yes")){
                    numOfRefundYes++;
                }

                if(maritalStatusInData.equals("single")){//获得evade为no中maritalStatus各类的数量
                    numOfmaritalStatusNoSingle++;
                }else if(maritalStatusInData.equals("married")){
                    numOfmaritalStatusNoMarried++;
                }else if(maritalStatusInData.equals("divorced")){
                    numOfmaritalStatusNoDivorced++;
                }
            }
        }
        Pro_OfVNRN = GetProabilityOfNo(numOfRefundNo);
        Pro_OfVNRY = GetProabilityOfNo(numOfRefundYes);
        Pro_OfVNMD = GetProabilityOfNo(numOfmaritalStatusNoDivorced);
        Pro_OfVNMM = GetProabilityOfNo(numOfmaritalStatusNoMarried);
        Pro_OfVNMS = GetProabilityOfNo(numOfmaritalStatusNoSingle);
    }

    public void GetProbabilityYes(){

        int numOfRefundNo = 0;
        int numOfRefundYes = 0;

        int numOfmaritalStatusYesSingle = 0;
        int numOfmaritalStatusYesMarried = 0;
        int numOfmaritalStatusYesDivorced = 0;

        String refundInData;
        String maritalStatusInData;
        String taxableIncameInData;
        String evadeInData;

        for(int i=0;i<data.size();i++){
            refundInData = data.get(i).get("refund");
            evadeInData = data.get(i).get("evade");
            maritalStatusInData = data.get(i).get("maritalStatus");
            taxableIncameInData = data.get(i).get("taxableIncome");

            if(evadeInData.equals("yes")){
                countOfEvadeYes++;
                taxableIncomesYes.add(Integer.parseInt(taxableIncameInData));//获得taxableIncome|yes的所有数据

                if(refundInData.equals("no")){//获得evade为no中refund各类的数量
                    numOfRefundNo++;
                }else if(refundInData.equals("Yes")){
                    numOfRefundYes++;
                }

                if(maritalStatusInData.equals("single")){//获得evade为no中maritalStatus各类的数量
                    numOfmaritalStatusYesSingle++;
                }else if(maritalStatusInData.equals("married")){
                    numOfmaritalStatusYesMarried++;
                }else if(maritalStatusInData.equals("divorced")){
                    numOfmaritalStatusYesDivorced++;
                }
            }
        }
        Pro_OfVYRN = GetProabilityOfYes(numOfRefundNo);
        Pro_OfVYRY = GetProabilityOfYes(numOfRefundYes);
        Pro_OfVYMD = GetProabilityOfYes(numOfmaritalStatusYesDivorced);
        Pro_OfVYMM = GetProabilityOfYes(numOfmaritalStatusYesMarried);
        Pro_OfVYMS = GetProabilityOfYes(numOfmaritalStatusYesSingle);
    }

    public double GetProabilityOfNo(double num){
        return num/countOfEvadeNo;
    }

    public double GetProabilityOfYes(double num){
        return num/countOfEvadeYes;
    }

    public double GetAvg(ArrayList<Integer> list){
        int count = 0;
        int size = list.size();
        for(int i = 0;i<size;i++){
            count += list.get(i);
        }
        return count/size;
    }

    public double GetVar(ArrayList<Integer> list,double avg){
        int count = 0;
        int size = list.size();
        for(int i=0;i<size;i++){
            count += Math.pow(list.get(i)-avg,2);
        }
        return count/size;
    }

    public double function(double avg,double var,int currNum){//正态分布函数求非连续的概率
        double former = 1;
        double latter = 1;
        former = 1/(Math.sqrt(2*Math.PI)*Math.sqrt(var));
        latter = Math.pow(Math.E,-((Math.pow((currNum-avg),2))/(2*var)));
        return former*latter;
    }
}
