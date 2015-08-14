package sample.code.corejavainterviewquestion.questions;

import sample.code.corejavainterviewquestion.R;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ExceptionQuestions extends ActionBarActivity implements OnClickListener {
	
	private static final String ANSWER = "answer";
	private static final String INDEX = "index";
	private String[] exceptionQuestions = null;
	private String[] exceptionAnswers = null;
	private int index;
	
	private TextView textviewQuestion, textviewAnswer, textviewXX, textviewYY;
	private Button buttonLeft, buttonShowAnswer, buttonRight;
	
	private String defaultTextviewAnswer = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Set layout xml file
		setContentView(R.layout.questions_template);
		
		// initialize the TextView
		textviewQuestion = (TextView) findViewById(R.id.textViewQuestion);
		textviewAnswer = (TextView) findViewById(R.id.textViewAnswer);
		textviewXX = (TextView) findViewById(R.id.textViewXX);
		textviewYY = (TextView) findViewById(R.id.textViewYY);
		
		// Initialize the Button and set the onClickListener for Button
		buttonLeft = (Button) findViewById(R.id.buttonLeft);
		buttonLeft.setOnClickListener(this);
		buttonRight = (Button) findViewById(R.id.buttonRight);
		buttonRight.setOnClickListener(this);
		buttonShowAnswer = (Button) findViewById(R.id.buttonAnswer);
		buttonShowAnswer.setOnClickListener(this);
		
		// Import and initialize the string-array elements from values folder
		exceptionQuestions = getResources().getStringArray(R.array.exception_questions);
		exceptionAnswers = getResources().getStringArray(R.array.exception_answers);
		
		// Set the first question index
		index = 0;
		
		// Get the default answer in TextView.
		defaultTextviewAnswer = getResources().getString(R.string.answer);
		
		// Set the first question in page.
		textviewQuestion.setText(exceptionQuestions[index]);
		textviewAnswer.setText(defaultTextviewAnswer);
		textviewXX.setText(String.valueOf(index + 1) + "/" );
		textviewYY.setText(String.valueOf(exceptionQuestions.length));
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		// Save the state of current Question and Answer
		outState.putString(ANSWER, textviewAnswer.getText().toString());
		outState.putInt(INDEX, index);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		index = savedInstanceState.getInt(INDEX);
		textviewAnswer.setText(savedInstanceState.getString(ANSWER));
		
		textviewQuestion.setText(exceptionQuestions[index]);
		textviewXX.setText(String.valueOf(index + 1) + "/" );
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonLeft:
			index--;
			if(index< 0) {
				index++;
				break;
			}
			textviewAnswer.setText(defaultTextviewAnswer);
			textviewQuestion.setText(exceptionQuestions[index]);
			textviewXX.setText(String.valueOf(index + 1) + "/" );
			break;
		case R.id.buttonRight:
			index++;
			if((index+1) > exceptionQuestions.length){
				index--;
				break;
			}
			textviewAnswer.setText(defaultTextviewAnswer);
			textviewQuestion.setText(exceptionQuestions[index]);
			textviewXX.setText(String.valueOf(index + 1) + "/" );
			
			break;
		case R.id.buttonAnswer:
			textviewAnswer.setText(exceptionAnswers[index]);
			break;
		default:
			break;
		}
	}
}
