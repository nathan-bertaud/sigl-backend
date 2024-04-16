package com.sigl.sigl.service;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.*;


import com.azure.storage.blob.models.BlobItem;
import com.sigl.sigl.model.*;
import com.sigl.sigl.repository.*;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Transactional
@Service
public class AzureBlobStorageService {
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

    @Autowired
    private UserRepository userRepository;
    @Value("${azure.storage.account-name}")
    private String accountName;

    @Value("sp=r&st=2023-11-24T12:41:18Z&se=2023-11-24T20:41:18Z&spr=https&sv=2022-11-02&sr=c&sig=qUh%2BBgGJSCHOSmqXGvbfOwOawn5E7AQqZ52zqW0Uh14%3D")
    private String accountKey;

    @Value("https://siglgrp3storageaccount.blob.core.windows.net/uploadfilesigl")
    private String blobEndpoint;
    public BinaryData download(String fileName) {
       BlobContainerClient blobContainerClient =tryConnection();
        BlobClient blobClient1 = blobContainerClient.getBlobClient(fileName);
        BinaryData content = blobClient1.downloadContent();
        return content;
        //saveBinaryDataToLocalFile(content,"./");
        }
    public List<String> getAllDocument(){
        System.out.println("getAllDocument()");
        List<String> documentNames = new ArrayList<>();
        BlobContainerClient blobContainerClient =tryConnection();
        System.out.println(blobContainerClient.listBlobs());
        for (BlobItem blobItem : blobContainerClient.listBlobs()) {
            System.out.println("This is the blob name: " + blobItem.getName());
            String documentName = blobItem.getName();
            documentNames.add(documentName);
        }
        System.out.println("-----Fin getAllDocument()-----");
        return documentNames;
    }
    public ResponseEntity upload(Long id,MultipartFile multipartFile, String fileName, String comment, Status status,String selectedChoice, String docType, String keyword1, String keyword2, String keyword3, String keyword4) throws IOException {



        System.out.println("Document : " + selectedChoice);
        Document test;
        BlobContainerClient blobContainerClient =tryConnection();
        switch (docType){
            case "Document1": //Fiche de synthèse
                System.out.println("Fiche de synthese : " + selectedChoice);
                test = new SummarySheet();
                break;
            case "Document2": //Rapport
                System.out.println("Rapport : " + selectedChoice);
                test = new TechnicalAnalysis();
                break;
            case "Document3": //Plan de rapport
                System.out.println("Plan de rapport : " + selectedChoice);
                test = new Agenda();
                break;
            case "Document4": //Support
                System.out.println("Support : " + selectedChoice);
                test = new Support();
                break;
            default:
                System.out.println("Autre document : " + selectedChoice);
                test = new OtherDocument();
                break;

        }
        System.out.println("Apres switch : " );

        String hash = this.calculateFileHash(multipartFile.getInputStream());

        if (technicalAnalysisRepository.existsByHash(hash) || summarySheetRepository.existsByHash(hash)
                || agendaRepository.existsByHash(hash) || supportRepository.existsByHash(hash) || otherDocumentRepository.existsByHash(hash)) {
            System.out.println("-----If-----");
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Fichier déjà existant dans la BDD.Upload annulé");
        }
        else {
            System.out.println("-----Else-----");
            //Mise à jour des champs de la BDD
            test.setName(fileName);

            test.setAuthor(userRepository.findById(id).get());

            Date uploadDate = new Date(System.currentTimeMillis());
            test.setUploadDate(uploadDate);

            test.setComment(comment);

            test.setStatus(status);

            test.setHash(hash);
            test.setUrl("Azure Storage Account -> uploadfilesigl");
            test.setKeyword1(keyword1);
            test.setKeyword2(keyword2);
            test.setKeyword3(keyword3);
            test.setKeyword4(keyword4);
            System.out.println("-----Suite-----");
            //Enregistrement dans la BDD

            switch (docType){
                case "Document1": //Fiche de synthèse
                    System.out.println("1");
                    ((SummarySheet) test).setSemestre(selectedChoice.substring(0,3));
                    summarySheetRepository.save((SummarySheet) test);
                    System.out.println("Fin 1");
                    break;
                case "Document2": //Rapport
                    System.out.println("2");
                    ((TechnicalAnalysis) test).setSemestre(selectedChoice.substring(0,3));
                    technicalAnalysisRepository.save((TechnicalAnalysis)test);
                    System.out.println("Fin 2");
                    break;
                case "Document3": //Plan de rapport
                    System.out.println("3");
                    ((Agenda) test).setSemestre(selectedChoice.substring(0,3));
                    agendaRepository.save((Agenda) test);
                    System.out.println("Fin 3");
                    break;
                case "Document4": //Support
                    System.out.println("4");
                    ((Support) test).setSemestre(selectedChoice.substring(0,3));
                    supportRepository.save((Support) test);
                    System.out.println("Fin 4");
                    break;
                default:
                    System.out.println("5");
                    ((OtherDocument) test).setSemestre(selectedChoice.substring(0,3));
                    otherDocumentRepository.save((OtherDocument)test);
                    System.out.println("Fin 5");
                    break;

            }

            BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
            blobClient.upload(multipartFile.getInputStream(), multipartFile.getSize(), true);

            return ResponseEntity.ok("Ajout dans la base de données et sur l'Azure Storage Account");
        }
    }
    private static void saveBinaryDataToLocalFile(BinaryData binaryData, String localFilePath) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(localFilePath)) {
            fileOutputStream.write(binaryData.toBytes());
            System.out.println("Blob content saved to: " + localFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String calculateFileHash(InputStream inputStream) throws IOException {
        try (DigestInputStream digestInputStream = new DigestInputStream(inputStream, MessageDigest.getInstance("SHA-256"))) {
            // Read the entire file to calculate the hash
            while (digestInputStream.read() != -1) {
                // Reading the file to update the hash
            }

            // Get the hash value
            byte[] hashBytes = digestInputStream.getMessageDigest().digest();
            StringBuilder hashStringBuilder = new StringBuilder();

            // Convert the hash bytes to a hexadecimal representation
            for (byte hashByte : hashBytes) {
                hashStringBuilder.append(String.format("%02x", hashByte));
            }

            return hashStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error calculating file hash", e);
        }
    }
    private static BlobContainerClient tryConnection(){
        try {
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .endpoint("https://siglgrp3storageaccount.blob.core.windows.net/uploadfilesigl")
                    .sasToken("sp=racwdli&st=2023-12-12T14:32:20Z&se=2024-02-29T22:32:20Z&spr=https&sv=2022-11-02&sr=c&sig=uYtJ9%2F7o7gQjwrTD0DomGOtFq4VJwz2CZG58%2BqVb7zs%3D")
                    .buildClient();
        } catch (Exception e) {
            String test =e.toString();
            System.out.println("1 ere exception"+test);
            throw new BeanCreationException("Error creating AzureBlobServiceClient bean", e);
        }

        BlobContainerClient blobContainerClient;
        try {
            blobContainerClient = new BlobContainerClientBuilder()
                    .endpoint("https://siglgrp3storageaccount.blob.core.windows.net/uploadfilesigl")
                    .sasToken("sp=racwdli&st=2023-12-12T14:32:20Z&se=2024-02-29T22:32:20Z&spr=https&sv=2022-11-02&sr=c&sig=uYtJ9%2F7o7gQjwrTD0DomGOtFq4VJwz2CZG58%2BqVb7zs%3D")
                    .containerName("uploadfilesigl")
                    .buildClient();
        } catch (Exception e) {
            // Log the exception or handle it as needed
            String test =e.toString();
            System.out.println("2 eme exception"+test);
            throw new BeanCreationException("Error creating AzureBlobContainerClient bean", e);
        }
        return blobContainerClient;
    }
}
