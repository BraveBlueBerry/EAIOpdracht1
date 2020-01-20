package hanze.nl.bussimulator;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.thoughtworks.xstream.XStream;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private Connection connection;
    private Session session;
    private String queueName = "BUSMESSAGEQUEUE";

    //TODO maak de verbinding met de message broker en verstuur het bericht.
//    public static void main(String[] args) {
//        Producer producer = new Producer();
//        producer.run();
//    }

    public void startSendingMessage(Bericht bericht) {
        try {
            createConnection();
            sendMessage(bericht);
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendBericht(String berichtRaw) {
        try {
            createConnection();
            sendTextMessage(berichtRaw);
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Bericht bericht) throws JMSException {
        XStream xstream = new XStream();
        xstream.alias("Bericht", Bericht.class);
        xstream.alias("ETA", ETA.class);
        String xml = xstream.toXML(bericht);
        sendTextMessage(xml);
    }

    private void sendTextMessage(String xml) throws JMSException {
        System.out.println("Producer starting message: " + new Date());

        Destination destination = session.createQueue(queueName);
        MessageProducer producer = session.createProducer(destination);
        TextMessage msg = session.createTextMessage(xml);

        // Here we are sending the message!
        producer.send(msg);
        System.out.println("Sent message '" + msg.getText() + "'");
    }

    private void createConnection() throws JMSException {
        // Getting JMS connection from the server and starting it
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.start();

        session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
    }

}
