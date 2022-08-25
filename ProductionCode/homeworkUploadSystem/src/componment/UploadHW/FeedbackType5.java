package componment.UploadHW;

//程式執行值間過長
public class FeedbackType5 implements Feedback{
	private final String feedback = 
			"在撰寫程式時，務必了解題目需求，</br>" + 
			"若在上傳程式時，有部分需求未滿足，</br>" + 
			"容易使程式陷入無窮迴圈，或等待輸入的狀態，</br>" + 
			"造成程式長時間未關閉。";
	
	private final String type = "程式執行值間過長";

	public String getFeedback() {
		return feedback;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}
}
