import java.util.*;
import java.io.*;

public class Question
{
	private String question;
	private int num_answers;
	private String [] answers;
	private int correct_answer;
	private int num_asked;
	private int num_right;
	
	//constructor for initialization of instance vartiables
	public Question(String q, int num_ans, String [] a, int correct_a, int num_a, int num_r)
	{
		question = q;
		num_answers = num_ans;
		
		answers = new String [a.length];
		
		for (int i = 0; i < a.length; i++)
		{
			answers[i] = a[i];
		}
		
		correct_answer = correct_a;
		num_asked = num_a;
		num_right = num_r;
	}
	
	//to override the constructor
	public Question()
	{
	}
	
	//accessor method to return the question itself
	public String getQuestion()
	{
		return question;
	}
	
	//accessor method to return all of the answers in a nice string
	public String getAnswers()
	{
		StringBuilder B = new StringBuilder();
		
		for (int i = 0; i < answers.length; i++)
		{
			B.append((i + 1) + ". "); //appends the number of the answer (index + 1)
			B.append(answers[i]); //appends the actual answer
			
			if (i != answers.length - 1) //if not on the last answer, add next line
			{
				B.append("\n");
			}
		}
		
		return B.toString();
	}
	
	//accessor to get value of correct answer for question
	public int getCorrectAnswer()
	{
		return correct_answer;
	}
	
	//accessor to get number of times the question has been asked
	public int getNumTimesAsked()
	{
		return num_asked;
	}
	
	//accessor to get number of times the question was answered correctly
	public int getNumTimesRight()
	{
		return num_right;
	}
	
	//method to add to the number of times correct and asked
	public void correct()
	{
		num_asked++;
		num_right++;
	}
	
	//method to add to the number of times asked
	public void incorrect()
	{
		num_asked++;
	}
	
	//method to print the question to the file of questions
	public void toFile(PrintWriter P)
	{	
		P.println(question);
		P.println(num_answers);
		
		for (int i = 0; i < answers.length; i++)
		{
			P.println(answers[i]);
		}
		
		P.println(correct_answer);
		P.println(num_asked);
		P.println(num_right);
	}
	
	//method to get the number of answers for a question
	public int getNumAnswers()
	{
		return num_answers;
	}
}