import java.util.*;

public class Autoassociator {
	public int weights[][];
	private int trainingCapacity;

	public int numberOfCasesTrained; // number of slots trained on

	public Autoassociator(CourseArray courses) {
		weights = new int[courses.length() - 1][courses.length() - 1];
		trainingCapacity = (int)((courses.length() - 1)*0.139);
		numberOfCasesTrained=0;
	}

	public void printWeights(){
		for(int i=1;i<weights.length;i++){
			for(int j=1;j< weights[0].length; j++){
				System.out.print(weights[i][j]+" ");
			}
			System.out.println();
		}
	}

	public int getTrainingCapacity() {
		return trainingCapacity;
	}

	public void setNumberOfCasesTrained(int n){
		numberOfCasesTrained=n;
	}

	public void training(int pattern[]) {
		for (int i = 1; i < weights.length; i++) {
			for (int j = 1; j < weights[0].length; j++) {
				if (i != j) {
					weights[i][j] += pattern[i] * pattern[j]; //weights[i][j] += pattern[i]*pattern[j];
				}
			}
		}
	}

	public int unitUpdate_replace(int neurons1[], int neurons2[]) {
		Random r = new Random();
		int i = r.nextInt(neurons1.length - 1) + 1;
		int val1 = neurons1[i];
		unitUpdate(neurons1, i);
		int val2 = neurons1[i];
		if(val1*val2==-1){// if it changed from 1 to -1 in neurons1 then add it to neurons2
			neurons2[i]=1;
		}
		return i;
	}


	public int unitUpdate(int neurons[]) {
		Random r = new Random();
		int i = r.nextInt(neurons.length - 1) + 1;
		unitUpdate(neurons, i);
		return i;
	}
	public void unitUpdate(int neurons[], int index) {
		int s = 0;
		for (int i = 1; i < neurons.length; i++) {
			s += weights[index - 1][i - 1] * neurons[i];
		}
		neurons[index] = s > 0 ? 1 : -1;//this always gives -1
//		System.out.println("Neuron " + index + " updated to: " + neurons[index]);
	}

	public void chainUpdate(int neurons[], int steps) {
		// TO DO
		// implements the specified number od update steps
		for(int i=0;i<steps;i++){
			unitUpdate(neurons);
		}
	}
}
