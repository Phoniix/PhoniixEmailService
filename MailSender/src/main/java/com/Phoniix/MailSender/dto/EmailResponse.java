package com.Phoniix.MailSender.dto;

public class EmailResponse {
    // Variable Declarations ---------------------------------------------------------------------
    private boolean success;
    private String message;
    private String timestamp;

    //Default Constructor -----------------------------------------------------------------------
    public EmailResponse () {}

    // Constructor with params ------------------------------------------------------------------
    public EmailResponse (boolean success, String message) {
        this.success = success;
        this.message = message;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    // Methods For Calculating Error/Success ----------------------------------------------------
    public static EmailResponse success (String message) {
        return new EmailResponse(true, message);
    }

    public static EmailResponse error (String message) {
        return new EmailResponse(false, message);
    }

    // Getters and Setters ----------------------------------------------------------------------
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "EmailResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}