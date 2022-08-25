package ClassAnalyze;

import java.util.ArrayList;

public class PolymorphismAnalyzer 
{
	private double similarity;
	private ArrayList<Class> classArrayList1 = new ArrayList<Class>();
	private ArrayList<Class> classArrayList2 = new ArrayList<Class>();
	
	public PolymorphismAnalyzer(ArrayList<Class> _classArrayList1,ArrayList<Class> _classArrayList2)
	{
		classArrayList1 = _classArrayList1;
		classArrayList2 = _classArrayList2;
	}
	
	public void execute()
	{
		/*分別以第一個檔案及第二個檔案為主作比較並取平均*/
		similarity = (comparefiles(classArrayList1,classArrayList2)+comparefiles(classArrayList2,classArrayList1))/2;
		
		if(similarity==-0.5)
		{
			similarity=0;
		}
	}
	
	private double comparefiles(ArrayList<Class> classArray1,ArrayList<Class> classArray2)
	{
		double sim = 0.0; //最後相似度
		int count1=0; //計算classArray1裡有多少class有多型的行為
		int count2=0; //計算classArray2裡有多少class有多型的行為
		int baseclass1=0; //classArray1中的class的父類別的Index
		int baseclass2=0; //classArray2中的class的父類別的Index
		
		for(int i=0;i<classArray1.size();i++)
		{
			/*如果此class沒有繼承，略過*/
			if(classArray1.get(i).getParentName()==null)
			{
				continue;
			}
			
			count2=0;
			double singleclasssimilarity=0.0;
			ArrayList<Method> polymorphismmethodlist1 = new ArrayList<Method>();
			baseclass1 = findBaseClass(classArray1,classArray1.get(i).getParentName());//尋找base class		
			findVirtualFunctionFromBaseClass(classArray1.get(baseclass1),polymorphismmethodlist1);//從base class中找尋virtual function		
			removeFunctionNotInConcreteClass(classArray1.get(i),polymorphismmethodlist1);//移除concrete class中沒有的function
			
			/*如果此class沒有多型，略過*/
			if(polymorphismmethodlist1.size()==0)
			{
				continue;
			}
				
			for(int j=0;j<classArray2.size();j++)
			{				
				/*如果此class沒有繼承，略過*/
				if(classArray2.get(j).getParentName()==null)
				{
					continue;
				}
				

				double singleclasstmp=0.0;
				ArrayList<Method> polymorphismmethodlist2 = new ArrayList<Method>();
				baseclass2 = findBaseClass(classArray2,classArray2.get(j).getParentName());//尋找base class
				findVirtualFunctionFromBaseClass(classArray2.get(baseclass2),polymorphismmethodlist2);//從base class中找尋virtual function
				removeFunctionNotInConcreteClass(classArray2.get(j),polymorphismmethodlist2);//移除concrete class中沒有的function
				
				/*如果此class沒有多型，略過*/
				if(polymorphismmethodlist2.size()==0)
				{
					continue;
				}
				
				count2++;
				singleclasstmp = compareMethod(polymorphismmethodlist1,polymorphismmethodlist2);//比較兩個function list相似度傳入singleclasstmp					
					
				/*判斷此次算出來的相似度是否有比之前算的大，如果有的話，則替換掉*/
				if(singleclasstmp>singleclasssimilarity)
				{
					singleclasssimilarity = singleclasstmp;
				}
			}
			
			sim+=singleclasssimilarity; //將一個function的相似度加到總相似度裡
			count1++;
		}
		//System.out.println(count1);
		/*取得總相似度的平均*/
		if(count1!=0)
		{
			sim/=count1;			
		}
		else if (count2==0)
		{
			sim = -1;
		}

		return sim;
	}
	
	/*尋找Base Class的index位置*/
	private int findBaseClass(ArrayList<Class> classArray,String classname)
	{
		for(int location=0;location<classArray.size();location++)
		{
			if(classArray.get(location).getName().equals(classname))
			{
				return location;
			}
		}
		
		return -1;
	}
	
	private void findVirtualFunctionFromBaseClass(Class baseclass,ArrayList<Method> polymorphismmethodlist)
	{
		for(int i=0;i<baseclass.getMethodList().size();i++)
		{
			if(baseclass.getMethodList().get(i).getisVirtual())
			{
				polymorphismmethodlist.add(baseclass.getMethodList().get(i));
			}
		}
	}
	
	private void removeFunctionNotInConcreteClass(Class concreteclass,ArrayList<Method> polymorphismmethodlist)
	{
		int i=0;
		
		while(i<polymorphismmethodlist.size())
		{
			boolean deletemethodfromlist = false;
			
			for(int j=0;j<concreteclass.getMethodList().size();j++)
			{
				if(polymorphismmethodlist.get(i).equals(concreteclass.getMethodList().get(j)))
				{
					deletemethodfromlist = true;
					break;
				}
			}

			if(deletemethodfromlist)
			{
				i++;
			}
			else
			{
				polymorphismmethodlist.remove(i);
			}
		}
	}
	
	/*比較兩個class間的Method相似度*/
	private double compareMethod(ArrayList<Method> methodlist1,ArrayList<Method> methodlist2)
	{
		double sim = 0.0; //最後相似度
		
		for(int i=0;i<methodlist1.size();i++)
		{
			double methodsimilarity=0.0;
			
			for(int j=0;j<methodlist2.size();j++)
			{
				double methodtmp=0.0;
				int numerator = 0; //參數相似度的分子
				int	denominator = 0; //參數相似度的分母
				
				/*第二個class的第j個function的參數，用來與第一個class的function參數相減*/
				ArrayList<String> parameterlist = new ArrayList<String>();
				parameterlist = (ArrayList<String>) methodlist2.get(j).getParameterList().clone();
				
				/*判斷存取層級的相似度，權重0.1*/
				if(methodlist1.get(i).getAccessLevel().equals(methodlist2.get(j).getAccessLevel()))
				{
					methodtmp+=10;
				}
				
				/*判斷回傳值的相似度，權重0.4*/
				if(methodlist1.get(i).getReturnType().equals(methodlist2.get(j).getReturnType()))
				{
					methodtmp+=40;
				}
				
				/*判斷參數的相似度，權重0.5 start*/
				/*將第二個class的第j個function的參數與第一個class的function參數相減*/
				for(int k=0;k<methodlist1.get(i).getParameterList().size();k++)
				{
					parameterlist.remove(methodlist1.get(i).getParameterList().get(k));
				}
				
				/*第二個class的function原有的參數數量扣掉與第一個class的function參數相減後的數量，即為兩份參數交集的數量*/
				numerator = methodlist2.get(j).getParameterList().size()-parameterlist.size();
				
				/*第二個class的function原有的參數數量加上第一個class的function參數數量再扣掉與第一個class的function參數相減後的數量，即為兩份參數聯集的數量*/
				denominator = methodlist1.get(i).getParameterList().size()+methodlist2.get(j).getParameterList().size()-numerator;
				
				/*如果兩個function都沒有參數，則參數相似度為50(表完全相同)*/
				if(methodlist1.get(i).getParameterList().size()==0 && methodlist2.get(j).getParameterList().size()==0)
				{
					methodtmp+=50;
				}
				else if(numerator==0||denominator==0); //如果分子、分母有其中一個為0，則參數相似度為0
				else //一般情況
				{
					methodtmp += (double)((numerator/denominator)*50);							
				}
				/*判斷參數的相似度，權重0.5 end*/
				
				/*判斷此次算出來的相似度是否有比之前算的大，如果有的話，則替換掉*/
				if(methodtmp>methodsimilarity)
				{
					methodsimilarity = methodtmp;
				}
			}
			
			sim+=methodsimilarity; //將單一個function的相似度加到總相似度裡
		}
		
		sim/=methodlist1.size(); //取得總相似度的平均
		return sim;		
	}
	
	public double getSimilarity()
	{
		return similarity;
	}
}