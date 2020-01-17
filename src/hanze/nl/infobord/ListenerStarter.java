package hanze.nl.infobord;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public  class ListenerStarter implements Runnable, ExceptionListener {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static String subject = "halteTopic";
    private String selector = "";
    private Connection connection;
    private Session session;

    public ListenerStarter(String selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        System.out.println("Running ListenerStarter");
        try {
            System.out.println("Open Connection?");
            openConnection();
            System.out.println("CONNECTION OPEN");
            System.out.println("Receive messages?");
            receiveMessages();
            System.out.println("RECEIVING MESSAGES");
        } catch (JMSException e) {
            onException(e);
        }
    }

    @Override
    public void onException(JMSException e) {
        e.printStackTrace();
    }

    private void receiveMessages() throws JMSException {
        // Getting the queue 'TESTQUEUE'
        Topic topic = session.createTopic(subject);

        // MessageConsumer is used for receiving (consuming) messages
        MessageConsumer subscriber = session.createConsumer(topic, selector);
        String messageSelector = subscriber.getMessageSelector();
        System.out.println(messageSelector);

        subscriber.setMessageListener(new QueueListener());
    }

    private void openConnection() throws JMSException {
        // Getting JMS connection from the server
        ConnectionFactory connectionFactory
                = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.start();

        // Creating session for sending messages
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
//TODO	Implementeer de starter voor de messagelistener:
//		Zet de verbinding op met de messagebroker en start de listener met 
//		de juiste selector.

}