package com.hackathon.pronto;

public interface Config {

	
	// CONSTANTS
	static final String YOUR_SERVER_URL =  "http://www.mailivy.com/discountapp/register.php";
	// YOUR_SERVER_URL : Server url where you have placed your server files
    // Google project id
    static final String GOOGLE_SENDER_ID = "99287698128";  // Place here your Google project id

    /**
     * Tag used on log messages.
     */
    static final String TAG = "Pronto";

    static final String DISPLAY_MESSAGE_ACTION =
            "com.hackathon.pronto.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";
		
	
}
