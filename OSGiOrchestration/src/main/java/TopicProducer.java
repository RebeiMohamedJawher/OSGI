import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicProducer implements Runnable {
	//connection Factory witch will help in connection to ActiveMQ server
	ActiveMQConnectionFactory connectionFactory = null;
	
	public TopicProducer(ActiveMQConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
		
	}
	
	public void run() {
		try {
			//first create a connection
			Connection connection = connectionFactory.createConnection();
			connection.start();
			
			//Create a session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			//Create a topic.if the topic exist
			//it will return that
			Destination destination = session.createTopic("JMS Broker");
			
			//Create a messageProducer from the session to the Topic or queue
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			
			//Create a Message for the current topic
			String text = "Complete JMS Expt";
			TextMessage message = session.createTextMessage(text);
			
			//send the message to the topic
			producer.send(message);
			
			
			//Do the cleanUp
			session.close();
			connection.close();
			
		} catch (JMSException jmse) {
			System.out.println("Exception: " + jmse.getMessage());
		}
	}
	

}
