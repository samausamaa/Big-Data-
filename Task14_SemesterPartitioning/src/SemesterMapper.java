import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Counter;
import java.io.IOException;

public class SemesterMapper extends Mapper<LongWritable, Text, Text, Text> {

    public static enum ValidationCounters {
        INVALID_GRADE,
        INVALID_FORMAT
    }

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString().trim();

        if (line.isEmpty()) return;

        String[] fields = line.split(",");

        if (fields.length < 3) {
            context.getCounter(ValidationCounters.INVALID_FORMAT).increment(1);
            return;
        }

        String studentId = fields[0].trim();
        String semester  = fields[1].trim();
        String gradeStr  = fields[2].trim();

        int grade;
        try {
            grade = Integer.parseInt(gradeStr);
        } catch (NumberFormatException e) {
            context.getCounter(ValidationCounters.INVALID_GRADE).increment(1);
            return;
        }

        if (grade < 0 || grade > 100) {
            context.getCounter(ValidationCounters.INVALID_GRADE).increment(1);
            return;
        }

        context.write(new Text(semester), new Text(studentId + "," + grade));
    }
}
