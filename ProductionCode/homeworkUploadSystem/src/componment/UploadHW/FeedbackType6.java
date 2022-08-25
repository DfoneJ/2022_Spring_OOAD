package componment.UploadHW;

//程式碼抄襲
public class FeedbackType6 implements Feedback{
	private final String feedback = 
			"若有任何程式上的問題，</br>" + 
			"可以請求助教協助，</br>" + 
			"請勿抄襲同學的程式。";
	
	private final String type = 
			"程式碼抄襲";

	public String getFeedback() {
		return feedback;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}
}
