import java.util.*;
import java.io.*;

public class Assig3
{
	public static void main(String[] args) throws IOException
	{
		System.out.println("Welcome to the quiz, good luck!");
		
		//reads in filename from command line
		String filename = args[0];
		ArrayList<Question> list;
		
		list = getQuestionList(filename); //calls the getQuestionList() local method to read all of the questions from the file and create Question objects for them
		
		Scanner keyIn = new Scanner(System.in);
		
		int [] user_choice = new int [list.size()]; //creates an array of integers to keep track of the user's answers
		
		//for each Question: print the question, print the answers, ask user for input
		for (int i = 0; i < list.size(); i++)
		{
			System.out.println(list.get(i).getQuestion()); //uses get() method on ArrayList of questions to access each question, then uses getQuestion instance method to get question string
			System.out.println(list.get(i).getAnswers()); //same as above but for answers
			do//do-while loop to make sure user inputs a valid integer
			{
				user_choice[i] = keyIn.nextInt();
			} while (user_choice[i] < 1 || user_choice[i] > list.get(i).getNumAnswers());
			System.out.println();
		}
		
		System.out.println("Thanks for your answers!");
		System.out.println("Here are your results:");
		System.out.println();
		
		File outFile = new File(filename);
		PrintWriter P = new PrintWriter(outFile);
		int right = 0;
		int wrong = 0;
		
		//for each Question, print question, correct answer, and user answer
		//then display the response and add to right, wrong, and asked totals
		for (int i = 0; i < list.size(); i++)
		{
			System.out.println(list.get(i).getQuestion());
			System.out.println("Correct answer: " + list.get(i).getCorrectAnswer());
			System.out.println("User answer: " + user_choice[i]);
			
			//if user choice is correct, tell them and add to totals in Question object and right variable
			if (user_choice[i] == list.get(i).getCorrectAnswer())
			{
				System.out.println("CORRECT! Great work!");
				list.get(i).correct();
				right++;
			}
			//else, the user is incorrect, iterate appropriate totals
			else
			{
				System.out.println("INCORRECT! Better luck next time!");
				list.get(i).incorrect();
				wrong++;
			}
			System.out.println();
			list.get(i).toFile(P); //prints question's updated information to the file via PrintWriter P
		}
		P.close();
		
		System.out.println();
		System.out.println("Here is your overall performance: ");
		System.out.println("Right: " + right);
		System.out.println("Wrong: " + wrong);
		
		double percent_right = (double) right / (wrong + right);
		
		System.out.printf("Percent: %4.2f", percent_right);
		System.out.println();
		System.out.println();
		
		System.out.println("Here are some cumulative statistics:");
		System.out.println();
		
		//for each Question, print the question, times it was asked, times it was answered correctly, and percentage correct
		for (int i = 0; i < list.size(); i++)
		{
			System.out.println(list.get(i).getQuestion());
			System.out.println("Times asked: " + list.get(i).getNumTimesAsked());
			System.out.println("Times right: " + list.get(i).getNumTimesRight());
			double cum_percent_right = (double) list.get(i).getNumTimesRight() / list.get(i).getNumTimesAsked();
			System.out.printf("Percent Right: %4.2f", cum_percent_right);
			System.out.println();
			System.out.println();
		}
		
		//make two new Question objects to use as a comparison when finding easiest and hardest question
		Question easiest = new Question();
		Question hardest = new Question();
		for (int i = 0; i < list.size(); i++)
		{
			//the first question is automatically set to be the easiest and hardest
			if (i == 0)
			{
				easiest = list.get(i);
				hardest = list.get(i);
			}
			//as the loop continues, it compares the correct answers of each question to the easiest and hardest,
			//reassigning the easiest and hardest questions when appropriate
			else if (((double)list.get(i).getNumTimesRight() / list.get(i).getNumTimesAsked()) > ((double)easiest.getNumTimesRight() / easiest.getNumTimesAsked()))
			{
				easiest = list.get(i);
			}
			
			else if (((double)list.get(i).getNumTimesRight() / list.get(i).getNumTimesAsked()) < ((double)hardest.getNumTimesRight() / hardest.getNumTimesAsked()))
			{
				hardest = list.get(i);
			}
		}
		
		System.out.println("Easiest question:");
		System.out.println(easiest.getQuestion());
		System.out.println("Times asked: " + easiest.getNumTimesAsked());
		System.out.println("Times right: " + easiest.getNumTimesRight());
		double easiest_percent_right = (double) easiest.getNumTimesRight() / easiest.getNumTimesAsked();
		System.out.printf("Percent right: %4.2f", easiest_percent_right);
		System.out.println("\n");
		
		System.out.println("Hardest Question:");
		System.out.println(hardest.getQuestion());
		System.out.println("Times asked: " + hardest.getNumTimesAsked());
		System.out.println("Times right: " + hardest.getNumTimesRight());
		double hardest_percent_right = (double) hardest.getNumTimesRight() / hardest.getNumTimesAsked();
		System.out.printf("Percent right: %4.2f", hardest_percent_right);
		System.out.println("\n");
	}
	
	//static method which accepts a file name and returns an ArrayList of Questions
	public static ArrayList<Question> getQuestionList(String filename) throws IOException
	{
		File inFile = new File(filename);
		Scanner inScan = new Scanner(inFile);
		ArrayList<Question> L = new ArrayList<>();
		
		//while the file has more data after each question is read
		while (inScan.hasNextLine())
		{
			String question = inScan.nextLine(); //scans in question
			int num_answers = inScan.nextInt(); //scans in number of answers
			inScan.nextLine(); //puts scanner on new line after the nextInt() call
			String [] answers = new String [num_answers];
			
			//creating an array of answers
			for (int i = 0; i < num_answers; i++)
			{
				answers[i] = inScan.nextLine();
			}
			
			//scans in rest of data
			int correct_answer = inScan.nextInt();
			inScan.nextLine();
			int num_asked = inScan.nextInt();
			inScan.nextLine();
			int num_right = inScan.nextInt();
			
			//only moves scanner to next line if it exists in the file
			if (inScan.hasNextLine())
			{
				inScan.nextLine();
			}
			
			//adds new Question object to ArrayList L
			L.add(new Question(question, num_answers, answers, correct_answer, num_asked, num_right));
		}
		inScan.close();
		
		return L;
	}
}