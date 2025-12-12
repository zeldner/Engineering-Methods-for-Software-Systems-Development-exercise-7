// Ilya Zeldner
class EmailService implements MessageService {
    private String emailHost;
    // This constructor itself could receive its own dependencies if needed
    public EmailService(String emailHost) {
        this.emailHost = emailHost;
        System.out.println("EmailService configured with host: " + emailHost);
    }
    
    public void sendMessage(String message) 
    {
    	System.out.println("Sending email via " + emailHost + ": " + message);
    }
}
