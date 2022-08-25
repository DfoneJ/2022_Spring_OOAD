package Parser;

import java.util.ArrayList;
import StructureAnalyze.Token;

public class Parser {

	public void parserCodeToToken(String str, ArrayList<Token> tokenArrayList) {
		int i = -1, flag = 0, index = 0, commentFlag = 0;
		int quotes = 0; // 紀錄左引號
		String tmp = "";
		while (index < str.length()) {
			// System.out.println("Line 15 while");
			// System.out.println("Line 15 while:"+(index+1)+(" strL:")+str.length());
			// System.out.println("Line 114:"+str.charAt(index)+str.charAt(index+1));
//	    	if(str.charAt(index) == '/') {
//	    		for(int xx = index - 30; xx < index ; xx++) {
//	        		System.out.print("ZZZZZZZZZZZZZZZZ"+str.charAt(xx));
//	        	}
//	    		System.out.println("HAHAHA" + index + "???" + commentFlag);
//	    	}
//	    	System.out.print(str.charAt(index)+"    ");
			// 忽略//後的字串直到換行
			if (commentFlag == 5) // 如我是//開頭的註釋遇到'\n'結束
			{// System.out.println("Line 26 if");
				while (index < str.length() && str.charAt(index) != '\n') {
					index++;
				}

				commentFlag = 0;
			}
			// 忽略/*之後的字串直到*的下一個是/
			else if (commentFlag == 6) // 判斷/*開頭的註釋結束
			{ // System.out.println("Line 35 if");
				while (index < str.length()) {
					if (str.charAt(index) == '*') {
						if (index + 1 < str.length() && str.charAt(index + 1) == '/') {
							index++;
							break;
						}
					}
					index++;
				}
				commentFlag = 0;
			} // **************************
			else if (str.charAt(index) == '/') // 判斷註釋
			{ // System.out.println("Line 51 if");
				if (str.charAt(index + 1) == '/') {
					index++;
					commentFlag = 5;
				}
				if (str.charAt(index + 1) == '*') {
					index++;
					commentFlag = 6;
				}
			}
			// 將"....."生成Token物件並存入ArrayList<Token>
			else if (str.charAt(index) == '"' || str.charAt(index) == '\'') // 判斷""
			{// System.out.println("Line 65 if");
				int option = 0;
				if (str.charAt(index) == '"')
					option = 1;
				else if (str.charAt(index) == '\'')
					option = 2;
//	         	System.out.print(str.charAt(index)+"#");

				String tmpStr = "";
				Token token = new Token();
				tmpStr = tmpStr + str.charAt(index);
				quotes++;
				while (index + 1 < str.length() && quotes != 0) {// System.out.println("Line 78 while");
//	        		System.out.print(str.charAt(index)+"★");
					index++;
					tmpStr = tmpStr + str.charAt(index);
					if (str.charAt(index) == '"' && option == 1)
						break;
					if (str.charAt(index) == '\'' && option == 2)
						break;
				}
				tokenArrayList.add(token);
				i++;
				// System.out.println("tmpStr:"+tmpStr);
				tokenArrayList.get(i).setName(tmpStr);
				tokenArrayList.get(i).setType("Letter");
			}
			// 將大小寫字母、符號_、符號.生成一個Token物件並存入ArrayList<Token>
			else if (Character.isLetter(str.charAt(index)) || str.charAt(index) == '_' || str.charAt(index) == '.') // 判斷文字
			{// System.out.println("Line 95 if:"+str.charAt(index));
				Token token = new Token();
				tmp = tmp + str.charAt(index);

				if (flag != 1) {
					flag = 1; // flag = 1 表示是緊接在文字後面的數字
					i++;
					tokenArrayList.add(token);
				}
				tokenArrayList.get(i).setName(tmp);
				tokenArrayList.get(i).setType("Letter");
			}

			else if (str.charAt(index) == '*' && str.length() > index + 1) {// 2020/10/09 problem fixed:python file "*"
																			// has nothing after it.(causing null
																			// pointer exception)
																			// System.out.println("Line 115
																			// while:"+(index+1)+("
																			// strL:")+str.length());
				if (Character.isLetter(str.charAt(index + 1))) {
					// System.out.println("Line 114:"+str.charAt(index)+str.charAt(index+1));
					// System.out.println("Line 114 if");
					Token token = new Token();
					tmp = tmp + str.charAt(index);
					if (flag != 1) {
						flag = 1; // flag = 1 表示是緊接在文字後面的數字
						i++;
						tokenArrayList.add(token);
					}
					tokenArrayList.get(i).setName(tmp);
					tokenArrayList.get(i).setType("Letter");
				}

			}

			else if (str.charAt(index) == '[') {
				tmp = tmp + str.charAt(index);
				tokenArrayList.get(i).setName(tmp);
				tokenArrayList.get(i).setType("Letter");
				flag = 0;
				tmp = "";
			} else if (Character.isDigit(str.charAt(index))) // 判斷數字
			{
				if (flag == 1) {
					tmp = tmp + str.charAt(index);
					tokenArrayList.get(i).setName(tmp);
				} else if (flag == 2) {
					tmp = tmp + str.charAt(index);
					tokenArrayList.get(i).setName(tmp);
					tokenArrayList.get(i).setType("Digit");
				} else {
					flag = 2; // flag = 2 表示是緊接在數字後面的數字
					i++;
					Token token = new Token();
					tokenArrayList.add(token);
					tmp = tmp + str.charAt(index);
					tokenArrayList.get(i).setName(tmp);
					tokenArrayList.get(i).setType("Digit");
					// tmp = "";
				}
			} else if (Character.isWhitespace(str.charAt(index))) // 判斷空白
			{
				if (flag != 3) {
					flag = 3; // flag = 3 表示前一個是空白
					tmp = "";
				} else {
					flag = 0;
					tmp = "";
				}
			} else if (ispunct(str.charAt(index))) // 判斷符號
			{
				flag = 4; // flag = 4代表符號
				tmp = "";
				i++;
				Token token = new Token();
				tokenArrayList.add(token);
				tmp = tmp + str.charAt(index);
				tokenArrayList.get(i).setName(tmp);
				tokenArrayList.get(i).setType("Punctuation");
				tmp = "";
			}
//	    	System.out.println(tokenArrayList.get(i).getName()+"     "+tokenArrayList.get(i).getType()+"    "+i);
			index++;
		}
	}

	/*
	 * 設定保留字
	 */
	public void setReservedWordType(ArrayList<Token> tokenArrayList) {
		int i, j;
		String includeStr = "#include";
		i = tokenArrayList.size();

		for (j = 0; j < i; j++) {
			if (tokenArrayList.get(j).getName().equals("else")) // 將else if合併成一個token
			{
				if (tokenArrayList.get(j + 1).getName().equals("if")) {
					tokenArrayList.get(j).setName("else if");
					tokenArrayList.get(j).setType("ReservedWord");
					tokenArrayList.remove(j + 1);
					i--;
				}
			} else if (tokenArrayList.get(j).getName().equals("#")) // 將include合成一個token
			{
				if (tokenArrayList.get(j + 1).getName().equals("include")) {
					j++;
					tokenArrayList.remove(j);
					i--;

					while (!(tokenArrayList.get(j).getName().equals(">")/* add by hongzhan */ || tokenArrayList.get(j)
							.getName().contains("\"")/* add by hongzhan */)) {
						includeStr += tokenArrayList.get(j).getName();
						tokenArrayList.remove(j);
						i--;
					}

					includeStr += tokenArrayList.get(j).getName();
					tokenArrayList.get(j - 1).setName(includeStr);
					tokenArrayList.get(j - 1).setType("include");
					tokenArrayList.remove(j);
					i--;
					j--;
					includeStr = "#include";
				} else if (tokenArrayList.get(j + 1).getName().equals("define")) // 將define合成一個token，
				{
					String tmp = "";
					tmp = "#define" + " " + tokenArrayList.get(j + 2).getName() + " "
							+ tokenArrayList.get(j + 3).getName();
					tokenArrayList.get(j).setName(tmp);
					tokenArrayList.get(j).setType("define");
					tokenArrayList.remove(j + 1);
					tokenArrayList.remove(j + 1);
					tokenArrayList.remove(j + 1);
					i = i - 3;
					// j = j + 3;//? by hongzhan
				} // 僅限#define PI 3.14159此種形式，#difine max(x,y) ((x)>(y)?(x):(y))及#define _UNICODE
					// 會亂 by hongzhan
			} else if (tokenArrayList.get(j).getName().equals("+")) {
				if (tokenArrayList.get(j + 1).getName().equals("+")) {
					tokenArrayList.get(j).setName("++");
					tokenArrayList.get(j).setType("Punctuation");
					tokenArrayList.remove(j + 1);
					i--;
				} else if (tokenArrayList.get(j + 1).getName().equals("=")) {
					tokenArrayList.get(j).setName("+=");
					tokenArrayList.get(j).setType("Punctuation");
					tokenArrayList.remove(j + 1);
					i--;
				}
			} else if (tokenArrayList.get(j).getName().equals("-")) {
				if (tokenArrayList.get(j + 1).getName().equals("-")) {
					tokenArrayList.get(j).setName("--");
					tokenArrayList.get(j).setType("Punctuation");
					tokenArrayList.remove(j + 1);
					i--;
				} else if (tokenArrayList.get(j + 1).getName().equals(">")) {
					tokenArrayList.get(j).setName("->");
					tokenArrayList.get(j).setType("Punctuation");
					tokenArrayList.remove(j + 1);
					i--;
				} else if (tokenArrayList.get(j + 1).getName().equals("=")) {
					tokenArrayList.get(j).setName("-=");
					tokenArrayList.get(j).setType("Punctuation");
					tokenArrayList.remove(j + 1);
					i--;
				}
			} else if (tokenArrayList.get(j).getName().equals("/")) {
				if (tokenArrayList.get(j + 1).getName().equals("=")) {
					tokenArrayList.get(j).setName("/=");
					tokenArrayList.get(j).setType("Punctuation");
					tokenArrayList.remove(j + 1);
					i--;
				}
			} else if (tokenArrayList.get(j).getName().equals("*")) {// 2020/10/09 problem fixed:python file "*" has
																		// nothing after it.(causing null pointer
																		// exception)
																		// System.out.println("HERE");
				if (tokenArrayList.size() - 1 > j) {
					// System.out.println("HERE2:"+j+" size-1:"+(tokenArrayList.size()-1));
					if (tokenArrayList.get(j + 1).getName().equals("=")) {// System.out.println("HERE3");
						tokenArrayList.get(j).setName("*=");
						tokenArrayList.get(j).setType("Punctuation");
						tokenArrayList.remove(j + 1);
						i--;
					}
				}

			} else if (tokenArrayList.get(j).getName().equals("=")) {
				if (tokenArrayList.get(j + 1).getName().equals("=")) {
					tokenArrayList.get(j).setName("=="); // "*=" to "==" by hongzhan
					tokenArrayList.get(j).setType("Punctuation");
					tokenArrayList.remove(j + 1);
					i--;
				}
			} else if (tokenArrayList.get(j).getName().equals(">")) {
				if (tokenArrayList.get(j + 1).getName().equals("=")) {
					tokenArrayList.get(j).setName(">=");
					tokenArrayList.get(j).setType("Punctuation");
					tokenArrayList.remove(j + 1);
					i--;
				} else if (tokenArrayList.get(j + 1).getName().equals(">")) {
					tokenArrayList.get(j).setName(">>");
					tokenArrayList.get(j).setType("Punctuation");
					tokenArrayList.remove(j + 1);
					i--;
				}
			} else if (tokenArrayList.get(j).getName().equals("<")) {
				if (tokenArrayList.get(j + 1).getName().equals("=")) {
					tokenArrayList.get(j).setName("<=");
					tokenArrayList.get(j).setType("Punctuation");
					tokenArrayList.remove(j + 1);
					i--;
				} else if (tokenArrayList.get(j + 1).getName().equals("<")) {
					tokenArrayList.get(j).setName("<<");
					tokenArrayList.get(j).setType("Punctuation");
					tokenArrayList.remove(j + 1);
					i--;
				}
			} else if (tokenArrayList.get(j).getName().equals("!")) {
				if (tokenArrayList.get(j + 1).getName().equals("=")) {
					tokenArrayList.get(j).setName("!=");
					tokenArrayList.get(j).setType("Punctuation");
					tokenArrayList.remove(j + 1);
					i--;
				}
			} else if (tokenArrayList.get(j).getName().equals("&")) {
				if (tokenArrayList.get(j + 1).getName().equals("&")) {
					tokenArrayList.get(j).setName("&&");
					tokenArrayList.get(j).setType("Punctuation");
					tokenArrayList.remove(j + 1);
					i--;
				}
			} else if (tokenArrayList.get(j).getName().equals("|")) {
				if (tokenArrayList.get(j + 1).getName().equals("|")) {
					tokenArrayList.get(j).setName("||");
					tokenArrayList.get(j).setType("Punctuation");
					tokenArrayList.remove(j + 1);
					i--;
				}
			}

		}

		for (j = 0; j < tokenArrayList.size(); j++) // 將下面設為ReservedWord
		{
			if (isReservedWord(tokenArrayList.get(j).getName()))
				tokenArrayList.get(j).setType("ReservedWord");
			else if (isDataType(tokenArrayList.get(j).getName()))
				tokenArrayList.get(j).setType("DataType");
		}
	}

	/*
	 * 設定token的行數
	 */
	public void setLine(ArrayList<Token> tokenArrayList) {
		int index = 0;
		int line = 0; // 設定line變數
		int parenthesis = 0; // 左小括號的數量
		int endFlag = 0;

		while (index < tokenArrayList.size()) {
			tokenArrayList.get(index).setLine(line);

			/*
			 * for的情況，分號另外處理
			 */
			if (tokenArrayList.get(index).getName().equals("for") || tokenArrayList.get(index).getName().equals("if")
					|| tokenArrayList.get(index).getName().equals("else if")
					|| tokenArrayList.get(index).getName().equals("while")) {
				tokenArrayList.get(index).setLine(line);
				// 避免分號跟換行的分號搞混
				// for,if,else if,while後()內的文字皆算同一行 by hongzhan
				do {
					index++;
					if (tokenArrayList.get(index).getName().equals("("))
						parenthesis++;
					else if (tokenArrayList.get(index).getName().equals(")"))
						parenthesis--;
					tokenArrayList.get(index).setLine(line);
				} while (parenthesis != 0);
				// 偵測像while();的情況，非這種情況則下一個token被視為下一行
				if (tokenArrayList.get(index + 1).getName().equals(";")) {
					tokenArrayList.get(++index).setLine(line);
					endFlag = 1;
				} else
					line++;
				if (tokenArrayList.get(index + 1).getName().equals("{")) {
					index++;
					tokenArrayList.get(index).setLine(line);
				}
				// 用來補足單行沒有大括號的程式
				// 幫單行沒有大括號的程式前後加上大括號，讓整個程式格式統一 by hongzhan
				else if (!tokenArrayList.get(index + 1).getName().equals("{")
						&& !tokenArrayList.get(index + 1).getName().equals(";") && endFlag == 0) {
					index++;
					Token leftBracket = new Token();
					leftBracket.setName("{");
					leftBracket.setType("Punctuation");
					tokenArrayList.add(index, leftBracket);
					tokenArrayList.get(index).setLine(line++);
					// bracket++;
					do {
						index++;
						tokenArrayList.get(index).setLine(line);
					} while (!tokenArrayList.get(index).getName().equals(";"));
					// this.setLine(tokenArrayList, index, bracket);

					index++;
					Token rightBracket = new Token();
					rightBracket.setName("}");
					rightBracket.setType("Punctuation");
					tokenArrayList.add(index, rightBracket);
					tokenArrayList.get(index).setLine(++line);
					// bracket--;
				}
				line++;
				endFlag = 0;
			}
			/*
			 * 因為else和上列結構不同所以需要獨立出來
			 */
			// if(){},else{}結構上差了() by hongzhan
			else if (tokenArrayList.get(index).getName().equals("else")) {
				line++;
				if (tokenArrayList.get(index + 1).getName().equals("{")) {
					index++;
					tokenArrayList.get(index).setLine(line);
				}
				// 用來補足單行沒有大括號的程式
				// 幫單行沒有大括號的程式前後加上大括號，讓整個程式格式統一 by hongzhan
				else if (!tokenArrayList.get(index + 1).getName().equals("{")) {
					index++;
					Token leftBracket = new Token();
					leftBracket.setName("{");
					leftBracket.setType("Punctuation");
					tokenArrayList.add(index, leftBracket);
					tokenArrayList.get(index).setLine(line++);

					do {
						index++;
						tokenArrayList.get(index).setLine(line);
					} while (!tokenArrayList.get(index).getName().equals(";"));

					index++;
					Token rightBracket = new Token();
					rightBracket.setName("}");
					rightBracket.setType("Punctuation");
					tokenArrayList.add(index, rightBracket);
					tokenArrayList.get(index).setLine(++line);
				}
				line++;
			}
			/*
			 * 基本：遇到分號行數加一
			 */
			/*
			 * 遇到}行數也加一，但像{{1,2},{2,3}} {}; do{}while();這三種情況則不換行 do{}while();標準格式為 do {
			 * }while(); by hongzhan
			 */
			else if (tokenArrayList.get(index).getName().equals(";") ||
			// (tokenArrayList.get(index).getName().equals("{") &&
			// tokenArrayList.get(index-1).getName().equals(")"))||
					(index + 1 < tokenArrayList.size() && tokenArrayList.get(index).getName().equals("}")
							&& !tokenArrayList.get(index + 1).getName().equals(","))
							&& !tokenArrayList.get(index + 1).getName().equals("while")
							&& !tokenArrayList.get(index + 1).getName().equals(";"))
				line++;
			else if (tokenArrayList.get(index).getName().equals("{")) {
				line++;
				tokenArrayList.get(index).setLine(line++);
			}
			/*
			 * include和define
			 */
			// 此為getType，所以其token已在setReservedWordType method中整合為像#include<stdio>的樣式 by
			// hongzhan
			else if (tokenArrayList.get(index).getType().equals("include")
					|| tokenArrayList.get(index).getType().equals("define"))
				line++;

			index++;
		}
	}

	/*
	 * 設定token的層數
	 */
	// 遇到{加一層，遇到}減一層 by hongzhan
	public void setLevel(ArrayList<Token> tokenArrayList) {
		int index = 0;
		int level = 0;

		while (index < tokenArrayList.size()) {
			if (tokenArrayList.get(index).getName().equals("{")) {
				tokenArrayList.get(index).setLevel(level);
				level++;
			} else if (tokenArrayList.get(index).getName().equals("}")) {
				level--;
				tokenArrayList.get(index).setLevel(level);
			} else if (tokenArrayList.get(index).getName().equals("if")
					|| tokenArrayList.get(index).getName().equals("else if")
					|| tokenArrayList.get(index).getName().equals("for")) {
				while (tokenArrayList.get(index).getLine() == tokenArrayList.get(index + 1).getLine()) {
					tokenArrayList.get(index).setLevel(level);
					index++;
				}
				tokenArrayList.get(index).setLevel(level);
			} else if (tokenArrayList.get(index).getName().equals("else")) {
				tokenArrayList.get(index).setLevel(level);
			} else
				tokenArrayList.get(index).setLevel(level);

			index++;
		}
	}

	/*
	 * 設定函數和傳遞參數
	 */
	public void setFunction(ArrayList<Token> tokenArrayList) {
		for (int index = 0; index < tokenArrayList.size(); index++) {
			// 設定函數
			if (isDataType(tokenArrayList.get(index).getName()) /* && tokenArrayList.get(index).getLevel() == 0 */) {
				String dataType = tokenArrayList.get(index).getName(); // 暫存型態的String
				int parenthesis = 0; // 括號旗標
				int functionIndex = 0;
				int flag = 0;

				if (tokenArrayList.get(++index).getType().equals("Letter")
						&& tokenArrayList.get(index + 1).getName().equals("(")) // 確定是function
				{
					tokenArrayList.get(index).setType("function");
					tokenArrayList.get(index).setDataType(dataType);
					flag = 1;
					if (tokenArrayList.get(index).getName().equals("main"))
						flag = 0;// 修正main的錯誤 by SuZheHong
				}
				functionIndex = index;

				if (flag == 1) // 假如是function則開始記錄傳遞參數
				{
					// 用迴圈跑傳遞參數
					do {
						index++;
						if (tokenArrayList.get(index).getName().equals("("))
							parenthesis++;
						else if (tokenArrayList.get(index).getName().equals(")"))
							parenthesis--;
						else if (isDataType(tokenArrayList.get(index).getName())) {
							while (!tokenArrayList.get(++index).getType().equals("Letter"))
								;
							tokenArrayList.get(index).setType("parameter");
							tokenArrayList.get(functionIndex).setParameterList(index);
						}
					} while (parenthesis != 0 && index + 1 < tokenArrayList.size());
				}
				flag = 0;
			} else if (tokenArrayList.get(index).getName().equals("class")) {
				tokenArrayList.get(++index).setType("class");
				tokenArrayList.get(++index).setDataType("class");// 這邊++偵測到的是class name後面的括號? by hongzhan
			}
		}
	}

	/*
	 * 設定函數內變數
	 */
	public void setVariable(ArrayList<Token> tokenArrayList) {
		for (int index = 0; index < tokenArrayList.size(); index++) {
			// 全域變數不會被偵測到，因在最上層 by hongzhan
			if (isDataType(tokenArrayList.get(index).getName()) && tokenArrayList.get(index).getLevel() != 0) // 確定它是變數
			{
				String dataType = "";
				int vectorFlag = 0;
				// 特別針對vector(ex.vector<int> ivector(10);)做預先的處理 by hongzhan
				if (tokenArrayList.get(index).getName().equals("vector")) {
					index = index + 2;
					dataType = tokenArrayList.get(index).getName();
					index = index + 1;
					vectorFlag = 1;
				} else
					dataType = tokenArrayList.get(index).getName(); // 暫存型態的String
				int line = tokenArrayList.get(index).getLine();
				while (true) {
					if (tokenArrayList.get(++index).getType().equals("Letter") && vectorFlag == 1) {
						tokenArrayList.get(index).setDataType(dataType);
						tokenArrayList.get(index).setType("variable");
					} else if (tokenArrayList.get(index).getType().equals("Letter")
							&& (tokenArrayList.get(index - 1).getName().equals(",")
									|| tokenArrayList.get(index - 1).getType().equals("DataType"))) {
						tokenArrayList.get(index).setDataType(dataType);
						tokenArrayList.get(index).setType("variable");
					} else if (tokenArrayList.get(index).getName().equals(";"))
						break;
					// 換行則跳出 by hongzhan
					if (line != tokenArrayList.get(index).getLine())
						break;
				}
			}
		}
	}

	/*
	 * 把參數加到function裡
	 */
	public void addVariableInfunction(ArrayList<Token> tokenArrayList) {
		for (int index = 0; index < tokenArrayList.size(); index++) {
			if (tokenArrayList.get(index).getType().equals("function")) // ����function���Ѽ�
			{
				int functionIndex = index;
				index++;
				while (index < tokenArrayList.size() && !tokenArrayList.get(index).getType().equals("function")
						&& !tokenArrayList.get(index).getType().equals("class")) {
					if (tokenArrayList.get(index).getType().equals("variable")) {
						tokenArrayList.get(index).setParentIndex(functionIndex);
						tokenArrayList.get(functionIndex).setVariableList(index++);
					} else
						index++;
				}
			} else if (tokenArrayList.get(index).getType().equals("class")) // ����class����T
			{
				int classIndex = index;
				index += 3;
				// int functionIndex = 0;
				while (tokenArrayList.get(classIndex).getLevel() != tokenArrayList.get(index).getLevel()) {
					if (tokenArrayList.get(index).getType().equals("function")) {
						// System.out.println(tokenArrayList.get(index).getName());
						tokenArrayList.get(index).setVariableList(classIndex);
						tokenArrayList.get(classIndex).setFunctionList(index++);

					} else if (tokenArrayList.get(index).getType().equals("variable")) {
						tokenArrayList.get(classIndex).setVariableList(index++);
					} else
						index++;
				}
			}
		}
	}

	/*
	 * ����ܼƦW�ټҲ�
	 */
	public void changeVariableNameInfunction(ArrayList<Token> tokenArrayList) {
		int index = 0;
		for (int i = 0; i < tokenArrayList.size() - 1; i++) {
			if (tokenArrayList.get(i).getType().equals("variable")
					|| tokenArrayList.get(i).getType().equals("parameter")) {
				for (int j = i + 1; j < tokenArrayList.size(); j++) {
					if (tokenArrayList.get(i).getName().equals(tokenArrayList.get(j).getName())) {
						tokenArrayList.get(j).setName("changeName" + index);
					}
				}
				tokenArrayList.get(i).setName("changeName" + index);
				index++;
			}
		}
	}

	/*
	 * �L�X�{��
	 */
	public void printProgram(ArrayList<Token> tokenArrayList) {
		for (int index = 0; index < tokenArrayList.size(); index++) {
			int lin = tokenArrayList.get(index).getLine();
			for (int i = 0; i < tokenArrayList.get(index).getLevel(); i++)
				System.out.print("\t");
			while (index < tokenArrayList.size() && lin == tokenArrayList.get(index).getLine()) {
				System.out.print(tokenArrayList.get(index).getName() + " ");
				index++;
			}
			index--;
			System.out.println();
		}
		/*
		 * print funtion list
		 */
		/*
		 * for(int index = 0 ; index < tokenArrayList.size() ; index++) {
		 * if(tokenArrayList.get(index).getType().equals("function")) {
		 * if(tokenArrayList.get(index).getParentIndex() == 0) { System.out.println();
		 * System.out.println( "function:" + tokenArrayList.get(index).getName() + " " +
		 * tokenArrayList.get(index).getDataType()); } for(int i = 0 ; i <
		 * tokenArrayList.get(index).getParameterList().size() ; i++)
		 * if(tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).
		 * getType().equals("parameter")) { System.out.println( "parameter:" +
		 * tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).
		 * getName() + " " +
		 * tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).
		 * getDataType()); for(int j = 0; j <
		 * tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).
		 * getstatisticalSimilarity().size(); j++) System.out.println( "parameter:" +
		 * tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).
		 * getstatisticalSimilarity().get(j)); for(int j = 0; j <
		 * tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).
		 * getFormationSimilarity().size(); j++) System.out.println(
		 * "FormationSimilarity:" +
		 * tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).
		 * getFormationSimilarity().get(j)); } for(int i = 0 ; i <
		 * tokenArrayList.get(index).getVariableList().size() ; i++) {
		 * System.out.println( "variable:" +
		 * tokenArrayList.get(tokenArrayList.get(index).getVariableList().get(i)).
		 * getName() + " " +
		 * tokenArrayList.get(tokenArrayList.get(index).getVariableList().get(i)).
		 * getDataType()); for(int j = 0; j <
		 * tokenArrayList.get(tokenArrayList.get(index).getVariableList().get(i)).
		 * getstatisticalSimilarity().size(); j++) System.out.println(
		 * "statisticalSimilarity:" +
		 * tokenArrayList.get(tokenArrayList.get(index).getVariableList().get(i)).
		 * getstatisticalSimilarity().get(j)); for(int j = 0; j <
		 * tokenArrayList.get(tokenArrayList.get(index).getVariableList().get(i)).
		 * getFormationSimilarity().size(); j++) System.out.println(
		 * "FormationSimilarity:" +
		 * tokenArrayList.get(tokenArrayList.get(index).getVariableList().get(i)).
		 * getFormationSimilarity().get(j)); } } else
		 * if(tokenArrayList.get(index).getType().equals("class")) {
		 * System.out.println(); System.out.println( "class:" +
		 * tokenArrayList.get(index).getName()); for(int i = 0 ; i <
		 * tokenArrayList.get(index).getParameterList().size() ; i++) {
		 * if(tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).
		 * getType().equals("parameter")) System.out.println( "parameter:" +
		 * tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).
		 * getName() + " " +
		 * tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).
		 * getDataType()); else System.out.println( "variable:" +
		 * tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).
		 * getName() + " " +
		 * tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).
		 * getDataType()); } for(int i = 0 ; i <
		 * tokenArrayList.get(index).getFunctionList().size() ; i++) {
		 * System.out.println( "function:" +
		 * tokenArrayList.get(tokenArrayList.get(index).getFunctionList().get(i)).
		 * getName() + " " +
		 * tokenArrayList.get(tokenArrayList.get(index).getFunctionList().get(i)).
		 * getDataType()); } } }
		 */
	}

	public boolean ispunct(char ch) {
		Character[] ary = { '(', ')', '{', '}', '[', ']', ',', '.', '"', ';', '=', '>', '<', '+', '-', '*', '/', '#',
				':' };

		for (int i = 0; i < ary.length; i++)
			if (ary[i].charValue() == ch)
				return true;

		return false;
	}

	public boolean isDataType(String str) {
		String[] StrAry = { "int", "long", "double", "short", "float", "byte", "char", "String", "bool", "void",
				"vector", "arg" };

		for (int i = 0; i < StrAry.length; i++)
			if (StrAry[i].equals(str))
				return true;

		return false;
	}

	public boolean isReservedWord(String str) {
		String[] StrAry = { "for", "while", "do", "if", "eles if", "else" };

		for (int i = 0; i < StrAry.length; i++)
			if (StrAry[i].equals(str))
				return true;

		return false;
	}
}
