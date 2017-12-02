package com.mycompany.core.framework.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
 
import javax.mail.Transport;
 
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 * This class constructs and sends out an email message.
 *
 * @author  UST-TACOE
 * @version 1.0
 */
public final class EmailUtils
{
    //~ Instance variables ---------------------------------------------------------------

    private String bcc;
    private String cc;
    private String from; 
    private String mailhost; 
    public String subject;
    private String text;
    private String to; 
    @SuppressWarnings("unused")
	private Boolean flag;
    

	/**
     * Constructs an instance of Mailer.
     * @return 
     */
    public void setEmailFlagAndServer(Boolean emailflag,String smtpServer)
    {
    	this.flag = emailflag;
        this.mailhost = smtpServer;

        if ((to == null) || to.equals(""))
        {
            flag = false;
        }

       
    }
    
    
    
    
    /**
     * 
     * @param fromlist
     * @param tolist
     * @param cclist
     * @param bcclist
     */
    public void setSendList(String fromlist,String tolist,String cclist,String bcclist)
    {
    	
         from = fromlist; // Base.get("FromProject");
         cc = cclist;
         to = tolist;
         bcc = bcclist; 
         text = "";
    }
    /**
     * 
     * @param subject
     */
    public void setEmailSubject(String subject){
    	 this.subject = subject;
    	
    }
    /**
     * 
     * @param text
     */
    public void setEmailBody(String text)
    {
    	this.text = text;
    }
    //~ Methods --------------------------------------------------------------------------

    /**
     * This method will sends the mail if the required information in the
     * 
     *
     * @param file a path to the attachment (single test).
     * @param subjectSuffix subject test fail/pass/skip info.
     * @param files paths to the attachments for the suite test.
     *
     * @throws Exception
     */
    /**
     * Utility method to send simple HTML email
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    public  void sendEmail(String FilePath,String AttachmentName){
        try
        {
        	
            Properties props = System.getProperties();
            
            props.put("mail.smtp.host",  this.mailhost);
     
            Session session = Session.getInstance(props, null);

          MimeMessage msg = new MimeMessage(session);
          //set message headers
          msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
          msg.addHeader("format", "flowed");
          msg.addHeader("Content-Transfer-Encoding", "8bit");
 
          if (this.from != null)
          {
              InternetAddress addr = new InternetAddress(this.from );
              addr.setPersonal(this.from);
              msg.setFrom(addr);
          }
          else
          {
              msg.setFrom();
          }

          msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.to, false));

          if (this.cc != null)
          {
              msg.setRecipients(
                  Message.RecipientType.CC, InternetAddress.parse(this.cc, false));
          }

          if (this.bcc != null)
          {
              msg.setRecipients(
                  Message.RecipientType.BCC, InternetAddress.parse(this.bcc, false));
          }
 
          msg.setSubject(subject, "UTF-8");
          
          msg.setText(this.text, "UTF-8");
 
          msg.setSentDate(new Date());
          
          // Create the message body part
          BodyPart messageBodyPart = new MimeBodyPart();
  
          // Fill the message
          messageBodyPart.setText(this.text);
          //String text = "";
          if (FilePath != null)
          {
         	 // Create a multipart message for attachment
              Multipart multipart = new MimeMultipart();
      
              // Set text message part
              multipart.addBodyPart(messageBodyPart);
      
              // Second part is attachment
              messageBodyPart = new MimeBodyPart();
               
              DataSource source = new FileDataSource(FilePath);
              messageBodyPart.setDataHandler(new DataHandler(source));
              messageBodyPart.setFileName(AttachmentName);
              multipart.addBodyPart(messageBodyPart);
      
              // Send the complete message parts
              msg.setContent(multipart);
          }
         
 
           
          System.out.println("Message is ready");
          Transport.send(msg);  
 
          System.out.println("EMail Sent Successfully!!");
        }
        catch (Exception e) {
          e.printStackTrace();
        }
    }


    /**
     * Utility method to send email with attachment
     * @param FilePath
     * @param Files
     * @throws IOException
     */
    public  void sendAttachmentsEmail(List<String> Files) throws IOException{
        try{
        	
        		Properties props = System.getProperties();
                props.put("mail.smtp.host", this.mailhost);
	     
	            Session session = Session.getInstance(props, null);

             MimeMessage msg = new MimeMessage(session);
             msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
             msg.addHeader("format", "flowed");
             msg.addHeader("Content-Transfer-Encoding", "8bit");
               
             if (this.from != null)
             {
                 InternetAddress addr = new InternetAddress(this.from );
                 addr.setPersonal(this.from);
                 msg.setFrom(addr);
             }
             else
             {
                 msg.setFrom();
             }

             msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.to, false));

             if (this.cc != null)
             {
                 msg.setRecipients(
                     Message.RecipientType.CC, InternetAddress.parse(this.cc, false));
             }

             if (this.bcc != null)
             {
                 msg.setRecipients(
                     Message.RecipientType.BCC, InternetAddress.parse(this.bcc, false));
             }
    
     
             msg.setSubject(this.subject, "UTF-8");
     
             msg.setSentDate(new Date());
     
            
               
             // Create the message body part
             BodyPart messageBodyPart = new MimeBodyPart();
     
             // Fill the message
             messageBodyPart.setText(this.text);
              
             
             
             
            if (Files != null)
             {
                 MimeMultipart mp = new MimeMultipart();
                 MimeBodyPart mbp1 = new MimeBodyPart();
                 mbp1.setText(this.text);
                 mp.addBodyPart(mbp1);

                 for (String f : Files)
                 {
                     if (f != null)
                     {
                         MimeBodyPart mbp2 = new MimeBodyPart();
                         mbp2.attachFile(f);
                         mp.addBodyPart(mbp2);
                     }
                 }

                 msg.setContent(mp);
             }
            
     
             // Send message
             Transport.send(msg);
             System.out.println("EMail Sent Successfully with attachment!!");
          }catch (MessagingException e) {
             e.printStackTrace();
          } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
        }
    }
}
