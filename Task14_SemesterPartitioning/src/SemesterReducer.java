import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class SemesterReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        int sum = 0;
        int count = 0;
        int passCount = 0;

        int maxGrade = Integer.MIN_VALUE;
        int minGrade = Integer.MAX_VALUE;

        for (Text val : values) {

            String[] parts = val.toString().split(",");

            try {
                int grade = Integer.parseInt(parts[1]);

                if (grade >= 0 && grade <= 100) {

                    sum += grade;
                    count++;


                    
                    if (grade <= 50) {
                        passCount++;
                    }

                    // max / min
                    if (grade > maxGrade) maxGrade = grade;
                    if (grade < minGrade) minGrade = grade;
                }

            } catch (Exception e) {
                continue;
            }
        }

        double avg = (count == 0) ? 0 : (double) sum / count;
        double passRate = (count == 0) ? 0 : ((double) passCount / count) * 100;

        String result =
                "Avg: " + String.format("%.2f", avg) +
                " | PassRate: " + String.format("%.2f", passRate) + "%" +
                " | Students: " + count +
                " | Max: " + maxGrade +
                " | Min: " + minGrade;

        context.write(key, new Text(result));
    }
}
