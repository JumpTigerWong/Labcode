import java.util.HashMap;
import java.util.Map;
class LexcicalAnalyzer {
    static public Map<String, Integer> keyTable;

    static public void initKeyTable() {//初始化保留字
        keyTable = new HashMap<String, Integer>();
        String key[] = {"#", "begin", "if", "then", "while", "do", "end", "+", "-", "*", "/", ":",
                ":=", "<", "<>", "<=", ">", ">=", "=", ";", "(", ")"};
        for (int i = 0; i < key.length; i++) {
            if (i <= 6) {
                keyTable.put(key[i], i);
            } else {
                keyTable.put(key[i], i + 6);
            }
        }
    }

    static public void analyzer(String str) {//分析过程，遇到空格或是保留字就停止读入
        StringBuffer sBuffer = new StringBuffer();
        for(int i=0;i<str.length();i++){
            char temp = str.charAt(i);
            if (keyTable.containsKey(String.valueOf(temp))){
                String finalWord = sBuffer.toString();
                if(finalWord.length()!=0){
                    kindOfWord(finalWord);
                }
                System.out.println("< "+temp+" , "+keyTable.get(String.valueOf(temp))+" >");
                sBuffer = new StringBuffer();
            }else if(temp == ' '){
                String finalWord = sBuffer.toString();
                kindOfWord(finalWord);
                sBuffer = new StringBuffer();
            }else{
                sBuffer.append(temp);
            }
        }
    }

    static public void kindOfWord(String finalWord){//判断非标识符
        if(keyTable.containsKey(finalWord)){
            System.out.println("< "+finalWord+" , "+keyTable.get(finalWord)+" >");
        }else if(isLetter(finalWord)){
            System.out.println("< "+finalWord+" , "+"10 >");
        }else if(isTigit(finalWord)){
            System.out.println("< "+finalWord+" , "+"11 >");
        }else{
            System.out.println(finalWord+"是非法字符");
        }
    }

    static public boolean isLetter(String str){//判断是否是标识符
        for (int i=0;i<str.length();i++){
            int isLetter=str.charAt(0)-'0';
            if(isLetter>16&isLetter<43||isLetter>48&&isLetter<74){
                continue;
            }else if(i>0&&isLetter>0&&isLetter<9){
                continue;
            }else{
                return false;
            }
        }
        return true;
    }

    static public boolean isTigit(String str){//判断是否是数字
        for(int i=0;i<str.length();i++){
            int isTigit=str.charAt(i)-'0';
            if(isTigit>=0&&isTigit<=9){
                continue;
            }else{
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String testInput = "x:=9;if x>0 1sadas begina then x:=2*x+1/3;end#";
        initKeyTable();
        analyzer(testInput);
    }
}
