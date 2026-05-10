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

# Author

Name: Shahd
