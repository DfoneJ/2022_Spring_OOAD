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
		similarity = compareClassList(classArrayList1,classArrayList2);//材郎ゑ耕
		similarity += compareClassList(classArrayList2,classArrayList1);//材郎ゑ耕
		similarity /= 2; //程挡狦キА
	}
	
	private double compareClassList(ArrayList<Class> classArray1,ArrayList<Class> classArray2)
	{
		double sim = 0.0; //程
		
		for(int i=0;i<classArray1.size();i++)
		{
			double singleclasssimilarity=0.0;
			
			for(int j=0;j<classArray2.size();j++)
			{
				double singleclasstmp=compareclasses(classArray1.get(i),classArray2.get(j));
				
				/*耞Ω衡ㄓ琌Τゑぇ玡衡狦Τ杠玥蠢传奔*/
				if(singleclasstmp>singleclasssimilarity)
				{
					singleclasssimilarity = singleclasstmp;
				}
			}
			
			sim+=singleclasssimilarity; //盢function羆柑
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
	
	/*ゑ耕ㄢclass丁*/
	public double compareclasses(Class class1,Class class2)
	{
		double sim = 0.0;
		sim=compareAttribute(class1,class2)+compareMethod(class1,class2);
		sim/=2;
		return sim;
	}
	
	/*ゑ耕ㄢclass丁Method*/
	private double compareMethod(Class class1,Class class2)
	{
		double sim = 0.0; //程
		
		for(int i=0;i<class1.getMethodList().size();i++)
		{
			double methodsimilarity=0.0;
			
			for(int j=0;j<class2.getMethodList().size();j++)
			{
				double methodtmp=0.0;
				int numerator = 0; //把计だ
				int	denominator = 0; //把计だダ
				
				/*材class材jfunction把计ノㄓ籔材classfunction把计搭*/
				ArrayList<String> parameterlist = new ArrayList<String>();
				parameterlist = (ArrayList<String>) class2.getMethodList().get(j).getParameterList().clone();
				
				/*耞糷舦0.1*/
				if(class1.getMethodList().get(i).getAccessLevel().equals(class2.getMethodList().get(j).getAccessLevel()))
				{
					methodtmp+=10;
				}
				
				/*耞肚舦0.4*/
				if(class1.getMethodList().get(i).getReturnType().equals(class2.getMethodList().get(j).getReturnType()))
				{
					methodtmp+=40;
				}
				
				/*耞把计舦0.5 start*/
				/*盢材class材jfunction把计籔材classfunction把计搭*/
				for(int k=0;k<class1.getMethodList().get(i).getParameterList().size();k++)
				{
					parameterlist.remove(class1.getMethodList().get(i).getParameterList().get(k));
				}
				
				/*材classfunctionΤ把计计秖Ι奔籔材classfunction把计搭计秖ㄢ把计ユ栋计秖*/
				numerator = class2.getMethodList().get(j).getParameterList().size()-parameterlist.size();
				
				/*材classfunctionΤ把计计秖材classfunction把计计秖Ι奔籔材classfunction把计搭计秖ㄢ把计羛栋计秖*/
				denominator = class1.getMethodList().get(i).getParameterList().size()+class2.getMethodList().get(j).getParameterList().size()-numerator;
				
				/*狦ㄢfunction常⊿Τ把计玥把计50(Ч)*/
				if(class1.getMethodList().get(i).getParameterList().size()==0 && class2.getMethodList().get(j).getParameterList().size()==0)
				{
					methodtmp+=50;
				}
				else if(numerator==0||denominator==0); //狦だだダΤㄤい0玥把计0
				else //薄猵
				{
					methodtmp += (double)((numerator/denominator)*50);							
				}
				/*耞把计舦0.5 end*/
				
				/*耞Ω衡ㄓ琌Τゑぇ玡衡狦Τ杠玥蠢传奔*/
				if(methodtmp>methodsimilarity)
				{
					methodsimilarity = methodtmp;
				}
			}
			
			sim+=methodsimilarity; //盢虫function羆柑
		}
		
		if(class1.getMethodList().size()!=0)
		{
			sim/=class1.getMethodList().size(); //眔羆キА
		}
		else if (class2.getMethodList().size()==0)
		{
			sim = 100;
		}

		return sim;		
	}
	
	/*ゑ耕ㄢclass丁Attribute*/
	private double compareAttribute(Class class1,Class class2)
	{
		double numerator = 0; //だ
		double denominator = 0; //だダ
		double sim = 0.0; //程
		ArrayList<String> Attributelistonlytype = new ArrayList<String>();//度class2い┮ΤAttributetype
		ArrayList<Attribute> Attributelist = new ArrayList<Attribute>();//class2いAttributeList狡セ
		Attributelist =(ArrayList<Attribute>)class2.getAttributeList().clone();
		
		/*盢class2い┮ΤAttributeAttributelistい*/
		for(int k=0;k<class2.getAttributeList().size();k++)
		{
			Attributelistonlytype.add(class2.getAttributeList().get(k).getDatatype());
		}
		
		/*眔class2い┮ΤAttributeい籔class12い┮ΤAttribute畉栋
		 * だ度Τ戈摸の糷+戈摸ㄢ贺
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
		
		/*だダㄢclass羆Attribute计秖*/
		denominator = class1.getAttributeList().size() + class2.getAttributeList().size();
		
		//だAttributelist(糷)籔class2畉+(Attributelist(ぃ糷)籔class2畉-だAttributelist(糷)籔class2畉)*0.7
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