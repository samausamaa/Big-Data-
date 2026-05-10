import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Task6Driver {
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Reduce Side Join Task 6");
        job.setJarByClass(Task6Driver.class);

        MultipleInputs.addInputPath(job,
            new Path(args[0] + "/customers.csv"),
            TextInputFormat.class,
            CustomerMapper.class);
	
	MultipleInputs.addInputPath(job,
            new Path(args[0] + "/orders_big.csv.gz"),
            TextInputFormat.class,
            OrderMapper.class);

        job.setReducerClass(OrderReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setNumReduceTasks(1);

	FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

