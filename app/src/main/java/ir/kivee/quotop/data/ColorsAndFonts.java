package ir.kivee.quotop.data;

import java.util.ArrayList;
import java.util.List;

import ir.kivee.quotop.R;

/**
 * Created by payam on 10/5/17.
 */

public class ColorsAndFonts {
    private static final List<Integer> fontColors = new ArrayList<Integer>() {{
        add(R.color.color1);
        add(R.color.color2);
        add(R.color.color3);
        add(R.color.color4);
        add(R.color.color5);
        add(R.color.color6);
        add(R.color.color7);
        add(R.color.color8);
        add(R.color.color9);
        add(R.color.color10);
        add(R.color.color11);
        add(R.color.color12);
        add(R.color.color13);
    }};
    private static final List<Integer> backColors = new ArrayList<Integer>() {{
        add(R.drawable.ic_perm_media);
        add(R.color.color1);
        add(R.color.color2);
        add(R.color.color3);
        add(R.color.color4);
        add(R.color.color5);
        add(R.color.color6);
        add(R.color.color7);
        add(R.color.color8);
        add(R.color.color9);
        add(R.color.color10);
        add(R.color.color11);
        add(R.color.color12);
        add(R.color.color13);
    }};
    private static final List<String> fontNames = new ArrayList<String>() {{
        add("Butler");
        add("Aleo");
        add("Jura");
        add("Titillium");
        add("League Gothic");
        add("Comfortaa");
        add("Ubuntu");
        add("Nickainlay");
        add("Pacifico");
        add("Cute Punk");
        add("Yellow Tail");
        add("Cheque");
        add("Hamurz");
        add("Jaapokki");

    }};
    private static final List<String> fontRes = new ArrayList<String>() {{
        add("fonts/imagefonts/font0.otf");
        add("fonts/imagefonts/font1.ttf");
        add("fonts/imagefonts/font2.ttf");
        add("fonts/imagefonts/font3.ttf");
        add("fonts/imagefonts/font4.ttf");
        add("fonts/imagefonts/font5.ttf");
        add("fonts/imagefonts/font6.ttf");
        add("fonts/imagefonts/font7.otf");
        add("fonts/imagefonts/font8.ttf");
        add("fonts/imagefonts/font9.otf");
        add("fonts/imagefonts/font10.ttf");
        add("fonts/imagefonts/font11.otf");
        add("fonts/imagefonts/font12.ttf");
        add("fonts/imagefonts/font13.ttf");

    }};


    public static List<String> getFontRes() {
        return fontRes;
    }

    public static List<String> getFontNames() {
        return fontNames;
    }

    public static List<Integer> getFontColors() {
        return fontColors;
    }

    public static List<Integer> getBackColors() {
        return backColors;
    }
}
