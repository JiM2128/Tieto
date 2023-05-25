import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class GUI extends JFrame {

    private JPanel mainPanel;
    private JLabel deadlineLabel;
    private JLabel hoursLabel;
    private JTextField deadlineField;
    private JTextField hoursField;
    private JButton generateButton;
    private JLabel resultLabel;
    private JLabel errorLabel;
    private JLabel outputLabel;

    public GUI() {

        setTitle("Schedule Generator");
        setSize(500, 300);
        mainPanel = new JPanel(new GridLayout(10, 3));
        add(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        deadlineLabel = new JLabel("Deadline (YYYY-MM-DD):");
        deadlineField = new JTextField(1);

        hoursLabel = new JLabel("Hours Required:");
        hoursField = new JTextField(1);

        generateButton = new JButton("Generate");

        resultLabel = new JLabel();
        errorLabel = new JLabel();
        outputLabel = new JLabel();

        mainPanel.add(deadlineLabel);
        mainPanel.add(deadlineField);

        mainPanel.add(hoursLabel);
        mainPanel.add(hoursField);

        mainPanel.add(new JPanel());
        mainPanel.add(generateButton);
        mainPanel.add(new JPanel());

        mainPanel.add(resultLabel);
        mainPanel.add(new JLabel());
        mainPanel.add(outputLabel);

        generateButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {

                Util utility = new Util();
                File_IO fileIo = new File_IO();

                ArrayList<Input> data;

                try {
                    data = fileIo.readData("C:\\Users\\Justas\\Desktop\\TietoHM\\ScheduleGenerator\\JSON_files\\Input.json");
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                utility.printList(data);

                DataManager dataManager = getInputFromGUI(data);

                if(dataManager != null)
                {
                    ArrayList<Output> output = dataManager.createSchedule(resultLabel);

                    System.out.println("------   FINAL   ------");
                    utility.printList(output);

                    fileIo.printData(output, "C:\\Users\\Justas\\Desktop\\TietoHM\\ScheduleGenerator\\JSON_files\\Output.json");

                    outputLabel.setText("Schedule generated and output to Json file \"Output.json\".");
                }

                else
                    outputLabel.setText("");


            }
        });
    }

    public DataManager getInputFromGUI(ArrayList<Input> data) {

        LocalDate deadline =  LocalDate.parse(deadlineField.getText());
        int hoursRequired = Integer.parseInt(hoursField.getText());

        boolean isValid = true;

        while (isValid)
        {

            if(deadline.isBefore(data.get(0).getDate()))
            {
                JOptionPane.showMessageDialog(mainPanel, "Invalid date input. Please enter a valid date (yyyy-MM-dd) after " + data.get(0).getDate().toString());
                isValid = false;
            }


            if(hoursRequired <= 0)
            {
                JOptionPane.showMessageDialog(mainPanel, "Invalid hours input. Please enter a positive integer value.");
                isValid = false;
            }


            if (isValid)
                return new DataManager(deadline, hoursRequired, data);
        }
        return null;
    }
}
