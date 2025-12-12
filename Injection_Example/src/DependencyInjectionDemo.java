// Ilya Zeldner
public class DependencyInjectionDemo {
    public static void main(String[] args) {
        System.out.println("--- Simulating Constructor Injection ---");

        // Create the dependency instance
        MessageService emailServiceForConstructor = new EmailService("smtp.example.com");

        // Inject the dependency via the constructor
        NotificationService constructorInjectedService = new NotificationService(emailServiceForConstructor, "AdminViaConstructor");
        constructorInjectedService.sendNotification("Hello via Constructor Injection!");

        System.out.println("\n--- Simulating Property (Setter) Injection ---");

        // Create another dependency instance (could be the same or different)
        MessageService smsServiceForSetter = new SMSService("+1234567890");

        // Create the dependent object using its default constructor
        NotificationService setterInjectedService = new NotificationService();

        // At this point, if we try to use it, it might fail or not work as expected
        // if the dependency is crucial for all operations.
        System.out.println("Attempting to use service before setter injection (if applicable):");
        setterInjectedService.sendNotification("A pre-injection test message."); // Will show error

        // Inject the dependency using the setter method
        setterInjectedService.setMessageService(smsServiceForSetter);
        setterInjectedService.setDefaultSender("AdminViaSetter"); // Also setting another property via setter

        // Now the service is ready to be used
        setterInjectedService.sendNotification("Hello via Setter Injection!");

        // We can also change the dependency later if the design allows (though this can make things complex)
        System.out.println("\n--- Simulating changing dependency via Setter Injection ---");
        MessageService anotherEmailService = new EmailService("mail.another-provider.net");
        setterInjectedService.setMessageService(anotherEmailService);
        setterInjectedService.setDefaultSender("NewAdminViaSetter");
        setterInjectedService.sendNotification("Hello again with a different service via Setter Injection!");
    }
}