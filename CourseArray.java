import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Random;
public class CourseArray {

	private Course elements[];
	private int period;
	
	public CourseArray(int numOfCourses, int numOfSlots) {
		period = numOfSlots;
		elements = new Course[numOfCourses];
		for (int i = 1; i < elements.length; i++) 
			elements[i] = new Course();
	}
	
	public void readClashes(String filename) {
		try {
			BufferedReader file = new BufferedReader(new FileReader(filename));
			StringTokenizer line = new StringTokenizer(file.readLine());
			int count = line.countTokens(), i, j, k;
			int index[];
			while (count > 0) {
				if (count > 1) {
					index = new int[count];
					i = 0;
					while (line.hasMoreTokens()) {
						index[i] = Integer.parseInt(line.nextToken());
						i++;
					}

					for (i = 0; i < index.length; i++)
						for (j = 0; j < index.length; j++)
							if (j != i)
							{
								k = 0;
								while (k < elements[index[i]].clashesWith.size() && elements[index[i]].clashesWith.elementAt(k) != elements[index[j]])
									k++;
								if (k == elements[index[i]].clashesWith.size())
									elements[index[i]].addClash(elements[index[j]]);
							}
				}
				line = new StringTokenizer(file.readLine());
				count = line.countTokens();
			}
			file.close();
		}
		catch (Exception e) {
		}
	}
	
	public int length() {
		return elements.length;
	}
	
	public int status(int index) {
		return elements[index].clashSize();
	}
	
	public int slot(int index) {
		return elements[index].mySlot;
	}
	
	public void setSlot(int index, int newSlot) {
		elements[index].mySlot = newSlot;
	}
	
	public int maxClashSize(int index) {
		return elements[index] == null || elements[index].clashesWith.isEmpty() ? 0 : elements[index].clashesWith.size();
	}
	
	public int clashesLeft() {
		int result = 0;
		for (int i = 1; i < elements.length; i++)
			result += elements[i].clashSize();
		
		return result;
	}
	
	public void iterate(int shifts) {

		for (int index = 1; index < elements.length; index++) {
			elements[index].setForce();
			for (int move = 1; move <= shifts && elements[index].force != 0; move++) {
				elements[index].setForce();
				elements[index].shift(period);
			}
//			shifts++;
//			if(index<elements.length/2){
//				shifts++;
//			}else{
//				shifts--;
//			}

		}
	}
	
	public void printResult() {
		for (int i = 1; i < elements.length; i++)
			System.out.println(i + "\t" + elements[i].mySlot);
	}
	public void printSlot(){//print slot, number of courses, number of clashes
		System.out.println("Slot Courses Clashes");
		for (int i = 0; i < period; i++) {
			int coursesInSlot = 0;
			int clashesInSlot = 0;
			for (int j = 1; j < elements.length; j++) {
				if (elements[j].mySlot == i) {
					coursesInSlot++;
					clashesInSlot += elements[j].clashSize();
				}
			}
			System.out.println(i + "\t" + coursesInSlot + "\t" + clashesInSlot);
		}


	}

	public int[] getTimeSlot(int index){
		int[] res = new int[elements.length];
		for(int i=1;i< elements.length;i++){
			if(elements[i].mySlot==index) {
				res[i]=1;
			}else{
				res[i]=-1;
			}
		}
		return res;
	}
	public void findGoodPatterns(Autoassociator autoassociator){
		System.out.println("Good Patterns:");
		for (int j = 0; j < period; j++) { // Assuming period is the number of timeslots
			int coursesInSlot = 0;
			int clashesInSlot = 0;
			for (int k = 1; k < elements.length; k++) {
				if (elements[k].mySlot == j) {
					coursesInSlot++;
					clashesInSlot += elements[k].clashSize();
				}
			}
			// Check if clashes are 0 and courses are >= (all courses / training capacity) / 2
			if (clashesInSlot == 0 && coursesInSlot >= (elements.length - 1) / autoassociator.getTrainingCapacity() / 2) {
				int[] timeslot = new int[elements.length];
				for (int k = 1; k < elements.length; k++) {
					if (elements[k].mySlot == j) {
						timeslot[k] = 1;
					} else {
						timeslot[k] = -1;
					}
				}
				System.out.println("Timeslot " + j + ": " + Arrays.toString(timeslot));
				autoassociator.training(timeslot);
			}
		}
	}

}
