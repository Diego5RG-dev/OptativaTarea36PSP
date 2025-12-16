package PSP;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class Recepcion {

    public static void main(String[] args) {

        Properties props = new Properties();

        //Configuracion

        props.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        props.put("mail.smtp.port", "2525");

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        //recepcion
        props.put("mail.imap.host", "inbox.mailtrap.io");
        props.put("mail.imap.port", "993");
        props.put("mail.imap.ssl.enable", "true");


        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return null;
            }
        });

        //conectar al Store
        try {
            Store store= session.getStore("imap");
            store.connect("tu_usuario_mailtrap", "tu_pass_mailtrap");

            //abrir inbox y leer
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            for (Message m : messages){
                System.out.println("Asunto: " + m.getSubject());
            }

        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
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
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
