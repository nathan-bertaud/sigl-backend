package com.sigl.sigl.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sigl.sigl.dto.NotificationRequest;
import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.model.Notification;
import com.sigl.sigl.model.User;
import com.sigl.sigl.repository.ApprenticeRepository;
import com.sigl.sigl.repository.NotificationRepository;
import com.sigl.sigl.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class NotificationService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ApprenticeRepository apprenticeRepository;

  @Autowired
  private NotificationRepository notificationRepository;

  public ResponseEntity<List<Notification>> getNotifications(String email) {
    User user = userRepository.findByEmail(email).orElse(new User());
    Optional<List<Notification>> notifications = this.notificationRepository.findByUserId(user.getId());
    if (notifications.isPresent()) {
      return ResponseEntity.ok(notifications.get());
    } else {
      throw new ResourceNotFoundException();
    }
  }

  public ResponseEntity<Void> setNotification(NotificationRequest notificationRequest) {
    Notification notification = new Notification();
    notification.setUserId(notificationRequest.getId());
    notification.setContent(notificationRequest.getContent());
    notification.setDate(notificationRequest.getDate());
    notification.setAuthor(notificationRequest.getAuthor());
    this.notificationRepository.save(notification);
    return ResponseEntity.noContent().build();
  }

  public ResponseEntity<Void> removeNotification(Long id) {
    this.notificationRepository.deleteNotificationById(id);
    return ResponseEntity.noContent().build();
  }

  public ResponseEntity<Void> allApprenticeSetNotification(NotificationRequest notificationRequest) {
    List<Apprentice> list = this.apprenticeRepository.findAll();
    for (Apprentice apprentice : list) {
      Notification notification = new Notification();
      notification.setUserId(notificationRequest.getId());
      notification.setContent(notificationRequest.getContent());
      notification.setDate(notificationRequest.getDate());
      notification.setAuthor(notificationRequest.getAuthor());
      notification.setUserId(apprentice.getId());
      this.notificationRepository.save(notification);
    }
    return ResponseEntity.noContent().build();
  }
}
