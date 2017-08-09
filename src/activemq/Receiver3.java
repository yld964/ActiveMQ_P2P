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
 * ��Ե�ģ��  �����ߣ���Ϣ�����ߣ�3
 * 
 * �����Ϣ�������ڴ����������ʱ���õ��ǲ���false��CLIENT_ACKNOWLEDGE
 * false��ʾ��������������ڽ�����Ϣ�󣬾Ͳ��������session.commit()���
 * CLIENT_ACKONWLEDGE���������߶ˣ�ָ���������߶�ȷ���ջ�
 * ��ô��ôȷ���ջ��أ�ͨ��message.acknowledge()��������
 * ���ַ������Ա�֤��Ϣ���ɹ������Żᱻɾ��
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
			//false��ʾ�Զ��ύ���CLIENT_ACKNOWLEDGE��ʾ�����߶�ȷ���ջ����������Զ��ջ�
			Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
			//��������
			Destination destination = session.createQueue("messagequeue");
			//����Ϣ������ע�ᵽ������
			MessageConsumer consumer = session.createConsumer(destination);
			while (true) {
				ObjectMessage message = (ObjectMessage)consumer.receive(1000);
				if(null != message){
					String messagestr = (String)message.getObject();
					System.out.println(messagestr);
					//ȷ���յ���Ϣ��ֻ��ִ������仰��mq�Ż�ɾ�������е���Ϣ
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
