//entity.Stack
package entity;

import java.util.ArrayList;

public class Stack {
	private ArrayList<Word> stack = new ArrayList<>();

	public Stack() {
		super();
	}
	
	public void push(Word n) {
		this.stack.add(n);
	}

	//获取栈的大小
	public int size() {
		return this.stack.size();
	}
	
	//获取当前读取的栈元素下标
	public Word get(int index) {
		return this.stack.get(index);
	}
}
