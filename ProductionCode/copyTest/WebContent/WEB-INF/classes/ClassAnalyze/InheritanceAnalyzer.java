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
		/*���O�H�Ĥ@���ɮפβĤG���ɮ׬��D�@����è�����*/
		similarity = (comparefiles(classArrayList1,classArrayList2)+comparefiles(classArrayList2,classArrayList1))/2;
		
		if(similarity==-0.5)
		{
			similarity=0;
		}
	}
	
	/*�������ɮ׶��Ҧ��~�Ӭ[�c���ۦ���*/
	private double comparefiles(ArrayList<Class> classArray1,ArrayList<Class> classArray2)
	{
		double sim = 0.0; //�̫�ۦ���
		int count1=0; //�p��classArray1�̦��h��class���~�Ӫ��欰
		int count2=0; //�p��classArray1�̦��h��class���~�Ӫ��欰
		int baseclass1=0; //classArray1����class�������O��Index
		int baseclass2=0; //classArray2����class�������O��Index
		EncapsulationAnalyzer calculateClassSimilarity = new EncapsulationAnalyzer();
		
		for(int i=0;i<classArray1.size();i++)
		{
			double singleclasssimilarity=0.0;
			
			/*�p�G��class�S���~�ӡA���L*/
			if(classArray1.get(i).getParentName()==null)
			{
				continue;
			}
			
			count2=0;
			
			for(int j=0;j<classArray2.size();j++)
			{
				double singleclasstmp=0.0;
				
				/*�p�G��class�S���~�ӡA���L*/
				if(classArray2.get(j).getParentName()==null)
				{
					continue;
				}
				
				count2++;
				
				/*�P�_�s���h�Ū��ۦ��סA�v��0.2*/
				if(classArray1.get(i).getInheritanceLevel().equals(classArray2.get(j).getInheritanceLevel()))
				{
					singleclasstmp+=20;
				}

				/*�P�_Derived class���ۦ��סA�v��0.4*/
				singleclasstmp+=calculateClassSimilarity.compareclasses(classArray1.get(i),classArray2.get(j))*0.4;

				/*�M��base class*/
				baseclass1 = findBaseClass(classArray1,classArray1.get(i).getParentName());
				baseclass2 = findBaseClass(classArray2,classArray2.get(j).getParentName());
				
				/*�P�_base class���ۦ��סA�v��0.4*/
				if(baseclass1>=0 && baseclass2>=0)
				{
					singleclasstmp+=calculateClassSimilarity.compareclasses(classArray1.get(baseclass1),classArray2.get(baseclass2))*0.4;					
				}

				/*�P�_������X�Ӫ��ۦ��׬O�_���񤧫e�⪺�j�A�p�G�����ܡA�h������*/
				if(singleclasstmp>singleclasssimilarity)
				{
					singleclasssimilarity = singleclasstmp;
				}
			}
			
			sim+=singleclasssimilarity; //�N�@��function���ۦ��ץ[���`�ۦ��׸�
			count1++;
		}

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
	
	public double getSimilarity()
	{
		return similarity;
	}
}