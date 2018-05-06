//analysis.Analysis
package analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import entity.Stack;
import entity.Word;
import symbolTable.KEYWORD;
import symbolTable.SYMBOL_BOOL;
import symbolTable.SYMBOL_BOUNDARY;
import symbolTable.SYMBOL_OPRATION;

public class Analysis {
	private int line;
	private String flag = "";
	
	//扫描文件，读入源代码文件，去除空格、换行符等 返回 源文件字符串
	public Stack Scan(String path){
		//识别出来的单词符号序列入栈保存
		Stack WORD_SQU = new Stack();
		String content = ReadFromFile(path);
		//将制表符替换为四个空格
		content = content.replace("\t", "    ");
		//读取文件到第几行
		line = 0;
		//循环次数
		int i = 0;
		//最小元单词
		String word;
		
		while (i < content.length()) {
			word = "";
			//从第一个字符开始，返回指定索引处的字符
			char token = content.charAt(i);
			
			//遇到空格和换行时跳过，换行后line+1
			if(token == ' ' || token =='\r') {
				i++;
				continue;
			}
			if(token == '\n' ) {
				i++;
				line++;
				continue;
			}
			
			//判断读入字符串是否是数字的DFA
			while(isNumber(token)) {
				word += token;
				++i;
				try {
					token = content.charAt(i);
					if(!isNumber(token))
						flag = "notNumber";
				} catch (Exception e) {
					break;
				}
			}
			if(flag.equals("notNumber")) {
				flag = "";
				WORD_SQU.push(new Word("num", word, line));
				continue;
			}
			
			//判断读入字符串是否是关键字或标识符的DFA
			while(isLetter(token)/* || token=='.'*/ || token == '_' || token == '$' || isNumber(token)) {
				word += token;
				++i;
				try {
					token = content.charAt(i);
					if(!isLetter(token))
						flag = "notLetter";
				} catch (Exception e) {
					break;
				}	
			}
			//如果读入单词全为字母组成，则需继续判断其为关键字还是标识符
			if(flag.equals("notLetter")) {
				flag = "";
				if(isKeyWord(word)) {
					WORD_SQU.push(new Word("keyword", word, line));
				}else {
					WORD_SQU.push(new Word("id", word, line));
				}
				continue;
			}
			
			//判断读入字符串是否是布尔运算符的DFA
			while(isBool(token)) {
				word += token;
				++i;
				try {
					token = content.charAt(i);
					if(!isBool(token))
						flag = "notBool";
				} catch (Exception e) {
					break;
				}
			}
			if(flag.equals("notBool")) {
				flag = "";
				//如果是是算术运算符"=" 
				if(word.equals("=")) {
					WORD_SQU.push(new Word("opration", word, line));
				}else{
					//如果是布尔运算符"=="
					WORD_SQU.push(new Word("bool", word, line));
				}
				continue;
			}
			
			//判断读入字符串是否是算术运算符的DFA
			while(isOpration(token)) {
				word += token;
				++i;
				try {
					token = content.charAt(i);
					if(!isOpration(token))
						flag = "notOp";
				} catch (Exception e) {
					break;
				}
			}
			if(flag.equals("notOp")) {
				WORD_SQU.push(new Word("opration", word, line));
				flag = "";
				continue;
			}
			
			//判断读入字符串是否是界符的DFA
			if(isBoundary(token)) {
				word += token;
				++i;
				WORD_SQU.push(new Word("boundary", word, line));
				continue;
			}
		}
		return WORD_SQU;
	}
	
	//读取文件的内容并以字符串的形式输出
	public static String ReadFromFile(String path) {
		File file = new File(path);
		//字符串变量,适用于字符串对象经常改变的情况
		StringBuilder result = new StringBuilder();
		
		 try{
			 BufferedReader br = new BufferedReader(new FileReader(file));
	         String s = null;
	         //当读入新的一行内容不为空时执行
	         while((s = br.readLine())!=null) {
	        	 result.append(s + System.lineSeparator());
	         }
	         br.close();
		 }catch(Exception e){
	            e.printStackTrace();
	     }
		 return result.toString();
	}
	
	//判断读入字符是否为数字
	public boolean isNumber(char token) {
		try {
			//+""可以使括号内的内容转换成字符串
			Integer.parseInt(token+"");
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	//判断读入字符是否为字母
	public boolean isLetter(char token) {
		if((token >= 'A' && token <= 'Z') || (token >= 'a' && token <= 'z')) {
			return true;
		}else {
			return false;
		}
	}
	
	//判断读入字符串是否为关键字
	public Boolean isKeyWord(String word) {
		String[] KEYWORD_LIST = KEYWORD.keyWordList;
		for (int i = 0; i < KEYWORD_LIST.length; i++) {
			if (word.equals(KEYWORD_LIST[i])) {
				return true;
			}
		}
		return false;
	}
	
	//判断读入字符串是否为布尔运算符
	public boolean isBool(char token) {
		for (char bool : SYMBOL_BOOL.getList()) {
			if (token == bool) {
				return true;
			}
		}
		return false;
	}
	
	//判断读入字符串是否为算术运算符
	public boolean isOpration(char token) {
		for (char op : SYMBOL_OPRATION.getList()) {
			if (token == op) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isBoundary(char c) {
		char[] BOUNDARY = SYMBOL_BOUNDARY.getList();
		for (int i = 0; i < BOUNDARY.length; i++) {
			//参数字符为界符时返回 true
			if(c == BOUNDARY[i]){
				return true;
			}
		}
		return false;
	}
}
