package hanze.nl.infobord;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import hanze.nl.bussimulator.Bericht;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class QueueListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("A MESSAGE HAS BEEN RECEIVED");
        System.out.println(message.toString());
//        InfoBord infoBord = new InfoBord();
//        infoBord.verwerkBericht(message.toString());
//        try {
//            XStream xstream = new XStream(new StaxDriver());
//            xstream.alias("Bericht", JSONBericht.class);
//            if (message instanceof TextMessage) {
//                TextMessage msg = (TextMessage)message;
//                Bericht p2 = null;
//                p2 = (Bericht)xstream.fromXML(msg.getText());
//                System.out.println(p2);
//            } else {
//                System.out.println("Not text");
//            }
//        } catch (JMSException e) {
//            System.out.println("AYOOOO");
//            e.printStackTrace();
//        }
        try {
            String JSONBericht = ((TextMessage)message).getText();
            InfoBord bord = InfoBord.getInfoBord();
            InfoBord.verwerkBericht(JSONBericht);
            bord.setRegels();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
//TODO 	implementeer de messagelistener die het bericht ophaald en
//		doorstuurd naar verwerkBericht van het infoBord.
//		Ook moet setRegels aangeroepen worden.
}

