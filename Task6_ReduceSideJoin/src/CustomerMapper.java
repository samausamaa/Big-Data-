import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CustomerMapper extends Mapper<LongWritable, Text, Text, Text> {

    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString().trim();

        
        if (line.startsWith("customerId")) return;

        String[] parts = line.split(",");
	
	if (parts.length >= 3) {
            String customerId = parts[0].trim();
            String name       = parts[1].trim();
            String city       = parts[2].trim();

            context.write(
                new Text(customerId),
                new Text("customer~" + name + "," + city)
            );
        }
    }
}

