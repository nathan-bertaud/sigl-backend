package com.sigl.sigl.service;


import com.sigl.sigl.model.*;
import com.sigl.sigl.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@Transactional
public class BiannualAssesmentService {
    @Autowired
    private BiAnnualAssessmentS5Repository biAnnualAssessmentS5Repository;
    @Autowired
    private BiAnnualAssessmentS6Repository biAnnualAssessmentS6Repository;
    @Autowired
    private BiAnnualAssessmentS7Repository biAnnualAssessmentS7Repository;
    @Autowired
    private BiAnnualAssessmentS8Repository biAnnualAssessmentS8Repository;
    @Autowired
    private BiAnnualAssessmentS9Repository biAnnualAssessmentS9Repository;
    @Autowired
    private BiAnnualAssessmentS10Repository biAnnualAssessmentS10Repository;

    @Autowired
    private ApprenticeService apprenticeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BiannualAssessmentS5.class);

    public void addBiannualAssesmentS5(BiannualAssessmentS5 biannualAssessmentS5, Long idApprentice){

        try {
            Optional<Apprentice> optionalApprenti = apprenticeService.getApprenticeById(idApprentice);
            Apprentice apprentice = optionalApprenti.orElse(null);

            biannualAssessmentS5.setApprentice(apprentice);
            biAnnualAssessmentS5Repository.save(biannualAssessmentS5);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de l'enregistrement de l'évaluation S5 : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de l'enregistrement de l'évaluation S5", e);
        }
    }

    public void addBiannualAssesmentS6(BiannualAssessmentS6 biannualAssessmentS6, Long idApprentice){

        try {
            Optional<Apprentice> optionalApprenti = apprenticeService.getApprenticeById(idApprentice);
            Apprentice apprentice = optionalApprenti.orElse(null);

            biannualAssessmentS6.setApprentice(apprentice);
            biAnnualAssessmentS6Repository.save(biannualAssessmentS6);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de l'enregistrement de l'évaluation S6 : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de l'enregistrement de l'évaluation S5", e);
        }
    }

    public void addBiannualAssesmentS7(BiannualAssessmentS7 biannualAssessmentS7, Long idApprentice){

        try {
            Optional<Apprentice> optionalApprenti = apprenticeService.getApprenticeById(idApprentice);
            Apprentice apprentice = optionalApprenti.orElse(null);

            biannualAssessmentS7.setApprentice(apprentice);
            biAnnualAssessmentS7Repository.save(biannualAssessmentS7);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de l'enregistrement de l'évaluation S7 : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de l'enregistrement de l'évaluation S7", e);
        }
    }

    public void addBiannualAssesmentS8(BiannualAssessmentS8 biannualAssessmentS8, Long idApprentice){

        try {
            Optional<Apprentice> optionalApprenti = apprenticeService.getApprenticeById(idApprentice);
            Apprentice apprentice = optionalApprenti.orElse(null);

            biannualAssessmentS8.setApprentice(apprentice);
            biAnnualAssessmentS8Repository.save(biannualAssessmentS8);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de l'enregistrement de l'évaluation S8 : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de l'enregistrement de l'évaluation S8", e);
        }
    }

    public void addBiannualAssesmentS9(BiannualAssessmentS9 biannualAssessmentS9, Long idApprentice){

        try {
            Optional<Apprentice> optionalApprenti = apprenticeService.getApprenticeById(idApprentice);
            Apprentice apprentice = optionalApprenti.orElse(null);

            biannualAssessmentS9.setApprentice(apprentice);
            biAnnualAssessmentS9Repository.save(biannualAssessmentS9);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de l'enregistrement de l'évaluation S9 : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de l'enregistrement de l'évaluation S9", e);
        }
    }

    public void addBiannualAssesmentS10(BiannualAssessmentS10 biannualAssessmentS10, Long idApprentice){

        try {
            Optional<Apprentice> optionalApprenti = apprenticeService.getApprenticeById(idApprentice);
            Apprentice apprentice = optionalApprenti.orElse(null);

            biannualAssessmentS10.setApprentice(apprentice);
            biAnnualAssessmentS10Repository.save(biannualAssessmentS10);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de l'enregistrement de l'évaluation S10 : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de l'enregistrement de l'évaluation S10", e);
        }
    }

    public void updateBiannualAssessmentS5(BiannualAssessmentS5 updatedBiannualAssessmentS5, Long idApprentice) {
        try {
            Optional<BiannualAssessmentS5> optionalBiannualAssessmentS5 = biAnnualAssessmentS5Repository.findByApprenticeId(idApprentice);
            BiannualAssessmentS5 existingBiannualAssessmentS5 = optionalBiannualAssessmentS5.orElseThrow(() -> new EntityNotFoundException("Évaluation S5 non trouvée"));

            if (!existingBiannualAssessmentS5.getApprentice().getId().equals(idApprentice)) {
                throw new IllegalStateException("L'évaluation S5 n'appartient pas à l'apprenti spécifié");
            }

            // Mettez à jour les propriétés de l'évaluation existante avec les nouvelles valeurs
            existingBiannualAssessmentS5.setComMA(updatedBiannualAssessmentS5.getComMA());
            existingBiannualAssessmentS5.setComTutor(updatedBiannualAssessmentS5.getComTutor());
            existingBiannualAssessmentS5.setCommAdapter(updatedBiannualAssessmentS5.getCommAdapter());
            existingBiannualAssessmentS5.setCommCommuniquer(updatedBiannualAssessmentS5.getCommCommuniquer());
            existingBiannualAssessmentS5.setCommConcevoir(updatedBiannualAssessmentS5.getCommConcevoir());
            existingBiannualAssessmentS5.setCommDiag(updatedBiannualAssessmentS5.getCommDiag());
            existingBiannualAssessmentS5.setCommPiloter(updatedBiannualAssessmentS5.getCommPiloter());
            existingBiannualAssessmentS5.setCommProduire(updatedBiannualAssessmentS5.getCommProduire());
            existingBiannualAssessmentS5.setCommValider(updatedBiannualAssessmentS5.getCommValider());
            existingBiannualAssessmentS5.setValueAdapter(updatedBiannualAssessmentS5.getValueAdapter());
            existingBiannualAssessmentS5.setValueCommuniquer(updatedBiannualAssessmentS5.getValueCommuniquer());
            existingBiannualAssessmentS5.setValueConcevoir(updatedBiannualAssessmentS5.getValueConcevoir());
            existingBiannualAssessmentS5.setValueDiag(updatedBiannualAssessmentS5.getValueDiag());
            existingBiannualAssessmentS5.setValuePiloter(updatedBiannualAssessmentS5.getValuePiloter());
            existingBiannualAssessmentS5.setValueProduire(updatedBiannualAssessmentS5.getValueProduire());
            existingBiannualAssessmentS5.setValueValider(updatedBiannualAssessmentS5.getValueValider());

            biAnnualAssessmentS5Repository.save(existingBiannualAssessmentS5);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la mise à jour de l'évaluation S5 : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la mise à jour de l'évaluation S5", e);
        }
    }

    public void updateBiannualAssessmentS6(BiannualAssessmentS6 updatedBiannualAssessmentS6, Long idApprentice) {
        try {
            Optional<BiannualAssessmentS6> optionalBiannualAssessmentS6 = biAnnualAssessmentS6Repository.findByApprenticeId(idApprentice);
            BiannualAssessmentS6 existingBiannualAssessmentS6 = optionalBiannualAssessmentS6.orElseThrow(() -> new EntityNotFoundException("Évaluation S6 non trouvée"));

            if (!existingBiannualAssessmentS6.getApprentice().getId().equals(idApprentice)) {
                throw new IllegalStateException("L'évaluation S6 n'appartient pas à l'apprenti spécifié");
            }

            // Mettez à jour les propriétés de l'évaluation existante avec les nouvelles valeurs
            existingBiannualAssessmentS6.setComMA(updatedBiannualAssessmentS6.getComMA());
            existingBiannualAssessmentS6.setComTutor(updatedBiannualAssessmentS6.getComTutor());
            existingBiannualAssessmentS6.setCommAdapter(updatedBiannualAssessmentS6.getCommAdapter());
            existingBiannualAssessmentS6.setCommCommuniquer(updatedBiannualAssessmentS6.getCommCommuniquer());
            existingBiannualAssessmentS6.setCommConcevoir(updatedBiannualAssessmentS6.getCommConcevoir());
            existingBiannualAssessmentS6.setCommDiag(updatedBiannualAssessmentS6.getCommDiag());
            existingBiannualAssessmentS6.setCommPiloter(updatedBiannualAssessmentS6.getCommPiloter());
            existingBiannualAssessmentS6.setCommProduire(updatedBiannualAssessmentS6.getCommProduire());
            existingBiannualAssessmentS6.setCommValider(updatedBiannualAssessmentS6.getCommValider());
            existingBiannualAssessmentS6.setValueAdapter(updatedBiannualAssessmentS6.getValueAdapter());
            existingBiannualAssessmentS6.setValueCommuniquer(updatedBiannualAssessmentS6.getValueCommuniquer());
            existingBiannualAssessmentS6.setValueConcevoir(updatedBiannualAssessmentS6.getValueConcevoir());
            existingBiannualAssessmentS6.setValueDiag(updatedBiannualAssessmentS6.getValueDiag());
            existingBiannualAssessmentS6.setValuePiloter(updatedBiannualAssessmentS6.getValuePiloter());
            existingBiannualAssessmentS6.setValueProduire(updatedBiannualAssessmentS6.getValueProduire());
            existingBiannualAssessmentS6.setValueValider(updatedBiannualAssessmentS6.getValueValider());

            biAnnualAssessmentS6Repository.save(existingBiannualAssessmentS6);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la mise à jour de l'évaluation S6 : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la mise à jour de l'évaluation S6", e);
        }
    }

    public void updateBiannualAssessmentS7(BiannualAssessmentS7 updatedBiannualAssessmentS7, Long idApprentice) {
        try {
            Optional<BiannualAssessmentS7> optionalBiannualAssessmentS7 = biAnnualAssessmentS7Repository.findByApprenticeId(idApprentice);
            BiannualAssessmentS7 existingBiannualAssessmentS7 = optionalBiannualAssessmentS7.orElseThrow(() -> new EntityNotFoundException("Évaluation S7 non trouvée"));

            if (!existingBiannualAssessmentS7.getApprentice().getId().equals(idApprentice)) {
                throw new IllegalStateException("L'évaluation S7 n'appartient pas à l'apprenti spécifié");
            }

            // Mettez à jour les propriétés de l'évaluation existante avec les nouvelles valeurs
            existingBiannualAssessmentS7.setComMA(updatedBiannualAssessmentS7.getComMA());
            existingBiannualAssessmentS7.setComTutor(updatedBiannualAssessmentS7.getComTutor());
            existingBiannualAssessmentS7.setCommAdapter(updatedBiannualAssessmentS7.getCommAdapter());
            existingBiannualAssessmentS7.setCommCommuniquer(updatedBiannualAssessmentS7.getCommCommuniquer());
            existingBiannualAssessmentS7.setCommConcevoir(updatedBiannualAssessmentS7.getCommConcevoir());
            existingBiannualAssessmentS7.setCommDiag(updatedBiannualAssessmentS7.getCommDiag());
            existingBiannualAssessmentS7.setCommPiloter(updatedBiannualAssessmentS7.getCommPiloter());
            existingBiannualAssessmentS7.setCommProduire(updatedBiannualAssessmentS7.getCommProduire());
            existingBiannualAssessmentS7.setCommValider(updatedBiannualAssessmentS7.getCommValider());
            existingBiannualAssessmentS7.setValueAdapter(updatedBiannualAssessmentS7.getValueAdapter());
            existingBiannualAssessmentS7.setValueCommuniquer(updatedBiannualAssessmentS7.getValueCommuniquer());
            existingBiannualAssessmentS7.setValueConcevoir(updatedBiannualAssessmentS7.getValueConcevoir());
            existingBiannualAssessmentS7.setValueDiag(updatedBiannualAssessmentS7.getValueDiag());
            existingBiannualAssessmentS7.setValuePiloter(updatedBiannualAssessmentS7.getValuePiloter());
            existingBiannualAssessmentS7.setValueProduire(updatedBiannualAssessmentS7.getValueProduire());
            existingBiannualAssessmentS7.setValueValider(updatedBiannualAssessmentS7.getValueValider());

            biAnnualAssessmentS7Repository.save(existingBiannualAssessmentS7);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la mise à jour de l'évaluation S7 : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la mise à jour de l'évaluation S7", e);
        }
    }

    public void updateBiannualAssessmentS8(BiannualAssessmentS8 updatedBiannualAssessmentS8, Long idApprentice) {
        try {
            Optional<BiannualAssessmentS8> optionalBiannualAssessmentS8 = biAnnualAssessmentS8Repository.findByApprenticeId(idApprentice);
            BiannualAssessmentS8 existingBiannualAssessmentS8 = optionalBiannualAssessmentS8.orElseThrow(() -> new EntityNotFoundException("Évaluation S8 non trouvée"));

            if (!existingBiannualAssessmentS8.getApprentice().getId().equals(idApprentice)) {
                throw new IllegalStateException("L'évaluation S8 n'appartient pas à l'apprenti spécifié");
            }

            // Mettez à jour les propriétés de l'évaluation existante avec les nouvelles valeurs
            existingBiannualAssessmentS8.setComMA(updatedBiannualAssessmentS8.getComMA());
            existingBiannualAssessmentS8.setComTutor(updatedBiannualAssessmentS8.getComTutor());
            existingBiannualAssessmentS8.setCommAdapter(updatedBiannualAssessmentS8.getCommAdapter());
            existingBiannualAssessmentS8.setCommCommuniquer(updatedBiannualAssessmentS8.getCommCommuniquer());
            existingBiannualAssessmentS8.setCommConcevoir(updatedBiannualAssessmentS8.getCommConcevoir());
            existingBiannualAssessmentS8.setCommDiag(updatedBiannualAssessmentS8.getCommDiag());
            existingBiannualAssessmentS8.setCommPiloter(updatedBiannualAssessmentS8.getCommPiloter());
            existingBiannualAssessmentS8.setCommProduire(updatedBiannualAssessmentS8.getCommProduire());
            existingBiannualAssessmentS8.setCommValider(updatedBiannualAssessmentS8.getCommValider());
            existingBiannualAssessmentS8.setValueAdapter(updatedBiannualAssessmentS8.getValueAdapter());
            existingBiannualAssessmentS8.setValueCommuniquer(updatedBiannualAssessmentS8.getValueCommuniquer());
            existingBiannualAssessmentS8.setValueConcevoir(updatedBiannualAssessmentS8.getValueConcevoir());
            existingBiannualAssessmentS8.setValueDiag(updatedBiannualAssessmentS8.getValueDiag());
            existingBiannualAssessmentS8.setValuePiloter(updatedBiannualAssessmentS8.getValuePiloter());
            existingBiannualAssessmentS8.setValueProduire(updatedBiannualAssessmentS8.getValueProduire());
            existingBiannualAssessmentS8.setValueValider(updatedBiannualAssessmentS8.getValueValider());

            biAnnualAssessmentS8Repository.save(existingBiannualAssessmentS8);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la mise à jour de l'évaluation S8 : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la mise à jour de l'évaluation S8", e);
        }
    }

    public void updateBiannualAssessmentS9(BiannualAssessmentS9 updatedBiannualAssessmentS9, Long idApprentice) {
        try {
            Optional<BiannualAssessmentS9> optionalBiannualAssessmentS9 = biAnnualAssessmentS9Repository.findByApprenticeId(idApprentice);
            BiannualAssessmentS9 existingBiannualAssessmentS9 = optionalBiannualAssessmentS9.orElseThrow(() -> new EntityNotFoundException("Évaluation S9 non trouvée"));

            if (!existingBiannualAssessmentS9.getApprentice().getId().equals(idApprentice)) {
                throw new IllegalStateException("L'évaluation S9 n'appartient pas à l'apprenti spécifié");
            }

            // Mettez à jour les propriétés de l'évaluation existante avec les nouvelles valeurs
            existingBiannualAssessmentS9.setComMA(updatedBiannualAssessmentS9.getComMA());
            existingBiannualAssessmentS9.setComTutor(updatedBiannualAssessmentS9.getComTutor());
            existingBiannualAssessmentS9.setCommAdapter(updatedBiannualAssessmentS9.getCommAdapter());
            existingBiannualAssessmentS9.setCommCommuniquer(updatedBiannualAssessmentS9.getCommCommuniquer());
            existingBiannualAssessmentS9.setCommConcevoir(updatedBiannualAssessmentS9.getCommConcevoir());
            existingBiannualAssessmentS9.setCommDiag(updatedBiannualAssessmentS9.getCommDiag());
            existingBiannualAssessmentS9.setCommPiloter(updatedBiannualAssessmentS9.getCommPiloter());
            existingBiannualAssessmentS9.setCommProduire(updatedBiannualAssessmentS9.getCommProduire());
            existingBiannualAssessmentS9.setCommValider(updatedBiannualAssessmentS9.getCommValider());
            existingBiannualAssessmentS9.setValueAdapter(updatedBiannualAssessmentS9.getValueAdapter());
            existingBiannualAssessmentS9.setValueCommuniquer(updatedBiannualAssessmentS9.getValueCommuniquer());
            existingBiannualAssessmentS9.setValueConcevoir(updatedBiannualAssessmentS9.getValueConcevoir());
            existingBiannualAssessmentS9.setValueDiag(updatedBiannualAssessmentS9.getValueDiag());
            existingBiannualAssessmentS9.setValuePiloter(updatedBiannualAssessmentS9.getValuePiloter());
            existingBiannualAssessmentS9.setValueProduire(updatedBiannualAssessmentS9.getValueProduire());
            existingBiannualAssessmentS9.setValueValider(updatedBiannualAssessmentS9.getValueValider());

            biAnnualAssessmentS9Repository.save(existingBiannualAssessmentS9);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la mise à jour de l'évaluation S9 : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la mise à jour de l'évaluation S9", e);
        }
    }

    public void updateBiannualAssessmentS10(BiannualAssessmentS10 updatedBiannualAssessmentS10, Long idApprentice) {
        try {
            Optional<BiannualAssessmentS10> optionalBiannualAssessmentS10 = biAnnualAssessmentS10Repository.findByApprenticeId(idApprentice);
            BiannualAssessmentS10 existingBiannualAssessmentS10 = optionalBiannualAssessmentS10.orElseThrow(() -> new EntityNotFoundException("Évaluation S10 non trouvée"));

            if (!existingBiannualAssessmentS10.getApprentice().getId().equals(idApprentice)) {
                throw new IllegalStateException("L'évaluation S10 n'appartient pas à l'apprenti spécifié");
            }

            // Mettez à jour les propriétés de l'évaluation existante avec les nouvelles valeurs
            existingBiannualAssessmentS10.setComMA(updatedBiannualAssessmentS10.getComMA());
            existingBiannualAssessmentS10.setComTutor(updatedBiannualAssessmentS10.getComTutor());
            existingBiannualAssessmentS10.setCommAdapter(updatedBiannualAssessmentS10.getCommAdapter());
            existingBiannualAssessmentS10.setCommCommuniquer(updatedBiannualAssessmentS10.getCommCommuniquer());
            existingBiannualAssessmentS10.setCommConcevoir(updatedBiannualAssessmentS10.getCommConcevoir());
            existingBiannualAssessmentS10.setCommDiag(updatedBiannualAssessmentS10.getCommDiag());
            existingBiannualAssessmentS10.setCommPiloter(updatedBiannualAssessmentS10.getCommPiloter());
            existingBiannualAssessmentS10.setCommProduire(updatedBiannualAssessmentS10.getCommProduire());
            existingBiannualAssessmentS10.setCommValider(updatedBiannualAssessmentS10.getCommValider());
            existingBiannualAssessmentS10.setValueAdapter(updatedBiannualAssessmentS10.getValueAdapter());
            existingBiannualAssessmentS10.setValueCommuniquer(updatedBiannualAssessmentS10.getValueCommuniquer());
            existingBiannualAssessmentS10.setValueConcevoir(updatedBiannualAssessmentS10.getValueConcevoir());
            existingBiannualAssessmentS10.setValueDiag(updatedBiannualAssessmentS10.getValueDiag());
            existingBiannualAssessmentS10.setValuePiloter(updatedBiannualAssessmentS10.getValuePiloter());
            existingBiannualAssessmentS10.setValueProduire(updatedBiannualAssessmentS10.getValueProduire());
            existingBiannualAssessmentS10.setValueValider(updatedBiannualAssessmentS10.getValueValider());

            biAnnualAssessmentS10Repository.save(existingBiannualAssessmentS10);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la mise à jour de l'évaluation S10 : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la mise à jour de l'évaluation S10", e);
        }
    }



    public BiannualAssessmentRequest getBiannualAssessmentRequest(Long idApprenti) {

        BiannualAssessmentRequest biannualAssessmentRequest = new BiannualAssessmentRequest();

        Optional<BiannualAssessmentS5> optionalApprentice1 = biAnnualAssessmentS5Repository.findByApprenticeId(idApprenti);
        BiannualAssessmentS5 biannualAssessmentS5 = optionalApprentice1.orElse(null);
        biannualAssessmentRequest.setBiannualAssessmentS5(biannualAssessmentS5);

        Optional<BiannualAssessmentS6> optionalApprentice2 = biAnnualAssessmentS6Repository.findByApprenticeId(idApprenti);
        BiannualAssessmentS6 biannualAssessmentS6 = optionalApprentice2.orElse(null);
        biannualAssessmentRequest.setBiannualAssessmentS6(biannualAssessmentS6);

        Optional<BiannualAssessmentS7> optionalApprentice3 = biAnnualAssessmentS7Repository.findByApprenticeId(idApprenti);
        BiannualAssessmentS7 biannualAssessmentS7 = optionalApprentice3.orElse(null);
        biannualAssessmentRequest.setBiannualAssessmentS7(biannualAssessmentS7);

        Optional<BiannualAssessmentS8> optionalApprentice4 = biAnnualAssessmentS8Repository.findByApprenticeId(idApprenti);
        BiannualAssessmentS8 biannualAssessmentS8 = optionalApprentice4.orElse(null);
        biannualAssessmentRequest.setBiannualAssessmentS8(biannualAssessmentS8);

        Optional<BiannualAssessmentS9> optionalApprentice5 = biAnnualAssessmentS9Repository.findByApprenticeId(idApprenti);
        BiannualAssessmentS9 biannualAssessmentS9 = optionalApprentice5.orElse(null);
        biannualAssessmentRequest.setBiannualAssessmentS9(biannualAssessmentS9);

        Optional<BiannualAssessmentS10> optionalApprentice6 = biAnnualAssessmentS10Repository.findByApprenticeId(idApprenti);
        BiannualAssessmentS10 biannualAssessmentS10 = optionalApprentice6.orElse(null);
        biannualAssessmentRequest.setBiannualAssessmentS10(biannualAssessmentS10);


        return biannualAssessmentRequest ;
        }

    public boolean existsByApprentice_Id(Long apprenticeId) {
        return biAnnualAssessmentS5Repository.existsByApprentice_Id(apprenticeId);
    }

}
