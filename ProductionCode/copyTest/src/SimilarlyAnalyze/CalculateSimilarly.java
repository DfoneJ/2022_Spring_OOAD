package SimilarlyAnalyze;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Vector;

import TextAnalyze.Winnow;
import ClassAnalyze.ClassAnalyzer;
import Parser.*;
import StructureAnalyze.*;
import VariableAnalyze.*;

//ricehuang
import VariableAnalyze.Filer;
import VariableAnalyze.CompareSymbol;
// ricehuang
import WebGUI.SettingInformation;

public class CalculateSimilarly {

	private ArrayList<ArrayList<String>> textSimilarityTable = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> structureSimilarityTable = new ArrayList<ArrayList<String>>();
																										
	private ArrayList<ArrayList<String>> variableSimilarityTable = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> ooSimilarityTable = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> totalSimilarityTable = new ArrayList<ArrayList<String>>();// 
	private ArrayList<ArrayList<String>> highSimilarityList = new ArrayList<ArrayList<String>>();// 
	private ArrayList<ArrayList<String>> DCSandOOSimilarityTable = new ArrayList<ArrayList<String>>();// 
																										
	private ArrayList<ArrayList<String>> finalSimilarityTable = new ArrayList<ArrayList<String>>();
	
	public void degreeOfPlagiarismUsingTextAnalyze(int topNum,
			ArrayList<ArrayList<String>> fileList) {
		Winnow winnow = new Winnow();
		NumberFormat nf = NumberFormat.getInstance(); // 設定小數點位數
		nf.setMaximumFractionDigits(2);
		ArrayList<ArrayList<String>> orderList = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < fileList.size() - 1; i++) // 程式比對(使用text analyze)
		{
			for (int j = i + 1; j < fileList.size(); j++) {
				ArrayList<String> object = new ArrayList<String>();
				object.add(Integer.toString(i));
				object.add(Integer.toString(j));
				// 使用更改變數名稱
				// 此處add進入的為文字相似度 by hongzhan
//				System.out.println(fileList.get(i).get(0)+"    "+fileList.get(j).get(0));
				object.add(Double.toString(winnow
						.textAnalyzeByChangeVariableName(
								fileList.get(i).get(1), fileList.get(j).get(1),
								4)));
				this.textSimilarityTable.add(object);
				orderList.add(object);
			}
		}

		for (int i = 0; i < orderList.size() - 1; i++) // 結果排序
		{
			for (int j = i + 1; j < orderList.size(); j++) {
				if (Double.parseDouble(orderList.get(i).get(2)) < Double
						.parseDouble(orderList.get(j).get(2))) {
					ArrayList<String> tmp = new ArrayList<String>();
					tmp.add(orderList.get(i).get(0));
					tmp.add(orderList.get(i).get(1));
					tmp.add(orderList.get(i).get(2));
					orderList.remove(i);
					orderList.add(i, orderList.get(j - 1));//orderList.get(j-1)?? by SuZheHong
					orderList.remove(j);
					orderList.add(j, tmp);
				}
			}
		}
		// ricehuang 2011-04-08
		// for(int i = 0; i < topNum; i++) //印出相似度高的file
		for (int i = 0; i < orderList.size(); i++) //印出相似度高的file
		// ricehuang 2011-04-08
		{
			highSimilarityList.add(orderList.get(i));
		}
	}

	/* 結構分析 */
	public void degreeOfPlagiarismUsingStructureAnalyze(int topNum,
			ArrayList<ArrayList<String>> fileList) {
		AlgebraicExpression algebraicExpression = new AlgebraicExpression();
		Parser parser = new Parser();
		Winnow winnow = new Winnow();
		ArrayList<String> expressionArrayList = new ArrayList<String>();
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(3);

		for (int i = 0; i < fileList.size(); i++) {
			ArrayList<Token> tokenArrayList = new ArrayList<Token>();
			parser.parserCodeToToken(fileList.get(i).get(1), tokenArrayList);
			parser.setReservedWordType(tokenArrayList);

			for (int x = 0; x < 10; x++) {
				parser.setLine(tokenArrayList);
				parser.setLevel(tokenArrayList);
			}

			parser.setFunction(tokenArrayList);
			parser.setVariable(tokenArrayList);
			parser.addVariableInfunction(tokenArrayList);
			algebraicExpression.setAlgebraicExpression(tokenArrayList);
			algebraicExpression.setExpression(expressionArrayList);
		}

		for (int i = 0; i < expressionArrayList.size() - 1; i++) // structure
																	// analyze
		{
			for (int j = i + 1; j < expressionArrayList.size(); j++) {
				ArrayList<String> object = new ArrayList<String>();
				object.add(Integer.toString(i));
				object.add(Integer.toString(j));
				object.add(Double.toString(winnow.textAnalyzeForStructure(
						expressionArrayList.get(i), expressionArrayList.get(j),
						6)));
				this.structureSimilarityTable.add(object);
			}
		}

		/*
		 * for(int i = 0; i < this.structureSimilarityTable.size()-1; i++)
		 * //���G�Ƨ� { for(int j = i+1; j < this.structureSimilarityTable.size();
		 * j++) {
		 * if(Double.parseDouble(this.structureSimilarityTable.get(i).get(2)) <
		 * Double.parseDouble(this.structureSimilarityTable.get(j).get(2))) {
		 * ArrayList<String> tmp = new ArrayList<String>();
		 * tmp.add(Integer.toString
		 * (Integer.parseInt(this.structureSimilarityTable.get(i).get(0))));
		 * tmp.
		 * add(Integer.toString(Integer.parseInt(this.structureSimilarityTable
		 * .get(i).get(1))));
		 * tmp.add(Double.toString(Double.parseDouble(this.structureSimilarityTable
		 * .get(i).get(2)))); structureSimilarityTable.remove(i);
		 * structureSimilarityTable.add(i, structureSimilarityTable.get(j-1));
		 * structureSimilarityTable.remove(j); structureSimilarityTable.add(j,
		 * tmp); } } }
		 * 
		 * System.out.println("\n<<Structure Analyze>>\n");
		 * 
		 * for(int i = 0; i < topNum; i++)  {
		 * System.out.print(fileList
		 * .get(Integer.parseInt(structureSimilarityTable.get(i).get(0))).get(0)
		 * + "      ");
		 * System.out.print(fileList.get(Integer.parseInt(structureSimilarityTable
		 * .get(i).get(1))).get(0) + "      "); System.out.println("�ۦ��סG" +
		 * nf.format
		 * (Double.parseDouble(structureSimilarityTable.get(i).get(2)))); }
		 */
	}

	/* �ܼƤ��R */
	public void degreeOfPlagiarismUsingVariableAnalyze(int topNum,
			ArrayList<ArrayList<String>> fileList) {
		VariableAnalyzer variableAnalyze = new VariableAnalyzer();
		Parser parser = new Parser();
		ArrayList<ArrayList<Token>> fileTokenList = new ArrayList<ArrayList<Token>>();
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(3);

		for (int i = 0; i < fileList.size(); i++) {
			ArrayList<Token> tokenArrayList = new ArrayList<Token>();
			parser.parserCodeToToken(fileList.get(i).get(1), tokenArrayList);
			parser.setReservedWordType(tokenArrayList);

			for (int x = 0; x < 10; x++) {
				parser.setLine(tokenArrayList);
				parser.setLevel(tokenArrayList);
			}

			parser.setFunction(tokenArrayList);
			parser.setVariable(tokenArrayList);
			parser.addVariableInfunction(tokenArrayList);

			variableAnalyze.setStatisticalSimilarity(tokenArrayList);
			variableAnalyze.setFormationSimilarity(tokenArrayList);
			fileTokenList.add(tokenArrayList);
		}

		for (int i = 0; i < fileTokenList.size(); i++) {
			for (int j = i + 1; j < fileTokenList.size(); j++) {
				ArrayList<String> object = new ArrayList<String>();
				object.add(Integer.toString(i));
				object.add(Integer.toString(j));
				object.add(Double.toString((variableAnalyze
						.calculateStatisticalSimilarity(fileTokenList.get(i),
								fileTokenList.get(j)) + variableAnalyze
						.calculateStatisticalSimilarity(fileTokenList.get(j),
								fileTokenList.get(i))) / 2 * 100));
				this.variableSimilarityTable.add(object);
			}
		}

		/*
		 * for(int i = 0; i < this.variableSimilarityTable.size()-1; i++) 
		 * { for(int j = i+1; j < this.variableSimilarityTable.size(); j++) {
		 * if(Double.parseDouble(this.variableSimilarityTable.get(i).get(2)) <
		 * Double.parseDouble(this.variableSimilarityTable.get(j).get(2))) {
		 * ArrayList<String> tmp = new ArrayList<String>();
		 * tmp.add(Integer.toString
		 * (Integer.parseInt(this.variableSimilarityTable.get(i).get(0))));
		 * tmp.add
		 * (Integer.toString(Integer.parseInt(this.variableSimilarityTable
		 * .get(i).get(1))));
		 * tmp.add(Double.toString(Double.parseDouble(this.variableSimilarityTable
		 * .get(i).get(2)))); variableSimilarityTable.remove(i);
		 * variableSimilarityTable.add(i, variableSimilarityTable.get(j-1));
		 * variableSimilarityTable.remove(j); variableSimilarityTable.add(j,
		 * tmp); } } }
		 * 
		 * System.out.println("\n<<Variable Analyze>>\n");
		 * 
		 * for(int i = 0; i < topNum; i++) //�L�X�ۦ��װ���file {
		 * System.out.print(fileList
		 * .get(Integer.parseInt(variableSimilarityTable.get(i).get(0))).get(0)
		 * + "      ");
		 * System.out.print(fileList.get(Integer.parseInt(variableSimilarityTable
		 * .get(i).get(1))).get(0) + "      "); System.out.println("�ۦ��סG" +
		 * nf.format
		 * (Double.parseDouble(variableSimilarityTable.get(i).get(2)))); }
		 */
	}

	// ricehuang
	/* �ܼƤ��R */
	public void degreeOfPlagiarismUsingVariableAnalyze2(int topNum,
			ArrayList<ArrayList<String>> fileList) {
		Filer filer;
		int fileCount;
		double symbolSim;
		Filer filer1, filer2;
		CompareSymbol symBinder;
		Vector compareFileContainer;

		compareFileContainer = new Vector();
		fileCount = fileList.size();

		for (int i = 0; i < fileCount; i++) {
			filer = new Filer(fileList.get(i).get(2));
			filer.excute();
			System.out.println(fileList.get(i).get(0));
			for(int k=0;k<filer.getSymbolTable().size();k++)
			{
				System.out.println(filer.getSymbolTable().get(k));
				
			}
			System.out.println();
			compareFileContainer.add(filer);
		}

		for (int i = 0; i < fileCount; i++) 
		{
			for (int j = i + 1; j < fileCount; j++) 
			{
				ArrayList<String> object = new ArrayList<String>();
				filer1 = (Filer) compareFileContainer.elementAt(i);
				filer2 = (Filer) compareFileContainer.elementAt(j);
				symBinder = new CompareSymbol(filer1.getSymbolTable(),
						filer2.getSymbolTable(),/* this.algorithm */"TRD", /*
																			 * this.
																			 * kgrmSize
																			 */
						4,/* this.interval */1,/* this.windowSize */1);
				symbolSim = ((symBinder.getStatsRatio() + symBinder.getFormsRatio()) / 2) * 100;
				System.out.println(fileList.get(i).get(0));
				System.out.println(fileList.get(j).get(0));
				System.out.println(symBinder.getStatsRatio());
				System.out.println(symBinder.getFormsRatio());
				System.out.println();
				object.add(Integer.toString(i));
				object.add(Integer.toString(j));
				object.add(Double.toString(symbolSim));
				this.variableSimilarityTable.add(object);
			}
		}
	}

	// ricehuang

	public void degreeOfPlagiarismUsingOOAnalyze(int topNum,
			ArrayList<ArrayList<String>> fileList) {
		ClassAnalyzer ca = new ClassAnalyzer(fileList);
		ca.execute();
		ooSimilarityTable = ca.getSimilarity();
		/*
		for (int k = 0; k < ooSimilarityTable.size(); k++) {
			System.out.println("ClassSimilarity:"
					+ ooSimilarityTable.get(k).get(2));
		}
		*/
	}

	/* �T�����R�`�M���� */
	public void degreeOfTotalAnalyzePlagiarism(int topNum,
			ArrayList<ArrayList<String>> fileList) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(3);

		for (int i = 0; i < this.textSimilarityTable.size(); i++) {
			ArrayList<String> object = new ArrayList<String>();
			object.add(this.textSimilarityTable.get(i).get(0));
			object.add(this.textSimilarityTable.get(i).get(1));
			if (Double.parseDouble(this.ooSimilarityTable.get(i).get(2)) < 0) {
				if (Double.parseDouble(this.variableSimilarityTable.get(i).get(
						2)) < 0) {
					object.add(Double.toString(((Double
							.parseDouble(this.textSimilarityTable.get(i).get(2)) + (Double
							.parseDouble(this.structureSimilarityTable.get(i)
									.get(2)))) / 2))); // hongzhan
				} else {
					object.add(Double.toString(((Double
							.parseDouble(this.textSimilarityTable.get(i).get(2)) + (Double
							.parseDouble(this.structureSimilarityTable.get(i)
									.get(2)) + (Double
							.parseDouble(this.variableSimilarityTable.get(i)
									.get(2)))// hongzhan
					)) / 3))); // hongzhan
				}
			} else {
				if (Double.parseDouble(this.variableSimilarityTable.get(i).get(
						2)) < 0) {
					object.add(Double.toString(((Double
							.parseDouble(this.textSimilarityTable.get(i).get(2))
							+ (Double.parseDouble(this.structureSimilarityTable
									.get(i).get(2))) + (Double
							.parseDouble(this.ooSimilarityTable.get(i).get(2)) // hongzhan
					)) / 3))); // hongzhan
				} else {
					object.add(Double.toString(((Double
							.parseDouble(this.textSimilarityTable.get(i).get(2)) + (Double
							.parseDouble(this.structureSimilarityTable.get(i)
									.get(2))
							+ (Double.parseDouble(this.variableSimilarityTable
									.get(i).get(2))) + (Double
							.parseDouble(this.ooSimilarityTable.get(i).get(2)))// hongzhan
					)) / 4))); // hongzhan
				}
			}

			this.totalSimilarityTable.add(object);
		}

		for (int i = 0; i < this.totalSimilarityTable.size() - 1; i++) // ���G�Ƨ�
		{
			for (int j = i + 1; j < this.totalSimilarityTable.size(); j++) {
				if (Double.parseDouble(this.totalSimilarityTable.get(i).get(2)) < Double
						.parseDouble(this.totalSimilarityTable.get(j).get(2))) {
					ArrayList<String> tmp = new ArrayList<String>();
					tmp.add(Integer.toString(Integer
							.parseInt(this.totalSimilarityTable.get(i).get(0))));
					tmp.add(Integer.toString(Integer
							.parseInt(this.totalSimilarityTable.get(i).get(1))));
					tmp.add(Double.toString(Double
							.parseDouble(this.totalSimilarityTable.get(i)
									.get(2))));
					totalSimilarityTable.remove(i);
					totalSimilarityTable
							.add(i, totalSimilarityTable.get(j - 1));
					totalSimilarityTable.remove(j);
					totalSimilarityTable.add(j, tmp);
				}
			}
		}

		detectBaseOnTextAnalyze(highSimilarityList, fileList);
	}

	public void detectBaseOnTextAnalyze(
			ArrayList<ArrayList<String>> highSimilarityList,
			ArrayList<ArrayList<String>> fileList) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(3);

		for (int index = 0; index < highSimilarityList.size(); index++) {
			String str = highSimilarityList.get(index).get(0);
			String str2 = highSimilarityList.get(index).get(1);
			for (int k = 0; k < textSimilarityTable.size(); k++) {
				if (textSimilarityTable.get(k).get(0).equals(str)
						&& textSimilarityTable.get(k).get(1).equals(str2)) {
					System.out.print(fileList.get(Integer.parseInt(str)).get(0)
							+ "      ");
					System.out.print(fileList.get(Integer.parseInt(str2))
							.get(0) + "      ");
					System.out.println("文字相似度:"
							+ nf.format(Double.parseDouble(textSimilarityTable
									.get(k).get(2))));
				}
			}

			for (int k = 0; k < structureSimilarityTable.size(); k++) {
				if (structureSimilarityTable.get(k).get(0).equals(str)
						&& structureSimilarityTable.get(k).get(1).equals(str2)) {
					System.out.print(fileList.get(Integer.parseInt(str)).get(0)
							+ "      ");
					System.out.print(fileList.get(Integer.parseInt(str2))
							.get(0) + "      ");
					System.out.println("結構相似度："
							+ nf.format(Double
									.parseDouble(structureSimilarityTable
											.get(k).get(2))));
				}
			}

			for (int k = 0; k < variableSimilarityTable.size(); k++) {
				if (variableSimilarityTable.get(k).get(0).equals(str)
						&& variableSimilarityTable.get(k).get(1).equals(str2)) {
					System.out.print(fileList.get(Integer.parseInt(str)).get(0)
							+ "      ");
					System.out.print(fileList.get(Integer.parseInt(str2))
							.get(0) + "      ");
					System.out.println("變數相似度："
							+ nf.format(Double
									.parseDouble(variableSimilarityTable.get(k)
											.get(2))));
				}
			}

			for (int k = 0; k < ooSimilarityTable.size(); k++) {
				if (ooSimilarityTable.get(k).get(0).equals(str)
						&& ooSimilarityTable.get(k).get(1).equals(str2)) {
					System.out.print(fileList.get(Integer.parseInt(str)).get(0)
							+ "      ");
					System.out.print(fileList.get(Integer.parseInt(str2))
							.get(0) + "      ");
					System.out.println("物件相似度："
							+ nf.format(Double.parseDouble(ooSimilarityTable
									.get(k).get(2))));
				}
			}

			for (int k = 0; k < totalSimilarityTable.size(); k++) {
				if (totalSimilarityTable.get(k).get(0).equals(str)
						&& totalSimilarityTable.get(k).get(1).equals(str2)) {
					System.out.print(fileList.get(Integer.parseInt(str)).get(0)
							+ "      ");
					System.out.print(fileList.get(Integer.parseInt(str2))
							.get(0) + "      ");
					System.out.println("總合相似度："
							+ nf.format(Double.parseDouble(totalSimilarityTable
									.get(k).get(2))) + "\n");
				}
			}
		}
	}

	public void calculateStructureSimilarly() {
		for (int i = 0; i < structureSimilarityTable.size(); i++) {
			if (Double.parseDouble(structureSimilarityTable.get(i).get(2)) >= Double
					.parseDouble(ooSimilarityTable.get(i).get(2))) {
				DCSandOOSimilarityTable.add(structureSimilarityTable.get(i));
			} else {
				ArrayList<String> tmp = new ArrayList<String>();
				tmp.add(structureSimilarityTable.get(i).get(0));
				tmp.add(structureSimilarityTable.get(i).get(1));
				tmp.add(Double.toString((Double
						.parseDouble(structureSimilarityTable.get(i).get(2)) + Double
						.parseDouble(ooSimilarityTable.get(i).get(2))) / 2));
				DCSandOOSimilarityTable.add(tmp);
			}
		}
	}

	public void DeleteByThresholdsAndcalculateAverageSimilarly(
			SettingInformation settingInformation) {
		double tmpTotal;
		int count;
		ArrayList<ArrayList<String>> baseSimilarityTable;

		if (settingInformation.isStructureAnalyzer()) {
			baseSimilarityTable = DCSandOOSimilarityTable;

		} else if (settingInformation.isTextAnalyzer()) {
			baseSimilarityTable = textSimilarityTable;
		} else {
			baseSimilarityTable = variableSimilarityTable;
		}

		for (int i = 0; i < baseSimilarityTable.size(); i++) {
			count = 0;
			tmpTotal = 0;

			if (settingInformation.isTextAnalyzer()) {
				if (Double.parseDouble(textSimilarityTable.get(i).get(2)) >= settingInformation
						.getThresholdOfText()) {
					tmpTotal += Double.parseDouble(textSimilarityTable.get(i)
							.get(2)) * settingInformation.getWeightOfText();
					count += settingInformation.getWeightOfText();
				} else {
					continue;
				}
			}

			if (settingInformation.isStructureAnalyzer()) {
				if (Double.parseDouble(DCSandOOSimilarityTable.get(i).get(2)) >= settingInformation
						.getThresholdOfStructure()) {
					tmpTotal += Double.parseDouble(DCSandOOSimilarityTable.get(
							i).get(2))
							* settingInformation.getWeightOfStructure();
					count += settingInformation.getWeightOfStructure();
				} else {
					continue;
				}
			}

			if (settingInformation.isVariableAnalyzer()) {
				if (Double.parseDouble(variableSimilarityTable.get(i).get(2)) >= settingInformation
						.getThresholdOfVariable()) {
					tmpTotal += Double.parseDouble(variableSimilarityTable.get(
							i).get(2))
							* settingInformation.getWeightOfVariable();
					count += settingInformation.getWeightOfVariable();
				} else {
					continue;
				}
			}

			tmpTotal /= count;

			if (tmpTotal >= settingInformation.getThresholdOfTotal()) {
				ArrayList<String> tmp = new ArrayList<String>();
				tmp.add(baseSimilarityTable.get(i).get(0));
				tmp.add(baseSimilarityTable.get(i).get(1));
				tmp.add(Double.toString(tmpTotal));
				finalSimilarityTable.add(tmp);
			}
		}
	}

	/* �Ƨ�Total Similarly by hongzhan */
	public ArrayList<ArrayList<String>> sortTotalSimilarly(
			SettingInformation settingInformation) {
		for (int i = 0; i < this.finalSimilarityTable.size() - 1; i++) // ���G�Ƨ�
		{
			for (int j = i + 1; j < this.finalSimilarityTable.size(); j++) {
				if (Double.parseDouble(this.finalSimilarityTable.get(i).get(2)) < Double
						.parseDouble(this.finalSimilarityTable.get(j).get(2))) {
					ArrayList<String> tmp = new ArrayList<String>();
					tmp.add(Integer.toString(Integer
							.parseInt(this.finalSimilarityTable.get(i).get(0))));
					tmp.add(Integer.toString(Integer
							.parseInt(this.finalSimilarityTable.get(i).get(1))));
					tmp.add(Double.toString(Double
							.parseDouble(this.finalSimilarityTable.get(i)
									.get(2))));
					finalSimilarityTable.remove(i);
					finalSimilarityTable
							.add(i, finalSimilarityTable.get(j - 1));
					finalSimilarityTable.remove(j);
					finalSimilarityTable.add(j, tmp);
				}
			}
		}

		return finalSimilarityTable;
	}
}