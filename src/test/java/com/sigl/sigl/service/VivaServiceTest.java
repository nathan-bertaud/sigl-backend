package com.sigl.sigl.service;

import com.sigl.sigl.dto.UserNameFirstNameDto;
import com.sigl.sigl.model.Coordinator;
import com.sigl.sigl.model.User;
import com.sigl.sigl.model.Viva;
import com.sigl.sigl.repository.CoordinatorRepository;
import com.sigl.sigl.repository.UserRepository;
import com.sigl.sigl.repository.VivaRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

public class VivaServiceTest {

    @Mock
    private VivaRepository vivaRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CoordinatorRepository coordinatorRepository;

    @InjectMocks
    private VivaService vivaService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddJuriesSuccess(){
        Coordinator coordinator = new Coordinator();
        coordinator.setEmail("test@email.com");
        UserNameFirstNameDto userNameFirstNameDto1 = new UserNameFirstNameDto(1L,"name1","firstname1");
        UserNameFirstNameDto userNameFirstNameDto2 = new UserNameFirstNameDto(2L,"name2","firstname2");
        UserNameFirstNameDto userNameFirstNameDto3 = new UserNameFirstNameDto(3L,"name3","firstname3");
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setName(userNameFirstNameDto1.getName());
        user1.setFirstName(userNameFirstNameDto1.getFirstName());
        user2.setName(userNameFirstNameDto2.getName());
        user2.setFirstName(userNameFirstNameDto2.getFirstName());
        user3.setName(userNameFirstNameDto3.getName());
        user3.setFirstName(userNameFirstNameDto3.getFirstName());
        List<UserNameFirstNameDto> userNameFirstNameDtoList = new ArrayList<>();
        userNameFirstNameDtoList.add(userNameFirstNameDto1);
        userNameFirstNameDtoList.add(userNameFirstNameDto2);
        userNameFirstNameDtoList.add(userNameFirstNameDto3);
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        Viva viva = new Viva();
        viva.setJuries(userList);

        Mockito.when(
                this.coordinatorRepository.findByEmail(coordinator.getEmail())
        ).thenReturn(
                Optional.of(coordinator)
        );

        Mockito.when(
            this.userRepository.findByNameAndFirstName(userNameFirstNameDto1.getName(),userNameFirstNameDto1.getFirstName())
        ).thenReturn(
                user1
        );

        Mockito.when(
                this.userRepository.findByNameAndFirstName(userNameFirstNameDto2.getName(),userNameFirstNameDto2.getFirstName())
        ).thenReturn(
                user2
        );

        Mockito.when(
                this.userRepository.findByNameAndFirstName(userNameFirstNameDto3.getName(),userNameFirstNameDto3.getFirstName())
        ).thenReturn(
                user3
        );

        //Traitement
        this.vivaService.addJuries(coordinator.getEmail(), userNameFirstNameDtoList);

        //Verification
        Mockito.verify(this.coordinatorRepository, times(1)).findByEmail(coordinator.getEmail());
        Mockito.verifyNoMoreInteractions(this.coordinatorRepository);
        Mockito.verify(this.userRepository, times(1)).findByNameAndFirstName(userNameFirstNameDto1.getName(),userNameFirstNameDto1.getFirstName());
        Mockito.verify(this.userRepository, times(1)).findByNameAndFirstName(userNameFirstNameDto2.getName(),userNameFirstNameDto2.getFirstName());
        Mockito.verify(this.userRepository, times(1)).findByNameAndFirstName(userNameFirstNameDto3.getName(),userNameFirstNameDto3.getFirstName());
        Mockito.verifyNoMoreInteractions(this.userRepository);
        Mockito.verify(this.vivaRepository, times(1)).save(viva);
        Mockito.verifyNoMoreInteractions(this.vivaRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testAddJuriesFailNoCoordinator(){
        Coordinator coordinator = new Coordinator();
        coordinator.setEmail("test@email.com");

        Mockito.when(
                this.coordinatorRepository.findByEmail(coordinator.getEmail())
        ).thenReturn(
                Optional.empty()
        );

        //Traitement
        this.vivaService.addJuries(coordinator.getEmail(), null);

        //Verification
        Mockito.verify(this.coordinatorRepository, times(1)).findByEmail(coordinator.getEmail());
        Mockito.verifyNoMoreInteractions(this.coordinatorRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testAddJuriesFailNotEnoughJury(){
        Coordinator coordinator = new Coordinator();
        coordinator.setEmail("test@email.com");
        UserNameFirstNameDto userNameFirstNameDto1 = new UserNameFirstNameDto(1L,"name1","firstname1");
        UserNameFirstNameDto userNameFirstNameDto2 = new UserNameFirstNameDto(2L,"name2","firstname2");
        User user1 = new User();
        User user2 = new User();
        user1.setName(userNameFirstNameDto1.getName());
        user1.setFirstName(userNameFirstNameDto1.getFirstName());
        user2.setName(userNameFirstNameDto2.getName());
        user2.setFirstName(userNameFirstNameDto2.getFirstName());
        List<UserNameFirstNameDto> userNameFirstNameDtoList = new ArrayList<>();
        userNameFirstNameDtoList.add(userNameFirstNameDto1);
        userNameFirstNameDtoList.add(userNameFirstNameDto2);
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        Viva viva = new Viva();
        viva.setJuries(userList);

        Mockito.when(
                this.coordinatorRepository.findByEmail(coordinator.getEmail())
        ).thenReturn(
                Optional.of(coordinator)
        );

        Mockito.when(
                this.userRepository.findByNameAndFirstName(userNameFirstNameDto1.getName(),userNameFirstNameDto1.getFirstName())
        ).thenReturn(
                user1
        );

        Mockito.when(
                this.userRepository.findByNameAndFirstName(userNameFirstNameDto2.getName(),userNameFirstNameDto2.getFirstName())
        ).thenReturn(
                user2
        );

        //Traitement
        this.vivaService.addJuries(coordinator.getEmail(), userNameFirstNameDtoList);

        //Verification
        Mockito.verify(this.coordinatorRepository, times(1)).findByEmail(coordinator.getEmail());
        Mockito.verifyNoMoreInteractions(this.coordinatorRepository);
        Mockito.verify(this.userRepository, times(1)).findByNameAndFirstName(userNameFirstNameDto1.getName(),userNameFirstNameDto1.getFirstName());
        Mockito.verify(this.userRepository, times(1)).findByNameAndFirstName(userNameFirstNameDto2.getName(),userNameFirstNameDto2.getFirstName());
        Mockito.verifyNoMoreInteractions(this.userRepository);
    }
}
