package ClassAnalyze;

import java.util.ArrayList;

public class OverloadAnalyzer 
{
	private double similarity;
	private ArrayList<Class> classArrayList1 = new ArrayList<Class>();
	private ArrayList<Class> classArrayList2 = new ArrayList<Class>();
	private ArrayList<ArrayList<Integer>> OverloadIndex1= new ArrayList<ArrayList<Integer>>();
	private ArrayList<ArrayList<Integer>> OverloadIndex2= new ArrayList<ArrayList<Integer>>();

	public OverloadAnalyzer(ArrayList<Class> _classArrayList1,ArrayList<Class> _classArrayList2)
	{
		classArrayList1 = _classArrayList1;
		classArrayList2 = _classArrayList2;
	}
	
	public void execute()
	{
		findOverload(classArrayList1,OverloadIndex1); //從第一個檔案中找尋所有的Overload集合
		findOverload(classArrayList2,OverloadIndex2); //從第二個檔案中找尋所有的Overload集合
		similarity = compare(classArrayList1,classArrayList2,OverloadIndex1,OverloadIndex2);//以第一個檔案為主作比較
		similarity += compare(classArrayList2,classArrayList1,OverloadIndex2,OverloadIndex1);//以第二個檔案為主作比較
		similarity /= 2; //最後結果取平均
	}
	
	/*找尋所有的Overload集合*/
	private void findOverload(ArrayList<Class> classArrayList,ArrayList<ArrayList<Integer>> OverloadIndex)
	{
		int count=0;//最後用來清除沒有Overload的function的計數器
		
		for(int i=0;i<classArrayList.size();i++)//以function name為key，加入到OverloadIndex中，相同的name會被放在一起
		{
			ArrayList<Integer> classboundary = new ArrayList<Integer>();//class與class之間的間隔
			classboundary.add(-1);//設定-1當作class與class之間的間隔
			
			for(int j=0;j<classArrayList.get(i).getMethodList().size();j++)
			{
				boolean preflag = false; //紀錄前面是否有相同name的function
				
				/*從第一個function開始找，一直找到目前function的前一個function，看是否有相同的function*/
				for(int k = 0;k<j;k++)
				{
					if(classArrayList.get(i).getMethodList().get(j).getName().equals(classArrayList.get(i).getMethodList().get(k).getName()))
					{
						preflag = true;
					}
				}
				
				/*如果前面沒有相同名稱的function，則將此function加至OverloadIndex中，並且尋找後面是否有相同名稱的function，有則加入同一個set中*/
				if(!preflag)
				{
					ArrayList<Integer> overloadset = new ArrayList<Integer>();
					overloadset.add(j);
					
					for(int m = j+1;m<classArrayList.get(i).getMethodList().size();m++)
					{
						if(classArrayList.get(i).getMethodList().get(j).getName().equals(classArrayList.get(i).getMethodList().get(m).getName()))
						{
							overloadset.add(m);
						}
					}
					
					OverloadIndex.add(overloadset);
				}
			}
			
			OverloadIndex.add(classboundary); //一個class搜尋結束，插入換class的標誌
		}
		
		while(count<OverloadIndex.size())//將沒有overload的function清掉
		{
			if(OverloadIndex.get(count).size()<=1 && !(OverloadIndex.get(count).get(0).equals(-1)))
			{
				OverloadIndex.remove(count);
			}
			else
			{
				count++;
			}
		}
		
		if(OverloadIndex.size()>0)
		{
			OverloadIndex.remove(OverloadIndex.size()-1); //清掉最後一個Index，因為其為-1，沒用			
		}
	}
	
	private double compare(ArrayList<Class> classArray1,ArrayList<Class> classArray2,ArrayList<ArrayList<Integer>> Overload1,ArrayList<ArrayList<Integer>> Overload2)
	{
		double sim = 0.0; //最後相似度
		double parametersimilarity = 0.0; //參數相似度
		int numerator = 0; //參數相似度的分子
		int	denominator = 0; //參數相似度的分母
		int firstflag = 0; //計算目前位於classArray1的第幾個class
		int secondflag = 0; //計算目前位於classArray2的第幾個class
		
		if(((Overload1.size()-classArray1.size()+1)<=0) || ((Overload2.size()-classArray2.size()+1)<=0))
		{
			/*如果兩個檔案皆沒有overload，則不予採樣*/
			if(((Overload1.size()-classArray1.size()+1)<=0) && ((Overload2.size()-classArray2.size()+1)<=0))
			{
				return -1; 
			}
			else//如果僅有一個檔案沒有overload，則相似度零
			{
				return 0;
			}
		}
		
		for(int i=0;i<Overload1.size();i++) //第一個檔案的所有class的overload集合
		{
			double singleoverloadsetsimilarity = 0.0; //一個overload集合的相似度

			secondflag = 0;
			
			/*如果Overload1中第一個成員為-1，代表其為class的間隔，故將flag+1並略過下列比較的運算*/
			if(Overload1.get(i).get(0).equals(-1))
			{
				firstflag++;
				continue;
			}
			
			for(int j=0;j<Overload2.size();j++) //第二個檔案的所有class的overload集合
			{
				double singleoverloadsettmp = 0.0; //暫存一個overload集合的相似度
				
				/*如果Overload1中第一個成員為-1，代表其為class的間隔，故將flag+1並略過下列比較的運算*/
				if(Overload2.get(j).get(0).equals(-1))
				{
					secondflag++;
					continue;
				}
				
				for(int m=0;m<Overload1.get(i).size();m++) //第一個檔案的第i個overload集合
				{
					double singlefunctionsimilarity = 0.0; //存放一個function的相似度

					for(int n=0;n<Overload2.get(j).size();n++) //第二個檔案的第j個overload集合
					{
						double singlefunctiontmp = 0.0; //暫存一個function的相似度
						
						/*第二個檔案的第j個overload集合的第n個function的參數，用來與第一個檔案的function參數相減*/
						ArrayList<String> parameterlist = new ArrayList<String>();
						
						/*判斷虛擬函式的相似度，權重0.1*/
						if(classArray1.get(firstflag).getMethodList().get(Overload1.get(i).get(m)).getisVirtual()==classArray2.get(secondflag).getMethodList().get(Overload2.get(j).get(n)).getisVirtual())
						{
							singlefunctiontmp+=10;
						}
						
						/*判斷存取層級的相似度，權重0.1*/
						if(classArray1.get(firstflag).getMethodList().get(Overload1.get(i).get(m)).getAccessLevel().equals(classArray2.get(secondflag).getMethodList().get(Overload2.get(j).get(n)).getAccessLevel()))
						{
							singlefunctiontmp+=10;
						}
						
						/*判斷回傳值的相似度，權重0.3*/
						if(classArray1.get(firstflag).getMethodList().get(Overload1.get(i).get(m)).getReturnType().equals(classArray2.get(secondflag).getMethodList().get(Overload2.get(j).get(n)).getReturnType()))
						{
							singlefunctiontmp+=30;
						}
						
						/*判斷參數的相似度，權重0.5 start*/
						parameterlist = (ArrayList<String>) classArray2.get(secondflag).getMethodList().get(Overload2.get(j).get(n)).getParameterList().clone();
						
						/*將第二個檔案的第j個overload集合的第n個function的參數與第一個檔案的function參數相減*/
						for(int k=0;k<classArray1.get(firstflag).getMethodList().get(Overload1.get(i).get(m)).getParameterList().size();k++)
						{
							parameterlist.remove(classArray1.get(firstflag).getMethodList().get(Overload1.get(i).get(m)).getParameterList().get(k));
						}
						
						/*第二個檔案的function原有的參數數量扣掉與第一個檔案的function參數相減後的數量，即為兩份參數交集的數量*/
						numerator = classArray2.get(secondflag).getMethodList().get(Overload2.get(j).get(n)).getParameterList().size()-parameterlist.size();
						
						/*第二個檔案的function原有的參數數量加上第一個檔案的function參數數量再扣掉與第一個檔案的function參數相減後的數量，即為兩份參數聯集的數量*/						
						denominator = classArray1.get(firstflag).getMethodList().get(Overload1.get(i).get(m)).getParameterList().size()+classArray2.get(secondflag).getMethodList().get(Overload2.get(j).get(n)).getParameterList().size()-numerator;
						
						/*如果兩個function都沒有參數，則參數相似度為50(表完全相同)*/
						if(classArray1.get(firstflag).getMethodList().get(Overload1.get(i).get(m)).getParameterList().size()==0 && classArray2.get(secondflag).getMethodList().get(Overload2.get(j).get(n)).getParameterList().size()==0)
						{
							parametersimilarity=50;
						}
						else if(numerator==0||denominator==0) //如果分子、分母有其中一個為0，則參數相似度為0
						{
							parametersimilarity=0;
						}
						else //一般情況
						{
							parametersimilarity = (double)((numerator/denominator)*50);							
						}
						
						singlefunctiontmp+=parametersimilarity; //將參數相似度加入function相似度的暫存中
						/*判斷參數的相似度，權重0.5 end*/
						
						/*判斷此次算出來的相似度是否有比之前算的大，如果有的話，即替換掉*/
						if(singlefunctiontmp>singlefunctionsimilarity)
						{
							singlefunctionsimilarity = singlefunctiontmp;
						}
					}
					
					singleoverloadsettmp+=singlefunctionsimilarity; //將function的相似度加入到整個overload集合相似度的暫存中
				}
				
				singleoverloadsettmp /= Overload1.get(i).size(); //取得overload集合相似度的平均

				/*判斷此次算出來的相似度是否有比之前算的大，如果有的話，即替換掉*/
				if(singleoverloadsettmp>singleoverloadsetsimilarity)
				{
					singleoverloadsetsimilarity = singleoverloadsettmp;
				}
			}
			
			sim += singleoverloadsetsimilarity;//將整個overload集合的相似度加入到總相似度裡
		}
		
		sim /= (Overload1.size()-firstflag); //取得總相似度的平均
		return sim;
	}
	
	public double getSimilarity()
	{
		return similarity;
	}
}