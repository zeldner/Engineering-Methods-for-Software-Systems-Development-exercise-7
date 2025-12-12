// Ilya Zeldner
class SMSService implements MessageService {
    private String phoneNumber;

    public SMSService(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        System.out.println("SMSService configured for number: " + phoneNumber);
    }
    
    public void sendMessage(String message) 
    {
    	System.out.println("Sending SMS to " + phoneNumber + ": " + message);
    }
}