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
 * 点对点模型  接收者（消息消费者）3
 * 
 * 这个消息消费者在创建事物管理时，用的是参数false，CLIENT_ACKNOWLEDGE
 * false表示不利用事物，这样在接收消息后，就不必再添加session.commit()语句
 * CLIENT_ACKONWLEDGE用于消费者端，指的是消费者端确认收货
 * 那么怎么确认收货呢，通过message.acknowledge()方法即可
 * 这种方法可以保证消息被成功处理后才会被删除
 * @author lidongyang
 *
 */
public class Receiver3 {
	public static void main(String[] args) {
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					ActiveMQConnection.DEFAULT_USER,
					ActiveMQConnection.DEFAULT_PASSWORD,
					"tcp://localhost:61616"
					);
			Connection connection = connectionFactory.createConnection();
			connection.start();
			//false表示自动提交事物，CLIENT_ACKNOWLEDGE表示消费者端确认收货，而不是自动收货
			Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
			//创建队列
			Destination destination = session.createQueue("messagequeue");
			//将消息接收者注册到队列中
			MessageConsumer consumer = session.createConsumer(destination);
			while (true) {
				ObjectMessage message = (ObjectMessage)consumer.receive(1000);
				if(null != message){
					String messagestr = (String)message.getObject();
					System.out.println(messagestr);
					//确认收到消息，只有执行了这句话，mq才会删除队列中的消息
					message.acknowledge();
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
