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
		findOverload(classArrayList1,OverloadIndex1); //�q�Ĥ@���ɮפ���M�Ҧ���Overload���X
		findOverload(classArrayList2,OverloadIndex2); //�q�ĤG���ɮפ���M�Ҧ���Overload���X
		similarity = compare(classArrayList1,classArrayList2,OverloadIndex1,OverloadIndex2);//�H�Ĥ@���ɮ׬��D�@���
		similarity += compare(classArrayList2,classArrayList1,OverloadIndex2,OverloadIndex1);//�H�ĤG���ɮ׬��D�@���
		similarity /= 2; //�̫ᵲ�G������
	}
	
	/*��M�Ҧ���Overload���X*/
	private void findOverload(ArrayList<Class> classArrayList,ArrayList<ArrayList<Integer>> OverloadIndex)
	{
		int count=0;//�̫�ΨӲM���S��Overload��function���p�ƾ�
		
		for(int i=0;i<classArrayList.size();i++)//�Hfunction name��key�A�[�J��OverloadIndex���A�ۦP��name�|�Q��b�@�_
		{
			ArrayList<Integer> classboundary = new ArrayList<Integer>();//class�Pclass���������j
			classboundary.add(-1);//�]�w-1��@class�Pclass���������j
			
			for(int j=0;j<classArrayList.get(i).getMethodList().size();j++)
			{
				boolean preflag = false; //�����e���O�_���ۦPname��function
				
				/*�q�Ĥ@��function�}�l��A�@�����ثefunction���e�@��function�A�ݬO�_���ۦP��function*/
				for(int k = 0;k<j;k++)
				{
					if(classArrayList.get(i).getMethodList().get(j).getName().equals(classArrayList.get(i).getMethodList().get(k).getName()))
					{
						preflag = true;
					}
				}
				
				/*�p�G�e���S���ۦP�W�٪�function�A�h�N��function�[��OverloadIndex���A�åB�M��᭱�O�_���ۦP�W�٪�function�A���h�[�J�P�@��set��*/
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
			
			OverloadIndex.add(classboundary); //�@��class�j�M�����A���J��class���лx
		}
		
		while(count<OverloadIndex.size())//�N�S��overload��function�M��
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
			OverloadIndex.remove(OverloadIndex.size()-1); //�M���̫�@��Index�A�]���䬰-1�A�S��			
		}
	}
	
	private double compare(ArrayList<Class> classArray1,ArrayList<Class> classArray2,ArrayList<ArrayList<Integer>> Overload1,ArrayList<ArrayList<Integer>> Overload2)
	{
		double sim = 0.0; //�̫�ۦ���
		double parametersimilarity = 0.0; //�ѼƬۦ���
		int numerator = 0; //�ѼƬۦ��ת����l
		int	denominator = 0; //�ѼƬۦ��ת�����
		int firstflag = 0; //�p��ثe���classArray1���ĴX��class
		int secondflag = 0; //�p��ثe���classArray2���ĴX��class
		
		if(((Overload1.size()-classArray1.size()+1)<=0) || ((Overload2.size()-classArray2.size()+1)<=0))
		{
			/*�p�G����ɮ׬ҨS��overload�A�h�����ļ�*/
			if(((Overload1.size()-classArray1.size()+1)<=0) && ((Overload2.size()-classArray2.size()+1)<=0))
			{
				return -1; 
			}
			else//�p�G�Ȧ��@���ɮרS��overload�A�h�ۦ��׹s
			{
				return 0;
			}
		}
		
		for(int i=0;i<Overload1.size();i++) //�Ĥ@���ɮת��Ҧ�class��overload���X
		{
			double singleoverloadsetsimilarity = 0.0; //�@��overload���X���ۦ���

			secondflag = 0;
			
			/*�p�GOverload1���Ĥ@�Ӧ�����-1�A�N��䬰class�����j�A�G�Nflag+1�ò��L�U�C������B��*/
			if(Overload1.get(i).get(0).equals(-1))
			{
				firstflag++;
				continue;
			}
			
			for(int j=0;j<Overload2.size();j++) //�ĤG���ɮת��Ҧ�class��overload���X
			{
				double singleoverloadsettmp = 0.0; //�Ȧs�@��overload���X���ۦ���
				
				/*�p�GOverload1���Ĥ@�Ӧ�����-1�A�N��䬰class�����j�A�G�Nflag+1�ò��L�U�C������B��*/
				if(Overload2.get(j).get(0).equals(-1))
				{
					secondflag++;
					continue;
				}
				
				for(int m=0;m<Overload1.get(i).size();m++) //�Ĥ@���ɮת���i��overload���X
				{
					double singlefunctionsimilarity = 0.0; //�s��@��function���ۦ���

					for(int n=0;n<Overload2.get(j).size();n++) //�ĤG���ɮת���j��overload���X
					{
						double singlefunctiontmp = 0.0; //�Ȧs�@��function���ۦ���
						
						/*�ĤG���ɮת���j��overload���X����n��function���ѼơA�ΨӻP�Ĥ@���ɮת�function�ѼƬ۴�*/
						ArrayList<String> parameterlist = new ArrayList<String>();
						
						/*�P�_�����禡���ۦ��סA�v��0.1*/
						if(classArray1.get(firstflag).getMethodList().get(Overload1.get(i).get(m)).getisVirtual()==classArray2.get(secondflag).getMethodList().get(Overload2.get(j).get(n)).getisVirtual())
						{
							singlefunctiontmp+=10;
						}
						
						/*�P�_�s���h�Ū��ۦ��סA�v��0.1*/
						if(classArray1.get(firstflag).getMethodList().get(Overload1.get(i).get(m)).getAccessLevel().equals(classArray2.get(secondflag).getMethodList().get(Overload2.get(j).get(n)).getAccessLevel()))
						{
							singlefunctiontmp+=10;
						}
						
						/*�P�_�^�ǭȪ��ۦ��סA�v��0.3*/
						if(classArray1.get(firstflag).getMethodList().get(Overload1.get(i).get(m)).getReturnType().equals(classArray2.get(secondflag).getMethodList().get(Overload2.get(j).get(n)).getReturnType()))
						{
							singlefunctiontmp+=30;
						}
						
						/*�P�_�Ѽƪ��ۦ��סA�v��0.5 start*/
						parameterlist = (ArrayList<String>) classArray2.get(secondflag).getMethodList().get(Overload2.get(j).get(n)).getParameterList().clone();
						
						/*�N�ĤG���ɮת���j��overload���X����n��function���ѼƻP�Ĥ@���ɮת�function�ѼƬ۴�*/
						for(int k=0;k<classArray1.get(firstflag).getMethodList().get(Overload1.get(i).get(m)).getParameterList().size();k++)
						{
							parameterlist.remove(classArray1.get(firstflag).getMethodList().get(Overload1.get(i).get(m)).getParameterList().get(k));
						}
						
						/*�ĤG���ɮת�function�즳���ѼƼƶq�����P�Ĥ@���ɮת�function�ѼƬ۴�᪺�ƶq�A�Y������Ѽƥ涰���ƶq*/
						numerator = classArray2.get(secondflag).getMethodList().get(Overload2.get(j).get(n)).getParameterList().size()-parameterlist.size();
						
						/*�ĤG���ɮת�function�즳���ѼƼƶq�[�W�Ĥ@���ɮת�function�ѼƼƶq�A�����P�Ĥ@���ɮת�function�ѼƬ۴�᪺�ƶq�A�Y������Ѽ��p�����ƶq*/						
						denominator = classArray1.get(firstflag).getMethodList().get(Overload1.get(i).get(m)).getParameterList().size()+classArray2.get(secondflag).getMethodList().get(Overload2.get(j).get(n)).getParameterList().size()-numerator;
						
						/*�p�G���function���S���ѼơA�h�ѼƬۦ��׬�50(�����ۦP)*/
						if(classArray1.get(firstflag).getMethodList().get(Overload1.get(i).get(m)).getParameterList().size()==0 && classArray2.get(secondflag).getMethodList().get(Overload2.get(j).get(n)).getParameterList().size()==0)
						{
							parametersimilarity=50;
						}
						else if(numerator==0||denominator==0) //�p�G���l�B�������䤤�@�Ӭ�0�A�h�ѼƬۦ��׬�0
						{
							parametersimilarity=0;
						}
						else //�@�뱡�p
						{
							parametersimilarity = (double)((numerator/denominator)*50);							
						}
						
						singlefunctiontmp+=parametersimilarity; //�N�ѼƬۦ��ץ[�Jfunction�ۦ��ת��Ȧs��
						/*�P�_�Ѽƪ��ۦ��סA�v��0.5 end*/
						
						/*�P�_������X�Ӫ��ۦ��׬O�_���񤧫e�⪺�j�A�p�G�����ܡA�Y������*/
						if(singlefunctiontmp>singlefunctionsimilarity)
						{
							singlefunctionsimilarity = singlefunctiontmp;
						}
					}
					
					singleoverloadsettmp+=singlefunctionsimilarity; //�Nfunction���ۦ��ץ[�J����overload���X�ۦ��ת��Ȧs��
				}
				
				singleoverloadsettmp /= Overload1.get(i).size(); //���ooverload���X�ۦ��ת�����

				/*�P�_������X�Ӫ��ۦ��׬O�_���񤧫e�⪺�j�A�p�G�����ܡA�Y������*/
				if(singleoverloadsettmp>singleoverloadsetsimilarity)
				{
					singleoverloadsetsimilarity = singleoverloadsettmp;
				}
			}
			
			sim += singleoverloadsetsimilarity;//�N���overload���X���ۦ��ץ[�J���`�ۦ��׸�
		}
		
		sim /= (Overload1.size()-firstflag); //���o�`�ۦ��ת�����
		return sim;
	}
	
	public double getSimilarity()
	{
		return similarity;
	}
}