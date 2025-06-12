package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class GraphicsConverter implements TextGraphicsConverter {
    private int maxWidth = Integer.MAX_VALUE;
    private int maxHeight = Integer.MAX_VALUE;
    private double maxRatio = Double.MAX_VALUE;
    private TextColorSchema colorSchema = new ColorSchema();

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        BufferedImage img = ImageIO.read(new URL(url));


        double ratio = (double) img.getWidth() / img.getHeight();
        if (ratio > maxRatio) {
            throw new BadImageSizeException(ratio, maxRatio);
        }


        img = rescale(img);


        return convertToText(img);
    }

    private BufferedImage rescale(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();


        if (width > maxWidth) {
            height = (int) (height * ((double) maxWidth / width));
            width = maxWidth;
        }

        if (height > maxHeight) {
            width = (int) (width * ((double) maxHeight / height));
            height = maxHeight;
        }

        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        resized.getGraphics().drawImage(img.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH), 0, 0, null);
        return resized;
    }

    private String convertToText(BufferedImage img) {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int color = img.getRGB(x, y);

                sb.append(colorSchema.convert(color)).append(colorSchema.convert(color));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.colorSchema = schema;
    }
}