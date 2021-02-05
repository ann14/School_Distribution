
package hw4;


public class node {
    String name;
    int chinese;
    int english;
    int math;
    int total;
    int school;
    boolean have_school=false;
	
    node(String name,int chinese,int english,int math){
	this.name = name;
	this.chinese = chinese;
	this.english = english;
	this.math = math;
	this.total = chinese + english+ math;
        
    }
	
}
