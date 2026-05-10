import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class OrderMapper extends Mapper<LongWritable, Text, Text, Text> {

    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString().trim();

        if (line.startsWith("orderId")) return;

        String[] parts = line.split(",", 4);

        if (parts.length < 4) return;

	String orderId     = parts[0].trim();
        String customerId  = parts[1].trim();
        String productName = parts[2].trim();
        String amount      = parts[3].trim();

        if (customerId.isEmpty() || productName.isEmpty()) return;

        try {
            Double.parseDouble(amount);
        } catch (Exception e) {
            return;
        }

        context.write(
            new Text(customerId),
            new Text("order~" + orderId + "," + productName + "," + amount)
        );

    }
}

