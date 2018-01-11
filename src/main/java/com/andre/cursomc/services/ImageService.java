package com.andre.cursomc.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.andre.cursomc.services.exception.FileException;

@Service
public class ImageService {

	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		// busca a extrensao do arquivo
		String extensao = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
		if (!"png".equals(extensao) && !"jpg".equals(extensao)) {
			throw new FileException("Extensao Invalida, escolha uma imagem com formato PNG e JPG");
		}
		// Leitura o arquivo
		try {
			BufferedImage imagem = ImageIO.read(uploadedFile.getInputStream());
			if ("png".equals(extensao)) {
				imagem = pngToJpg(imagem);
			}
			return imagem;
		} catch (IOException e) {
			throw new FileException("Erro ao ler o arquivo");
		}
	}

	// Metodo para converter de PNG para JPG
	public BufferedImage pngToJpg(BufferedImage imagem) {
		BufferedImage jpgImage = new BufferedImage(imagem.getWidth(), imagem.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(imagem, 0, 0, Color.WHITE, null);
		return jpgImage;
	}

	//
	public InputStream getInputStream(BufferedImage imagem, String extensao) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(imagem, extensao, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			throw new FileException("Erro ao ler o arquivo");

		}
	}

	// Recorta a imagem
	public BufferedImage cropSquare(BufferedImage sourceImg) {
		int min = (sourceImg.getHeight() <= sourceImg.getWidth()) ? sourceImg.getHeight() : sourceImg.getWidth();
		return null;
//				Scalr.crop(sourceImg, (sourceImg.getWidth() / 2) - (min / 2), (sourceImg.getHeight() / 2) - (min / 2),
//				min, min);
	}

	public BufferedImage resize(BufferedImage sourceImg, int size) {
		return null; /*Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);*/
	}

}
