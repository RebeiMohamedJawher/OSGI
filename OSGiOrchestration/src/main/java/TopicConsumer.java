import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicConsumer implements Runnable {
	//connection Factory witch will help in connection to ActiveMQ server
		ActiveMQConnectionFactory connectionFactory = null;
		
		public TopicConsumer(ActiveMQConnectionFactory connectionFactory) {
			this.connectionFactory = connectionFactory;
			
		}
		
		public void run() {
			try {
				//first create a connection
				Connection connection = connectionFactory.createConnection();
				connection.start();
				
				//Create a session
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				
				//Create a topic.if the topic exist it will return that
				Destination topicDestination = session.createTopic("JMS Broker");
				
				//Create a messageProducer from the session to the Topic or queue
				MessageConsumer messageConsumer = session.createConsumer(topicDestination);
			
				
				
				//get the message 
				Message message = messageConsumer.receive();
				
				TextMessage textMessage = (TextMessage) message;
				
				System.out.println(textMessage.getText());
				
				//Do the cleanUp
				session.close();
				connection.close();
				
			} catch (JMSException jmse) {
				System.out.println("Exception: " + jmse.getMessage());
			}
		}
		

	}



