package ru.netology.graphics.image;
import static java.lang.Math.abs;

public class ColorSchema implements TextColorSchema{

    private static final char[] SCHEMA = {'-', '◌', '☉', '○', '◎', '◍', '◉', '●', '▇'};

    @Override
    public char convert(int color) {

        int brightness = Math.abs(color);
        int index = (int)Math.floor(brightness * (SCHEMA.length - 1) / 16777215);

        return SCHEMA[index];
    }
}
