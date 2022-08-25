package ClassAnalyze;

import java.util.ArrayList;

public class EncapsulationAnalyzer 
{
	private double similarity;
	private ArrayList<Class> classArrayList1 = new ArrayList<Class>();
	private ArrayList<Class> classArrayList2 = new ArrayList<Class>();
	
	public EncapsulationAnalyzer(){}
	
	public EncapsulationAnalyzer(ArrayList<Class> _classArrayList1,ArrayList<Class> _classArrayList2)
	{
		classArrayList1 = _classArrayList1;
		classArrayList2 = _classArrayList2;
	}
	
	public void execute()
	{
		similarity = compareClassList(classArrayList1,classArrayList2);//以第一個檔案為主作比較
		similarity += compareClassList(classArrayList2,classArrayList1);//以第二個檔案為主作比較
		similarity /= 2; //最後結果取平均
	}
	
	private double compareClassList(ArrayList<Class> classArray1,ArrayList<Class> classArray2)
	{
		double sim = 0.0; //最後相似度
		
		for(int i=0;i<classArray1.size();i++)
		{
			double singleclasssimilarity=0.0;
			
			for(int j=0;j<classArray2.size();j++)
			{
				double singleclasstmp=compareclasses(classArray1.get(i),classArray2.get(j));
				
				/*判斷此次算出來的相似度是否有比之前算的大，如果有的話，則替換掉*/
				if(singleclasstmp>singleclasssimilarity)
				{
					singleclasssimilarity = singleclasstmp;
				}
			}
			
			sim+=singleclasssimilarity; //將一個function的相似度加到總相似度裡
		}
		
		if(classArray1.size()!=0)
		{
			sim/=classArray1.size();			
		}
		else if (classArray2.size()==0)
		{
			sim = 100;
		}
		
		return sim;
	}
	
	/*比較兩個class間的相似度*/
	public double compareclasses(Class class1,Class class2)
	{
		double sim = 0.0;
		sim=compareAttribute(class1,class2)+compareMethod(class1,class2);
		sim/=2;
		return sim;
	}
	
	/*比較兩個class間的Method相似度*/
	private double compareMethod(Class class1,Class class2)
	{
		double sim = 0.0; //最後相似度
		
		for(int i=0;i<class1.getMethodList().size();i++)
		{
			double methodsimilarity=0.0;
			
			for(int j=0;j<class2.getMethodList().size();j++)
			{
				double methodtmp=0.0;
				int numerator = 0; //參數相似度的分子
				int	denominator = 0; //參數相似度的分母
				
				/*第二個class的第j個function的參數，用來與第一個class的function參數相減*/
				ArrayList<String> parameterlist = new ArrayList<String>();
				parameterlist = (ArrayList<String>) class2.getMethodList().get(j).getParameterList().clone();
				
				/*判斷存取層級的相似度，權重0.1*/
				if(class1.getMethodList().get(i).getAccessLevel().equals(class2.getMethodList().get(j).getAccessLevel()))
				{
					methodtmp+=10;
				}
				
				/*判斷回傳值的相似度，權重0.4*/
				if(class1.getMethodList().get(i).getReturnType().equals(class2.getMethodList().get(j).getReturnType()))
				{
					methodtmp+=40;
				}
				
				/*判斷參數的相似度，權重0.5 start*/
				/*將第二個class的第j個function的參數與第一個class的function參數相減*/
				for(int k=0;k<class1.getMethodList().get(i).getParameterList().size();k++)
				{
					parameterlist.remove(class1.getMethodList().get(i).getParameterList().get(k));
				}
				
				/*第二個class的function原有的參數數量扣掉與第一個class的function參數相減後的數量，即為兩份參數交集的數量*/
				numerator = class2.getMethodList().get(j).getParameterList().size()-parameterlist.size();
				
				/*第二個class的function原有的參數數量加上第一個class的function參數數量再扣掉與第一個class的function參數相減後的數量，即為兩份參數聯集的數量*/
				denominator = class1.getMethodList().get(i).getParameterList().size()+class2.getMethodList().get(j).getParameterList().size()-numerator;
				
				/*如果兩個function都沒有參數，則參數相似度為50(表完全相同)*/
				if(class1.getMethodList().get(i).getParameterList().size()==0 && class2.getMethodList().get(j).getParameterList().size()==0)
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
		
		if(class1.getMethodList().size()!=0)
		{
			sim/=class1.getMethodList().size(); //取得總相似度的平均
		}
		else if (class2.getMethodList().size()==0)
		{
			sim = 100;
		}

		return sim;		
	}
	
	/*比較兩個class間的Attribute相似度*/
	private double compareAttribute(Class class1,Class class2)
	{
		double numerator = 0; //相似度的分子
		double denominator = 0; //相似度的分母
		double sim = 0.0; //最後相似度
		ArrayList<String> Attributelistonlytype = new ArrayList<String>();//僅存class2中所有Attribute的type
		ArrayList<Attribute> Attributelist = new ArrayList<Attribute>();//class2中AttributeList的複本
		Attributelist =(ArrayList<Attribute>)class2.getAttributeList().clone();
		
		/*將class2中所有的Attribute加入Attributelist中*/
		for(int k=0;k<class2.getAttributeList().size();k++)
		{
			Attributelistonlytype.add(class2.getAttributeList().get(k).getDatatype());
		}
		
		/*取得class2中所有Attribute中，與class12中所有Attribute的差集
		 * 分為僅有資料類型及存取層級+資料類型兩種
		 */
		for(int i=0;i<class1.getAttributeList().size();i++)
		{
			int j=0;
			Attributelistonlytype.remove(class1.getAttributeList().get(i).getDatatype());
			
			while(j<Attributelist.size())
			{
				if(class1.getAttributeList().get(i).getDatatype().equals(Attributelist.get(j).getDatatype()) && class1.getAttributeList().get(i).getAccessLevel().equals(Attributelist.get(j).getAccessLevel()))
				{
					Attributelist.remove(j);
				}
				else
				{
					j++;
				}
			}
		}
		
		/*相似度分母為兩個class的總Attribute數量*/
		denominator = class1.getAttributeList().size() + class2.getAttributeList().size();
		
		//相似度分子為Attributelist(含存取層級)與class2的差值+(Attributelist(不含存取層級)與class2的差值-分子為Attributelist(含存取層級)與class2的差值)*0.7
		numerator = (class2.getAttributeList().size()- Attributelist.size())*2;
		numerator += ((class2.getAttributeList().size()-Attributelistonlytype.size())-(class2.getAttributeList().size()- Attributelist.size()))*0.7*2;

		if(denominator==0)
		{
			sim = 100;	
		}
		else
		{
			sim = numerator/denominator;
			sim*=100;			
		}
		
		return sim;		
	}
	
	public double getSimilarity()
	{
		return similarity;
	}
}