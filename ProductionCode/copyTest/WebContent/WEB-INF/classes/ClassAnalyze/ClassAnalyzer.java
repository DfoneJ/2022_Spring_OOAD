package ClassAnalyze;

import java.util.ArrayList;

public class ClassAnalyzer 
{
	private ArrayList<ArrayList<String>> ClassSimilarityTable = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> OverloadSimilarityTable = new ArrayList<ArrayList<String>>();//(i,j,Similarity)
	private ArrayList<ArrayList<String>> InheritanceSimilarityTable = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> EncapsulationSimilarityTable = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> PolymorphismSimilarityTable = new ArrayList<ArrayList<String>>();
	public ArrayList<ArrayList<String>> fileList = new ArrayList<ArrayList<String>>();
	
	public ClassAnalyzer(ArrayList<ArrayList<String>> _fileList)
	{
		fileList = _fileList;
	}
	
	public void execute()
	{
		for(int i = 0; i < fileList.size()-1; i++)
		{
			ArrayList<Class> classArrayList1 = new ArrayList<Class>();
			classArrayList1 = parseCode(fileList.get(i).get(1));

			for(int j = i+1; j < fileList.size(); j++)
			{
				double ClassSimilarity;
				double OverloadSimilarity;
				double InheritanceSimilarity;
				double PolymorphismSimilarity;
				double EncapsulationSimilarity;
				ArrayList<Class> classArrayList2 = new ArrayList<Class>();
				ArrayList<String> OverloadObject = new ArrayList<String>();
				ArrayList<String> ClassObject = new ArrayList<String>();
				ArrayList<String> InheritanceObject = new ArrayList<String>();
				ArrayList<String> PolymorphismObject = new ArrayList<String>();
				ArrayList<String> EncapsulationObject = new ArrayList<String>();
				
				classArrayList2 = parseCode(fileList.get(j).get(1));
				
				OverloadObject.add(String.valueOf(i));
				OverloadObject.add(String.valueOf(j));
				OverloadSimilarity = analyzeOverload(classArrayList1,classArrayList2);
				OverloadObject.add(String.valueOf(OverloadSimilarity));
				OverloadSimilarityTable.add(OverloadObject);

				InheritanceObject.add(String.valueOf(i));
				InheritanceObject.add(String.valueOf(j));
				InheritanceSimilarity = analyzeInheritance(classArrayList1,classArrayList2);
				InheritanceObject.add(String.valueOf(InheritanceSimilarity));
				InheritanceSimilarityTable.add(InheritanceObject);
				
				EncapsulationObject.add(String.valueOf(i));
				EncapsulationObject.add(String.valueOf(j));
				EncapsulationSimilarity = analyzeEncapsulation(classArrayList1,classArrayList2);
				EncapsulationObject.add(String.valueOf(EncapsulationSimilarity));	
				EncapsulationSimilarityTable.add(EncapsulationObject);
							
				PolymorphismObject.add(String.valueOf(i));
				PolymorphismObject.add(String.valueOf(j));
				PolymorphismSimilarity = analyzePolymorphism(classArrayList1,classArrayList2);
				PolymorphismObject.add(String.valueOf(PolymorphismSimilarity));	
				PolymorphismSimilarityTable.add(PolymorphismObject);
				
				ClassObject.add(String.valueOf(i));
				ClassObject.add(String.valueOf(j));
				ClassSimilarity = OverloadSimilarity + EncapsulationSimilarity + InheritanceSimilarity + PolymorphismSimilarity;
				ClassSimilarity += countInvalidNum(OverloadSimilarity , EncapsulationSimilarity , InheritanceSimilarity , PolymorphismSimilarity);
				if(countInvalidNum(OverloadSimilarity , EncapsulationSimilarity , InheritanceSimilarity , PolymorphismSimilarity)==4)
				{
					ClassSimilarity = -1;
				}
				else
				{
					ClassSimilarity /= (4-countInvalidNum(OverloadSimilarity , EncapsulationSimilarity , InheritanceSimilarity , PolymorphismSimilarity));										
				}
				ClassObject.add(String.valueOf(ClassSimilarity));	
				ClassSimilarityTable.add(ClassObject);
				
				/*
				for(int k=0;k<InheritanceSimilarityTable.size();k++)
				{
					System.out.println("Inheritance:"+InheritanceSimilarityTable.get(k).get(2));
				}
				for(int k=0;k<EncapsulationSimilarityTable.size();k++)
				{
					System.out.println("Encapsulation:"+EncapsulationSimilarityTable.get(k).get(2));
				}
				for(int k=0;k<PolymorphismSimilarityTable.size();k++)
				{
					System.out.println("Polymorphism:"+PolymorphismSimilarityTable.get(k).get(2));
				}
				for(int k=0;k<OverloadSimilarityTable.size();k++)
				{
					System.out.println("Overload:"+OverloadSimilarityTable.get(k).get(2));
				}
				for(int k=0;k<ClassSimilarityTable.size();k++)
				{
					System.out.println("ClassSimilarity:"+ClassSimilarityTable.get(k).get(2));
				}
				 */
			}
		}
	}
	
	private ArrayList<Class> parseCode(String str)
	{
		ArrayList<Class> classArrayList = new ArrayList<Class>();
		CodeParser parser = new CodeParser(str);
		parser.execute();
		classArrayList = parser.getClassArrayList();
		return classArrayList;
	}
	
	private double analyzeOverload(ArrayList<Class> classArrayList1,ArrayList<Class> classArrayList2)
	{	
		double sim = checkClassExist(classArrayList1,classArrayList2);
		
		if(sim==-1 ||sim==0)
		{
			return sim;
		}
		else
		{
			OverloadAnalyzer analyzer = new OverloadAnalyzer(classArrayList1,classArrayList2);
			analyzer.execute();
			return analyzer.getSimilarity();			
		}
	}
	
	private double analyzeInheritance(ArrayList<Class> classArrayList1,ArrayList<Class> classArrayList2)
	{
		double sim = checkClassExist(classArrayList1,classArrayList2);
		
		if(sim==-1 ||sim==0)
		{
			return sim;
		}
		else
		{
			InheritanceAnalyzer analyzer = new InheritanceAnalyzer(classArrayList1,classArrayList2);	
			analyzer.execute();
			return analyzer.getSimilarity();
		}
	}
	
	private double analyzeEncapsulation(ArrayList<Class> classArrayList1,ArrayList<Class> classArrayList2)
	{
		double sim = checkClassExist(classArrayList1,classArrayList2);
		
		if(sim==-1 ||sim==0)
		{
			return sim;
		}
		else
		{
			EncapsulationAnalyzer analyzer = new EncapsulationAnalyzer(classArrayList1,classArrayList2);	
			analyzer.execute();
			return analyzer.getSimilarity();
		}
	}
	
	private double analyzePolymorphism(ArrayList<Class> classArrayList1,ArrayList<Class> classArrayList2)
	{
		double sim = checkClassExist(classArrayList1,classArrayList2);
		
		if(sim==-1 ||sim==0)
		{
			return sim;
		}
		else
		{
			PolymorphismAnalyzer analyzer = new PolymorphismAnalyzer(classArrayList1,classArrayList2);
			analyzer.execute();
			return analyzer.getSimilarity();
		}
	}
	
	private double checkClassExist(ArrayList<Class> classArray1,ArrayList<Class> classArray2)
	{		
		if(classArray1.size()==0 || classArray2.size()==0)
		{
			if(classArray1.size()==0 && classArray2.size()==0)
			{
				return -1;
			}
			else
			{
				return 0;
			}
		} 
		return 1;
	}
	
	private int countInvalidNum(double OverloadSimilarity ,double EncapsulationSimilarity ,double InheritanceSimilarity ,double PolymorphismSimilarity)
	{
		int num=0;
		if(OverloadSimilarity==-1)
		{
			num++;
		}
		if(EncapsulationSimilarity==-1)
		{
			num++;
		}
		if(InheritanceSimilarity==-1)
		{
			num++;
		}
		if(PolymorphismSimilarity==-1)
		{
			num++;
		}
		
		return num;
	}
	
	public ArrayList<ArrayList<String>> getSimilarity()
	{
		return ClassSimilarityTable;
	}
}