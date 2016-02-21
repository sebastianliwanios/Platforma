package com.sebastian.platforma.domain.test;

public class Testt {

	public static void main(String[] args) {
		String numerZlecenia = new String ("U/23094/2016/9");
		System.out.println(numerZlecenia.replace('a', 'v'));
		String nowaNazwa =numerZlecenia.replace('/', ' ');
		System.out.println(nowaNazwa);
		String Str = new String("Welcome to Tutorialspoint.com");

	      System.out.print("Return Value :" );
	      System.out.println(Str.replace('o', 'T'));

	      System.out.print("Return Value :" );
	      System.out.println(Str.replace('l', 'D'));
		

	}

}
