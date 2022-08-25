package componment.UploadHW;

//不正確的使用函式庫
public class FeedbackType3 implements Feedback{
	private final String feedback = 
			"請注意函式庫的用法是否有誤，</br>" + 
			"建議搜尋相關文件說明。";
	
	private final String type = "不正確的使用函式庫";

	public String getFeedback() {
		return feedback;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}
}
