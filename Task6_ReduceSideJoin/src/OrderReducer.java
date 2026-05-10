import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class OrderReducer extends Reducer<Text, Text, Text, Text> {

    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        String customerInfo = "UNKNOWN,UNKNOWN";
        List<String> orders = new ArrayList<>();

        for (Text val : values) {
            String v = val.toString();

            if (v.startsWith("customer~")) {
                customerInfo = v.substring(9);

	     } else if (v.startsWith("order~")) {
                orders.add(v.substring(6));
            }
        }

        for (String order : orders) {
            context.write(
                key,
                new Text(customerInfo + "," + order)
            );
        }
    }
}

