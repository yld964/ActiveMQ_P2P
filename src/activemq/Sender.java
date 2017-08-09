package activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
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
 * true��ʾ������������ڽ�����Ϣ�󣬱���session.commit()��䣬����mq����Ϊ�㷢������Ϣ
 * AUTO_ACKNOWLEDGE��ʾ�Զ��ջ�
 * @author lidongyang
 *
 */
public class Sender {
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
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			for(int i = 0;i<3;i++){
				ObjectMessage message = session.createObjectMessage("hello " + (i+1));
				producer.send(message);
				System.out.println("�Һ�" + (i+1) + "�����к�"); 
			}
			session.commit();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
