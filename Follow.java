import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Follow {
    public static List<String> firstList = new ArrayList<>();
    public static List<String> followList = new ArrayList<>();

    public static String readGrammarFile (String path) throws Exception {//从文件中读取文法
        StringBuilder grammer = new StringBuilder();
        File file = new File(path);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String readline;
        while((readline=bufferedReader.readLine())!=null){
            grammer.append(readline+"\n");
        }
        return grammer.toString();
    }

    public static List<String> getGrammar(String string){//获得文法
        List<String> oldList = new ArrayList<>();
        String [] grammerItems = string.split("\n");
        for(int i=0;i<grammerItems.length;i++){
            oldList.add(grammerItems[i]);
        }
        return oldList;
    }

    public static List<String> getGrammarItems(String string){//获得产生式
        List<String> oldList = new ArrayList<>();
        List<String> oldListItems = new ArrayList<>();
        String [] grammerItems = string.split("\n");
        for(int i=0;i<grammerItems.length;i++){
            oldList.add(grammerItems[i]);
            String []items = grammerItems[i].split(">|\\|");//用>或者｜分割字符串
            for(int j=1;j<items.length;j++){
                oldListItems.add(grammerItems[i].charAt(0)+"->"+items[j]);
            }
        }
        return oldListItems;
    }

    public static List<String> getVN(String grammarItems){//获得非终结符
        List<String> vn = new ArrayList<>();
        String []string = grammarItems.split("\n");
        for(int i=0;i<string.length;i++){
            int index = string[i].indexOf("-");
            String tempStr = string[i].substring(0,index);
            if(vn.size()==0){
                vn.add(tempStr);
            }else{
                if(!isContain(vn,tempStr)){
                    vn.add(tempStr);
                }
            }
        }
        return vn;
    }

    public static List<String> getVT(List<String> newList){//获得终结符
        List<String> list=new ArrayList<>();
        for(int i=0;i<newList.size();i++){
            int index1=newList.get(i).indexOf('>')+1;
            String str=newList.get(i).substring(index1);
            for(int k=0;k<str.length();k++){
                if(!Character.isUpperCase(str.charAt(k))){//不是大写
                    if(str.charAt(k)!='’'&&str.charAt(k)!='~'){//～表示空
                        if(!isContain(list, ""+str.charAt(k))){
                            list.add(""+str.charAt(k));
                        }
                    }
                }
            }
        }
        return list;
    }

    public static boolean isContain(List<String> vn,String str){
        for(int i=0;i<vn.size();i++){
            if(vn.get(i).equals(str)){
                return true;
            }
        }
        return false;
    }

    public static boolean isContain(List<String> list){
        for(int i=0;i<list.size();i++){
            if(list.get(i).equals("~")){
                return true;
            }
        }
        return false;
    }

    public static boolean isVN(String ch,List<String> vn){//判断非终结符
        for(int i=0;i<vn.size();i++){
            if(ch.equals(vn.get(i))){
                return true;
            }
        }
        return false;
    }

    public static List<String> removeLeftRecursive(List<String> oldListItems){//消除左递归
        List<String> newList=new ArrayList<>();
        for(int i=0;i<oldListItems.size();i++){
            if(oldListItems.get(i).charAt(0)==oldListItems.get(i).charAt(3)){//E->E
                for(int j=0;j<oldListItems.size();j++){
                    if(oldListItems.get(j).startsWith(""+oldListItems.get(i).charAt(0)) &&oldListItems.get(j).length()==4){//
                        newList.add(oldListItems.get(i).charAt(0)+"->"+oldListItems.get(j).charAt(3)+oldListItems.get(i).charAt(0)+"’");
                        newList.add(oldListItems.get(i).charAt(0)+"’"+"->"+oldListItems.get(i).substring(4, oldListItems.get(i).length())+oldListItems.get(i).charAt(0)+"’");
                        newList.add(oldListItems.get(i).charAt(0)+"’"+"->"+"~");
                    }
                }
            }else{
                int tag=0;//用于标记是否重复
                for(int k=0;k<newList.size();k++){
                    if(newList.get(k).contains(oldListItems.get(i).substring(1, oldListItems.get(i).length()))){
                        tag=1;
                        break;
                    }
                }
                if(tag==0){
                    newList.add(oldListItems.get(i));
                }
            }
        }
        return newList;
    }

    public static Map<String,List<String>> getFirst(List<String> vn,List<String> newList){//获得first集
        Map<String,List<String>> oneFirstList=new HashMap<>();
        for(int i=0;i<vn.size();i++){
            firstList.clear();
            getOneFirst(vn.get(i),newList);
            List<String> temp = new ArrayList<>();
            for(int k=0;k<firstList.size();k++){
                temp.add(firstList.get(k));
            }
            oneFirstList.put(vn.get(i), temp);
        }
        return oneFirstList;
    }

    public static void getOneFirst(String vnItem,List<String> newList){
        for(int i=0;i<newList.size();i++){
            if(vnItem.length()==1){//添加原本存在的非终结符
                if(newList.get(i).startsWith(vnItem)&&newList.get(i).charAt(1)=='-'){
                    int index=newList.get(i).indexOf('>')+1;
                    if(Character.isUpperCase(newList.get(i).charAt(index))){
                        getOneFirst(""+newList.get(i).charAt(index),newList);
                    }else{
                        if(!isContain(firstList,""+newList.get(i).charAt(index))){
                            firstList.add(""+newList.get(i).charAt(index));
                        }
                    }
                }
            }else{//添加消除左递归后的非终结符
                if(newList.get(i).startsWith(vnItem)){
                    int index=newList.get(i).indexOf('>')+1;
                    if(Character.isUpperCase(newList.get(i).charAt(index))){
                        getOneFirst(""+newList.get(i).charAt(index),newList);
                    }else{
                        if(!isContain(firstList,""+newList.get(i).charAt(index))){
                            firstList.add(""+newList.get(i).charAt(index));
                        }
                    }
                }
            }
        }
    }

    public static void getOneFollow(String vnItem,List<String> vn,List<String> newList,Map<String,List<String>> onefirstList){//求follow集合的第一条
        if(vnItem.equals(vn.get(0))){
            if(!isContain(followList,"#")){
                followList.add("#");
            }
        }//求follow集合的第二条,字符后是终结符
        for(int i=0;i<newList.size();i++){
            int index1=newList.get(i).indexOf(">")+1;
            String str=newList.get(i).substring(index1);
            if(vnItem.length()==1){//非终结符中不含'的follow集
                if(str.contains(vnItem)&&str.charAt(str.indexOf(vnItem.charAt(0))+1)!='’'){//用来判断是E而不是E'
                    if(str.indexOf(vnItem.charAt(0))!=str.length()){
                        String temp=str.substring(str.indexOf(vnItem.charAt(0))+1);//寻找相关符号之后的一个非终结符
                        StringBuilder stringBuilder=new StringBuilder();
                        stringBuilder.append(temp);
                        for(int k=stringBuilder.length();k>1;k--){
                            if(isVN(stringBuilder.toString(),vn)){
                                break;
                            }else{
                                int length=stringBuilder.length();
                                stringBuilder.deleteCharAt(length-1);
                            }
                        }
                        if(stringBuilder.length()>0){
                            if(Character.isUpperCase(stringBuilder.charAt(0))){
                                if(onefirstList.containsKey(stringBuilder.toString())){
                                    List<String> list=onefirstList.get(stringBuilder.toString());
                                    for(int q=0;q<list.size();q++){
                                        if(!list.get(q).equals("~")){
                                            if(!isContain(followList,list.get(q))){
                                                followList.add(list.get(q));
                                            }
                                        }
                                    }
                                }
                            }else{
                                if(!isContain(followList,stringBuilder.toString())){
                                    followList.add(stringBuilder.toString());
                                }
                            }
                        }
                    }
                }
            }else{//非终结符中含’的follow集
                if(str.contains(vnItem)){
                    int index=str.indexOf(vnItem);
                    String left=str.substring(index+vnItem.length(), str.length());
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(left);
                    for(int k=stringBuilder.length();k>1;k--){
                        if(isVN(stringBuilder.toString(),vn)){
                            break;
                        }else{
                            int length=stringBuilder.length();
                            stringBuilder.deleteCharAt(length);
                        }
                    }
                    if(stringBuilder.length()>0){
                        if(Character.isUpperCase(stringBuilder.charAt(0))){
                            if(onefirstList.containsKey(stringBuilder.toString())){
                                List<String> list=onefirstList.get(stringBuilder.toString());
                                for(int q=0;q<list.size();q++){
                                    if(!list.get(q).equals("~")){
                                        if(!isContain(followList,list.get(q))){
                                            followList.add(list.get(q));
                                        }
                                    }
                                }
                            }
                        }else{
                            if(!isContain(followList,stringBuilder.toString())){
                                followList.add(stringBuilder.toString());
                            }
                        }
                    }
                }
            }
        }
        //求follow集合的第三条,字符后是非终结符
        for(int i=0;i<newList.size();i++){
            int index1=newList.get(i).indexOf(">")+1;
            String str=newList.get(i).substring(index1);
            if(vnItem.length()==1){
                if(str.contains(vnItem)){
                    if(str.indexOf(vnItem)==str.length()&&str.charAt(str.indexOf(vnItem.charAt(0))+1)!='’'){
                        String s=newList.get(i).substring(0,newList.get(i).indexOf("-"));
                        getOneFollow(s, vn, newList, onefirstList);
                    }else{
                        String left=str.substring(str.indexOf(vnItem)+1);
                        StringBuilder stringBuilder=new StringBuilder();
                        stringBuilder.append(left);
                        for(int k=stringBuilder.length();k>1;k--){
                            if(isVN(stringBuilder.toString(),vn)){
                                break;
                            }else{
                                int length=stringBuilder.length();
                                stringBuilder.deleteCharAt(length-1);
                            }
                        }
                        if(isVN(stringBuilder.toString(), vn)){
                            if(onefirstList.containsKey(stringBuilder.toString())){
                                List<String> temp=onefirstList.get(stringBuilder.toString());
                                if(isContain(temp)){
                                    String s=newList.get(i).substring(0,newList.get(i).indexOf("-"));
                                    getOneFollow(s, vn, newList, onefirstList);
                                }
                            }
                        }
                    }
                }
            }else{
                if(str.contains(vnItem)){
                    String st=newList.get(i);
                    if(st.endsWith(vnItem)){
                        if(!st.startsWith(vnItem)){
                            String s=newList.get(i).substring(0,newList.get(i).indexOf("-"));
                            getOneFollow(s, vn, newList, onefirstList);
                        }
                    }else{
                        String left=str.substring(str.indexOf(vnItem)+2);
                        StringBuilder stringBuilder=new StringBuilder();
                        stringBuilder.append(left);
                        for(int k=stringBuilder.length();k>1;k--){
                            if(isVN(stringBuilder.toString(),vn)){
                                break;
                            }else{
                                int length=stringBuilder.length();
                                stringBuilder.deleteCharAt(length-1);
                            }
                        }
                        if(isVN(stringBuilder.toString(), vn)){
                            if(onefirstList.containsKey(stringBuilder.toString())){
                                List<String> temp=onefirstList.get(stringBuilder.toString());
                                if(isContain(temp,"")){
                                    String s=newList.get(i).substring(0,newList.get(i).indexOf("-"));
                                    getOneFollow(s, vn, newList, onefirstList);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static Map<String,List<String>> getFollow(List<String> vn,List<String> newList,Map<String,List<String>> firstList){//获得follow集
        Map<String,List<String>> onefollowList=new HashMap<>();
        for(int i=0;i<vn.size();i++){
            followList.clear();
            getOneFollow(vn.get(i),vn,newList,firstList);
            List<String> temp=new ArrayList<>();
            for(int k=0;k<followList.size();k++){
                temp.add(followList.get(k));
            }
            onefollowList.put(vn.get(i),temp );
        }
        return onefollowList;
    }

    public static void main(String[] args) throws Exception {
        String path="src/com/company/data.txt";
        String grammar=readGrammarFile(path);
        System.out.println("文法如下：");
        List<String> oldList=getGrammar(grammar);
        System.out.println(oldList);
        System.out.println("文法的每一条产生式如下：");
        List<String> oldListItems=getGrammarItems(grammar);
        System.out.println(oldListItems);
        System.out.println("消除左递归后的文法如下：");
        List<String> newList=removeLeftRecursive(oldListItems);
        System.out.println(newList);
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<newList.size();i++){
            stringBuilder.append(newList.get(i)+"\n");
        }
        List<String> vn=getVN(stringBuilder.toString());
        List<String> vt=getVT(oldListItems);
        System.out.println("first集合如下：");
        Map<String,List<String>> firstList=getFirst(vn, newList);
        System.out.println(firstList);
        System.out.println("follow集合如下：");
        Map<String,List<String>> followList=getFollow(vn, newList, firstList);
        System.out.println(followList);
    }
}
