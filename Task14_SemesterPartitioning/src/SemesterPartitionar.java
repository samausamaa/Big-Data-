import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class SemesterPartitioner extends Partitioner<Text, Text> {

    @Override
    public int getPartition(Text key, Text value, int numReduceTasks) {
        String semester = key.toString();

        if (semester.equals("Fall2023")) return 0;
        else if (semester.equals("Spring2024")) return 1;
        else if (semester.equals("Fall2024")) return 2;
        else if (semester.equals("Spring2025")) return 3;

        return 0;
    }
}
