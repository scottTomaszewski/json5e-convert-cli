package dev.ebullient.json5e.qute;

import java.util.Collection;
import java.util.List;

public class QuteNote {
    final String name;
    public final String source;
    public final String text;
    public final Collection<String> tags;

    public QuteNote(String name, String source, String text, Collection<String> tags) {
        this.name = name;
        this.source = source;
        this.text = text;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public List<ImageRef> images() {
        return List.of();
    }

    public String title() {
        return name;
    }

    public String targetFile() {
        return name;
    }

    public String targetPath() {
        return ".";
    }
}
