package dev.ebullient.json5e;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.ebullient.json5e.io.Json5eTui;
import dev.ebullient.json5e.io.MarkdownWriter;
import dev.ebullient.json5e.io.TemplatePaths;
import dev.ebullient.json5e.io.Templates;
import dev.ebullient.json5e.tools5e.IndexType;
import dev.ebullient.json5e.tools5e.Json2MarkdownConverter;
import dev.ebullient.json5e.tools5e.JsonIndex;
import io.quarkus.arc.Arc;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class JsonDataTest {
    final static Path PROJECT_PATH = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
    final static Path TEST_PATH_JSON = PROJECT_PATH.resolve("src/test/resources/paths.json");
    final static Path OUTPUT_PATH = PROJECT_PATH.resolve("target/test-data");

    // for compile/test purposes. Must clone/sync separately.
    final static Path TOOLS_PATH = PROJECT_PATH.resolve("5etools-mirror-1.github.io/data");

    protected static Json5eTui tui;
    protected static Templates templates;
    protected static JsonIndex index;

    @BeforeAll
    public static void setupDir() throws Exception {
        OUTPUT_PATH.toFile().mkdirs();

        // inject application scoped beans..
        tui = Arc.container().instance(Json5eTui.class).get();
        templates = Arc.container().instance(Templates.class).get();
        tui.init(null, true, true);
        if (TOOLS_PATH.toFile().exists()) {
            index = new JsonIndex(List.of("*"), tui)
                    .importTree(TestUtils.doParse(TEST_PATH_JSON));
            TestUtils.fullIndex(index, TOOLS_PATH, tui);
            index.prepare();
        }
    }

    @BeforeEach
    public void before() throws Exception {

    }

    @AfterEach
    public void cleanup() {
        templates.setCustomTemplates(null);
        tui.close();
    }

    @Test
    public void testKeyIndex() throws Exception {
        if (TOOLS_PATH.toFile().exists()) {
            Path p1Full = OUTPUT_PATH.resolve("allIndex.json");
            index.writeIndex(p1Full);
            Path p1Source = OUTPUT_PATH.resolve("allSourceIndex.json");
            index.writeSourceIndex(p1Source);

            // JsonIndex index2 = new JsonIndex(List.of("PHB", "DMG", "XGE"), tui);
            // TestUtils.fullIndex(index2, TOOLS_PATH, tui);

            // Path p2Full = OUTPUT_PATH.resolve("some-Index.json");
            // index2.writeIndex(p2Full);
            // Path p2Source = OUTPUT_PATH.resolve("some-SourceIndex.json");
            // index2.writeSourceIndex(p2Source);

            // TestUtils.assertContents(p2Full, p2Source, false); // filtered
        }
    }

    @Test
    public void testNameList() throws Exception {
        if (TOOLS_PATH.toFile().exists()) {
            MarkdownWriter writer = new MarkdownWriter(OUTPUT_PATH, templates, tui);
            new Json2MarkdownConverter(index, writer)
                    .writeFiles(IndexType.namelist);
        }
    }

    @Test
    public void testFeatList() throws Exception {
        if (TOOLS_PATH.toFile().exists()) {
            MarkdownWriter writer = new MarkdownWriter(OUTPUT_PATH, templates, tui);
            new Json2MarkdownConverter(index, writer)
                    .writeFiles(IndexType.feat);
        }
    }

    @Test
    public void testBackgroundList() throws Exception {
        if (TOOLS_PATH.toFile().exists()) {
            MarkdownWriter writer = new MarkdownWriter(OUTPUT_PATH, templates, tui);
            new Json2MarkdownConverter(index, writer)
                    .writeFiles(IndexType.background);
        }
    }

    @Test
    public void testSpellList() throws Exception {
        if (TOOLS_PATH.toFile().exists()) {
            MarkdownWriter writer = new MarkdownWriter(OUTPUT_PATH, templates, tui);
            new Json2MarkdownConverter(index, writer)
                    .writeFiles(IndexType.spell);
        }
    }

    @Test
    public void testRaceList() throws Exception {
        if (TOOLS_PATH.toFile().exists()) {
            MarkdownWriter writer = new MarkdownWriter(OUTPUT_PATH, templates, tui);
            new Json2MarkdownConverter(index, writer)
                    .writeFiles(IndexType.race);
        }
    }

    @Test
    public void testClassList() throws Exception {
        if (TOOLS_PATH.toFile().exists()) {
            MarkdownWriter writer = new MarkdownWriter(OUTPUT_PATH, templates, tui);
            new Json2MarkdownConverter(index, writer)
                    .writeFiles(IndexType.classtype);
        }
    }

    @Test
    public void testItemList() throws Exception {
        if (TOOLS_PATH.toFile().exists()) {
            // TODO: objects, vehicles, magicvariants

            MarkdownWriter writer = new MarkdownWriter(OUTPUT_PATH, templates, tui);
            new Json2MarkdownConverter(index, writer)
                    .writeFiles(IndexType.item);
        }
    }

    @Test
    public void testMonsterList() throws Exception {
        if (TOOLS_PATH.toFile().exists()) {
            MarkdownWriter writer = new MarkdownWriter(OUTPUT_PATH, templates, tui);
            new Json2MarkdownConverter(index, writer)
                    .writeFiles(IndexType.monster);
        }
    }

    @Test
    public void testMonsterYamlHeader() throws Exception {
        if (TOOLS_PATH.toFile().exists()) {
            TemplatePaths templatePaths = new TemplatePaths();
            templatePaths.setCustomTemplate("monster2md.txt",
                    PROJECT_PATH.resolve("src/main/resources/templates/monster2md-yamlStatblock-header.txt"));
            templates.setCustomTemplates(templatePaths);

            MarkdownWriter writer = new MarkdownWriter(OUTPUT_PATH.resolve("yaml-header"), templates, tui);
            new Json2MarkdownConverter(index, writer)
                    .writeFiles(IndexType.monster);
        }
    }

    @Test
    public void testMonsterYamlBody() throws Exception {
        if (TOOLS_PATH.toFile().exists()) {
            TemplatePaths templatePaths = new TemplatePaths();
            templatePaths.setCustomTemplate("monster2md.txt",
                    PROJECT_PATH.resolve("src/main/resources/templates/monster2md-yamlStatblock-body.txt"));
            templates.setCustomTemplates(templatePaths);

            MarkdownWriter writer = new MarkdownWriter(OUTPUT_PATH.resolve("yaml-body"), templates, tui);
            new Json2MarkdownConverter(index, writer)
                    .writeFiles(IndexType.monster);
        }
    }

    @Test
    public void testRules() throws Exception {
        if (TOOLS_PATH.toFile().exists()) {
            MarkdownWriter writer = new MarkdownWriter(OUTPUT_PATH, templates, tui);
            new Json2MarkdownConverter(index, writer)
                    .writeRulesAndTables();
        }
    }
}
