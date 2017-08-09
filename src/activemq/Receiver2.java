package activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * 点对点模型  接收者（消息消费者）2
 * 
 * 这个消息消费者在创建事物管理时，用的是参数true，AUTO_ACKNOWLEDGE
 * true表示利用事物，这样在接收消息后，必须session.commit()语句，否则mq不认为你收到了消息，就不会删除掉队列里的消息
 * 此例当中，用了true参数，却没有执行session.commit()语句，实验表明，队列里的消息不会被删除
 * AUTO_ACKNOWLEDGE表示自动收货，只要你收到消息，mq就认为你处理成功，删除掉队列里的消息
 * @author lidongyang
 *
 */
public class Receiver2 {
	public static void main(String[] args) {
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					ActiveMQConnection.DEFAULT_USER,
					ActiveMQConnection.DEFAULT_PASSWORD,
					"tcp://localhost:61616"
					);
			Connection connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			//创建队列
			Destination destination = session.createQueue("messagequeue");
			//将消息接收者注册到队列中
			MessageConsumer consumer = session.createConsumer(destination);
			while (true) {
				ObjectMessage message = (ObjectMessage)consumer.receive(1000);
				if(null != message){
					String messagestr = (String)message.getObject();
					System.out.println(messagestr);
				}else{
					break;
				}
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
