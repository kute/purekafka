1. 启动 zookeeper 服务
bin/zookeeper-server-start.sh config/zookeeper.properties

2. 启动 kafka server (broker)
bin/kafka-server-start.sh config/server.properties

3. 创建1个备份1个分区的topic
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

配置: http://kafka.apache.org/documentation/#topic-config

3.1 修改topic 
bin/kafka-topics.sh --zookeeper localhost:port/chroot --alter --topic my_topic_name --partitions 40

3.2 删除topic 
bin/kafka-topics.sh --zookeeper zk_host:port/chroot --delete --topic my_topic_name

4. 查看已创建的topic
bin/kafka-topics.sh --list --zookeeper localhost:2181

5. 查看集群下某个topic的服务情况
bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic my-topic

6. 启动 producer 
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test

配置:http://kafka.apache.org/documentation/#producerconfigs

7. 启动 consumer
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning

配置:http://kafka.apache.org/documentation/#consumerconfigs

