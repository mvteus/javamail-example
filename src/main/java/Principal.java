import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class Principal {

    private final String username = "";
    private final String password = "";

    public Session getSession() {
        /*
         * Primeiro precisamos configurar as propriedades do provedor de email que usaremos.
         * Neste exemplo, usarei o GMail.
         * */
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.debug", "true"); /* propriedade debug para verificar o que está acontecendo por trás dos panos */

        /*
         * É necessário criar um MAIL SESSION OBJECT com as credenciais de acesso.
         * */
        return Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void enviarMail(Session session) throws MessagingException {
        /*
         * Precisamos agora criar um MIMEMESSAGE OBJECT para o envio.
         * */
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("test@gmail.com"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("test@bol.com.br"));
        msg.setSubject("Exemplo de E-Mail enviado por Java");

        String conteudo = "Meu primeiro e-mail usando o Java usando a implementação Angus Mail.";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(conteudo, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        msg.setContent(multipart);

        Transport.send(msg); /* Envio do e-mail */
    }

    public static void main(String[] args) {
        Principal javaMail = new Principal();
        try {
            javaMail.enviarMail(javaMail.getSession());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
