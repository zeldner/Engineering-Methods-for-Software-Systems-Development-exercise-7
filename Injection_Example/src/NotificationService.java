// Ilya Zeldner
class NotificationService {
    private MessageService messageService;
    private String defaultSender;
    // --- Constructor Injection ---
    // The MessageService dependency is injected through the constructor.
    // The 'defaultSender' is a regular constructor parameter, not a dependency being injected in the same way.
    public NotificationService(MessageService messageService, String defaultSender) {
        System.out.println("NotificationService: Constructor Injection in progress...");
        if (messageService == null) {
            throw new IllegalArgumentException("MessageService cannot be null for constructor injection!");
        }
        this.messageService = messageService;
        this.defaultSender = defaultSender;
        System.out.println("NotificationService: Constructor Injection complete. Service is ready.");
    }
    // --- Property (Setter) Injection ---
    // Default constructor for cases where setter injection will be used.
    public NotificationService() {
        System.out.println("NotificationService: Instance created with no-arg constructor (for Setter Injection).");
        // Service is not yet usable if it relies on messageService
    }
    // Setter method for injecting the MessageService dependency.
    public void setMessageService(MessageService messageService) {
        System.out.println("NotificationService: Setter Injection for MessageService in progress...");
        if (messageService == null) {
            throw new IllegalArgumentException("MessageService cannot be null for setter injection!");
        }
        this.messageService = messageService;
        System.out.println("NotificationService: Setter Injection for MessageService complete.");
    }
    // Setter method for a regular property (not a dependency in the DI sense here, but shows a typical setter)
    public void setDefaultSender(String defaultSender) {
        System.out.println("NotificationService: Setting defaultSender via setter to: " + defaultSender);
        this.defaultSender = defaultSender;
    }
    public void sendNotification(String message) {
        if (this.messageService == null) {
            System.out.println("Error: MessageService not injected. Cannot send notification.");
            return;
        }
        if (this.defaultSender == null || this.defaultSender.isEmpty()){
        	System.out.println("Warning: Default sender not set.");
        	this.messageService.sendMessage(message);} 
        else {this.messageService.sendMessage("[" + defaultSender + "] " + message);}}}
