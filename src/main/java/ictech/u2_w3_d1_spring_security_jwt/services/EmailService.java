package ictech.u2_w3_d1_spring_security_jwt.services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import ictech.u2_w3_d1_spring_security_jwt.entities.Booking;
import ictech.u2_w3_d1_spring_security_jwt.entities.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private SendGrid sendGrid;

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${sendgrid.sender.email}")
    private String sender;

    public void sendBookingConfirmationEmail(Booking booking) {
        Employee employee = booking.getEmployee();

        Email from = new Email(sender, "Corporate Travel System");
        String subject = "Booking Confirmation: " + booking.getTrip().getDestination();
        Email to = new Email(employee.getEmail());  // I'll use an employee with my verified email for the test.
        String emailContent = String.format(
                // Concatenation can be replaced with text block - thanks IntelliJ for this help 🤝😁
                """
                        Hi %s,
                        
                        Your business trip has been successfully booked.
                        
                        Booking Details:
                        - Booking ID: %s
                        - Destination: %s
                        - Trip Date: %s
                        
                        Notes: %s
                        
                        Thank you,
                        Corporate Travel Management""",
                employee.getFirstName(),
                // Better use a booking number which is shorter, so we could add an attribute in the Booking class:
                // (e.g. bookingNumber and then use booking.getBookingNumber() instead of booking.getId())
                booking.getId(),
                booking.getTrip().getDestination(),
                booking.getTrip().getTripDate().toString(),
                // Although 'notes' are currently mandatory, this ternary operator handles the case where 'notes' might be optional in the future.
                booking.getNotes() != null ? booking.getNotes() : "No notes provided."
        );
        Content content = new Content("text/plain", emailContent);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(this.sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (
                IOException ex) {
            System.out.println("Error sending email" + ex.getMessage());
        }
    }
}
