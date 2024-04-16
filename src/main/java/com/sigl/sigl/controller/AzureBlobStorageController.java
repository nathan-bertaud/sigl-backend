package com.sigl.sigl.controller;

import com.azure.core.util.BinaryData;
import com.sigl.sigl.dto.DocumentResponseDto;
import com.sigl.sigl.model.*;
import com.sigl.sigl.repository.*;
import com.sigl.sigl.service.AzureBlobStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AzureBlobStorageController {
    @Autowired
    private AzureBlobStorageService azureBlobStorageService;
    @Autowired
    private ApprenticeRepository apprenticeRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CoordinatorRepository coordinatorRepository;
    @Autowired
    private SummarySheetRepository summarySheetRepository;
    @Autowired
    private TechnicalAnalysisRepository technicalAnalysisRepository ;
    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private SupportRepository supportRepository;
    @Autowired
    private OtherDocumentRepository otherDocumentRepository;

// Requetes spécifiques à l'upload
    @GetMapping("/downloadFile")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName) {
        try {
            System.out.println("---Try downloadFile---");
            // Call the method in AzureBlobStorageService to handle file download
            BinaryData content =this.azureBlobStorageService.download(fileName);
            byte[] fileBytes = content.toBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());
            System.out.println("---Fin downloadFile---");
            return ResponseEntity.ok().headers(headers).body(fileBytes);
        } catch (Exception e) {
            System.out.println("---Catch downloadFile---");
            System.out.println(e.getMessage());
            return (ResponseEntity<byte[]>) ResponseEntity.badRequest();
        }
    }
    @PostMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam("id") long id){
        try  {
            System.out.println("----Début deleteFile----");
            technicalAnalysisRepository.deleteById(id);
            summarySheetRepository.deleteById(id);
            agendaRepository.deleteById(id);
            supportRepository.deleteById(id);
            otherDocumentRepository.deleteById(id);
            System.out.println("----Fin deleteFile----");
            return ResponseEntity.ok("Suppression correcte");

        } catch (Exception e) {
            System.out.println("----Catch deleteFile----");
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Erreur lors de la supression!", HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping(value = "/getDocument", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getDocument(Principal auth) {
        try {
            System.out.println("----Debut getDocument----");


            List<SummarySheet> summarySheets = null;
            List<TechnicalAnalysis> technicalAnalyses = null;
            List<Agenda> agendas = null;
            List<Support> supports = null;
            List<OtherDocument> otherDocuments = null;

            //Partie coordinatrice alternance
                if (auth.getName().equals("coraline.cholet@reseau.eseo.fr")){
                System.out.println("Coordinatrice -> GetDocuments()");
                summarySheets=summarySheetRepository.findAll();
                technicalAnalyses=technicalAnalysisRepository.findAll();
                agendas=agendaRepository.findAll();
                supports=supportRepository.findAll();
                otherDocuments=otherDocumentRepository.findAll();
            }

            else{
                // Call the method in AzureBlobStorageService to handle file download
                List<String> documentNames = azureBlobStorageService.getAllDocument();
                System.out.println(auth.getName());

                Optional<User> user = this.userRepository.findByEmail(auth.getName());
                System.out.println(user.get().getName());

                //Fiche de synthèse

                System.out.println("Fiches de synthèse:"+summarySheetRepository.findByAuthor(user.get()));
                summarySheets=summarySheetRepository.findByAuthor(user.get());

                //Rapport

                System.out.println("Rapports:"+technicalAnalysisRepository.findByAuthor(user.get()));
                technicalAnalyses=technicalAnalysisRepository.findByAuthor(user.get());

                //Plan de Rapport

                System.out.println("Plans de rapport:"+agendaRepository.findByAuthor(user.get()));
                agendas=agendaRepository.findByAuthor(user.get());

                //Support

                System.out.println("Supports :"+supportRepository.findByAuthor(user.get()));
                supports=supportRepository.findByAuthor(user.get());


                //Autre

                System.out.println("Autres Documents :"+otherDocumentRepository.findByAuthor(user.get()));
                otherDocuments=otherDocumentRepository.findByAuthor(user.get());





            }

            List<DocumentResponseDto> document_responseDto_List = new ArrayList<DocumentResponseDto>();
            if (summarySheets != null) {
                for (SummarySheet document : summarySheets) {

                    //System.out.println("Document ID: " + document.getId());
                    //System.out.println("Document Title: " + document.getName());
                    DocumentResponseDto documentResponseDto = new DocumentResponseDto(document.getName(),document.getUploadDate().toString(),document.getSemestre(),document.getId(),
                            document.getAuthor().getEmail(),"Fiche de synthèse",document.getKeyword1(),document.getKeyword2(),document.getKeyword3(),document.getKeyword4());
                    document_responseDto_List.add(documentResponseDto);
                }
                for (TechnicalAnalysis document : technicalAnalyses) {

                    //System.out.println("Document ID: " + document.getId());
                    //System.out.println("Document Title: " + document.getName());
                    DocumentResponseDto documentResponseDto = new DocumentResponseDto(document.getName(),document.getUploadDate().toString(),document.getSemestre(),document.getId(),
                            document.getAuthor().getEmail(),"Rapport",document.getKeyword1(),document.getKeyword2(),document.getKeyword3(),document.getKeyword4());
                    document_responseDto_List.add(documentResponseDto);
                }
                for (Agenda document : agendas) {

                    //System.out.println("Document ID: " + document.getId());
                    //System.out.println("Document Title: " + document.getName());
                    DocumentResponseDto documentResponseDto = new DocumentResponseDto(document.getName(),document.getUploadDate().toString(),document.getSemestre(),document.getId(),
                            document.getAuthor().getEmail(),"Plan de rapport",document.getKeyword1(),document.getKeyword2(),document.getKeyword3(),document.getKeyword4());
                    document_responseDto_List.add(documentResponseDto);
                }
                for (Support document : supports) {

                    //System.out.println("Document ID: " + document.getId());
                    //System.out.println("Document Title: " + document.getName());
                    DocumentResponseDto documentResponseDto = new DocumentResponseDto(document.getName(),document.getUploadDate().toString(),document.getSemestre(),document.getId(),
                            document.getAuthor().getEmail(),"Support",document.getKeyword1(),document.getKeyword2(),document.getKeyword3(),document.getKeyword4());
                    document_responseDto_List.add(documentResponseDto);
                }
                for (OtherDocument document : otherDocuments) {

                    //System.out.println("Document ID: " + document.getId());
                    //System.out.println("Document Title: " + document.getName());
                    DocumentResponseDto documentResponseDto = new DocumentResponseDto(document.getName(),document.getUploadDate().toString(),document.getSemestre(),document.getId(),
                            document.getAuthor().getEmail(),"Autre Document",document.getKeyword1(),document.getKeyword2(),document.getKeyword3(),document.getKeyword4());
                    document_responseDto_List.add(documentResponseDto);
                }
            }
            else {
                System.out.println("No documents found.");
            }

            System.out.println("----Fin try getDocument----");
            return ResponseEntity.ok(document_responseDto_List);

        } catch (Exception e) {
            System.out.println("----Catch getDocument----");
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Erreur lors du download!", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/testUpload")
    public ResponseEntity<String> uploadFile(Principal auth,@RequestParam("file") MultipartFile multipartFile,@RequestParam("fileName") String fileName,
                                             @RequestParam("comment") String comment,@RequestParam("status") String status,@RequestParam("selectedChoice") String selectedChoice,
                                             @RequestParam("docType") String docType,@RequestParam("keyword1") String keyword1,
                                             @RequestParam("keyword2") String keyword2,@RequestParam("keyword3") String keyword3,
                                             @RequestParam("keyword4") String keyword4) {

        try {
            String nom = auth.getName();
            Optional<User> user = userRepository.findByEmail(nom);
            Long id = user.get().getId();
            return this.azureBlobStorageService.upload(id,multipartFile, fileName,comment,statusSent(status),selectedChoice,docType,keyword1,keyword2,keyword3,keyword4);

        }
        catch(Exception e)
        {
            return new ResponseEntity<>("Erreur lors de l'upload!", HttpStatus.BAD_REQUEST);
        }

    }
    private Status statusSent(String status){
        //System.out.println(status);
        switch(status){
            case "brouillon":
                //System.out.println(status + "1");
                return Status.BROUILLON;
            case "valide":
                //System.out.println(status + "2");
                return Status.VALIDE;
            case "depose":
                //System.out.println(status + "3");
                return Status.DEPOSE;
            default :
                //System.out.println(status + "4");
                return Status.REFUSE;
        }

    }
}
