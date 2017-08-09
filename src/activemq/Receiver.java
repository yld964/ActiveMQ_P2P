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
 * ��Ե�ģ��  �����ߣ���Ϣ�����ߣ�1
 * 
 * �����Ϣ�������ڴ����������ʱ���õ��ǲ���true��AUTO_ACKNOWLEDGE
 * �����������������һ������Ϊtrue�������Ĳ�����ʵ�Ǳ����Եģ�
 * �����һ��������false�������Ĳ���������ѡ�񣬳��õ�������Session.AUTO_ACKNOWLEDGE��Session.CLIENT_ACKNOWLEDGE
 * true��ʾ������������ڽ�����Ϣ�󣬱���session.commit()��䣬����mq����Ϊ���յ�����Ϣ���Ͳ���ɾ�������������Ϣ
 * AUTO_ACKNOWLEDGE��ʾ�Զ��ջ���ֻҪ���յ���Ϣ��mq����Ϊ�㴦��ɹ���ɾ�������������Ϣ
 * @author lidongyang
 *
 */
public class Receiver {
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
			//��������
			Destination destination = session.createQueue("messagequeue");
			//����Ϣ������ע�ᵽ������
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
			session.commit();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
