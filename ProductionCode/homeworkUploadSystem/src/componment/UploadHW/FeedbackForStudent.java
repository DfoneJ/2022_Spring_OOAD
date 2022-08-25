package componment.UploadHW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import bean.StudentMistackDao;

public class FeedbackForStudent {
	StudentMistackDao stmd;
	public FeedbackForStudent(StudentMistackDao stmd) {
		this.stmd = stmd;
	}
	
	public FeedbackBox getFeedback() {
		HashMap<Integer, Integer> type_number = new HashMap<Integer, Integer>();
		type_number.put(1, stmd.getType1());
		type_number.put(2, stmd.getType2());
		type_number.put(3, stmd.getType3());
		type_number.put(4, stmd.getType4());
		type_number.put(5, stmd.getType5());
		type_number.put(6, stmd.getType6());
		
		List<Entry<Integer, Integer>> list = new ArrayList<>(type_number.entrySet());
	    list.sort(Entry.comparingByValue());
	    FeedbackBox feedbackBox = new FeedbackBox();
	     
		for(int i = 5; i >= 4; i--) {
			Feedback feedback = getFeedbackInstance(list.get(i).getKey());
			//top
			if(i == 5) {
				feedbackBox.setFeedbackIn1st(feedback.getFeedback());
				feedbackBox.setNumberOfMistacksIn1st(list.get(i).getValue());
				feedbackBox.setTypeIn1st(feedback.getType());
			}else if(i == 4) {
				feedbackBox.setFeedbackIn2nd(feedback.getFeedback());
				feedbackBox.setNumberOfMistacksIn2nd(list.get(i).getValue());
				feedbackBox.setTypeIn2nd(feedback.getType());
			}
		}
		feedbackBox.setTotal(stmd.getTotal());
		return feedbackBox;
		
	}
	
	private Feedback getFeedbackInstance(int number) {
		
		if(number == 1) {
			return new FeedbackType1();
		}else if(number == 2) {
			return new FeedbackType2();
		}else if(number == 3) {
			return new FeedbackType3();
		}else if(number == 4) {
			return new FeedbackType4();
		}else if(number == 5) {
			return new FeedbackType5();
		}else if(number == 6) {
			return new FeedbackType6();
		}
		return null;
	}
	
	
}
