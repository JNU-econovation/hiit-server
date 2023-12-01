package com.hiit.api.notification.builder;

import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FireBaseMessageBuilder {
	public static MulticastMessage makeMulticastMessage(
			List<String> tokens, String title, String contents) {
		Notification notification = Notification.builder().setTitle(title).setBody(contents).build();
		return MulticastMessage.builder().addAllTokens(tokens).setNotification(notification).build();
	}
}
