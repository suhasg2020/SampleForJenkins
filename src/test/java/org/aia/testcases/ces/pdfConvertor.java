package org.aia.testcases.ces;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class pdfConvertor {

	public static void main(String[] args) {
		String pdfFilePath = "C:\\Users\\sghodake\\Downloads\\50100552386127_1702279784452.pdf";
		String outputDirectory = System.getProperty("user.dir");

		try {
			convertPdfToPng(pdfFilePath, outputDirectory);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void convertPdfToPng(String pdfFilePath, String outputDirectory) throws Exception {
		try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
			PDFRenderer pdfRenderer = new PDFRenderer(document);

			for (int page = 0; page < document.getNumberOfPages(); ++page) {
				PDPage pdPage = document.getPage(page);
				BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

				String outputFileName = String.format("%s/page_%03d.png", outputDirectory, page + 1);
				ImageIO.write(image, "png", new File(outputFileName));

				System.out.println("Conversion of page " + (page + 1) + " complete.");
			}

		}
	}
}
