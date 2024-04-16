package com.sigl.sigl.service;

import com.sigl.sigl.dto.NotificationRequest;
import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.model.Notification;
import com.sigl.sigl.model.User;
import com.sigl.sigl.repository.ApprenticeRepository;
import com.sigl.sigl.repository.NotificationRepository;
import com.sigl.sigl.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;

import org.mockito.MockitoAnnotations;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApprenticeRepository apprenticeRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Before
    public void setUp() { MockitoAnnotations.initMocks(this); }

    @Test
    public void testGetNotificationsSuccess(){
        //Init
        User user = new User();
        user.setEmail("test@email.com");
        user.setId(189L);
        Notification notification = new Notification();
        notification.setAuthor("AuthorTest");
        notification.setContent("test");
        List<Notification> notifications = new ArrayList<>();
        notifications.add(notification);
        Optional <List<Notification>> notificationsOptional = Optional.of(notifications);

        //Mock
        Mockito.when(
                this.userRepository.findByEmail(user.getEmail())
        ).thenReturn(
                Optional.of(user)
        );

        Mockito.when(
                this.notificationRepository.findByUserId(user.getId())
        ).thenReturn(
                notificationsOptional
        );

        //Traitement
        ResponseEntity<List<Notification>> notificationsTraitement = this.notificationService.getNotifications(user.getEmail());

        //Vérification
        Assert.assertEquals("La réponse n'est pas bonne !",notificationsTraitement,ResponseEntity.ok(notificationsOptional.get()));
        Mockito.verify(this.userRepository,times(1)).findByEmail(user.getEmail());
        Mockito.verify(this.notificationRepository,times(1)).findByUserId(user.getId());
        Mockito.verifyNoMoreInteractions(this.userRepository);
        Mockito.verifyNoMoreInteractions(this.notificationRepository);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetNotificationsFailResourceNotFound(){
        //Init
        User user = new User();
        user.setEmail("test@email.com");
        user.setId(189L);

        //Mock
        Mockito.when(
                this.userRepository.findByEmail(user.getEmail())
        ).thenReturn(
                Optional.of(user)
        );

        Mockito.when(
                this.notificationRepository.findByUserId(user.getId())
        ).thenReturn(
                Optional.empty()
        );

        //Traitement
        ResponseEntity<List<Notification>> notificationsTraitement = this.notificationService.getNotifications(user.getEmail());

        //Vérification
        Mockito.verify(this.userRepository,times(1)).findByEmail(user.getEmail());
        Mockito.verify(this.notificationRepository,times(1)).findByUserId(user.getId());
        Mockito.verifyNoMoreInteractions(this.userRepository);
        Mockito.verifyNoMoreInteractions(this.notificationRepository);
    }

    @Test
    public void testSetNotificationSuccess(){
        //Init
        Date now = new Date();
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setAuthor("testAuthor");
        notificationRequest.setContent("testContent");
        notificationRequest.setDate(now);

        //Traitement
        ResponseEntity<Void> answerNotif = this.notificationService.setNotification(notificationRequest);

        //Verification
        Assert.assertTrue("Les réponses sont différentes",ResponseEntity.noContent().build().equals(answerNotif));
    }

    @Test
    public void testallApprenticeSetNotificationSuccess(){
        //Init
        Date now = new Date();
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setAuthor("testAuthor");
        notificationRequest.setContent("testContent");
        notificationRequest.setDate(now);

        Apprentice apprentice = new Apprentice();
        apprentice.setId(150L);

        List<Apprentice> apprenticeList = new ArrayList<>();
        apprenticeList.add(apprentice);

        Mockito.when(
                this.apprenticeRepository.findAll()
        ).thenReturn(
                apprenticeList
        );

        //Traitement
        ResponseEntity<Void> responseEntity = this.notificationService.allApprenticeSetNotification(notificationRequest);

        //Verification
        Assert.assertTrue("La reponse n'est pas celle attendue", ResponseEntity.noContent().build().equals(responseEntity));
        Mockito.verify(this.apprenticeRepository, times(1)).findAll();
        Mockito.verifyNoMoreInteractions(this.apprenticeRepository);
    }
}
