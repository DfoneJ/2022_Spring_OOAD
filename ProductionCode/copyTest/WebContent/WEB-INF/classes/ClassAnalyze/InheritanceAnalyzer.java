package ClassAnalyze;

import java.util.ArrayList;

public class InheritanceAnalyzer 
{
	private double similarity;
	private ArrayList<Class> classArrayList1 = new ArrayList<Class>();
	private ArrayList<Class> classArrayList2 = new ArrayList<Class>();
	
	public InheritanceAnalyzer(ArrayList<Class> _classArrayList1,ArrayList<Class> _classArrayList2)
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
	
	/*比較兩個檔案間所有繼承架構的相似度*/
	private double comparefiles(ArrayList<Class> classArray1,ArrayList<Class> classArray2)
	{
		double sim = 0.0; //最後相似度
		int count1=0; //計算classArray1裡有多少class有繼承的行為
		int count2=0; //計算classArray1裡有多少class有繼承的行為
		int baseclass1=0; //classArray1中的class的父類別的Index
		int baseclass2=0; //classArray2中的class的父類別的Index
		EncapsulationAnalyzer calculateClassSimilarity = new EncapsulationAnalyzer();
		
		for(int i=0;i<classArray1.size();i++)
		{
			double singleclasssimilarity=0.0;
			
			/*如果此class沒有繼承，略過*/
			if(classArray1.get(i).getParentName()==null)
			{
				continue;
			}
			
			count2=0;
			
			for(int j=0;j<classArray2.size();j++)
			{
				double singleclasstmp=0.0;
				
				/*如果此class沒有繼承，略過*/
				if(classArray2.get(j).getParentName()==null)
				{
					continue;
				}
				
				count2++;
				
				/*判斷存取層級的相似度，權重0.2*/
				if(classArray1.get(i).getInheritanceLevel().equals(classArray2.get(j).getInheritanceLevel()))
				{
					singleclasstmp+=20;
				}

				/*判斷Derived class的相似度，權重0.4*/
				singleclasstmp+=calculateClassSimilarity.compareclasses(classArray1.get(i),classArray2.get(j))*0.4;

				/*尋找base class*/
				baseclass1 = findBaseClass(classArray1,classArray1.get(i).getParentName());
				baseclass2 = findBaseClass(classArray2,classArray2.get(j).getParentName());
				
				/*判斷base class的相似度，權重0.4*/
				if(baseclass1>=0 && baseclass2>=0)
				{
					singleclasstmp+=calculateClassSimilarity.compareclasses(classArray1.get(baseclass1),classArray2.get(baseclass2))*0.4;					
				}

				/*判斷此次算出來的相似度是否有比之前算的大，如果有的話，則替換掉*/
				if(singleclasstmp>singleclasssimilarity)
				{
					singleclasssimilarity = singleclasstmp;
				}
			}
			
			sim+=singleclasssimilarity; //將一個function的相似度加到總相似度裡
			count1++;
		}

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
	
	public double getSimilarity()
	{
		return similarity;
	}
}