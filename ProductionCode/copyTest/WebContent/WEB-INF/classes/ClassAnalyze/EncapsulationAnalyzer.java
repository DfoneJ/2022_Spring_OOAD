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
		similarity = compareClassList(classArrayList1,classArrayList2);//�H�Ĥ@���ɮ׬��D�@���
		similarity += compareClassList(classArrayList2,classArrayList1);//�H�ĤG���ɮ׬��D�@���
		similarity /= 2; //�̫ᵲ�G������
	}
	
	private double compareClassList(ArrayList<Class> classArray1,ArrayList<Class> classArray2)
	{
		double sim = 0.0; //�̫�ۦ���
		
		for(int i=0;i<classArray1.size();i++)
		{
			double singleclasssimilarity=0.0;
			
			for(int j=0;j<classArray2.size();j++)
			{
				double singleclasstmp=compareclasses(classArray1.get(i),classArray2.get(j));
				
				/*�P�_������X�Ӫ��ۦ��׬O�_���񤧫e�⪺�j�A�p�G�����ܡA�h������*/
				if(singleclasstmp>singleclasssimilarity)
				{
					singleclasssimilarity = singleclasstmp;
				}
			}
			
			sim+=singleclasssimilarity; //�N�@��function���ۦ��ץ[���`�ۦ��׸�
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
	
	/*������class�����ۦ���*/
	public double compareclasses(Class class1,Class class2)
	{
		double sim = 0.0;
		sim=compareAttribute(class1,class2)+compareMethod(class1,class2);
		sim/=2;
		return sim;
	}
	
	/*������class����Method�ۦ���*/
	private double compareMethod(Class class1,Class class2)
	{
		double sim = 0.0; //�̫�ۦ���
		
		for(int i=0;i<class1.getMethodList().size();i++)
		{
			double methodsimilarity=0.0;
			
			for(int j=0;j<class2.getMethodList().size();j++)
			{
				double methodtmp=0.0;
				int numerator = 0; //�ѼƬۦ��ת����l
				int	denominator = 0; //�ѼƬۦ��ת�����
				
				/*�ĤG��class����j��function���ѼơA�ΨӻP�Ĥ@��class��function�ѼƬ۴�*/
				ArrayList<String> parameterlist = new ArrayList<String>();
				parameterlist = (ArrayList<String>) class2.getMethodList().get(j).getParameterList().clone();
				
				/*�P�_�s���h�Ū��ۦ��סA�v��0.1*/
				if(class1.getMethodList().get(i).getAccessLevel().equals(class2.getMethodList().get(j).getAccessLevel()))
				{
					methodtmp+=10;
				}
				
				/*�P�_�^�ǭȪ��ۦ��סA�v��0.4*/
				if(class1.getMethodList().get(i).getReturnType().equals(class2.getMethodList().get(j).getReturnType()))
				{
					methodtmp+=40;
				}
				
				/*�P�_�Ѽƪ��ۦ��סA�v��0.5 start*/
				/*�N�ĤG��class����j��function���ѼƻP�Ĥ@��class��function�ѼƬ۴�*/
				for(int k=0;k<class1.getMethodList().get(i).getParameterList().size();k++)
				{
					parameterlist.remove(class1.getMethodList().get(i).getParameterList().get(k));
				}
				
				/*�ĤG��class��function�즳���ѼƼƶq�����P�Ĥ@��class��function�ѼƬ۴�᪺�ƶq�A�Y������Ѽƥ涰���ƶq*/
				numerator = class2.getMethodList().get(j).getParameterList().size()-parameterlist.size();
				
				/*�ĤG��class��function�즳���ѼƼƶq�[�W�Ĥ@��class��function�ѼƼƶq�A�����P�Ĥ@��class��function�ѼƬ۴�᪺�ƶq�A�Y������Ѽ��p�����ƶq*/
				denominator = class1.getMethodList().get(i).getParameterList().size()+class2.getMethodList().get(j).getParameterList().size()-numerator;
				
				/*�p�G���function���S���ѼơA�h�ѼƬۦ��׬�50(�����ۦP)*/
				if(class1.getMethodList().get(i).getParameterList().size()==0 && class2.getMethodList().get(j).getParameterList().size()==0)
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
		
		if(class1.getMethodList().size()!=0)
		{
			sim/=class1.getMethodList().size(); //���o�`�ۦ��ת�����
		}
		else if (class2.getMethodList().size()==0)
		{
			sim = 100;
		}

		return sim;		
	}
	
	/*������class����Attribute�ۦ���*/
	private double compareAttribute(Class class1,Class class2)
	{
		double numerator = 0; //�ۦ��ת����l
		double denominator = 0; //�ۦ��ת�����
		double sim = 0.0; //�̫�ۦ���
		ArrayList<String> Attributelistonlytype = new ArrayList<String>();//�Ȧsclass2���Ҧ�Attribute��type
		ArrayList<Attribute> Attributelist = new ArrayList<Attribute>();//class2��AttributeList���ƥ�
		Attributelist =(ArrayList<Attribute>)class2.getAttributeList().clone();
		
		/*�Nclass2���Ҧ���Attribute�[�JAttributelist��*/
		for(int k=0;k<class2.getAttributeList().size();k++)
		{
			Attributelistonlytype.add(class2.getAttributeList().get(k).getDatatype());
		}
		
		/*���oclass2���Ҧ�Attribute���A�Pclass12���Ҧ�Attribute���t��
		 * �����Ȧ���������Φs���h��+����������
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
		
		/*�ۦ��פ��������class���`Attribute�ƶq*/
		denominator = class1.getAttributeList().size() + class2.getAttributeList().size();
		
		//�ۦ��פ��l��Attributelist(�t�s���h��)�Pclass2���t��+(Attributelist(���t�s���h��)�Pclass2���t��-���l��Attributelist(�t�s���h��)�Pclass2���t��)*0.7
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