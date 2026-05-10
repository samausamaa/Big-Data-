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

---

Task 14 - Semester-Based Partitioning using Hadoop MapReduce
Project Overview

This task analyzes student academic performance data across different semesters using Hadoop MapReduce.

The goal is to ensure that each reducer processes one semester only using a Custom Partitioner, and then compute:

Average grade per semester
Pass rate per semester
Basic student statistics per semester

This helps in efficient distributed processing of academic datasets grouped logically by semester.

Technologies Used
Java
Hadoop MapReduce
HDFS
YARN
Cloudera QuickStart VM
Custom Partitioner
Dataset Information
Input Dataset Format

student_id, semester, grade, subject, instructor

Example:
ST001,Fall2023,85,Mathematics,Prof. Smith
ST002,Fall2023,90,Mathematics,Prof. Smith
ST003,Spring2024,88,Physics,Prof. Johnson

Output Format
Per Semester Results

Fall2023
Avg Grade: 86
Pass Rate: XX%
Total Students: X

Spring2024
Avg Grade: 88.7
Pass Rate: XX%
Total Students: X

Hadoop Components
Mapper

The Mapper reads each record and extracts:

student_id
semester
grade
Output Key-Value:

(semester, grade)

Custom Partitioner

A custom partitioner is implemented to ensure that:

All records of the same semester go to the same reducer
Each reducer processes exactly one semester

Example logic:

Fall2023 → Reducer 0
Spring2024 → Reducer 1
Reducer

Each reducer processes one semester only and calculates:

Average Grade
Pass Rate (students with grade ≥ 50)
Total number of students
Basic statistics per semester
Data Validation

The implementation ensures:

Grade must be between 0 and 100
Invalid or malformed records are skipped
Missing fields are ignored safely
How to Run
Compile
javac -classpath `hadoop classpath` -d . *.java
jar -cvf task14.jar *
Run Hadoop Job
hadoop jar task14.jar Task14Driver \
/user/cloudera/project/task14/input \
/user/cloudera/project/task14/output
HDFS Commands
Upload Input Data
hdfs dfs -put students.csv /user/cloudera/project/task14/input
Key Concepts Demonstrated
Custom Partitioner in Hadoop
Key-based Data Distribution
Reducer-level Parallelism Control
Educational Data Analytics
Large-scale Batch Processing
Hadoop MapReduce Design Patterns
Author
