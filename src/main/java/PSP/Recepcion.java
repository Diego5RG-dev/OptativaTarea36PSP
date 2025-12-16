package PSP;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class Recepcion {

    public static void main(String[] args) {

        Properties props = new Properties();

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return null;
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);

            try {
                msg.setFrom(new InternetAddress("prueba@java.com"));
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("destino@cualquiera.com"));
                msg.setSubject("prueba mailtrap");
                msg.setText("Hola si vrs esto funciona");

                Transport.send(msg);
                System.out.println("ENviado");
            } catch (MessagingException e) {
                // Captura la excepción de la mensajería
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
