package componment.UploadHW;

//超出陣列邊界
public class FeedbackType1 implements Feedback {
	private final String feedback = 
			"近期撰寫程式容易使陣列超出邊界，</br>" + 
			"例: array[10]最大索引值為9，</br>" + 
			"切勿在迴圈中使條件判斷式為index <= 10或index < 11，</br>" + 
			"若犯了此錯誤，容易讀取未知的值，</br>" + 
			"使程式易出現無法預期的結果。";
	
	private final String type = "超出陣列的邊界";

	public String getFeedback() {
		return feedback;
	}
	public String getType() {
		return type;
	}
	
}
