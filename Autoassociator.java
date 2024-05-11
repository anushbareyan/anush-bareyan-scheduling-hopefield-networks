import java.util.Random;

public class Autoassociator {
	private int weights[][];
	private int trainingCapacity;

	public Autoassociator(CourseArray courses) {
		weights = new int[courses.length()][courses.length()];
		trainingCapacity = (int)(courses.length()*0.139);
		// TO DO
		// creates a new Hopfield network with the same number of neurons
		// as the number of courses in the input CourseArray
	}

	public int getTrainingCapacity() {
		return trainingCapacity;
	}

	public void training(int pattern[]) {
		// TO DO
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
