package pl.pawelec.shop.admin.common.utils;

import com.github.slugify.Slugify;
import org.apache.commons.io.FilenameUtils;

public class SlugifyUtils {

    /**
     * Weryfikuje i usprawnia nazwe pliku
     * hello word.png => hello-word.png
     * ąęśćżźńłó.png => aesczznlo.png
     * @param filename
     * @return
     */
    public static String slugifyFileName(String filename) {
        String name = FilenameUtils.getBaseName(filename);
        Slugify slugify = new Slugify();
        String changedName = slugify
                .withCustomReplacement("_","-")
                .slugify(name);
        return changedName + "." + FilenameUtils.getExtension(filename);
    }

    public static String slugifySlug(String slug) {
        Slugify slugify = new Slugify();
        return slugify.withCustomReplacement("_", "-")
                .slugify(slug);
    }
}
