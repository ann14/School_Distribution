
package hw4;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.*;
import java.util.Scanner;
import java.lang.String;

public class Score_Sorting{
    static String[][] data = new String [500][4];
    static int[][] wish=new int [500][12];
    static int[] school_num={59,30,44,70,42,35,57,44,54,25};
    static int[] count={0,0,0,0,0,0,0,0,0,0};
    static node[] node = new node[500];


    public static void main(String[] args) throws IOException {
        
        rdFile();//讀成績txt檔
        rdWish();//讀志願序txt檔
        CountTotal();//算總分
        BubbleSort();//氣泡排序
	//InsertionSort();//選擇排序 
        distribution();//分志願
        print1();//印出各個學校錄取誰以及總共錄取多少人
        print2();//印出誰沒有學校
        print3();//印出所有人的成績以及錄取學校
    }
    private static void BubbleSort() {
	while(true) {
            boolean bool = false;
		for(int i = 0 ;i<node.length-1;i++) {
                    if(node[i].total<node[i+1].total) { //總分可比則互換
			swapp(i,i+1);
                        //swapwish(i,i+1);
			bool=true;
                    }else if(node[i].total== node[i+1].total) {//總分相同比國文
			if(node[i].chinese<node[i+1].chinese) { //國文可比則互換
                            swapp(i,i+1);
                            //swapwish(i,i+1);
                            bool=true;
			}else if (node[i].chinese == node[i+1].chinese) {//國文相同比英文
                            if(node[i].english<node[i+1].english) { //英文可比則互換
                            swapp(i,i+1);
                            //swapwish(i,i+1);
                            bool=true;
                            }else if (node[i].english==node[i+1].english) {//英文相同比數學
				if(node[i].math<node[i+1].math) {//數學可比則互換
                                    swapp(i,i+1);
                                    //swapwish(i,i+1);
                                    bool=true;
				}
                            }
			}
                    }
		}
		if(!bool) {
                    break;
                }
	}		
    }
    
    private static void CountTotal() {
	int num=0;
	for(String[] data:data) {
            int chinese = Integer.parseInt(data[1]);
            int english = Integer.parseInt(data[2]);
            int math = Integer.parseInt(data[3]);
            
            node n = new node(data[0],chinese,english,math);
            node[num] = n;
            num++;
	}
    }
    
    private static void InsertionSort(){
        for(int fu=1;fu<node.length;fu++){
            int curTotal=node[fu].total;
            int curChi=node[fu].chinese;
            int curEng=node[fu].english;
            int curMath=node[fu].math;
            int place;
            for(place=fu-1;place >= 0; place--){
                if(node[place].total>curTotal){   
                    break;//前總分大於後總分則結束
                } 
                else if(node[place].total==node[place+1].total){//前總分等於後總分則做下面的
                    if(node[place].chinese>curChi){
                        break;//前中文大於後中文則結束
                    }
                    else if(node[place].chinese==node[place+1].chinese){//前中文等於後中文則做下面的
                        if(node[place].english>curEng){
                            break;//前英文大於後英文則結束
                        }
                        else if(node[place].english==node[place+1].english){//前英文等於後英文則做下面的
                            if(node[place].math>curMath){
                                break;//前數學大於後數學則結束
                            }swapp(place,place+1);//前數學小於後數學則換
                        }
                        else{
                            swapp(place,place+1);//前英文小於後英文則換
                        }
                    }
                    else{
                        swapp(place,place+1);//前中文小於後中文則換
                    }
                }
                else {
                    swapp(place,place+1);//前總分小於後總分則換
                }       
            }
        }
    }

    private static void swapp(int i, int j) {
	node tem = node[i];
	node[i] = node[j];
	node[j] = tem;
    }

    
    private static void distribution(){ 

        for(int i=0;i<node.length;i++){
            int num=Integer.parseInt(node[i].name.substring(1,4));

            for(int j=0;j<wish.length;j++){
                if(num==wish[j][0]){//學生的號碼等於他的志願序的號碼
                    for(int a=1;wish[j][a]!=0 && a<wish[j].length-1;a++){//還沒遇到o之前都走訪志願序
                        if(school_num[wish[j][a]-1]!=0){//學校還剩幾個位置
                            
                            node[i].school=wish[j][a];
                            node[i].have_school=true;
                            school_num[wish[j][a]-1]--;
                            break;
                            
                        }
                    }
                    
                }
                if(node[i].have_school){
                    break;
                }
            }
        }
    }
 
    public static void rdFile() {
        try {
            File f = new File("D:\\scores.txt");
            Scanner scn = new Scanner(f);
            int a=0;
            while(scn.hasNextLine()) {
		String scores = scn.nextLine();
		data[a] = scores.split(",");
		a++;
            }
            scn.close();
	}catch(FileNotFoundException e){
            System.out.println("Error occured");
            e.printStackTrace();
	}
    }
    
        public static void rdWish() {
        try {
            File f = new File("D:\\wish.txt");
		Scanner scn = new Scanner(f);
		int i=0;
		while(scn.hasNextLine()) {
                    String data = scn.nextLine();
                    String[] wi = data.split(" ");
				for(int j = 0 ;j<wi.length;j++) {
					if(Integer.parseInt(wi[j]) !=0 ) {
						wish[i][j] = Integer.parseInt(wi[j]);
					}
				}
				i++;
			}
			scn.close();
       
	}catch(FileNotFoundException e){
            System.out.println("Error occured");
            e.printStackTrace();
	}
        }

    private static void print1() {

        for(int i=0;i<school_num.length;i++){
            System.out.println("錄取學校："+(i+1));
            for(node o:node){
                if(o.school==i+1){
                    System.out.print(o.name+",");
                    count[i]++;                 
                }
            }
            System.out.println("");
            System.out.println("共錄取"+count[i]+"位");
            System.out.println("-------");
        }
    }

    private static void print2() {
        int count=0;
        System.out.println("未錄取學校：");        
        for(node o:node){
                if(o.school==0){
                    System.out.print(o.name+",");
                    count++;
                }
        }
        System.out.println("");
        System.out.println("共有"+count+"位未錄取學校");
        System.out.println("-------");
        }

    private static void print3(){
        System.out.println("學號\t總分\t學校");
	for(node n:node) {
            System.out.println(n.name+"\t"+n.total+"\t"+n.school);
		}    
    }    
   
}


