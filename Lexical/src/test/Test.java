//test.Test
package test;

import analysis.Analysis;
import entity.Stack;
import entity.Word;

public class Test {
	public static void test() {
		Analysis lex = new Analysis();
		String filePath = "D:\\eclipse\\MyProjects\\Lexical\\code.txt";
		Stack squ = lex.Scan(filePath);
		
		System.out.println("0\tLine\tName\tType");
		for (int i = 0; i < squ.size(); i++) {
			Word w = squ.get(i);
			if(w.getName().equals('\r'+"")) {
				w.setName("\\r");
			}
			if(w.getName().equals('\n'+"")) {
				w.setName("\\n");
			}
			System.out.println((i+1)+"\t"+w.getLine()+"\t"+w.getName()+"\t"+w.getType());
		}
	}
	
	public static void main(String[] args) {
		test();
	}
}
