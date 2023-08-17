#!/bin/bash
kafka-topics --create --topic test-local-allocation-topic --partitions 1 --replication-factor 1 --bootstrap-server localhost:9092

