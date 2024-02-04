package com.example.pdfutil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class HtmlFileToString {

    public static void main(String[] args) {

        String filePath = "/static/page1.html";
        String outputPath = "output/output.pdf";


        try {
            String htmlString = readHtmlFileToString(filePath);
            convertHtmlToPdf(htmlString, outputPath);
            System.out.println("PDF created successfully at: " + outputPath);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static String readHtmlFileToString(String filePath) throws IOException {
        Resource resource = new ClassPathResource(filePath);
        if (!resource.exists()) {
            throw new IOException("File not found: " + filePath);
        }
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }

    private static void convertHtmlToPdf(String html, String outputPath) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(new File(outputPath))) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
        }
    }

}
