package com.gree.utils;

import cn.hutool.core.io.FileTypeUtil;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.WatermarkParameter;
import com.alibaba.simpleimage.util.ImageUtils;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

/**
 * 图片处理工具类：<br>
 * 功能：缩放图像、切割图像、图像类型转换、彩色转黑白、文字水印、图片水印等
 */
@Slf4j
public class Watermark {

    public static final String[] IMAGE_SUFFIX = new String[]{"jpg", "png", "jpeg", "gif", "bmp"};
    public static final String[] PDF_SUFFIX = new String[]{"pdf"};
    private static int interval = -50;

    /**
     * SimpleImageTool.of(is)
     * .size(600, 600)  //指定固定的宽高
     * .width(480)      //定宽
     * .height(380)     //定高
     * .rotate(30)      //旋转
     * .lockScale(true) //锁定比例，差的部分用bgcolor的颜色来填充 不设置透明
     * .scale(0.2d)     //缩放比例
     * .bgcolor(Color.blue)
     * .gray(true)      //转换成黑白图片
     * .toFile(new File("c:/img/test/t2.gif"));
     * 这些参数最后的优先级越高。 比如设了size(600,600) 再设置width(480)  那么起初size()不起作用，之所以有这需求，文章中经常需要定宽的图片按比例自动。
     */

    /**
     * @param outputStream
     * @param inputStream
     * @param waterMarkName 水印内容
     */
    public static void waterMark(OutputStream outputStream, InputStream inputStream, String waterMarkName) {

        try {
            if (!inputStream.markSupported()) {
                //输入流复用
                inputStream = new BufferedInputStream(inputStream);
            }
            inputStream.mark(Integer.MAX_VALUE);
            //文件类型
            String fileType = FileTypeUtil.getType(inputStream);
            inputStream.reset();
            if (Arrays.asList(PDF_SUFFIX).contains(fileType)) {
                setWatermarkPDFFullFill(outputStream, inputStream, waterMarkName);
            } else if (Arrays.asList(IMAGE_SUFFIX).contains(fileType)) {
                setWatermarkImage(inputStream, outputStream, Color.GRAY, waterMarkName);
            } else {
                log.error("不支持的文件类型！");
            }
        } catch (IOException | DocumentException e) {
            log.error("文件加水印出错：" + e.getMessage(), e);
        }

    }


    /**
     * PDF加水印
     *
     * @param outputStream
     * @param inputStream
     * @param waterMarkName
     */
    public static void setWatermarkPDFFullFill(OutputStream outputStream, InputStream inputStream, String waterMarkName) throws IOException, DocumentException {
        PdfReader.unethicalreading = true;
        PdfReader pdfReader = new PdfReader(inputStream);
        PdfStamper stamper = new PdfStamper(pdfReader, outputStream);
        try {
            stamper.setEncryption(
                    null,
                    null,
                    PdfWriter.ALLOW_PRINTING,
                    PdfWriter.ENCRYPTION_AES_128 | PdfWriter.DO_NOT_ENCRYPT_METADATA
            );
            int total = pdfReader.getNumberOfPages() + 1;
            PdfContentByte contentByte;
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
            PdfGState gState = new PdfGState();
            Rectangle rectangle = null;
            //设置字体透明度
            gState.setFillOpacity(0.5f);
            //设置笔触字体不透明度
            gState.setStrokeOpacity(0.4f);
            JLabel label = new JLabel();
            FontMetrics metrics;
            int textH = 0;
            int textW = 0;
            label.setText(waterMarkName);
            metrics = label.getFontMetrics(label.getFont());
            textH = metrics.getHeight();
            textW = metrics.stringWidth(label.getText());
            for (int i = 1; i < total; i++) {
                rectangle = pdfReader.getPageSizeWithRotation(i);
                //内容上方加水印
                contentByte = stamper.getOverContent(i);
                contentByte.setGState(gState);
                contentByte.beginText();
                contentByte.setColorFill(BaseColor.LIGHT_GRAY);
                //字体大小
                contentByte.setFontAndSize(baseFont, 20);
                for (int height = interval + textH; height < rectangle.getHeight(); height = height + textH * 3) {
                    for (int width = interval + textW; width < rectangle.getWidth(); width = width + textW * 2) {
                        //字体位置
                        contentByte.showTextAligned(Element.ALIGN_CENTER, waterMarkName, width - textW, height - textH, 30);
                    }
                }
                contentByte.endText();
            }
        } finally {
            stamper.close();
        }

    }

    /**
     * PDF每页加一个水印
     *
     * @param outputStream
     * @param inputStream
     * @param waterMarkName
     */
    public static void setWatermarkPDFSingleFill(OutputStream outputStream, InputStream inputStream, String waterMarkName) throws IOException, DocumentException {
        PdfReader.unethicalreading = true;
        PdfReader pdfReader = new PdfReader(inputStream);
        PdfStamper stamper = new PdfStamper(pdfReader, outputStream);
        try {
            stamper.setEncryption(
                    null,
                    null,
                    PdfWriter.ALLOW_PRINTING,
                    PdfWriter.ENCRYPTION_AES_128 | PdfWriter.DO_NOT_ENCRYPT_METADATA
            );
            int total = pdfReader.getNumberOfPages() + 1;
            PdfContentByte contentByte;
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
            PdfGState gState = new PdfGState();
            //设置字体透明度
            gState.setFillOpacity(0.5f);
            //设置笔触字体不透明度
            gState.setStrokeOpacity(0.4f);

            for (int i = 1; i < total; i++) {
                //内容上方加水印
                contentByte = stamper.getOverContent(i);
                contentByte.setGState(gState);
                contentByte.beginText();
                contentByte.setColorFill(BaseColor.LIGHT_GRAY);
                //字体大小
                contentByte.setFontAndSize(baseFont, 100);
                //字体位置
                contentByte.showTextAligned(Element.ALIGN_CENTER, waterMarkName, 400, 300, 35);
                contentByte.endText();
            }
        } finally {
            stamper.close();
        }

    }

    /**
     * PDF加水印，自定义
     */
    public static void setWatermarkPDFSingleFill(
            OutputStream outputStream,
            InputStream inputStream,
            String waterMarkName,
            float fontSize, //字体大小
            float rotation
    ) throws IOException, DocumentException {
        PdfReader.unethicalreading = true;
        PdfReader pdfReader = new PdfReader(inputStream);
        PdfStamper stamper = new PdfStamper(pdfReader, outputStream);
        try {
            stamper.setEncryption(
                    null,
                    null,
                    PdfWriter.ALLOW_PRINTING,
                    PdfWriter.ENCRYPTION_AES_128 | PdfWriter.DO_NOT_ENCRYPT_METADATA
            );
            int total = pdfReader.getNumberOfPages() + 1;
            PdfContentByte contentByte;
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
            PdfGState gState = new PdfGState();
            Rectangle rectangle = null;
            //设置字体透明度
            gState.setFillOpacity(0.5f);
            //设置笔触字体不透明度
            gState.setStrokeOpacity(0.4f);
            //水印字段的长度
            int waterLength = waterMarkName.length();
            for (int i = 1; i < total; i++) {
                rectangle = pdfReader.getPageSizeWithRotation(i);
                //内容上方加水印
                contentByte = stamper.getOverContent(i);
                contentByte.setGState(gState);
                contentByte.beginText();
                contentByte.setColorFill(BaseColor.LIGHT_GRAY);
                //字体大小
                contentByte.setFontAndSize(baseFont, fontSize);

                if (waterLength <= 20) {
                    contentByte.showTextAligned(Element.ALIGN_CENTER, waterMarkName, rectangle.getWidth()/2, rectangle.getHeight()/2, rotation);
                } else if (waterLength <= 40) {
                    contentByte.showTextAligned(Element.ALIGN_CENTER, waterMarkName.substring(0, 20), rectangle.getWidth() / 2 - 20, rectangle.getHeight() / 2 + 40, rotation);
                    contentByte.showTextAligned(Element.ALIGN_CENTER, waterMarkName.substring(20, waterLength), rectangle.getWidth() / 2, rectangle.getHeight() / 2 + 10, rotation);
                } else {
                    contentByte.showTextAligned(Element.ALIGN_CENTER, waterMarkName.substring(0, 20), rectangle.getWidth() / 2 - 20, rectangle.getHeight() / 2 + 40, rotation);
                    contentByte.showTextAligned(Element.ALIGN_CENTER, waterMarkName.substring(20, 40), rectangle.getWidth() / 2, rectangle.getHeight() / 2 + 10, rotation);
                    contentByte.showTextAligned(Element.ALIGN_CENTER, waterMarkName.substring(40, waterLength), rectangle.getWidth() / 2 + 20, rectangle.getHeight() / 2 - 20, rotation);
                }

                contentByte.endText();
            }
        } finally {
            stamper.close();
        }

    }



    /**
     * 图片水印
     *
     * @param inputStream
     * @param outputStream
     * @param contentColor
     * @param waterMarkName
     */
    public static void setWatermarkImage(InputStream inputStream, OutputStream outputStream, Color contentColor, String waterMarkName) throws IOException {
        Image srcImge = ImageIO.read(inputStream);
        int srcImgWidth = srcImge.getWidth(null);
        int srcImgHeight = srcImge.getHeight(null);
        //加水印
        BufferedImage bufferedImage = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        try {
            graphics.drawImage(srcImge, 0, 0, srcImgWidth, srcImgHeight, null);
            Font font = new Font("宋体", Font.PLAIN, 20);
            //根据图片设置水印颜色
            graphics.setColor(contentColor);
            graphics.setFont(font);
            //获取水印文字长度
            int waterLength = graphics.getFontMetrics(graphics.getFont()).charsWidth(waterMarkName.toCharArray(), 0, waterMarkName.length());
            int waterHeight = graphics.getFontMetrics(graphics.getFont()).getHeight();
            for (int width = interval + waterLength; width < srcImgWidth; width = width + waterLength * 2) {
                for (int height = interval + waterHeight; height < srcImgHeight; height = height + waterHeight * 3) {
                    graphics.drawString(waterMarkName, width - waterLength, height - waterHeight);
                }
            }
        } finally {
            graphics.dispose();
        }
        ImageIO.write(bufferedImage, "jpg", outputStream);

    }


}