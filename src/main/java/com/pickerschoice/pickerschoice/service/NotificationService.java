package com.pickerschoice.pickerschoice.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.pickerschoice.pickerschoice.model.Notification;
import com.pickerschoice.pickerschoice.repository.NotificationRepository;
import com.pickerschoice.pickerschoice.security.AppUserRole;

@Service
public class NotificationService {
	private static final String NEW_ORDER = "%S placed a new order";
	private static final String ORDER_PROCESSED = "Your order %s is being processed";
	private static final String ORDER_DELIVERED = "Your Order %s has been delivered";
	private static final String NEW_REGISTRATION = "%s %s just created an account";
	
	private NotificationRepository notificationRepository;

	public NotificationService(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}
	
	/* SAVE NOTIFICATION */
	public Notification saveNotification(AppUserRole recipient, String message ) {
		Notification notification = new Notification(null, message, LocalDateTime.now(), false, recipient);
		return null;
	}
}
