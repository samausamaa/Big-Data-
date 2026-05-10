# Task 6 - Reduce Side Join using Hadoop MapReduce

## Project Overview

This project implements a Reduce Side Join using Hadoop MapReduce on a large-scale e-commerce dataset.

The goal is to join customer information with order records to produce enriched order data containing:

- Customer Name
- Customer City
- Product Name
- Order Amount

The project was implemented and tested on Cloudera Hadoop VM using HDFS and YARN.

---

# Technologies Used

- Java
- Hadoop MapReduce
- HDFS
- YARN
- Cloudera QuickStart VM
- MultipleInputs API

---

# Dataset Information

Dataset Used:
Olist Brazilian E-Commerce Dataset

Dataset Source:
https://www.kaggle.com/datasets/olistbr/brazilian-ecommerce

Original dataset tables used:
- olist_customers_dataset.csv
- olist_orders_dataset.csv
- olist_order_items_dataset.csv
- olist_products_dataset.csv

The dataset was preprocessed and scaled synthetically to exceed 1GB for Big Data testing purposes.

Final Dataset Size:
- orders_big.csv ≈ 1.4 GB

---

# Input Format

## Customers File

customerId,customerName,city

Example:
abc123,Ahmed_0,cairo

---

## Orders File

orderId,customerId,productName,amount

Example:
ord001,abc123,toys,120.5

---

# Output Format

customerId    customerName,city,productName,amount

Example:
abc123 Ahmed_0,cairo,toys,120.5

---

# Hadoop Components

## CustomerMapper

Reads customer records and emits:

(customerId, customer~customerName,city)

---

## OrderMapper

Reads order records and emits:

(customerId, order~orderId,productName,amount)

---

## OrderReducer

Performs Reduce Side Join between customer and order records.

Handles unknown customers using:
UNKNOWN,UNKNOWN

---

## Driver

Configured using:
- MultipleInputs
- TextInputFormat
- Reducer count = 1

---

# Data Validation

The implementation handles:
- Missing fields
- Invalid numeric values
- Malformed lines

Invalid records are skipped safely.

---

# How to Run

## Compile

javac -classpath `hadoop classpath` -d . *.java

jar -cvf task6.jar *

---

## Run Hadoop Job

hadoop jar task6.jar Task6Driver \
/user/cloudera/project/task6/input \
/user/cloudera/project/task6/output

---

# HDFS Commands

## Upload Files

hdfs dfs -put customers.csv /user/cloudera/project/task6/input

hdfs dfs -put orders_big.csv.gz /user/cloudera/project/task6/input

---

# Sample Output

00012a2ce6f8dcda20d059ce98491703    Ali_84790,osasco,toys,96.6

---

# Performance Statistics

Map input records: 16,996,943

Reduce input records: 16,996,941

Reduce output records: 16,897,500

Output size: 1.2 GB

---

# Key Concepts Demonstrated

- Reduce Side Join
- MultipleInputs
- Hadoop Distributed Processing
- Large Dataset Processing
- HDFS Integration
- Shuffle and Sort Phase

# Task 14 - Semester-Based Partitioning using Hadoop MapReduce

## Project Overview

This project analyzes student academic performance data across different semesters using Hadoop MapReduce.

The main goal of the project is to use a Custom Partitioner to distribute records so that each reducer processes data for one semester only.

The project calculates the following statistics for each semester:

- Average grade per semester
- Pass rate per semester
- Total number of students per semester

The project was implemented and tested on Cloudera Hadoop VM using HDFS and YARN.

---

## Technologies Used

- Java
- Hadoop MapReduce
- HDFS
- YARN
- Cloudera QuickStart VM
- Custom Partitioner

---

## Dataset Information

### Input Format

The input dataset is a CSV file with the following format:

student_id,semester,grade,subject,instructor

### Example Input

ST001,Fall2023,85,Mathematics,Prof. Smith
ST002,Fall2023,90,Mathematics,Prof. Smith
ST003,Spring2024,88,Physics,Prof. Johnson
ST004,Fall2023,92,Chemistry,Prof. Williams
ST005,Spring2024,87,Biology,Prof. Brown

---

## Output Format

The output contains statistics for each semester.

### Example Output

Fall2023
Avg Grade: 86
Pass Rate: XX%
Total Students: X

Spring2024
Avg Grade: 88.7
Pass Rate: XX%
Total Students: X

---

## Hadoop Components

## 1. Mapper

The Mapper reads student records from the input file.

For each valid record, it extracts:

- Semester
- Grade

Then it emits the following key-value pair:

(semester, grade)

### Example

Fall2023, 85
Spring2024, 88

---

## 2. Custom Partitioner

The Custom Partitioner controls how data is distributed among reducers.

It ensures that:

- All records of the same semester go to the same reducer
- Each reducer processes exactly one semester

### Example Mapping

Fall2023   → Reducer 0
Spring2024 → Reducer 1

This allows reducer-level parallelism and keeps semester data separated.

---

## 3. Reducer

Each reducer processes records for one semester only.

The Reducer calculates:

- Average grade
- Pass rate
- Total number of students

The pass rate is calculated based on the following condition:

grade >= 50

---

## 4. Driver

The Driver class configures and runs the Hadoop MapReduce job.

It sets:

- Mapper class
- Reducer class
- Custom Partitioner class
- Input path
- Output path
- Number of reducers

The number of reducers is set according to the number of semesters.

---

## Data Validation

The implementation handles invalid input safely.

Invalid records are skipped if they contain:

- Missing fields
- Malformed records
- Invalid numeric grade values
- Grades outside the range 0 to 100

This prevents the MapReduce job from failing due to bad input data.

---

## How to Run

### 1. Compile the Java Files

javac -classpath `hadoop classpath` -d . *.java

---

### 2. Create the JAR File

jar -cvf task14.jar *

---

### 3. Create Input Directory in HDFS

hdfs dfs -mkdir -p /user/cloudera/project/task14/input

---

### 4. Upload Input Data to HDFS

hdfs dfs -put students.csv /user/cloudera/project/task14/input

---

### 5. Remove Old Output Directory if It Exists

hdfs dfs -rm -r /user/cloudera/project/task14/output

---

### 6. Run the Hadoop Job

hadoop jar task14.jar Task14Driver /user/cloudera/project/task14/input /user/cloudera/project/task14/output

---

### 7. View the Output

hdfs dfs -cat /user/cloudera/project/task14/output/*

---

## HDFS Commands Used

### Upload Input Data

hdfs dfs -put students.csv /user/cloudera/project/task14/input

### View Files in Input Directory

hdfs dfs -ls /user/cloudera/project/task14/input

### View Output

hdfs dfs -cat /user/cloudera/project/task14/output/*

### Remove Output Directory

hdfs dfs -rm -r /user/cloudera/project/task14/output

---

## Key Concepts Demonstrated

- Hadoop MapReduce
- Custom Partitioner
- Key-based data distribution
- Reducer-level parallelism control
- HDFS input and output handling
- Educational data analytics
- Large-scale distributed processing
- Big Data batch processing

---

## Performance Statistics
After running the MapReduce job, Hadoop provides execution statistics such as:

Map input records: X
Reduce input records: X
Reduce output records: X
Output size: X

These statistics can be viewed in the terminal output or through the YARN ResourceManager interface.

---

## Project Summary

This project demonstrates how Hadoop MapReduce can be used to analyze student academic performance data.

By using a Custom Partitioner, records are distributed based on semester, ensuring that each reducer handles only one semester.

This improves data organization and demonstrates an important Big Data concept: controlling data flow between Mapper and Reducer using custom partitioning.

---

## Author

Task 14 - Semester-Based Partitioning using Hadoop MapReduce
