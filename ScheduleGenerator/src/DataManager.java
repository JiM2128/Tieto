import javax.swing.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class DataManager {
    private LocalDate deadline;
    private int hoursRequired;
    private ArrayList<Input> input;

    public DataManager(LocalDate deadline, int hoursRequired, ArrayList<Input> input) {
        this.deadline = deadline;
        this.hoursRequired = hoursRequired;
        this.input = input;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getHoursRequired() {
        return hoursRequired;
    }

    public void setHoursRequired(int hoursRequired) {
        this.hoursRequired = hoursRequired;
    }

    public ArrayList<Input> getInput() {
        return input;
    }

    public void setInput(ArrayList<Input> input) {
        this.input = input;
    }

    @Override
    public String toString() {
        return "DataManager{" +
                "deadline='" + deadline + '\'' +
                ", hoursRequired=" + hoursRequired +
                ", input=" + input +
                '}';
    }

    public int calculateTime(JLabel resultLabel) {

        int sum = 0, sumOfBusyHours = 0;
        Period period = Period.between(input.get(0).getDate(), getDeadline());

        for (int i = 0; i < input.size(); ++i)
            sumOfBusyHours += input.get(i).getBusyHours() + 8;

        for (int i = 0; i < period.getDays(); ++i)
            sum += 24 - 8;

        if (getHoursRequired() <= (sum - sumOfBusyHours))
        {
            resultLabel.setText("You will be able to finish on time.");
            return  1;
        }


        else if (24 * period.getDays() > getHoursRequired())
        {
            resultLabel.setText("You will be able to finish on time if you sleep less.");
            return 0;
        }


        else
            resultLabel.setText("You will not be able to finish on time.");


        return -1;
    }
    private void removeExtraHours(ArrayList<Output> outputList) {

        int i = outputList.size() - 1;

        while(getHoursRequired() < 0)
        {
            if(outputList.get(i).getHours() >= 1)
            {
                outputList.get(i).setHours(outputList.get(i).getHours() - 1);
                setHoursRequired(getHoursRequired() + 1);
            }

            i--;
        }

        for (int j = outputList.size() - 1; j >= 0; --j)
            if (outputList.get(j).getHours() == 0)
                outputList.remove(outputList.get(j));
    }

    public ArrayList<Output> createSchedule(JLabel resultLabel) {

        ArrayList<Output> outputList = new ArrayList<>();
        LocalDate current = input.get(0).getDate();

        while (!current.isAfter(deadline)) {
            outputList.add(new Output(current, 0));
            current = current.plusDays(1);
        }

        int index = calculateTime(resultLabel);
        double hoursPerDay = Math.ceil((double) hoursRequired / ((double) outputList.size()));

        int k = 0;

        for(Output out : outputList)
        {
            for(Input in : input)
            {
                if(in.getDate().equals(out.getDate()) && out.getHours() == 0)
                {
                    if(in.getBusyHours() >= 10)
                    {
                        if(24 - 12 - in.getBusyHours() < hoursPerDay)
                        {
                            out.setHours(24 - 12 - in.getBusyHours());
                            setHoursRequired(getHoursRequired() - (24 - 12 - in.getBusyHours()));
                        }

                        else
                        {
                            out.setHours((int) hoursPerDay);
                            setHoursRequired(getHoursRequired() - (int) hoursPerDay);
                        }

                    }

                    else if(in.getBusyHours() >= 8)
                    {
                        if (24 - 10 - in.getBusyHours() < hoursPerDay)
                        {
                            out.setHours(24 - 10 - in.getBusyHours());
                            setHoursRequired(getHoursRequired() - (24 - 10 - in.getBusyHours()));
                        }

                        else
                        {
                            out.setHours((int) hoursPerDay);
                            setHoursRequired(getHoursRequired() - (int) hoursPerDay);
                        }
                    }

                    else if(in.getBusyHours() >= 6)
                    {
                        if(24 - 8 - in.getBusyHours() < hoursPerDay)
                        {
                            out.setHours(24 - 8 - in.getBusyHours());
                            setHoursRequired(getHoursRequired() - (24 - 8 - in.getBusyHours()));
                        }

                        else
                        {
                            out.setHours((int) hoursPerDay);
                            setHoursRequired(getHoursRequired() - (int) hoursPerDay);
                        }
                    }

                    else if(in.getBusyHours() >= 4)
                    {
                        if(24 - 6 - in.getBusyHours() < hoursPerDay)
                        {
                            out.setHours(24 - 6 - in.getBusyHours());
                            setHoursRequired(getHoursRequired() - (24 - 6 - in.getBusyHours()));
                        }

                        else
                        {
                            out.setHours((int) hoursPerDay);
                            setHoursRequired(getHoursRequired() - (int) hoursPerDay);
                        }
                    }

                    else if(in.getBusyHours() >= 2)
                    {
                        if(24 - 4 - in.getBusyHours() < hoursPerDay)
                        {
                            out.setHours(24 - 4 - in.getBusyHours());
                            setHoursRequired(getHoursRequired() - (24 - 4 - in.getBusyHours()));
                        }

                        else
                        {
                            out.setHours((int) hoursPerDay);
                            setHoursRequired(getHoursRequired() - (int) hoursPerDay);
                        }
                    }

                    k++;
                }
            }
        }


        hoursPerDay = Math.ceil((double) hoursRequired / ((double) outputList.size() - k));

        if(index == 1)
        {
            for(Output out : outputList)
                if(out.getHours() == 0 && getHoursRequired() > 0)
                {
                    out.setHours(8);
                    setHoursRequired(getHoursRequired() - 8);
                }

        }

        else if(index == 0)
        {
            for(Output out : outputList)
                if(out.getHours() == 0 && getHoursRequired() > 0)
                {
                    out.setHours((int) hoursPerDay);
                    setHoursRequired(getHoursRequired() - (int) hoursPerDay);
                }
        }

        removeExtraHours(outputList);

        return outputList;
    }
}