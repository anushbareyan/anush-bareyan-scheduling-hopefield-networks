import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;

public class TimeTable extends JFrame implements ActionListener {

	private JPanel screen = new JPanel(), tools = new JPanel();
	private JButton tool[];
	private JTextField field[];
	private CourseArray courses;
	private Color CRScolor[] = {Color.RED, Color.GREEN, Color.BLACK};
	private PrintWriter logWriter;
	private Autoassociator autoassociator;
	
	public TimeTable() {
		super("Dynamic Time Table");
		setSize(500, 800);
		setLayout(new FlowLayout());
		
		screen.setPreferredSize(new Dimension(300, 1000));
		add(screen);
		
		setTools();
		add(tools);
		
		setVisible(true);


	}
	
	public void setTools() {
		String capField[] = {"Slots:", "Courses:", "Clash File:", "Iters:", "Shift:"};
		field = new JTextField[capField.length];
		
		String capButton[] = {"Load", "Start", "Step", "Print", "Exit", "Continue","Train", "PrintTimeslot","update"};
		tool = new JButton[capButton.length];
		
		tools.setLayout(new GridLayout(4 * capField.length + capButton.length, 1));
		
		for (int i = 0; i < field.length; i++) {
			tools.add(new JLabel(capField[i]));
			field[i] = new JTextField(5);
			tools.add(field[i]);
		}
		
		for (int i = 0; i < tool.length; i++) {
			tool[i] = new JButton(capButton[i]);
			tool[i].addActionListener(this);
			tools.add(tool[i]);
		}
		
		field[0].setText("17");
		field[1].setText("81"); //682  81
		field[2].setText("hec-s-92.stu"); //car-s-91.stu  hec-s-92.stu
		field[3].setText("1");
		field[4].setText("17");
	}
	
//	public void draw() {
//		Graphics g = screen.getGraphics();
//		int width = Integer.parseInt(field[0].getText()) * 10;
//		for (int courseIndex = 1; courseIndex < courses.length(); courseIndex++) {
//			g.setColor(CRScolor[courses.status(courseIndex) > 0 ? 0 : 1]);
//			g.drawLine(0, courseIndex, width, courseIndex);
//			g.setColor(CRScolor[CRScolor.length - 1]);
//			g.drawLine(10 * courses.slot(courseIndex), courseIndex, 10 * courses.slot(courseIndex) + 10, courseIndex);
//		}
//	}


	
	private int getButtonIndex(JButton source) {
		int result = 0;
		while (source != tool[result]) result++;
		return result;
	}
	
	public void actionPerformed(ActionEvent click) {
		int source = getButtonIndex((JButton) click.getSource()), i, min, step, clashes;
		switch (source) {
			case 0:
				int slots = Integer.parseInt(field[0].getText());
				courses = new CourseArray(Integer.parseInt(field[1].getText()) + 1, slots);
				autoassociator = new Autoassociator(courses);
				courses.readClashes(field[2].getText());
				draw();
				break;
			case 1:
				min = Integer.MAX_VALUE;
				step = 0;
				for (i = 1; i < courses.length(); i++) courses.setSlot(i, 0);

				for (int iteration = 1; iteration <= Integer.parseInt(field[3].getText()); iteration++) {
					courses.iterate(Integer.parseInt(field[4].getText()));
					draw();
					clashes = courses.clashesLeft();
					if (clashes < min) {
						min = clashes;
						step = iteration;
					}
				}
				System.out.println("Shift = " + field[4].getText() + "\tMin clashes = " + min + "\tat step " + step);
				setVisible(true);
//				autoassociator = new Autoassociator(courses); // resets after clicking start
				break;
			case 2:
				//			System.out.println(Arrays.toString(courses.getTimeSlot(0)));
				courses.iterate(Integer.parseInt(field[4].getText()));
				draw();

				break;
			case 3:
				break;

			case 4:
				System.exit(0);
			case 5:

				System.out.println("Exam\tSlot\tClashes");
				for (i = 1; i < courses.length(); i++)
					System.out.println(i + "\t" + courses.slot(i) + "\t" + courses.status(i));
				min = Integer.MAX_VALUE;
				step = 0;
				for (int iteration = 1; iteration <= Integer.parseInt(field[3].getText()); iteration++) {
					courses.iterate(Integer.parseInt(field[4].getText()));
					draw();
					clashes = courses.clashesLeft();
					if (clashes < min) {
						min = clashes;
						step = iteration;
					}
				}
				System.out.println("Shift = " + field[4].getText() + "\tMin clashes = " + min + "\tat step " + step);
				setVisible(true);
				break;

			case 6:
				try {
					logWriter = new PrintWriter(new BufferedWriter(new FileWriter("timeslots_no_clash.txt", true)));
				} catch (Exception e) {

				}
				//autoassociator.printWeights();//before
				String s = courses.findGoodPatterns(autoassociator, Integer.parseInt(field[0].getText()));//also trains
				System.out.println("cases: "+autoassociator.numberOfCasesTrained+" "+ autoassociator.getTrainingCapacity());
				logWriter.println(field[2].getText());
				logWriter.println("Shift = " + field[4].getText() + " Iters = " + field[3].getText());
				logWriter.println(s);
				logWriter.println();
				logWriter.flush();
				autoassociator.printWeights();//after
				System.out.println("cases: "+autoassociator.numberOfCasesTrained);
				break;
			case 7:
				courses.printSlot();
				break;
			case 8:
				min = Integer.MAX_VALUE;
				step = 0;
				int cycle_step = 0;
				for (i = 1; i < courses.length(); i++) courses.setSlot(i, 0);
				Random rand = new Random();
				for (int iteration = 1; iteration <= Integer.parseInt(field[3].getText()); iteration++) {
					courses.iterate(Integer.parseInt(field[4].getText()));
					draw();
					clashes = courses.clashesLeft();
					if (clashes < min) {
						min = clashes;
						step = iteration;

					}
					if (iteration % 1 == 0) {
						autoassociator.unitUpdate_replace(courses.getTimeSlot(rand.nextInt(0, 16)), courses.getTimeSlot(rand.nextInt(0, 16)));
					}
				}


				System.out.println("Shift = " + field[4].getText() + "\tMin clashes = " + min + "\tat step " + step);
				setVisible(true);
				break;
			}
		}
	public void draw() {
		Graphics g = screen.getGraphics();
		int width = Integer.parseInt(field[0].getText()) * 10;
		for (int courseIndex = 1; courseIndex < courses.length(); courseIndex++) {
			g.setColor(CRScolor[courses.status(courseIndex) > 0 ? 0 : 1]);
			g.drawLine(0, courseIndex, width, courseIndex);
			g.setColor(CRScolor[CRScolor.length - 1]);
			g.drawLine(10 * courses.slot(courseIndex), courseIndex, 10 * courses.slot(courseIndex) + 10, courseIndex);
		}
	}



	public static void main(String[] args) {
		new TimeTable();

	}
}
