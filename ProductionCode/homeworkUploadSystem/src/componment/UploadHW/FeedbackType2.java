package componment.UploadHW;

//使用未初始化的值
public class FeedbackType2 implements Feedback{
	private final String feedback = 
			"近期撰寫程式容易使用未初始化的值，</br>" + 
			"在宣告變數或陣列時，請務必初始化，</br>" + 
			"若將未初始化的變數，作為條件判斷式，</br>" + 
			"容易使程式誤判。";
	
	private final String type = "使用未初始化的值";

	public String getFeedback() {
		return feedback;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

}
