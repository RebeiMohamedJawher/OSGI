import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMQMain {
	
	
	public static void main(String[] args) throws JMSException {
		
		//Create the connection Factory
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://localhost:61616");
		
		
		//Create the consumer.it will wait the listen to the topic
		Thread topicConsumerThread = new Thread(new TopicConsumer (connectionFactory));
		topicConsumerThread.start();
		
		try {
			Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		
		//create a message.as soon as the message is published on the topic, it will be consumed by the consumer
		Thread topicProducerThread = new Thread(new TopicProducer (connectionFactory));
		topicProducerThread.start();

					
		

	}

}
