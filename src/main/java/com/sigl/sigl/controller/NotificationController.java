package com.sigl.sigl.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sigl.sigl.dto.NotificationRequest;
import com.sigl.sigl.model.Notification;
import com.sigl.sigl.service.NotificationService;

@RestController()
public class NotificationController {

  @Autowired
  private NotificationService notificationService;

  @GetMapping("notifications/get")
  public ResponseEntity<List<Notification>> getNotifications(Principal auth) {
    return this.notificationService.getNotifications(auth.getName());
  }

  @PostMapping("notifications/add")
  public ResponseEntity<Void> newNotification(@RequestBody NotificationRequest notificationRequest) {
    return this.notificationService.setNotification(notificationRequest);
  }

  @PostMapping("notifications/addAllApprentice")
  public ResponseEntity<Void> allApprenticeNotification(@RequestBody NotificationRequest notificationRequest) {
    return this.notificationService.allApprenticeSetNotification(notificationRequest);
  }

  @PostMapping("notifications/delete/{id}")
  public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
    return this.notificationService.removeNotification(id);
  }
}
