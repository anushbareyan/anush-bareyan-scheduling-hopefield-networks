import java.util.Arrays;
import java.util.Random;

public class Autoassociator {
	public int weights[][];
	private int trainingCapacity;

	public Autoassociator(CourseArray courses) {
		weights = new int[courses.length()][courses.length()];
		for(int i=1;i<weights.length;i++){
			for(int j=1;j< weights[0].length; j++){
				if(i==j){
					weights[i][j] = 0;
				}else if(courses.slot(i) == courses.slot(j)){
					weights[i][j] = 1;
				}else{
					weights[i][j] = -1;
				}


//				if(i==j){
//					weights[i][j] = 0;
//				}else{
//					weights[i][j] = 1; //TODO i couldnt yet initialize this properly with right values
//				}
			}
		}
		trainingCapacity = (int)(courses.length()*0.139);
		// TO DO
		// creates a new Hopfield network with the same number of neurons
		// as the number of courses in the input CourseArray
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

	public void training(int pattern[]) {
		// TODO i couldnt figure out where to use the unitUpdate method here
		for(int i=1;i<weights.length;i++){
			for(int j=1;j< weights[0].length; j++){
				if(i!=j){
					weights[i][j] += pattern[i]*pattern[j];
				}
			}
		}

	}

	public int unitUpdate(int neurons[]) {
		// TO DO
		// implements a single update step and
		// returns the index of the randomly selected and updated neuron
		Random r = new Random();
		int i = r.nextInt(neurons.length);
		unitUpdate(neurons,i);
		return i;
	}

	public void unitUpdate(int neurons[], int index) {
		int s = 0;
		for(int i=0;i<neurons.length;i++){
			s = s+weights[index][i]*neurons[i];
		}
		if(s>0){
			neurons[index] = 1;
		}else{
			neurons[index] = -1;
		}

		// TO DO
		// implements the update step of a single neuron specified by index
	}

	public void chainUpdate(int neurons[], int steps) {
		// TO DO
		// implements the specified number od update steps
		for(int i=0;i<steps;i++){
			unitUpdate(neurons);
		}
	}

//	public void fullUpdate(int neurons[]) {
//		// TO DO
//		// updates the input until the final state achieved
//	}
}
