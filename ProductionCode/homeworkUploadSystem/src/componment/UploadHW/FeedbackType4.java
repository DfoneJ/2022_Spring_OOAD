package componment.UploadHW;

//Segmentation Fault
public class FeedbackType4 implements Feedback{
	private final String feedback = 
			"近期對於指標的使用上有誤，</br>" + 
			"在指標指向記憶體空間時，</br>" + 
			"請務必確認是否指向未配置的空間，</br>" + 
			"若對未配置的空間進行存取，</br>" + 
			"容易使程式強制關閉。";
	
	private final String type = "Segmentation Fault";

	public String getFeedback() {
		return feedback;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}
}
