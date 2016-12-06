package org.bychan.core.langs.json;

import org.bychan.core.dynamic.Language;
import org.bychan.core.langs.json.nodes.JsonNode;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**
 * Created by alext on 2015-01-04.
 */
public class JsonPerfTest {

    @NotNull
    private static String streamToString(@NotNull InputStream is) {
        try {
            StringBuilder sb = new StringBuilder(2048);
            char[] read = new char[2048];
            try (InputStreamReader ir = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                for (int i; -1 != (i = ir.read(read)); ) {
                    sb.append(read, 0, i);
                }
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void perf() {
        InputStream is = getClass().getResourceAsStream("/sample.json");
        assertNotNull(is);
        Language<JsonNode> l = new JsonLangBuilder().build();
        String original = streamToString(is);
        JsonNode parsed = l.newLexParser().tryParse(original, p -> p.expr(null, 0)).root();
        String prettyPrinted = parsed.prettyPrint(0);
        assertTrue(prettyPrinted.contains("V{>ꤩ혙넪㭪"));
        String pattern = "[ \\t\\n\\r]";
        String outputStripped = prettyPrinted.replaceAll(pattern, "");
        String originalStripped = original.replaceAll(pattern, "");
        assertEquals(originalStripped, outputStripped);
    }
}
