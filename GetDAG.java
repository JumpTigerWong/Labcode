import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Node{
    char element;
    Node right = null;
    Node left =  null;
    Node(char element){
        this.element = element;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setLeft(Node left) {
        this.left = left;
    }
}

class DAG{
    String [] dagVexs;//顶点集
    int [][]dagMaterix;//邻接矩阵
}

public class GetDAG {
    public static String exp = "A+B*(C-D)+E/(C-D)^N";//用于测试的输入表达式 A+B*(C-D)+E/(C-D)^N

    public static String suffExp(String string){//将字符串转化成后缀表达式
        Stack <Character> stack = new Stack<>();
        String suffExpssion = new String();//用来存放后缀表达式
        for(int i=0;i<string.length()&&string!=null;i++){
            char temp = string.charAt(i);
            if(temp!=' '){
                if(temp=='('){//左括号
                    stack.push(temp);
                }else if(temp==')'){
                    char ac = stack.pop();
                    while(ac!='('){//右括号
                        suffExpssion=suffExpssion.concat(String.valueOf(ac));
                        ac=stack.pop();
                    }
                }else if(isOperater(temp)){//运算符
                    if(!stack.empty()){
                        char ac = stack.pop();
                        while(!stack.empty()&&priority(ac)>=priority(temp)){//栈取出的字符优先级比temp高
                            suffExpssion = suffExpssion.concat(String.valueOf(ac));
                            ac=stack.pop();
                        }
                        if(ac!=' '){//栈取出的字符优先级比temp低，则将取出的字符重新入栈
                            stack.push(ac);
                        }
                    }stack.push(temp);//temp入栈
                }else{//为数字，直接入栈
                    suffExpssion = suffExpssion.concat(String.valueOf(temp));
                }
            }
        }

        while(!stack.empty()){
            suffExpssion = suffExpssion.concat(String.valueOf(stack.pop()));
        }

        return suffExpssion;
    }

    public static boolean isOperater(char c){//判断是不是运算符
        if(c=='+'||c=='-'||c=='*'||c=='/'||c=='^'){
            return true;
        }
        return false;
    }

    public static int priority(char c){//运算符优先级
        if(c=='^'){
            return 3;
        }else if(c=='*'||c=='/'){
            return 2;
        }else if(c=='+'||c=='-'){
            return 1;
        }
        return 0;
    }

    public static Node suffExpToTree(String exp){//后缀表达式转AST
        Stack<Node> stack = new Stack<Node>();
        for(int i=0;i<exp.length();i++){
            char ch = exp.charAt(i);
            if(!isOperater(ch)){//不是运算符直接创建新的节点，入栈
                Node node = new Node(ch);
                stack.push(node);
            }else{//是运算符，创建符号节点，建立连接。
                if(stack.isEmpty()||stack.size()<2){//排除出错情况
                    System.out.println("error");
                    return null;
                }else{
                    Node node = new Node(ch);
                    Node right = stack.pop();
                    Node left = stack.pop();
                    node.setLeft(left);
                    node.setRight(right);
                    stack.push(node);
                }
            }
        }
        return stack.pop();
    }

    public static void printTree(Node node,int depth){//用来输出二叉树
        Node curNode = node;
        if(curNode!=null){
            System.out.println(node.element+" "+String.valueOf(depth));
            printTree(node.left,depth+1);
            printTree(node.right,depth+1);
        }
    }

    public static void helper(Node root,List<String> mVexs,int depth){//先序遍历二叉树
        if(root!=null){
            if(mVexs.contains(String.valueOf(root.element)+depth)){
                mVexs.add(String.valueOf(root.element)+depth+"1");
            }else{
                mVexs.add(String.valueOf(root.element)+depth);
            }
            if(root.left!=null){
                helper(root.left,mVexs,depth+1);
            }
            if(root.right!=null){
                helper(root.right,mVexs,depth+1);
            }
        }
    }

    /*
    * 1.查找相同的字符。
    * 2.在生成的抽象语法树中查找
    * 3.找到对应的根节点
    * 4.查找子节点是否相同
    * 5.如果相同说明是相同的操作，在保存到DAG的邻接矩阵时做相应的操作
    * 6.如果不同不用操作,直接保存
    */

    public static void getDAG(Node root,String string,DAG dag){//优化只用存储符号？
        int length = string.length();
        List<String> mVexs = new ArrayList<>();//用来存储树中各数字和操作符之间的关系。
        List<Integer> sameOper = new ArrayList<>();//用来存储两个相同的操作符的下标
        dag.dagVexs = new String[length];
        dag.dagMaterix = new int[length][length];
        for(int i=0;i<length;i++){//初始化有向图的邻接矩阵
            for(int j=0;j<length;j++){
                dag.dagMaterix[i][j]=0;
            }
        }
        helper(root,mVexs,0);
        for(int i=0;i<mVexs.size();i++){
            dag.dagVexs[i] = mVexs.get(i);
        }
        List<String> isRepet = new ArrayList<>();
        initMatrix(dag,root,0,isRepet);
        System.out.println("有向图为:");
        for(int i=0;i<dag.dagVexs.length;i++){
            System.out.print(dag.dagVexs[i].charAt(0)+",");
        }
        System.out.println();
        for(int i=0;i<dag.dagVexs.length;i++){
            for(int j=0;j<dag.dagVexs.length;j++){
                System.out.print(dag.dagMaterix[i][j]+",");
            }
            System.out.println();
        }
        sameOper = findDupOper(dag);
        if(sameOper.isEmpty()){
            System.out.println("输入的算符表达式中没有相同的操作");
            return;
        }
        int superIndex = findSuper(dag,sameOper.get(1));//操作符的父节点
        dag.dagMaterix[superIndex][sameOper.get(0)]=1;
        List<Integer> indexOfSon = findSon(dag,sameOper.get(1));//操作符的子节点
        removeE(dag.dagVexs[indexOfSon.get(1)],dag);
        removeE(dag.dagVexs[indexOfSon.get(0)],dag);
        removeE(dag.dagVexs[sameOper.get(1)],dag);
        System.out.println("生成的DAG为:");
        for(int i=0;i<dag.dagVexs.length;i++){
            System.out.print(dag.dagVexs[i].charAt(0)+",");
        }
        System.out.println();
        for(int i=0;i<dag.dagVexs.length;i++){
            for(int j=0;j<dag.dagVexs.length;j++){
                System.out.print(dag.dagMaterix[i][j]+",");
            }
            System.out.println();
        }
    }

    public static int findSuper(DAG dag,int index){
        for(int i=0;i<dag.dagVexs.length;i++){
            if(dag.dagMaterix[i][index]==1){
                return i;
            }
        }
        return -1;
    }

    public static List<Integer> findSon(DAG dag,int index){
        List<Integer> indexOfSon = new ArrayList<>();
        for(int i=0;i<dag.dagVexs.length;i++){
            if(dag.dagMaterix[index][i]==1){
                indexOfSon.add(i);
            }
        }
        return indexOfSon;
    }

    public static void initMatrix(DAG dag,Node root,int depth,List<String> isRepet){//重复判断
        if(root!=null){
            String s = root.element+String.valueOf(depth);
            if(isRepet.contains(s)){
                s = s+"1";
                isRepet.add(s);
            }else{
                isRepet.add(s);
            }
            if(root.left!=null){
                String sl = root.left.element+String.valueOf(depth+1);
                if(isRepet.contains(sl)){
                    sl = sl+"1";
                }
                dag.dagMaterix[getIndex(dag,s)][getIndex(dag,sl)]=1;
                initMatrix(dag,root.left,depth+1,isRepet);
            }
            if(root.right!=null){
                String sr = root.right.element+String.valueOf(depth+1);
                if(isRepet.contains(sr)){
                    sr = sr+"1";
                }
                dag.dagMaterix[getIndex(dag,s)][getIndex(dag,sr)]=1;
                initMatrix(dag,root.right,depth+1,isRepet);
            }
        }
    }

    public static int getIndex(DAG dag,String s){//获得对应字母在字母表中序号
        for(int i=0;i<dag.dagVexs.length;i++){
            if(dag.dagVexs[i].equals(s)){
                return i;
            }
        }
        return -1;
    }

    public static List<Integer> findDupOper(DAG dag){//获得两个相同操作的下标
        int index1=0,index2=0;
        List<Integer> res = new ArrayList<>();
        for(int i=0;i<dag.dagVexs.length-1;i++){
            boolean flag = false;
            for(int j=i+1;j<dag.dagVexs.length-1;j++){
                index1 = i;
                if(dag.dagVexs[index1].charAt(0)==dag.dagVexs[j].charAt(0)) {
                    index2 = j;
                    flag = true;
                }
            }
            if(is_Son(dag,index1,index2)&&flag){//找到左右子树
                res.add(index1);
                res.add(index2);
                return res;
            }
        }
        return res;
    }

    public static boolean is_Son(DAG dag,int index1,int index2){//判断相同操作中左右子树
        int ltreeNodeindex1=0,ltreeNodeindex2=0;
        int rtreeNodeindex1=0,rtreeNodeindex2=0;
        int length = dag.dagVexs.length;
        //寻找相同操作的左右子树
        for(int i=0;i<length;i++){
            if(dag.dagMaterix[index1][i]==1&&ltreeNodeindex1==0){
                ltreeNodeindex1 = i;
            }else if(dag.dagMaterix[index1][i]==1&&rtreeNodeindex1==0){
                rtreeNodeindex1 = i;
            }
        }
        for(int i=0;i<length;i++){
            if(dag.dagMaterix[index2][i]==1&&ltreeNodeindex2==0){
                ltreeNodeindex2 = i;
            }else if(dag.dagMaterix[index2][i]==1&&rtreeNodeindex2==0){
                rtreeNodeindex2 = i;
            }
        }
        //判断左右子树是否相同
        if(dag.dagVexs[ltreeNodeindex1].charAt(0)==dag.dagVexs[ltreeNodeindex2].charAt(0)&&
           dag.dagVexs[rtreeNodeindex1].charAt(0)==dag.dagVexs[rtreeNodeindex2].charAt(0)){
            return true;
        }
        return false;
    }

    public static void removeE(String string,DAG dag){//删除重复的操作
        int index = getIndex(dag,string);
        for(int i=index;i<dag.dagVexs.length-1;i++){
            for(int j=index;j<dag.dagVexs.length-1;j++){
                dag.dagMaterix[i][j]=dag.dagMaterix[i][j+1];
            }
            dag.dagMaterix[i]=dag.dagMaterix[i+1];
        }
        String []temp = new String [dag.dagVexs.length-1];
        int j=0;
        for(int i=0;i<temp.length;i++){
            if(dag.dagVexs[i].equals(string)){
                temp[i] = dag.dagVexs[++j];
                j++;
                continue;
            }else{
                temp[i] = dag.dagVexs[j++];
            }
        }
        dag.dagVexs = new String[temp.length];
        for(int i=0;i<temp.length;i++){
            dag.dagVexs[i] = temp[i];
        }
    }

    public static void main(String[] args) {
        DAG dag = new DAG();
        exp=suffExp(exp);
        System.out.println("后缀表达式为:");
        System.out.println(exp);
        Node root = suffExpToTree(exp);
        System.out.println("抽象语法树为:");
        printTree(suffExpToTree(exp),0);
        getDAG(root,exp,dag);
    }
}

