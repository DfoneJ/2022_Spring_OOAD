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
		/*���O�H�Ĥ@���ɮפβĤG���ɮ׬��D�@����è�����*/
		similarity = (comparefiles(classArrayList1,classArrayList2)+comparefiles(classArrayList2,classArrayList1))/2;
		
		if(similarity==-0.5)
		{
			similarity=0;
		}
	}
	
	private double comparefiles(ArrayList<Class> classArray1,ArrayList<Class> classArray2)
	{
		double sim = 0.0; //�̫�ۦ���
		int count1=0; //�p��classArray1�̦��h��class���h�����欰
		int count2=0; //�p��classArray2�̦��h��class���h�����欰
		int baseclass1=0; //classArray1����class�������O��Index
		int baseclass2=0; //classArray2����class�������O��Index
		
		for(int i=0;i<classArray1.size();i++)
		{
			/*�p�G��class�S���~�ӡA���L*/
			if(classArray1.get(i).getParentName()==null)
			{
				continue;
			}
			
			count2=0;
			double singleclasssimilarity=0.0;
			ArrayList<Method> polymorphismmethodlist1 = new ArrayList<Method>();
			baseclass1 = findBaseClass(classArray1,classArray1.get(i).getParentName());//�M��base class		
			findVirtualFunctionFromBaseClass(classArray1.get(baseclass1),polymorphismmethodlist1);//�qbase class����Mvirtual function		
			removeFunctionNotInConcreteClass(classArray1.get(i),polymorphismmethodlist1);//����concrete class���S����function
			
			/*�p�G��class�S���h���A���L*/
			if(polymorphismmethodlist1.size()==0)
			{
				continue;
			}
				
			for(int j=0;j<classArray2.size();j++)
			{				
				/*�p�G��class�S���~�ӡA���L*/
				if(classArray2.get(j).getParentName()==null)
				{
					continue;
				}
				

				double singleclasstmp=0.0;
				ArrayList<Method> polymorphismmethodlist2 = new ArrayList<Method>();
				baseclass2 = findBaseClass(classArray2,classArray2.get(j).getParentName());//�M��base class
				findVirtualFunctionFromBaseClass(classArray2.get(baseclass2),polymorphismmethodlist2);//�qbase class����Mvirtual function
				removeFunctionNotInConcreteClass(classArray2.get(j),polymorphismmethodlist2);//����concrete class���S����function
				
				/*�p�G��class�S���h���A���L*/
				if(polymorphismmethodlist2.size()==0)
				{
					continue;
				}
				
				count2++;
				singleclasstmp = compareMethod(polymorphismmethodlist1,polymorphismmethodlist2);//������function list�ۦ��׶ǤJsingleclasstmp					
					
				/*�P�_������X�Ӫ��ۦ��׬O�_���񤧫e�⪺�j�A�p�G�����ܡA�h������*/
				if(singleclasstmp>singleclasssimilarity)
				{
					singleclasssimilarity = singleclasstmp;
				}
			}
			
			sim+=singleclasssimilarity; //�N�@��function���ۦ��ץ[���`�ۦ��׸�
			count1++;
		}
		//System.out.println(count1);
		/*���o�`�ۦ��ת�����*/
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
	
	/*�M��Base Class��index��m*/
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
	
	/*������class����Method�ۦ���*/
	private double compareMethod(ArrayList<Method> methodlist1,ArrayList<Method> methodlist2)
	{
		double sim = 0.0; //�̫�ۦ���
		
		for(int i=0;i<methodlist1.size();i++)
		{
			double methodsimilarity=0.0;
			
			for(int j=0;j<methodlist2.size();j++)
			{
				double methodtmp=0.0;
				int numerator = 0; //�ѼƬۦ��ת����l
				int	denominator = 0; //�ѼƬۦ��ת�����
				
				/*�ĤG��class����j��function���ѼơA�ΨӻP�Ĥ@��class��function�ѼƬ۴�*/
				ArrayList<String> parameterlist = new ArrayList<String>();
				parameterlist = (ArrayList<String>) methodlist2.get(j).getParameterList().clone();
				
				/*�P�_�s���h�Ū��ۦ��סA�v��0.1*/
				if(methodlist1.get(i).getAccessLevel().equals(methodlist2.get(j).getAccessLevel()))
				{
					methodtmp+=10;
				}
				
				/*�P�_�^�ǭȪ��ۦ��סA�v��0.4*/
				if(methodlist1.get(i).getReturnType().equals(methodlist2.get(j).getReturnType()))
				{
					methodtmp+=40;
				}
				
				/*�P�_�Ѽƪ��ۦ��סA�v��0.5 start*/
				/*�N�ĤG��class����j��function���ѼƻP�Ĥ@��class��function�ѼƬ۴�*/
				for(int k=0;k<methodlist1.get(i).getParameterList().size();k++)
				{
					parameterlist.remove(methodlist1.get(i).getParameterList().get(k));
				}
				
				/*�ĤG��class��function�즳���ѼƼƶq�����P�Ĥ@��class��function�ѼƬ۴�᪺�ƶq�A�Y������Ѽƥ涰���ƶq*/
				numerator = methodlist2.get(j).getParameterList().size()-parameterlist.size();
				
				/*�ĤG��class��function�즳���ѼƼƶq�[�W�Ĥ@��class��function�ѼƼƶq�A�����P�Ĥ@��class��function�ѼƬ۴�᪺�ƶq�A�Y������Ѽ��p�����ƶq*/
				denominator = methodlist1.get(i).getParameterList().size()+methodlist2.get(j).getParameterList().size()-numerator;
				
				/*�p�G���function���S���ѼơA�h�ѼƬۦ��׬�50(�����ۦP)*/
				if(methodlist1.get(i).getParameterList().size()==0 && methodlist2.get(j).getParameterList().size()==0)
				{
					methodtmp+=50;
				}
				else if(numerator==0||denominator==0); //�p�G���l�B�������䤤�@�Ӭ�0�A�h�ѼƬۦ��׬�0
				else //�@�뱡�p
				{
					methodtmp += (double)((numerator/denominator)*50);							
				}
				/*�P�_�Ѽƪ��ۦ��סA�v��0.5 end*/
				
				/*�P�_������X�Ӫ��ۦ��׬O�_���񤧫e�⪺�j�A�p�G�����ܡA�h������*/
				if(methodtmp>methodsimilarity)
				{
					methodsimilarity = methodtmp;
				}
			}
			
			sim+=methodsimilarity; //�N��@��function���ۦ��ץ[���`�ۦ��׸�
		}
		
		sim/=methodlist1.size(); //���o�`�ۦ��ת�����
		return sim;		
	}
	
	public double getSimilarity()
	{
		return similarity;
	}
}