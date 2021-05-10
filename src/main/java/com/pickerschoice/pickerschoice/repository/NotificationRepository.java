package com.pickerschoice.pickerschoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pickerschoice.pickerschoice.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer>  {

}
