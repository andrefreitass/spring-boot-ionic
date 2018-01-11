package com.andre.cursomc.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class S3Service {

	private Logger log = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3Cliente;

	@Value("${s3.bucket}")
	private String bucketName;

	// Metodo que faz upload para amazon
	public URI uploadFile(MultipartFile multiPartFile) {
		try {
			String nomeArquivo = multiPartFile.getOriginalFilename();
			InputStream is = multiPartFile.getInputStream();
			String tipoArquivo = multiPartFile.getContentType();
			return uploadFile(is, nomeArquivo, tipoArquivo);
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO" + e.getMessage());
		}

	}

	// Aumento de modularidade
	public URI uploadFile(InputStream is, String nomeArquivo, String tipoArquivo) {
		try {
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(tipoArquivo);
			log.info("Iniciando Upload....");
			s3Cliente.putObject(bucketName, nomeArquivo, is, meta);
			log.info("Upload Finalizado....");

			return s3Cliente.getUrl(bucketName, nomeArquivo).toURI();
		} catch (URISyntaxException e) {
			throw new RuntimeException("Erro ao converter a URL para URI");
		}
	}
}
